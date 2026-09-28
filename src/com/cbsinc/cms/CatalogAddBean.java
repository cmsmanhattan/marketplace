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
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cbsinc.cms.utils.Validation;

public class CatalogAddBean implements java.io.Serializable {

	private static final long serialVersionUID = -7221967667229682339L;

	static private Logger log = Logger.getLogger(CatalogAddBean.class);

	private String query;

	private String name = "0";

	private Integer indxSelect = 0;

	private String holddate = "0";

	transient ResourceBundle sequencesRs = null;
	transient ResourceBundle localization = null;

	public CatalogAddBean() {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization");

	}

	public CatalogAddBean(Locale locale) {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", locale);

	}

	public void addCatalog(AuthorizationPageBean authorizationPageBeanId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String query = "";
		query = sequencesRs.getString("catalog");
		// query = "SELECT NEXT VALUE FOR catalog_catalog_id_seq AS ID FROM
		// ONE_SEQUENCES";

		try {
			Adp.executeQuery(query);
			String catalogId = Adp.getValueAt(0, 0);
			query = "insert into catalog (catalog_id , parent_id , site_id , tax , lable , lang_id ,active , catalog_image_id ) "
					+ " values ( ? , ? , ? , ? , ? , ? , ? , ? ) ";

			Map args = new HashMap();
			args.put("catalog_id", Long.valueOf(catalogId));
			args.put("parent_id", authorizationPageBeanId.getCatalogParentId());
			args.put("site_id", Long.valueOf(authorizationPageBeanId.getSiteId()));
			args.put("tax", 1);
			args.put("lable", Validation.removeSpecificSymbols(name));
			args.put("lang_id", authorizationPageBeanId.getLangId());
			args.put("active", true);
			args.put("catalog_image_id", -1);
			Adp.executeInsertWithArgs(query, args);
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

	public String getAddForm(String _name) {

		// localization.getString("add_catalog")

		StringBuffer buff = new StringBuffer();
		buff.append("<form method=\"post\"   name=\"catalog_add\"  ACTION=\"ProductPostCre.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"add\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  value = " + _name + " />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \""
				+ localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		return buff.toString();
	}

	public String getAddFormWhere(String title, String catalogImageId , String catalogImageName ,  ResourceBundle localization) {

		StringBuffer buff = new StringBuffer();

		buff.append("<h1>" + localization.getString("add_catalog") + " " + title + "</h1><br/> \n");
		buff.append("<div class='box'>\n");
		buff.append("<div class='body'>\n");
		buff.append("<div>\n");
		buff.append("<form method=\"post\"   name=\"catalog_edit\"  ACTION=\"ProductPostCre.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"add\"  />\n");
		buff.append("<TR><TD>Category:* </TD><TD><input type=\"text\" name=\"name\"  />\n");

		buff.append("<TR><TD>" + localization.getString("upload_small_image")  + ": </TD> \n");
		buff.append("<TD><input onChange=\"saveField(this.name,this.value)\" name=\"catalogImagename\"  disabled=\"disabled\" size=\"20\" value=" + catalogImageName + ">");
		buff.append("<input type=\"button\" name=\"newimage\" value=" + localization.getString("new_small_image") + "   onclick=\"dwindow('NewCatalogImage.jsp'); return false;\">");
		buff.append("<input type=\"button\" name=\"selectimage\" value=" + localization.getString("select_small_image") + " onclick=\"dwindow('SelectCatalogImage.jsp'); return false;\">");
		buff.append("<input onChange=\"saveField(this.name,this.value)\" type=\"hidden\"  name=\"catalogImageId\" size=\"20\" value=\"" + catalogImageId+ "\" ></TD></TR>") ;

		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \"" + localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		return buff.toString();
	}

	public String getAddUserCatalog(String title) {

		StringBuffer buff = new StringBuffer();

		buff.append("<h1>" + localization.getString("add_catalog") + " " + title + "</h1><br/> \n");
		buff.append("<div class='box'>\n");
		buff.append("<div class='body'>\n");
		buff.append("<div>\n");
		buff.append("<form method=\"post\"   name=\"catalog_add\"  ACTION=\"ProductUserPost.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"add\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \""
				+ localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		return buff.toString();
	}

	public String getAddForm() {

		StringBuffer buff = new StringBuffer();

		buff.append("<h1>" + localization.getString("add_catalog") + " </h1><br/> \n");
		buff.append("<div class='box'>\n");
		buff.append("<div class='body'>\n");
		buff.append("<div>\n");
		buff.append("<form method=\"post\"   name=\"catalog_add\"  ACTION=\"ProductPostCre.jsp\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"add\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \""
				+ localization.getString("save") + "\" />\n");
		buff.append("</tbody>\n");
		buff.append("</table>\n");
		buff.append("</form>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		buff.append("</div>\n");
		return buff.toString();
	}

	public String getAddFormWithJsp(String jspPage) {

		StringBuffer buff = new StringBuffer();
		buff.append("<form method=\"post\"   name=\"catalog_add\"  ACTION=\"" + jspPage + "\" >\n");
		buff.append("<table>\n");
		buff.append("<tbody>\n");
		buff.append("<TR><TD></TD><TD><input type=\"hidden\" name=\"action\"  value = \"add\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"text\" name=\"name\"  />\n");
		buff.append("<TR><TD></TD><TD><input type=\"submit\" name=\"submit\"  value = \""
				+ localization.getString("add_catalog") + "\" />\n");
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

	public String getHolddate() {
		return holddate;
	}

	public void setHolddate(String holddate) {
		this.holddate = holddate;
	}

}
