package com.cbsinc.cms.controllers;

import java.util.List;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

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

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of Support.jsp: the support request form.
 */
@PageController(jspName = "Support.jsp")
public class SupportAction implements IAction {

	AuthorizationPageFaced authorizationPageFaced;

	public SupportAction() {

	}

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		HttpSession session;
		java.util.LinkedList<String> domainList = null;
		AuthorizationPageBean authorizationPageBeanId = null;

		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		HttpSession session;
		List<String> domainList = null;
		AuthorizationPageBean authorizationPageBeanId = null;

		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		session = request.getSession();
		domainList = (List<String>) session.getAttribute("domainList");
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

	}

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session;
		java.util.LinkedList<String> domainList = null;
		AuthorizationPageBean authorizationPageBeanId = null;

		String email = "";
		String problem = "";
		String person = "";
		String mailto = authorizationPageFaced.getResourcesCmsSettings().getString("support_mail");

		session = request.getSession();
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		email = request.getParameter("email");
		request.setAttribute("email", email);
		problem = request.getParameter("problem");
		request.setAttribute("problem", problem);
		person = request.getParameter("person");
		request.setAttribute("person", person);
		request.setAttribute("message",
				authorizationPageBeanId.getLocalization(servletContext).getString("message_send_well"));

		MessageSender mqSender = new MessageSender(request.getSession(), SendMailMessageBean.messageQuery);
		Message message = new Message();
		message.put("to", mailto);
		message.put("mailfrom", email);
		message.put("subject", "Tech support");
		message.put("body", problem);
		message.put("fromperson", person);
		mqSender.send(message);

	}

}
