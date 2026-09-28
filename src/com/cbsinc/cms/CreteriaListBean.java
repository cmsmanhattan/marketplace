package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cbsinc.cms.utils.Validation;

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

public class CreteriaListBean implements java.io.Serializable {

	private static final long serialVersionUID = -9098863923171610711L;

	public String[][] rows = new String[10][2];

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private Integer roleId = 0;

	private String cururl;

	static private Logger log = Logger.getLogger(CreteriaListBean.class);

	private String rowId = "0";

	private Integer indxSelect = 0;

	private String tableName = "creteria1 ";
	private String subTableName = "creteria";

	private Integer linkId = 0;

	private String title = "";
	private String subTitle = "";

	private ListDataGet listDataGet = new ListDataGet();
	private ListDataGet sublistDataGet = new ListDataGet();

	transient ResourceBundle localization = null;

	public CreteriaListBean() {

	}

	public void initPage(String addstring) {

		QueryManager Adp = new QueryManager();
		List list = null;
		String query = "";
		query = "select " + tableName + "_id , name , label  FROM " + tableName + " WHERE active = true " + addstring
				+ "  and ( link_id = 0 or link_id = ? )";
		try {
			// paged load: LIMIT via method (rs.absolute(offset) + limit), avoids loading the whole table
			list = Adp.executeQueryWithArgsList(query, new Object[] { linkId }, 10, offset);
			listDataGet.addList(list);

			if (listDataGet.getRowCount() > 0)
				title = listDataGet.getValueAt(0, 2);

			if (tableName != null && tableName.contains("creteria1") ) return ;

			String tableNumber = tableName.replaceAll("creteria","").trim();
			int number = Integer.parseInt(tableNumber) ;
			if (number == 10 ) return ;
			subTableName = subTableName + ++number ;

			query = "select " + subTableName + "_id , name , label  FROM " + subTableName + " WHERE active = true " + addstring
					+ "  and ( link_id = 0 or link_id = ? )";

				list = Adp.executeQueryWithArgsList(query, new Object[] { linkId }, 10, offset);
				sublistDataGet.addList(list);

				if (sublistDataGet.getRowCount() > 0)
					subTitle = sublistDataGet.getValueAt(0, 2);


		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}




	}

	public void setTitle(String label, String addstring) {

		QueryManager Adp = new QueryManager();
		String query = "";
		// query = "select " + table_name + "_id , name , label FROM " + table_name + "
		// WHERE active = true " + addstring + " and ( link_id = 0 or link_id = " +
		// link_id + " ) limit 10 offset " + offset;
		query = "update " + tableName + " set label = '" + Validation.removeSpecificSymbols(label) + "' WHERE 0 = 0 " + addstring;
		this.title = title;
		try {
			Adp.executeUpdate(query);
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}
	}

	public String getPartCriteria(String _criteriaId, boolean isSpace) {

		try {
			if (Long.parseLong(_criteriaId) < 0)
				_criteriaId = "0";
		} catch (Exception ex) {
			log.error(ex);
		}

		return !isSpace ? "" : " and catalog_id = " + _criteriaId;
	}

	public String getTable(Locale locale) {

		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", locale);

		cururl = "Creteria.jsp?offset=" + offset;
		listup = "Creteria.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "Creteria.jsp?offset=0";
		else
			listdown = "Creteria.jsp?offset=" + (offset - 10);

		StringBuffer table = new StringBuffer();
		table.append("<table class=\"columns\">\n");
		table.append("<tbody>\n");
		if (roleId == 2) {
			table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >ID</TD>" + "<TD WIDTH=\"70%\" >"
					+ localization.getString("list_means_of_keywords") + "  </TD>"
					+ "<TD WIDTH=\"20%\" ><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">"
					+ localization.getString("add_creteria") + " </a> </TD>" + "</TR>\n");

		} else {
			table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >ID</TD>" + "<TD WIDTH=\"70%\" >"
					+ localization.getString("list_means_of_creteria") + "  </TD>"
					+ "<TD WIDTH=\"20%\" ><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">"
					+ localization.getString("add_creteria") + " </a> </TD>" + "</TR>\n");
		}

		if (listDataGet.getRowCount() < 10) {
			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
		} else {
			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">"
					+ localization.getString("next_creteria") + " 10</a>  </TD>" + "</TR>\n");
		}

		if (listDataGet.getRowCount() > 0) {
			title = (String) listDataGet.getValueAt(0, 2);
		}

		for (int i = 0; listDataGet.getRowCount() > i; i++) {
			rows[i][0] = (String) listDataGet.getValueAt(i, 0);
			rows[i][1] = (String) listDataGet.getValueAt(i, 1);

			// urlParent = "<a href=\"Creteria.jsp?parent_id="+ rows[i][0] +"\"
			// >"+rows[i][1]+"</a>";

			table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + rows[i][1] + "</TD>"
					+ "<TD algin=\"rigth\" ><a href =\"Creteria_edit.jsp?row=" + i + "\">"
					+ localization.getString("edit_creteria") + "</a> </TD>"
					+ "<TD algin=\"rigth\" ><a href =\"Creteria.jsp?del=" + i + "\">"
					+ localization.getString("del_creteria") + "</a> </TD>" + "</TR>\n");
		}

		table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listdown + "\">"
				+ localization.getString("back_creteria") + " 10</a>  </TD>" + "</TR>\n");
		table.append("</tbody>\n");
		table.append("</TABLE>\n");

		return table.toString();
	}



	public String getTableInXML(Locale locale) {

		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", locale);

		cururl = "Creteria.jsp?offset=" + offset;
		listup = "Creteria.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "Creteria.jsp?offset=0";
		else
			listdown = "Creteria.jsp?offset=" + (offset - 10);

		StringBuffer table = new StringBuffer();
		table.append("<table>\n");
		if (roleId == 2) {
			table.append("<TR>ID</TD>" + "<TD>"+ "<TD><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">"
					+ localization.getString("add_creteria") + " </a> </TD>" + "</TR>\n");

		} else {
			table.append("<TR><TD>ID</TD><TD>"+ localization.getString("list_means_of_creteria") + "  </TD>"
					+ "<TD><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">"
					+ localization.getString("add_creteria") + " </a> </TD>" + "</TR>\n");
		}

		if (listDataGet.getRowCount() < 10) {
			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
		} else {
			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">"
					+ localization.getString("next_creteria") + " 10</a>  </TD>" + "</TR>\n");
		}

		if (listDataGet.getRowCount() > 0) {
			title = (String) listDataGet.getValueAt(0, 2);
		}

		for (int i = 0; listDataGet.getRowCount() > i; i++) {
			rows[i][0] = (String) listDataGet.getValueAt(i, 0);
			rows[i][1] = (String) listDataGet.getValueAt(i, 1);

			// urlParent = "<a href=\"Creteria.jsp?parent_id="+ rows[i][0] +"\"
			// >"+rows[i][1]+"</a>";

			table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + rows[i][1] + "</TD>"
					+ "<TD><a href =\"Creteria_edit.jsp?row=" + i + "\">"
					+ localization.getString("edit_creteria") + "</a> </TD>"
					+ "<TD><a href =\"Creteria.jsp?del=" + i + "\">"
					+ localization.getString("del_creteria") + "</a> </TD>" + "</TR>\n");
		}

		table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listdown + "\">"
				+ localization.getString("back_creteria") + " 10</a>  </TD>" + "</TR>\n");
		table.append("</tbody>\n");
		table.append("</TABLE>\n");

		return table.toString();
	}



	public String getTableOld(String addstring) {
		String urlParent = "";
		cururl = "Creteria.jsp?offset=" + offset;

		listup = "Creteria.jsp?offset=" + (offset + 10);

		if (offset - 10 < 0)
			listdown = "Creteria.jsp?offset=0";
		else
			listdown = "Creteria.jsp?offset=" + (offset - 10);

		StringBuffer table = new StringBuffer();

		// listDataGet.getValueAt(aRow, aColumn)

		QueryManager Adp = new QueryManager();
		String query = "";
		query = "select " + tableName + "_id , name , label  FROM " + tableName + " WHERE active = ? " + addstring
				+ "  and ( link_id = 0 or link_id = ? ) ";
		try {
			Object[] args = new Object[2];
			args[0] = true;
			args[1] = Long.valueOf(linkId);
			// queryManager.executeQuery(query);
			Adp.executeQueryWithArgs(query, args, 10, offset);

			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (roleId == 2) {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>"
						+ "<TD WIDTH=\"70%\" >Критерий  </TD>"
						+ "<TD WIDTH=\"20%\" ><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">добавить</a> </TD>"
						+ "</TR>\n");

			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>"
						+ "<TD WIDTH=\"70%\" >Критерий  </TD>"
						+ "<TD WIDTH=\"20%\" ><a href =\"Creteria_add.jsp?link_id=" + linkId + "\">добавить</a> </TD>"
						+ "</TR>\n");
			}

			if (Adp.rows().size() < 10) {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
			} else {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">след 10</a>  </TD>"
						+ "</TR>\n");
			}

			if (Adp.rows().size() > 0) {
				title = (String) Adp.getValueAt(0, 2);
			}

			for (int i = 0; Adp.rows().size() > i; i++) {
				rows[i][0] = (String) Adp.getValueAt(i, 0);
				rows[i][1] = (String) Adp.getValueAt(i, 1);

				// urlParent = "<a href=\"Creteria.jsp?parent_id="+ rows[i][0] +"\"
				// >"+rows[i][1]+"</a>";

				table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + rows[i][1] + "</TD>"
						+ "<TD algin=\"rigth\" ><a href =\"Creteria_edit.jsp?row=" + i + "\">редактировать</a> </TD>"
						+ "<TD algin=\"rigth\" ><a href =\"Creteria.jsp?del=" + i + "\">удалить</a> </TD>" + "</TR>\n");
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

	String getPageNumber() {
		if (offset == 0)
			return "1";
		if (offset == 10)
			return "2";
		if (offset == 20)
			return "3";
		if (offset == 30)
			return "4";
		if (offset == 40)
			return "5";
		if (offset == 50)
			return "6";
		if (offset == 60)
			return "7";
		if (offset == 70)
			return "8";
		if (offset == 80)
			return "9";
		if (offset == 90)
			return "10";
		if (offset == 100)
			return "11";
		if (offset == 110)
			return "12";
		if (offset == 120)
			return "13";
		if (offset == 130)
			return "14";
		if (offset == 140)
			return "15";
		if (offset == 150)
			return "16";
		if (offset == 160)
			return "17";
		if (offset == 170)
			return "18";
		if (offset == 180)
			return "19";
		if (offset == 190)
			return "20";
		if (offset == 200)
			return "21";
		return "0";
	}

	public void delete(String catalogId) {
		if (catalogId.startsWith("-") || catalogId.equals("0"))
			return;
		QueryManager Adp = new QueryManager();
		String query = "";
		query = "delete FROM " + tableName + " WHERE  " + tableName + "_id = " + catalogId;
		try {
			Adp.executeUpdate(query);
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
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
		Adp.close();
		return true;
	}

	public boolean setPassiveDemand() {
		QueryManager Adp = new QueryManager();
		Adp.close();
		return true;
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

	public String getTableName() {
		return tableName;
	}

	/**
	 * The table name is concatenated into SQL, so it must be one of the ten
	 * criteria tables. Anything else falls back to creteria1 and is logged.
	 */
	public void setTableName(String tableName) {
		if (tableName != null && tableName.trim().matches("creteria([1-9]|10)"))
			this.tableName = tableName.trim();
		else {
			log.warn("Rejected criteria table name: " + tableName);
			this.tableName = "creteria1";
		}
	}

	public int getLinkId() {
		return linkId;
	}

	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
