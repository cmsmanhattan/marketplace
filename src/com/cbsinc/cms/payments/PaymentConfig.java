package com.cbsinc.cms.payments;

import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

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
 * Reads payment.properties and lets each value be overridden by a system
 * property or an environment variable, so secrets never have to live in the
 * WAR.
 *
 * Resolution order for key {@code stripe.secret_key}:
 * <ol>
 * <li>system property {@code stripe.secret_key}</li>
 * <li>environment variable {@code STRIPE_SECRET_KEY}</li>
 * <li>payment.properties</li>
 * <li>the default passed by the caller</li>
 * </ol>
 */
public final class PaymentConfig {

	private static final Logger log = Logger.getLogger(PaymentConfig.class);

	private static volatile PaymentConfig instance;

	private final ResourceBundle bundle;

	private PaymentConfig() {
		ResourceBundle b = null;
		try {
			b = PropertyResourceBundle.getBundle("payment");
		} catch (MissingResourceException e) {
			log.warn("payment.properties not found on the classpath; Stripe is disabled and "
					+ "the legacy gateway will be used for every payment.");
		}
		bundle = b;
	}

	public static PaymentConfig getInstance() {
		PaymentConfig local = instance;
		if (local == null) {
			synchronized (PaymentConfig.class) {
				local = instance;
				if (local == null)
					instance = local = new PaymentConfig();
			}
		}
		return local;
	}

	/** Drops the cached instance so the next call re-reads the properties. */
	public static void reload() {
		synchronized (PaymentConfig.class) {
			instance = null;
		}
	}

	public String get(String key, String def) {
		String v = System.getProperty(key);
		if (v != null && !v.isEmpty())
			return v.trim();

		v = System.getenv(key.toUpperCase(Locale.ROOT).replace('.', '_'));
		if (v != null && !v.isEmpty())
			return v.trim();

		if (bundle != null && bundle.containsKey(key)) {
			v = bundle.getString(key);
			if (v != null && !v.trim().isEmpty())
				return v.trim();
		}
		return def;
	}

	public boolean getBoolean(String key, boolean def) {
		String v = get(key, null);
		return v == null ? def : "true".equalsIgnoreCase(v) || "1".equals(v) || "yes".equalsIgnoreCase(v);
	}

	public int getInt(String key, int def) {
		String v = get(key, null);
		if (v == null)
			return def;
		try {
			return Integer.parseInt(v);
		} catch (NumberFormatException e) {
			log.warn("payment config: " + key + "=" + v + " is not an integer; using " + def);
			return def;
		}
	}

	// --- typed accessors -------------------------------------------------

	public PaymentChannel getPrimaryChannel() {
		return "legacy".equalsIgnoreCase(get("payment.primary", "stripe")) ? PaymentChannel.LEGACY
				: PaymentChannel.STRIPE;
	}

	public boolean isFallbackToLegacy() {
		return getBoolean("payment.fallback_to_legacy", true);
	}

	public int getCircuitFailures() {
		return getInt("payment.circuit_failures", 3);
	}

	public int getCircuitOpenSeconds() {
		return getInt("payment.circuit_open_seconds", 120);
	}

	public boolean isStripeEnabled() {
		return getBoolean("stripe.enabled", false) && getStripeSecretKey() != null;
	}

	public String getStripeSecretKey() {
		return get("stripe.secret_key", null);
	}

	public String getStripeWebhookSecret() {
		return get("stripe.webhook_secret", null);
	}

	public String getStripePublishableKey() {
		return get("stripe.publishable_key", "");
	}

	public int getStripeConnectTimeoutMs() {
		return getInt("stripe.connect_timeout_ms", 5000);
	}

	public int getStripeRequestTimeoutMs() {
		return getInt("stripe.request_timeout_ms", 15000);
	}

	public int getStripeWebhookToleranceSeconds() {
		return getInt("stripe.webhook_tolerance_seconds", 300);
	}

	public String getStripePublicBaseUrl() {
		String v = get("stripe.public_base_url", "");
		while (v.endsWith("/"))
			v = v.substring(0, v.length() - 1);
		return v;
	}

	public Set<String> getStripeCurrencies() {
		Set<String> out = new HashSet<>();
		for (String c : get("stripe.currencies", "USD,EUR,GBP").split(",")) {
			c = c.trim().toUpperCase(Locale.ROOT);
			if (!c.isEmpty())
				out.add(c);
		}
		return out;
	}

}
