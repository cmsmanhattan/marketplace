package com.cbsinc.cms.faceds;

import com.cbsinc.cms.utils.Validation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.perf4j.aop.Profiled;
import org.w3c.dom.Document;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CreateShopBean;
import com.cbsinc.cms.GetValueTool;
import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.controllers.LanguageEnum;
import com.cbsinc.cms.controllers.SiteType;
import com.cbsinc.cms.services.tomcat.AddAliase;
import com.cbsinc.cms.services.tomcat.DomainRegister;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code. You can not use it and you
 * cannot change it without written permission from Konstantin Grabko Email:
 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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

public class AuthorizationPageFaced extends com.cbsinc.cms.WebControls {

	final private static String CLASS_NAME = "com.cbsinc.cms.faceds.AuthorizationPageFaced";
	final static private Logger log = Logger.getLogger(AuthorizationPageFaced.class);

	final CreateShopBean createShopBean = new CreateShopBean();
//	sequences_rs = PropertyResourceBundle.getBundle("sequence");
	final private ResourceBundle sequencesRs = PropertyResourceBundle.getBundle("sequence");
	final private ResourceBundle setupResources = PropertyResourceBundle.getBundle("appconfig");
	final private ResourceBundle sessionScope = PropertyResourceBundle.getBundle("session_scope");

	final ConcurrentHashMap transformerMap = new ConcurrentHashMap(1024);

	final ResourceBundle actionsResources = PropertyResourceBundle.getBundle("web_actions");
	final ResourceBundle xsltResources = PropertyResourceBundle.getBundle("web_xslt");
	final Document doc = null;

	public AuthorizationPageFaced() {
	}

	/**
	 * Returns the session id carried by the {@code session_id} cookie, or, when no such cookie exists, the id of the current HTTP session, issuing it as an HttpOnly, path=/ cookie (Secure on HTTPS).
	 *
	 * @return the session id, or an empty string when there is no session
	 */
	@Profiled(logger = CLASS_NAME, tag = "getCokieSessionId", message = "HttpServletRequest: {$0} , HttpServletResponse: { $1 }  , getCokieSessionId: {@ retrun }")
	final public String getCokieSessionId(final HttpServletRequest request, final HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("session_id")) {
					return cookies[i].getValue();
				}
			}
		}

		// HttpSession httpSession = request.getSession() ;
		HttpSession httpSession = request.getSession(false);
		if (httpSession == null)
			return "";
		// FIX: the cookie that restores a login on every request was issued
		// without HttpOnly, so any script on the page (see the XSS fix in
		// FrontControllers) could read it and replay it from elsewhere. It is
		// also scoped to the whole site now, so it is sent on every path and not
		// only under the directory of the page that created it.
		Cookie sessionCookie = new Cookie("session_id", httpSession.getId());
		sessionCookie.setHttpOnly(true);
		sessionCookie.setPath("/");
		sessionCookie.setSecure(request.isSecure());
		response.addCookie(sessionCookie);
		return httpSession.getId();
	}

	/**
	 * Tells whether the request carries a {@code session_id} cookie.
	 *
	 * @return true if the cookie is present
	 */
	@Profiled(logger = CLASS_NAME, tag = "isCokieSessionIdExists", message = "HttpServletRequest: {$0} , HttpServletResponse: { $1 }  , isCokieSessionIdExists: {@ retrun }")
	final public boolean isCokieSessionIdExists(final HttpServletRequest request, final HttpServletResponse response) {
		boolean exists = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("session_id")) {
					return true;
				}
			}
		}

		return exists;
	}

	/**
	 * Verifies a login/password pair for the current site and fills the bean on success. The query is parameterised (was injectable); passwords are PBKDF2 hashes checked in Java, and a legacy clear-text match is transparently re-hashed.
	 *
	 * @param login user login
	 * @param passwd clear-text password from the form
	 * @param authorizationBean bean populated on success
	 * @param idsession session id to associate with the login
	 * @return true if the credentials match
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginCorrect", message = "login: {$0} , idsession: { $3 } , isLoginCorrect: {@ retrun }")
	final public boolean isLoginCorrect(final String login, final String passwd,
			final AuthorizationPageBean authorizationBean, String idsession) {
		QueryManager qm = null;
		String query = "";
		try {
			if (login == null || login.length() == 0)
				return false;
			if (passwd == null || passwd.length() == 0)
				return false;

			qm = new QueryManager();
			qm.beginTransaction();
			// FIX (SQL injection on the login form): the query used to be built as
			//   "... where login = '" + login + "' and passwd = '" + passwd + "' ..."
			// so a login of   admin' --   ended the string, commented out the
			// password check and signed the caller in as admin with any password.
			// The same field could also read the whole tuser table with a UNION.
			// The query manager already supports PreparedStatement parameters;
			// they are used here. site_id is a numeric column, so it is bound as a
			// Long rather than a string.
			// Passwords are stored hashed (PBKDF2). Never compare the password in
			// SQL: select the account by login only, then verify the hash in Java.
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  FROM tuser  where  login = ?  and  site_id = ?";
			qm.executeQueryWithArgs(query, new Object[] { login, Long.valueOf(authorizationBean.getSiteId()) });
			// Second clause: controllers re-authenticate on site switch using the
			// password kept in the session bean; when that bean was loaded from the
			// database (cookie login, admin lookup) it holds the stored hash itself.
			String storedPasswd = (String) (qm.rows().size() == 1 ? qm.getValueAt(0, 2) : null);
			boolean passwdIsStoredHash = com.cbsinc.cms.utils.PasswordHash.looksHashed(passwd)
					&& passwd.equals(storedPasswd);
			if (qm.rows().size() == 1
					&& (passwdIsStoredHash || com.cbsinc.cms.utils.PasswordHash.matches(passwd, storedPasswd))) {
				authorizationBean.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));
				authorizationBean.setStrLogin((String) qm.getValueAt(0, 1));
				authorizationBean.setStrPasswd(passwd);
				authorizationBean.setStrFirstName((String) qm.getValueAt(0, 3));
				authorizationBean.setStrLastName((String) qm.getValueAt(0, 4));
				authorizationBean.setStrEMail((String) qm.getValueAt(0, 5));
				authorizationBean.setStrPhone((String) qm.getValueAt(0, 6));
				authorizationBean.setStrMPhone((String) qm.getValueAt(0, 7));
				authorizationBean.setStrFax((String) qm.getValueAt(0, 8));
				authorizationBean.setStrIcq((String) qm.getValueAt(0, 9));
				authorizationBean.setStrWebsite((String) qm.getValueAt(0, 10));
				authorizationBean.setStrQuestion((String) qm.getValueAt(0, 11));
				authorizationBean.setStrAnswer((String) qm.getValueAt(0, 12));
				authorizationBean.setRoleId(Integer.parseInt((String) qm.getValueAt(0, 13)));
				authorizationBean.setStrCompany((String) qm.getValueAt(0, 15));
				authorizationBean.setCountryId((String) qm.getValueAt(0, 16));
				authorizationBean.setCityId((String) qm.getValueAt(0, 17));
				authorizationBean.setCurrencyId((String) qm.getValueAt(0, 18));
				authorizationBean.setIntLogined(1);
				// FIX: idsession comes from the request as well; bound as a parameter
				// for the same reason as above.
				// The value is the "session_id" cookie, returned verbatim from the
				// client, so it is attacker-controlled just like the login field.
				query = "UPDATE tuser set idsession = ?  where  user_id = ?";
				try (java.sql.PreparedStatement ps = qm.getCurrentConnection().prepareStatement(query)) {
					ps.setString(1, idsession);
					ps.setLong(2, authorizationBean.getIntUserID());
					ps.executeUpdate();
				}
				// Upgrade a legacy clear-text password to a hash on successful login.
				// Best effort: a stored hash is 83 characters long, so on a database
				// that still declares PASSWD varchar(50) (sql/password_hash_migration.sql
				// not applied yet) this UPDATE raises "Data too long for column 'PASSWD'"
				// in strict mode, or silently truncates the hash in non-strict mode.
				// It must not abort an otherwise valid login, and it must not roll back
				// the idsession update above, so the failure is caught right here.
				// Falling through to the outer catch used to roll the login back and
				// report the (unrelated) idsession statement, because the local variable
				// holding this statement was never the one logged.
				if (!passwdIsStoredHash && !com.cbsinc.cms.utils.PasswordHash.looksHashed(storedPasswd)) {
					String upgrade = "UPDATE tuser set passwd = ? where user_id = ?";
					try (java.sql.PreparedStatement ps = qm.getCurrentConnection().prepareStatement(upgrade)) {
						ps.setString(1, com.cbsinc.cms.utils.PasswordHash.hash(passwd));
						ps.setLong(2, authorizationBean.getIntUserID());
						ps.executeUpdate();
					} catch (SQLException ex) {
						// Login stays valid; only the transparent upgrade is skipped.
						log.error(upgrade + " -- the password was left in its legacy form. "
								+ "Apply sql/password_hash_migration.sql to widen tuser.PASSWD "
								+ "to VARCHAR(255), otherwise new registrations and password "
								+ "changes will fail the same way.", ex);
					}
				}
				qm.commit();
				return true;
			}
			authorizationBean.setIntLogined(2);
			return false;
		} catch (SQLException ex) {
			qm.rollback();
			log.error(query, ex);
			return false;
		} catch (Exception ex) {
			qm.rollback();
			log.error(ex);
			return false;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Resolves the site id for a virtual host name.
	 *
	 * @param host request host name
	 * @return the newest site_id for the host, or "2" when unknown
	 */
	@Profiled(logger = CLASS_NAME, tag = "getSiteIdByHost", message = "host: {$0}  , getSiteIdByHost: {@ retrun }")
	final public String getSiteIdByHost(final String host) {

		String siteId = "2";
		QueryManager Adp = null;
		// FIX (pre-auth SQL injection): `host` is the HTTP Host header and was
		// concatenated straight into the statement. Bound as a parameter.
		String query = "select site_id  from site where host = ? order by site_id DESC limit 1 ";
		try {
			Adp = new QueryManager();
			Adp.executeQueryWithArgs(query, new Object[] { host });
			if (Adp.rows().size() > 0) {
				siteId = Adp.getValueAt(0, 0);
			}
		} catch (SQLException e) {
			log.error(e);
		} finally {
			Adp.close();
		}

		return siteId;
	}

	@Profiled(logger = CLASS_NAME, tag = "loadClassesSessionScopeFromBase", message = "httpSession: {$0}  , userId: {@1}")
	final void loadClassesSessionScopeFromBase(final HttpSession httpSession, final Integer userId) {
		QueryManager qm = new QueryManager();
		String query = "";
		String key = "";
		try {
			query = "select USER_ID , TYPE , CLASSBODY  from store_session WHERE  USER_ID = ?";
			ResultSet rs = qm.executeQueryResultSet(query, new Object[] { userId });
			while (rs.next()) {
				Enumeration enumeration = sessionScope.getKeys();
				while (enumeration.hasMoreElements()) {
					key = (String) enumeration.nextElement();
					String type = sessionScope.getString(key).trim();
					String typedb = rs.getString("TYPE");
					if (typedb != null)
						if (typedb.equals(type)) {
							Object obj = rs.getObject("CLASSBODY");
							httpSession.setAttribute(key, obj);
							System.out.println("load key: " + key + " object: " + obj.getClass().getName());
						}
				}
			}

		} catch (Exception e) {
			log.error(e);
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Deletes the persisted session rows for a cookie session id (logout) and marks the bean as logged out.
	 *
	 * @param authorizationBean bean to mark, may be null
	 * @param cokieSessionId cookie session id; null is a no-op
	 */
	@Profiled(logger = CLASS_NAME, tag = "clearCookieFromBD", message = "authorizationBean: {$0}  , cokieSessionId: {@1}")
	final public void clearCookieFromBD(final AuthorizationPageBean authorizationBean, final String cokieSessionId) {
		QueryManager Adp = null;
		if (cokieSessionId == null)
			return;
		String query = "";
		try {
			Adp = new QueryManager();
			Adp.beginTransaction();
			query = "DELETE  FROM  store_session WHERE  idsession_hash1 = ?  and idsession_hash2 = ?"
					+ " and idsession_hash3 = ? and idsession_hash4 = ?";
			Adp.executeUpdateWithArgs(query, new Object[] {
					Long.valueOf(getIdsessionHash1(cokieSessionId)),
					Long.valueOf(getIdsessionHash2(cokieSessionId)),
					Long.valueOf(getIdsessionHash3(cokieSessionId)),
					Long.valueOf(getIdsessionHash4(cokieSessionId)) });
			Adp.commit();
			if (authorizationBean != null)
				authorizationBean.setIntLogined(2);
		} catch (SQLException ex) {
			Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			Adp.rollback();
			log.error(ex);
		} finally {
			if (Adp != null)
				Adp.close();
		}
	}

	/**
	 * Restores a login from a {@code session_id} cookie by looking the session up in store_session. Runs on every request; the cookie value is bound as a parameter (was injectable, granting admin).
	 *
	 * @param sessionId cookie session id
	 * @param authorizationBean bean populated on success
	 * @return true if a stored session matched
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginCorrect", message = "sessionId: {$0}  , authorizationBean: {@1}")
	final public boolean isLoginCorrect(final String sessionId, final AuthorizationPageBean authorizationBean) {
		QueryManager qm = null;
		String query = "";
		try {
			if (sessionId == null || sessionId.length() == 0)
				return false;

			qm = new QueryManager();
			// FIX (SQL injection via cookie): sessionId is the raw value of the
			// "session_id" cookie and was concatenated into the query. This method
			// runs on every request to restore a login from the cookie, so a cookie
			// of   ' OR levelup_cd = 2 LIMIT 1 --   signed the visitor in as the
			// first administrator on any page, with no form and no password.
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  FROM tuser  where  idsession = ?";

			qm.executeQueryWithArgs(query, new Object[] { sessionId });
			if (qm.rows().size() == 1) {
				authorizationBean.setIntUserID(Integer.parseInt((String) qm.getValueAt(0, 0)));
				authorizationBean.setStrLogin((String) qm.getValueAt(0, 1));
				authorizationBean.setStrPasswd((String) qm.getValueAt(0, 2));
				authorizationBean.setStrFirstName((String) qm.getValueAt(0, 3));
				authorizationBean.setStrLastName((String) qm.getValueAt(0, 4));
				authorizationBean.setStrEMail((String) qm.getValueAt(0, 5));
				authorizationBean.setStrPhone((String) qm.getValueAt(0, 6));
				authorizationBean.setStrMPhone((String) qm.getValueAt(0, 7));
				authorizationBean.setStrFax((String) qm.getValueAt(0, 8));
				authorizationBean.setStrIcq((String) qm.getValueAt(0, 9));
				authorizationBean.setStrWebsite((String) qm.getValueAt(0, 10));
				authorizationBean.setStrQuestion((String) qm.getValueAt(0, 11));
				authorizationBean.setStrAnswer((String) qm.getValueAt(0, 12));
				authorizationBean.setRoleId(Integer.parseInt((String) qm.getValueAt(0, 13)));
				authorizationBean.setStrCompany((String) qm.getValueAt(0, 15));
				authorizationBean.setCountryId((String) qm.getValueAt(0, 16));
				authorizationBean.setCityId((String) qm.getValueAt(0, 17));
				authorizationBean.setCurrencyId((String) qm.getValueAt(0, 18));
				authorizationBean.setIntLogined(1);

				// Adp.close();
				return true;
			}
			authorizationBean.setIntLogined(2);
			// Adp.close();
			return false;
		} catch (SQLException ex) {
			log.error(query, ex);
			return false;
		} catch (Exception ex) {
			log.error(ex);
			return false;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * For a new HTTP session, reloads the serialised session-scope beans saved under the cookie session id and puts them back into the HttpSession.
	 *
	 * @return true if any stored bean was restored; for an existing session returns false
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginFromCookie", message = "sessionId: {$0}  , httpSession: {@1} , servletContext: {@2} , sessionScope: {@3} , isLoginFromCookie: {@retrun}")
	final public boolean isLoginFromCookie(final String sessionId, final HttpSession httpSession,
			final ServletContext servletContext, ResourceBundle sessionScope) {
		boolean result = false;
		QueryManager qm = new QueryManager();
		String query = "";
		if (!httpSession.isNew())
			return httpSession.isNew();
		try {
			if (sessionId == null || sessionId.length() == 0)
				return false;

			// StandardSession ss = (StandardSession)session ;

			String key = "";
			// FIX: same cookie value, same injection; bound as a parameter.
			query = "select USER_ID , TYPE , CLASSBODY  from store_session WHERE  USER_ID IN (SELECT user_id  FROM tuser where idsession = ? ) ";
			ResultSet rs = qm.executeQueryResultSet(query, new Object[] { sessionId });
			while (rs.next()) {
				result = true;
				Enumeration enumeration = sessionScope.getKeys();
				while (enumeration.hasMoreElements()) {
					key = (String) enumeration.nextElement();
					String type = sessionScope.getString(key).trim();
					String typedb = rs.getString("TYPE");
					if (typedb != null)
						if (typedb.equals(type)) {
							Object obj = rs.getObject("CLASSBODY");
							Object newobj = createObject(type);
							BeanUtils.copyProperties(newobj, obj);
							httpSession.setAttribute(key, newobj);
							obj = null;
							// System.out.println("load key: " + key + " object: " +
							// obj.getClass().getName() );
						}
				}
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			result = false;
		} catch (Exception ex) {
			log.error(ex);
			result = false;
		} finally {
			if (qm != null)
				qm.close();
		}

		return result;
	}

	@Profiled(logger = CLASS_NAME, tag = "getCookiesDir", message = "servletContext: {$0}  , getCookiesDir: {@retrun}  ")
	final String getCookiesDir(final ServletContext servletContext) {
		return (String) servletContext.getAttribute("cookies_dir");
	}

	/**
	 * Variant of {@link #isLoginFromCookie} that reads the serialised beans from the cookies directory on disk instead of the database.
	 *
	 * @return true if beans were restored
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginFromCookieFromDir", message = "servletContext: {$0}  , getCookiesDir: {@retrun}  ")
	final public boolean isLoginFromCookieFromDir(final String sessionId, final HttpSession httpSession,
			final ServletContext servletContext, final ResourceBundle sessionScope) {
		boolean result = false;

		String pathDir = getCookiesDir(servletContext);
		if (!httpSession.isNew())
			return httpSession.isNew();
		try {
			if (sessionId == null || sessionId.length() == 0)
				return false;
			String fileCookies = pathDir + File.separatorChar + sessionId;
			File file = new File(fileCookies);
			if (file.exists()) {
				Map map = deserializeObject(file);
				Set keyList = map.keySet();
				Iterator iterator = keyList.iterator();
				String type = "";
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object obj = map.get(key);
					type = sessionScope.getString(key).trim();
					Object newobj = createObject(type);
					if (newobj == null || obj == null)
						continue;
					BeanUtils.copyProperties(newobj, obj);
					httpSession.setAttribute(key, obj);
					System.out.println("key " + key);
					System.out.println("object_new " + newobj);
					System.out.println("object " + obj);
				}

			}

		} catch (Exception ex) {
			log.error(ex);
			result = false;
		} finally {

		}

		return result;
	}

	/**
	 * Newer variant of {@link #isLoginFromCookie}; restores stored beans for the given session id.
	 *
	 * @return true if beans were restored
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginFromCookieNew", message = "servletContext: {$0}  , getCookiesDir: {@retrun}  ")
	final public boolean isLoginFromCookieNew(final String sessionId, final HttpSession httpSession,
			final ResourceBundle sessionScope) {
		boolean result = false;
		QueryManager qm = new QueryManager();
		String query = "";
		try {
			if (sessionId == null || sessionId.length() == 0)
				return false;

			String key = "";
			query = "select USER_ID , TYPE , CLASSBODY  from store_session WHERE  idsession_hash1 = ?"
					+ "  and idsession_hash2 = ? and idsession_hash3 = ? and idsession_hash4 = ?";
			ResultSet rs = qm.executeQueryResultSet(query, new Object[] {
					Long.valueOf(getIdsessionHash1(sessionId)),
					Long.valueOf(getIdsessionHash2(sessionId)),
					Long.valueOf(getIdsessionHash3(sessionId)),
					Long.valueOf(getIdsessionHash4(sessionId)) });
			while (rs.next()) {
				result = true;
				Enumeration enumeration = sessionScope.getKeys();
				while (enumeration.hasMoreElements()) {
					key = (String) enumeration.nextElement();
					String type = sessionScope.getString(key).trim();
					String typedb = rs.getString("TYPE");
					if (typedb != null)
						if (typedb.equals(type)) {
							Object obj = rs.getObject("CLASSBODY");
							Object newobj = createObject(type);
							BeanUtils.copyProperties(newobj, obj);
							httpSession.setAttribute(key, newobj);
							obj = null;
							// System.out.println("load key: " + key + " object: " +
							// obj.getClass().getName() );
						}
				}
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			result = false;
		} catch (Exception ex) {
			log.error(ex);
			result = false;
		} finally {
			if (qm != null)
				qm.close();
		}

		return result;
	}

	/**
	 * Alternate variant of {@link #isLoginFromCookieNew}; kept for compatibility.
	 *
	 * @return true if beans were restored
	 */
	@Profiled(logger = CLASS_NAME, tag = "isLoginFromCookieNew1", message = "sessionId: {$0}  , httpSession: {@1} , sessionScope: {@2} , isLoginFromCookieNew1: {@retrun} ")
	final public boolean isLoginFromCookieNew1(final String sessionId, final HttpSession httpSession,
			final ResourceBundle sessionScope) {
		boolean result = false;
		QueryManager qm = new QueryManager();
		String query = "";
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			if (sessionId == null || sessionId.length() == 0)
				return false;

			String key = "";
			query = "select USER_ID , TYPE , BCLASSBODY  from store_session WHERE  idsession_hash1 = ?"
					+ "  and idsession_hash2 = ? and idsession_hash3 = ? and idsession_hash4 = ?";
			ResultSet rs = qm.executeQueryResultSet(query, new Object[] {
					Long.valueOf(getIdsessionHash1(sessionId)),
					Long.valueOf(getIdsessionHash2(sessionId)),
					Long.valueOf(getIdsessionHash3(sessionId)),
					Long.valueOf(getIdsessionHash4(sessionId)) });
			while (rs.next()) {
				result = true;
				Enumeration enumeration = sessionScope.getKeys();
				while (enumeration.hasMoreElements()) {
					key = (String) enumeration.nextElement();
					String type = sessionScope.getString(key).trim();
					String typedb = rs.getString("TYPE");
					if (typedb != null)
						if (typedb.equals(type)) {
							bais = new ByteArrayInputStream(rs.getBytes("BCLASSBODY"));
							ois = new ObjectInputStream(bais);
							Object obj = ois.readObject();
							httpSession.setAttribute(key, obj);
						}
				}
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			result = false;
		} catch (Exception ex) {
			log.error(ex);
			result = false;
		} finally {
			try {
				if (bais != null)
					bais.close();
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (qm != null)
				qm.close();
		}

		return result;
	}

	/**
	 * Restores the stored session-scope beans of a user by user id (used when switching sites).
	 *
	 * @param userId user id whose beans are loaded
	 * @return true if any bean was restored
	 */
	@Profiled(logger = CLASS_NAME, tag = "loadOldSessionbyLogin", message = "userId: {$0}  , httpSession: {@1} , sessionScope: {@2} , loadOldSessionbyLogin: {@retrun} ")
	final public boolean loadOldSessionbyLogin(final String userId, final HttpSession httpSession,
			final ResourceBundle sessionScope) {
		boolean result = false;
		QueryManager qm = new QueryManager();
		String query = "";
		try {
			if (userId == null || userId.length() == 0)
				return false;
			String key = "";
			query = "select USER_ID , TYPE , CLASSBODY  from store_session WHERE  USER_ID = ?";
			ResultSet rs = qm.executeQueryResultSet(query, new Object[] { userId });
			while (rs.next()) {
				result = true;
				Enumeration enumeration = sessionScope.getKeys();
				while (enumeration.hasMoreElements()) {
					key = (String) enumeration.nextElement();
					String type = sessionScope.getString(key).trim();
					String typedb = rs.getString("TYPE");
					if (typedb != null)
						if (typedb.equals(type)) {
							Object obj = rs.getObject("CLASSBODY");
							Object newobj = createObject(type);
							BeanUtils.copyProperties(newobj, obj);
							httpSession.setAttribute(key, newobj);
							obj = null;
							// System.out.println("load key: " + key + " object: " +
							// obj.getClass().getName() );
						}
				}
			}

		} catch (SQLException ex) {
			log.error(query, ex);
			result = false;
		} catch (Exception ex) {
			log.error(ex);
			result = false;
		} finally {
			if (qm != null)
				qm.close();
		}

		return result;
	}

	/**
	 * Instantiates a class by name with its no-arg constructor.
	 *
	 * @param className fully qualified class name
	 * @return the new instance, or null on failure (logged)
	 */
	@Profiled(logger = CLASS_NAME, tag = "createObject", message = "userId: {$0}  , createObject: {@retrun} ")
	final public Object createObject(final String className) {
		Object obj = null;
		try {
			Class cls = Class.forName(className);
			obj = cls.newInstance();
		} catch (Exception ex) {
			log.error(ex);
		}
		return obj;
	}

	/**
	 * Reads a serialised {@code Map} from a file.
	 *
	 * @param fileName file to read
	 * @return the map, or null on failure (logged)
	 */
	@Profiled(logger = CLASS_NAME, tag = "deserializeObject", message = "fileName: {$0}  , deserializeObject: {@retrun} ")
	final public Map deserializeObject(final File fileName) {

		Map map = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(fileName));
			map = (Map) in.readObject();

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return map;
	}

	/**
	 * Loads a user's profile into a fresh bean by user id.
	 *
	 * @param userId user id; null or empty returns null
	 * @return a populated bean, or an empty bean when the user is not found
	 */
	@Profiled(logger = CLASS_NAME, tag = "getAuthorizationBean", message = "userId: {$0}  , getAuthorizationBean: {@retrun} ")
	final public AuthorizationPageBean getAuthorizationBean(final String userId) {
		QueryManager qm = null;
		AuthorizationPageBean authorization = new AuthorizationPageBean();
		String query = "";
		try {
			if (userId == null || userId.length() == 0)
				return null;
			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  "
					+ "FROM tuser  where  user_id = ?";
			qm.executeQueryWithArgs(query, new Object[] { Long.valueOf(userId) });
			if (qm.rows().size() == 1) {
				authorization.setIntUserID(Integer.parseInt((String) qm.getValueAt(0, 0)));
				authorization.setStrLogin((String) qm.getValueAt(0, 1));
				authorization.setStrPasswd((String) qm.getValueAt(0, 2));
				authorization.setStrFirstName((String) qm.getValueAt(0, 3));
				authorization.setStrLastName((String) qm.getValueAt(0, 4));
				authorization.setStrEMail((String) qm.getValueAt(0, 5));
				authorization.setStrPhone((String) qm.getValueAt(0, 6));
				authorization.setStrMPhone((String) qm.getValueAt(0, 7));
				authorization.setStrFax((String) qm.getValueAt(0, 8));
				authorization.setStrIcq((String) qm.getValueAt(0, 9));
				authorization.setStrWebsite((String) qm.getValueAt(0, 10));
				authorization.setRoleId(Integer.parseInt((String) qm.getValueAt(0, 13)));
				authorization.setStrCompany((String) qm.getValueAt(0, 15));
				authorization.setCountryId((String) qm.getValueAt(0, 16));
				authorization.setCityId((String) qm.getValueAt(0, 17));
				authorization.setCurrencyId((String) qm.getValueAt(0, 18));
				return authorization;
			}
			return authorization;
		} catch (SQLException ex) {
			log.error(query, ex);
			return authorization;
		} catch (Exception ex) {
			log.error(ex);
			return authorization;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Loads the administrator account of a site.
	 *
	 * @param siteId site id
	 * @return the administrator's bean, or an empty bean if none
	 */
	@Profiled(logger = CLASS_NAME, tag = "getAuthorizationBeanOfRoleAdmin", message = "siteId: {$0}  , getAuthorizationBeanOfRoleAdmin: {@retrun} ")
	final public AuthorizationPageBean getAuthorizationBeanOfRoleAdmin(final String siteId) {
		QueryManager qm = null;
		AuthorizationPageBean authorization = new AuthorizationPageBean();
		String query = "";
		try {
			if (siteId == null || siteId.length() == 0)
				return null;
			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  "
					+ "FROM tuser  where  levelup_cd = 2 and site_id = ?";
			qm.executeQueryWithArgs(query, new Object[] { Long.valueOf(siteId) });
			if (qm.rows().size() == 1) {
				authorization.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));
				authorization.setStrLogin((String) qm.getValueAt(0, 1));
				authorization.setStrPasswd((String) qm.getValueAt(0, 2));
				authorization.setStrFirstName((String) qm.getValueAt(0, 3));
				authorization.setStrLastName((String) qm.getValueAt(0, 4));
				authorization.setStrEMail((String) qm.getValueAt(0, 5));
				authorization.setStrPhone((String) qm.getValueAt(0, 6));
				authorization.setStrMPhone((String) qm.getValueAt(0, 7));
				authorization.setStrFax((String) qm.getValueAt(0, 8));
				authorization.setStrIcq((String) qm.getValueAt(0, 9));
				authorization.setStrWebsite((String) qm.getValueAt(0, 10));
				authorization.setRoleId(Integer.parseInt((String) qm.getValueAt(0, 13)));
				authorization.setStrCompany((String) qm.getValueAt(0, 15));
				authorization.setCountryId((String) qm.getValueAt(0, 16));
				authorization.setCityId((String) qm.getValueAt(0, 17));
				authorization.setCurrencyId((String) qm.getValueAt(0, 18));
				return authorization;
			}
			return authorization;
		} catch (SQLException ex) {
			log.error(query, ex);
			return authorization;
		} catch (Exception ex) {
			log.error(ex);
			return authorization;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Loads a user's profile from the main site by login.
	 *
	 * @param login user login
	 * @return the bean, or an empty bean if not found
	 */
	@Profiled(logger = CLASS_NAME, tag = "getFromMainSiteUserAuthorizationBean", message = "login: {$0}  , getFromMainSiteUserAuthorizationBean: {@retrun} ")
	final public AuthorizationPageBean getFromMainSiteUserAuthorizationBean(final String login) {
		QueryManager qm = null;
		AuthorizationPageBean authorization = new AuthorizationPageBean();
		String query = "";
		try {
			if (login == null || login.length() == 0)
				return null;

			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  "
					+ "FROM tuser  where  user_id = ? and  site_id = " + SiteType.MAIN_SITE;
			qm.executeQueryWithArgs(query, new Object[] { login });
			if (qm.rows().size() == 1) {
				authorization.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));
				authorization.setStrLogin((String) qm.getValueAt(0, 1));
				authorization.setStrPasswd((String) qm.getValueAt(0, 2));
				authorization.setStrFirstName((String) qm.getValueAt(0, 3));
				authorization.setStrLastName((String) qm.getValueAt(0, 4));
				authorization.setStrEMail((String) qm.getValueAt(0, 5));
				authorization.setStrPhone((String) qm.getValueAt(0, 6));
				authorization.setStrMPhone((String) qm.getValueAt(0, 7));
				authorization.setStrFax((String) qm.getValueAt(0, 8));
				authorization.setStrIcq((String) qm.getValueAt(0, 9));
				authorization.setStrWebsite((String) qm.getValueAt(0, 10));
				authorization.setRoleId(Integer.parseInt((String) qm.getValueAt(0, 13)));
				authorization.setStrCompany((String) qm.getValueAt(0, 15));
				authorization.setCountryId((String) qm.getValueAt(0, 16));
				authorization.setCityId((String) qm.getValueAt(0, 17));
				authorization.setCurrencyId((String) qm.getValueAt(0, 18));
				return authorization;
			}
			return authorization;
		} catch (SQLException ex) {
			log.error(query, ex);
			return authorization;
		} catch (Exception ex) {
			log.error(ex);
			return authorization;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Resolves a user id on the main site.
	 *
	 * @param login login (matched against user_id in the current query)
	 * @return the user id, or 0 if not found
	 */
	@Profiled(logger = CLASS_NAME, tag = "getFromMainSiteUserId", message = "login: {$0}  , getFromMainSiteUserId: {@retrun} ")
	final public int getFromMainSiteUserId(final String login) {
		QueryManager qm = null;
		String query = "";
		try {
			if (login == null || login.length() == 0)
				return 0;
			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,levelup_cd ,bank_cd , company , country_id , city_id ,  currency_id  "
					+ "FROM tuser  where  user_id = ? and  site_id = " + SiteType.MAIN_SITE;
			qm.executeQueryWithArgs(query, new Object[] { login });
			if (qm.rows().size() == 1) {
				// authorization.setIntUserID( Integer.parseInt((String) Adp.getValueAt(0, 0)));
				return Integer.parseInt((String) qm.getValueAt(0, 0));
			}
			return 0;
		} catch (SQLException ex) {
			log.error(query, ex);
			return 0;
		} catch (Exception ex) {
			log.error(ex);
			return 0;
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * Validates a registration form and, when valid, creates the account. Returns 0 on success or a numeric code for the first failing field (1 login, 2 password, 12 password shorter than 6, 10 confirmation, 3 first name, 4 last name, ...).
	 *
	 * @return 0 on success, otherwise a validation code
	 */
	@Profiled(logger = CLASS_NAME, tag = "isRegCorrect", message = "login: {$0} , firstName: {$3} , lastName: {$4} , companyName: {$5}, email: {$6} ,  phone: {$7}, mobilePhone: {$8} , faxNumber: {$9} , messageId: {$10} , websiteHost: {$11} , secureQuestion: {$12} ,  secureAnswer , {$13} birthday: {$14} , countryId: {$15} , cityId: {$16} , currencyId: {$17} , cookieSessionId: {$18} ,  authorizationBean {$19}  , isRegCorrect: {@retrun} ")
	final public int isRegCorrect(final String login, final String passwd, final String cPasswd, final String firstName,
			final String lastName, final String companyName, final String email, final String phone,
			final String mobilePhone, final String faxNumber, final String messageId, final String websiteHost,
			final String secureQuestion, final String secureAnswer, final String birthday, final String countryId,
			final String cityId, final String currencyId, final String cookieSessionId,
			final AuthorizationPageBean authorizationBean) {

		QueryManager qm = null;
		String query = "";
		try {
			if (login == null || login.length() == 0)
				return 1;
			if (passwd == null || passwd.length() == 0)
				return 2;
			if (passwd.length() < 6)
				return 12;

			if (cPasswd == null || passwd.length() == 0)
				return 10;
			if (firstName == null || firstName.length() == 0)
				return 3;
			if (lastName == null || lastName.length() == 0)
				return 4;
			if (email == null || email.length() == 0)
				return 5;
			if (cityId == null || cityId.length() == 0)
				return 11;
			if (email.indexOf("@") == -1)
				return 8;
			if (passwd.compareTo(cPasswd) != 0)
				return 9;
			if (!isEnglish(login))
				return 13;

			qm = new QueryManager();
			qm.beginTransaction();
			// FIX: registration form values bound as parameters instead of
			// concatenated (passwd is not restricted by isEnglish()).
			// Look up an existing account by login only; the password is stored
			// hashed and cannot be matched in SQL.
			query = "SELECT user_id  FROM tuser where  login = ?";
			qm.executeQueryWithArgs(query, new Object[] { login });
			if (qm.rows().size() != 0) {
				authorizationBean.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));
				// FIX (SQL injection): fifteen registration-form fields were spliced
				// into this UPDATE by concatenation. The INSERT a few lines below
				// was already parameterised by the author; this branch was not.
				// Bound directly on a PreparedStatement over the same transaction.
				query = "update tuser set login = ?, passwd = ?, first_name = ?, last_name = ?, e_mail = ?, phone = ?,"
						+ " mobil_phone = ?, fax = ?, icq = ?, website = ?, question = ?, answer = ?, company = ?,"
						+ " country_id = ?, city_id = ?, currency_id = ? where user_id = ?";
				try (java.sql.PreparedStatement ps = qm.getCurrentConnection().prepareStatement(query)) {
					ps.setString(1, login);
					ps.setString(2, com.cbsinc.cms.utils.PasswordHash.hash(passwd));
					ps.setString(3, firstName);
					ps.setString(4, lastName);
					ps.setString(5, email);
					ps.setString(6, phone);
					ps.setString(7, mobilePhone);
					ps.setString(8, faxNumber);
					ps.setString(9, messageId);
					ps.setString(10, websiteHost);
					ps.setString(11, secureQuestion);
					ps.setString(12, secureAnswer);
					ps.setString(13, companyName);
					ps.setLong(14, Long.parseLong(countryId));
					ps.setLong(15, Long.parseLong(cityId));
					ps.setLong(16, Long.parseLong(currencyId));
					ps.setLong(17, authorizationBean.getIntUserID());
					ps.executeUpdate();
				}
				authorizationBean.setIntLogined(1);
				qm.commit();
				return 0;
			}

			/*
			 * else { intLogined = 2 ; intUserID = 0 ; strLogin = "" ; Adp.commit();
			 * Adp.close(); return -1 ; }
			 */

			query = "SELECT user_id  FROM tuser  where login  = ?";
			qm.executeQueryWithArgs(query, new Object[] { login });
			if (qm.rows().size() != 0) {
				authorizationBean.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));
				authorizationBean.setIntLogined(3);
				authorizationBean.setIntUserID(0);
				authorizationBean.setStrLogin("");
				qm.commit();
				return -1;
			}

			// query = "SELECT NEXT VALUE FOR tuser_user_id_seq AS ID FROM ONE_SEQUENCES";
			query = sequencesRs.getString("tuser");
			qm.executeQuery(query);
			authorizationBean.setIntUserID(Long.parseLong((String) qm.getValueAt(0, 0)));

			query = "insert into tuser ( user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone,fax,icq,website,question,answer,acvive_session ,active ,regdate ,levelup_cd ,bank_cd , company , country_id , city_id , currency_id , site_id , idsession ) "
					+ " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			Map args = qm.getArgs();
			args.put("user_id", authorizationBean.getIntUserID());
			args.put("login", login);
			args.put("passwd", com.cbsinc.cms.utils.PasswordHash.hash(passwd));
			args.put("first_name", firstName);
			args.put("last_name", lastName);
			args.put("e_mail", email);
			args.put("phone", phone);
			args.put("mobil_phone", mobilePhone);
			args.put("fax", faxNumber);
			args.put("icq", messageId);
			args.put("website", websiteHost);
			args.put("question", secureQuestion);
			args.put("answer", secureAnswer);
			args.put("acvive_session", true);
			args.put("active", true);
			args.put("regdate", new java.util.Date());
			args.put("levelup_cd", 1);
			args.put("bank_cd", 0);
			args.put("company", companyName);
			args.put("country_id", Long.parseLong(countryId));
			args.put("city_id", Long.parseLong(cityId));
			args.put("currency_id", Long.parseLong(currencyId));
			args.put("site_id", Long.parseLong(authorizationBean.getSiteId()));
			args.put("idsession", cookieSessionId);
			qm.executeInsertWithArgs(query, args);

			// + " idsession = '" + cookie_session_id + "' , "

			// query = "SELECT NEXT VALUE FOR account_id_seq AS ID FROM ONE_SEQUENCES";
			query = sequencesRs.getString("account");
			qm.executeQuery(query);
			String accountId = (String) qm.getValueAt(0, 0);

			query = "insert into account ( account_id , user_id , amount , curr , date_input ,  description ,  currency_id ) "
					+ " values (  ? , ? , ? , ? , ? ,  ? ,  ?  ) ";

			args = qm.getArgs();
			args.put("account_id", Long.parseLong(accountId));
			args.put("user_id", authorizationBean.getIntUserID());
			args.put("amount", 0);
			args.put("curr", 3);
			args.put("date_input", new java.util.Date());
			args.put("description", " new_account ");
			args.put("currency_id", Long.parseLong(currencyId));

			qm.executeInsertWithArgs(query, args);

			authorizationBean.setIntLogined(1);
			authorizationBean.setRoleId(1);
			qm.commit();
			return 0;

		} catch (SQLException ex) {
			log.error(query, ex);
			qm.rollback();
			return -2;
		} catch (Exception ex) {
			log.error(ex);
			qm.rollback();
			return -2;
		}

		finally {
			if (qm != null)
				qm.close();
		}

	}

	/**
	 * Builds the HTML markup of a day/month/year date control.
	 *
	 * @return the control markup
	 */
	@Profiled(logger = CLASS_NAME, tag = "getDateControl", message = "name1: {$0}  , name2: {$1}  , name3: {$2} , query: {$3}, at: {$4}, to: {$5}, getDateControl: {$retrun} ")
	final public String getDateControl(final String name1, final String name2, final String name3, String query, int at,
			int to) {
		return super.getDateControl(name1, name2, name3, query, at, to);
	}

	/**
	 * Stores the site's file directory on the bean.
	 *
	 * @param site_id site id
	 * @param authorizationBean bean to initialise
	 */
	@Profiled(logger = CLASS_NAME, tag = "initSiteDir", message = "site_id: {$0}  , authorizationBean: {$1} ")
	final public void initSiteDir(final String siteId, final AuthorizationPageBean authorizationBean) {
		String query = "select site_dir, subject_site , nick_site , company_name , host from site where site_id = "
				+ siteId;
		QueryManager qm = new QueryManager();
		// Adp.BeginTransaction();
		try {
			qm.executeQuery(query);
			if (qm.rows().size() != 0) {
				authorizationBean.setSiteDir((String) qm.getValueAt(0, 0)); // + " " +
				authorizationBean.setSubjectSite((String) qm.getValueAt(0, 1));
				authorizationBean.setNickSite((String) qm.getValueAt(0, 2));
				authorizationBean.setCompanyName((String) qm.getValueAt(0, 3));
				authorizationBean.setHost((String) qm.getValueAt(0, 4));
			}
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		}

		finally {
			qm.close();
		}

	}

	/**
	 * Loads the site owned by the user onto the bean.
	 *
	 * @param userId user id
	 * @param authorizationBean bean to initialise
	 */
	@Profiled(logger = CLASS_NAME, tag = "initSiteDir", message = "site_id: {$0}  , authorizationBean: {$1} ")
	final public void initUserSite(final long userId, final AuthorizationPageBean authorizationBean) {
		String query = "select site_id  from site where site.owner = ? order by site.site_id desc ";

		QueryManager qm = new QueryManager();

		try {
			qm.executeQueryWithArgs(query, new Object[] { Long.valueOf(userId) });
			if (qm.rows().size() != 0) {
				authorizationBean.setUserSite((String) qm.getValueAt(0, 0)); // + " " +
			} else
				authorizationBean.setUserSite("-1");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		}

		finally {
			qm.close();
		}

	}

	/**
	 * Maps a locale string to the language id used in the database.
	 *
	 * @param locale locale code such as "en" or "ru"
	 * @return the language id
	 */
	@Profiled(logger = CLASS_NAME, tag = "getLengId", message = "locale: {$0}  , getLengId: {$retrun} ")
	final public int getLengId(final String locale) {
		String langId = LanguageEnum.EN.getStrId();
		;
		// FIX: locale was concatenated as a quoted literal; bound as a parameter.
		String query = "select lang_id  from lang where lable = ?";
		QueryManager qm = new QueryManager();

		try {
			qm.executeQueryWithArgs(query, new Object[] { locale });
			if (qm.rows().size() != 0) {
				langId = (String) qm.getValueAt(0, 0); // + " " +
			}

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		}

		finally {
			qm.close();
		}

		return Integer.parseInt(langId);
	}

	/**
	 * Loads the payment-system shop code of a site onto the bean.
	 *
	 * @param siteId site id
	 * @param authorizationBean bean to initialise
	 */
	@Profiled(logger = CLASS_NAME, tag = "initPaySysShopCd", message = "siteId: {$0}  , authorizationBean: {$1}  , initPaySysShopCd: {$retrun} ")
	final public void initPaySysShopCd(final String siteId, final AuthorizationPageBean authorizationBean) {
		String query = "select  shop_cd from shop where site_id = ?";
		QueryManager qm = new QueryManager();
		qm.beginTransaction();
		try {
			qm.executeQueryWithArgs(query, new Object[] { Long.valueOf(siteId) });

			if (qm.rows().size() != 0) {
				authorizationBean.setPaysysShopCd((String) qm.getValueAt(0, 0));
				if (authorizationBean.getPaysysShopCd() == null)
					log.assertLog(false,
							"ERROR: select shop_cd  from shop where site_id = " + siteId + " \n   shop_cd = null ");
			} else
				log.assertLog(false,
						"ERROR: select shop_cd  from shop where site_id = " + siteId + " \n   shop_cd = null ");

		} catch (SQLException ex) {
			log.error(ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			qm.close();
		}

	}

	/**
	 * Renders the list of users of the current site for the template.
	 *
	 * @return the rendered list
	 */
	@Profiled(logger = CLASS_NAME, tag = "getUserList", message = "authorizationBean: {$0}  , getUserList: {$retrun} ")
	final public String getUserList(final AuthorizationPageBean authorizationBean) {
		StringBuffer table = new StringBuffer();
		QueryManager qm = null;
		String query = "";

		if (authorizationBean.getRoleId() != 2)
			return "";
		try {
			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone , regdate FROM tuser  where	site_id = "
					+ authorizationBean.getSiteId() + "";

			qm.executeQuery(query);
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");

			table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"10%\" >Login  </TD>"
					+ "<TD WIDTH=\"10%\" >Password</TD>" + "<TD WIDTH=\"10%\" >First Name</TD>"
					+ "<TD WIDTH=\"10%\" >Last Name</TD>" + "<TD WIDTH=\"20%\" >E-Mail</TD>"
					+ "<TD WIDTH=\"10%\" >Phone</TD>" + "<TD WIDTH=\"10%\" >Mobl-Phone</TD>"
					+ "<TD WIDTH=\"10%\" >Date</TD>" + "</TR>\n");

			for (int i = 0; qm.rows().size() > i; i++) {
				table.append("<tr>");
				table.append("<td>");
				table.append(qm.getValueAt(i, 0));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 1));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 2));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 3));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 4));
				table.append("</td>");

				table.append("<td>");
				table.append("<a href='mailto:" + qm.getValueAt(i, 5) + " '  >" + qm.getValueAt(i, 5) + "</a>");
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 6));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 7));
				table.append("</td>");

				table.append("<td>");
				table.append(((String) qm.getValueAt(i, 8)).substring(0, 16));
				table.append("</td>");

				table.append("</tr>");
			}
			table.append("</tbody>\n");
			table.append("</table>");
		} catch (SQLException ex) {
			log.error(query, ex);

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (qm != null)
				qm.close();
		}

		return table.toString();
	}

	/**
	 * Renders the list of all users visible to the caller.
	 *
	 * @return the rendered list
	 */
	@Profiled(logger = CLASS_NAME, tag = "getUserListAll", message = "authorizationBean: {$0}  , getUserListAll: {$retrun} ")
	final public String getUserListAll(final AuthorizationPageBean authorizationBean) {
		StringBuffer table = new StringBuffer();
		QueryManager qm = null;
		String query = "";

		if (authorizationBean.getRoleId() != 2)
			return "";
		try {
			qm = new QueryManager();
			query = "SELECT user_id , login , passwd , first_name , last_name , e_mail , phone , mobil_phone , regdate , site_id FROM tuser ";

			qm.executeQuery(query);
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");

			table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"10%\" >Login  </TD>"
					+ "<TD WIDTH=\"10%\" >Password</TD>" + "<TD WIDTH=\"10%\" >First Name</TD>"
					+ "<TD WIDTH=\"10%\" >Last Name</TD>" + "<TD WIDTH=\"20%\" >E-Mail</TD>"
					+ "<TD WIDTH=\"10%\" >Phone</TD>" + "<TD WIDTH=\"10%\" >Mobl-Phone</TD>"
					+ "<TD WIDTH=\"10%\" >Date</TD>" + "<TD WIDTH=\"10%\" >Site ID</TD>" + "</TR>\n");

			for (int i = 0; qm.rows().size() > i; i++) {
				table.append("<tr>");
				table.append("<td>");
				table.append(qm.getValueAt(i, 0));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 1));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 2));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 3));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 4));
				table.append("</td>");

				table.append("<td>");
				table.append("<a href='mailto:" + qm.getValueAt(i, 5) + " '  >" + qm.getValueAt(i, 5) + "</a>");
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 6));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 7));
				table.append("</td>");

				table.append("<td>");
				table.append(((String) qm.getValueAt(i, 8)).substring(0, 16));
				table.append("</td>");

				table.append("<td>");
				table.append(qm.getValueAt(i, 9));
				table.append("</td>");

				table.append("</tr>");
			}
			table.append("</tbody>\n");
			table.append("</table>");
		} catch (SQLException ex) {
			log.error(query, ex);

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (qm != null)
				qm.close();
		}

		return table.toString();
	}

	/**
	 * Checks that a word consists only of ASCII letters, digits, underscore and dash (suitable for logins and host labels).
	 *
	 * @return true if every character is allowed
	 */
	@Profiled(logger = CLASS_NAME, tag = "isEnglish", message = "word: {$0}  , isEnglish: {$retrun} ")
	final public boolean isEnglish(String word) {
		if (word == null)
			return false;
		word = word.toLowerCase();
		String IntField = "0123456789qwertyuiopasdfghjklzxcvbnm_-";
		for (int i = 0; i < word.length(); i++) {

			if (IntField.indexOf(word.charAt(i)) == -1) {
				if (word.charAt(i) != '-' && i != 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * Registers a new host name for a site and updates the server aliases.
	 *
	 * @param host host name to add
	 * @param siteId owning site
	 */
	@Profiled(logger = CLASS_NAME, tag = "saveNewDomain", message = "host: {$0} ,  siteId: {$0}  , saveNewDomain: {$retrun} ")
	public final void saveNewDomain(final String host, final String siteId) {
		QueryManager qm = new QueryManager();
		String query = "";
		qm.beginTransaction();
		try {
			// FIX: HOST was bound but SITE_ID was still concatenated.
			query = "UPDATE site SET HOST = ?  where SITE_ID = ?";
			qm.executeUpdateWithArgs(query, new Object[] { host, Long.valueOf(siteId) });
			qm.commit();
		} catch (Exception e) {
			qm.rollback();
			log.error(e);
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	@Profiled(logger = CLASS_NAME, tag = "saveClassesSessionScope", message = "httpSession: {$0} , saveClassesSessionScope: {$retrun} ")
	final void saveClassesSessionScope(final HttpSession httpSession) {
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) httpSession
				.getAttribute("authorizationPageBeanId");
		if (AuthorizationPageBeanId == null)
			return;
		// if( AuthorizationPageBeanId.getStrLogin().equals(SiteRole.GUEST)) return ;
		// if( AuthorizationPageBeanId.getRoleId() == 0 ) return ;
		QueryManager qm = new QueryManager();
		String query = "";
		String key = "";
		qm.beginTransaction();
		Enumeration enumeration;
		String type = null;
		Object obj = null;

		try {
			enumeration = sessionScope.getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				type = sessionScope.getString(key).trim();
				obj = httpSession.getAttribute(key);
				if (obj == null)
					continue;

				query = "select USER_ID from store_session WHERE USER_ID = ? AND TYPE = ?";
				qm.executeQueryWithArgs(query,
						new Object[] { AuthorizationPageBeanId.getIntUserID(), type });

				if (qm.rows().size() != 0) {
					query = "update store_session  set  USER_ID = ? , TYPE = ? , CLASSBODY = ? , "
							+ " ACTIVE = ? where USER_ID = ? AND TYPE = ?";
					qm.executeUpdateWithArgs(query, new Object[] {
							AuthorizationPageBeanId.getIntUserID(), type, obj, Boolean.TRUE,
							AuthorizationPageBeanId.getIntUserID(), type });

				} else {

					query = "insert into store_session ( USER_ID ,  TYPE ,  CLASSBODY ,  ACTIVE ) "
							+ " VALUES ( ? ,  ? ,  ? , ? )";
					Map args = qm.getArgs();
					args.put("USER_ID", AuthorizationPageBeanId.getIntUserID());
					args.put("TYPE", type);
					args.put("CLASSBODY", obj);
					args.put("ACTIVE", true);
					qm.executeInsertWithArgs(query, args);

				}

				// session.setAttribute(key,obj) ;
			}
			qm.commit();
		} catch (Exception e) {
			qm.rollback();
			log.error(e);
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	final long getIdsessionHash1(final String sessionId) {
		String hash = sessionId.substring(0, 10);
		return hash.hashCode();
	}

	final long getIdsessionHash2(final String sessionId) {
		String hash = sessionId.substring(10, 20);
		return hash.hashCode();
	}

	final long getIdsessionHash3(final String sessionId) {
		String hash = sessionId.substring(20, 30);
		return hash.hashCode();
	}

	final long getIdsessionHash4(final String sessionId) {
		String hash = sessionId.substring(30);
		if (hash.indexOf(".") != -1)
			hash = hash.substring(0, hash.indexOf("."));
		return hash.hashCode();
	}

	/**
	 * Serialises the session-scope beans of the HttpSession into store_session so the login survives a server restart.
	 *
	 * @param httpSession session whose beans are stored
	 */
	@Profiled(logger = CLASS_NAME, tag = "saveClassesSessionScopeNew", message = "httpSession: {$0} , saveClassesSessionScopeNew: {$retrun} ")
	final public void saveClassesSessionScopeNew(final HttpSession httpSession) {
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) httpSession
				.getAttribute("authorizationPageBeanId");
		if (AuthorizationPageBeanId == null)
			return;
		QueryManager Adp = new QueryManager();
		String query = "";
		String key = "";
		Adp.beginTransaction();
		Enumeration enumeration;
		String type = null;
		Object obj = null;
		String cokieSessionId = (String) httpSession.getAttribute("cokie_session_id");
		httpSession.removeAttribute(cokieSessionId);
		try {
			enumeration = sessionScope.getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				type = sessionScope.getString(key).trim();
				obj = httpSession.getAttribute(key);
				if (obj == null)
					continue;
				query = "select USER_ID  from store_session WHERE  idsession_hash1 = ?"
						+ "  and idsession_hash2 = ? and idsession_hash3 = ? and idsession_hash4 = ?"
						+ " AND TYPE = ?";
				final Object[] __probeArgs = new Object[] {
						Long.valueOf(getIdsessionHash1(cokieSessionId)),
						Long.valueOf(getIdsessionHash2(cokieSessionId)),
						Long.valueOf(getIdsessionHash3(cokieSessionId)),
						Long.valueOf(getIdsessionHash4(cokieSessionId)), type };
				// query = "select USER_ID from store_session WHERE USER_ID = " +
				// AuthorizationPageBeanId.getIntUserID() + " AND TYPE = '" +type+ "'" ;
				Adp.executeQueryWithArgs(query, __probeArgs);

				if (Adp.rows().size() > 0) {
					query = "update store_session  set  USER_ID = ? , TYPE = ? , CLASSBODY = ? , "
							+ " ACTIVE = ? WHERE  idsession_hash1 = ?  and idsession_hash2 = ?"
							+ " and idsession_hash3 = ? and idsession_hash4 = ? AND TYPE = ?";
					Adp.executeUpdateWithArgs(query, new Object[] {
							AuthorizationPageBeanId.getIntUserID(), type, obj, Boolean.TRUE,
							Long.valueOf(getIdsessionHash1(cokieSessionId)),
							Long.valueOf(getIdsessionHash2(cokieSessionId)),
							Long.valueOf(getIdsessionHash3(cokieSessionId)),
							Long.valueOf(getIdsessionHash4(cokieSessionId)), type });

				} else {

					query = "insert into store_session ( USER_ID ,  TYPE ,  CLASSBODY ,  ACTIVE , idsession_hash1 , idsession_hash2 , idsession_hash3 , idsession_hash4 ) "
							+ " VALUES ( ? ,  ? ,  ? , ? , ? ,  ? ,  ? , ?)";
					Map args = Adp.getArgs();
					args.put("USER_ID", AuthorizationPageBeanId.getIntUserID());
					args.put("TYPE", type);
					args.put("CLASSBODY", obj);
					args.put("ACTIVE", true);
					args.put("idsession_hash1", getIdsessionHash1(cokieSessionId));
					args.put("idsession_hash2", getIdsessionHash2(cokieSessionId));
					args.put("idsession_hash3", getIdsessionHash3(cokieSessionId));
					args.put("idsession_hash4", getIdsessionHash4(cokieSessionId));
					Adp.executeInsertWithArgs(query, args);

				}

				// session.setAttribute(key,obj) ;
			}
			Adp.commit();
		} catch (Exception e) {
			Adp.rollback();
			log.error(e);
		} finally {
			if (Adp != null)
				Adp.close();
		}
	}

	/**
	 * Alternate variant of {@link #saveClassesSessionScopeNew}; kept for compatibility.
	 *
	 * @param httpSession session whose beans are stored
	 */
	@Profiled(logger = CLASS_NAME, tag = "saveClassesSessionScopeNew1", message = "httpSession: {$0} , saveClassesSessionScopeNew1: {$retrun} ")
	final public void saveClassesSessionScopeNew1(final HttpSession httpSession) {
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) httpSession
				.getAttribute("authorizationPageBeanId");
		if (AuthorizationPageBeanId == null)
			return;
		// if( AuthorizationPageBeanId.getStrLogin().equals(SiteRole.GUEST)) return ;
		// if( AuthorizationPageBeanId.getRoleId() == 0 ) return ;
		QueryManager Adp = new QueryManager();
		String query = "";
		String key = "";
		Adp.beginTransaction();
		Enumeration enumeration;
		String type = null;
		Object obj = null;
		String cokieSessionId = (String) httpSession.getAttribute("cokie_session_id");
		httpSession.removeAttribute(cokieSessionId);
		try {
			enumeration = sessionScope.getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				type = sessionScope.getString(key).trim();
				obj = httpSession.getAttribute(key);
				if (obj == null)
					continue;
				query = "select USER_ID  from store_session WHERE  idsession_hash1 = ?"
						+ "  and idsession_hash2 = ? and idsession_hash3 = ? and idsession_hash4 = ?"
						+ " AND TYPE = ?";
				final Object[] __probeArgs = new Object[] {
						Long.valueOf(getIdsessionHash1(cokieSessionId)),
						Long.valueOf(getIdsessionHash2(cokieSessionId)),
						Long.valueOf(getIdsessionHash3(cokieSessionId)),
						Long.valueOf(getIdsessionHash4(cokieSessionId)), type };
				// query = "select USER_ID from store_session WHERE USER_ID = " +
				// AuthorizationPageBeanId.getIntUserID() + " AND TYPE = '" +type+ "'" ;
				Adp.executeQueryWithArgs(query, __probeArgs);

				if (Adp.rows().size() > 0) {
					query = "update store_session  set  USER_ID = ? , TYPE = ? , BCLASSBODY = ? , "
							+ " ACTIVE = ? WHERE  idsession_hash1 = ?  and idsession_hash2 = ?"
							+ " and idsession_hash3 = ? and idsession_hash4 = ? AND TYPE = ?";
					Adp.executeUpdateWithArgs(query, new Object[] {
							AuthorizationPageBeanId.getIntUserID(), type, objectToBytes(obj), Boolean.TRUE,
							Long.valueOf(getIdsessionHash1(cokieSessionId)),
							Long.valueOf(getIdsessionHash2(cokieSessionId)),
							Long.valueOf(getIdsessionHash3(cokieSessionId)),
							Long.valueOf(getIdsessionHash4(cokieSessionId)), type });

				} else {

					query = "insert into store_session ( USER_ID ,  TYPE ,  BCLASSBODY ,  ACTIVE , idsession_hash1 , idsession_hash2 , idsession_hash3 , idsession_hash4 ) "
							+ " VALUES ( ? ,  ? ,  ? , ? , ? ,  ? ,  ? , ?)";
					Map args = Adp.getArgs();
					args.put("USER_ID", AuthorizationPageBeanId.getIntUserID());
					args.put("TYPE", type);
					args.put("BCLASSBODY", objectToBytes(obj));
					args.put("ACTIVE", true);
					args.put("idsession_hash1", getIdsessionHash1(cokieSessionId));
					args.put("idsession_hash2", getIdsessionHash2(cokieSessionId));
					args.put("idsession_hash3", getIdsessionHash3(cokieSessionId));
					args.put("idsession_hash4", getIdsessionHash4(cokieSessionId));
					Adp.executeInsertWithArgs(query, args);

				}

				// session.setAttribute(key,obj) ;
			}
			Adp.commit();
		} catch (Exception e) {
			Adp.rollback();
			log.error(e);
		} finally {
			if (Adp != null)
				Adp.close();
		}
	}

	/**
	 * Serialises an object to a byte array.
	 *
	 * @param obj object to serialise
	 * @return the bytes, or null on failure (logged)
	 */
	@Profiled(logger = CLASS_NAME, tag = "objectToBytes", message = "obj: {$0} , objectToBytes: {$retrun} ")
	final public byte[] objectToBytes(final Object obj) {

		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		byte[] result = null;
		// if(!session.isNew()) return session.isNew() ;
		try {

			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			result = baos.toByteArray();

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			try {
				if (baos != null)
					baos.close();
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}

	/**
	 * Serialises the session-scope beans keyed by the logged-in user id; a no-op when nobody is logged in.
	 *
	 * @param httpSession session whose beans are stored
	 */
	@Profiled(logger = CLASS_NAME, tag = "saveClassesSessionScopeByLogin", message = "httpSession: {$0} , saveClassesSessionScopeByLogin: {$retrun} ")
	final public void saveClassesSessionScopeByLogin(final HttpSession httpSession) {
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) httpSession
				.getAttribute("authorizationPageBeanId");
		if (AuthorizationPageBeanId == null)
			return;
		QueryManager qm = new QueryManager();
		String query = "";
		String key = "";
		qm.beginTransaction();
		Enumeration enumeration;
		String type = null;
		Object obj = null;
		String cokieSessionId = (String) httpSession.getAttribute("cokie_session_id");
		httpSession.removeAttribute(cokieSessionId);
		try {
			enumeration = sessionScope.getKeys();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				type = sessionScope.getString(key).trim();
				obj = httpSession.getAttribute(key);
				if (obj == null)
					continue;
				query = "select USER_ID  from store_session WHERE  USER_ID = ? AND TYPE = ?";
				qm.executeQueryWithArgs(query,
						new Object[] { AuthorizationPageBeanId.getIntUserID(), type });

				if (qm.rows().size() > 0) {
					query = "update store_session  set  USER_ID = ? , TYPE = ? , CLASSBODY = ? , "
							+ " ACTIVE = ? WHERE  USER_ID = ? AND TYPE = ?";
					qm.executeUpdateWithArgs(query, new Object[] {
							AuthorizationPageBeanId.getIntUserID(), type, obj, Boolean.TRUE,
							AuthorizationPageBeanId.getIntUserID(), type });

				} else {

					query = "insert into store_session ( USER_ID ,  TYPE ,  CLASSBODY ,  ACTIVE  )  VALUES ( ? ,  ? ,  ? , ? )";
					Map args = qm.getArgs();
					args.put("USER_ID", AuthorizationPageBeanId.getIntUserID());
					args.put("TYPE", type);
					args.put("CLASSBODY", obj);
					args.put("ACTIVE", true);
					qm.executeInsertWithArgs(query, args);

				}

				// session.setAttribute(key,obj) ;
			}
			qm.commit();
		} catch (Exception e) {
			qm.rollback();
			log.error(e);
		} finally {
			if (qm != null)
				qm.close();
		}
	}

	/**
	 * On context start, synchronises the site host names with the Tomcat server.xml aliases and the DNS server, throttled by DOMAIN_PROC.LAST_DATE.
	 *
	 * @param sce context event
	 */
	@Profiled(logger = CLASS_NAME, tag = "addAliases", message = "sce: {$0} , addAliases: {$retrun} ")
	final public void addAliases(final ServletContextEvent sce) {

		// SELECT LAST_DATE FROM DOMAIN_PROC WHERE DOMAIN_PROC_ID = 0
		GetValueTool tool = new GetValueTool();
		Date curentDate = new Date();
		Date lastPost = new Date();
		QueryManager Adp = null;
		AddAliase addAliase;
		Document doc;
		String query = "select host  from site group by host";
		String countQuery = "select count(host) from site where host in ( select host from site group by host )";
		String host = "www.siteoneclick.com";
		String aliase = "";

		StringBuffer hostsForDns = new StringBuffer();
		File file = new File("/");
		String serverConfig = System.getProperty("conf");
		if (serverConfig == null || serverConfig.length() == 0) {
			String dir = System.getProperty("user.dir");
			dir = dir.substring(0, dir.indexOf("bin")) + "conf" + File.separatorChar + "server.xml";
			// File f = new File("C:/apache-tomcat-6.0.14/conf/server.xml") ;
			file = new File(dir);
		} else {
			file = new File(serverConfig);
		}

		try {

			Adp = new QueryManager();
			if (Adp == null)
				return;
			Adp.executeQuery("SELECT  LAST_DATE  FROM DOMAIN_PROC WHERE DOMAIN_PROC_ID = 0");
			if (Adp.rows().size() > 0) {
				String date = Adp.getValueAt(0, 0);
				lastPost = Adp.getSimpleDateFormat().parse(date);
			}

			addAliase = new AddAliase();
			doc = addAliase.loadConfig(file);
			addAliase.RemoveAllAliase(doc, host);

			long size = 0;
			int limmit = 20;
			Adp.executeQuery(countQuery);
			if (Adp.rows().size() > 0)
				size = Long.parseLong(Adp.getValueAt(0, 0));

			for (int offset = 0; size > offset; offset = offset + 20) {
				List list = Adp.executeQueryList(query, limmit, offset);

				for (int i = 0; list.size() > i; i++) {
					// aliase = Adp.getValueAt(i, 0);
					aliase = tool.getValueAt(list, i, 0);
					int indexof = aliase.indexOf(".irr.bz");
					// if(indexof != -1 && i < 280 && isAnsi(aliase) )
					if (indexof != -1 && isAnsi(aliase)) {
						addAliase.AddAliaseToHost(doc, host, aliase);
						hostsForDns.append(aliase.substring(0, indexof));
						hostsForDns.append(" A 0 78.37.191.193 86400\n");
					}
				}
			}

			addAliase.writeXmlFile(doc, file);

			if (curentDate.getHours() != lastPost.getHours()) {
				DomainRegister r = new DomainRegister();
				String auth = r.getAuth();
				r.setDomainAliases("irr.bz", hostsForDns.toString(), auth);
				System.out.println(r.getPostCommand(auth));
				Adp.getArgs().clear();
				Adp.getArgs().put("LAST_DATE", new Date());
				Adp.executeUpdateWithArgs("UPDATE DOMAIN_PROC SET LAST_DATE  = ? WHERE DOMAIN_PROC_ID = 0",
						Adp.getArgs());
			}

			// System.exit(0);
		} catch (SQLException e) {
			log.error(e);
		} catch (Exception e) {
			System.out.println(" server config: " + file.getPath());
			log.error(e);
		} finally {

			if (Adp != null) {
				Adp.close();
			}

		}

	}

	/**
	 * Pushes the current list of site hosts to the DNS server.
	 */
	@Profiled(logger = CLASS_NAME, tag = "postToDNSServerAliases", message = "postToDNSServerAliases: {$retrun} ")
	final public void postToDNSServerAliases() {

		// SELECT LAST_DATE FROM DOMAIN_PROC WHERE DOMAIN_PROC_ID = 0
		Date curentDate = new Date();
		Date lastPost = new Date();
		QueryManager Adp = null;
		// AddAliase addAliase;
		// Document doc ;
		String query = "select host  from site group by host";
		// String host = "www.siteoneclick.com" ;
		String aliase = "";
		StringBuffer hostsForDns = new StringBuffer();

		try {
			Adp = new QueryManager();
			if (Adp == null)
				return;
			Adp.executeQuery("SELECT  LAST_DATE  FROM DOMAIN_PROC WHERE DOMAIN_PROC_ID = 0");
			if (Adp.rows().size() > 0) {
				String date = Adp.getValueAt(0, 0);
				lastPost = Adp.getSimpleDateFormat().parse(date);
			}

			Adp.executeQuery(query);
			for (int i = 0; Adp.rows().size() > i; i++) {
				aliase = Adp.getValueAt(i, 0);
				int indexof = aliase.indexOf(".irr.bz");
				// if(indexof != -1 && i < 280 && isAnsi(aliase) )
				if (indexof != -1 && isAnsi(aliase)) {

					hostsForDns.append(aliase.substring(0, indexof));
					hostsForDns.append(" A 0 78.37.191.193 86400\n");
				}
			}

			if (curentDate.getHours() != lastPost.getHours()) {
				DomainRegister r = new DomainRegister();
				String auth = r.getAuth();
				r.setDomainAliases("irr.bz", hostsForDns.toString(), auth);
				System.out.println(r.getPostCommand(auth));
				Adp.getArgs().clear();
				Adp.getArgs().put("LAST_DATE", new Date());
				Adp.executeUpdateWithArgs("UPDATE DOMAIN_PROC SET LAST_DATE  = ? WHERE DOMAIN_PROC_ID = 0",
						Adp.getArgs());
			}

			// System.exit(0);
		} catch (SQLException e) {
			log.error(e);
		} catch (Exception e) {
			// System.out.println(" server config: " + file.getPath());
			log.error(e);
		} finally {

			if (Adp != null) {
				Adp.close();
			}

		}

	}

	/**
	 * Writes the current list of site hosts into the server configuration file.
	 */
	@Profiled(logger = CLASS_NAME, tag = "addAliasesInFile", message = "addAliasesInFile: {$retrun} ")
	final public void addAliasesInFile() {

		// SELECT LAST_DATE FROM DOMAIN_PROC WHERE DOMAIN_PROC_ID = 0

		QueryManager qm = null;
		AddAliase addAliase;
		Document doc;
		String query = "select host  from site group by host";
		String host = "www.siteforyou.net";
		String aliase = "";

		// StringBuffer hosts_for_dns = new StringBuffer();
		File file = new File("/");
		String serverConfig = System.getProperty("conf");
		if (serverConfig == null || serverConfig.length() == 0) {
			String dir = System.getProperty("user.dir");
			dir = dir.substring(0, dir.indexOf("bin")) + "conf" + File.separatorChar + "server.xml";
			// File f = new File("C:/apache-tomcat-6.0.14/conf/server.xml") ;
			file = new File(dir);
		} else {
			file = new File(serverConfig);
		}

		try {
			qm = new QueryManager();
			if (qm == null)
				return;

			addAliase = new AddAliase();
			doc = addAliase.loadConfig(file);
			addAliase.RemoveAllAliase(doc, host);

			qm.executeQuery(query);
			for (int i = 0; qm.rows().size() > i; i++) {
				aliase = qm.getValueAt(i, 0);
				int indexof = aliase.indexOf(".irr.bz");
				// if(indexof != -1 && i < 280 && isAnsi(aliase) )
				if (indexof != -1 && isAnsi(aliase)) {
					addAliase.AddAliaseToHost(doc, host, aliase);
				}
			}
			addAliase.writeXmlFile(doc, file);

			// System.exit(0);
		} catch (SQLException e) {
			log.error(e);
		} catch (Exception e) {
			System.out.println(" server config: " + file.getPath());
			log.error(e);
		} finally {
			if (qm != null) {
				qm.close();
			}
		}
	}

	/**
	 * Checks that a word contains only ANSI characters.
	 *
	 * @return true if no non-ANSI character is present
	 */
	@Profiled(logger = CLASS_NAME, tag = "isAnsi", message = "word: {$0} , isAnsi: {$retrun} ")
	final public boolean isAnsi(String word) {
		if (word == null)
			return false;
		word = word.toLowerCase();
		String IntField = "0123456789qwertyuiopasdfghjklzxcvbnm_-.";
		for (int i = 0; i < word.length(); i++) {

			if (IntField.indexOf(word.charAt(i)) == -1) {
				if (word.charAt(i) != '-' && i != 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * @return the cms_settings resource bundle
	 */
	final public ResourceBundle getResourcesCmsSettings() {
		return setupResources;
	}

	/**
	 * @return the cached XSL transformer map
	 */
	final public Map getTransformerMap() {
		return transformerMap;
	}

	/**
	 * @return the actions resource bundle
	 */
	final public ResourceBundle getActionsResources() {
		return actionsResources;
	}

	/**
	 * @return the XSLT resource bundle
	 */
	final public ResourceBundle getXsltResources() {
		return xsltResources;
	}

	/**
	 * @return the session-scope bean registry bundle
	 */
	final public ResourceBundle getSessionScope() {
		return sessionScope;
	}

	/**
	 * @return the setup resource bundle
	 */
	final public ResourceBundle getSetupResources() {
		return setupResources;
	}

	/**
	 * @return the shop-creation bean
	 */
	final public CreateShopBean getCreateShopBean() {
		return createShopBean;
	}

}
