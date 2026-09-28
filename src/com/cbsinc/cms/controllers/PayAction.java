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

import com.cbsinc.cms.utils.Validation;
import java.io.File;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.OperationAmountBean;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.PayBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.OrderFaced;
import com.cbsinc.cms.payments.PaymentRouter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Balance top-up controller (Pay.jsp). Validates the amount, creates the account_hist row and routes the payment through PaymentRouter: Stripe Checkout when enabled, otherwise the legacy bank gateway form.
 */
@PageController(jspName = "Pay.jsp")
public class PayAction extends TemplateAction {

	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PayAction.class);

	public boolean isInternet = true;

	public PayAction() {
	}

	/**
	 * Validates Amount (positive, at most two decimals, bounded), records the pending top-up and redirects to the chosen payment channel.
	 *
	 * @throws Exception on failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		AuthorizationPageBean authorizationPageBean = getAuthorizationPageBean();
		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		OrderFaced orderFaced = ServiceLocator.getInstance().getOrderFaced();
		PayBean payBeanId = getPayBean();
		Map messageMail = getMessageMail();
		OrderBean orderBeanId = getOrderBean();
		OperationAmountBean operationAmountBeanId = getOperationAmountBean();

		// if (getAuthorizationPageBean().empty() || getPayBean().empty() ||
		// orderFaced.empty())return;

		request.setCharacterEncoding("UTF-8");

		ResourceBundle resources = null;
		if (resources == null)
			resources = PropertyResourceBundle.getBundle("localization", response.getLocale());

		String Amount = request.getParameter("Amount");
		// FIX: Amount went straight into new Double(...) and into account_hist.
		// Non-numeric input produced an error page after the row was created;
		// a negative value created a negative top-up. Positive, at most two
		// decimals, and bounded.
		Amount = normaliseAmount(Amount);
		if (Amount == null) {
			response.sendRedirect("PrePay.jsp?pay_result=badamount");
			return;
		}
		payBeanId.setAmount(Amount);
		Amount = null;

		String currencyId = request.getParameter("currency_id");
		if (currencyId != null) {
			payBeanId.setCurrencyId(currencyId);
			payBeanId.setCurrencyCd(payBeanId.getCurrencyCode(currencyId));
		} else {
			payBeanId.setCurrencyId("0");
		}
		currencyId = null;

		String paysystemId = request.getParameter("paysystem_id");
		if (paysystemId != null) {
			payBeanId.setPaysystemId(paysystemId);
			payBeanId.setPaysystemCd(payBeanId.getPaySystemCode(paysystemId));
		} else {
			payBeanId.setAmount("0");
		}
		paysystemId = null;

		authorizationPageFaced.initPaySysShopCd(authorizationPageBean.getSiteId(), authorizationPageBean);
		payBeanId.setDescription(resources.getString("input_money"));
		String userOs = "";
		String headerName = "";
		String headerValue = "";
		java.util.Enumeration en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			headerName = (String) en.nextElement();
			headerValue = request.getHeader(headerName);
			userOs = userOs.concat(headerName + "=" + headerValue + "\n");
		}
		payBeanId.setAccountHistId(operationAmountBeanId.addMoneyStart(payBeanId.getDescription(),
				new Double(payBeanId.getAmount()), payBeanId.getCurrencyId(), authorizationPageBean.getIntUserID(),
				request.getRemoteAddr(), userOs, orderBeanId.getOrderId()));

		if (payBeanId.getAccountHistId() == null || payBeanId.getAccountHistId().isEmpty()) {
			// addMoneyStart rolled back; there is no row to pay against. Sending the
			// customer to a gateway with an empty order id would produce a payment
			// nobody can reconcile.
			log.error("addMoneyStart produced no account_hist row for user " + authorizationPageBean.getIntUserID());
			response.sendRedirect("PrePay.jsp?pay_result=error");
			return;
		}

		// --- Payment routing: Stripe Checkout first, legacy bank form as backup.
		// PaymentRouter creates the Checkout Session and returns where to send
		// the customer. If Stripe is off, the currency is not enabled for it, the
		// circuit breaker is open, or session creation fails, the decision is
		// LEGACY and this action carries on exactly as before: Pay.jsp renders
		// the bank form and CheckPaymentResult settles it.
		PaymentRouter.Decision decision = PaymentRouter.getInstance().route(request, payBeanId.getAccountHistId(),
				payBeanId.getAmount(), payBeanId.getCurrencyCd(), payBeanId.getDescription(),
				authorizationPageBean.getStrEMail());
		payBeanId.setPaymentChannel(decision.channel.code());
		payBeanId.setPaymentReason(decision.reason);
		if (decision.isStripe()) {
			payBeanId.setStripeCheckoutUrl(decision.redirectUrl);
			payBeanId.setStripeSessionId(decision.reference);
			payBeanId.setStatusInrocess(orderBeanId.getOrderId());
			// 303 so a POST from PrePay.jsp is not replayed against Stripe's URL.
			response.setStatus(HttpServletResponse.SC_SEE_OTHER);
			response.setHeader("Location", decision.redirectUrl);
			response.flushBuffer();
			return;
		}

		messageMail.clear();
		messageMail.put("@FirstName", authorizationPageBean.getStrFirstName());
		messageMail.put("@LastName", authorizationPageBean.getStrLastName());
		messageMail.put("@NumberOfOrder", orderBeanId.getOrderId());
		messageMail.put("@ContactPerson", orderBeanId.getContactPerson());
		messageMail.put("@Balans", "" + orderFaced.getBalans(authorizationPageBean.getIntUserID()));
		messageMail.put("@Phone", orderBeanId.getShipmentPhone());
		messageMail.put("@Address", orderBeanId.getShipmentAddress());
		messageMail.put("@City", orderBeanId.getCityFullname());
		messageMail.put("@Contry", orderBeanId.getCountryFullname());
		messageMail.put("@CustomerEmail", orderBeanId.getShipmentEmail());
		messageMail.put("@CustomerFax", orderBeanId.getShipmentFax());
		messageMail.put("@CustomerCommentariy", orderBeanId.getShipmentDescription());
		messageMail.put("@ProductCount", "" + orderFaced.getProductsListSize(request, orderBeanId));
		messageMail.put("@IPAddress", "" + request.getRemoteAddr());
		messageMail.put("@HTTPHEAD", "" + userOs);

		System.out.println("code pay: " + payBeanId.getPaysystemCd());
		System.out.println("code pay1: " + resources.getString(payBeanId.getPaysystemCd()));
		messageMail.put("@PaySystem", "" + resources.getString(payBeanId.getPaysystemCd()));
		messageMail.put("@Amount", "" + payBeanId.getAmount());
		messageMail.put("@Currency", "" + payBeanId.getCurrencyLable(payBeanId.getCurrencyId()));

		String sitePath = (String) request.getSession().getAttribute("site_path");
		String shopOrder = sitePath + File.separatorChar + "mail" + File.separatorChar + "ShopPayment.txt";
		String clientOrder = sitePath + File.separatorChar + "mail" + File.separatorChar + "ClientPayment.txt";
		String attachFile = sitePath + File.separatorChar + "mail" + File.separatorChar + "info.txt";

//			MQSender mqSender = new MQSender( request.getSession(),SendMailMessageBean.messageQuery) ;
//			Message message = new Message();
//			message.put("to" , ownerShop.getStrEMail()  ) ;
//			message.put("subject" , resources.getString("order.order_number.text")) ;
//			message.put("pathmessage" , shopOrder ) ;
//			message.put("attachFile" , attachFile ) ;
//			message.put("fields" , messageMail ) ;
//			mqSender.send(message);
//
//			message = new Message();
//			message.put("to" , authorizationPageBean.getStrEMail()  ) ;
//			message.put("mailfrom" , ownerShop.getStrEMail() ) ;
//			message.put("subject" , resources.getString("order.order_number.text")) ;
//			message.put("pathmessage" , clientOrder ) ;
//			message.put("attachFile" , attachFile ) ;
//			message.put("fields" , messageMail ) ;
//			mqSender.send(message);
//			authorizationPageBean.setStrMessage("Order was send by email");

		System.out.println("path: " + shopOrder);
		System.out.println("path: " + resources.getString("pay.subject_mail_client"));
//		System.out.println("rezalt: " + sendMailAgent.putMessageInPool(null ,resources.getString("pay.subject_mail_client"),   shopOrder   , attachFile, messageMail  ) ) ;
//		System.out.println("rezalt: " + sendMailAgent.putMessageInPool(orderBeanId.getshipment_email() ,resources.getString("pay.subject_mail_shop"), clientOrder , attachFile , messageMail  ) ) ;

		payBeanId.setStatusInrocess(orderBeanId.getOrderId());

	}


	/** Largest single top-up accepted, major units. */
	static final java.math.BigDecimal MAX_TOPUP = new java.math.BigDecimal("1000000");

	/**
	 * Returns the amount as a canonical "123.45" string, or null if it is not
	 * a positive number with at most two decimals within MAX_TOPUP.
	 */
	static String normaliseAmount(String raw) {
		if (raw == null)
			return null;
		raw = raw.trim().replace(',', '.');
		if (!raw.matches("[0-9]{1,9}(\\.[0-9]{1,2})?"))
			return null;
		java.math.BigDecimal v = new java.math.BigDecimal(raw);
		if (v.signum() <= 0 || v.compareTo(MAX_TOPUP) > 0)
			return null;
		return v.stripTrailingZeros().toPlainString();
	}
}
