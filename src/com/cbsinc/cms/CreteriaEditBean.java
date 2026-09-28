package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.HashMap;

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

public class CreteriaEditBean implements java.io.Serializable {

	private static final long serialVersionUID = 2800199928016319689L;

	private String query;

	private String name = "0";

	private String creteriaId = "0";

	private Integer indxSelect = 0;

	private String tableName = "creteria1 ";

	private Integer linkId = 0;

	static private Logger log = Logger.getLogger(CreteriaEditBean.class);

	public void editCatalog() {

		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";

		query = "update " + tableName + " set  name = ? , link_id = ?  where " + tableName.trim() + "_id = "
				+ creteriaId;

		try {
			HashMap args = new HashMap();
			args.put("name", Validation.removeSpecificSymbols(name));
			args.put("link_id", Long.valueOf(linkId));
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return;
	}

	public String getEditForm(String catalogId, String name) {

		StringBuffer buff = new StringBuffer();
		buff.append("<form method=\"post\"   name=\"creteria_edit\"  ACTION=\"Creteria.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"creteria_id\"  value = " + creteriaId + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  value = " + name + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"edit\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"Сохранить\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		return buff.toString();
	}

	public String getEditForm(String creteriaId, String name, String jspPage) {

		StringBuffer buff = new StringBuffer();
		buff.append("<form method=\"post\"   name=\"creteria_edit\"  ACTION=\"" + jspPage + "\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"creteria_id\"  value = " + creteriaId + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  value = " + name + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"edit\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"Сохранить\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		return buff.toString();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndxSelect() {
		return indxSelect;
	}

	public void setIndxSelect(int indxSelect) {
		this.indxSelect = indxSelect;
	}

	public String getCreteriaId() {
		return creteriaId;
	}

	public void setCreteriaId(String creteriaId) {
		this.creteriaId = creteriaId;
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

}
