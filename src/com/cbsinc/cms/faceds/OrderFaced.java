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
import java.util.HashMap;
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
import com.cbsinc.cms.controllers.SpecialCatalog;

public class OrderFaced extends com.cbsinc.cms.WebControls {

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

	public OrderFaced() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		// calendar = java.util.Calendar.getInstance();
		// formatter = new SimpleDateFormat(datePattern, locale );
	}

	/**
	 * Creates an empty order (shopping basket) for the given user and currency and
	 * returns its new id. A fresh id is drawn from the ONE_SEQUENCES table, then a
	 * row is inserted into <code>orders</code> with zero amounts, pay status
	 * CREATE_PAYMENT and delivery status FILLING_BASKET. Runs in a single
	 * transaction that is rolled back on any failure.
	 *
	 * @param user_currency_id currency id to record on the order
	 * @param orderBean        bean that receives the generated order id
	 * @return the new order id as a string
	 * @throws Exception if the insert fails; the transaction is rolled back first
	 */
	public String createOrder(final String userCurrencyId, final OrderBean orderBean) throws Exception {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();

		String query = sequencesRs.getString("orders");
		// String query = "SELECT NEXT VALUE FOR orders_order_id_seq AS ID FROM
		// ONE_SEQUENCES";
		try {
			Adp.executeQuery(query);
			orderBean.setOrderId(Adp.getValueAt(0, 0));
			query = "insert into orders ( order_id ,  cdate ,  end_amount ,  amount ,  tax ,  user_id ,  delivery_amount ,  paystatus_id , deliverystatus_id , currency_id ) "
					+ " VALUES ( ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? , ? , ? )";

			Map args = Adp.getArgs();
			args.put("order_id", Long.valueOf(orderBean.getOrderId()));
			args.put("cdate", new java.util.Date());
			args.put("end_amount", 0);
			args.put("amount", 0);
			args.put("tax", 0);
			args.put("user_id", Long.valueOf(orderBean.getUserID()));
			args.put("delivery_amount", 0);
			args.put("paystatus_id", PayStatus.CREATE_PAYMENT);
			args.put("deliverystatus_id", DeliveryStatus.FILLING_BASKET);
			args.put("currency_id", Long.valueOf(userCurrencyId));

			Adp.executeInsertWithArgs(query, args);

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return orderBean.getOrderId();
	}

	/**
	 * Deletes an order (basket) and detaches its positions. Intended for a basket
	 * that has not yet been paid.
	 *
	 * @param basket_id  id of the order to remove
	 * @param orderBean  order context
	 * @return the basket id passed in
	 * @throws Exception if the delete fails; the transaction is rolled back first
	 */
	public String deleteOrder(final String basketId, final OrderBean orderBean) throws Exception {
		if (basketId == null || basketId.length() == 0)
			return "";
		if (orderBean.getOrderPaystatus().compareTo("0") != 0)
			return orderBean.getOrderId();
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		float fltProductAmount = 0;
		float fltOrderAmount = 0;
		float fltEndOrderAmount = 0;
		String strProductId = "";
		try {
			query = "select  product_id   from  basket where basket_id = " + basketId;
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				strProductId = (String) Adp.getValueAt(0, 0);
			else {
				return orderBean.getOrderId();
			}
			query = "select cost  from soft  where soft_id = " + strProductId;
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				fltProductAmount = (new Float((String) Adp.getValueAt(0, 0))).floatValue(); // + " " +
																								// (String)Adp.getValueAt(0,1)
																								// ;
			query = "select amount , end_amount from orders  where order_id = ?";
			Adp.executeQueryWithArgs(query, new Object[] { orderBean.getOrderId() });
			if (Adp.rows().size() != 0) {
				fltOrderAmount = (new Float((String) Adp.getValueAt(0, 0))).floatValue(); // + " " +
																							// (String)Adp.getValueAt(0,1)
																							// ;
				fltEndOrderAmount = (new Float((String) Adp.getValueAt(0, 1))).floatValue(); // + " " +
																								// (String)Adp.getValueAt(0,1)
																								// ;
			}

			int quantity = 1;
			query = "select quantity  from  basket where basket_id = " + basketId;
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0) {
				String value = (String) Adp.getValueAt(0, 0);
				if (value != null)
					quantity = Integer.parseInt(value);
			}

			query = "update orders  set end_amount =  ? , amount = ?  where order_id = ?";
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("end_amount", (double) (fltEndOrderAmount - (fltProductAmount * quantity)));
			args.put("amount", (double) (fltOrderAmount - (fltProductAmount * quantity)));
			Adp.executeUpdateWithArgs(query, args);

			query = "delete  from  basket where basket_id = " + basketId;
			Adp.executeUpdate(query);

			query = "select  product_id   from  basket where basket_id = " + basketId;
			Adp.executeQuery(query);

			if (Adp.rows().size() == 0) {
				orderBean.setEndAmount("0");
				orderBean.setOrderAmount("0");
			}
			Adp.commit();

		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return orderBean.getOrderId();
	}

	/*
	 * если возврацается значение -1 то не совпадение валют
	 */

	final public String addGroupPosition(final String strPositionsId, final int quantity, final OrderBean orderBean)
			throws Exception {
		String[] positionsArrayId = strPositionsId.split("_");
		String rezult = "";
		for (int i = 0; positionsArrayId.length > i; i++) {
			rezult = addPosition(positionsArrayId[i], quantity, orderBean);
			if (rezult.compareTo("-1") == 0) {
				break;
			}
		}

		return rezult;
	}

	/*
	 * если возврацается значение -1 то не совпадение валют
	 */

	final public String addPosition(final String strPositionId, final int quantity, final OrderBean orderBean)
			throws Exception {
		if (orderBean.getOrderPaystatus().compareTo("0") != 0)
			return strPositionId;
		if (quantity == 0)
			return strPositionId;
		String basketId = "";
		String query = "";
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		float fltProductAmount = 0;
		float fltOrderAmount = 0;
		float fltEndOrderAmount = 0;
		String strProductCurrency = "0";
		String strOrderCurrency = "0";
		try {
			query = "select cost ,  currency from soft  where soft_id = " + strPositionId;
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0) {
				fltProductAmount = (new Float((String) Adp.getValueAt(0, 0))).floatValue(); // + " " +
																								// (String)Adp.getValueAt(0,1)
																								// ;
				strProductCurrency = (String) Adp.getValueAt(0, 1); // + " "
																		// +
																		// (String)Adp.getValueAt(0,1)
																		// ;
			}
			query = "select currency_id  from orders   where order_id = ?";
			Adp.executeQueryWithArgs(query, new Object[] { orderBean.getOrderId() });
			if (Adp.rows().size() != 0) {
				strOrderCurrency = (String) Adp.getValueAt(0, 0);
			}
			if (strProductCurrency.compareTo(strOrderCurrency) != 0)
				return "-1";
			query = "select amount , end_amount , currency_id   from orders  where order_id = ?";
			Adp.executeQueryWithArgs(query, new Object[] { orderBean.getOrderId() });
			if (Adp.rows().size() != 0) {
				fltOrderAmount = (new Float((String) Adp.getValueAt(0, 0))).floatValue(); // + " " +
																							// (String)Adp.getValueAt(0,1)
																							// ;
				fltEndOrderAmount = (new Float((String) Adp.getValueAt(0, 1))).floatValue(); // + " " +
																								// (String)Adp.getValueAt(0,1)
																								// ;
			}

			query = "update orders  set end_amount =  ?, amount = ? where order_id = ?";
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("end_amount", (double) (fltEndOrderAmount + (fltProductAmount * quantity)));
			args.put("amount", (double) (fltOrderAmount + (fltProductAmount * quantity)));
			Adp.executeUpdateWithArgs(query, args);

			// query = "SELECT NEXT VALUE FOR basket_basket_id_seq AS ID FROM
			// ONE_SEQUENCES";
			query = sequencesRs.getString("basket");
			Adp.executeQuery(query);

			basketId = Adp.getValueAt(0, 0);

//			query = "insert into basket ( basket_id ,  product_id , order_id ) VALUES "
//					+ "( " + basket_id + ", " + strPosition_id + ", "
//					+ orderBean.getOrder_id() + " " + ")";
//			Adp.executeUpdate(query);

			query = "insert into basket ( basket_id ,  product_id , order_id , quantity ) VALUES ( ?, ?, ? , ? )";
			args = Adp.getArgs();
			args.put("basket_id", Long.parseLong(basketId));
			args.put("product_id", Long.parseLong(strPositionId));
			args.put("order_id", Long.parseLong(orderBean.getOrderId()));
			args.put("quantity", quantity);
			Adp.executeInsertWithArgs(query, args);

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}

		return strPositionId;
	}

	/**
	 * Removes a single position from the basket by clearing its order_id, but only
	 * while the order is still unpaid (order_paystatus "0"). A null/empty id or a
	 * non-zero pay status is a no-op and the id is returned unchanged.
	 *
	 * @param strPosition_id soft_id of the position to detach
	 * @param orderBean      order context, used to read the current pay status
	 * @return the position id passed in
	 * @throws Exception if the update fails; the transaction is rolled back first
	 */
	final public String deletePosition(final String strPositionId, final OrderBean orderBean) throws Exception {
		if (orderBean.getOrderPaystatus().compareTo("0") != 0)
			return strPositionId;
		if (strPositionId == null || strPositionId.length() == 0)
			return strPositionId;
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = "update soft set order_id = null where soft_id = " + strPositionId;
		try {
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}

		return strPositionId;
	}

	final public int getProductsListSize(final jakarta.servlet.http.HttpServletRequest request,
			final OrderBean orderBean) throws Exception {
		String size = "0";

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = "SELECT count( orders.order_id ) as size FROM orders "
				// + "RIGHT JOIN basket ON orders.order_id = basket.order_id "
				+ " JOIN basket  ON orders.order_id = basket.order_id "
				+ "LEFT  JOIN soft  ON soft.soft_id = basket.product_id "
				+ "LEFT  JOIN file  ON soft.file_id = file.file_id  "
				+ "LEFT  JOIN images ON soft.image_id = images.image_id "
				+ "LEFT OUTER   JOIN currency  currency_order  ON orders.currency_id = currency_order.currency_id "
				+ "LEFT OUTER   JOIN currency  currency_product ON soft.currency = currency_product.currency_id "
				+ "LEFT OUTER   JOIN paystatus  ON orders.paystatus_id = paystatus.paystatus_id  "
				+ "WHERE orders.order_id = ?";

		try {
			Adp.executeQueryWithArgs(query, new Object[] { orderBean.getOrderId() });
			if (Adp.rows().size() > 0)
				size = (String) Adp.getValueAt(0, 0);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}

		return Integer.parseInt(size);
	}

	/**
	 * Looks up the order id linked to a given account_hist row. Used by the payment
	 * settlement paths to find the order behind a balance transaction.
	 *
	 * @param account_history_id account_hist.id to resolve
	 * @return the order id, or "0" when no matching row exists
	 * @throws Exception if the query fails; the transaction is rolled back first
	 */
	final public String getOrderByAccount(final String accountHistoryId) throws Exception {
		String orderId = "0";
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = "SELECT order_id  FROM account_hist WHERE account_hist.id = " + accountHistoryId;

		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() > 0)
				orderId = (String) Adp.getValueAt(0, 0);
			Adp.commit();
		}

		catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}

		return orderId;
	}

	/**
	 * Parses a string to an int, returning 0 for null or non-numeric input instead
	 * of throwing.
	 *
	 * @param s value to parse
	 * @return the parsed int, or 0 when {@code s} is not a valid integer
	 */
	final public int stringToInt(final String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
		}
		return i;
	}

	/**
	 * Legacy no-op retained for API compatibility; the demand update it once
	 * performed is commented out. Always returns true.
	 *
	 * @return true
	 */
	final public boolean setSelectedDemand() {
		QueryManager Adp = new QueryManager();
		// String query = "update tdemand set selected=true where id=" + demand
		// ;
		// Adp.executeUpdate(query);
		Adp.close();
		return true;
	}

	/**
	 * Legacy no-op retained for API compatibility; the demand update it once
	 * performed is commented out. Always returns true.
	 *
	 * @return true
	 */
	final public boolean setPassiveDemand() {
		QueryManager Adp = new QueryManager();
		// String query = "update tdemand set active=false where id=" + demand ;
		// Adp.executeUpdate(query);
		Adp.close();
		return true;
	}

	/**
	 * Superseded earlier version of {@link #setSave(OrderBean)}, kept for reference.
	 * Prefer {@code setSave}.
	 *
	 * @param orderBean the order to save
	 * @return 0 on success, or a positive validation code
	 * @throws Exception if persistence fails
	 */
	final public int setSaveOld(final OrderBean orderBean) throws Exception {
		QueryManager Adp = new QueryManager();
		String query = "";
		try {
			// if(shipment_address == null || shipment_address.length() == 0)
			// return 1 ;
			if (orderBean.getCountryId() == null || orderBean.getCountryId().length() == 0)
				return 3;
			if (orderBean.getCityId() == null || orderBean.getCityId().length() == 0)
				return 4;
			if (orderBean.getShipmentPhone() == null || orderBean.getShipmentPhone().length() == 0)
				return 5;
			if (orderBean.getContactPerson() == null || orderBean.getContactPerson().length() == 0)
				return 6;
			// if(shipment_email == null || shipment_email.length() == 0) return
			// 7 ;
			// if(shipment_fax == null || shipment_fax.length() == 0) return 8 ;
			// if(shipment_description == null || shipment_description.length()
			// == 0) return 9 ;
			// if(shipment_zip == null || shipment_zip.length() == 0) return 10
			// ;

			Adp.beginTransaction();

			query = "update orders  set user_id =  ? , amount = ? , tax = ? , end_amount = ? , "
					+ " delivery_amount = ? , paystatus_id = ? , deliverystatus_id = ? , shipping_company_id = ? , "
					+ resolutionSetFragment()
					+ " delivery_start = ? , currency_id = ? , country_id = ? , city_id = ? , "
					+ " address = ? , phone = ? , contact_person = ? , email = ?  , fax = ? , description = ? "
					+ " where order_id = ?";
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("user_id", Long.valueOf(orderBean.getUserID()));
			args.put("amount", Double.valueOf(orderBean.getOrderAmount()));
			args.put("tax", Long.valueOf(orderBean.getOrderTax()));
			args.put("end_amount", Double.valueOf(orderBean.getEndAmount()));
			args.put("delivery_amount", Double.valueOf(orderBean.getDeliveryAmoun()));
			args.put("paystatus_id", Long.valueOf(orderBean.getOrderPaystatus()));
			args.put("deliverystatus_id", Long.valueOf(orderBean.getDeliverystatusId()));
			args.put("shipping_company_id", Long.valueOf(shippingCompanyOrZero(orderBean)));
			putResolutionArgs(args, orderBean);
			args.put("delivery_start", new java.util.Date());
			args.put("currency_id", Long.valueOf(orderBean.getOrderCurrencyId()));
			args.put("country_id", Long.valueOf(orderBean.getCountryId()));
			args.put("city_id", Long.valueOf(orderBean.getCityId()));
			args.put("address", orderBean.getShipmentAddress());
			args.put("phone", orderBean.getShipmentPhone());
			args.put("contact_person", orderBean.getContactPerson());
			args.put("email", orderBean.getShipmentEmail());
			args.put("fax", orderBean.getShipmentFax());
			args.put("description", orderBean.getShipmentDescription());

			Adp.executeUpdateWithArgs(query, args);

			if (orderBean.getAccountHistoryId().length() == 0) {
				int intUserId = Integer.parseInt(orderBean.getUserID());
				float Balans = getBalans(intUserId);
				float fltEndAmount = (new Float(orderBean.getEndAmount())).floatValue();
				float fltTotalAmount = (Balans - fltEndAmount);
				float fltOrderTax = (new Float(orderBean.getOrderTax())).floatValue();
				float fltWithtaxTotalAmount = fltTotalAmount - fltOrderTax;
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				Currency curr = currencyHash.getCurrency(orderBean.getOrderCurrencyId());
				// query = "SELECT NEXT VALUE FOR account_hist_id_seq AS ID FROM ONE_SEQUENCES";
				query = sequencesRs.getString("account_hist");
				Adp.executeQuery(query);
				orderBean.setAccountHistoryId(Adp.getValueAt(0, 0));
				String strAccountCurrencyId = "-1";
				query = "SELECT currency_id from account WHERE  user_id = ?";

				Adp.executeQueryWithArgs(query, new Object[] { orderBean.getUserID() });
				strAccountCurrencyId = Adp.getValueAt(0, 0);
				query = "insert into account_hist ( id , user_id , order_id , add_amount , old_amount , date_input , date_end , complete , "
						+ "decsription , currency_id_add , currency_id_old , currency_id_total , active , \"SYSDATE\" , total_amount , tax , withtax_total_amount , rate ) "
						+ "VALUES ( ?, ?, ?, ?, ?, now(), now(), true, ?, ?, ?, ?, false, now(), ?, ?, ?, ? )";
				Adp.executeInsertWithArgs(query, new Object[] { orderBean.getAccountHistoryId(), orderBean.getUserID(),
						orderBean.getOrderId(), Float.valueOf(-fltEndAmount), Float.valueOf(Balans),
						"Credit operation for order  N " + orderBean.getOrderId(), orderBean.getOrderCurrencyId(),
						strAccountCurrencyId, strAccountCurrencyId, Float.valueOf(fltTotalAmount), orderBean.getOrderTax(),
						Float.valueOf(fltWithtaxTotalAmount), curr.getRate() });
				query = "UPDATE account SET amount = ? WHERE  user_id = ?";
				Map<String, Object> accountArgs = new HashMap<>();
				accountArgs.put("amount", Float.valueOf(Balans - fltEndAmount));
				accountArgs.put("user_id", orderBean.getUserID());

				Adp.executeUpdateWithArgs(query, accountArgs);

				// Adp.executeUpdate(query);
			}

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return 0;
	}

	/**
	 * Saves the shipping details of the customer's OWN order at checkout and, when
	 * the order is confirmed, debits the customer's balance for it. Validates the
	 * address fields first, returning a small numeric code for the first missing
	 * one (3 country, 4 city, 5 phone, 6 contact person, ...).
	 *
	 * <p><b>Note:</b> this method both moves money and takes ownership of the order
	 * (it writes orders.user_id). It must only be used for the buyer paying for
	 * their own basket. Operational staff changing another user's order status go
	 * through {@link #setStatusOnly(OrderBean)} instead, which touches no money and
	 * no ownership (see the access rules in OrderAction).
	 *
	 * @param orderBean the order being checked out
	 * @return 0 on success, or a positive code identifying the first invalid field
	 * @throws Exception if persistence fails; the transaction is rolled back first
	 */
	final public int setSave(final OrderBean orderBean) throws Exception {
		QueryManager Adp = new QueryManager();
		String query = "";
		try {
			// if(shipment_address == null || shipment_address.length() == 0)
			// return 1 ;
			if (orderBean.getCountryId() == null || orderBean.getCountryId().length() == 0)
				return 3;
			if (orderBean.getCityId() == null || orderBean.getCityId().length() == 0)
				return 4;
			if (orderBean.getShipmentPhone() == null || orderBean.getShipmentPhone().length() == 0)
				return 5;
			if (orderBean.getContactPerson() == null || orderBean.getContactPerson().length() == 0)
				return 6;
			// if(shipment_email == null || shipment_email.length() == 0) return
			// 7 ;
			// if(shipment_fax == null || shipment_fax.length() == 0) return 8 ;
			// if(shipment_description == null || shipment_description.length()
			// == 0) return 9 ;
			// if(shipment_zip == null || shipment_zip.length() == 0) return 10
			// ;

			Adp.beginTransaction();

			query = "update orders  set user_id =  ? , amount = ? , tax = ? , end_amount = ? , "
					+ " delivery_amount = ? , paystatus_id = ? , deliverystatus_id = ? , shipping_company_id = ? , "
					+ resolutionSetFragment()
					+ " delivery_start = ? , currency_id = ? , country_id = ? , city_id = ? , "
					+ " address = ? , phone = ? , contact_person = ? , email = ?  , fax = ? , description = ? "
					+ " where order_id = ?";
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("user_id", Long.valueOf(orderBean.getUserID()));
			args.put("amount", Double.valueOf(orderBean.getOrderAmount()));
			args.put("tax", Double.valueOf(orderBean.getOrderTax()));
			args.put("end_amount", Double.valueOf(orderBean.getEndAmount()));
			args.put("delivery_amount", Double.valueOf(orderBean.getDeliveryAmoun()));
			args.put("paystatus_id", Long.valueOf(orderBean.getOrderPaystatus()));
			args.put("deliverystatus_id", Long.valueOf(orderBean.getDeliverystatusId()));
			args.put("shipping_company_id", Long.valueOf(shippingCompanyOrZero(orderBean)));
			putResolutionArgs(args, orderBean);
			args.put("delivery_start", new java.util.Date());
			args.put("currency_id", Long.valueOf(orderBean.getOrderCurrencyId()));
			args.put("country_id", Long.valueOf(orderBean.getCountryId()));
			args.put("city_id", Long.valueOf(orderBean.getCityId()));
			args.put("address", orderBean.getShipmentAddress());
			args.put("phone", orderBean.getShipmentPhone());
			args.put("contact_person", orderBean.getContactPerson());
			args.put("email", orderBean.getShipmentEmail());
			args.put("fax", orderBean.getShipmentFax());
			args.put("description", orderBean.getShipmentDescription());

			Adp.executeUpdateWithArgs(query, args);

			if (orderBean.getAccountHistoryId().length() == 0) {
				long intUserId = Long.parseLong(orderBean.getUserID());
				float Balans = getBalans(intUserId);
				float fltEndAmount = (new Float(orderBean.getEndAmount())).floatValue();
				float fltTotalAmount = (Balans - fltEndAmount);
				float fltOrderTax = (new Float(orderBean.getOrderTax())).floatValue();
				float fltWithtaxTotalAmount = fltTotalAmount - fltOrderTax;
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				Currency curr = currencyHash.getCurrency(orderBean.getOrderCurrencyId());
				// query = "SELECT NEXT VALUE FOR account_hist_id_seq AS ID FROM ONE_SEQUENCES";
				query = sequencesRs.getString("account_hist");
				Adp.executeQuery(query);
				orderBean.setAccountHistoryId(Adp.getValueAt(0, 0));
				String strAccountCurrencyId = "-1";
				query = "SELECT currency_id from account WHERE  user_id = ?";

				Adp.executeQueryWithArgs(query, new Object[] { orderBean.getUserID() });
				strAccountCurrencyId = Adp.getValueAt(0, 0);
				query = "insert into account_hist (id  ,  user_id ,  order_id ,  add_amount , "
						+ " old_amount  ,  date_input ,  date_end , complete   ,  decsription  ,  currency_id_add  , "
						+ " currency_id_old  , "
						+ " currency_id_total  ,  active  ,  account_hist.sysdate  ,  total_amount ,  tax  , "
						+ " withtax_total_amount , rate ) " + " VALUES ( ?  ,  ? ,  ? ,  ? , "
						+ " ?  ,  ? ,  ? , ?   ,  ?  ,  ?  ,  ?  ,  ?  ,  ?  ,  ?  ,  ? ,  ?  ,  ? , ? )";

				args = Adp.getArgs();
				args.put("id", Long.parseLong(orderBean.getAccountHistoryId()));
				args.put("user_id", Long.parseLong(orderBean.getUserID()));
				args.put("order_id", Long.parseLong(orderBean.getOrderId()));
				args.put("add_amount", Float.parseFloat(orderBean.getEndAmount()) * -1);
				args.put("old_amount", Balans);
				args.put("date_input", new java.util.Date());
				args.put("date_end", new java.util.Date());
				args.put("complete", true);
				args.put("decsription", "Credit operation for order  N " + orderBean.getOrderId());
				args.put("currency_id_add", Long.parseLong(orderBean.getOrderCurrencyId()));
				args.put("currency_id_old", Long.parseLong(strAccountCurrencyId));
				args.put("currency_id_total", Long.parseLong(strAccountCurrencyId));
				args.put("active", false);
				args.put("account_hist.sysdate", new java.util.Date());
				args.put("total_amount", fltTotalAmount);
				args.put("tax", Double.parseDouble(orderBean.getOrderTax()));
				args.put("withtax_total_amount", fltWithtaxTotalAmount);
				args.put("rate", curr.getRate());
				Adp.executeInsertWithArgs(query, args);

				query = "update account set amount = ? where  user_id = ?";
				args = Adp.getArgs();
				args.put("user_id", orderBean.getUserID());
				args.put("amount", (double) (Balans - fltEndAmount));
				Adp.executeUpdateWithArgs(query, args);

			}

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return 0;
	}

	/**
	 * Who owns an order: {@code {user_id, site_id}} of orders.user_id joined to
	 * tuser, or {@code null} when the order does not exist. Used by OrderAction
	 * to refuse access to somebody else's order.
	 */
	final public long[] getOrderOwner(final String orderId) throws Exception {
		if (orderId == null || !orderId.matches("[0-9]{1,18}"))
			return null;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQueryWithArgs("SELECT orders.user_id, tuser.site_id FROM orders"
					+ " LEFT JOIN tuser ON tuser.user_id = orders.user_id WHERE orders.order_id = ?",
					new Object[] { Long.valueOf(orderId) });
			if (Adp.getRowCount() == 0)
				return null;
			String u = Adp.getValueAt(0, 0);
			String st = Adp.getValueAt(0, 1);
			long uid = (u == null || u.isEmpty() || "null".equals(u)) ? -1 : Long.parseLong(u);
			long sid = (st == null || st.isEmpty() || "null".equals(st)) ? -1 : Long.parseLong(st);
			return new long[] { uid, sid };
		} finally {
			Adp.close();
		}
	}

	/**
	 * Status change performed by staff on a customer's order: writes only the
	 * payment / delivery status (and resolution-center fields). Unlike
	 * {@link #setSave} it does not rewrite orders.user_id, the address or the
	 * amounts, and it never touches the account balance - the debit belongs to
	 * the customer's own checkout, not to the warehouse.
	 */
	final public int setStatusOnly(final OrderBean orderBean) throws Exception {
		QueryManager Adp = new QueryManager();
		String query = "update orders set " + resolutionSetFragment()
				+ " paystatus_id = ? , deliverystatus_id = ? where order_id = ?";
		try {
			Adp.beginTransaction();
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("paystatus_id", Long.valueOf(orderBean.getOrderPaystatus()));
			args.put("deliverystatus_id", Long.valueOf(orderBean.getDeliverystatusId()));
			putResolutionArgs(args, orderBean);
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (Exception ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return 0;
	}

	/**
	 * Same address validation and save as {@link #setSave(OrderBean)} but WITHOUT
	 * debiting the balance. Used where the order details must be stored without
	 * charging the customer.
	 *
	 * @param orderBean the order to save
	 * @return 0 on success, or a positive code identifying the first invalid field
	 * @throws Exception if persistence fails; the transaction is rolled back first
	 */
	final public int setSaveWithOutDeductMoney(final OrderBean orderBean) throws Exception {
		QueryManager Adp = new QueryManager();
		String query = "";
		try {
			// if(shipment_address == null || shipment_address.length() == 0)
			// return 1 ;
			if (orderBean.getCountryId() == null || orderBean.getCountryId().length() == 0)
				return 3;
			if (orderBean.getCityId() == null || orderBean.getCityId().length() == 0)
				return 4;
			if (orderBean.getShipmentPhone() == null || orderBean.getShipmentPhone().length() == 0)
				return 5;
			if (orderBean.getContactPerson() == null || orderBean.getContactPerson().length() == 0)
				return 6;
			// if(shipment_email == null || shipment_email.length() == 0) return
			// 7 ;
			// if(shipment_fax == null || shipment_fax.length() == 0) return 8 ;
			// if(shipment_description == null || shipment_description.length()
			// == 0) return 9 ;
			// if(shipment_zip == null || shipment_zip.length() == 0) return 10
			// ;

			Adp.beginTransaction();

			query = "update orders  set user_id =  ? , amount = ? , tax = ? , end_amount = ? , "
					+ " delivery_amount = ? , paystatus_id = ? , deliverystatus_id = ? , shipping_company_id = ? , "
					+ resolutionSetFragment()
					+ " delivery_start = ? , currency_id = ? , country_id = ? , city_id = ? , "
					+ " address = ? , phone = ? , contact_person = ? , email = ?  , fax = ? , description = ? "
					+ " where order_id = ?";
			Map args = Adp.getArgs();
			args.put("order_id", orderBean.getOrderId());
			args.put("user_id", Long.valueOf(orderBean.getUserID()));
			args.put("amount", Double.valueOf(orderBean.getOrderAmount()));
			args.put("tax", Double.valueOf(orderBean.getOrderTax()));
			args.put("end_amount", Double.valueOf(orderBean.getEndAmount()));
			args.put("delivery_amount", Double.valueOf(orderBean.getDeliveryAmoun()));
			args.put("paystatus_id", Long.valueOf(orderBean.getOrderPaystatus()));
			args.put("deliverystatus_id", Long.valueOf(orderBean.getDeliverystatusId()));
			args.put("shipping_company_id", Long.valueOf(shippingCompanyOrZero(orderBean)));
			putResolutionArgs(args, orderBean);
			args.put("delivery_start", new java.util.Date());
			args.put("currency_id", Long.valueOf(orderBean.getOrderCurrencyId()));
			args.put("country_id", Long.valueOf(orderBean.getCountryId()));
			args.put("city_id", Long.valueOf(orderBean.getCityId()));
			args.put("address", orderBean.getShipmentAddress());
			args.put("phone", orderBean.getShipmentPhone());
			args.put("contact_person", orderBean.getContactPerson());
			args.put("email", orderBean.getShipmentEmail());
			args.put("fax", orderBean.getShipmentFax());
			args.put("description", orderBean.getShipmentDescription());

			Adp.executeUpdateWithArgs(query, args);

			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}
		return 0;
	}
//	public List getProducts1(jakarta.servlet.http.HttpServletRequest request, OrderBean orderBean )
//	throws Exception {
//
//
//		List list = new  LinkedList();
//		StringBuffer table = new StringBuffer();
//		QueryManager Adp = new QueryManager();
//		Adp.BeginTransaction();
//		String query = "";
//		query = "SELECT "
//		+ "orders.order_id,"
//		+ "orders.delivery_timeend,"
//		+ "orders.end_amount, "
//		+ "orders.amount, "
//		+ "orders.tax, "
//		+ "orders.delivery_long ,"
//		+ "orders.delivery_start,"
//		+ "orders.cdate , "
//		+ "soft.name, "
//		+ "soft.description ,"
//		+ "soft.cost, "
//		+ "soft.weight, "
//		+ "soft.count, "
//		+ "images.img_url ,"
//		+ "currency_product.currency_lable,"
//		+ "orders.address,"
//		+ "orders.phone,"
//		+ "orders.contact_person, "
//		+ "orders.email, "
//		+ "orders.fax, "
//		+ "orders.description, "
//		+ "orders.zip, "
//		+ "currency_product.currency_id, "
//		+ "orders.delivery_amount, "
//		+ "orders.country_id, "
//		+ "orders.city_id , "
//		+ "currency_product.currency_lable As curr_lable ,"
//		+ "currency_product.currency_id As curr_cd ,"
//		+ "basket.basket_id ,"
//		+ "paystatus.lable ,"
//		+ "paystatus.paystatus_id , "
//		+ "file.file_id  "
//		+ " FROM orders "
//		//+ "RIGHT  JOIN basket  ON orders.order_id = basket.order_id "
//		+ " JOIN basket  ON orders.order_id = basket.order_id "
//		+ "LEFT  JOIN soft  ON soft.soft_id = basket.product_id "
//		+ "LEFT  JOIN file  ON soft.file_id = file.file_id  "
//		+ "LEFT  JOIN images ON soft.image_id = images.image_id "
//		+ "LEFT OUTER   JOIN currency  currency_order  ON orders.currency_id = currency_order.currency_id "
//		+ "LEFT OUTER   JOIN currency  currency_product ON soft.currency = currency_product.currency_id "
//		+ "LEFT OUTER   JOIN paystatus  ON orders.paystatus_id = paystatus.paystatus_id  "
//		+ "WHERE orders.order_id = " + orderBean.getOrder_id() ;
//				// LIMIT 10  OFFSET "+ orderBean.getOffset();
//
//		try {
//			list = Adp.executeQueryList(query,10,orderBean.getOffset());
//			orderBean.setPagecount(Adp.rows().size());
//	//pagecount = Adp.rows().size();
//			Adp.commit();
//		}
//		catch (SQLException ex)
//		{
//			log.error(query,ex);
//			Adp.rollback();
//			throw ex;
//		}
//		catch (Exception ex)
//		{
//			log.error(ex);
//			Adp.rollback();
//			throw ex;
//		}
//		finally
//		{
//			Adp.close();
//		}
//	 return list;
//	}
//

	final public String getProducts(final jakarta.servlet.http.HttpServletRequest request, final OrderBean orderBean)
			throws Exception {
		orderBean.setListup("Order.jsp?offset=" + (orderBean.getOffset() + 10));
		if (orderBean.getOffset() - 10 < 0)
			orderBean.setListdown("Order.jsp?offset=0");
		else
			orderBean.setListdown("Order.jsp?offset=" + (orderBean.getOffset() - 10));
		boolean fileExist = false;
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = "SELECT " + "orders.order_id," + "orders.delivery_timeend," + "orders.end_amount, " + "orders.amount, "
				+ "orders.tax, " + "orders.delivery_long ," + "orders.delivery_start," + "orders.cdate , "
				+ "soft.name, " + "soft.description ," + "soft.cost, " + "soft.weight, "
				// + "soft.\"count\" , "
				// + "orders.amount, "
				+ "soft.count , " + "images.img_url ," + "currency_product.currency_lable," + "orders.address,"
				+ "orders.phone," + "orders.contact_person, " + "orders.email, " + "orders.fax, "
				+ "orders.description, " + "orders.zip, " + "currency_product.currency_id, "
				+ "orders.delivery_amount, " + "orders.country_id, " + "orders.city_id , "
				+ "currency_product.currency_lable As curr_lable ," + "currency_product.currency_id As curr_cd ,"
				+ "basket.basket_id ," + "paystatus.lable as order_paystatus ," + "paystatus.paystatus_id , "
				+ "file.file_id , " + "deliverystatus.deliverystatus_id , " + "deliverystatus.lable as order_status,  "
				+ "basket.quantity " + " FROM orders "
				// + "RIGHT JOIN basket ON orders.order_id = basket.order_id "
				+ " JOIN basket  ON orders.order_id = basket.order_id "
				+ "LEFT  JOIN soft  ON soft.soft_id = basket.product_id "
				+ "LEFT  JOIN file  ON soft.file_id = file.file_id  "
				+ "LEFT  JOIN images ON soft.image_id = images.image_id "
				+ "LEFT OUTER   JOIN currency  currency_order  ON orders.currency_id = currency_order.currency_id "
				+ "LEFT OUTER   JOIN currency  currency_product ON soft.currency = currency_product.currency_id "
				+ "LEFT OUTER   JOIN paystatus  ON orders.paystatus_id = paystatus.paystatus_id  "
				+ "LEFT OUTER   JOIN deliverystatus  ON orders.deliverystatus_id = deliverystatus.deliverystatus_id  "
				+ "WHERE orders.order_id = ? ";// + orderBean.getOrder_id() + " LIMIT 10 OFFSET " +
												// orderBean.getOffset();
// GROUP BY orders.order_id,orders.delivery_timeend,orders.end_amount, orders.amount, orders.tax, orders.delivery_long ,orders.delivery_start,orders.cdate ,soft.name ,soft.description ,soft.cost , soft.weight , images.img_url , currency_product.currency_lable , orders.address , orders.phone , orders.contact_person , orders.email , orders.fax , orders.description , orders.zip , orders.zip , currency_product.currency_id , orders.delivery_amount , orders.country_id , orders.country_id , orders.city_id , basket.basket_id , paystatus.lable , paystatus.paystatus_id , file.file_id , deliverystatus.deliverystatus_id , deliverystatus.lable

		try {

			Object[] args = new Object[1];
			args[0] = Long.valueOf(orderBean.getOrderId());
			// Adp.executeQuery(query);
			Adp.executeQueryWithArgs(query, args, 10, orderBean.getOffset());
			orderBean.setPagecount(Adp.rows().size());
			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				// order_id = (String) Adp.getValueAt(i, 0);
				// String delivery_timeend = (String) Adp.getValueAt(i, 1);
				String endAmount = (String) Adp.getValueAt(i, 2);
				endAmount = "" + Float.parseFloat(endAmount);
				orderBean.setEndAmount(endAmount);
				String orderAmount = (String) Adp.getValueAt(i, 3);
				orderAmount = "" + Float.parseFloat(orderAmount);
				orderBean.setOrderAmount(orderAmount);
				String orderTax = (String) Adp.getValueAt(i, 4);
				orderTax = "" + Float.parseFloat(orderTax);
				orderBean.setOrderTax(orderTax);
				String orderDeliveryLong = (String) Adp.getValueAt(i, 5);
				orderBean.setOrderDeliveryLong(orderDeliveryLong);
				// String delivery_start = (String) Adp.getValueAt(i, 6);
				// orderBean.setdelivery_start(delivery_start) ;
				String cdate = (String) Adp.getValueAt(i, 7);
				orderBean.setCdate(cdate);
				String productName = (String) Adp.getValueAt(i, 8);
				orderBean.setProductName(productName);
				String productDescription = (String) Adp.getValueAt(i, 9);
				orderBean.setProductDescription(productDescription);
				String productCost = (String) Adp.getValueAt(i, 10);
				productCost = "" + Float.parseFloat(productCost);
				orderBean.setProductCost(productCost);
				String productWeight = (String) Adp.getValueAt(i, 11);
				orderBean.setProductWeight(productWeight);
				String productCount = (String) Adp.getValueAt(i, 12);
				orderBean.setProductCount(productCount);
				String imgUrl = (String) Adp.getValueAt(i, 13);
				if (imgUrl.equals("")) {
					orderBean.setImgUrl("images/logo.gif");
				} else
					orderBean.setImgUrl(imgUrl);
				String currencyLable = (String) Adp.getValueAt(i, 14);
				orderBean.setCurrencyLable(currencyLable);
				//
				String shipmentAddress = (String) Adp.getValueAt(i, 15);
				if (shipmentAddress.length() > 0)
					orderBean.setShipmentAddress(shipmentAddress);
				String shipmentPhone = (String) Adp.getValueAt(i, 16);
				if (shipmentPhone.length() > 0)
					orderBean.setShipmentPhone(shipmentPhone);
				String contactPerson = (String) Adp.getValueAt(i, 17);
				if (contactPerson.length() > 0)
					orderBean.setContactPerson(contactPerson);
				String shipmentEmail = (String) Adp.getValueAt(i, 18);
				if (shipmentEmail.length() > 0)
					orderBean.setShipmentEmail(shipmentEmail);
				String shipmentFax = (String) Adp.getValueAt(i, 19);
				if (shipmentFax.length() > 0)
					orderBean.setShipmentFax(shipmentFax);
				String shipmentDescription = (String) Adp.getValueAt(i, 20);
				if (shipmentDescription.length() > 0)
					orderBean.setShipmentDescription(shipmentDescription);
				String shipmentZip = (String) Adp.getValueAt(i, 21);
				if (shipmentZip.length() > 0)
					orderBean.setShipmentZip(shipmentZip);
				String orderCurrencyId = (String) Adp.getValueAt(i, 22);
				if (orderCurrencyId.length() > 0)
					orderBean.setOrderCurrencyId(orderCurrencyId);

				String countryId = (String) Adp.getValueAt(i, 24);
				if (countryId.length() > 0)
					orderBean.setCountryId(countryId);

				String cityId = (String) Adp.getValueAt(i, 25);
				if (cityId.length() > 0)
					orderBean.setCityId(cityId);

				String productCurrencyLable = (String) Adp.getValueAt(i, 26);
				String productCurrencyCd = (String) Adp.getValueAt(i, 27);

				String basketId = (String) Adp.getValueAt(i, 28);
				orderBean.setBasketId(basketId);
				String paystatusLable = (String) Adp.getValueAt(i, 29);
				orderBean.setPaystatusLable(paystatusLable);
				String orderPaystatus = (String) Adp.getValueAt(i, 30);
				orderBean.setOrderPaystatus(orderPaystatus);
				String fileId = Adp.getValueAt(i, 31) == null ? "" : Adp.getValueAt(i, 31);

				String deliverystatusId = (String) Adp.getValueAt(i, 32);
				orderBean.setDeliverystatusId(deliverystatusId);

				String orderStatus = (String) Adp.getValueAt(i, 33);
				orderBean.setOrderStatus(orderStatus);

				String quantity = (String) Adp.getValueAt(i, 34);

				if (fileId.length() > 0) {
					fileExist = true;
				} else {
					fileExist = false;
				}

//		String strImgURL;
//		if (img_url != null) strImgURL = img_url;
//		else strImgURL = "images/Folder.jpg";

				table.append("<product>\n");
				table.append("<basket_id>" + basketId + "</basket_id>\n");
				// <product_url>http://<%= request.getServerName()
				// %>:<%=request.getServerPort()%>/<jsp:getProperty
				// name="productInfoBeanId" property="productURL" /></product_url>
				table.append("<product_url>" + "http://" + request.getServerName() + ":" + request.getServerPort()
						+ "/downloadservletbyodrder?basket_id=" + basketId + "</product_url>\n");
				table.append("<file_exist>" + (fileExist ? "true" : "") + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + orderBean.getImgUrl() + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<policy_url>" + "" + "</policy_url>\n");
				table.append("<item_info>" + "" + "</item_info>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<quantity>" + quantity + "</quantity>\n");
				table.append("<currency>\n");
				table.append("<code>" + productCurrencyCd + "</code>\n");
				table.append("<description>" + productCurrencyLable + "</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + "" + "</version>\n");
				table.append("</product>\n");
			}

			table.append("</list>\n");
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
			throw ex;
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
			throw ex;
		} finally {
			Adp.close();
		}

		return table.toString();
	}

	final public String getOrderlist(long userId, final OrderListBean orderListBean, Locale locale) {
		String orderId = "";
		String endAmount = "0";
		String paystatusId = "0";
		String cdate = "";
		String paystatusLable = "";
		orderListBean.setListup("OrderList.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("OrderList.jsp?offset=0");
		else
			orderListBean.setListdown("OrderList.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT " + "orders.order_id," + "orders.end_amount, " + "orders.cdate , " + "paystatus.lable , "
				+ "paystatus.paystatus_id  , " + "currency.currency_lable " + " FROM orders "
				+ " LEFT  JOIN paystatus ON orders.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT OUTER   JOIN currency ON orders.currency_id = currency.currency_id "
				+ "WHERE orders.user_id = ? ORDER BY orders.order_id DESC  "; // + user_id ; //+ " LIMIT 10 OFFSET "+
																				// orderListBean.getOffset();

		Object[] args = new Object[1];
		args[0] = Long.valueOf(userId);

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				endAmount = (String) Adp.getValueAt(i, 1);
				cdate = (String) Adp.getValueAt(i, 2);
				paystatusLable = (String) Adp.getValueAt(i, 3);
				paystatusId = (String) Adp.getValueAt(i, 4);
				orderListBean.setCurrencyLable((String) Adp.getValueAt(i, 5));
				table.append("<order>\n");
				table.append("<order_id>" + orderId + "</order_id>\n");
				table.append("<end_amount>" + getStrFormatNumberFloat(endAmount) + "</end_amount>\n");
				try {
					table.append("<cdate>"
							+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate))
							+ "</cdate>\n");
				} catch (ParseException ex) {
					log.error(ex);
				}
				table.append("<paystatus_id>" + paystatusId + "</paystatus_id>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<currency_lable>" + orderListBean.getCurrencyLable() + "</currency_lable>\n");
				table.append("</order>\n");
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

	final public String getOrderlistByDate(final long userId, final OrderListBean orderListBean, final Locale locale,
			long roleId, String siteId) {

		String orderId = "";
		String endAmount = "0";
		String paystatusId = "0";
		String cdate = "";
		String paystatusLable = "";
		orderListBean.setListup("OrderList.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("OrderList.jsp?offset=0");
		else
			orderListBean.setListdown("OrderList.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		Object[] args = null;
		if (com.cbsinc.cms.controllers.SiteRole.canProcessOrders(roleId)) {
			query = "SELECT " + "orders.order_id," + "orders.end_amount, " + "orders.cdate , " + "paystatus.lable , "
					+ "paystatus.paystatus_id  , " + "currency.currency_lable " + " FROM orders "
					+ " LEFT  JOIN paystatus ON orders.paystatus_id  =  paystatus.paystatus_id "
					+ " LEFT OUTER   JOIN currency ON orders.currency_id = currency.currency_id "
					+ " LEFT JOIN tuser ON orders.user_id = tuser.user_id "
					+ "WHERE tuser.site_id  = ? and orders.cdate >= ? and orders.cdate <=  ? "; // LIMIT 10 OFFSET ? " ;

			args = new Object[3];
			args[0] = Long.valueOf(siteId);
			args[1] = orderListBean.getSQLDateFrom();
			args[2] = orderListBean.getSQLDateTo();

		} else {
			query = "SELECT " + "orders.order_id," + "orders.end_amount, " + "orders.cdate , " + "paystatus.lable , "
					+ "paystatus.paystatus_id  , " + "currency.currency_lable " + " FROM orders "
					+ " LEFT  JOIN paystatus ON orders.paystatus_id  =  paystatus.paystatus_id "
					+ " LEFT OUTER   JOIN currency ON orders.currency_id = currency.currency_id "
					+ "WHERE orders.user_id = ? and orders.cdate >= ? and orders.cdate <=  ? "; // LIMIT 10 OFFSET ? " ;

			args = new Object[3];
			args[0] = Long.valueOf(userId);
			args[1] = orderListBean.getSQLDateFrom();
			args[2] = orderListBean.getSQLDateTo();

		}

		// orders.cdate > ? and orders.cdate < ?

		// else query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" FROM \"soft\"
		// WHERE \"soft\".\"type_id\" = " + type_id + " and
		// \"soft\".\"phonetype_id\" = " + phonetype_id + " and
		// \"soft\".\"progname_id\" = " + progname_id + " and
		// \"soft\".\"soft_id\" = func_soft_position_id(\"soft\".\"type_id\")
		// limit 10 offset " + offset ;
		// query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" FROM \"soft\"
		// WHERE \"soft\".\"type_id\" = " + type_id + " and
		// \"soft\".\"phonetype_id\" = " + phonetype_id + " and
		// \"soft\".\"soft_id\" = func_soft_position_id(\"soft\".\"type_id\")
		// limit 5 offset " + offset ;

		// args[3] = orderListBean.getOffset();

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());
			// Adp.executeQuery(query);

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				endAmount = (String) Adp.getValueAt(i, 1);
				cdate = (String) Adp.getValueAt(i, 2);
				paystatusLable = (String) Adp.getValueAt(i, 3);
				paystatusId = (String) Adp.getValueAt(i, 4);
				orderListBean.setCurrencyLable((String) Adp.getValueAt(i, 5));
				table.append("<order>\n");
				table.append("<order_id>" + orderId + "</order_id>\n");
				table.append("<end_amount>" + getStrFormatNumberFloat(endAmount) + "</end_amount>\n");
				try {
					table.append("<cdate>"
							+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate))
							+ "</cdate>\n");
				} catch (Exception ex) {
					log.error(ex);
				}
				table.append("<paystatus_id>" + paystatusId + "</paystatus_id>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<currency_lable>" + orderListBean.getCurrencyLable() + "</currency_lable>\n");
				table.append("</order>\n");
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

	final public String getOrderlistByStatus(final String siteId, final OrderListBean orderListBean,
			final Locale locale) {

		String orderId = "";
		String endAmount = "0";
		String paystatusId = "0";
		String cdate = "";
		String paystatusLable = "";
		orderListBean.setListup("OrderList.jsp?offset=" + (orderListBean.getOffset() + 10));
		if (orderListBean.getOffset() - 10 < 0)
			orderListBean.setListdown("OrderList.jsp?offset=0");
		else
			orderListBean.setListdown("OrderList.jsp?offset=" + (orderListBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "SELECT " + "orders.order_id," + "orders.end_amount, " + "orders.cdate , " + "paystatus.lable , "
				+ "paystatus.paystatus_id  , " + "currency.currency_lable " + " FROM orders "
				+ " LEFT  JOIN paystatus ON orders.paystatus_id  =  paystatus.paystatus_id "
				+ " LEFT OUTER   JOIN currency ON orders.currency_id = currency.currency_id "
				+ " LEFT JOIN tuser ON orders.user_id = tuser.user_id "
				+ "WHERE tuser.site_id  = ? and ( orders.paystatus_id = ? or orders.deliverystatus_id =  ? )"; // LIMIT
																												// 10
																												// OFFSET
																												// ? " ;

		Object[] args = new Object[3];
		args[0] = Long.valueOf(siteId);
		args[1] = orderListBean.getOrderPaystatusId();
		args[2] = orderListBean.getDeliverystatusId();
		// args[3] = orderListBean.getOffset();

		try {
			Adp.executeQueryWithArgs(query, args, 10, orderListBean.getOffset());
			// Adp.executeQuery(query);

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				orderId = (String) Adp.getValueAt(i, 0);
				endAmount = (String) Adp.getValueAt(i, 1);
				cdate = (String) Adp.getValueAt(i, 2);
				paystatusLable = (String) Adp.getValueAt(i, 3);
				paystatusId = (String) Adp.getValueAt(i, 4);
				orderListBean.setCurrencyLable((String) Adp.getValueAt(i, 5));
				table.append("<order>\n");
				table.append("<order_id>" + orderId + "</order_id>\n");
				table.append("<end_amount>" + getStrFormatNumberFloat(endAmount) + "</end_amount>\n");
				try {
					table.append("<cdate>"
							+ orderListBean.getSimpleDateFormat(locale).format(Adp.getSimpleDateFormat().parse(cdate))
							+ "</cdate>\n");
				} catch (Exception ex) {
					log.error(ex);
				}
				table.append("<paystatus_id>" + paystatusId + "</paystatus_id>\n");
				table.append("<paystatus_lable>" + paystatusLable + "</paystatus_lable>\n");
				table.append("<currency_lable>" + orderListBean.getCurrencyLable() + "</currency_lable>\n");
				table.append("</order>\n");
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

	final public String getPaymentlistByDate(final long intUserID, final long roleId,
			final AccountHistoryBean accountHistoryBean) {

		String[] arrayAmountId = new String[10];
		String addAmount = "";
		String oldAmount = "";
		String dateInput = "";
		String dateEnd = "";
		String sysdate = "";
		String complete = "";
		String decsription = "";
		String active = "";
		String amountId = "";
		String totalAmount = "";
		String currencyAddLable = "";
		String currencyOldLable = "";
		String currencyTotalLable = "";
		String rezultCd = "";

		accountHistoryBean.setCururl("AccountHistory.jsp?offset=" + accountHistoryBean.getOffset());
		accountHistoryBean.setListup("AccountHistory.jsp?offset=" + (accountHistoryBean.getOffset() + 10));

		if (accountHistoryBean.getOffset() - 10 < 0)
			accountHistoryBean.setListdown("AccountHistory.jsp?offset=0");
		else
			accountHistoryBean.setListdown("AccountHistory.jsp?offset=" + (accountHistoryBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.user_id = ? and account_hist.sysdate >= ? and account_hist.sysdate <=  ? "
				+ " ORDER BY account_hist.id DESC "; // limit 10 offset " + accountHistoryBean.getOffset();

		Object[] args = new Object[3];
		args[0] = intUserID;
		args[1] = accountHistoryBean.getSQLDateFrom();
		args[2] = accountHistoryBean.getSQLDateTo();

		try {
			Adp.executeQueryWithArgs(query, args, 10, accountHistoryBean.getOffset());

			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {

				addAmount = (String) Adp.getValueAt(i, 0);
				oldAmount = (String) Adp.getValueAt(i, 1);
				dateInput = (String) Adp.getValueAt(i, 2);
				dateEnd = (String) Adp.getValueAt(i, 3);
				sysdate = (String) Adp.getValueAt(i, 4);
				complete = (String) Adp.getValueAt(i, 5);
				decsription = (String) Adp.getValueAt(i, 6);
				active = (String) Adp.getValueAt(i, 7);
				amountId = (String) Adp.getValueAt(i, 8);
				totalAmount = (String) Adp.getValueAt(i, 9);
				currencyAddLable = (String) Adp.getValueAt(i, 10);
				currencyOldLable = (String) Adp.getValueAt(i, 11);
				currencyTotalLable = (String) Adp.getValueAt(i, 12);
				rezultCd = (String) Adp.getValueAt(i, 13);
				arrayAmountId[i] = amountId;

				table.append("<payment>\n");
				table.append("<amount_id>" + amountId + "</amount_id>\n");
				table.append("<currency_add_lable>" + currencyAddLable + "</currency_add_lable>\n");
				table.append("<add_amount>" + addAmount + "</add_amount>\n");
				table.append("<sysdate>" + sysdate + "</sysdate>\n");
				table.append("<complete>" + complete + "</complete>\n");
				table.append("<rezult_cd>" + rezultCd + "</rezult_cd>\n");
				table.append("</payment>\n");
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

	final public String getPaymentlist(final long intUserID, final long roleId,
			final AccountHistoryBean accountHistoryBean) {

		String[] arrayAmountId = new String[10];
		String addAmount = "";
		String oldAmount = "";
		String dateInput = "";
		String dateEnd = "";
		String sysdate = "";
		String complete = "";
		String decsription = "";
		String active = "";
		String amountId = "";
		String totalAmount = "";
		String currencyAddLable = "";
		String currencyOldLable = "";
		String currencyTotalLable = "";
		String rezultCd = "";

		accountHistoryBean.setCururl("AccountHistory.jsp?offset=" + accountHistoryBean.getOffset());
		accountHistoryBean.setListup("AccountHistory.jsp?offset=" + (accountHistoryBean.getOffset() + 10));

		if (accountHistoryBean.getOffset() - 10 < 0)
			accountHistoryBean.setListdown("AccountHistory.jsp?offset=0");
		else
			accountHistoryBean.setListdown("AccountHistory.jsp?offset=" + (accountHistoryBean.getOffset() - 10));

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.user_id = ?  and account_hist.complete = true  ORDER BY account_hist.id DESC "; // limit
																														// 10
																														// offset
																														// "
																														// +
																														// accountHistoryBean.getOffset();

		try {

			Object[] args = new Object[1];
			args[0] = intUserID;
			Adp.executeQueryWithArgs(query, args, 10, accountHistoryBean.getOffset());
			table.append("<list>\n");
			for (int i = 0; Adp.rows().size() > i; i++) {

				addAmount = (String) Adp.getValueAt(i, 0);
				oldAmount = (String) Adp.getValueAt(i, 1);
				dateInput = (String) Adp.getValueAt(i, 2);
				dateEnd = (String) Adp.getValueAt(i, 3);
				sysdate = (String) Adp.getValueAt(i, 4);
				complete = (String) Adp.getValueAt(i, 5);
				decsription = (String) Adp.getValueAt(i, 6);
				active = (String) Adp.getValueAt(i, 7);
				amountId = (String) Adp.getValueAt(i, 8);
				totalAmount = (String) Adp.getValueAt(i, 9);
				currencyAddLable = (String) Adp.getValueAt(i, 10);
				currencyOldLable = (String) Adp.getValueAt(i, 11);
				currencyTotalLable = (String) Adp.getValueAt(i, 12);
				rezultCd = (String) Adp.getValueAt(i, 13);
				arrayAmountId[i] = amountId;

				table.append("<payment>\n");
				table.append("<amount_id>" + amountId + "</amount_id>\n");
				table.append("<currency_add_lable>" + currencyAddLable + "</currency_add_lable>\n");
				table.append("<add_amount>" + addAmount + "</add_amount>\n");
				table.append("<sysdate>" + sysdate + "</sysdate>\n");
				table.append("<complete>" + complete + "</complete>\n");
				table.append("<rezult_cd>" + rezultCd + "</rezult_cd>\n");
				table.append("</payment>\n");
			}

		} catch (SQLException ex) {

			log.error(query, ex);

		} catch (Exception ex) {

			log.error(ex);

		} finally {
			table.append("</list>\n");
			Adp.close();
		}

		return table.toString();

	}

	/**
	 * Marks an order as payment-in-process by setting orders.paystatus_id = 2.
	 * Errors are logged and the transaction rolled back; no exception is
	 * propagated to the caller for the logged cases.
	 *
	 * @param order_id id of the order to update
	 * @throws Exception if a checked failure escapes the logged paths
	 */
	public void setStatusInrocess(String orderId) throws Exception {

		String query = "";
		QueryManager Adp = new QueryManager();
		try {
			Adp.beginTransaction();

			query = "update orders  set paystatus_id = 2 where order_id = " + orderId;
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}
	}

	final public String getPayment(final long intUserID, final long roleId,
			final AccountHistoryDetalBean accountHistoryDetalBean) {

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.user_ip , account_hist.user_header , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.id = ? ";

		try {
			Object[] args = new Object[1];
			args[0] = Long.valueOf(accountHistoryDetalBean.getAmountId());
			Adp.executeQueryWithArgs(query, args);

			accountHistoryDetalBean.setAddAmount((String) Adp.getValueAt(0, 0));

			// add_amount = (String) Adp.getValueAt(0, 0);
			accountHistoryDetalBean.setOldAmount((String) Adp.getValueAt(0, 1));
			// old_amount = (String) Adp.getValueAt(0, 1);

			// date_input = (String) Adp.getValueAt(0, 2);

			accountHistoryDetalBean.setDateInput((String) Adp.getValueAt(0, 2));

			// date_end = (String) Adp.getValueAt(0, 3);

			accountHistoryDetalBean.setDateEnd((String) Adp.getValueAt(0, 3));

			// sysdate = (String) Adp.getValueAt(0, 4);
			accountHistoryDetalBean.setSysdate((String) Adp.getValueAt(0, 4));

			// complete = (String) Adp.getValueAt(0, 5);

			accountHistoryDetalBean.setComplete((String) Adp.getValueAt(0, 5));

			// decsription = (String) Adp.getValueAt(0, 6);

			accountHistoryDetalBean.setDecsription((String) Adp.getValueAt(0, 6));

			// active = (String) Adp.getValueAt(0, 7);

			accountHistoryDetalBean.setActive((String) Adp.getValueAt(0, 7));

			// amount_id = (String) Adp.getValueAt(0, 8);

			accountHistoryDetalBean.setAmountId((String) Adp.getValueAt(0, 8));

			// total_amount = (String) Adp.getValueAt(0, 9);

			accountHistoryDetalBean.setTotalAmount((String) Adp.getValueAt(0, 9));

			// currency_add_lable = (String) Adp.getValueAt(0, 10);

			accountHistoryDetalBean.setCurrencyAddLable((String) Adp.getValueAt(0, 10));

			// currency_old_lable = (String) Adp.getValueAt(0, 11);

			accountHistoryDetalBean.setCurrencyOldLable((String) Adp.getValueAt(0, 11));

			// currency_total_lable = (String) Adp.getValueAt(0, 12);

			accountHistoryDetalBean.setCurrencyTotalLable((String) Adp.getValueAt(0, 12));

			// user_ip = (String) Adp.getValueAt(0, 13);

			accountHistoryDetalBean.setUserIp((String) Adp.getValueAt(0, 13));

			// user_header = (String) Adp.getValueAt(0, 14);

			accountHistoryDetalBean.setUserHeader((String) Adp.getValueAt(0, 14));

			// rezult_cd = (String) Adp.getValueAt(0, 15);

			accountHistoryDetalBean.setRezultCd((String) Adp.getValueAt(0, 15));

			table.append("<payment>\n");

			table.append("<add_amount>" + getStrFormatNumberFloat(accountHistoryDetalBean.getAddAmount())
					+ "</add_amount>\n");
			table.append("<old_amount>" + getStrFormatNumberFloat(accountHistoryDetalBean.getOldAmount())
					+ "</old_amount>\n");
			table.append("<date_input>" + accountHistoryDetalBean.getDateInput() + "</date_input>\n");
			table.append("<date_end>" + accountHistoryDetalBean.getDateEnd() + "</date_end>\n");
			table.append("<sysdate>" + accountHistoryDetalBean.getSysdate() + "</sysdate>\n");
			table.append("<complete>" + accountHistoryDetalBean.getComplete() + "</complete>\n");
			table.append("<decsription>" + accountHistoryDetalBean.getDecsription() + "</decsription>\n");
			table.append("<active>" + accountHistoryDetalBean.getActive() + "</active>\n");
			table.append("<amount_id>" + accountHistoryDetalBean.getAmountId() + "</amount_id>\n");
			table.append("<total_amount>" + getStrFormatNumberFloat(accountHistoryDetalBean.getTotalAmount())
					+ "</total_amount>\n");
			table.append("<currency_add_lable>" + accountHistoryDetalBean.getCurrencyAddLable()
					+ "</currency_add_lable>\n");
			table.append("<currency_old_lable>" + accountHistoryDetalBean.getCurrencyOldLable()
					+ "</currency_old_lable>\n");
			table.append("<currency_total_lable>" + accountHistoryDetalBean.getCurrencyTotalLable()
					+ "</currency_total_lable>\n");
			table.append("<user_ip>" + accountHistoryDetalBean.getUserIp() + "</user_ip>\n");
			table.append("<user_header>" + accountHistoryDetalBean.getUserHeader() + "</user_header>\n");
			table.append("<rezult_cd>" + accountHistoryDetalBean.getRezultCd() + "</rezult_cd>\n");
			table.append("</payment>\n");

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
	 * The customer's carrier choice, or 0 when none was made. The value has
	 * already been checked against shipping_company by loadShippingCompany();
	 * this only guards the numeric form for the UPDATE binding, which cannot
	 * take null.
	 */
	static String shippingCompanyOrZero(OrderBean orderBean) {
		String v = orderBean.getShippingCompanyId();
		return v != null && v.matches("-?[0-9]{1,18}") ? v : "0";
	}

	/**
	 * Option list of carriers for the order page. Every carrier is a row in
	 * shipping_company and owns a site (shipping_company.site_id); only
	 * carriers whose site is active are offered.
	 */
	final public String getShippingCompanyList(final OrderBean orderBean) {
		// FIX (empty "Shipping company" dropdown): this used to join site and keep
		// only "s.active = true or s.site_id is null" rows. Once the carriers point
		// at the shop site itself, the LEFT JOIN *does* match a site row, so the
		// "s.site_id is null" branch no longer applies and the whole list depends on
		// that one site's active flag: if the shop's site row is not active the query
		// returns nothing. getXMLDBList catches the SQLException (and an empty
		// result) and still returns a well-formed <shipping_company> element, so the
		// page showed an empty dropdown with no error anywhere - the exact symptom.
		// The carriers of a shop are its own rows, so read them directly; ordering by
		// name keeps the dropdown stable and needs no join to site at all.
		return getXMLDBList("Order.jsp?shipping_company_id", "shipping_company", orderBean.getShippingCompanyId(),
				"select sc.shipping_company_id , sc.name from shipping_company sc order by sc.name");
	}

	/**
	 * Resolves the chosen carrier into name / site for the template and
	 * rejects ids that do not exist. Returns false (and resets the choice to 0)
	 * if the id is not a shipping company.
	 */
	final public boolean loadShippingCompany(final OrderBean orderBean) {
		String id = orderBean.getShippingCompanyId();
		orderBean.setShippingCompanyName("");
		orderBean.setShippingCompanySiteId("0");
		orderBean.setShippingCompanyHost("");
		// "-1" is the empty first option that getXMLDBList emits
		if (id == null || "0".equals(id) || "-1".equals(id) || !id.matches("-?[0-9]{1,18}")) {
			orderBean.setShippingCompanyId("0");
			return true;
		}
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select sc.name , sc.site_id , s.host from shipping_company sc"
					+ " left join site s on s.site_id = sc.site_id where sc.shipping_company_id = ?",
					new Object[] { Long.valueOf(id) });
			if (qm.rows().size() == 0) {
				log.warn("Order " + orderBean.getOrderId() + ": unknown shipping_company_id " + id + "; ignored");
				orderBean.setShippingCompanyId("0");
				return false;
			}
			orderBean.setShippingCompanyName((String) qm.getValueAt(0, 0));
			orderBean.setShippingCompanySiteId((String) qm.getValueAt(0, 1));
			orderBean.setShippingCompanyHost((String) qm.getValueAt(0, 2));
			return true;
		} catch (Exception ex) {
			log.error("loadShippingCompany " + id, ex);
			orderBean.setShippingCompanyId("0");
			return false;
		} finally {
			qm.close();
		}
	}

	/** Reads the carrier stored on an existing order back into the bean. */
	final public void readShippingCompany(final OrderBean orderBean) {
		if (orderBean.getOrderId() == null || !orderBean.getOrderId().matches("[0-9]{1,18}"))
			return;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select shipping_company_id from orders where order_id = ?",
					new Object[] { Long.valueOf(orderBean.getOrderId()) });
			if (qm.rows().size() > 0 && qm.getValueAt(0, 0) != null)
				orderBean.setShippingCompanyId((String) qm.getValueAt(0, 0));
		} catch (Exception ex) {
			log.error("readShippingCompany " + orderBean.getOrderId(), ex);
		} finally {
			qm.close();
		}
	}


	// ------------------------------------------------------------------
	// Resolution center
	// ------------------------------------------------------------------

	private static volatile Boolean resolutionColumns = null;

	/**
	 * orders.resolution_center_id / resolution_status_id come from
	 * sql/resolution_center_migration.sql. Detected once; without them the
	 * order page does not offer a resolution center and nothing is written.
	 */
	final public boolean resolutionColumnsPresent() {
		Boolean v = resolutionColumns;
		if (v == null) {
			boolean found = false;
			QueryManager qm = new QueryManager();
			try {
				// getCurrentConnection() is a stub, so
				// probe with a query: it throws SQLException if the column
				// does not exist and returns no rows otherwise. Logged once.
				qm.executeQuery("select resolution_center_id , resolution_status_id from orders where 1 = 0");
				found = true;
			} catch (Exception ex) {
				found = false;
			} finally {
				qm.close();
			}
			if (!found)
				log.info("orders has no resolution_center_id/resolution_status_id; run "
						+ "sql/resolution_center_migration.sql to enable disputes on orders.");
			resolutionColumns = v = found;
		}
		return v;
	}

	private String resolutionSetFragment() {
		return resolutionColumnsPresent() ? " resolution_center_id = ? , resolution_status_id = ? , " : "";
	}

	private void putResolutionArgs(Map args, OrderBean orderBean) {
		if (!resolutionColumnsPresent())
			return;
		String rc = orderBean.getResolutionCenterId();
		String st = orderBean.getResolutionStatusId();
		if (rc == null || !rc.matches("-?[0-9]{1,18}"))
			rc = "0";
		if (st == null || !st.matches("-?[0-9]{1,18}") || "0".equals(rc))
			st = "0";
		// a newly assigned dispute starts in the "New" folder of the center
		if (!"0".equals(rc) && "0".equals(st))
			st = "" + SpecialCatalog.RESOLUTION_CENTER_NEW;
		args.put("resolution_center_id", Long.valueOf(rc));
		args.put("resolution_status_id", Long.valueOf(st));
	}

	/** Option list of resolution centers whose site is active; first entry = none. */
	final public String getResolutionCenterList(final OrderBean orderBean) {
		return getXMLDBList("Order.jsp?resolution_center_id", "resolution_center", orderBean.getResolutionCenterId(),
				"select rc.resolution_center_id , rc.name from resolution_center rc"
						+ " left join site s on s.site_id = rc.site_id"
						+ " where s.active = true or s.site_id is null order by rc.name");
	}

	/**
	 * Resolves the chosen center into name / site / host and the current
	 * status label; unknown ids are reset to 0 and logged.
	 */
	final public boolean loadResolutionCenter(final OrderBean orderBean) {
		String id = orderBean.getResolutionCenterId();
		orderBean.setResolutionCenterName("");
		orderBean.setResolutionCenterSiteId("0");
		orderBean.setResolutionCenterHost("");
		orderBean.setResolutionStatusLable("");
		if (id == null || "0".equals(id) || "-1".equals(id) || !id.matches("-?[0-9]{1,18}")) {
			orderBean.setResolutionCenterId("0");
			orderBean.setResolutionStatusId("0");
			return true;
		}
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select rc.name , rc.site_id , s.host from resolution_center rc"
					+ " left join site s on s.site_id = rc.site_id where rc.resolution_center_id = ?",
					new Object[] { Long.valueOf(id) });
			if (qm.rows().size() == 0) {
				log.warn("Order " + orderBean.getOrderId() + ": unknown resolution_center_id " + id + "; ignored");
				orderBean.setResolutionCenterId("0");
				orderBean.setResolutionStatusId("0");
				return false;
			}
			orderBean.setResolutionCenterName((String) qm.getValueAt(0, 0));
			orderBean.setResolutionCenterSiteId((String) qm.getValueAt(0, 1));
			orderBean.setResolutionCenterHost((String) qm.getValueAt(0, 2));
			String st = orderBean.getResolutionStatusId();
			if (st != null && st.matches("-?[0-9]{1,18}") && !"0".equals(st)) {
				qm.executeQueryWithArgs("select lable from catalog where catalog_id = ? and site_id = ?",
						new Object[] { Long.valueOf(st), Long.valueOf(orderBean.getResolutionCenterSiteId()) });
				if (qm.rows().size() > 0)
					orderBean.setResolutionStatusLable((String) qm.getValueAt(0, 0));
			}
			return true;
		} catch (Exception ex) {
			log.error("loadResolutionCenter " + id, ex);
			orderBean.setResolutionCenterId("0");
			return false;
		} finally {
			qm.close();
		}
	}

	/** Reads the resolution center / status stored on the order (if the columns exist). */
	final public void readResolutionCenter(final OrderBean orderBean) {
		if (!resolutionColumnsPresent())
			return;
		if (orderBean.getOrderId() == null || !orderBean.getOrderId().matches("[0-9]{1,18}"))
			return;
		QueryManager qm = new QueryManager();
		try {
			qm.executeQueryWithArgs("select resolution_center_id , resolution_status_id from orders where order_id = ?",
					new Object[] { Long.valueOf(orderBean.getOrderId()) });
			if (qm.rows().size() > 0) {
				if (qm.getValueAt(0, 0) != null)
					orderBean.setResolutionCenterId((String) qm.getValueAt(0, 0));
				if (qm.getValueAt(0, 1) != null)
					orderBean.setResolutionStatusId((String) qm.getValueAt(0, 1));
			}
		} catch (Exception ex) {
			log.error("readResolutionCenter " + orderBean.getOrderId(), ex);
		} finally {
			qm.close();
		}
	}

}
