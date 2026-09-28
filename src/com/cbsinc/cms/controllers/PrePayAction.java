package com.cbsinc.cms.controllers;

import com.cbsinc.cms.utils.Validation;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.PrePayBean;
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
 * Controller of PrePay.jsp: the step before a top-up where the amount and payment gateway are chosen.
 */
@PageController(jspName = "PrePay.jsp")
public class PrePayAction extends TemplateAction {

	public boolean isInternet = true;

	public PrePayAction() {
	}

	/**
	 * Template-method hook: processes the request and fills the beans; called by both doGet and doPost.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		PrePayBean prePayBean = getPrePayBean();
		OrderBean orderBean = getOrderBean();
		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

		if (authorizationPageFaced == null || prePayBean == null || orderBean == null)
			return;
		request.setCharacterEncoding("UTF-8");
		prePayBean.setSelectCurrencyListXML(
				authorizationPageFaced.getXMLDBList("Pay.jsp?currency_id", "currency", orderBean.getOrderCurrencyId(),
						"SELECT currency_id , currency_desc FROM currency  WHERE active = true"));
		prePayBean.setSelectPaysystemListXML(authorizationPageFaced.getXMLDBList("Pay.jsp?paysystem_id", "paysystem",
				"1",
				"SELECT paysystem.paysystem_id , paysystem.description FROM  paysystem WHERE paysystem.active = true"));
	}

}
