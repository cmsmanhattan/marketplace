package com.cbsinc.cms;

import java.sql.SQLException;
import java.text.NumberFormat;

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

//@PageXsltView( jspName = "AccountHistory.jsp" , xsltName="accounthistory.xsl" , responseType= Type.XML  )
@PageModel(Id = "accountHistoryBeanId", scope = Scope.SESSION)
public class AccountHistoryBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -5645998100032237469L;

	static private Logger log = Logger.getLogger(AccountHistoryBean.class);

	private String[] arrayAmountId = new String[10];

	private Integer intUserID = 0;

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String typeId = "1";

	private Integer roleId = 0;

	private String cururl;

	private String strLogin = "";

	private String curency = "$";

	private String addAmount = "0";

	private String oldAmount = "0";

	private String dateInput = "";

	private String dateEnd = "";

	private String sysdate = "";

	private String complete = "";

	private String decsription = "";

	private String active = "";

	private String amountId = "";

	private String catalogId = "1";

	private Float fltCreditLimit = Float.valueOf(-10);

	private String totalAmount;

	private String currencyAddLable;

	private String currencyOldLable;

	private String currencyTotalLable;

	private String rezultCd = "";

	private NumberFormat nf;

	private java.util.Calendar calendar;

	public AccountHistoryBean() {
		nf = NumberFormat.getInstance();
		nf.setGroupingUsed(true);
		calendar = java.util.Calendar.getInstance();
	}

	transient long dateFrom = 0;
	transient long dateTo = 0;

	String selectAccountHistoryXML = "";
	String searchquery = "0";

	public String getPaymentlist(int intUserID) {

		cururl = "AccountHistory.jsp?offset=" + offset;
		listup = "AccountHistory.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "AccountHistory.jsp?offset=0";
		else
			listdown = "AccountHistory.jsp?offset=" + (offset - 10);

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.rezult_cd"
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.user_id = " + intUserID + " limit 10 offset " + offset;

		try {
			Adp.executeQuery(query);

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

	public String getPaymentlist(int intUserID, int roleId) {

		cururl = "AccountHistory.jsp?offset=" + offset;
		listup = "AccountHistory.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "AccountHistory.jsp?offset=0";
		else
			listdown = "AccountHistory.jsp?offset=" + (offset - 10);

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();

		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.user_id = " + intUserID + " ORDER BY account_hist.id DESC  limit 10 offset "
				+ offset;

		try {
			Adp.executeQuery(query);

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

	// --------- Business logic functionality start -----

	public int stringToInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
			log.error(ex);
		}
		return i;
	}

	protected float getBalans() {
		return getBalans(intUserID);
	}

	public boolean isCreditable(int strUserId) {
		return isCreditable(strUserId, fltCreditLimit);
	}

	public boolean isCreditable(int strUserId, float fltCreditLimit) {
		boolean rezalt = false;
		String strBalans = "0";
		float fltBalans = 0;
		String strCurrencyID = "1";
		float floRate = 0;
		String query = "SELECT  account.amount, currency.currency_id, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
				+ strUserId;

		QueryManager Adp = new QueryManager();

		try {
			Adp.executeQuery(query);

			strBalans = (String) Adp.getValueAt(0, 0);
			/// strBalans = "" + Float.parseFloat(strBalans);
			// strBalans = nf.format(Double.parseDouble(strBalans)) ;
			fltBalans = Float.parseFloat(strBalans);
			strCurrencyID = (String) Adp.getValueAt(0, 1);
			Adp.close();
			CurrencyHash currencyHash = CurrencyHash.getInstance();
			Currency curr = currencyHash.getCurrency(strCurrencyID);
			if (curr == null) {
				rezalt = false;
				throw new java.lang.UnsupportedOperationException("Object Currency == null");
			}

			floRate = curr.getRate();
			if (floRate == 0) {
				rezalt = false;
				throw new java.lang.UnsupportedOperationException("Currency rate = 0");
			}

			if (fltCreditLimit < fltBalans * floRate)
				rezalt = true;
		} catch (SQLException ex) {

			log.error(query, ex);
		} catch (Exception ex) {

			log.error(ex);
		} finally {
			Adp.close();
		}

		return rezalt;
	}

	// --------- Business logic functionality end -----
	// --------- Properties begib ---------------------

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}

	public String getStrLogin() {
		return strLogin;
	}

	public void setStrLogin(String strLogin) {
		this.strLogin = strLogin;
	}

	public String getCurency() {
		return curency;
	}

	public void setCurency(String curency) {
		this.curency = curency;
	}

	public void setIntUserID(int intUserID) {
		this.intUserID = intUserID;
	}

	public int getIntUserID() {
		return intUserID;
	}

	public String getAddAmount() {
		return addAmount;
	}

	public void setAddAmount(String addAmount) {
		this.addAmount = addAmount;
	}

	public String getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(String oldAmount) {
		this.oldAmount = oldAmount;
	}

	public String getDateInput() {
		return dateInput;
	}

	public void setDateInput(String dateInput) {
		this.dateInput = dateInput;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getDecsription() {
		return decsription;
	}

	public void setDecsription(String decsription) {
		this.decsription = decsription;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String[] getArrayAmountId() {
		return arrayAmountId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public float getIntCreditLimit() {
		return fltCreditLimit;
	}

	public void setIntCreditLimit(float fltCreditLimit) {
		this.fltCreditLimit = fltCreditLimit;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public String getListdown() {
		return listdown;
	}

	public String getListup() {
		return listup;
	}

	public void setListdown(String listdown) {
		this.listdown = listdown;
	}

	public void setListup(String listup) {
		this.listup = listup;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrencyAddLable() {
		return currencyAddLable;
	}

	public void setCurrencyAddLable(String currencyAddLable) {
		this.currencyAddLable = currencyAddLable;
	}

	public String getCurrencyOldLable() {
		return currencyOldLable;
	}

	public void setCurrencyOldLable(String currencyOldLable) {
		this.currencyOldLable = currencyOldLable;
	}

	public String getCurrencyTotalLable() {
		return currencyTotalLable;
	}

	public void setCurrencyTotalLable(String currencyTotalLable) {
		this.currencyTotalLable = currencyTotalLable;
	}

	public java.util.Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(java.util.Calendar calendar) {
		this.calendar = calendar;
	}

	public long getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(long dateFrom) {
		this.dateFrom = dateFrom;
	}

	public long getDateTo() {
		return dateTo;
	}

	public void setDateTo(long dateTo) {
		this.dateTo = dateTo;
	}

	/*
	 * String dateTo Input format is "dd/mm/yyyy"
	 */
	public void setStrDateTo(String dateTo) {
		if (dateTo.split("/").length > 2) {
			getCalendar().set(Integer.parseInt(dateTo.split("/")[2]), Integer.parseInt(dateTo.split("/")[1]) - 1,
					Integer.parseInt(dateTo.split("/")[0]), 0, 1);
			this.dateTo = getCalendar().getTimeInMillis();
		}
	}

	/*
	 * String dateFrom Input format is "dd/mm/yyyy"
	 */
	public void setStrDateFrom(String dateFrom) {
		if (dateFrom.split("/").length > 2) {
			getCalendar().set(Integer.parseInt(dateFrom.split("/")[2]), Integer.parseInt(dateFrom.split("/")[1]) - 1,
					Integer.parseInt(dateFrom.split("/")[0]), 0, 1);
			this.dateFrom = getCalendar().getTimeInMillis();
		}
	}

	public java.util.Date getSQLDateTo() {
		return new java.sql.Date(dateTo);
	}

	public java.sql.Date getSQLDateFrom() {
		return new java.sql.Date(dateFrom);
	}

	public String getSearchquery() {
		return searchquery;
	}

	public void setSearchquery(String searchquery) {
		this.searchquery = searchquery;
	}

	public String getSelectAccountHistoryXML() {
		return selectAccountHistoryXML;
	}

	public void setSelectAccountHistoryXML(String selectAccountHistoryXML) {
		this.selectAccountHistoryXML = selectAccountHistoryXML;
	}

}
