package com.cbsinc.cms;

import com.cbsinc.cms.utils.Validation;
import java.sql.SQLException;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

public class WebControls {

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

	static private Logger log = Logger.getLogger(WebControls.class);
	NumberFormat nf;
	// transient DecimalFormat df ;

	public WebControls() {
		// df= new DecimalFormat("0.##");
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);
	}

	public String getDateControl(String name1, String name2, String name3, String query, int at, int to) {

		String strNumberCD = "00";
		String strMonthCD = "00";
		String strYesrCD = "2000";
		String strTmp = "";
		String strDate = "0000-00-00";
		// if(selected_cd == null ) selected_cd = "" ;
		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		QueryManager Adp = new QueryManager();
		try {

			if (query != null && query.length() != 0) {
				Adp.executeQuery(query);

			}
			if (Adp.rows().size() != 0) {
				try {
					strDate = (String) Adp.getValueAt(0, 0);
					strYesrCD = strDate.substring(0, 4);
					strMonthCD = strDate.substring(5, 7);
					strNumberCD = strDate.substring(8, 10);
				} catch (Exception ex) {
					strDate = "0000-00-00";
					strYesrCD = "0000";
					strMonthCD = "00";
					strNumberCD = "00";
					strNumberCD.replaceAll("/*", "");
				}
			}
			table.append("<TABLE  border=\"1\"  CELLSPACING=\"0\" CELLPADDING=\"2\" ><TR> \n");
			table.append("<TD>Number</TD> \n");
			table.append("<TD><select name=\"" + name1 + "\" > \n");
			for (int i = 1; 32 > i; i++) {
				strTmp = "" + i;
				if (strTmp.length() == 1)
					strTmp = "0" + strTmp;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strNumberCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("<TD>Month</TD> \n");
			table.append("<TD><select name=\"" + name2 + "\" > \n");
			for (int i = 1; 13 > i; i++) {
				strTmp = "" + i;
				if (strTmp.length() == 1)
					strTmp = "0" + strTmp;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strMonthCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("<TD>Year</TD> \n");
			table.append("<TD><select name=\"" + name3 + "\" > \n");
			for (int i = at; to > i; i++) {
				strTmp = "" + i;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strYesrCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("</TR></TABLE > \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getLiteDateControl(String name1, String name2, String name3, String query, int at, int to) {

		QueryManager adp = null;
		String strNumberCD = "00";
		String strMonthCD = "00";
		String strYesrCD = "2000";
		String strTmp = "";
		String strDate = "0000-00-00";
		StringBuffer table = new StringBuffer();

		try {
			adp = new QueryManager();
			if (query != null && query.length() != 0) {
				adp.executeQuery(query);
			}
			if (adp.rows().size() != 0) {
				try {
					strDate = (String) adp.getValueAt(0, 0);
					strYesrCD = strDate.substring(0, 4);
					strMonthCD = strDate.substring(5, 7);
					strNumberCD = strDate.substring(8, 10);
				} catch (Exception ex) {
					strDate = "0000-00-00";
					strYesrCD = "0000";
					strMonthCD = "00";
					strNumberCD = "00";
				}
			}
			table.append("<TABLE  border=\"0\"  CELLSPACING=\"0\" CELLPADDING=\"0\" ><TR> \n");
			table.append("<TD><select name=\"" + name1 + "\" > \n");
			for (int i = 1; 31 > i; i++) {
				strTmp = "" + i;
				if (strTmp.length() == 1)
					strTmp = "0" + strTmp;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strNumberCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("<TD><select name=\"" + name2 + "\" > \n");
			for (int i = 1; 12 > i; i++) {
				strTmp = "" + i;
				if (strTmp.length() == 1)
					strTmp = "0" + strTmp;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strMonthCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("<TD><select name=\"" + name3 + "\" > \n");
			for (int i = at; to > i; i++) {
				strTmp = "" + i;
				// strLable = (String)Adp.getValueAt(i,0) ;
				if (strTmp.compareTo(strYesrCD) == 0)
					table.append("<option value=\"" + strTmp + "\" selected >" + i + "\n");
				else
					table.append("<option value=\"" + strTmp + "\">" + i + "\n");
			}
			table.append("</select></TD> \n");
			table.append("</TR></TABLE > \n");

		} catch (SQLException ex) {
			log.error(ex);
			System.err.println("Method " + "getLiteDateControl()");
			System.err.println(query);
		} catch (Exception ex) {
			// FIX: this catch was empty, so any non-SQL failure in
			// getLiteDateControl() produced a silently blank control on the page
			// with nothing in the log to explain it.
			log.error("getLiteDateControl() failed for query: " + query, ex);
		} finally {
			adp.close();
		}

		return table.toString();
	}

	public String getComboBox(String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			// onChange=\"return document.forms[0].submit()\"
			table.append("<select name=\"" + name + "\" > \n");

			for (int i = 0; Adp.rows().size() > i; i++) {
				// if(i==0) if(((String)selected_cd).length()==0 ) selected_cd =
				// (String)Adp.getValueAt(i,0) ;
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (selectedCd.compareTo(strCD) == 0)
					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
				else
					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getOneLabel(String query) {
		String name = "";

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				name = (String) Adp.getValueAt(0, 0);
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return name;
	}

	public String getComboBoxAutoSubmit(String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();

		QueryManager Adp = new QueryManager();
		try {

			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    onChange=\"doChangeCreteria('" + name
					+ "', this.value)\"    > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {

				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (selectedCd.compareTo(strCD) == 0)

					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
				else

					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}

			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getComboBoxAutoSubmitLocale(String name, String selectedCd, String defaultLabel, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();

		QueryManager Adp = new QueryManager();
		try {

			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    onChange=\"doChangeCreteria('" + name
					+ "', this.value)\"    > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {

				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (strCD.equals("0"))
					strLable = defaultLabel;

				if (selectedCd.compareTo(strCD) == 0)

					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
				else

					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}

			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getComboBoxWithJavaScript(String name, String selectedCd, String query, String javascriptStatment) {
		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			// onChange=\"return document.forms[0].submit()\"
			table.append("<select name=\"" + name + "\"   id=\"" + name + "\"    " + javascriptStatment + "   > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				// if(i==0) if(((String)selected_cd).length()==0 ) selected_cd =
				// (String)Adp.getValueAt(i,0) ;
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (selectedCd.compareTo(strCD) == 0)
					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
				else
					table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getComboBoxWap(String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\" size=\"2\" > \n");

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				// if(selected_cd.compareTo(strCD) == 0 ) table.append("<option
				// selected >" + strLable + "</option>\n");
				// else table.append("<option>" + strLable + "</option>\n");
				if (selectedCd.compareTo(strCD) == 0)
					table.append("<option value=\"" + strCD + "\" selected >" + strLable + "</option>\n");
				else
					table.append("<option value=\"" + strCD + "\">" + strLable + "</option>\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getWmlComboBoxWap(String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\" value=\"" + selectedCd + "\" title=\"" + name + "\" > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<option value=\"" + strCD + "\">" + strLable + "</option>\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getWmlComboBoxWapEvent(String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://192.168.0.10:5432/nnt")
		// ;
		// QueryManager Adp = new
		// QueryManager("jdbc:postgresql://nntsport.com:5432/nnt")
		// ;
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<select name=\"" + name + "\" value=\"" + selectedCd + "\" title=\"" + name + "\" > \n");
			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<option value=\"" + strCD + "\">" + strLable + "</option>\n");
			}
			table.append("</select> \n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getComboBoxDay(String name, String selectedCd) {

		String[] arrDay = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// onChange=\"return document.forms[0].submit()\"
		table.append("<select name=\"" + name + "\" > \n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			if (selectedCd.compareTo(strCD) == 0)
				table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
			else
				table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
		}
		table.append("</select> \n");
		return table.toString();
	}

	public String getComboBoxMount(String name, String selectedCd) {

		String[] arrDay = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// onChange=\"return document.forms[0].submit()\"
		table.append("<select name=\"" + name + "\" > \n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			if (selectedCd.compareTo(strCD) == 0)
				table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
			else
				table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
		}
		table.append("</select> \n");
		return table.toString();
	}

	public String getComboBoxYear(String name, String selectedCd) {

		String[] arrDay = { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" };
		String strCD = "0";
		String strLable = "Other";
		if (selectedCd == null)
			selectedCd = "";

		StringBuffer table = new StringBuffer();
		// onChange=\"return document.forms[0].submit()\"
		table.append("<select name=\"" + name + "\" > \n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			if (selectedCd.compareTo(strCD) == 0)
				table.append("<option value=\"" + strCD + "\" selected >" + strLable + "\n");
			else
				table.append("<option value=\"" + strCD + "\">" + strLable + "\n");
		}
		table.append("</select> \n");
		return table.toString();
	}

	public String getXMLListDateDay(String pagejsp, String name, String selectedCd) {
		String[] arrDay = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		String strCD = "1";
		String strLable = "1";
		StringBuffer table = new StringBuffer();
		table.append("<" + name + ">\n");

		table.append("<" + name + "-item>");
		table.append("<selected>" + selectedCd + "</selected>");
		table.append("<item></item>");
		table.append("<code>" + "-1" + "</code>");
		table.append("<url>" + pagejsp + "=" + strCD + "</url>");
		table.append("</" + name + "-item>\n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item>" + strLable + "</item>");
			table.append("<code>" + strCD + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");
		}

		table.append("</" + name + ">\n");

		return table.toString();
	}

	public String getXMLListDateMount(String pagejsp, String name, String selectedCd) {
		String[] arrDay = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		String strCD = "1";
		String strLable = "1";
		StringBuffer table = new StringBuffer();
		table.append("<" + name + ">\n");

		table.append("<" + name + "-item>");
		table.append("<selected>" + selectedCd + "</selected>");
		table.append("<item></item>");
		table.append("<code>" + "-1" + "</code>");
		table.append("<url>" + pagejsp + "=" + strCD + "</url>");
		table.append("</" + name + "-item>\n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item>" + strLable + "</item>");
			table.append("<code>" + strCD + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");
		}
		table.append("</" + name + ">\n");

		return table.toString();
	}

	public String getXMLListDateYear(String pagejsp, String name, String selectedCd) {
		String[] arrDay = { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" };
		String strCD = "1";
		String strLable = "1";
		StringBuffer table = new StringBuffer();

		table.append("<" + name + ">\n");
		table.append("<" + name + "-item>");
		table.append("<selected>" + selectedCd + "</selected>");
		table.append("<item></item>");
		table.append("<code>" + "-1" + "</code>");
		table.append("<url>" + pagejsp + "=" + strCD + "</url>");
		table.append("</" + name + "-item>\n");

		for (int i = 0; arrDay.length > i; i++) {
			strCD = arrDay[i];
			strLable = arrDay[i];
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item>" + strLable + "</item>");
			table.append("<code>" + strCD + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");
		}

		table.append("</" + name + ">\n");

		return table.toString();
	}

	/*
	 * public String getXMLComboBox(String name , String selected_cd , String query
	 * ) {
	 *
	 * String strCD = "0" ; String strLable = "Other"; if(selected_cd == null )
	 * selected_cd = "" ;
	 *
	 * StringBuffer table = new StringBuffer(); QueryManager Adp = new
	 * QueryManager("jdbc:postgresql://192.168.0.1:5432/mobilsoft") ; try {
	 * Adp.executeQuery(query); } catch (SQLException ex) {
	 * System.err.println(query); System.err.println(ex); System.err.println("" +
	 * this.getClass()); System.err.println("Method " + "getXMLComboBox()");
	 * Adp.close(this.getServletContext()); }
	 *
	 * table.append("<"+name+">\n"); table.append("<name-item>" + name +
	 * "</name-item>\n"); for(int i = 0 ; Adp.rows().size() > i ; i++ ){ strCD =
	 * (String)Adp.getValueAt(i,0) ; strLable = (String)Adp.getValueAt(i,1) ;
	 * table.append("<combo-item>\n"); table.append("<value-item>"+ strCD
	 * +"</value-item>\n"); table.append("<text-item>" + strLable +
	 * "</text-item>\n"); if(selected_cd.compareTo(strCD) == 0
	 * )table.append("<selected-item>true</selected-item>\n"); else
	 * table.append("<selected-item>false</selected-item>\n");
	 * table.append("</combo-item>\n"); //table.append("<option value=\"" + strCD +
	 * "\">" + strLable + "</option>\n"); }
	 * table.append("<type-item>combobox</type-item>\n");
	 * table.append("</"+name+">\n"); Adp.close(this.getServletContext()); return
	 * table.toString(); }
	 *
	 */

	public String getXMLDBList(String pagejsp, String name, String selectedCd, String query) {
		// System.out.println(query) ;
		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<" + name + ">\n");

			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item></item>");
			table.append("<code>" + "-1" + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<" + name + "-item>");
				table.append("<selected>" + selectedCd + "</selected>");
				table.append("<item>" + strLable + "</item>");
				table.append("<code>" + strCD + "</code>");
				table.append("<url>" + pagejsp + "=" + strCD + "</url>");
				table.append("</" + name + "-item>\n");
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getXMLDBCriteriaListLocale(String pagejsp, String name, String selectedCd, String defaultString,
			String query) {

		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {

			Adp.executeQuery(query);

			table.append("<" + name + ">\n");

//		table.append("<" + name + "-item>");
//		table.append("<selected>" + selected_cd + "</selected>");
//		table.append("<item></item>");
//		table.append("<code>" + "-1" + "</code>");
//		table.append("<url>" + pagejsp + "=" + strCD + "</url>");
//		table.append("</" + name + "-item>\n");

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (strCD.equals("0"))
					strLable = defaultString;

				table.append("<" + name + "-item>");
				table.append("<selected>" + selectedCd + "</selected>");
				table.append("<item>" + strLable + "</item>");
				table.append("<code>" + strCD + "</code>");
				table.append("<url>" + pagejsp + "=" + strCD + "</url>");
				table.append("</" + name + "-item>\n");
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getAjaxDBList(String pagejsp, String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<" + name + ">\n");

			/*
			 * for(int i = 0 ; Adp.rows().size() > i ; i++ ) { strCD =
			 * (String)Adp.getValueAt(i,0) ; strLable = (String)Adp.getValueAt(i,1) ;
			 * if(strCD.compareTo(selected_cd)==0) { table.append("<"+name+"-item>");
			 * table.append("<selected>"+ selected_cd +"</selected>");
			 * table.append("<item>"+ strLable +"</item>"); table.append("<code>"+ strCD
			 * +"</code>"); table.append("<url>"+ pagejsp +"=" + strCD +"</url>");
			 * table.append("</"+name+"-item>\n"); break ; } }
			 *
			 */

			// notselect = \u041d\u0435 \u0432\u044b\u0431\u0440\u0430\u043d\u043e
			/*
			 * table.append("<" + name + "-item>"); table.append("<selected>" + selected_cd
			 * + "</selected>"); table.append("<item>...</item>"); table.append("<code>" +
			 * "0" + "</code>"); table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			 * table.append("</" + name + "-item>\n");
			 */

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<" + name + "-item>");
				table.append("<selected>" + selectedCd + "</selected>");
				// table.append("<lable><![CDATA[" + URLEncoder.encode(strLable,"UTF-8") +
				// "]]></lable>");
				table.append("<item>" + strLable + "</item>");
				table.append("<code>" + strCD + "</code>");
				table.append("<url>" + pagejsp + "=" + strCD + "</url>");
				table.append("</" + name + "-item>\n");
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getAjaxDBListLocale(String pagejsp, String name, String selectedCd, String defaultLabel,
			String query) {

		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {

			Adp.executeQuery(query);
			table.append("<" + name + ">\n");

			/*
			 * for(int i = 0 ; Adp.rows().size() > i ; i++ ) { strCD =
			 * (String)Adp.getValueAt(i,0) ; strLable = (String)Adp.getValueAt(i,1) ;
			 * if(strCD.compareTo(selected_cd)==0) { table.append("<"+name+"-item>");
			 * table.append("<selected>"+ selected_cd +"</selected>");
			 * table.append("<item>"+ strLable +"</item>"); table.append("<code>"+ strCD
			 * +"</code>"); table.append("<url>"+ pagejsp +"=" + strCD +"</url>");
			 * table.append("</"+name+"-item>\n"); break ; } }
			 *
			 */

			// notselect = \u041d\u0435 \u0432\u044b\u0431\u0440\u0430\u043d\u043e
			/*
			 * table.append("<" + name + "-item>"); table.append("<selected>" + selected_cd
			 * + "</selected>"); table.append("<item>" + defaultLabel + "</item>");
			 * table.append("<code>" + "0" + "</code>"); table.append("<url>" + pagejsp +
			 * "=" + strCD + "</url>"); table.append("</" + name + "-item>\n");
			 */

			for (int i = 0; Adp.rows().size() > i; i++) {

				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				if (strCD.equals("0"))
					strLable = defaultLabel;

				table.append("<" + name + "-item>");
				table.append("<selected>" + selectedCd + "</selected>");
				// table.append("<lable><![CDATA[" + URLEncoder.encode(strLable,"UTF-8") +
				// "]]></lable>");
				table.append("<item>" + strLable + "</item>");
				table.append("<code>" + strCD + "</code>");
				table.append("<url>" + pagejsp + "=" + strCD + "</url>");
				table.append("</" + name + "-item>\n");
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getTreeXMLDBList(String pagejsp, String name, String selectedCd, String query, String subquery) {

		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<" + name + ">\n");

			/*
			 * for(int i = 0 ; Adp.rows().size() > i ; i++ ) { strCD =
			 * (String)Adp.getValueAt(i,0) ; strLable = (String)Adp.getValueAt(i,1) ;
			 * if(strCD.compareTo(selected_cd)==0) { table.append("<"+name+"-item>");
			 * table.append("<selected>"+ selected_cd +"</selected>");
			 * table.append("<item>"+ strLable +"</item>"); table.append("<code>"+ strCD
			 * +"</code>"); table.append("<url>"+ pagejsp +"=" + strCD +"</url>");
			 * table.append("</"+name+"-item>\n"); break ; } }
			 *
			 */
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item></item>");
			table.append("<code>" + "-1" + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("<sub" + name + "-item>");
			table.append("<subselected></subselected>");
			table.append("<subitem></subitem>");
			table.append("<subcode></subcode>");
			table.append("<suburl></suburl>");
			table.append("</sub" + name + "-item>\n");
			table.append("</" + name + "-item>\n");

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<" + name + "-item>");
				table.append("<selected>" + selectedCd + "</selected>");
				table.append("<item>" + strLable + "</item>");
				table.append("<code>" + strCD + "</code>");
				table.append("<url>" + pagejsp + "=" + strCD + "</url>");
				table.append("<sub" + name + "-item>");
				table.append("<subselected></subselected>");
				table.append("<subitem></subitem>");
				table.append("<subcode></subcode>");
				table.append("<suburl></suburl>");
				table.append("</sub" + name + "-item>\n");
				table.append("</" + name + "-item>\n");
				if (strCD.equals(selectedCd)) {
					table.append(getSubXMLDBList(pagejsp, name, selectedCd, strLable, subquery));
				}

			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getMenuXMLDBListOld(String pagejsp, String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		String strParent = "0";
		StringBuffer table = new StringBuffer();
		StringBuffer subTable = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<" + name + ">\n");
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item></item>");
			table.append("<code>" + "-1" + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");

			for (int i = 0; Adp.rows().size() > i; i++) {

				strParent = (String) Adp.getValueAt(i, 2);
				if (strParent.compareTo("0") != 0) {

					strCD = (String) Adp.getValueAt(i, 0);
					strLable = (String) Adp.getValueAt(i, 1);
					subTable.append("<sub" + name + "-item>");
					subTable.append("<subselected>" + selectedCd + "</subselected>");
					subTable.append("<subitem>" + strLable + "</subitem>");
					subTable.append("<subcode>" + strCD + "</subcode>");
					subTable.append("<suburl>" + pagejsp + "=" + strCD + "</suburl>");
					subTable.append("</sub" + name + "-item>\n");
				}

				if (strParent.compareTo("0") == 0) {
					strCD = (String) Adp.getValueAt(i, 0);
					strLable = (String) Adp.getValueAt(i, 1);
					table.append("<" + name + "-item>");
					table.append("<selected>" + selectedCd + "</selected>");
					table.append("<item>" + strLable + "</item>");
					table.append("<code>" + strCD + "</code>");
					table.append("<url>" + pagejsp + "=" + strCD + "</url>");
					// table.append(getSubXMLDBList( pagejsp, name, selected_cd, strLable , subquery
					// )) ;
					table.append(subTable.toString());
					subTable.delete(0, subTable.length());
					table.append("</" + name + "-item>\n");
				}
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	public String getMenuXMLDBList(String pagejsp, String name, String selectedCd, String query) {

		String strCD = "0";
		String strLable = "Other";
		String strParent = "0";
		StringBuffer table = new StringBuffer();

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			table.append("<" + name + ">\n");
			table.append("<" + name + "-item>");
			table.append("<selected>" + selectedCd + "</selected>");
			table.append("<item></item>");
			table.append("<code>" + "-1" + "</code>");
			table.append("<url>" + pagejsp + "=" + strCD + "</url>");
			table.append("</" + name + "-item>\n");

			for (int i = 0; Adp.rows().size() > i; i++) {

				strParent = (String) Adp.getValueAt(i, 2);
				if (strParent.compareTo("-2") == 0) {
					strCD = (String) Adp.getValueAt(i, 0);
					strLable = (String) Adp.getValueAt(i, 1);
					table.append("<" + name + "-item>");
					table.append("<selected>" + selectedCd + "</selected>");
					table.append("<item>" + strLable + "</item>");
					table.append("<code>" + strCD + "</code>");
					table.append("<url>" + pagejsp + "=" + strCD + "</url>");
					table.append(getSubMenuXMLDBList(pagejsp, name, selectedCd, strLable, Adp, strCD));
					table.append("</" + name + "-item>\n");
				}
			}

			table.append("</" + name + ">\n");
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	private String getSubMenuXMLDBList(String pagejsp, String name, String selectedCd, String strLableArg,
			QueryManager Adp, String parent) throws Exception {

		String strCD = "0";
		String strLable = "Other";
		String strParent = "0";
		StringBuffer subTable = new StringBuffer();

		try {

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				strParent = (String) Adp.getValueAt(i, 2);
				if (strParent.compareTo(parent) == 0) {
					subTable.append("<sub" + name + "-item>");
					subTable.append("<subselected>" + selectedCd + "</subselected>");
					subTable.append("<subitem>" + strLable + "</subitem>");
					subTable.append("<subcode>" + strCD + "</subcode>");
					subTable.append("<suburl>" + pagejsp + "=" + strCD + "</suburl>");
					subTable.append("</sub" + name + "-item>\n");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex);
		}

		return subTable.toString();
	}

	private String getSubXMLDBList(String pagejsp, String name, String selectedCd, String strLableArg, String query) {

		String strCD = "0";
		String strLable = "Other";
		StringBuffer table = new StringBuffer();
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);

			/*
			 * for(int i = 0 ; Adp.rows().size() > i ; i++ ) { strCD =
			 * (String)Adp.getValueAt(i,0) ; strLable = (String)Adp.getValueAt(i,1) ;
			 * if(strCD.compareTo(selected_cd)==0) { table.append("<"+name+"-item>");
			 * table.append("<selected>"+ selected_cd +"</selected>");
			 * table.append("<item>"+ strLable +"</item>"); table.append("<code>"+ strCD
			 * +"</code>"); table.append("<url>"+ pagejsp +"=" + strCD +"</url>");
			 * table.append("</"+name+"-item>\n"); break ; } }
			 *
			 */

			for (int i = 0; Adp.rows().size() > i; i++) {
				strCD = (String) Adp.getValueAt(i, 0);
				strLable = (String) Adp.getValueAt(i, 1);
				table.append("<" + name + "-item>");

				// table.append("<selected>" + selected_cd+ "</selected>");
				// table.append("<item>" + strLable_arg + "</item>");
				// table.append("<code>" + selected_cd + "</code>");
				// table.append("<url>" + pagejsp + "=" + strCD + "</url>");

				table.append("<selected></selected>");
				table.append("<item></item>");
				table.append("<code></code>");
				table.append("<url></url>");

				table.append("<sub" + name + "-item>");
				table.append("<subselected>" + selectedCd + "</subselected>");
				table.append("<subitem>" + strLable + "</subitem>");
				table.append("<subcode>" + strCD + "</subcode>");
				table.append("<suburl>" + pagejsp + "=" + strCD + "</suburl>");
				table.append("</sub" + name + "-item>\n");
				table.append("</" + name + "-item>\n");
			}

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
		return table.toString();
	}

	// onpick="depth/pc.wml#pitcher"


	public float getBalans(int intUserID) {
		String field = "0";
		float balans = 0;
		String query = "SELECT  account.amount, currency.currency_lable, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
				+ intUserID;

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				field = (String) Adp.getValueAt(0, 0); // + " " +
			balans = Float.parseFloat(field);

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return balans;
	}

	public float getBalans(long intUserID) {
		String field = "0";
		float balans = 0;
		String query = "SELECT  account.amount, currency.currency_lable, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
				+ intUserID;

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				field = (String) Adp.getValueAt(0, 0); // + " " +
			balans = Float.parseFloat(field);
			;
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return balans;
	}

	public String getStrBalans(long intUserID) {
		String field = "0";
		String falans = "0";
		String query = "SELECT  account.amount, currency.currency_lable, currency.currency_desc FROM account LEFT OUTER JOIN currency ON account.currency_id = currency.currency_id WHERE account.user_id = "
				+ intUserID;

		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
			if (Adp.rows().size() != 0)
				field = (String) Adp.getValueAt(0, 0); // + " " +
			falans = nf.format(Float.parseFloat(field));
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return falans;
	}

	public String getStrFormatNumber(float amount) {
		String falans = "0";
		try {
			falans = nf.format(amount);
		} catch (Exception ex) {
			log.error(ex);
		}
		return falans;
	}

	public String getStrFormatNumber(double amount) {
		String falans = "0";
		try {
			falans = nf.format(amount);
		} catch (Exception ex) {
			log.error(ex);
		}

		return falans;
	}

	public String getStrFormatNumberFloat(String floatAmount) {
		String falans = "0";
		try {
			falans = nf.format(Float.parseFloat(floatAmount));
		} catch (Exception ex) {
			log.error(ex);
		}

		return falans;
	}

	public String getStrFormatNumberDouble(String doubleAmount) {
		String falans = "0";
		try {
			falans = nf.format(Double.parseDouble(doubleAmount));
		} catch (Exception ex) {
			log.error(ex);
		}

		return falans;
	}

}
