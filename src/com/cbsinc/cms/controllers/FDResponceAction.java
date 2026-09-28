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


import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CheckPaymentResult;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@PageController( jspName = "FDRespond.jsp" )
/**
 * Legacy bank-gateway callback: receives the gateway result and settles the pending top-up through PaymentSettlement (pass 30). Non-numeric invoice/result values are rejected.
 */
public class FDResponceAction extends TemplateAction {

	static private Logger log = Logger.getLogger(CheckPaymentResult.class);

	public FDResponceAction() {

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
		String xInvoiceNum = request.getParameter("x_invoice_num");
		String xResponseCode = request.getParameter("x_response_code");
		String xPoNum = request.getParameter("x_po_num");
		String siteId = xPoNum.split("_")[0];
		String orderId = xPoNum.split("_")[1];
		String xResponseReasonText = request.getParameter("x_response_reason_text");
		parserRequest(xInvoiceNum, xResponseCode, xResponseReasonText);
		System.out.println("pay respone: " + request.getRequestURI());
		authorizationPageBean.setSiteId(siteId, authorizationPageFaced);

		String url;
		if ((url = getCookiesValue(request, "payment_page")) != null) {
			response.sendRedirect(url);
			return;
		}

	}

	private String getCookiesValue(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie c = cookies[i];
			String _name = c.getName();
			if (name.equals(_name))
				return c.getValue();
		}

		return null;
	}

	/**
	 * Parses the gateway callback: code 1 settles the top-up, codes 2/3 record a failure; anything else is ignored.
	 */
	public void parserRequest(String iStrNumerOrder, String iStrRezult, String iStrDecsription) {
		// dfis codes: 1 Approved, 2 Declined, 3 Error
		final int approved = 1;
		final int declined = 2;
		final int error = 3;

		log.info("bank callback: invoice " + iStrNumerOrder + " result " + iStrRezult + " " + iStrDecsription);
		if (iStrRezult == null || iStrNumerOrder == null)
			return;
		iStrRezult = iStrRezult.trim();
		long code;
		try {
			code = Long.parseLong(iStrRezult);
			Long.parseLong(iStrNumerOrder.trim()); // account_hist id must be numeric
		} catch (NumberFormatException ex) {
			log.error("bank callback with non-numeric fields: " + iStrNumerOrder + " / " + iStrRezult);
			return;
		}

		// FIX: the previous end_addmoney() recomputed the balance from a snapshot
		// taken when the top-up started (stale, and 0 in the original code), had no
		// protection against a repeated callback (double credit) and spliced ids
		// into SQL. PaymentSettlement locks the pending row and the account,
		// rejects duplicates and credits the current balance atomically.
		com.cbsinc.cms.payments.PaymentSettlement settlement = new com.cbsinc.cms.payments.PaymentSettlement();
		String reference = "bank:" + iStrNumerOrder;
		if (code == approved) {
			com.cbsinc.cms.payments.PaymentSettlement.Result r = settlement.settleSuccess(iStrNumerOrder.trim(),
					null, null, iStrRezult, reference);
			if (r == com.cbsinc.cms.payments.PaymentSettlement.Result.SETTLED
					|| r == com.cbsinc.cms.payments.PaymentSettlement.Result.ALREADY_SETTLED) {
				getAuthorizationPageBean().setStrMessage("Thank you, your order has been placed");
				getAuthorizationPageBean().setStrEMail("Your order was processed successfully. Here is your receipt.");
			} else {
				getAuthorizationPageBean().setStrMessage("Payment could not be confirmed, please contact support");
			}
		} else if (code == declined || code == error) {
			settlement.settleFailure(iStrNumerOrder.trim(), iStrRezult, reference);
		}
	}

}
