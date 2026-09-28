package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

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
@PageModel(Id = "operationAmountBeanId", scope = Scope.SESSION)
//@PageXsltView(jspName = "Pay.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class OperationAmountBean implements java.io.Serializable {

	transient private static final long serialVersionUID = -5629328315307393690L;

	transient static private Logger log = Logger.getLogger(OperationAmountBean.class);

	transient ResourceBundle sequencesRs = null;

	public OperationAmountBean() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
	}

	public float getConvCurrency(String strCurrencyIDFrom, String strCurrencyIDTo) {
		float floRate = 0;
		String strRezult = "";
		// String query = "SELECT account.amount, currency.currency_lable,
		// currency.currency_desc FROM account LEFT OUTER JOIN public.currency
		// ON account.currency_id = currency.currency_id WHERE account.user_id =
		// " + intUserID ;
		String query = "SELECT " + strCurrencyIDTo + " FROM currency_converter WHERE currency_id = "
				+ strCurrencyIDFrom;

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			if (Adp.rows().size() != 0) {
				strRezult = (String) Adp.getValueAt(0, 0);
			} else {
				strRezult = "0";
			}
			floRate = new Float(strRezult).floatValue();

		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

		return floRate;
	}

	public String setBuy(String softId, int intUserID, String orderId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		float floRate = 1; // Curs
		String strID;
		String oldAmount = "0";
		String strCurrencyIDAccount = "0";

		String strProductName = "";
		String strDescriptionProduct = "";
		String strVersionProduct = "";
		String strCostProduct = "";
		String strCurrencyIDProduct = "0";
		String strTaxProduct = "0";
		float floRezultAmount = 0;
		float floWithtaxRezultAmount = 0;

		// String query = "SELECT NEXT VALUE FOR account_hist_id_seq AS ID FROM
		// ONE_SEQUENCES";
		String query = sequencesRs.getString("account_hist");
		try {

			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);
			query = "SELECT  account.amount, account.currency_id , currency.currency_lable, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
					+ intUserID;

			Adp.executeQuery(query);
			oldAmount = (String) Adp.getValueAt(0, 0);
			strCurrencyIDAccount = (String) Adp.getValueAt(0, 1);

			// query = "SELECT \"soft\".\"name\",\"soft\".\"description\",
			// \"soft\".\"version\", \"soft\".\"cost\", \"soft\".\"currency\",
			// \"soft\".\"serial_nubmer\" , \"typesoft \".\"type_lable\" ,
			// \"typesoft \".\"tax\" FROM \"soft\" LEFT JOIN \"typesoft \" ON
			// \"soft\".\"type_id\" = \"typesoft\".\"type_id\" WHERE
			// \"soft\".\"soft_id\" = " + soft_id ;
			query = "SELECT  soft.name, soft.description , soft.version, soft.cost , soft.currency, soft.serial_nubmer , catalog.lable , catalog.tax  FROM soft LEFT  JOIN catalog  ON soft.catalog_id = catalog.catalog_id WHERE soft.soft_id = "
					+ softId;
			Adp.executeQuery(query);

			strProductName = (String) Adp.getValueAt(0, 0);
			strDescriptionProduct = (String) Adp.getValueAt(0, 1);
			strVersionProduct = (String) Adp.getValueAt(0, 2);
			strCostProduct = (String) Adp.getValueAt(0, 3);
			strCurrencyIDProduct = (String) Adp.getValueAt(0, 4);
			strTaxProduct = (String) Adp.getValueAt(0, 7);

			// float floRate = getConvCurrency( strCurrency_IDAccount ,
			// strCurrency_IDProduct) ;

			// floRezult_amount = Float.parseFloat(old_amount) - (
			// Float.parseFloat(strCostProduct) * floRate);
			floRezultAmount = Float.parseFloat(oldAmount) - Float.parseFloat(strCostProduct);
			floWithtaxRezultAmount = floRezultAmount - Float.parseFloat(strTaxProduct);

			query = "insert into account_hist " + "(" + " id  , " + " user_id , " + " order_id , " + " add_amount , "
					+ " old_amount  , " + " date_input , " + " complete   , " + " decsription  , "
					+ " currency_id_add  , " + " currency_id_old  , " + " currency_id_total  , " + " active  , "
					+ " rezult_cd , " + " date_end , " + "account_hist.sysdate , " + " total_amount  , " + " tax  , "
					+ " withtax_total_amount  " + ")" + " VALUES " + "( " + strID + ", " + intUserID + ", " + orderId
					+ ", " + strCostProduct + ", " + oldAmount + ", " + "now()" + ", " + "true" + ", '"
					+ "Bought product " + strProductName + ", " + strDescriptionProduct + ", version "
					+ strVersionProduct + "', " + strCurrencyIDProduct + ", " + strCurrencyIDAccount + ", "
					+ strCurrencyIDAccount + ", " + "false  , '" + "bought" + "', " + "now()" + ", " + "now()" + ", "
					+ floRezultAmount + ", " + strTaxProduct + ", " + floWithtaxRezultAmount + " " + ")";

			Adp.executeUpdate(query);

			query = "UPDATE account SET amount = " + floRezultAmount + " WHERE account.user_id = " + intUserID;

			Adp.executeUpdate(query);
			Adp.commit();

		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(query, ex);
			Adp.rollback();
		}

		finally {
			Adp.close();
		}

		return "";
	}

	/*
	 * return Account ID
	 */

	// public String addMoneyStart( String strDescriptionProduct , String
	// strAmountDebit , String strCurrency_IDAmountDebit , String currency_id ,
	// int intUserID ) {
	public String addMoneyStart(String strDescriptionProduct, double amountDebit, String strCurrencyIDAmountDebit,
			long intUserID, String userIp, String userHeader, String orderId) {
		String query = "";
		String strID = "";
		QueryManager Adp = new QueryManager();
		boolean committed = false;
		// FIX: strDescriptionProduct, user_ip and user_header are spliced into
		// the INSERT below by concatenation. user_header is the full set of
		// request headers, so a User-Agent containing a quote broke every
		// top-up, and a crafted one could rewrite the statement. The column is
		// varchar(500); longer values made the INSERT fail. Escaped and cut.
		strDescriptionProduct = com.cbsinc.cms.utils.Validation.escapeSqlLiteral(strDescriptionProduct);
		userIp = com.cbsinc.cms.utils.Validation.escapeSqlLiteral(userIp);
		if (userHeader != null && userHeader.length() > 480)
			userHeader = userHeader.substring(0, 480);
		userHeader = com.cbsinc.cms.utils.Validation.escapeSqlLiteral(userHeader);
		try {
			Adp.beginTransaction();
			double oldAmount = 0;
			String strCurrencyIDAccount = "0";
			CurrencyHash currencyHash = CurrencyHash.getInstance();
			Currency curr = currencyHash.getCurrency(strCurrencyIDAmountDebit);
			// if( curr == null ) return "" ;
			// float floRate = curr.getRate();
			// float floRate = CurrencyHash.getCurrency()

			double taxProduct = 0;
			double rezultAmount = 0;
			double withtaxRezultAmount = 0;

			// query = "SELECT NEXT VALUE FOR account_hist_id_seq AS ID FROM ONE_SEQUENCES";
			query = sequencesRs.getString("account_hist");
			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);

			query = "SELECT  account.amount, account.currency_id , currency.currency_lable, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
					+ intUserID;
			Adp.executeQuery(query);

			oldAmount = new Double((String) Adp.getValueAt(0, 0)).doubleValue();

			strCurrencyIDAccount = (String) Adp.getValueAt(0, 1);
			rezultAmount = oldAmount + amountDebit;
			withtaxRezultAmount = rezultAmount - taxProduct;

			query = "insert into account_hist " + "(" + " id  , " + " user_id , " + " order_id , " + " add_amount , "
					+ " old_amount  , " + " date_input , " + " complete   , " + " decsription  , "
					+ " currency_id_add  , " + " currency_id_old  , " + " currency_id_total  , " + " active  , "
					+ " account_hist.sysdate , " + " total_amount , " + " tax  , " + " withtax_total_amount ,"
					+ " user_ip ," + " user_header ," + " rate " + ")" + " VALUES " + "( " + strID + ", " + intUserID
					+ ", " + orderId + ", " + amountDebit + ", " + oldAmount + ", " + "now()" + ", " + "false" + ", '"
					+ strDescriptionProduct + "', " + strCurrencyIDAmountDebit + ", " + strCurrencyIDAccount + ", "
					+ strCurrencyIDAccount + ", " + "true  , " + "now()" + ", " + rezultAmount + ", " + taxProduct
					+ ", " + withtaxRezultAmount + ", '" + userIp + "', '" + userHeader + "', " + 0 + " " + ")";

			Adp.executeUpdate(query);
			Adp.commit();
			committed = true;
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(query, ex);
			Adp.rollback();
		}

		finally {
			Adp.close();
		}

		// FIX: on rollback the method still returned the sequence value, so the
		// caller sent the customer to the bank with an order id that did not
		// exist. Callers now get "" and must not proceed.
		if (!committed)
			return "";

		/*
		 * query = "UPDATE account SET complete = false , amount = " + floRezult_amount
		 * + " WHERE account.user_id = " + intUserID ;
		 *
		 * try{ Adp.executeUpdate(query); } catch (SQLException ex) {
		 * System.err.println(query); System.err.println(ex); System.err.println("" +
		 * this.getClass()); System.err.println("Method " + "addMoneyStart()");
		 * Adp.rollback(); Adp.close(); return null ; }
		 */

		return strID;
	}

	/*
	 * public boolean addMoneyEnd( String strAccount_Hist_ID , int intUserID ) {
	 * QueryManager Adp = new
	 * QueryManager("jdbc:postgresql://192.168.0.1:5432/mobilsoft") ;
	 * Adp.BeginTransaction(); String old_amount = "0" ; String
	 * strCurrency_IDAccount = "0" ; String strTaxProduct = "0" ; float
	 * floRezult_amount = 0 ; float floWithtax_rezult_amount = 0 ; String query = ""
	 * ; query = "UPDATE account_hist SET complete = true WHERE account_hist.id = "
	 * + strAccount_Hist_ID ; try { Adp.executeUpdate(query); } catch (SQLException
	 * ex) { System.err.println(query); System.err.println(ex);
	 * System.err.println("" + this.getClass()); System.err.println("Method " +
	 * "addMoneyEnd()"); Adp.rollback(); Adp.close(); return false ; } query =
	 * "UPDATE account SET complete = true WHERE account.user_id = " + intUserID ;
	 * try { Adp.executeUpdate(query); } catch (SQLException ex) {
	 * System.err.println(query); System.err.println(ex); System.err.println("" +
	 * this.getClass()); System.err.println("Method " + "addMoneyEnd()");
	 * Adp.rollback(); Adp.close(); return false ; } Adp.commit(); Adp.close();
	 * return true ; }
	 *
	 */

}
