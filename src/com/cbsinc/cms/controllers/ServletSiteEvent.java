package com.cbsinc.cms.controllers;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.RequestFilter;
import com.cbsinc.cms.eureka.CMSEurekaClient;
import com.cbsinc.cms.eureka.CMSEurekaService;
import com.cbsinc.cms.eureka.CMSServiceBase;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.utils.FileStorage;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/*
 *   <listener>
 *   <listener-class>mobilesoft.ServletSiteEvent</listener-class>
 *   </listener>
 *
 */

public class ServletSiteEvent extends HttpServlet
		implements HttpSessionListener, ServletContextListener, ServletRequestListener {

	private static final long serialVersionUID = -2594377223913825222L;
	private Logger log = Logger.getLogger(ServletSiteEvent.class);
	private ResourceBundle sessionScope = null;
	ServletContext servletContext;

	static CMSServiceBase cmsServiceBase = null ;


	transient ResourceBundle setupResources = null;
	transient long delay = 60000;
	transient AuthorizationPageFaced authorizationPageFaced = null;

	public ServletSiteEvent() {
		super();
		if (sessionScope == null)
			sessionScope = PropertyResourceBundle.getBundle("session_scope");

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");

		synchronized (this) {
			if( cmsServiceBase == null ) {

		        try {
		        	ResourceBundle eurekaClientResources = PropertyResourceBundle.getBundle("eureka-client");
		        	Enumeration<String> en = eurekaClientResources.getKeys() ;
		        	while (en.hasMoreElements()) {
		                String key = en.nextElement() ;
		                System.setProperty(key,eurekaClientResources.getString(key));
		            }

		            DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
		            ApplicationInfoManager applicationInfoManager = CMSEurekaService.initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		            EurekaClient eurekaClient = CMSEurekaService.initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		            cmsServiceBase = new CMSServiceBase(applicationInfoManager, eurekaClient, configInstance);


		        } catch (Exception e) {
		            e.printStackTrace();
		        }

			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletContext = config.getServletContext();
		super.init(config);
	}

//	void loadClassesSessionScope( HttpSession  session  )
//	{
//		//HttpServletRequest servletRequest =
//			//session.getServletContext().getNamedDispatcher("/").
//
//		QueryManager Adp = new QueryManager();
//		String query = "" ;
//		String key = "";
//		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
//		try {
//		query = "select USER_ID , TYPE , CLASSBODY  from STORE_SESSION WHERE where USER_ID = " + AuthorizationPageBeanId.getIntUserID() ;
//		ResultSet rs  = Adp.executeQueryResultSet(query);
//		//ResultSet rs = ps.executeQuery("SELECT ENGINEERS FROM PERSONNEL");
//		while (rs.next())
//		{
//			//Engineer eng = (Engineer)rs.getObject("CLASSBODY");
//			//System.out.println(eng.lastName + ", " + eng.firstName);
//			Enumeration enumeration = session_scope.getKeys();
//			while (enumeration.hasMoreElements())
//			{
//				key = (String) enumeration.nextElement();
//				String type = session_scope.getString(key).trim();
//				String typedb =  rs.getString("TYPE");
//				if(typedb  != null ) if(typedb.equals(type))
//				{
//					Object obj = rs.getObject("CLASSBODY") ;
//					session.setAttribute(key, obj);
//				}
//			}
//		}
//
//
//		}
//		catch (Exception e)
//		{
//			log.error(e) ;
//		}
//		finally
//		{
//			if(Adp != null)	Adp.close();
//		}
//	}

	public void serializeObject(File fileName, Object obj) {

		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(obj);

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return;
	}

	String getCookiesDir(ServletContext servletContext) {
		return (String) servletContext.getAttribute("cookies_dir");
	}

	void saveClassesSessionScopeFromDir(HttpSession session, ServletContext servletContext) {

		String cookiesDir = getCookiesDir(servletContext);
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		if (AuthorizationPageBeanId == null)
			return;

		String key = "";

		Enumeration enumeration;
		String type = null;
		Object obj = null;
		String cokieSessionId = (String) session.getAttribute("cokie_session_id");
		session.removeAttribute(cokieSessionId);
		try {
			enumeration = sessionScope.getKeys();
			HashMap map = new HashMap();
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				obj = session.getAttribute(key);
				if (obj == null)
					continue;
				map.put(key, obj);
			}
			File file = new File(cookiesDir + File.separatorChar + cokieSessionId);
			serializeObject(file, map);
		} catch (Exception e) {

			log.error(e);
		} finally {

		}
	}

	public void sessionCreated(HttpSessionEvent se) {

		System.out.println("load session");

		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpServletRequest request = RequestFilter.getRequest();
		HttpServletResponse response = RequestFilter.getResponse();
		HttpSession hsession = se.getSession();

		String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
				(HttpServletResponse) response);
		hsession.setAttribute("cokie_session_id", sessionId);
//		System.out.println("session: " + session_id );
		if (authorizationPageFaced.isCokieSessionIdExists((HttpServletRequest) request, (HttpServletResponse) response)
				&& authorizationPageFaced.isLoginFromCookieNew1(sessionId, hsession,
						authorizationPageFaced.getSessionScope()))
//		//if( authorizationPageFaced.isLoginFromCookieFromDir( session_id ,  hsession , servletContext , session_scope ) )
		{
			// authorizationPageBeanId = ((AuthorizationPageBean)
			// hsession.getAttribute("authorizationPageBeanId"));
			System.out.println("A session was loaded");
		}

		/*
		 * Cookie[] cookies = request.getCookies(); for (int i = 0; i < cookies.length;
		 * i++) { Cookie c = cookies[i]; String name = c.getName(); String value =
		 * c.getValue(); out.println(name + " = " + value); }
		 */

		// se
		// Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		// Cookie cookie = new Cookie("","");
		// ((HttpServletResponse) response).addCookie(cookie);

	}

	public void sessionDestroyed(HttpSessionEvent se) {

		// System.gc();
//		FacesContext fc = FacesContext.getCurrentInstance();
//		HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
//		String lastPage = request.getQueryString() ;
		// -- Удаляем всех кто не на сайте из контакт листа
		AuthorizationPageFaced authorizationPageFaced = null;
		Map hashMap = null;
		HttpSession httpSession = se.getSession();
		if (httpSession == null)
			return;
		// expiring

		//////////// saveSession( httpSession ) ;

		ServletContext servletContext = httpSession.getServletContext();
		if (!(se.getSession().getAttribute("authorizationPageBeanId") instanceof AuthorizationPageBean))
			return;
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) se.getSession()
				.getAttribute("authorizationPageBeanId");

		// saveClassesSessionScopeFromDir(httpSession , servletContext) ;
		if (AuthorizationPageBeanId.getRoleId() == SiteRole.ADMINISTRATOR_ROLE_ID
				|| AuthorizationPageBeanId.getRoleId() == SiteRole.MEMBER_ROLE_ID) {
			if (servletContext == null)
				return;
			try {
				authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// authorizationPageFaced.saveClassesSessionScopeByLogin(httpSession) ;
			authorizationPageFaced.saveClassesSessionScopeNew1(httpSession);
		}

		if (!(servletContext.getAttribute("userlist") instanceof Map))
			return;
		hashMap = (Map) servletContext.getAttribute("userlist");
		hashMap.remove(AuthorizationPageBeanId.getStrLogin());

	}

	void saveSession(HttpSession httpSession) {

		ObjectOutputStream outputToServlet = null;
		FileOutputStream fileOutputStream = null;
		String FileName = httpSession.getId() + ".ser";
		//String sessionStorePath = this.getClass().getResource("").getPath();
		//sessionStorePath = sessionStorePath.substring(1, sessionStorePath.indexOf("/WEB-INF/"));
		String path = FileStorage.getInstance().getPath() ;
		File file = new File(path + + File.separatorChar +  "sessions");
		try {

			if (!file.exists()) {
				if (file.mkdirs()) {
					file = new File(file.getPath(), FileName);
					file.createNewFile();
				}
			} else {
				file = new File(file.getPath(), FileName);
				if (!file.exists())
					file.createNewFile();
			}

			fileOutputStream = new FileOutputStream(file);

			Hashtable hashtableSessionVaribal = new Hashtable();

			Enumeration enumeration = httpSession.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String name = (String) enumeration.nextElement();
				Object obj = httpSession.getAttribute(name);
				if (obj instanceof Serializable) {
					hashtableSessionVaribal.put(name, obj);
				}
			}

			outputToServlet = new ObjectOutputStream(fileOutputStream);

			outputToServlet.writeObject(hashtableSessionVaribal);

			outputToServlet.flush();

		} catch (IOException e) {
			log.error(e);
		} finally {
			if (outputToServlet != null) {
				try {
					outputToServlet.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	void loadSession(HttpSession httpSession) {

		ObjectInputStream inputToServlet = null;
		Hashtable hashtableSessionVaribal = null;
		FileInputStream fileInputStream = null;
		File file = null;
		//String search = "webapps";
		String FileName = httpSession.getId() + ".ser";
		//String sessionStorePath = this.getClass().getResource("").getPath();
		//sessionStorePath = sessionStorePath.substring(1, sessionStorePath.indexOf("/WEB-INF/"));
		String path = FileStorage.getInstance().getPath() ;
		file = new File(path + + File.separatorChar +"sessions", FileName);

		if (!file.exists())
			return;

		try {

			fileInputStream = new FileInputStream(file);
			inputToServlet = new ObjectInputStream(fileInputStream);

			Object obj = inputToServlet.readObject();

			if (obj instanceof Hashtable) {
				hashtableSessionVaribal = (Hashtable) obj;
				Enumeration enumeration = hashtableSessionVaribal.keys();
				while (enumeration.hasMoreElements()) {
					String name = (String) enumeration.nextElement();
					Object data = hashtableSessionVaribal.get(name);
					httpSession.setAttribute(name, data);
				}
			}

		} catch (Exception e) {
			log.error(e);
		} finally {
			if (inputToServlet != null) {
				try {
					inputToServlet.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		destr();

//		ServletContext servletContext =  sce.getServletContext() ;
//		AuthorizationPageFaced authorizationPageFaced = null;
//		try {
//			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		authorizationPageFaced.AddAliasesInFile(sce);

	}

	public void contextInitialized(ServletContextEvent sce) {
		init();

	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest servletRequest = sre.getServletRequest();
		// servletRequest.getServletContext().
		System.out.println("ServletRequest requestDestroyed. Remote IP=" + servletRequest.getRemoteAddr());
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		ServletRequest servletRequest = sre.getServletRequest();
		System.out.println("ServletRequest initialized. Remote IP=" + servletRequest.getRemoteAddr());
	}


	 public void init() {
			String eureka = System.getenv("EUREKA");
			if(eureka == null || eureka.trim().isEmpty())
			{
				eureka = setupResources.getString("eureka.client").trim();
			}
			if(cmsServiceBase != null && eureka.equalsIgnoreCase("true")) {
		     cmsServiceBase.start();
		     cmsServiceBase.sendRequestToServiceUsingEureka();
			}
	    }


	 private void destr() {
		 if(cmsServiceBase != null ) cmsServiceBase.stop();

	}

}
