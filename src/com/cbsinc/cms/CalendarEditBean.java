package com.cbsinc.cms;

import java.sql.SQLException;

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

public class CalendarEditBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = 8381891800819464179L;

	static private Logger log = Logger.getLogger(CalendarEditBean.class);

	private String query;

	private String holddate = "0";

	private String siteId = "0";

	private String calendarId = "0";

	private Integer indxSelect = 0;

	private String dayId = "0";

	private String mountId = "0";

	private String yearId = "0";

	private String productId = "";

	transient java.util.Calendar calendar;

	private String firstName = "";
	private String lastName = "";
	private String fatherName = "";
	private String documentNumber = "";
	private String documentType = "";
	private String age = "";
	private String note = "";

	public CalendarEditBean() {
		calendar = java.util.Calendar.getInstance();
		dayId = "" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
		mountId = "" + calendar.get(java.util.Calendar.MONTH);
		yearId = "" + calendar.get(java.util.Calendar.YEAR);
	}

	public void initCalendar(String holddate) {
		calendar.setTimeInMillis(Long.parseLong(holddate));
		dayId = "" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
		mountId = "" + (calendar.get(java.util.Calendar.MONTH) + 1);
		yearId = "" + calendar.get(java.util.Calendar.YEAR);
	}

	public void editCalendar() {
		if (productId == null || productId.length() == 0)
			return;
		calendar.set(Integer.parseInt(yearId), Integer.parseInt(mountId) - 1, Integer.parseInt(dayId));
		holddate = "" + calendar.getTimeInMillis();
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";

		// if(roleId == 2 ) query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" ,
		// \"soft\".\"image_id\" , \"images\".\"img_url\" FROM \"soft\" LEFT
		// JOIN \"images\" ON \"soft\".\"image_id\" = \"images\".\"image_id\"
		// WHERE \"soft\".\"type_id\" = " + type_id + " and
		// \"soft\".\"phonetype_id\" = " + phonetype_id + " and
		// \"soft\".\"progname_id\" = " + progname_id + " and
		// \"soft\".\"phonemodel_id\" = " + phonemodel_id + " limit 10 offset "
		// + offset ;
		// else query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" ,
		// \"soft\".\"image_id\" , \"images\".\"img_url\" FROM \"soft\" LEFT
		// JOIN \"images\" ON \"soft\".\"image_id\" = \"images\".\"image_id\"
		// WHERE \"soft\".\"type_id\" = " + type_id + " and
		// \"soft\".\"phonetype_id\" = " + phonetype_id + " and
		// \"soft\".\"progname_id\" = " + progname_id + " and
		// \"soft\".\"phonemodel_id\" = " + phonemodel_id + " and
		// \"soft\".\"soft_id\" = func_soft_file_id(\"soft\".\"file_id\") limit
		// 10 offset " + offset ;

		query = "update calendar set soft_id = " + productId + " , holddate = " + holddate + " where calendar_id = "
				+ calendarId;

		try {
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}
		return;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

}
