package com.cbsinc.cms;

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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cbsinc.cms.utils.Validation;

public class CreteriaAddBean implements java.io.Serializable {

	private static final long serialVersionUID = 8448583709739712289L;

	static private Logger log = Logger.getLogger(CreteriaAddBean.class);

	private String query;

	private String name = "0";

	private String label = "0";

	private String creteriaId = "0";

	private Integer indxSelect = 0;

	private String tableName = "creteria1";

	private Integer linkId = 0;

	transient ResourceBundle sequencesRs = null;

	public CreteriaAddBean() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
	}

	public void addCatalog(String siteId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		// query = "SELECT NEXT VALUE FOR " + table_name + "_" + table_name + "_id_seq
		// AS ID FROM ONE_SEQUENCES";
		query = "SELECT COALESCE(MAX(" + tableName + "_id ),0) + 1  as ID FROM " + tableName + " FOR UPDATE";

		try {

			Adp.executeQuery(query);

			creteriaId = Adp.getValueAt(0, 0);

			query = "insert into " + tableName + " (" + tableName
					+ "_id , catalog_id , link_id , name , label , active ) " + " values ( ? , ? , ? , ? , ? , ? ) ";

			Map args = new HashMap();
			args.put(tableName + "_id", creteriaId);
			args.put("catalog_id", siteId);
			args.put("link_id", linkId);
			args.put("name", Validation.removeSpecificSymbols(name));
			args.put("label", Validation.removeSpecificSymbols(label));
			args.put("active", true);
			Adp.executeInsertWithArgs(query, args);
			Adp.commit();

		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(query, ex);
			Adp.rollback();
		}

		finally {
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
