package com.cbsinc.cms.controllers;

import com.cbsinc.cms.utils.Validation;
import com.cbsinc.cms.AccountHistoryDetalBean;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.OrderFaced;

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
 * Controller of AccountHistoryDetal.jsp: detail of a single balance transaction.
 */
@PageController(jspName = "AccountHistoryDetal.jsp")
public class AccountHistoryDetalAction extends TemplateAction {

	public AccountHistoryDetalAction() {
	}

	/**
	 * Template-method hook: processes the request and fills the beans; called by both doGet and doPost.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		AuthorizationPageBean authorizationPageBeanId = getAuthorizationPageBean();
		AccountHistoryDetalBean accountHistoryDetalBean = null;
		OrderFaced orderFaced = ServiceLocator.getInstance().getOrderFaced();

		if (authorizationPageBeanId == null || accountHistoryDetalBean == null || orderFaced == null)
			return;
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("amount_id") != null)
			accountHistoryDetalBean.setAmountId(request.getParameter("amount_id"));
		accountHistoryDetalBean
				.setSelectAccountHistoryDetalXML(orderFaced.getPayment(authorizationPageBeanId.getIntUserID(),
						authorizationPageBeanId.getRoleId(), accountHistoryDetalBean));
	}

}
