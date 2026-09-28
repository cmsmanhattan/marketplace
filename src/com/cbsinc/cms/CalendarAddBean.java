package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

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

public class CalendarAddBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = 4879601106156013341L;

	static private Logger log = Logger.getLogger(CalendarAddBean.class);

	private String query;

	private String holddate = "0";

	private String siteId = "0";

	private String calendarId = "0";

	private Integer indxSelect = 0;

	private String dayId = "0";

	private String mountId = "0";

	private String yearId = "0";

	java.util.Calendar calendar;

	private String productId = "";

	private String firstName = "";
	private String lastName = "";
	private String fatherName = "";
	private String documentNumber = "";
	private String documentType = "";
	private String age = "";
	private String note = "";

	transient ResourceBundle sequencesRs = null;

	public CalendarAddBean() {
		calendar = java.util.Calendar.getInstance();
		dayId = "" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
		mountId = "" + (calendar.get(java.util.Calendar.MONTH) + 1);
		yearId = "" + calendar.get(java.util.Calendar.YEAR);
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
	}

	public void addCalendar() {

		if (productId == null || productId.length() == 0)
			return;
		calendar.set(Integer.parseInt(yearId), Integer.parseInt(mountId) - 1, Integer.parseInt(dayId));
		holddate = "" + calendar.getTimeInMillis();
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = sequencesRs.getString("calendar");
		// query = "SELECT NEXT VALUE FOR calendar_calendar_id_seq AS ID FROM
		// ONE_SEQUENCES";

		try {
			Adp.executeQuery(query);
			calendarId = Adp.getValueAt(0, 0);
			query = "insert into calendar (calendar_id , soft_id , site_id , holddate  , active  ) " + " values ( " + ""
					+ calendarId + " , " + "" + productId + " , " + "" + siteId + " , " + "" + holddate + ", "
					+ "true" + " ) ; ";

			Adp.executeUpdate(query);
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

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int getIndxSelect() {
		return indxSelect;
	}

	public void setIndxSelect(int indxSelect) {
		this.indxSelect = indxSelect;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getHolddate() {
		return holddate;
	}

	public void setHolddate(String holddate) {
		this.holddate = holddate;
	}

	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	public String getMountId() {
		return mountId;
	}

	public void setMountId(String mountId) {
		this.mountId = mountId;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
