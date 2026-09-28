package com.cbsinc.cms.payments;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;
import org.json.JSONObject;

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
 * Receives Stripe events at {@code /stripe/webhook}.
 *
 * <p>
 * This is the authoritative settlement path. The customer's browser may never
 * come back to the site after paying (closed tab, lost connection), but Stripe
 * will keep delivering this event until it gets a 2xx.
 * </p>
 *
 * <p>
 * Handled events:
 * </p>
 * <ul>
 * <li>{@code checkout.session.completed} with {@code payment_status = paid}
 * → credit the account;</li>
 * <li>{@code checkout.session.async_payment_succeeded} → credit (bank
 * debits, etc., that confirm later);</li>
 * <li>{@code checkout.session.async_payment_failed} and
 * {@code checkout.session.expired} → close the row as failed.</li>
 * </ul>
 * <p>
 * Everything else is acknowledged and ignored. Responses: 200 for handled or
 * ignored, 400 for a bad signature or body, 500 if the database failed (so
 * Stripe retries).
 * </p>
 *
 * <p>
 * Register the URL in the Stripe dashboard and put its signing secret in
 * {@code stripe.webhook_secret}. Without the secret every delivery is
 * rejected; a webhook that accepts unsigned events lets anyone credit any
 * account.
 * </p>
 */
public class StripeWebhookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(StripeWebhookServlet.class);

	/** Stripe events are small; anything past this is not one of them. */
	private static final int MAX_BODY = 1024 * 1024;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PaymentConfig cfg = PaymentConfig.getInstance();
		String secret = cfg.getStripeWebhookSecret();
		if (secret == null || secret.isEmpty()) {
			log.error("Stripe webhook received but stripe.webhook_secret is not configured; rejecting");
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return;
		}

		byte[] body = readBody(request);
		if (body == null) {
			response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
			return;
		}

		String sig = request.getHeader("Stripe-Signature");
		if (!StripeWebhookVerifier.verify(body, sig, secret, cfg.getStripeWebhookToleranceSeconds(),
				System.currentTimeMillis() / 1000)) {
			log.warn("Stripe webhook with bad signature from " + request.getRemoteAddr());
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid signature");
			return;
		}

		JSONObject event;
		try {
			event = new JSONObject(new String(body, StandardCharsets.UTF_8));
		} catch (RuntimeException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
			return;
		}

		String type = event.optString("type", "");
		JSONObject data = event.optJSONObject("data");
		JSONObject session = data == null ? null : data.optJSONObject("object");
		String eventId = event.optString("id", "?");

		if (session == null
				|| !(type.startsWith("checkout.session.") || type.startsWith("charge."))) {
			log.debug("Stripe event " + eventId + " of type " + type + " ignored");
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		String accountHistId = session.optString("client_reference_id", "");
		if (accountHistId.isEmpty()) {
			JSONObject md = session.optJSONObject("metadata");
			if (md != null)
				accountHistId = md.optString("account_hist_id", "");
		}
		if (accountHistId.isEmpty() && type.startsWith("charge.dispute.")) {
			// data.object is a Dispute: no metadata of ours, only the charge id.
			// Ask Stripe for the charge, which inherited metadata.account_hist_id.
			String chargeId = session.optString("charge", "");
			try {
				accountHistId = new StripeClient(cfg).retrieveChargeAccountHistId(chargeId);
			} catch (IOException e) {
				log.error("Stripe event " + eventId + ": could not read charge " + chargeId + "; asking for retry", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		}
		if (!accountHistId.matches("[0-9]{1,18}")) {
			log.warn("Stripe event " + eventId + " has no usable account_hist reference; ignored");
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		String sessionId = session.optString("id", "");
		String paymentStatus = session.optString("payment_status", "");
		String currency = session.optString("currency", "").toUpperCase();
		long amountMinor = session.optLong("amount_total", -1);
		BigDecimal amount = amountMinor < 0 ? null : StripeClient.fromMinorUnits(amountMinor, currency);

		PaymentSettlement settlement = new PaymentSettlement();
		PaymentSettlement.Result r;

		switch (type) {
		case "checkout.session.completed":
			if ("paid".equals(paymentStatus)) {
				r = settlement.settleSuccess(accountHistId, amount, currency, "stripe_ok", sessionId);
			} else {
				// unpaid = asynchronous method still pending; wait for the
				// async_payment_* event. no_payment_required cannot happen for
				// mode=payment with a positive amount.
				log.info("Stripe session " + sessionId + " completed with payment_status=" + paymentStatus
						+ "; waiting for async result");
				r = PaymentSettlement.Result.ALREADY_SETTLED;
			}
			break;
		case "checkout.session.async_payment_succeeded":
			r = settlement.settleSuccess(accountHistId, amount, currency, "stripe_ok", sessionId);
			break;
		case "checkout.session.async_payment_failed":
			r = settlement.settleFailure(accountHistId, "stripe_no", sessionId);
			break;
		case "checkout.session.expired":
			r = settlement.settleFailure(accountHistId, "stripe_exp", sessionId);
			break;
		// A chargeback: the customer disputed the payment with their bank. Stripe
		// has already pulled the funds, so debit the credited amount back.
		case "charge.dispute.created":
			r = settlement.reverse(accountHistId, "disputed", sessionId);
			break;
		// The dispute was won and Stripe returned the money; re-credit it.
		case "charge.dispute.funds_reinstated":
			r = settlement.reinstate(accountHistId, "disp_back", sessionId, "disputed");
			break;
		// A refund (full or partial). We only issued full-amount top-ups, so treat
		// any refund as a full reversal.
		case "charge.refunded":
			r = settlement.reverse(accountHistId, "refunded", sessionId);
			break;
		default:
			r = PaymentSettlement.Result.ALREADY_SETTLED;
		}

		log.info("Stripe event " + eventId + " " + type + " for account_hist " + accountHistId + " -> " + r);

		if (r == PaymentSettlement.Result.ERROR) {
			// Tell Stripe to retry; the database was the problem, not the event.
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	private static byte[] readBody(HttpServletRequest request) throws IOException {
		try (InputStream in = request.getInputStream()) {
			byte[] buf = new byte[8192];
			java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
			int n;
			while ((n = in.read(buf)) > 0) {
				out.write(buf, 0, n);
				if (out.size() > MAX_BODY)
					return null;
			}
			return out.toByteArray();
		}
	}

}
