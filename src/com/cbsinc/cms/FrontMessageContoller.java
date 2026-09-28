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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.controllers.ServletSiteEvent;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageListener;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

/*
 *   <listener>
 *   <listener-class>com.cbsinc.cms.FrontMessageContoller</listener-class>
 *   </listener>
 *
 */

public class FrontMessageContoller extends HttpServlet implements HttpSessionAttributeListener {

	private Logger log = Logger.getLogger(ServletSiteEvent.class);
	private ResourceBundle jmsBeansResources = null;
	private HashMap controllerMap = new HashMap();

	public FrontMessageContoller() {
		jmsBeansResources = PropertyResourceBundle.getBundle("jms_beans");
	}

	public void init() throws ServletException {
		// if( jms_beans_resources == null ) jms_beans_resources =
		// PropertyResourceBundle.getBundle("jms_beans");

	}

	public void attributeAdded(HttpSessionBindingEvent se) {

		String messageQuery = "";

		if (se.getName() instanceof String)
			messageQuery = se.getName();
		else
			return;

		if (isExistKey(jmsBeansResources.getKeys(), messageQuery)) {
			se.getSession().removeAttribute(se.getName());
			Message message = null;
			if (se.getValue() instanceof Message)
				message = (Message) se.getValue();
			else
				message = (Message) new HashMap();

			Object obj = null;
			if (!controllerMap.containsKey(messageQuery)) {
				String className = jmsBeansResources.getString(messageQuery).trim();
				obj = createObject(className);
				// if(obj == null) throw new Exception("Class " + className + " is not found."
				// );
				controllerMap.put(messageQuery, obj);
			} else {
				obj = controllerMap.get(messageQuery);
			}

			if (obj != null) {
				if (obj instanceof MessageListener) {
					((MessageListener) obj).onMessage(message, se.getSession().getServletContext(), se.getSession());
				}
			}

			// se.getSession().removeAttribute(se.getName());
		}

	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub

	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub

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

	boolean isExistKey(Enumeration en, String key) {
		while (en.hasMoreElements()) {
			String _key = (String) en.nextElement();
			if (_key.equals(key))
				return true;
		}

		return false;

	}
}
