package com.cbsinc.cms.payments;

import java.io.IOException;

import org.apache.log4j.Logger;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code. You can not use it and you
 * cannot change it without written permission from Konstantin Grabko Email:
 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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

/**
 * Where Stripe sends the customer's browser after Checkout,
 * {@code /stripe/return?result=success|cancel&account_hist_id=N&session_id=cs_...}.
 *
 * <p>
 * The browser is not trusted: the query string is whatever the customer
 * typed. On {@code success} the session is read back from Stripe with the
 * secret key and settled only if Stripe itself says it is paid and it belongs
 * to the row named in the URL. This makes the return page a second, equally
 * safe settlement path, so the balance is usually already updated by the time
 * the customer lands on AccountHistory.jsp even if the webhook is a few
 * seconds behind. If the two race, the row lock in PaymentSettlement makes
 * one of them a no-op.
 * </p>
 *
 * <p>
 * On {@code cancel} nothing is settled: the customer may still go back and
 * pay, and Stripe will send {@code checkout.session.expired} in 24 h if they
 * do not. The customer is simply returned to the top-up page.
 * </p>
 */
public class StripeReturnServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(StripeReturnServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = request.getParameter("result");
		String accountHistId = request.getParameter("account_hist_id");
		String sessionId = request.getParameter("session_id");
		String ctx = request.getContextPath() == null ? "" : request.getContextPath();

		if (accountHistId == null || !accountHistId.matches("[0-9]{1,18}")) {
			response.sendRedirect(ctx + "/PrePay.jsp?pay_result=error");
			return;
		}

		if (!"success".equals(result)) {
			log.info("Stripe checkout cancelled by customer for account_hist " + accountHistId);
			response.sendRedirect(ctx + "/PrePay.jsp?pay_result=cancel");
			return;
		}

		if (sessionId == null || !sessionId.startsWith("cs_")) {
			response.sendRedirect(ctx + "/PrePay.jsp?pay_result=error");
			return;
		}

		String outcome = "pending";
		try {
			StripeClient client = new StripeClient(PaymentConfig.getInstance());
			StripeClient.CheckoutSession s = client.retrieveCheckoutSession(sessionId);

			if (!accountHistId.equals(s.clientReferenceId)) {
				log.warn("Return for account_hist " + accountHistId + " with session " + sessionId
						+ " that belongs to " + s.clientReferenceId + " from " + request.getRemoteAddr());
				response.sendRedirect(ctx + "/PrePay.jsp?pay_result=error");
				return;
			}

			if (s.isPaid()) {
				PaymentSettlement.Result r = new PaymentSettlement().settleSuccess(accountHistId,
						StripeClient.fromMinorUnits(s.amountTotalMinor, s.currency), s.currency, "stripe_ok", s.id);
				switch (r) {
				case SETTLED:
				case ALREADY_SETTLED:
					outcome = "ok";
					break;
				case MISMATCH:
					outcome = "mismatch";
					break;
				default:
					outcome = "pending";
				}
			} else if ("expired".equals(s.status)) {
				outcome = "expired";
			}
		} catch (Exception e) {
			// Stripe unreachable right now: the webhook will settle it. Show the
			// customer the history page; the row is still marked in-process.
			log.warn("Could not verify Stripe session " + sessionId + " on return; relying on webhook", e);
			outcome = "pending";
		}

		response.sendRedirect(ctx + "/AccountHistory.jsp?pay_result=" + outcome);
	}

}
