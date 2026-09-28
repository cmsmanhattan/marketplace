package com.cbsinc.cms.payments;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;

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
 * Decides, for one top-up, whether the customer goes to Stripe Checkout or to
 * the legacy bank form, and creates the Stripe session when it is Stripe.
 *
 * <p>
 * Order of decisions:
 * </p>
 * <ol>
 * <li>{@code payment.primary = legacy} → legacy, always.</li>
 * <li>Stripe disabled or no secret key → legacy.</li>
 * <li>Customer explicitly chose the legacy pay system (request parameter
 * {@code channel=legacy}) → legacy.</li>
 * <li>Currency not in {@code stripe.currencies} → legacy.</li>
 * <li>Circuit breaker open (recent consecutive Stripe failures) → legacy.</li>
 * <li>Create the Checkout Session. On any exception: count the failure,
 * open the circuit if the threshold is reached, and → legacy if
 * {@code payment.fallback_to_legacy} is on; otherwise the failure is
 * surfaced.</li>
 * </ol>
 *
 * <p>
 * The customer never sees the fallback happen: the same Pay.jsp renders,
 * only with the bank form instead of a redirect.
 * </p>
 */
public final class PaymentRouter {

	private static final Logger log = Logger.getLogger(PaymentRouter.class);

	private static final PaymentRouter INSTANCE = new PaymentRouter();

	private final AtomicInteger consecutiveFailures = new AtomicInteger();
	private final AtomicLong circuitOpenUntilMs = new AtomicLong();

	private PaymentRouter() {
	}

	public static PaymentRouter getInstance() {
		return INSTANCE;
	}

	/** What the caller should do with the customer. */
	public static final class Decision {
		public final PaymentChannel channel;
		/** Stripe Checkout URL when channel is STRIPE, otherwise null. */
		public final String redirectUrl;
		/** Stripe session id when channel is STRIPE, otherwise null. */
		public final String reference;
		/** Human-readable reason, for logs and the template. */
		public final String reason;

		Decision(PaymentChannel channel, String redirectUrl, String reference, String reason) {
			this.channel = channel;
			this.redirectUrl = redirectUrl;
			this.reference = reference;
			this.reason = reason;
		}

		public boolean isStripe() {
			return channel == PaymentChannel.STRIPE;
		}
	}

	/**
	 * @param request       current request, used to build return URLs and read
	 *                      the optional {@code channel} parameter
	 * @param accountHistId the pending account_hist row
	 * @param amount        major units, e.g. "12.50"
	 * @param currencyCode  ISO 4217, e.g. "USD"
	 * @param description   line-item text
	 * @param customerEmail may be null
	 */
	public Decision route(HttpServletRequest request, String accountHistId, String amount, String currencyCode,
			String description, String customerEmail) {

		PaymentConfig cfg = PaymentConfig.getInstance();

		if (cfg.getPrimaryChannel() == PaymentChannel.LEGACY)
			return legacy("payment.primary=legacy");
		if (!cfg.isStripeEnabled())
			return legacy("stripe disabled or no secret key");
		if ("legacy".equalsIgnoreCase(request.getParameter("channel")))
			return legacy("customer chose legacy");

		String cur = currencyCode == null ? "" : currencyCode.trim().toUpperCase(Locale.ROOT);
		if (!cfg.getStripeCurrencies().contains(cur))
			return legacy("currency " + cur + " not enabled for Stripe");

		long now = System.currentTimeMillis();
		if (now < circuitOpenUntilMs.get())
			return legacy("stripe circuit open for another " + (circuitOpenUntilMs.get() - now) / 1000 + "s");

		String base = publicBaseUrl(request, cfg);
		String successUrl = base + "/stripe/return?result=success&account_hist_id=" + accountHistId
				+ "&session_id={CHECKOUT_SESSION_ID}";
		String cancelUrl = base + "/stripe/return?result=cancel&account_hist_id=" + accountHistId;

		try {
			StripeClient client = new StripeClient(cfg);
			StripeClient.CheckoutSession s = client.createCheckoutSession(accountHistId, amount, cur, description,
					customerEmail, successUrl, cancelUrl);
			if (s.url == null || s.url.isEmpty())
				throw new IllegalStateException("Stripe returned a session without a url");

			consecutiveFailures.set(0);
			new PaymentSettlement().markChannel(accountHistId, PaymentChannel.STRIPE, s.id);
			return new Decision(PaymentChannel.STRIPE, s.url, s.id, "stripe checkout " + s.id);
		} catch (Exception e) {
			int n = consecutiveFailures.incrementAndGet();
			log.error("Stripe checkout creation failed for account_hist " + accountHistId + " (failure " + n + ")",
					e);
			if (n >= cfg.getCircuitFailures()) {
				circuitOpenUntilMs.set(System.currentTimeMillis() + cfg.getCircuitOpenSeconds() * 1000L);
				log.error("Stripe circuit opened for " + cfg.getCircuitOpenSeconds()
						+ "s; routing all payments to the legacy gateway");
			}
			if (cfg.isFallbackToLegacy())
				return legacy("stripe error: " + e.getMessage());
			throw new IllegalStateException("Stripe checkout is unavailable and fallback is disabled", e);
		}
	}

	private static Decision legacy(String reason) {
		log.info("Payment routed to legacy gateway: " + reason);
		return new Decision(PaymentChannel.LEGACY, null, null, reason);
	}

	/** Manual reset, for an admin/health endpoint. */
	public void resetCircuit() {
		consecutiveFailures.set(0);
		circuitOpenUntilMs.set(0);
	}

	public boolean isCircuitOpen() {
		return System.currentTimeMillis() < circuitOpenUntilMs.get();
	}

	/**
	 * Base URL Stripe should send the customer back to. Behind a reverse proxy
	 * the request's scheme and host are the proxy's internal ones, so an
	 * explicit stripe.public_base_url wins.
	 */
	static String publicBaseUrl(HttpServletRequest request, PaymentConfig cfg) {
		String configured = cfg.getStripePublicBaseUrl();
		if (!configured.isEmpty())
			return configured;

		String scheme = request.getHeader("X-Forwarded-Proto");
		if (scheme == null || scheme.isEmpty())
			scheme = request.getScheme();
		String host = request.getHeader("X-Forwarded-Host");
		if (host == null || host.isEmpty()) {
			host = request.getServerName();
			int port = request.getServerPort();
			boolean defaultPort = ("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443);
			if (!defaultPort)
				host = host + ":" + port;
		}
		String ctx = request.getContextPath() == null ? "" : request.getContextPath();
		return scheme + "://" + host + ctx;
	}

}
