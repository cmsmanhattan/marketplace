package com.cbsinc.cms.controllers;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

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

/**
 * Controller of SetDomain.jsp: attaches a domain name to the site.
 */
@PageController(jspName = "SetDomain.jsp")
public class SetDomainAction extends TemplateAction {

	public boolean isInternet = true;

	public SetDomainAction() {
	}

	/**
	 * Template-method hook: processes the request and fills the beans; called by both doGet and doPost.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		AuthorizationPageBean authorizationPageBean = getAuthorizationPageBean();
		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

		if (authorizationPageBean == null)
			return;
		request.setCharacterEncoding("UTF-8");
		String host = request.getParameter("domain");
		if (host != null) {
			authorizationPageBean.setHost(host);
			authorizationPageFaced.saveNewDomain(authorizationPageBean.getHost(), authorizationPageBean.getSiteId());
			request.setAttribute("message", "host name changed successfully.");
		}

	}

}
