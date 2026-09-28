package com.cbsinc.cms.utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Password hashing utility.
 *
 * <p>
 * Passwords must never be stored in clear text. This helper produces a salted
 * PBKDF2 hash using only the standard JDK (no external dependency, which the
 * build cannot currently add or verify offline). The stored string is
 * self-describing:
 * </p>
 *
 * <pre>pbkdf2$&lt;iterations&gt;$&lt;base64 salt&gt;$&lt;base64 hash&gt;</pre>
 *
 * <p>
 * {@link #matches(String, String)} also accepts a legacy clear-text value so
 * that existing rows keep working during migration; callers should re-hash and
 * store the result on the next successful login (see
 * {@link #looksHashed(String)}).
 * </p>
 */
public final class PasswordHash {

	private static final String PREFIX = "pbkdf2";
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int ITERATIONS = 120000;
	private static final int SALT_BYTES = 16;
	private static final int KEY_BITS = 256;
	private static final SecureRandom RANDOM = new SecureRandom();

	private PasswordHash() {
	}

	/**
	 * @return a self-describing salted PBKDF2 hash of the given clear-text
	 *         password, or an empty string when the input is null/empty.
	 */
	public static String hash(String plain) {
		if (plain == null || plain.length() == 0)
			return "";
		byte[] salt = new byte[SALT_BYTES];
		RANDOM.nextBytes(salt);
		byte[] dk = pbkdf2(plain.toCharArray(), salt, ITERATIONS);
		return PREFIX + "$" + ITERATIONS + "$"
				+ Base64.getEncoder().encodeToString(salt) + "$"
				+ Base64.getEncoder().encodeToString(dk);
	}

	/**
	 * @return true when the stored value is a hash produced by {@link #hash}.
	 *         A false result means the row still holds a legacy clear-text
	 *         password and should be upgraded.
	 */
	public static boolean looksHashed(String stored) {
		return stored != null && stored.startsWith(PREFIX + "$");
	}

	/**
	 * Verifies a clear-text password against a stored value. Handles both a
	 * PBKDF2 hash and a legacy clear-text password (constant-time compare in
	 * both cases).
	 */
	public static boolean matches(String plain, String stored) {
		if (plain == null || stored == null)
			return false;
		if (!looksHashed(stored))
			return constantTimeEquals(plain.getBytes(), stored.getBytes());
		try {
			String[] parts = stored.split("\\$");
			if (parts.length != 4)
				return false;
			int iterations = Integer.parseInt(parts[1]);
			byte[] salt = Base64.getDecoder().decode(parts[2]);
			byte[] expected = Base64.getDecoder().decode(parts[3]);
			byte[] actual = pbkdf2(plain.toCharArray(), salt, iterations);
			return constantTimeEquals(expected, actual);
		} catch (RuntimeException ex) {
			return false;
		}
	}

	/**
	 * @return a random 12-character temporary password (letters and digits),
	 *         used by the "forgot password" flow.
	 */
	public static String generateTemporary() {
		final String alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
		StringBuilder sb = new StringBuilder(12);
		for (int i = 0; i < 12; i++)
			sb.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
		return sb.toString();
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
		try {
			KeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_BITS);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			return factory.generateSecret(spec).getEncoded();
		} catch (java.security.GeneralSecurityException ex) {
			throw new IllegalStateException("PBKDF2 not available", ex);
		}
	}

	private static boolean constantTimeEquals(byte[] a, byte[] b) {
		if (a.length != b.length)
			return false;
		int result = 0;
		for (int i = 0; i < a.length; i++)
			result |= a[i] ^ b[i];
		return result == 0;
	}

}
