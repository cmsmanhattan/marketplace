package com.cbsinc.cms.services.ajax;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
 * собственностью.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

import java.io.IOException;

import com.cbsinc.cms.AuthorizationPageBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Class
 *
 * @web.servlet name="chatclient" display-name="Name for chatclient"
 *              description="Description for chatclient"
 * @web.servlet-mapping url-pattern="/chatclient"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class ChatClient extends HttpServlet {

	final static int sizeChat = 500;

	public ChatClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// StringBuffer buff =
		// (StringBuffer)getServletContext().getAttribute("messages");
		HttpSession session = request.getSession();
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		StringBuffer buff = new StringBuffer();
		if (getServletContext() == null)
			return;
		if (getServletContext().getAttribute("messages") != null)
			buff = (StringBuffer) getServletContext().getAttribute("messages");
		else
			getServletContext().setAttribute("messages", buff);
		String message = "";
		String userNumer = "" + AuthorizationPageBeanId.hashCode();
		if (userNumer.length() > 5)
			userNumer = userNumer.substring(5);
		if (request.getParameter("message") != null) {
			message = request.getParameter("message");
			if (message.length() > 0)

				buff.append(AuthorizationPageBeanId.getStrLogin() + userNumer + ": " + message + "\n");
		}

		if (buff.length() > sizeChat) {
			int d = buff.length() - sizeChat;
			d = buff.indexOf("\n", d);
			buff.delete(0, d);
		}

		response.getWriter().write("<document>");
		response.getWriter().write("<message>");
		response.getWriter().write(buff.toString());
		response.getWriter().write("</message>");
		response.getWriter().write("</document>");

	}

}
