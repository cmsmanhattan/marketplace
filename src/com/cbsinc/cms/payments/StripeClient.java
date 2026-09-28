package com.cbsinc.cms.payments;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONObject;

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
 * The few Stripe API calls this application needs, made with the JDK HTTP
 * client rather than the Stripe SDK.
 *
 * <p>
 * Stripe's API is plain HTTPS with form-encoded requests and JSON responses,
 * authenticated with a Basic header carrying the secret key. Two calls cover
 * the whole hosted-checkout flow:
 * </p>
 * <ul>
 * <li>{@code POST /v1/checkout/sessions} - create a Checkout Session and get
 * a URL to send the customer to;</li>
 * <li>{@code GET /v1/checkout/sessions/{id}} - read it back when the customer
 * returns, to confirm {@code payment_status}.</li>
 * </ul>
 * <p>
 * Settlement of the account is driven by the webhook
 * ({@link StripeWebhookServlet}); the return page only shows the customer a
 * result. Both paths are idempotent so it does not matter which arrives
 * first.
 * </p>
 */
public class StripeClient {

	private static final Logger log = Logger.getLogger(StripeClient.class);

	private static final String API_BASE = "https://api.stripe.com";

	/** Pinned so Stripe never silently changes response shapes under us. */
	private static final String API_VERSION = "2024-06-20";

	/** Currencies Stripe charges in whole units rather than hundredths. */
	private static final Set<String> ZERO_DECIMAL = Set.of("BIF", "CLP", "DJF", "GNF", "JPY", "KMF", "KRW", "MGA",
			"PYG", "RWF", "UGX", "VND", "VUV", "XAF", "XOF", "XPF");

	private final String secretKey;
	private final HttpClient http;
	private final Duration requestTimeout;

	public StripeClient(PaymentConfig config) {
		this(config.getStripeSecretKey(), config.getStripeConnectTimeoutMs(), config.getStripeRequestTimeoutMs());
	}

	public StripeClient(String secretKey, int connectTimeoutMs, int requestTimeoutMs) {
		if (secretKey == null || secretKey.isEmpty())
			throw new IllegalStateException("stripe.secret_key is not configured");
		this.secretKey = secretKey;
		this.requestTimeout = Duration.ofMillis(requestTimeoutMs);
		this.http = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(connectTimeoutMs))
				.followRedirects(HttpClient.Redirect.NEVER).build();
	}

	/**
	 * Result of creating a Checkout Session: where to send the customer, and
	 * the id to store against the account_hist row.
	 */
	public static final class CheckoutSession {
		public final String id;
		public final String url;
		public final String paymentStatus;
		public final String status;
		public final String clientReferenceId;
		public final long amountTotalMinor;
		public final String currency;

		CheckoutSession(JSONObject j) {
			id = j.optString("id", "");
			url = j.optString("url", "");
			paymentStatus = j.optString("payment_status", "");
			status = j.optString("status", "");
			clientReferenceId = j.optString("client_reference_id", "");
			amountTotalMinor = j.optLong("amount_total", 0);
			currency = j.optString("currency", "").toUpperCase(Locale.ROOT);
		}

		public boolean isPaid() {
			return "paid".equals(paymentStatus);
		}
	}

	/**
	 * Creates a hosted Checkout Session for a single account top-up.
	 *
	 * @param accountHistId id of the account_hist row created by
	 *                      OperationAmountBean.addMoneyStart(); sent as
	 *                      client_reference_id and metadata so the webhook can
	 *                      find the row without any other state
	 * @param amount        amount in major units ("12.50")
	 * @param currency      ISO 4217 code ("USD")
	 * @param description   line-item text shown on the Stripe page
	 * @param customerEmail pre-fills the email field; may be null
	 * @param successUrl    where Stripe sends the customer after payment;
	 *                      {CHECKOUT_SESSION_ID} in it is replaced by Stripe
	 * @param cancelUrl     where Stripe sends the customer if they back out
	 */
	public CheckoutSession createCheckoutSession(String accountHistId, String amount, String currency,
			String description, String customerEmail, String successUrl, String cancelUrl) throws IOException {

		long minor = toMinorUnits(amount, currency);
		if (minor <= 0)
			throw new IllegalArgumentException("Amount must be positive: " + amount + " " + currency);

		Map<String, String> form = new LinkedHashMap<>();
		form.put("mode", "payment");
		form.put("client_reference_id", accountHistId);
		form.put("success_url", successUrl);
		form.put("cancel_url", cancelUrl);
		form.put("line_items[0][quantity]", "1");
		form.put("line_items[0][price_data][currency]", currency.toLowerCase(Locale.ROOT));
		form.put("line_items[0][price_data][unit_amount]", Long.toString(minor));
		form.put("line_items[0][price_data][product_data][name]",
				description == null || description.isEmpty() ? "Account top-up" : description);
		form.put("metadata[account_hist_id]", accountHistId);
		form.put("payment_intent_data[metadata][account_hist_id]", accountHistId);
		if (customerEmail != null && !customerEmail.isEmpty() && customerEmail.indexOf('@') > 0)
			form.put("customer_email", customerEmail);

		// One account_hist row -> one session. If the request is retried after
		// a network error Stripe returns the same session instead of a second.
		String idempotencyKey = "account_hist-" + accountHistId + "-" + UUID.nameUUIDFromBytes(
				(accountHistId + "|" + minor + "|" + currency).getBytes(StandardCharsets.UTF_8));

		JSONObject j = post("/v1/checkout/sessions", form, idempotencyKey);
		return new CheckoutSession(j);
	}

	/** Reads a Checkout Session back by id. */
	public CheckoutSession retrieveCheckoutSession(String sessionId) throws IOException {
		if (sessionId == null || !sessionId.startsWith("cs_"))
			throw new IllegalArgumentException("Not a Checkout Session id");
		JSONObject j = get("/v1/checkout/sessions/" + URLEncoder.encode(sessionId, StandardCharsets.UTF_8));
		return new CheckoutSession(j);
	}

	/**
	 * Converts "12.50" USD to 1250, or "1200" JPY to 1200. Uses BigDecimal so
	 * 0.1 + 0.2 style float error can never shift a charge by a cent.
	 */
	/**
	 * Reads {@code metadata.account_hist_id} of a Charge. Dispute events carry a
	 * Dispute object which has no metadata of ours, only the charge id; the
	 * charge inherits the metadata we set via payment_intent_data at Checkout.
	 *
	 * @return the id, or an empty string when absent.
	 */
	public String retrieveChargeAccountHistId(String chargeId) throws IOException {
		if (chargeId == null || chargeId.isEmpty())
			return "";
		JSONObject j = get("/v1/charges/" + URLEncoder.encode(chargeId, StandardCharsets.UTF_8));
		JSONObject md = j.optJSONObject("metadata");
		return md == null ? "" : md.optString("account_hist_id", "");
	}

	public static long toMinorUnits(String amount, String currency) {
		BigDecimal value = new BigDecimal(amount.trim());
		int scale = ZERO_DECIMAL.contains(currency.toUpperCase(Locale.ROOT)) ? 0 : 2;
		return value.setScale(scale, RoundingMode.HALF_UP).movePointRight(scale).longValueExact();
	}

	/** Inverse of {@link #toMinorUnits}, for logging and comparisons. */
	public static BigDecimal fromMinorUnits(long minor, String currency) {
		int scale = ZERO_DECIMAL.contains(currency.toUpperCase(Locale.ROOT)) ? 0 : 2;
		return BigDecimal.valueOf(minor).movePointLeft(scale);
	}

	// --- transport --------------------------------------------------------

	private JSONObject post(String path, Map<String, String> form, String idempotencyKey) throws IOException {
		StringBuilder body = new StringBuilder();
		for (Map.Entry<String, String> e : form.entrySet()) {
			if (body.length() > 0)
				body.append('&');
			body.append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)).append('=')
					.append(URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8));
		}
		HttpRequest.Builder b = HttpRequest.newBuilder(URI.create(API_BASE + path)).timeout(requestTimeout)
				.header("Content-Type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(body.toString()));
		if (idempotencyKey != null)
			b.header("Idempotency-Key", idempotencyKey);
		return send(b);
	}

	private JSONObject get(String path) throws IOException {
		return send(HttpRequest.newBuilder(URI.create(API_BASE + path)).timeout(requestTimeout).GET());
	}

	private JSONObject send(HttpRequest.Builder b) throws IOException {
		b.header("Authorization",
				"Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8)));
		b.header("Stripe-Version", API_VERSION);
		b.header("Accept", "application/json");

		HttpResponse<String> r;
		try {
			r = http.send(b.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Interrupted while calling Stripe", e);
		}

		JSONObject j;
		try {
			j = new JSONObject(r.body());
		} catch (RuntimeException e) {
			throw new IOException("Stripe returned HTTP " + r.statusCode() + " with a non-JSON body");
		}

		if (r.statusCode() / 100 != 2) {
			JSONObject err = j.optJSONObject("error");
			String msg = err == null ? r.body() : err.optString("type", "") + ": " + err.optString("message", "");
			// Never log the request body: it can carry customer email.
			log.error("Stripe HTTP " + r.statusCode() + " on " + b.build().uri().getPath() + " - " + msg);
			throw new IOException("Stripe error " + r.statusCode() + ": " + msg);
		}
		return j;
	}

}
