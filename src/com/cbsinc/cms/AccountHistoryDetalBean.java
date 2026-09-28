/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
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

package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageModel;
import com.cbsinc.cms.annotations.Scope;

//@PageXsltView( jspName = "AccountHistoryDetal.jsp" , xsltName="accounthistorydetal.xsl" , responseType= Type.XML  )
@PageModel(Id = "accountHistoryDetalBeanId", scope = Scope.SESSION)
public class AccountHistoryDetalBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -8230325849058152562L;

	private static Logger log = Logger.getLogger(AccountHistoryDetalBean.class);

	private Integer intUserID = 0;

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

	private String accountId = "";

	private String catalogId = "1";

	private String totalAmount;

	private String currencyAddLable;

	private String currencyOldLable;

	private String currencyTotalLable;

	private String userIp = "";

	private String userHeader = "";

	private String rezultCd = "";

	String selectAccountHistoryDetalXML = "";

	public String getPayment(int intUserID) {

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.user_ip , account_hist.user_header , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.id = " + accountId;

		try {
			Adp.executeQuery(query);

			addAmount = (String) Adp.getValueAt(0, 0);
			oldAmount = (String) Adp.getValueAt(0, 1);
			dateInput = (String) Adp.getValueAt(0, 2);
			dateEnd = (String) Adp.getValueAt(0, 3);
			sysdate = (String) Adp.getValueAt(0, 4);
			complete = (String) Adp.getValueAt(0, 5);
			decsription = (String) Adp.getValueAt(0, 6);
			active = (String) Adp.getValueAt(0, 7);
			accountId = (String) Adp.getValueAt(0, 8);
			totalAmount = (String) Adp.getValueAt(0, 9);
			currencyAddLable = (String) Adp.getValueAt(0, 10);
			currencyOldLable = (String) Adp.getValueAt(0, 11);
			currencyTotalLable = (String) Adp.getValueAt(0, 12);
			userIp = (String) Adp.getValueAt(0, 13);
			userHeader = (String) Adp.getValueAt(0, 14);
			rezultCd = (String) Adp.getValueAt(0, 15);

			table.append("<payment>\n");

			table.append("<add_amount>" + addAmount + "</add_amount>\n");
			table.append("<old_amount>" + oldAmount + "</old_amount>\n");
			table.append("<date_input>" + dateInput + "</date_input>\n");
			table.append("<date_end>" + dateEnd + "</date_end>\n");
			table.append("<sysdate>" + sysdate + "</sysdate>\n");
			table.append("<complete>" + complete + "</complete>\n");
			table.append("<decsription>" + decsription + "</decsription>\n");
			table.append("<active>" + active + "</active>\n");
			table.append("<amount_id>" + accountId + "</amount_id>\n");
			table.append("<total_amount>" + totalAmount + "</total_amount>\n");
			table.append("<currency_add_lable>" + currencyAddLable + "</currency_add_lable>\n");
			table.append("<currency_old_lable>" + currencyOldLable + "</currency_old_lable>\n");
			table.append("<currency_total_lable>" + currencyTotalLable + "</currency_total_lable>\n");
			table.append("<user_ip>" + userIp + "</user_ip>\n");
			table.append("<user_header>" + userHeader + "</user_header>\n");
			table.append("<rezult_cd>" + rezultCd + "</rezult_cd>\n");
			table.append("</payment>\n");
			// Adp.commit();
		} catch (SQLException ex) {
			System.err.println(query);
			System.err.println(ex);
			System.err.println("" + this.getClass());
			System.err.println("Method " + "getPayment()");
			// Adp.rollback();
			// Adp.close();
		} finally {
			Adp.close();
		}

		return table.toString();

	}

	public String getPayment(int intUserID, int roleId) {

		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		// Adp.BeginTransaction();
		String query = "SELECT  account_hist.add_amount, account_hist.old_amount, account_hist.date_input, account_hist.date_end, account_hist.sysdate, account_hist.complete, account_hist.decsription, account_hist.active , account_hist.id  , account_hist.total_amount , "
				+ " currency_add.currency_lable , currency_old.currency_lable ,currency_total.currency_lable , account_hist.user_ip , account_hist.user_header , account_hist.rezult_cd "
				+ " FROM account_hist  "
				+ " LEFT OUTER   JOIN currency  currency_add ON account_hist.currency_id_add = currency_add.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_old ON account_hist.currency_id_old = currency_old.currency_id "
				+ " LEFT OUTER   JOIN currency  currency_total ON account_hist.currency_id_total = currency_total.currency_id "
				+ " WHERE account_hist.id = " + accountId;

		try {

			Adp.executeQuery(query);
			addAmount = (String) Adp.getValueAt(0, 0);
			oldAmount = (String) Adp.getValueAt(0, 1);
			dateInput = (String) Adp.getValueAt(0, 2);
			dateEnd = (String) Adp.getValueAt(0, 3);
			sysdate = (String) Adp.getValueAt(0, 4);
			complete = (String) Adp.getValueAt(0, 5);
			decsription = (String) Adp.getValueAt(0, 6);
			active = (String) Adp.getValueAt(0, 7);
			accountId = (String) Adp.getValueAt(0, 8);
			totalAmount = (String) Adp.getValueAt(0, 9);
			currencyAddLable = (String) Adp.getValueAt(0, 10);
			currencyOldLable = (String) Adp.getValueAt(0, 11);
			currencyTotalLable = (String) Adp.getValueAt(0, 12);
			userIp = (String) Adp.getValueAt(0, 13);
			userHeader = (String) Adp.getValueAt(0, 14);
			rezultCd = (String) Adp.getValueAt(0, 15);

			table.append("<payment>\n");

			table.append("<add_amount>" + getStrFormatNumberFloat(addAmount) + "</add_amount>\n");
			table.append("<old_amount>" + getStrFormatNumberFloat(oldAmount) + "</old_amount>\n");
			table.append("<date_input>" + dateInput + "</date_input>\n");
			table.append("<date_end>" + dateEnd + "</date_end>\n");
			table.append("<sysdate>" + sysdate + "</sysdate>\n");
			table.append("<complete>" + complete + "</complete>\n");
			table.append("<decsription>" + decsription + "</decsription>\n");
			table.append("<active>" + active + "</active>\n");
			table.append("<amount_id>" + accountId + "</amount_id>\n");
			table.append("<total_amount>" + getStrFormatNumberFloat(totalAmount) + "</total_amount>\n");
			table.append("<currency_add_lable>" + currencyAddLable + "</currency_add_lable>\n");
			table.append("<currency_old_lable>" + currencyOldLable + "</currency_old_lable>\n");
			table.append("<currency_total_lable>" + currencyTotalLable + "</currency_total_lable>\n");
			table.append("<user_ip>" + userIp + "</user_ip>\n");
			table.append("<user_header>" + userHeader + "</user_header>\n");
			table.append("<rezult_cd>" + rezultCd + "</rezult_cd>\n");
			table.append("</payment>\n");

		} catch (SQLException ex) {
			System.err.println(query);
			System.err.println(ex);
			System.err.println("" + this.getClass());
			System.err.println("Method " + "getPayment()");
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

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
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

	public String getAmountId() {
		return accountId;
	}

	public void setAmountId(String amountId) {
		this.accountId = amountId;
	}

	public String getUserHeader() {
		return userHeader;
	}

	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setIntUserID(Integer intUserID) {
		this.intUserID = intUserID;
	}

	public String getRezultCd() {
		return rezultCd;
	}

	public void setRezultCd(String rezultCd) {
		this.rezultCd = rezultCd;
	}

	public String getSelectAccountHistoryDetalXML() {
		return selectAccountHistoryDetalXML;
	}

	public void setSelectAccountHistoryDetalXML(String selectAccountHistoryDetalXML) {
		this.selectAccountHistoryDetalXML = selectAccountHistoryDetalXML;
	}

}
