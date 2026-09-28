package com.cbsinc.cms.payments;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
 * Verifies the {@code Stripe-Signature} header on a webhook delivery.
 *
 * <p>
 * The header looks like {@code t=1712345678,v1=hex,v1=hex}. The signature is
 * HMAC-SHA256 over {@code "<t>.<raw body>"} keyed with the endpoint's signing
 * secret. Any one matching {@code v1} value is accepted (Stripe sends several
 * while a secret is being rotated). The timestamp must be within the
 * tolerance window so a captured delivery cannot be replayed later.
 * </p>
 *
 * <p>
 * This is the only line of defence for the webhook: without it anyone who
 * knows the URL can POST a fabricated {@code checkout.session.completed} and
 * credit an account. It must not be bypassed, and the secret must be set.
 * </p>
 */
public final class StripeWebhookVerifier {

	private StripeWebhookVerifier() {
	}

	/**
	 * @param payload      the raw request body, exactly as received
	 * @param header       value of the Stripe-Signature header
	 * @param secret       webhook signing secret (whsec_...)
	 * @param toleranceSec maximum age of the timestamp, seconds
	 * @param nowSec       current time, seconds since the epoch
	 * @return true only if the signature is valid and fresh
	 */
	public static boolean verify(byte[] payload, String header, String secret, long toleranceSec, long nowSec) {
		if (payload == null || header == null || secret == null || secret.isEmpty())
			return false;

		long ts = -1;
		java.util.List<String> sigs = new java.util.ArrayList<>();
		for (String part : header.split(",")) {
			int eq = part.indexOf('=');
			if (eq <= 0)
				continue;
			String k = part.substring(0, eq).trim();
			String v = part.substring(eq + 1).trim();
			if (k.equals("t")) {
				try {
					ts = Long.parseLong(v);
				} catch (NumberFormatException e) {
					return false;
				}
			} else if (k.equals("v1")) {
				sigs.add(v);
			}
		}
		if (ts < 0 || sigs.isEmpty())
			return false;
		if (Math.abs(nowSec - ts) > toleranceSec)
			return false;

		byte[] expected;
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
			mac.update(Long.toString(ts).getBytes(StandardCharsets.US_ASCII));
			mac.update((byte) '.');
			mac.update(payload);
			expected = mac.doFinal();
		} catch (GeneralSecurityException e) {
			return false;
		}

		for (String s : sigs) {
			byte[] given = hexToBytes(s);
			// Constant-time comparison; a plain equals() leaks byte-by-byte.
			if (given != null && MessageDigest.isEqual(expected, given))
				return true;
		}
		return false;
	}

	static byte[] hexToBytes(String hex) {
		if (hex == null || hex.length() % 2 != 0)
			return null;
		byte[] out = new byte[hex.length() / 2];
		for (int i = 0; i < out.length; i++) {
			int hi = Character.digit(hex.charAt(2 * i), 16);
			int lo = Character.digit(hex.charAt(2 * i + 1), 16);
			if (hi < 0 || lo < 0)
				return null;
			out[i] = (byte) ((hi << 4) | lo);
		}
		return out;
	}

}
