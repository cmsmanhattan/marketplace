package com.cbsinc.cms.faceds;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change without written permission from Konstantin Grabko
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

import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.QueryManager;

/**
 * Business logic of catalogue folders: create, rename and delete a folder of the current site.
 */
public class FolderFaced {

	ResourceBundle sequencesRs = null;

	public FolderFaced() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
	}

	transient static private Logger log = Logger.getLogger(FolderFaced.class);

	/**
	 * Creates a folder under the current catalogue of the user's site.
	 * @return the new folder id
	 */
	public String addFolder(final AuthorizationPageBean authorizationPageBeanId, final String name,
			String catalogParentId) {
		QueryManager queryManager = new QueryManager();
		queryManager.beginTransaction();
		String query = "";
		String catalogId = "-1";
		query = sequencesRs.getString("catalog");
		// query = "SELECT NEXT VALUE FOR catalog_catalog_id_seq AS ID FROM
		// ONE_SEQUENCES";

		try {
			queryManager.executeQuery(query);
			catalogId = queryManager.getValueAt(0, 0);
			query = "insert into catalog (catalog_id , parent_id , site_id , tax , lable , lang_id ,active ) "
					+ " values ( ? , ? , ? , ? , ? , ? , ? ) ";

			Map args = new HashMap();
			args.put("catalog_id", Long.valueOf(catalogId));
			// args.put("parent_id" ,
			// Long.valueOf(authorizationPageBeanId.getCatalogParent_id()) );
			args.put("parent_id", Long.valueOf(catalogParentId));
			args.put("site_id", Long.valueOf(authorizationPageBeanId.getSiteId()));
			args.put("tax", 1);
			args.put("lable", name);
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("active", true);
			queryManager.executeInsertWithArgs(query, args);
			queryManager.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			queryManager.rollback();
		} catch (Exception ex) {
			log.error(ex);
			queryManager.rollback();
		} finally {
			queryManager.close();
		}

		return catalogId;
	}

	/**
	 * Renames the currently selected folder.
	 */
	public void editFolder(final AuthorizationPageBean authorizationPageBeanId, final String name) {

		QueryManager queryManager = new QueryManager();
		queryManager.beginTransaction();
		String query = "";
		query = "update catalog set  lable = ? , lang_id = ?  where catalog_id = "
				+ authorizationPageBeanId.getCatalogId() + " and site_id = " + authorizationPageBeanId.getSiteId();

		try {
			HashMap args = new HashMap();
			args.put("lable", name);
			args.put("lang_id", authorizationPageBeanId.getLangId());
			// args.put("parent_id" ,
			// Long.valueOf(authorizationPageBeanId.getCatalogParent_id()) );
			queryManager.executeUpdateWithArgs(query, args);
			queryManager.commit();
		} catch (SQLException ex) {
			queryManager.rollback();
		} finally {
			queryManager.close();
		}

		return;
	}

	/**
	 * Deletes the folder and detaches its content.
	 */
	public void deleteFolder(String selectedCatalogId, AuthorizationPageBean authorizationPageBeanId) {
		if (selectedCatalogId.startsWith("-") || selectedCatalogId.equals("2"))
			return;
		QueryManager queryManager = new QueryManager();
		String query = "";
		query = "delete FROM catalog WHERE site_id = ? and catalog_id = ?";
		try {
			queryManager.executeUpdateWithArgs(query, new Object[] { authorizationPageBeanId.getSiteId(), selectedCatalogId });
		} catch (SQLException ex) {
			System.err.println(query);
			System.err.println(ex);
			System.err.println("" + this.getClass());
			System.err.println("Method " + "delete(String catalog_id)");
		} finally {
			queryManager.close();
		}

	}

}
