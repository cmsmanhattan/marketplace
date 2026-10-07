# CMS-Manhattan — Fixes and Updates Summary

Changes have been made to existing project classes. There is only one new file:
`com/cbsinc/cms/utils/FileNames.java`.

Compilation note: The container only contains JRE 21, with no `javac`, `mvn`, or network access. Everything was verified statically—bracket balance and structure across all 427 files, and called method signatures cross-referenced with sources.

## 1. Deployment Blockers

### `web.xml` — Invalid URL Pattern

```
<url-pattern>*</url-pattern>    →    <url-pattern>/*</url-pattern>

```

Under Servlet 6.0 §12.2, a pattern must be `/`, start with `/`, or start with `*.`. A naked `*` does not match any valid case. Tomcat 10 rejects the **entire** descriptor rather than just a single rule, preventing the context from deploying. This was the sole reason the application failed to start.

### Servlets Without Classes

`DownloadServletByOdrder1` and `DownloadServletByOdrder2` were declared and mapped, but no corresponding classes existed in `src/`. Requests to `/downloadservletbyodrder1` or `/downloadservletbyodrder2` resulted in `ClassNotFoundException` → HTTP 500. Their declarations and mappings have been commented out. The working servlet is `downloadservletbyodrder` (without the suffix number).

## 2. Security Improvements

### `AuthFilter` Was Unregistered and Non-Functional

Two independent defects:

1. **Unregistered:** The class existed, and its Javadoc listed 20 protected paths in `@web.filter-mapping` tags (using XDoclet syntax, which is not processed here). There was no filter declaration in `web.xml`. Consequently, the filter never executed, leaving all 20 paths—including `catalog_add.jsp`, `PayGatewaySetup.jsp`, and `fileservletupload`—completely open.

2. **Check Always Passed:** The condition checked:

   ```
   if (authorizationPageBeanId.getStrLogin().length() == 0)
   
   ```

   However, `FrontControllers.doFilter` assigns `setStrLogin(SiteRole.GUEST)` to every anonymous user, where `SiteRole.GUEST` = `"user"`. The login was never empty, so the condition was never true. Even if the filter had been registered, it would have allowed everyone through.

*Fix:* Rewritten to check against `SiteRole.GUEST`, `getIntUserID()`, and the access level. The threshold is set via `init-param`:

```
<init-param>
  <param-name>minAccessLevel</param-name>
  <param-value>1</param-value>
</init-param>

```

Project levels: 0 = guest, 1 = registered (`RegistrationAction` sets 1), 2 = administrator. Set to **1** intentionally because protected paths include `fileservletupload` and `imageservletupload`, which regular vendors use. A threshold of 2 would block them.

### Stored XSS in `FrontControllers` Post-Transformation

```
htmlData = htmlData.replaceAll("&lt;", "<");
htmlData = htmlData.replaceAll("&gt;", ">");

```

These two lines scanned the **entire** document and undid escaping just performed by the stylesheet. Any text entering the page via XML—product names, store names, reviews, profile fields—was turned back into markup. Stored values like `&lt;script&gt;…&lt;/script&gt;` were served to all visitors as executable tags. Because session cookies are not `HttpOnly`, this represented a full account takeover vector.
*Fix:* These lines were removed. A mechanism for intentional raw HTML already existed via the `<r>` element, which is wrapped in CDATA further up.

### Path Traversal via Cache File Names

`buildCashPageName()` constructed filenames from raw request parameters (`searchquery`, `catalog_id`, `fromcost`, `offset`, `creteria1_id`…`creteria10_id`). For `ProductInfo.jsp`, it used `getQueryString()` directly. The result was passed to file readers and forward dispatchers:

```
pathInfo = path + "_" + buildCashPageName(...) + ".html";
cashPage = getCashDir(servletContext) + File.separatorChar + pathInfo;
new File(cashPage)                                        // read
getRequestDispatcher("cashes" + sep + pathInfo).forward(…)  // serve

```

A request like `Productlist.jsp?searchquery=../../../WEB-INF/web` escaped the cache directory during both reads and writes, enabling arbitrary file reading and overwriting within the deployed application.
*Fix:* Names are now hashed using SHA-256 (hex). This adopts the approach previously commented out right below: `// return Long.toString(buff.toString().hashCode());` but upgrades to SHA-256 to avoid 32-bit collisions. This also resolves potential `NullPointerException` issues with `getQueryString()` and prevents `File name too long` errors on complex filtered queries.

### XXE / SSRF in the Transformer

`TransformerFactory.newInstance()` used default settings, allowing stylesheets to pull in external DTDs or style files via `<!DOCTYPE … SYSTEM "…">`, `document()`, or `xsl:import` with absolute paths or HTTP URLs.
*Fix:* `ACCESS_EXTERNAL_DTD` and `ACCESS_EXTERNAL_STYLESHEET` were set to empty strings. Relative links within `xsl/` continue to work normally.

## 3. Stability & Availability Fixes

### Container Restart on OutOfMemoryError

```
catch (OutOfMemoryError e) {
    …
    reloadServer();   // Runtime.getRuntime().exec("/etc/init.d/cmsbo1")
}

```

A single request allocating too much memory would restart the entire container, dropping all in-flight requests and sessions.
*Fix:* The error is logged and rethrown. `reloadServer()` now only logs and returns `false`, and is marked `@Deprecated`.

### `System.exit()` on Startup Paths

`ServletSiteEvent.init()` invoked `cmsServiceBase.sendRequestToServiceUsingEureka()`, whose catch block executed `System.exit(-1)`. An unreachable or slow Eureka server would shut down the **entire Tomcat JVM** and all hosted applications.
*Fix:* Replaced with logging and a clean `return`. An adjacent NPE loop bug was also fixed.

### `System.exit(0)` on Mail Server Outage

`AddUserInMailNew.exec()` used `System.exit(0)` if the mail server was unreachable, signaling a normal shutdown so supervisors wouldn't restart it.
*Fix:* Replaced with `IllegalStateException`.

## 4. Fixed `@Singleton` Injection

`CmsBeansFactoty.getBean()` had three defects:

1. **Returned a String:** It returned class name strings instead of object instances, causing `IllegalArgumentException` on every annotated controller request. Now resolved via `Class.forName(...).getDeclaredConstructor().newInstance()`.

2. **`unlock()` Without Lock Ownership:** `lock.unlock()` in a `finally` block caused `IllegalMonitorStateException` under contention. Replaced with `ConcurrentHashMap.computeIfAbsent` for safe, lock-free thread-safe initialization.

3. **Silent `null` Returns:** Timeouts returned `null` silently; now throws `IOException`.

## 5. Extension Handling on Files Without Extensions

Throughout the project, filenames lacking dots (e.g., `IMG_0431`) caused `StringIndexOutOfBoundsException` due to unsafe `lastIndexOf(".")` logic, while download servlets incorrectly treated whole filenames as extensions.
*Fix:* Created `com.cbsinc.cms.utils.FileNames` providing safe extension and path-stripping methods (`stripPath()` prevents path traversal attacks during file uploads).

## 6. Logging and Exception Cleanup

* **Empty catch blocks** were populated with contextual logging.

* **`InterruptedException` handling:** Restored thread interruption flags (`Thread.currentThread().interrupt()`) to prevent hanging un-deployments.

* **HTTP status codes:** Fixed catch blocks that previously returned HTTP 200 with empty bodies on error; uncommitted error states now properly trigger HTTP 500.

* **`System.gc()` calls** were stripped from error handlers to prevent massive global thread pauses.

* **`isClearMemory()` spam:** Reduced excessive stdout logging to a single debug statement.

## 7. Formatting & Renaming

* **`intLevelUp` → `roleId`**: Thoroughly renamed across Java classes and 24 JSP files to match database columns (`levelup_cd`) and XSL templates (`role_id`), preventing runtime expression errors.

* **File Cleanup:** Removed trailing whitespace across 281 files, normalized line endings to CRLF, added missing end-of-file newlines, and cleaned up unused imports.

## 8. Critical Input Validation & SQL Injections Addressed

* **Login Form SQL Injection:** Fixed string concatenation in `AuthorizationPageFaced.isLoginCorrect` by switching to parameterized queries (`executeQueryWithArgs`).

* **Cookie SQL Injection:** Secured the `session_id` cookie check against SQL injection vectors and enforced `HttpOnly`, `Path=/`, and `Secure` attributes.

* **Numeric ID Enforcement:** Added validation middleware in `FrontControllers.doFilter` ensuring parameters ending in `_id` (and `offset`) are strictly valid integers, returning HTTP 400 on violations.

* **Search Input Sanitization:** Added `Validation.escapeSqlLiteral()` to neutralize malicious input in search queries and site creation fields.
