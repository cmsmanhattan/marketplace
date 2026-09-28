package com.cbsinc.cms.utils;

public class Validation {

	public static String removeSpecificSymbols( String value)
	{
		value = value.replaceAll("&", "&amp;");
		value = value.replaceAll("'", "&apos;");
		return value ;
	}

	/**
	 * Checks that a string is a plain non-negative integer.
	 *
	 * <p>
	 * Replaces six copies of isNumber()/isFloat() that were scattered across
	 * WebControls, ProductlistFaced, ProductlistAction, ProductlistMarketPlaceAction,
	 * SearchAction and DeployDesignAction. All six shared the same body and the
	 * same defect:
	 * </p>
	 *
	 * <pre>
	 * String IntField = "0123456789.";
	 * for (int i = 0; i &lt; tmp.length(); i++) {
	 *     if (IntField.indexOf(tmp.charAt(i)) == -1) {
	 *         if (tmp.charAt(i) != '-' &amp;&amp; i != 0)
	 *             return false;
	 *     }
	 * }
	 * return true;
	 * </pre>
	 *
	 * <p>
	 * The inner condition is wrong in two independent ways. Because of
	 * <code>i != 0</code> the character at position 0 is never rejected, and
	 * because of <code>charAt(i) != '-'</code> a hyphen is accepted at any
	 * position. So the old check returned true for "", "a", "x123", "1.2.3.4",
	 * "999-----" and - the dangerous one - <b>"1--"</b>.
	 * </p>
	 *
	 * <p>
	 * These methods guard request parameters (catalog_id, offset, creteria1_id
	 * through creteria10_id, dayfrom_id, yearto_id and others) that are then
	 * concatenated straight into SQL, for example in ProductlistFaced:
	 * </p>
	 *
	 * <pre>
	 * WHERE soft.catalog_id = 1--  and soft.tree_id IS NULL and soft.active = true
	 *       and soft.lang_id = 3 and soft.site_id = 42 ORDER BY soft.soft_id DESC
	 * </pre>
	 *
	 * <p>
	 * "--" opens a line comment in PostgreSQL, so everything after it is
	 * discarded: the <code>active</code> filter and, more seriously, the
	 * <code>site_id</code> filter. On a multi-tenant install that returns rows
	 * belonging to other sites.
	 * </p>
	 *
	 * @param value candidate value, may be null
	 * @return true only if value is non-empty and consists solely of ASCII digits
	 */
		/** Escape text for inclusion as XML character data. */
	public static String escapeXml(String value) {
		if (value == null)
			return "";
		StringBuilder b = new StringBuilder(value.length() + 16);
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '&': b.append("&amp;"); break;
			case '<': b.append("&lt;"); break;
			case '>': b.append("&gt;"); break;
			case '"': b.append("&quot;"); break;
			default: b.append(c);
			}
		}
		return b.toString();
	}

/**
	 * Makes a free-text value safe to embed inside a single-quoted SQL string
	 * literal on MySQL/MariaDB and PostgreSQL.
	 *
	 * <p>
	 * This is a stop-gap for the search queries in ProductlistFaced, which
	 * splice the visitor's search text into LIKE '%...%' with string
	 * concatenation. Those queries are long, multi-branch and cannot be
	 * converted to PreparedStatement parameters without a rewrite; until that
	 * happens every path that stores search text goes through this method.
	 * A backslash and a single quote are the only characters that can
	 * terminate a literal in either dialect; both are doubled/escaped. NUL and
	 * other control characters are dropped because MySQL treats an embedded
	 * NUL as the end of the statement in some client paths.
	 * </p>
	 *
	 * <p>
	 * This does not make concatenation a good idea. Parameters remain the
	 * correct fix and this method should disappear when the search queries
	 * are converted.
	 * </p>
	 *
	 * @param value raw text from the request, may be null
	 * @return text safe inside '...' in a MySQL or PostgreSQL statement; never
	 *         null
	 */
	public static String escapeSqlLiteral(String value) {
		if (value == null)
			return "";
		StringBuilder out = new StringBuilder(value.length() + 8);
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '\\') {
				out.append("\\\\");
			} else if (c == '\'') {
				out.append("''");
			} else if (c < 0x20 || c == 0x7f) {
				// drop control characters, including NUL
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static boolean isNonNegativeInteger(String value) {
		if (value == null)
			return false;

		String trimmed = value.trim();
		if (trimmed.isEmpty())
			return false;

		for (int i = 0; i < trimmed.length(); i++) {
			if (trimmed.charAt(i) < '0' || trimmed.charAt(i) > '9')
				return false;
		}

		return true;
	}

	/**
	 * Checks that a string is a plain decimal number, optionally signed.
	 *
	 * <p>
	 * Used for the cost filters (fromcost, tocost), which the old isFloat()
	 * guarded with the same broken loop described above. Accepts "12", "-3",
	 * "0.5", "-0.75"; rejects "", ".", "-", "1.2.3", "1--", "1e5" and anything
	 * containing a character that is not a digit, a single leading sign or a
	 * single decimal point.
	 * </p>
	 *
	 * @param value candidate value, may be null
	 * @return true only if value parses as a simple decimal literal
	 */
	public static boolean isDecimal(String value) {
		if (value == null)
			return false;

		String trimmed = value.trim();
		if (trimmed.isEmpty())
			return false;

		int start = 0;
		if (trimmed.charAt(0) == '-' || trimmed.charAt(0) == '+')
			start = 1;

		// A sign on its own is not a number.
		if (start == trimmed.length())
			return false;

		boolean seenDot = false;
		boolean seenDigit = false;

		for (int i = start; i < trimmed.length(); i++) {
			char c = trimmed.charAt(i);
			if (c == '.') {
				// Only one decimal point is allowed; "1.2.3" is rejected.
				if (seenDot)
					return false;
				seenDot = true;
			} else if (c >= '0' && c <= '9') {
				seenDigit = true;
			} else {
				return false;
			}
		}

		return seenDigit;
	}


	/**
	 * Whitelist for the criteria table names (creteria1 .. creteria10).
	 *
	 * <p>These names cannot be bound as SQL parameters, so they are concatenated
	 * into the statement. Earlier passes claimed the value was whitelisted but the
	 * check was never actually implemented anywhere, so the guarantee is enforced
	 * here, at the point of use, rather than trusted from the caller.</p>
	 *
	 * @param tableName candidate table name, may be null
	 * @return the trimmed, lower-cased table name when it is one of creteria1..10
	 * @throws IllegalArgumentException for anything else
	 */
	public static String requireCriteriaTable(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("criteria table name is null");
		}
		String candidate = tableName.trim().toLowerCase(java.util.Locale.ROOT);
		for (int i = 1; i <= 10; i++) {
			if (candidate.equals("creteria" + i)) {
				return candidate;
			}
		}
		throw new IllegalArgumentException("not a criteria table: " + tableName);
	}

	/**
	 * Sanitises a numeric identifier that will be concatenated into SQL.
	 *
	 * <p>site_id and catalog_id arrive straight from request parameters and are
	 * spliced into WHERE clauses as bare, unquoted numbers in more than ninety
	 * places (" ... site_id = " + bean.getSite_id() + " and ..."). Because they are
	 * unquoted, anything non-numeric is injected directly into the statement.
	 * Rewriting all of those call sites to bind parameters is the proper fix; until
	 * then the value is constrained here, at the single point every path sets it.</p>
	 *
	 * <p>An invalid value becomes "-1" rather than an exception or an empty string:
	 * "-1" keeps the generated SQL syntactically valid and simply matches no rows,
	 * so a malformed request fails closed instead of erroring or widening the query.</p>
	 *
	 * @param value candidate identifier, may be null
	 * @return the trimmed value when it is an optionally-signed run of digits,
	 *         "" when it was null or blank, otherwise "-1"
	 */
	public static String requireNumericId(String value) {
		if (value == null) {
			return "";
		}
		String trimmed = value.trim();
		if (trimmed.length() == 0) {
			return "";
		}
		if (trimmed.matches("-?\\d{1,18}")) {
			return trimmed;
		}
		// Deliberately silent: this class has no dependencies (no log4j), which is what
		// lets ValidationTest run without the web classpath. The caller sees "-1".
		return "-1";
	}
}
