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
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.AccountHistoryBean;
import com.cbsinc.cms.AccountHistoryDetalBean;
import com.cbsinc.cms.Currency;
import com.cbsinc.cms.CurrencyHash;
import com.cbsinc.cms.DeliveryStatus;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.OrderListBean;
import com.cbsinc.cms.PayStatus;
import com.cbsinc.cms.QueryManager;

/**
 * Business logic of the user's purchases list.
 */
public class PurchaseFaced extends com.cbsinc.cms.WebControls {

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

	static private Logger log = Logger.getLogger(OrderFaced.class);

	ResourceBundle sequencesRs = null;
	// transient java.util.Calendar calendar;
	// SimpleDateFormat formatter ;

	public PurchaseFaced() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		// calendar = java.util.Calendar.getInstance();
		// formatter = new SimpleDateFormat(datePattern, locale );
	}



	/*
	 * если возврацается значение -1 то не совпадение валют
	 */



	/*
	 * если возврацается значение -1 то не совпадение валют
	 */

	final public String getPurchases(long userId, final OrderListBean orderListBean, Locale locale) {
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

		orderListBean.setListup("Purchases.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Purchases.jsp?offset=0");
		else
			orderListBean.setListdown("Purchases.jsp?offset=" + (orderListBean.getOffset() - 10));
		 //LEFT JOIN shipping_tracking ON soft.soft_id = shipping_tracking.soft_id
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT orders_hist.ORDER_ID, orders_hist.NAME , orders_hist.PRODUCT_COST, orders_hist.cdate ,  itemdeliverystatus.lable ,  paystatus.lable ,  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID , "
				+ " paystatus.paystatus_id  , currency.currency_lable , shipping_company.NAME , shipping_tracking.SHIPPING_NUNBER  FROM orders_hist "
				+ " LEFT  JOIN shipping_company ON orders_hist.SHIPPING_COMPANY_ID  =  shipping_company.SHIPPING_COMPANY_ID "
				+ " LEFT  JOIN shipping_tracking ON orders_hist.soft_id  =  shipping_tracking.soft_id "
				+ " LEFT  JOIN paystatus ON orders_hist.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT  JOIN itemdeliverystatus ON orders_hist.ITEM_DELIVERYSTATUS_ID  =  itemdeliverystatus.ITEM_DELIVERYSTATUS_ID "
				+ " LEFT OUTER   JOIN currency ON orders_hist.CURRENCY = currency.currency_id "
				+ " WHERE orders_hist.user_id = ? ORDER BY orders_hist.ORDER_HIST_ID DESC  "; // + user_id ; //+ " LIMIT 10 OFFSET "+
																				// orderListBean.getOffset();

		Object[] args = new Object[1];
		args[0] = Long.valueOf(userId);

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
	 * Renders the user's purchases within a date range.
	 * @return the rendered list
	 */
	final public String getPurchasesByDate(final long userId, final OrderListBean orderListBean, final Locale locale,
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

		orderListBean.setListup("Purchases.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Purchases.jsp?offset=0");
		else
			orderListBean.setListdown("Purchases.jsp?offset=" + (orderListBean.getOffset() - 10));

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
	 * Renders the site's purchases filtered by status.
	 * @return the rendered list
	 */
	final public String getPurchasesByStatus(final String siteId, final OrderListBean orderListBean,
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

		orderListBean.setListup("Purchases.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("Purchases.jsp?offset=0");
		else
			orderListBean.setListdown("Purchases.jsp?offset=" + (orderListBean.getOffset() - 10));

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
				// FIX: orders is not in FROM (only orders_hist); MySQL rejected the query.
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

}
