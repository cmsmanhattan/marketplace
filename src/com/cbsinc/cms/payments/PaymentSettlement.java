package com.cbsinc.cms.payments;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cbsinc.cms.QueryManager;

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
 * Turns a pending account_hist row into money on the account.
 *
 * <p>
 * The legacy gateway settles through {@code CheckPaymentResult.end_addmoney};
 * Stripe settles through here. Both work on the same rows:
 * </p>
 * <ul>
 * <li>{@code account_hist}: created by {@code OperationAmountBean.addMoneyStart}
 * with {@code complete = false, active = true}; settlement flips both and
 * records the result code;</li>
 * <li>{@code account}: the running balance, {@code amount += add_amount};</li>
 * <li>{@code orders}: if the top-up was made for an order, its paystatus is
 * set to SUCCESS.</li>
 * </ul>
 *
 * <p>
 * Everything is idempotent and row-locked: Stripe delivers each webhook at
 * least once, the return page may also try to settle, and the operator may
 * replay events from the dashboard. Only the first caller to lock an
 * incomplete row credits the account; every later call sees
 * {@code complete = true} and returns {@link Result#ALREADY_SETTLED}.
 * </p>
 *
 * <p>
 * Note on the legacy routine: {@code end_addmoney} computes
 * {@code total_amount = amount + add_amount * rate} where {@code amount} is
 * a local initialised to 0 and {@code rate} is stored as 0 at creation, so
 * it writes {@code account.amount = 0} on every successful payment. That is
 * not reproduced here; the balance is read under lock and incremented.
 * </p>
 */
public class PaymentSettlement {

	private static final Logger log = Logger.getLogger(PaymentSettlement.class);

	/** Mirrors com.cbsinc.cms.PayStatus, which is package-private. */
	static final long PAYSTATUS_SUCCESS = 2;
	static final long PAYSTATUS_UNSUCCESS = 3;

	public enum Result {
		SETTLED, ALREADY_SETTLED, NOT_FOUND, MISMATCH, FAILED_RECORDED, REVERSED, ALREADY_REVERSED, NOT_SETTLED, ERROR
	}

	/** Whether account_hist has the optional pay_channel / pay_ref columns. */
	private static volatile Boolean hasChannelColumns;

	/**
	 * Records which channel a pending top-up was sent through and the
	 * provider's reference (Stripe session id). Called right after the
	 * Checkout Session is created; never fails the checkout if it cannot
	 * write.
	 */
	public void markChannel(String accountHistId, PaymentChannel channel, String reference) {
		QueryManager qm = new QueryManager();
		try {
			Connection c = qm.getCurrentConnection();
			if (!channelColumnsPresent(c))
				return;
			try (PreparedStatement ps = c
					.prepareStatement("UPDATE account_hist SET pay_channel = ?, pay_ref = ? WHERE id = ?")) {
				ps.setString(1, channel.code());
				ps.setString(2, truncate(reference, 80));
				ps.setLong(3, Long.parseLong(accountHistId));
				ps.executeUpdate();
			}
			qm.commit();
		} catch (Exception e) {
			log.warn("Could not record payment channel for account_hist " + accountHistId, e);
			qm.rollback();
		} finally {
			qm.close();
		}
	}

	/**
	 * Credits the account for a paid top-up.
	 *
	 * @param accountHistId  the pending row
	 * @param paidAmount     amount the provider says was paid, major units;
	 *                       null to skip the check
	 * @param paidCurrency   ISO code the provider charged in; null to skip
	 * @param resultCode     stored in account_hist.rezult_cd (max 10 chars)
	 * @param reference      provider reference for the audit trail
	 */
	public Result settleSuccess(String accountHistId, BigDecimal paidAmount, String paidCurrency, String resultCode,
			String reference) {
		QueryManager qm = new QueryManager();
		try {
			Connection c = qm.getCurrentConnection();
			qm.beginTransaction();

			Pending p = lockPending(c, accountHistId);
			if (p == null) {
				qm.rollback();
				return Result.NOT_FOUND;
			}
			if (p.complete) {
				qm.rollback();
				log.info("account_hist " + accountHistId + " already settled; ignoring duplicate " + reference);
				return Result.ALREADY_SETTLED;
			}

			if (paidAmount != null && paidAmount.compareTo(BigDecimal.valueOf(p.addAmount)) != 0
					|| paidCurrency != null && !paidCurrency.equalsIgnoreCase(p.currencyCode)) {
				// The provider charged something other than what we asked for.
				// Do not credit; leave the row for a human.
				log.error("account_hist " + accountHistId + " expected " + p.addAmount + " " + p.currencyCode
						+ " but provider reports " + paidAmount + " " + paidCurrency + " (" + reference + ")");
				writeResult(c, accountHistId, "mismatch", reference, false);
				qm.commit();
				return Result.MISMATCH;
			}

			double oldBalance = lockBalance(c, p.userId);
			double newBalance = oldBalance + p.addAmount;

			writeResult(c, accountHistId, resultCode, reference, true);

			try (PreparedStatement ps = c
					.prepareStatement("UPDATE account SET amount = ?, date_input = ? WHERE user_id = ?")) {
				ps.setDouble(1, newBalance);
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				ps.setLong(3, p.userId);
				if (ps.executeUpdate() == 0)
					throw new SQLException("No account row for user " + p.userId);
			}

			if (p.orderId > 0) {
				try (PreparedStatement ps = c
						.prepareStatement("UPDATE orders SET paystatus_id = ? WHERE order_id = ?")) {
					ps.setLong(1, PAYSTATUS_SUCCESS);
					ps.setLong(2, p.orderId);
					ps.executeUpdate();
				}
			}

			qm.commit();
			if (p.orderId > 0) {
				try { // in-app notice for both parties of the order
					new com.cbsinc.cms.faceds.NotificationsFaced().notifyOrderChanged(String.valueOf(p.orderId), "0", 0);
				} catch (Exception ex) {
					log.warn("notification after settlement failed", ex);
				}
			}
			log.info("Settled account_hist " + accountHistId + ": user " + p.userId + " +" + p.addAmount + " "
					+ p.currencyCode + " (balance " + oldBalance + " -> " + newBalance + ") via " + reference);
			return Result.SETTLED;
		} catch (Exception e) {
			log.error("Settlement of account_hist " + accountHistId + " failed", e);
			qm.rollback();
			return Result.ERROR;
		} finally {
			qm.close();
		}
	}

	/**
	 * Records a failed or abandoned payment. The account is not touched; the
	 * row is closed so the legacy poller does not keep asking the bank about
	 * it, and the order is marked unpaid.
	 */
	public Result settleFailure(String accountHistId, String resultCode, String reference) {
		QueryManager qm = new QueryManager();
		try {
			Connection c = qm.getCurrentConnection();
			qm.beginTransaction();
			Pending p = lockPending(c, accountHistId);
			if (p == null) {
				qm.rollback();
				return Result.NOT_FOUND;
			}
			if (p.complete) {
				qm.rollback();
				return Result.ALREADY_SETTLED;
			}
			writeResult(c, accountHistId, resultCode, reference, false);
			if (p.orderId > 0) {
				try (PreparedStatement ps = c
						.prepareStatement("UPDATE orders SET paystatus_id = ? WHERE order_id = ?")) {
					ps.setLong(1, PAYSTATUS_UNSUCCESS);
					ps.setLong(2, p.orderId);
					ps.executeUpdate();
				}
			}
			qm.commit();
			return Result.FAILED_RECORDED;
		} catch (Exception e) {
			log.error("Recording failure of account_hist " + accountHistId + " failed", e);
			qm.rollback();
			return Result.ERROR;
		} finally {
			qm.close();
		}
	}

	/**
	 * Reverses a previously settled top-up after a refund or a chargeback
	 * (Stripe {@code charge.refunded} / {@code charge.dispute.created}). Unlike
	 * {@link #settleSuccess}/{@link #settleFailure}, which act on a still-pending
	 * row, this operates on a row that is already {@code complete = true}: it
	 * debits the credited amount back from the current balance and marks the row
	 * so a repeated callback does not debit twice. The account is allowed to go
	 * negative — the money genuinely left the platform.
	 *
	 * @param resultCode short marker stored in rezult_cd, also used as the
	 *                   idempotency guard (e.g. "refunded", "disputed").
	 */
	public Result reverse(String accountHistId, String resultCode, String reference) {
		return adjust(accountHistId, resultCode, reference, -1, null);
	}

	/**
	 * Re-credits a top-up that was debited by {@link #reverse} after the dispute
	 * was won ({@code charge.dispute.funds_reinstated}). Only applies when the
	 * row currently carries the {@code expectedPrev} marker, so a stray event
	 * cannot credit money that was never taken.
	 */
	public Result reinstate(String accountHistId, String resultCode, String reference, String expectedPrev) {
		return adjust(accountHistId, resultCode, reference, +1, expectedPrev);
	}

	private Result adjust(String accountHistId, String resultCode, String reference, int sign,
			String expectedPrev) {
		if (accountHistId == null || !accountHistId.matches("[0-9]{1,18}"))
			return Result.NOT_FOUND;
		String marker = truncate(resultCode, 10);
		QueryManager qm = new QueryManager();
		try {
			Connection c = qm.getCurrentConnection();
			qm.beginTransaction();
			String sql = "SELECT h.user_id, h.add_amount, h.complete, h.rezult_cd, h.order_id"
					+ " FROM account_hist h WHERE h.id = ? FOR UPDATE";
			long userId;
			double addAmount;
			long orderId;
			boolean complete;
			String rezult;
			try (PreparedStatement ps = c.prepareStatement(sql)) {
				ps.setLong(1, Long.parseLong(accountHistId));
				try (ResultSet rs = ps.executeQuery()) {
					if (!rs.next()) {
						qm.rollback();
						return Result.NOT_FOUND;
					}
					userId = rs.getLong(1);
					addAmount = rs.getDouble(2);
					complete = rs.getBoolean(3);
					rezult = rs.getString(4);
					orderId = rs.getLong(5);
				}
			}
			if (!complete) {
				// The credit never landed; nothing to take back. Close it as failed.
				qm.rollback();
				return settleFailure(accountHistId, marker, reference);
			}
			String prev = rezult == null ? "" : rezult.trim();
			if (marker.equals(prev)) {
				qm.rollback();
				log.info("account_hist " + accountHistId + " already at state " + marker + "; ignoring " + reference);
				return Result.ALREADY_REVERSED;
			}
			if (expectedPrev != null && !expectedPrev.equals(prev)) {
				qm.rollback();
				log.warn("account_hist " + accountHistId + " is in state '" + prev + "', expected '" + expectedPrev
						+ "'; not applying " + marker + " (" + reference + ")");
				return Result.NOT_SETTLED;
			}
			double oldBalance = lockBalance(c, userId);
			double newBalance = oldBalance + sign * addAmount;
			try (PreparedStatement ps = c
					.prepareStatement("UPDATE account SET amount = ?, date_input = ? WHERE user_id = ?")) {
				ps.setDouble(1, newBalance);
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				ps.setLong(3, userId);
				if (ps.executeUpdate() == 0)
					throw new SQLException("No account row for user " + userId);
			}
			boolean ext = channelColumnsPresent(c);
			String upd = "UPDATE account_hist SET rezult_cd = ?, date_end = ?"
					+ (ext ? ", pay_ref = ?" : "") + " WHERE id = ?";
			try (PreparedStatement ps = c.prepareStatement(upd)) {
				int i = 1;
				ps.setString(i++, marker);
				ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
				if (ext)
					ps.setString(i++, truncate(reference, 80));
				ps.setLong(i, Long.parseLong(accountHistId));
				ps.executeUpdate();
			}
			if (orderId > 0) {
				try (PreparedStatement ps = c
						.prepareStatement("UPDATE orders SET paystatus_id = ? WHERE order_id = ?")) {
					ps.setLong(1, sign < 0 ? PAYSTATUS_UNSUCCESS : PAYSTATUS_SUCCESS);
					ps.setLong(2, orderId);
					ps.executeUpdate();
				}
			}
			qm.commit();
			if (orderId > 0) {
				try {
					new com.cbsinc.cms.faceds.NotificationsFaced().notifyOrderChanged(String.valueOf(orderId), "0", 0);
				} catch (Exception ex) {
					log.warn("notification after reversal failed", ex);
				}
			}
			log.warn((sign < 0 ? "Reversed" : "Reinstated") + " account_hist " + accountHistId + " (" + marker + "): user " + userId + (sign < 0 ? " -" : " +")
					+ addAmount + " (balance " + oldBalance + " -> " + newBalance + ") via " + reference);
			return Result.REVERSED;
		} catch (Exception e) {
			log.error("Reversal of account_hist " + accountHistId + " failed", e);
			qm.rollback();
			return Result.ERROR;
		} finally {
			qm.close();
		}
	}

	// --- internals ---------------------------------------------------------

	private static final class Pending {
		long userId;
		long orderId;
		double addAmount;
		String currencyCode;
		boolean complete;
	}

	private Pending lockPending(Connection c, String accountHistId) throws SQLException {
		String sql = "SELECT h.user_id, h.order_id, h.add_amount, h.complete, cu.currency_cd"
				+ " FROM account_hist h LEFT JOIN currency cu ON cu.currency_id = h.currency_id_add"
				+ " WHERE h.id = ? FOR UPDATE";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setLong(1, Long.parseLong(accountHistId));
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					return null;
				Pending p = new Pending();
				p.userId = rs.getLong(1);
				p.orderId = rs.getLong(2);
				p.addAmount = rs.getDouble(3);
				p.complete = rs.getBoolean(4);
				String cd = rs.getString(5);
				p.currencyCode = cd == null ? "" : cd.trim().toUpperCase(Locale.ROOT);
				return p;
			}
		}
	}

	private double lockBalance(Connection c, long userId) throws SQLException {
		try (PreparedStatement ps = c.prepareStatement("SELECT amount FROM account WHERE user_id = ? FOR UPDATE")) {
			ps.setLong(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					throw new SQLException("No account row for user " + userId);
				return rs.getDouble(1);
			}
		}
	}

	private void writeResult(Connection c, String accountHistId, String resultCode, String reference,
			boolean success) throws SQLException {
		boolean ext = channelColumnsPresent(c);
		String sql = "UPDATE account_hist SET complete = ?, active = ?, rezult_cd = ?, date_end = ?"
				+ (ext ? ", pay_ref = ?" : "") + " WHERE id = ?";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			int i = 1;
			ps.setBoolean(i++, true);
			ps.setBoolean(i++, false);
			ps.setString(i++, truncate(resultCode, 10));
			ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			if (ext)
				ps.setString(i++, truncate(reference, 80));
			ps.setLong(i, Long.parseLong(accountHistId));
			ps.executeUpdate();
		}
	}

	/**
	 * The two audit columns are added by sql/stripe_migration.sql. If the
	 * migration has not been run, settlement still works; only the audit
	 * trail is missing, and this is logged once.
	 */
	private static boolean channelColumnsPresent(Connection c) {
		Boolean v = hasChannelColumns;
		if (v == null) {
			boolean found = false;
			try {
				DatabaseMetaData md = c.getMetaData();
				try (ResultSet rs = md.getColumns(c.getCatalog(), null, "account_hist", "pay_ref")) {
					found = rs.next();
				}
				if (!found) {
					try (ResultSet rs = md.getColumns(c.getCatalog(), null, "ACCOUNT_HIST", "PAY_REF")) {
						found = rs.next();
					}
				}
			} catch (SQLException e) {
				log.warn("Could not inspect account_hist columns", e);
			}
			if (!found)
				log.warn("account_hist has no pay_channel/pay_ref columns; run sql/stripe_migration.sql "
						+ "to keep the Stripe reference on each payment.");
			hasChannelColumns = v = found;
		}
		return v;
	}

	private static String truncate(String s, int max) {
		if (s == null)
			return null;
		return s.length() <= max ? s : s.substring(0, max);
	}

}
