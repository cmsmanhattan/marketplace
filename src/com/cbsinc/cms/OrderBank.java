package com.cbsinc.cms;

import com.cbsinc.cms.utils.Validation;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Logger;

public class OrderBank implements java.io.Serializable {

	/**
	 *
	 */
	transient private static final long serialVersionUID = -3147862933505181532L;

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

	transient static private Logger log = Logger.getLogger(OrderBank.class);

	// Calendar startCalendar ;
	transient Calendar endCalendar;

	Integer intDayPeriod = 1;

	private String orderID;

	private String beginData;

	private String endData;

	public OrderBank() {
		endCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"), new Locale("ru"));

		// startCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00")
		// , new Locale("ru"));
		// endCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00") ,
		// new Locale("ru"));
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getBeginData() {
		return beginData;
	}

	public String getBeginDataDay() {
		// return "" + startCalendar.get(startCalendar.DAY_OF_WEEK) ;
		return tokenize(beginData, "-")[2];
	}

	public int getBeginDataIntDay() {
		int intSDay = 0;
		String strSDay = (tokenize(beginData, "-")[2]).trim();
		if (Validation.isNonNegativeInteger(strSDay)) {
			intSDay = Integer.parseInt(strSDay);
		}
		return intSDay;
	}

	/*
	 * int intSDay = 0 ; String strSDay = (tokenize(beginData,"-")[2]).trim() ;
	 * if(Validation.isNonNegativeInteger(strSDay)){ intSDay = new Integer(strSDay).intValue() ;
	 */

	public String getEndDataDay() {
		int intEDay = 0;
		String strEDay = "0";
		// endCalendar.set( getBeginData_intYear(), getBeginData_intMonth(),
		// getBeginData_intDay() ) ;
		// endCalendar.add(endCalendar.DATE , intDayPeriod);
		try {
			intEDay = endCalendar.get(Calendar.DATE);
			strEDay = "" + intEDay;
			if (strEDay.length() == 1)
				strEDay = "0" + strEDay;
			// if(strEDay.length() = 1)strEDay = "0" + strEDay
		} catch (Exception e) {
			log.error(e);
		}

		return strEDay;
	}

	public String getEndDataMonth() {
		int intEMonth = 0;
		String strEMonth = "0";
		// endCalendar.set( getBeginData_intYear(), getBeginData_intMonth(),
		// getBeginData_intDay() ) ;
		try {
			int intEDay = endCalendar.get(Calendar.DATE);
			if (intEDay > getBeginDataIntDay())
				endCalendar.add(Calendar.MONTH, 1);
			intEMonth = endCalendar.get(Calendar.MONTH);
			strEMonth = "" + intEMonth;
			if (strEMonth.length() == 1)
				strEMonth = "0" + strEMonth;
		} catch (Exception e) {
			log.error(e);
		}
		return strEMonth;
		// return getBeginData_Month() ;
	}

	public String getEndDataYear() {
		/*
		 * int intEMonth = endCalendar.get(endCalendar.MONTH) ; if( intEMonth >
		 * getBeginData_intMonth() ) endCalendar.add(endCalendar.YEAR , 1); int intEYear
		 * = endCalendar.get(endCalendar.YEAR) ; return "" + intEYear ;
		 */
		return getBeginDataYear();
	}

	public String getBeginDataMonth() {
		// return "" + startCalendar.get(startCalendar.MONTH) ;
		// return tokenize(beginData,"-")[1] ;
		return "" + getBeginDataIntMonth();
	}

	public int getBeginDataIntMonth() {
		int intSMonth = 0;

		try {
			String strSMonth = (tokenize(beginData, "-")[1]).trim();
			if (Validation.isNonNegativeInteger(strSMonth)) {
				intSMonth = Integer.parseInt(strSMonth);
			}
		} catch (Exception e) {
			log.error(e);
		}

		return intSMonth - 1;
	}

	public String getBeginDataYear() {
		// return "" + startCalendar.get(startCalendar.YEAR) ;
		return tokenize(beginData, "-")[0];
	}

	public int getBeginDataIntYear() {
		int intSYear = 0;
		String strSYear = "0";
		try {
			strSYear = (tokenize(beginData, "-")[0]).trim();
			if (Validation.isNonNegativeInteger(strSYear)) {
				intSYear = Integer.parseInt(strSYear);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return intSYear;
	}

	public void setBeginData(String beginData) {
		this.beginData = beginData.substring(0, 10);

		try {
			endCalendar.set(getBeginDataIntYear(), getBeginDataIntMonth(), getBeginDataIntDay());
			endCalendar.add(Calendar.DATE, intDayPeriod);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public String getEndData() {
		return endData;
	}

	public void setEndData(String endData) {
		this.endData = endData.substring(0, 10);

	}

	public String[] tokenize(String s, String d) {
		String[] as = null;
		try {
			Vector vector = new Vector();
			for (StringTokenizer stringtokenizer = new StringTokenizer(s, d); stringtokenizer.hasMoreTokens(); vector
					.addElement(stringtokenizer.nextToken()))
				;

			as = new String[vector.size()];
			for (int i = 0; i < as.length; i++)
				as[i] = (String) vector.elementAt(i);
		} catch (Exception e) {
			log.error(e);
		}

		return as;
	}


}
