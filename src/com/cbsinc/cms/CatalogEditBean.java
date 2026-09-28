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
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;

import com.cbsinc.cms.utils.Validation;

public class CatalogEditBean implements java.io.Serializable {

	transient private static final long serialVersionUID = -6130230014231390789L;

	private String query;

	private String name = "0";
	private long catalogImageId = -1;

	private Integer indxSelect = 0;

	private String holddate = "0";
	transient ResourceBundle localization = null;

	public CatalogEditBean(Locale locale) {
		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", locale);
	}

	public CatalogEditBean() {
		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization");

	}

	public void editCatalog(AuthorizationPageBean authorizationPageBeanId) {

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

		query = "update catalog set  lable = ? , lang_id = ? , catalog_image_id = ?  where catalog_id = "
				+ authorizationPageBeanId.getCatalogId() + " and site_id = " + authorizationPageBeanId.getSiteId();

		try {
			HashMap args = new HashMap();
			args.put("lable", Validation.removeSpecificSymbols(name));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("catalog_image_id", catalogImageId);
			// args.put("parent_id" ,
			// Long.valueOf(authorizationPageBeanId.getCatalogParent_id()) );
			Adp.executeUpdateWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return;
	}

//edit_catalog
	// "+localization.getString("edit_catalog") +"
	public String getEditForm(String catalogId, String name, String catalogImageId ,  String catalogImageName , ResourceBundle localization) {

		StringBuffer buff = new StringBuffer();
		buff.append("<h1>" + localization.getString("edit_catalog") + " " + name + "</h1><br/> \n");
		buff.append("<div class='box'>\n");
		buff.append("<div class='body'>\n");
		buff.append("<div>\n");
		buff.append("<form method=\"post\"   name=\"catalog_edit\"  ACTION=\"ProductPostCre.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"catalog_id\"  value = " + catalogId + " />\n");
		buff.append("<TR><TD>Category:* </TD><TD><input type=\"text\" name=\"name\"  value = " + name + " />\n");
		buff.append("<TR><TD>" + localization.getString("upload_small_image")  + ": </TD> \n");
		buff.append("<TD><input onChange=\"saveField(this.name,this.value)\" name=\"catalogImagename\"  disabled=\"disabled\" size=\"20\" value=" + catalogImageName + ">");
		buff.append("<input type=\"button\" name=\"newimage\" value=" + localization.getString("new_small_image") + "   onclick=\"dwindow('NewCatalogImage.jsp'); return false;\">");
		buff.append("<input type=\"button\" name=\"selectimage\" value=" + localization.getString("select_small_image") + " onclick=\"dwindow('SelectCatalogImage.jsp'); return false;\">");
		buff.append("<input onChange=\"saveField(this.name,this.value)\" type=\"hidden\"  name=\"catalogImageId\" size=\"20\" value=\"" + catalogImageId+ "\" ></TD></TR>") ;

		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"edit\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"" + localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");

		return buff.toString();
	}

	public String getEditUserCatalog(String catalogId, String name) {

		StringBuffer buff = new StringBuffer();
		buff.append("<h1>" + localization.getString("edit_catalog") + " " + name + "</h1><br/> \n");
		buff.append("<div class='box'>\n");
		buff.append("<div class='body'>\n");
		buff.append("<div>\n");
		buff.append("<form method=\"post\"   name=\"catalog_edit\"  ACTION=\"ProductUserPost.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"catalog_id\"  value = " + catalogId + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  value = " + name + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"edit\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"" + localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");

		return buff.toString();
	}

	public String getEditForm(String catalogId, String name, String jspPage) {

		StringBuffer buff = new StringBuffer();
		buff.append("<form method=\"post\"   name=\"catalog_edit\"  ACTION=\"" + jspPage + "\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"catalog_id\"  value = " + catalogId + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  value = " + name + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"edit\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"" + localization.getString("save") + "\" />\n");
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



	public long getCatalogImageId() {
		return catalogImageId;
	}

	public void setCatalogImageId(long catalogImageId) {
		this.catalogImageId = catalogImageId;
	}

	public int getIndxSelect() {
		return indxSelect;
	}

	public void setIndxSelect(int indxSelect) {
		this.indxSelect = indxSelect;
	}

	public String getHolddate() {
		return holddate;
	}

	public void setHolddate(String holddate) {
		this.holddate = holddate;
	}

}
