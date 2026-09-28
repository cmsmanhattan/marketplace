package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import jakarta.servlet.ServletContext;

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

public class CalendarListBean extends com.cbsinc.cms.WebControls implements java.io.Serializable {

	private static final long serialVersionUID = -3430180944717439685L;

	private static Logger log = Logger.getLogger(CalendarListBean.class);

	public String[][] rows = new String[10][5];

	transient public QueryManager Adp;

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String cururl;

	private String calendarId = "-1";

	private String siteId = "2";

	private String rowId = "0";

	private Integer indxSelect = 0;

	// Access sample property
	// Access sample property

	private String mountId = "0";

	private String yearId = "0";

	transient java.util.Calendar calendar;

	String holddateFrom = "0";

	String holddateTo = "0";

	private String productId = "";

	private String descrition = "";

	transient ServletContext applicationContext;

	public CalendarListBean() {
		calendar = java.util.Calendar.getInstance();
		mountId = "" + (calendar.get(java.util.Calendar.MONTH) + 1);
		yearId = "" + calendar.get(java.util.Calendar.YEAR);
	}

	public void initCalendar(String holddate) {
		calendar.setTimeInMillis(Long.parseLong(holddate));
		// day_id = "" + calendar.get(java.util.Calendar.DAY_OF_MONTH) ;
		// mount_id = "" + calendar.get(java.util.Calendar.MONTH) ;
		// year_id = "" + calendar.get(java.util.Calendar.YEAR) ;
	}

	public String calendarDay(String holddate) {
		calendar.setTimeInMillis(Long.parseLong(holddate));
		return "" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
		// mount_id = "" + calendar.get(java.util.Calendar.MONTH) ;
		// year_id = "" + calendar.get(java.util.Calendar.YEAR) ;
	}

	public String calendarDate(String holddate) {
		calendar.setTimeInMillis(Long.parseLong(holddate));
		return "" + calendar.getTime();
		// mount_id = "" + calendar.get(java.util.Calendar.MONTH) ;
		// year_id = "" + calendar.get(java.util.Calendar.YEAR) ;
	}

	public void calendarChange() {

		calendar.set(Integer.parseInt(yearId), Integer.parseInt(mountId) - 1, 1);
		holddateFrom = "" + calendar.getTimeInMillis();
		System.out.println(calendar.getTime());
		if (Integer.parseInt(mountId) > 11)
			calendar.set(Integer.parseInt(yearId) + 1, 1, 1);
		else
			calendar.set(Integer.parseInt(yearId), Integer.parseInt(mountId), 1);
		holddateTo = "" + calendar.getTimeInMillis();
		System.out.println(calendar.getTime());
		// mount_id = "" + calendar.get(java.util.Calendar.MONTH) ;
		// year_id = "" + calendar.get(java.util.Calendar.YEAR) ;
	}

	public String getTable(int roleId) {

		/*
		 * listup = "Softlisting.jsp?offset=" + (offset + 10) + "&type_id=" + type_id ;
		 * if( offset - 10 < 0 ) listdown = "Softlisting.jsp?offset=0&type_id=" +
		 * type_id ; else listdown = "Softlisting.jsp?offset=" + (offset - 10) +
		 * "&type_id=" + type_id ;
		 */

		cururl = "calendar_list.jsp?offset=" + offset;

		listup = "calendar_list.jsp?offset=" + (offset + 10); // +
																// "&calendar_id="
																// + calendar_id
																// +
																// "&phonetype_id="
																// +
																// phonetype_id
																// +
																// "&licence_id="
																// + licence_id
																// ;
		if (offset - 10 < 0)
			listdown = "calendar_list.jsp?offset=0"; // &calendar_id=" +
														// calendar_id +
														// "&phonetype_id=" +
														// phonetype_id +
														// "&licence_id=" +
														// licence_id ;
		else
			listdown = "calendar_list.jsp?offset=" + (offset - 10); // +
																	// "&calendar_id="
																	// +
																	// calendar_id
																	// +
																	// "&phonetype_id="
																	// +
																	// phonetype_id
																	// +
																	// "&licence_id="
																	// +
																	// licence_id
																	// ;

		StringBuffer table = new StringBuffer();
		Adp = new QueryManager();

		String query = "";
		// query = "select calendar_id , holddate FROM calendar WHERE active =
		// true and site_id = " + site_id + " limit 10 offset " + offset ;
		query = "select calendar_id ,  holddate   FROM calendar WHERE active = true and holddate > " + holddateFrom
				+ " and holddate < " + holddateTo + "  and soft_id = " + productId + " limit 10 offset " + offset;

		try {
			Adp.executeQuery(query);

			// <%= JspPostPredictionBeanId.getComboBox("gteam_cd", "1" ,"select
			// team_cd , team_name from team where ( active = true) and ( sport_cd =
			// " + strSport_cd + " ) " ) %>
			// if(strUser_id.compareTo("1") != 0 )
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (roleId == 2) {
				table.append(
						"<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"70%\" >Число  </TD>"
								+ "<TD WIDTH=\"20%\" ><a href =\"calendar_add.jsp\">добавить</a> </TD>" + "</TR>\n");

				// "<TD height=\"*\" border=\"0\" >" + this.getComboBox("type_id",
				// "" + type_id ,"select type_id , type_lable from typesoft where
				// active = true") + "<input type=\"submit\" name=\"Submit\"
				// value=\"OK\"></TD>" +
				// postManager
			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>"
						+ "<TD WIDTH=\"70%\" >Число  </TD>" + "<TD WIDTH=\"20%\" ></TD>" + "</TR>\n");
			}

			if (Adp.rows().size() < 10) {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
			} else {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">след 10</a>  </TD>"
						+ "</TR>\n");
			}
			for (int i = 0; Adp.rows().size() > i; i++) {
				rows[i][0] = (String) Adp.getValueAt(i, 0);
				rows[i][1] = (String) Adp.getValueAt(i, 1);
				rows[i][2] = calendarDay((String) Adp.getValueAt(i, 1));
				rows[i][3] = calendarDate((String) Adp.getValueAt(i, 1));

				if (roleId == 2) {
					table.append("<TR>" + "<TD  >" + rows[i][0] + "</TD>" + "<TD  >" + rows[i][2] + " ( " + rows[i][3]
							+ " ) </TD>" + "<TD algin=\"rigth\" ><a href =\"calendar_edit.jsp?row=" + i
							+ "\">редактировать</a> </TD>" + "<TD algin=\"rigth\" ><a href =\"calendar_list.jsp?del="
							+ i + "\">удалить</a> </TD>" + "</TR>\n");
				} else {
					table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + rows[i][2] + " ( " + rows[i][3]
							+ " ) </TD>" + "<TD algin=\"rigth\" ></TD>" + "<TD algin=\"rigth\" ></TD>" + "</TR>\n");
				}

			}

			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listdown + "\">назад 10</a>  </TD>"
					+ "</TR>\n");
			table.append("</tbody>\n");
			table.append("</TABLE>\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public void delete(String calendarId) {
		Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = "delete FROM calendar WHERE site_id = " + siteId + " and calendar_id = " + calendarId;
		try {
			Adp.executeUpdate(query);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(ex);
			Adp.rollback();

		} finally {
			Adp.close();
		}

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

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public int getIndxSelect() {
		return indxSelect;
	}

	public void setIndxSelect(int indxSelect) {
		this.indxSelect = indxSelect;
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

	public String getDescrition() {
		return descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return applicationContext;
	}

	public void setServletContext(ServletContext applicationContext) {
		this.applicationContext = applicationContext;

	}
}
