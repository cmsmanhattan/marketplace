package com.cbsinc.cms.controllers;

import java.util.Enumeration;

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

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.OperationAmountBean;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.PayBean;
import com.cbsinc.cms.faceds.OrderFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@PageController( jspName = "FDPay.jsp" )
/**
 * Legacy bank-gateway controller: builds the redirect to the external payment form for a pending top-up.
 */
public class FDPayAction extends TemplateAction {

	public boolean isInternet = true;

	public FDPayAction() {
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
		OrderBean orderBeanId = getOrderBean();
		OperationAmountBean operationAmountBean = getOperationAmountBean();
		PayBean payBeanId = getPayBean();
		OrderFaced orderFaced = ServiceLocator.getInstance().getOrderFaced();
		ResourceBundle resources = PropertyResourceBundle.getBundle("localization", response.getLocale());

		// if (authorizationPageBean.empty() || payBeanId.empty() ||
		// orderFaced.empty())return;

		request.setCharacterEncoding("UTF-8");
		payBeanId.setDescription(resources.getString("Purchase"));

		String userOs = "";
		String headerName = "";
		String headerValue = "";
		Enumeration en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			headerName = (String) en.nextElement();
			headerValue = request.getHeader(headerName);
			userOs = userOs.concat(headerName + "=" + headerValue + "\n");
		}
		payBeanId.setAccountHistId(operationAmountBean.addMoneyStart("Purchase",
				Double.parseDouble(orderBeanId.getOrderAmount()), orderBeanId.getOrderCurrencyId(),
				authorizationPageBean.getIntUserID(), request.getRemoteAddr(), userOs, orderBeanId.getOrderId()));
		payBeanId.setStatusInrocess(orderBeanId.getOrderId());
	}

}
