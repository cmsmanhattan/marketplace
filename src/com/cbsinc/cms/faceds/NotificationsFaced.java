package com.cbsinc.cms.faceds;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CurrencyHash;
import com.cbsinc.cms.NotificationsBean;
import com.cbsinc.cms.OrderListBean;
import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.controllers.SpecialCatalog;

/**
 * Business logic of event notifications (pass 11): writes rows into the optional notification table and renders the notification lists.
 */
public class NotificationsFaced extends com.cbsinc.cms.WebControls {

	
	// ------------------------------------------------------------------
	// Event notifications (table `notification`, sql/notifications_migration.sql)
	// ------------------------------------------------------------------
	private static volatile Boolean notificationTable = null;

	/** One probe per JVM: does the optional `notification` table exist? */
	final public boolean notificationTablePresent() {
		Boolean cached = notificationTable;
		if (cached != null)
			return cached.booleanValue();
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select notification_id from notification where 1 = 0");
			notificationTable = Boolean.TRUE;
		} catch (Exception ex) {
			log.warn("Table `notification` is missing - event notifications are disabled. Run sql/notifications_migration.sql");
			notificationTable = Boolean.FALSE;
		} finally {
			qm.close();
		}
		return notificationTable.booleanValue();
	}

	/** Store one notification for a user. Never throws; a failure is only logged. */
	final public void notify(final long userId, final String siteId, final String kind, final String message,
			final String productId, final String orderId, final String amount) {
		if (userId <= 0 || !notificationTablePresent())
			return;
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "insert into notification ( user_id , site_id , kind , message , product_id , order_id , amount ) values ( ? , ? , ? , ? , ? , ? , ? )";
		try {
			java.util.Map args = Adp.getArgs();
			args.put("user_id", Long.valueOf(userId));
			args.put("site_id", Long.valueOf(siteId == null || siteId.length() == 0 ? "0" : siteId));
			args.put("kind", kind);
			args.put("message", message == null ? "" : (message.length() > 490 ? message.substring(0, 490) : message));
			args.put("product_id", Long.valueOf(productId == null || !productId.matches("\\d{1,18}") ? "0" : productId)); // binder cannot pass null
			args.put("order_id", Long.valueOf(orderId == null || !orderId.matches("\\d{1,18}") ? "0" : orderId));
			args.put("amount", Double.valueOf(amount == null || !amount.matches("\\d{1,12}(\\.\\d{1,4})?") ? "0" : amount));
			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (Exception ex) {
			Adp.rollback();
			log.error("notify " + kind + " user " + userId, ex);
		} finally {
			Adp.close();
		}
	}

	/**
	 * Fan-out to everybody following the product through the eye icon
	 * (`subscription`), except the users listed in `except` (the parties who
	 * already received a personal notice).
	 */
	final public void notifySubscribers(final String productId, final String siteId, final String kind,
			final String message, final String amount, final long... except) {
		if (productId == null || !productId.matches("\\d{1,18}") || !notificationTablePresent())
			return;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select distinct user_id from subscription where soft_id = " + productId);
			for (int i = 0; i < qm.rows().size(); i++) {
				String v = qm.getValueAt(i, 0);
				if (v == null || !v.matches("\\d+"))
					continue;
				long u = Long.parseLong(v);
				boolean skip = false;
				for (long e : except)
					if (e == u) skip = true;
				if (!skip)
					notify(u, siteId, kind, message, productId, null, amount);
			}
		} catch (Exception ex) {
			log.error("notifySubscribers " + productId, ex);
		} finally {
			qm.close();
		}
	}

	/** Seller of a product: soft.user_id, or the site owner when the card has none. */
	final public long productSellerId(final String productId) {
		if (productId == null || !productId.matches("\\d{1,18}"))
			return 0;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select coalesce(soft.user_id, 0) , coalesce(site.owner, 0) from soft left join site on site.site_id = soft.site_id where soft.soft_id = " + productId);
			if (qm.rows().size() == 0)
				return 0;
			long u = Long.parseLong(qm.getValueAt(0, 0));
			return u > 0 ? u : Long.parseLong(qm.getValueAt(0, 1));
		} catch (Exception ex) {
			log.error("productSellerId " + productId, ex);
			return 0;
		} finally {
			qm.close();
		}
	}

	/** Name of a soft row, "" if unknown. */
	final public String productName(final String productId) {
		if (productId == null || !productId.matches("\\d{1,18}"))
			return "";
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select name from soft where soft_id = " + productId);
			return qm.rows().size() == 0 || qm.getValueAt(0, 0) == null ? "" : qm.getValueAt(0, 0);
		} catch (Exception ex) {
			return "";
		} finally {
			qm.close();
		}
	}

	/**
	 * Users holding a live (submitted) bid on a product, except `exceptUserId`.
	 */
	final public java.util.List<Long> liveBidders(final String productId, final long exceptUserId) {
		java.util.List<Long> out = new java.util.ArrayList<Long>();
		if (productId == null || !productId.matches("\\d{1,18}"))
			return out;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select distinct user_id from soft where catalog_id = " + SpecialCatalog.AUCTION_BID_CATALOG
					+ " and tree_id = " + productId + " and active = true and ACTION_BID_STATUS_ID = "
					+ com.cbsinc.cms.controllers.AuctionBidStatus.PRODUCT_AUCTION_BID_SUBMITED);
			for (int i = 0; i < qm.rows().size(); i++) {
				String v = qm.getValueAt(i, 0);
				if (v != null && v.matches("\\d+") && Long.parseLong(v) != exceptUserId)
					out.add(Long.valueOf(v));
			}
		} catch (Exception ex) {
			log.error("liveBidders " + productId, ex);
		} finally {
			qm.close();
		}
		return out;
	}

	/**
	 * Order saved or its status changed: tell the buyer and the seller. Reads
	 * everything from the order itself so every save path can call it.
	 */
	final public void notifyOrderChanged(final String orderId, final String siteId, final long actorUserId) {
		if (orderId == null || !orderId.matches("\\d{1,18}") || !notificationTablePresent())
			return;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select o.user_id , o.tree_id , coalesce(p.name, '') , coalesce(ps.lable, '') , coalesce(ds.lable, '') , o.end_amount"
					+ " from orders o left join soft p on p.soft_id = o.tree_id"
					+ " left join paystatus ps on ps.paystatus_id = o.paystatus_id"
					+ " left join itemdeliverystatus ds on ds.item_deliverystatus_id = o.deliverystatus_id"
					+ " where o.order_id = " + orderId);
			if (qm.rows().size() == 0)
				return;
			String buyer = qm.getValueAt(0, 0), product = qm.getValueAt(0, 1), name = qm.getValueAt(0, 2);
			String text = "Order #" + orderId + (name.length() == 0 ? "" : " (" + name + ")") + ": payment - "
					+ qm.getValueAt(0, 3) + ", delivery - " + qm.getValueAt(0, 4);
			String amount = qm.getValueAt(0, 5);
			long buyerId = buyer == null || !buyer.matches("\\d+") ? 0 : Long.parseLong(buyer);
			long sellerId = productSellerId(product);
			if (buyerId > 0 && buyerId != actorUserId)
				notify(buyerId, siteId, "order", text, product, orderId, amount);
			if (sellerId > 0 && sellerId != buyerId && sellerId != actorUserId)
				notify(sellerId, siteId, "order", text, product, orderId, amount);
			notifySubscribers(product, siteId, "followed_order", (name.length() == 0 ? "A product you follow" : "\"" + name + "\"")
					+ " was ordered: payment - " + qm.getValueAt(0, 3) + ", delivery - " + qm.getValueAt(0, 4), null, buyerId, sellerId, actorUserId);
		} catch (Exception ex) {
			log.error("notifyOrderChanged " + orderId, ex);
		} finally {
			qm.close();
		}
	}

	/** Dispute sent to a resolution center or its status changed. */
	final public void notifyResolutionChanged(final String orderId, final String siteId, final String statusLabel, final long actorUserId) {
		if (orderId == null || !orderId.matches("\\d{1,18}") || !notificationTablePresent())
			return;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQuery("select o.user_id , o.tree_id from orders o where o.order_id = " + orderId);
			if (qm.rows().size() == 0)
				return;
			String buyer = qm.getValueAt(0, 0), product = qm.getValueAt(0, 1);
			String text = "Order #" + orderId + ": dispute - " + statusLabel;
			long buyerId = buyer == null || !buyer.matches("\\d+") ? 0 : Long.parseLong(buyer);
			long sellerId = productSellerId(product);
			if (buyerId > 0 && buyerId != actorUserId)
				notify(buyerId, siteId, "dispute", text, product, orderId, null);
			if (sellerId > 0 && sellerId != buyerId && sellerId != actorUserId)
				notify(sellerId, siteId, "dispute", text, product, orderId, null);
		} catch (Exception ex) {
			log.error("notifyResolutionChanged " + orderId, ex);
		} finally {
			qm.close();
		}
	}

	/**
	 * Event rows for the user, in the same <notification> shape as the
	 * subscription list so the templates need no change. Rows shown are
	 * marked read.
	 */
	final private void appendEventNotifications(final StringBuffer table, final long userId, final int offset) {
		if (!notificationTablePresent())
			return;
		QueryManager qm = new QueryManager();
		try {
			Object[] args = new Object[] { Long.valueOf(userId) };
			qm.executeQueryWithArgs("select notification_id , kind , message , product_id , order_id , amount , cdate , is_read from notification"
					+ " where user_id = ? order by notification_id desc", args, 10, offset);
			StringBuffer shown = new StringBuffer();
			for (int i = 0; i < qm.rows().size(); i++) {
				String id = qm.getValueAt(i, 0), kind = qm.getValueAt(i, 1), msg = qm.getValueAt(i, 2);
				String product = qm.getValueAt(i, 3), order = qm.getValueAt(i, 4), amount = qm.getValueAt(i, 5), cdate = qm.getValueAt(i, 6);
				if (product == null || "0".equals(product)) product = "";
				if (order != null && "0".equals(order)) order = null;
				if (amount != null && "0.0".equals(amount)) amount = "";
				table.append("<notification>\n");
				table.append("<event_id>" + id + "</event_id>\n");
				table.append("<kind>" + kind + "</kind>\n");
				table.append("<unread>" + ("0".equals(qm.getValueAt(i, 7)) ? "true" : "false") + "</unread>\n");
				table.append("<product_id>" + product + "</product_id>\n");
				table.append("<order_id>" + (order == null ? "" : order) + "</order_id>\n");
				table.append("<message>" + com.cbsinc.cms.utils.Validation.escapeXml(msg) + "</message>\n");
				table.append("<row_id>e" + i + "</row_id>\n");
				table.append("<file_exist></file_exist>\n");
				table.append("<n>" + com.cbsinc.cms.utils.Validation.escapeXml(msg) + "</n>\n");
				table.append("<image>images/Folder.jpg</image>\n");
				table.append("<big_image>images/Folder.jpg</big_image>\n");
				table.append("<big_image_type>jpg</big_image_type>\n");
				table.append("<icon_type>jpg</icon_type>\n");
				table.append("<user_id>" + userId + "</user_id>\n");
				table.append("<product_url>" + (product.length() > 0 ? "ProductInfo.jsp?policy_byproductid=" + product
						: (order == null ? "Notifications.jsp" : "Order.jsp?order_id=" + order)) + "</product_url>\n");
				table.append("<description></description>\n");
				table.append("<amount>" + (amount == null ? "" : amount) + "</amount>\n");
				table.append("<offer_status></offer_status>\n<offer_status_id>-1</offer_status_id>\n");
				table.append("<actionbidstatus></actionbidstatus>\n<actionbidstatus_id>-1</actionbidstatus_id>\n");
				table.append("<paystatus></paystatus>\n<paystatus_id></paystatus_id>\n<deliverystatus></deliverystatus>\n<deliverystatus_id></deliverystatus_id>\n");
				table.append("<cdate>" + (cdate == null ? "" : cdate) + "</cdate>\n<currency_desc></currency_desc>\n");
				table.append("</notification>\n");
				if (shown.length() > 0) shown.append(",");
				shown.append(id);
			}
			if (shown.length() > 0)
				markRead(shown.toString());
		} catch (Exception ex) {
			log.error("appendEventNotifications " + userId, ex);
		} finally {
			qm.close();
		}
	}

	final private void markRead(final String idList) {
		if (!idList.matches("[0-9,]+"))
			return;
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		try {
			Adp.executeUpdate("update notification set is_read = 1 where notification_id in (" + idList + ")");
			Adp.commit();
		} catch (Exception ex) {
			Adp.rollback();
			log.error("markRead", ex);
		} finally {
			Adp.close();
		}
	}

	/** Number of unread event notifications, for the page header. */
	final public long unreadCount(final long userId) {
		if (userId <= 0 || !notificationTablePresent())
			return 0;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select count(*) from notification where user_id = ? and is_read = 0", new Object[] { Long.valueOf(userId) });
			return Long.parseLong(qm.getValueAt(0, 0));
		} catch (Exception ex) {
			return 0;
		} finally {
			qm.close();
		}
	}
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
	 * Copyright: Copyright (c) 2014
	 * </p>
	 * <p>
	 * Company: CENTER BUSINESS SOLUTIONS INC
	 * </p>
	 *
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	static private Logger log = Logger.getLogger(NotificationsFaced.class);

	ResourceBundle sequencesRs = null;
	// transient java.util.Calendar calendar;
	// SimpleDateFormat formatter ;

	public NotificationsFaced() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		// calendar = java.util.Calendar.getInstance();
		// formatter = new SimpleDateFormat(datePattern, locale );
	}


	/**
	 * Renders the notification list of the current user with paging.
	 * @return the rendered list
	 */
	final public String getNotificatios( final NotificationsBean notificationsBean ,  AuthorizationPageBean authorizationPageBean, Locale locale) {

		String productDescription = "";
		String productVersion = "";
		String productCost = "";
		String currencyId = "";
		String currencyDesc = "";
		String imageId = "";
		String productFulldescription = "";
		String user1Id = "";
		String offerStatus = "";
		String offerStatusId = "";
		String actionbidstatus = "";
		String actionbidstatusId = "";
		String message = "" ;
		String fileExist1 = "";

		String productId = "";
		String productName = "";
		String productUrl = "";
		String productIconurl = "";
		String imgUrl = "";
		String fileId= "" ;

		String paystatusId = "" ;
		String paystatus = "" ;

		String deliverystatusId = "" ;
		String deliverystatus = "" ;

		String shippingCompany = "" ;
		String trackingNumber = "" ;

		String orderId = "" ;
		String cdate = "" ;


		notificationsBean.setListup("Notifications.jsp.jsp?offset=" + (notificationsBean.getOffset() + 10));
		if (notificationsBean.getOffset() - 10 < 0)
			notificationsBean.setListdown("Notifications.jsp.jsp?offset=0");
		else
			notificationsBean.setListdown("Notifications.jsp.jsp?offset=" + (notificationsBean.getOffset() - 10));
		 //LEFT JOIN shipping_tracking ON soft_hist.soft_id = shipping_tracking.soft_id
		StringBuffer table = new StringBuffer();
		QueryManager qm = new QueryManager();
		String query = "";
		query = "SELECT  soft_hist.soft_id, soft_hist.name,soft_hist.description, soft_hist.version, soft_hist.cost, soft_hist.currency, soft_hist.serial_nubmer, file.file_id, soft_hist.type_id, soft_hist.active , soft_hist.phonetype_id , soft_hist.progname_id  , soft_hist.image_id , images.img_url as img_url_s , soft_hist.fulldescription , big_images.img_url as img_url_b , soft_hist.user_id  , soft_hist.CDATE , offerstatus.LABLE , offerstatus.OFFER_STATUS_ID  "
				+ ",  soft_hist.amount1 ,  soft_hist.amount2 ,  soft_hist.amount3 ,   soft_hist.search2 ,  soft_hist.name2 ,  soft_hist.show_rating1 ,  soft_hist.show_rating2 ,  soft_hist.show_rating3 ,  soft_hist.show_blog ,  soft_hist.jsp_url , soft_hist.COLOR , subscription.message , actionbidstatus.LABLE , actionbidstatus.ACTION_BID_STATUS_ID  "
				+ " FROM soft_hist LEFT  JOIN subscription ON soft_hist.soft_id = subscription.soft_id  "
				+ " LEFT  JOIN images ON soft_hist.image_id = images.image_id  "
				+ " LEFT  JOIN big_images ON soft_hist.bigimage_id = big_images.big_images_id  "
				+ " LEFT  JOIN file  ON  soft_hist.file_id = file.file_id  "
				+ " LEFT  JOIN offerstatus  ON  soft_hist.OFFER_STATUS_ID = offerstatus.OFFER_STATUS_ID  "
				+ " LEFT  JOIN actionbidstatus  ON  soft_hist.ACTION_BID_STATUS_ID = actionbidstatus.ACTION_BID_STATUS_ID  "
				+ " WHERE  soft_hist.active = true and soft_hist.lang_id = ? "
				+ " and subscription.user_id = ? "
				+ " ORDER BY soft_hist.soft_id DESC "; // limit " + limit_news_list + " offset " +

		//+ "LEFT OUTER   JOIN paystatus  ON orders.paystatus_id = paystatus.paystatus_id  "
		//+ "LEFT OUTER   JOIN deliverystatus  ON orders.deliverystatus_id = deliverystatus.deliverystatus_id  "

		Object[] args = new Object[2];
		args[0] = Long.valueOf(authorizationPageBean.getLangId());
		args[1] = Long.valueOf(authorizationPageBean.getIntUserID());

		try {
			qm.executeQueryWithArgs(query, args, 10, notificationsBean.getOffset());

			table.append("<notifications>\n");
			appendEventNotifications(table, authorizationPageBean.getIntUserID(), notificationsBean.getOffset());
			for (int i = 0; qm.rows().size() > i; i++) {


				productDescription = (String) qm.getValueAt( i, 2);
				productVersion = (String) qm.getValueAt(i, 3);
				productCost = (String) qm.getValueAt( i, 4);
				currencyId = (String) qm.getValueAt( i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) qm.getValueAt( i, 12);
				productFulldescription = (String) qm.getValueAt( i, 14);
				user1Id = (String) qm.getValueAt( i, 16);
				cdate = (String) qm.getValueAt( i, 17);
				offerStatus = (String) qm.getValueAt( i, 18);
				offerStatusId = (String) qm.getValueAt( i, 19);
				message = (String) qm.getValueAt( i, 31);
				productId = (String) qm.getValueAt( i, 0);
				fileId =  (String) qm.getValueAt( i, 7);
				productName = (String) qm.getValueAt( i, 1);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) qm.getValueAt( i, 0);
				productIconurl = (String) qm.getValueAt( i, 13);
				if (productIconurl == null) productIconurl = "images/Folder.jpg";
				imgUrl = (String) qm.getValueAt( i, 15);
				if (imgUrl == null) imgUrl = "images/Folder.jpg";
				if ( fileId != null && fileId.length() > 0) fileExist1 = "true";
				actionbidstatus = (String) qm.getValueAt( i, 32);
				actionbidstatusId = (String) qm.getValueAt( i, 33);


				table.append("<notification>\n");
				table.append("<product_id>" + productId + "</product_id>\n");
				table.append("<message>" + message + "</message>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<image>" + productIconurl + "</image>\n");
				table.append("<big_image>" + imgUrl + "</big_image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append("<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<product_url>" + productUrl + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<offer_status>" + offerStatus + "</offer_status>\n");
				table.append("<offer_status_id>" + offerStatusId + "</offer_status_id>\n");
				table.append("<actionbidstatus>" + actionbidstatus + "</actionbidstatus>\n");
				table.append("<actionbidstatus_id>" + actionbidstatusId + "</actionbidstatus_id>\n");

				table.append("<paystatus>" + paystatus + "</paystatus>\n");
				table.append("<paystatus_id>" + paystatusId + "</paystatus_id>\n");

				table.append("<deliverystatus>" + deliverystatus + "</deliverystatus>\n");
				table.append("<deliverystatus_id>" + deliverystatusId + "</deliverystatus_id>\n");
				table.append("<cdate>" + cdate + "</cdate>\n");

				table.append("<currency_desc>" + currencyDesc + "</currency_desc>\n");
				table.append("</notification>\n");
			}




			table.append("</notifications>\n");
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			qm.close();
		}
		return table.toString();

	}

	/**
	 * Renders the notifications of the user within a date range.
	 * @return the rendered list
	 */
	final public String getNotificationByDate(final long userId, final NotificationsBean orderListBean, final Locale locale,
			long roleId, String siteId) {

		String orderId = "";
		String productName = "0";
		String productCost = "0";
		String cdate = "";
		String paystatusLable = "";
		String paystatusId = "";
		String itemDeliverystatusLable = "";
		String itemDeliverystatusId = "";
		String currencyLable = "" ;
		String shippingCompanyName = "" ;
		String trackingNumber = "" ;

		orderListBean.setListup("Notifications.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Notifications.jsp?offset=0");
		else
			orderListBean.setListdown("Notifications.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		Object[] args = null;
		if (roleId == 2) {
			query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
					+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
					+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
					+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
					+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
					+ " LEFT  JOIN tuser ON orders_hist.user_id = tuser.user_id "
					+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
					+ " LEFT  OUTER JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
					+  "WHERE tuser.site_id  = ? and orders_hist.cdate >= ? and orders_hist.cdate <=  ? "; // LIMIT 10 OFFSET ? " ;


			args = new Object[3];
			args[0] = Long.valueOf(siteId);
			args[1] = orderListBean.getSQLDateFrom();
			args[2] = orderListBean.getSQLDateTo();

		} else {
			query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
					+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
					+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
					+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
					+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
					+ " LEFT  JOIN tuser ON orders_hist.user_id = tuser.user_id "
					+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
					+ " LEFT  OUTER JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
					+  "WHERE orders_hist.user_id  = ? and orders_hist.cdate >= ? and orders_hist.cdate <=  ? "; // LIMIT 10 OFFSET ? " ;


			args = new Object[3];
			args[0] = Long.valueOf(userId);
			args[1] = orderListBean.getSQLDateFrom();
			args[2] = orderListBean.getSQLDateTo();

		}



		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());
			// Adp.executeQuery(query);

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				productName = (String) Adp.getValueAt(i, 1);
				productCost = (String) Adp.getValueAt(i, 2);
				cdate = (String) Adp.getValueAt(i, 3);
				itemDeliverystatusLable = (String) Adp.getValueAt(i, 4);
				paystatusLable = (String) Adp.getValueAt(i, 5);
				itemDeliverystatusId = (String) Adp.getValueAt(i, 6);
				paystatusId = (String) Adp.getValueAt(i, 7);
				currencyLable = (String) Adp.getValueAt(i, 8);
				shippingCompanyName = (String) Adp.getValueAt(i, 9);
				trackingNumber = (String) Adp.getValueAt(i, 10);
				//orderListBean.setCurrency_lable((String) Adp.getValueAt(i, 5));
				table.append("<purchase>\n");
				table.append("<orderId>" + orderId + "</orderId>\n");
				table.append("<productName>" + productName + "</productName>\n");
				table.append("<productCost>" + getStrFormatNumberFloat(productCost) + "</productCost>\n");
				try {
					table.append("<cdate>"+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate)) + "</cdate>\n");
				} catch (ParseException ex) {
					log.error(ex);
				}
				table.append("<itemDeliverystatusLable>" + itemDeliverystatusLable + "</itemDeliverystatusLable>\n");
				table.append("<itemDeliverystatusId>" + itemDeliverystatusId + "</itemDeliverystatusId>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<paystatusId>" + paystatusId + "</paystatusId>\n");
				table.append("<currency_lable>" +currencyLable + "</currency_lable>\n");
				table.append("<shippingCompanyName>" +shippingCompanyName + "</shippingCompanyName>\n");
				table.append("<trackingNumber>" +trackingNumber + "</trackingNumber>\n");
				table.append("</purchase>\n");
			}

			table.append("</list>\n");

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();

	}

	/**
	 * Renders the notifications of the site filtered by status.
	 * @return the rendered list
	 */
	final public String getNotificationByStatus(final String siteId, final NotificationsBean orderListBean,
			final Locale locale) {

		String orderId = "";
		String productName = "0";
		String productCost = "0";
		String cdate = "";
		String paystatusLable = "";
		String paystatusId = "";
		String itemDeliverystatusLable = "";
		String itemDeliverystatusId = "";
		String currencyLable = "" ;
		String shippingCompanyName = "" ;
		String trackingNumber = "" ;

		orderListBean.setListup("Notifications.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Notifications.jsp?offset=0");
		else
			orderListBean.setListdown("Notifications.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
				+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
				+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
				+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
				+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT  JOIN tuser ON orders_hist.user_id = tuser.user_id "
				+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
				+ " LEFT  OUTER JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
				// FIX: referred to orders.paystatus_id / orders.ITEM_DELIVERYSTATUS_ID,
				// but only orders_hist is in FROM, so MySQL rejected the query and
				// the status filter never returned rows.
				+ "WHERE tuser.site_id  = ? and ( orders_hist.paystatus_id = ? or orders_hist.ITEM_DELIVERYSTATUS_ID =  ? )"
				+ " ORDER BY orders_hist.cdate DESC"; // LIMIT

		Object[] args = new Object[3];
		args[0] = Long.valueOf(siteId);
		args[1] = orderListBean.getOrderPaystatusId();
		args[2] = orderListBean.getDeliverystatusId();
		// args[3] = orderListBean.getOffset();

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				productName = (String) Adp.getValueAt(i, 1);
				productCost = (String) Adp.getValueAt(i, 2);
				cdate = (String) Adp.getValueAt(i, 3);
				itemDeliverystatusLable = (String) Adp.getValueAt(i, 4);
				paystatusLable = (String) Adp.getValueAt(i, 5);
				itemDeliverystatusId = (String) Adp.getValueAt(i, 6);
				paystatusId = (String) Adp.getValueAt(i, 7);
				currencyLable = (String) Adp.getValueAt(i, 8);
				shippingCompanyName = (String) Adp.getValueAt(i, 9);
				trackingNumber = (String) Adp.getValueAt(i, 10);
				//orderListBean.setCurrency_lable((String) Adp.getValueAt(i, 5));
				table.append("<purchase>\n");
				table.append("<orderId>" + orderId + "</orderId>\n");
				table.append("<productName>" + productName + "</productName>\n");
				table.append("<productCost>" + getStrFormatNumberFloat(productCost) + "</productCost>\n");
				try {
					table.append("<cdate>"+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate)) + "</cdate>\n");
				} catch (ParseException ex) {
					log.error(ex);
				}
				table.append("<itemDeliverystatusLable>" + itemDeliverystatusLable + "</itemDeliverystatusLable>\n");
				table.append("<itemDeliverystatusId>" + itemDeliverystatusId + "</itemDeliverystatusId>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<paystatusId>" + paystatusId + "</paystatusId>\n");
				table.append("<currency_lable>" +currencyLable + "</currency_lable>\n");
				table.append("<shippingCompanyName>" +shippingCompanyName + "</shippingCompanyName>\n");
				table.append("<trackingNumber>" +trackingNumber + "</trackingNumber>\n");
				table.append("</purchase>\n");
			}

			table.append("</list>\n");

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();

	}

	/**
	 * True if the given site is the home site of a shipping company (UPS,
	 * FedEx, USPS, DHL ... each owns a row in shipping_company with its
	 * site_id). Such a site shows the orders assigned to that carrier instead
	 * of the shop's own notifications.
	 */
	final public boolean isShippingCompanySite(final String siteId) {
		if (siteId == null || !siteId.matches("-?[0-9]{1,18}"))
			return false;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select count(*) from shipping_company where site_id = ?",
					new Object[] { Long.valueOf(siteId) });
			return qm.rows().size() > 0 && Long.parseLong((String) qm.getValueAt(0, 0)) > 0;
		} catch (Exception ex) {
			log.error("isShippingCompanySite " + siteId, ex);
			return false;
		} finally {
			qm.close();
		}
	}

	/**
	 * Orders handed to the shipping company that owns the given site. Same
	 * columns and XML as getNotificationByStatus so the Notifications template
	 * renders it unchanged.
	 */
	final public String getCarrierNotifications(final String siteId, final NotificationsBean orderListBean,
			final Locale locale) {

		String orderId = "";
		String productName = "0";
		String productCost = "0";
		String cdate = "";
		String paystatusLable = "";
		String paystatusId = "";
		String itemDeliverystatusLable = "";
		String itemDeliverystatusId = "";
		String currencyLable = "" ;
		String shippingCompanyName = "" ;
		String trackingNumber = "" ;

		orderListBean.setListup("Notifications.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Notifications.jsp?offset=0");
		else
			orderListBean.setListdown("Notifications.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
				+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
				+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
				+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
				+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT  JOIN tuser ON orders_hist.user_id = tuser.user_id "
				+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
				+ " LEFT  OUTER JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
				// Carrier view: the current site belongs to a shipping company; show
				// every order handed to that company, newest first.
				+ "WHERE shipping_company.site_id = ? ORDER BY orders_hist.cdate DESC";

		Object[] args = new Object[1];
		args[0] = Long.valueOf(siteId);
		// args[3] = orderListBean.getOffset();

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				productName = (String) Adp.getValueAt(i, 1);
				productCost = (String) Adp.getValueAt(i, 2);
				cdate = (String) Adp.getValueAt(i, 3);
				itemDeliverystatusLable = (String) Adp.getValueAt(i, 4);
				paystatusLable = (String) Adp.getValueAt(i, 5);
				itemDeliverystatusId = (String) Adp.getValueAt(i, 6);
				paystatusId = (String) Adp.getValueAt(i, 7);
				currencyLable = (String) Adp.getValueAt(i, 8);
				shippingCompanyName = (String) Adp.getValueAt(i, 9);
				trackingNumber = (String) Adp.getValueAt(i, 10);
				//orderListBean.setCurrency_lable((String) Adp.getValueAt(i, 5));
				table.append("<purchase>\n");
				table.append("<orderId>" + orderId + "</orderId>\n");
				table.append("<productName>" + productName + "</productName>\n");
				table.append("<productCost>" + getStrFormatNumberFloat(productCost) + "</productCost>\n");
				try {
					table.append("<cdate>"+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate)) + "</cdate>\n");
				} catch (ParseException ex) {
					log.error(ex);
				}
				table.append("<itemDeliverystatusLable>" + itemDeliverystatusLable + "</itemDeliverystatusLable>\n");
				table.append("<itemDeliverystatusId>" + itemDeliverystatusId + "</itemDeliverystatusId>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<paystatusId>" + paystatusId + "</paystatusId>\n");
				table.append("<currency_lable>" +currencyLable + "</currency_lable>\n");
				table.append("<shippingCompanyName>" +shippingCompanyName + "</shippingCompanyName>\n");
				table.append("<trackingNumber>" +trackingNumber + "</trackingNumber>\n");
				table.append("</purchase>\n");
			}

			table.append("</list>\n");

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();

	}

	/**
	 * True if the given site is the home site of a resolution center
	 * (resolution_center.site_id).
	 */
	final public boolean isResolutionCenterSite(final String siteId) {
		if (siteId == null || !siteId.matches("-?[0-9]{1,18}"))
			return false;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select count(*) from resolution_center where site_id = ?",
					new Object[] { Long.valueOf(siteId) });
			return qm.rows().size() > 0 && Long.parseLong((String) qm.getValueAt(0, 0)) > 0;
		} catch (Exception ex) {
			log.error("isResolutionCenterSite " + siteId, ex);
			return false;
		} finally {
			qm.close();
		}
	}

	/**
	 * Disputes (orders with a resolution center) assigned to the center that
	 * owns the given site. Same XML as getNotificationByStatus. Requires the
	 * columns from sql/resolution_center_migration.sql; the caller checks.
	 */
	final public String getResolutionCenterNotifications(final String siteId, final NotificationsBean orderListBean,
			final Locale locale) {

		String orderId = "";
		String productName = "0";
		String productCost = "0";
		String cdate = "";
		String paystatusLable = "";
		String paystatusId = "";
		String itemDeliverystatusLable = "";
		String itemDeliverystatusId = "";
		String currencyLable = "" ;
		String shippingCompanyName = "" ;
		String trackingNumber = "" ;

		orderListBean.setListup("Notifications.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Notifications.jsp?offset=0");
		else
			orderListBean.setListdown("Notifications.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
				+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
				+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
				+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
				+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT  JOIN tuser ON orders_hist.user_id = tuser.user_id "
				+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
				+ " LEFT  OUTER JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
				// Resolution-center view: orders whose dispute was assigned to the
				// center that owns the current site. The two columns live on
				// orders (sql/resolution_center_migration.sql), not orders_hist.
				+ " LEFT  JOIN orders ON orders_hist.order_id = orders.order_id "
				+ " LEFT  JOIN resolution_center ON orders.resolution_center_id = resolution_center.resolution_center_id "
				+ "WHERE resolution_center.site_id = ? ORDER BY orders_hist.cdate DESC";

		Object[] args = new Object[1];
		args[0] = Long.valueOf(siteId);
		// args[3] = orderListBean.getOffset();

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				productName = (String) Adp.getValueAt(i, 1);
				productCost = (String) Adp.getValueAt(i, 2);
				cdate = (String) Adp.getValueAt(i, 3);
				itemDeliverystatusLable = (String) Adp.getValueAt(i, 4);
				paystatusLable = (String) Adp.getValueAt(i, 5);
				itemDeliverystatusId = (String) Adp.getValueAt(i, 6);
				paystatusId = (String) Adp.getValueAt(i, 7);
				currencyLable = (String) Adp.getValueAt(i, 8);
				shippingCompanyName = (String) Adp.getValueAt(i, 9);
				trackingNumber = (String) Adp.getValueAt(i, 10);
				//orderListBean.setCurrency_lable((String) Adp.getValueAt(i, 5));
				table.append("<purchase>\n");
				table.append("<orderId>" + orderId + "</orderId>\n");
				table.append("<productName>" + productName + "</productName>\n");
				table.append("<productCost>" + getStrFormatNumberFloat(productCost) + "</productCost>\n");
				try {
					table.append("<cdate>"+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate)) + "</cdate>\n");
				} catch (ParseException ex) {
					log.error(ex);
				}
				table.append("<itemDeliverystatusLable>" + itemDeliverystatusLable + "</itemDeliverystatusLable>\n");
				table.append("<itemDeliverystatusId>" + itemDeliverystatusId + "</itemDeliverystatusId>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<paystatusId>" + paystatusId + "</paystatusId>\n");
				table.append("<currency_lable>" +currencyLable + "</currency_lable>\n");
				table.append("<shippingCompanyName>" +shippingCompanyName + "</shippingCompanyName>\n");
				table.append("<trackingNumber>" +trackingNumber + "</trackingNumber>\n");
				table.append("</purchase>\n");
			}

			table.append("</list>\n");

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();

	}

}
