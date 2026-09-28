package com.cbsinc.cms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
@PageModel(Id = "actionBidsBeanId", scope = Scope.REQUESR)
//@PageXsltView(jspName = "ActionBids.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class ActionBidsBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -429983991018001127L;

	static private Logger log = Logger.getLogger(ActionBidsBean.class);

	public String[][] rows = new String[10][2];

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private Integer intLevelUp = 0;

	private String userId;

	private String currencyLable = "0";

	private String datePattern = "dd/MM/yyyy";

	java.util.Calendar calendar;

	long dateFrom = 0;
	long dateTo = 0;

	String selectActionBidsXML = "";
	
	String searchquery = "0";



	public ActionBidsBean() {
		calendar = java.util.Calendar.getInstance();
		dateFrom = calendar.getTimeInMillis();
		dateTo = calendar.getTimeInMillis();
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public int stringToInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
		}
		return i;
	}

	public boolean setSelectedDemand() {
		QueryManager Adp = new QueryManager();
		// String query = "update tdemand set selected=true where id=" + demand
		// ;
		// Adp.executeUpdate(query);
		Adp.close();
		return true;
	}

	public boolean setPassiveDemand() {
		QueryManager Adp = new QueryManager();
		// String query = "update tdemand set active=false where id=" + demand ;
		// Adp.executeUpdate(query);
		Adp.close();
		return true;
	}



	
	public void setIntLevelUp(int intLevelUp) {
		this.intLevelUp = intLevelUp;
	}

	public int getIntLevelUp() {
		return intLevelUp;
	}

	public void setUserID(String strUserID) {
		this.userId = strUserID;
	}

	public String getUserID() {
		return userId;
	}

	public String getListup() {
		return listup;
	}

	public void setListup(String listup) {
		this.listup = listup;
	}

	public String getListdown() {
		return listdown;
	}

	public void setListdown(String listdown) {
		this.listdown = listdown;

	}

	public String getCurrencyLable() {
		return currencyLable;
	}

	public void setCurrencyLable(String currencyLable) {
		this.currencyLable = currencyLable;
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
		// getCalendar().setTimeInMillis(dateTo);
	}
	
	

	/*
	 * String dateTo Input format is "dd/mm/yyyy"
	 */
	public void setDateTo(String dateTo, String datePattern, Locale locale) {
		this.datePattern = datePattern;
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern, locale);
		try {
			this.dateTo = formatter.parse(dateTo).getTime();
		} catch (ParseException e) {
			log.error(e);
		}

	}

	/*
	 * String dateFrom Input format is "dd/mm/yyyy"
	 */
	public void setDateFrom(String dateFrom, String datePattern, Locale locale) {
		this.datePattern = datePattern;
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern, locale);
		try {
			this.dateFrom = formatter.parse(dateFrom).getTime();
		} catch (ParseException e) {
			log.error(e);
		}
	}

	public java.util.Date getSQLDateTo() {
		return new java.sql.Date(dateTo);
	}

	public java.sql.Date getSQLDateFrom() {
		return new java.sql.Date(dateFrom);
	}

	public String getFormatedDateTo(Locale locale) {
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern, locale);
		return formatter.format(getSQLDateTo());
	}

	public String getFormatedDateFrom(Locale locale) {
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern, locale);
		return formatter.format(getSQLDateFrom());
	}

	public SimpleDateFormat getSimpleDateFormat(Locale locale) {
		return new SimpleDateFormat(datePattern, locale);
	}

	
	public String getSelectActionBidsXML() {
		return selectActionBidsXML;
	}

	public void setSelectActionBidsXML(String selectActionBidsXML) {
		this.selectActionBidsXML = selectActionBidsXML;
	}

	public String getSearchquery() {
		return searchquery;
	}

	public void setSearchquery(String searchquery) {
		this.searchquery = searchquery;
	}

	public String getDatePattern() {
		return datePattern;
	}

	private String selectMenuCatalog = "" ;

	public String getSelectMenuCatalog() {
		return selectMenuCatalog;
	}

	public void setSelectMenuCatalog(String selectMenuCatalog) {
		this.selectMenuCatalog = selectMenuCatalog;
	}

}
