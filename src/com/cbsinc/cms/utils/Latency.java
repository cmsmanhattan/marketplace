package com.cbsinc.cms.utils;

import org.apache.log4j.Logger;

/**
 * Central latency guard. Any database call or controller method that runs
 * longer than the configured threshold is logged at WARN level, the same way
 * an exception is, with the method name, elapsed time and (for SQL) the query.
 * Thresholds come from system properties and need no rebuild to tune:
 *
 *   -Dcms.latency.sql.ms=1000      slow SQL threshold (default 1000 ms)
 *   -Dcms.latency.method.ms=1000   slow controller threshold (default 1000 ms)
 */
public final class Latency {

	private static final Logger log = Logger.getLogger(Latency.class);

	public static final long SQL_THRESHOLD_MS = readMs("cms.latency.sql.ms", 1000L);
	public static final long METHOD_THRESHOLD_MS = readMs("cms.latency.method.ms", 1000L);

	private Latency() {
	}

	private static long readMs(String key, long def) {
		try {
			String v = System.getProperty(key);
			return v == null ? def : Long.parseLong(v.trim());
		} catch (RuntimeException e) {
			return def;
		}
	}

	/** Returns a start mark; pass it back to sql() / method(). */
	public static long start() {
		return System.nanoTime();
	}

	public static long elapsedMs(long startNanos) {
		return (System.nanoTime() - startNanos) / 1000000L;
	}

	/** Report a database call; WARN if it exceeded SQL_THRESHOLD_MS. */
	public static void sql(String where, long startNanos, String query) {
		long ms = elapsedMs(startNanos);
		if (ms >= SQL_THRESHOLD_MS) {
			log.warn("slow sql", new SlowSqlException(where, ms, query));
		} else if (log.isDebugEnabled()) {
			log.debug("sql " + where + " took " + ms + " ms");
		}
	}

	/** Report a method / controller call; WARN if it exceeded METHOD_THRESHOLD_MS. */
	public static void method(String where, long startNanos) {
		long ms = elapsedMs(startNanos);
		if (ms >= METHOD_THRESHOLD_MS) {
			log.warn("slow method", new SlowMethodException(where, ms));
		} else if (log.isDebugEnabled()) {
			log.debug("method " + where + " took " + ms + " ms");
		}
	}

	/** Logged, never thrown: the slow call shows up in the log with a stack trace, like an error. */
	public static class SlowSqlException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public SlowSqlException(String where, long ms, String query) {
			super("SLOW SQL " + ms + " ms (threshold " + SQL_THRESHOLD_MS + " ms) in " + where
					+ " - check indexes / execution plan: " + abbreviate(query));
		}
	}

	public static class SlowMethodException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public SlowMethodException(String where, long ms) {
			super("SLOW METHOD " + ms + " ms (threshold " + METHOD_THRESHOLD_MS + " ms) in " + where);
		}
	}

	private static String abbreviate(String s) {
		if (s == null) {
			return "";
		}
		s = s.replaceAll("\\s+", " ").trim();
		return s.length() > 600 ? s.substring(0, 600) + "..." : s;
	}
}
