package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.controllers.SiteType;
import com.cbsinc.cms.faceds.ApplicationContext;

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

public class CatalogListBean implements java.io.Serializable, ApplicationContext {

	private static final long serialVersionUID = 6603561134477886219L;

	public String[][] rows = new String[50][4];

	transient public QueryManager queryManager;

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String cururl;

	private String rowId = "0";

	private Integer indxSelect = 0;

	private String holddate = "0";

	private Boolean isWriteble = true;

	private String message = "";

	static private Logger log = Logger.getLogger(CatalogListBean.class);

	public CatalogListBean() {

	}

	public CatalogListBean(ServletContext applicationContext) {
		this.applicationContext = applicationContext;

	}

	public String getTable(String typeId, AuthorizationPageBean authorizationPageBeanId,
			ServletContext applicationContext) {
		if (typeId == null || typeId.length() == 0)
			typeId = "0";

		String urlParent = "";

		cururl = "catalog_list.jsp?offset=" + offset;

		listup = "catalog_list.jsp?offset=" + (offset + 50);
		if (offset - 50 < 0)
			listdown = "catalog_list.jsp?offset=0";
		else
			listdown = "catalog_list.jsp?offset=" + (offset - 50);

		StringBuffer table = new StringBuffer();

		queryManager = new QueryManager();

		String query = "";

		query = "select catalog_id ,  lable   FROM catalog WHERE active = ? and site_id = ? and parent_id = ? ";

		// query = "select catalog_id , lable FROM catalog WHERE active = true and
		// site_id = " + site_id + " limit 50 offset " + offset;

		try {
			Object[] args = new Object[3];
			args[0] = true;
			args[1] = Long.valueOf(authorizationPageBeanId.getSiteId());
			args[2] = Long.valueOf(authorizationPageBeanId.getCatalogId());
			// args[2] = Long.valueOf(parent_id) ;
			// queryManager.executeQuery(query);
			queryManager.executeQueryWithArgs(query, args, 50, offset);

			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (authorizationPageBeanId.getRoleId() == 2) {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"70%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"20%\" ><a href =\"catalog_add.jsp\">"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("add_catalog")
						+ "</a> </TD>" + "</TR>\n");

			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"70%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"20%\" ><a href =\"catalog_add.jsp\">"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("add_catalog")
						+ "</a> </TD>" + "</TR>\n");
			}

			if (queryManager.rows().size() < 50) {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
			} else {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup + "\">"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("next_catalog")
						+ " 50</a>  </TD>" + "</TR>\n");
			}
			for (int i = 0; queryManager.rows().size() > i; i++) {
				rows[i][0] = (String) queryManager.getValueAt(i, 0);
				rows[i][1] = (String) queryManager.getValueAt(i, 1);

				urlParent = "<a href=\"catalog_list.jsp?parent_id=" + rows[i][0] + "\" >" + rows[i][1] + "</a>";

				table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + urlParent + "</TD>"
						+ "<TD algin=\"rigth\" ><a href =\"catalog_edit.jsp?row=" + i + "\">"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("edit_catalog")
						+ "</a> </TD>" + "<TD algin=\"rigth\" ><a href =\"catalog_list.jsp?del=" + i + "\">"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("del_catalog")
						+ "</a> </TD>" + "</TR>\n");
			}

			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listdown + "\">"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("back_catalog")
					+ " 50</a>  </TD>" + "</TR>\n");
			table.append("</tbody>\n");
			table.append("</TABLE>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}
		return table.toString();
	}

	public String getNavigator(AuthorizationPageBean authorizationPageBeanId) {

		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		String urlParent = "";
		String urlimgParent = "";
		String jspPage = "ProductPostCre.jsp";
		boolean folder = false;
		String image = "";

		cururl = "ProductPostCre.jsp?offset=" + offset;

		listup = "ProductPostCre.jsp?offset=" + (offset + 50);

		if (offset - 50 < 0)
			listdown = "ProductPostCre.jsp?offset=0";
		else
			listdown = "ProductPostCre.jsp?offset=" + (offset - 50);

		StringBuffer table = new StringBuffer();

		queryManager = new QueryManager();

		String query = "";

		query = "select catalog_id, lable, catalog_image_id, catalog_images.imgname from catalog left join catalog_images on catalog.catalog_image_id = catalog_images.catalog_images_id WHERE active = true and lang_id = ? and site_id = ? and parent_id = ? limit 50 offset ?";

		//select catalog_id ,  lable   FROM catalog  INNER JOIN catalog_images ON catalog.CATALOG_IMAGE_ID =catalog_images.CATALOG_IMAGES_ID  ;
		// query = "select catalog_id , lable FROM catalog WHERE active = true and
		// site_id = " + site_id + " limit 50 offset " + offset;

		try {

			queryManager.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getLangId(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogParentId(), offset });
			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div style='overflow-y:auto; width:100%; height:200px;' >\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
				table.append(
						"<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" > </TD>" + "<TD WIDTH=\"77%\" >"
								+ authorizationPageBeanId.getLocalization(applicationContext)
										.getString("section_catalog")
								+ "  </TD>"
								+ "<TD WIDTH=\"10%\" ><a href =\"ProductPostCre.jsp?action=add\"><font color='white' >"
								+ authorizationPageBeanId.getLocalization(applicationContext).getString("add_catalog")
								+ "</font></a> </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");

			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" > </TD>" + "<TD WIDTH=\"77%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");
			}

			for (int i = 0; queryManager.rows().size() > i; i++) {
				rows[i][0] = (String) queryManager.getValueAt(i, 0);
				rows[i][1] = (String) queryManager.getValueAt(i, 1);
				rows[i][2] = (String) queryManager.getValueAt(i, 2);
				rows[i][3] = (String) queryManager.getValueAt(i, 3);
				folder = isFolder(rows[i][0]);
				image = folder
						? "<img alt='"
								+ authorizationPageBeanId.getLocalization(applicationContext)
										.getString("this_is_folder_catalog")
								+ "'  width='22' height='22' src ='images/folder.png' ></img>"
						: "<img alt='Нет вложений' width='22' height='22' src ='images/file.png' ></img>";

				urlimgParent = "<a   href=\"ProductPostCre.jsp?parent_id=" + rows[i][0] + "\" >" + image + "</a>";
				urlParent = "<a   href=\"ProductPostCre.jsp?parent_id=" + rows[i][0] + "\" >" + rows[i][1] + "</a>";

				if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>"
							+ "<TD algin=\"rigth\" ><a href =\"ProductPostCre.jsp?action=edit&row=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("edit_catalog")
							+ "</a> </TD>" + "<TD algin=\"rigth\" ><a href =\"ProductPostCre.jsp?del=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("del_catalog")
							+ "</a> </TD>" + "</TR>\n");
				} else {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>" + "<TD algin=\"rigth\" ></TD>"
							+ "<TD algin=\"rigth\" ></TD>" + "</TR>\n");
				}

			}

			// table.append("<TR>" + "<TD>Страница "+ getPageNumber() +"</TD>" + "<TD> " +
			// authorizationPageBeanId.getLocalization(applicationContext).getString("open_page_catalog")
			// + " <a href=\""+ jspPage+"?offset=0" + "\">1</a>.<a href=\""+
			// jspPage+"?offset=50" + "\">2</a>.<a href=\""+ jspPage+"?offset=20" +
			// "\">3</a>.<a href=\""+ jspPage+"?offset=30" + "\">4</a>.<a href=\""+
			// jspPage+"?offset=40" + "\">5</a>.<a href=\""+ jspPage+"?offset=50" +
			// "\">6</a>.<a href=\""+ jspPage+"?offset=60" + "\">7</a></TD>" + "<TD><a
			// href=\"" + listup + "\">>></a> " +
			// authorizationPageBeanId.getLocalization(applicationContext).getString("next_page")
			// + " </TD>" + "<TD><a href=\"" + listdown + "\"><<</a> </TD>" + "</TR>\n");

			table.append("</tbody>\n");
			table.append("</TABLE>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div>\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			table.append("<TR>" + "<TD  align='left' WIDTH='33%' >"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("page_catalog") + " "
					+ getPageNumber() + " </TD> <TD align='center' WIDTH='34%' > "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("open_page_catalog")
					+ " <a href=\"" + jspPage + "?offset=0" + "\">1</a>.<a href=\"" + jspPage + "?offset=50"
					+ "\">2</a>.<a href=\"" + jspPage + "?offset=100" + "\">3</a>.<a href=\"" + jspPage + "?offset=150"
					+ "\">4</a>.<a href=\"" + jspPage + "?offset=200" + "\">5</a>.<a href=\"" + jspPage + "?offset=250"
					+ "\">6</a>.<a href=\"" + jspPage + "?offset=300" + "\">7</a></TD>"
					+ "<TD WIDTH='33%' align='right' ><a href=\"" + listup + "\"> >> </a> "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("next_page") + " <a href=\""
					+ listdown + "\"> << </a>  </TD>" + "</TR>\n");
			table.append("</tbody>\n");
			table.append("</table>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return table.toString();
	}

	public String getUserNavigator(AuthorizationPageBean authorizationPageBeanId) {

		String urlParent = "";
		String urlimgParent = "";
		String jspPage = "ProductUserPost.jsp";
		boolean folder = false;
		String image = "";
		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);

		cururl = "ProductUserPost.jsp?offset=" + offset;

		listup = "ProductUserPost.jsp?offset=" + (offset + 50);

		if (offset - 50 < 0)
			listdown = "ProductUserPost.jsp?offset=0";
		else
			listdown = "ProductUserPost.jsp?offset=" + (offset - 50);

		StringBuffer table = new StringBuffer();

		queryManager = new QueryManager();

		String query = "";


		query = "select catalog_id, lable, catalog_image_id, catalog_images.imgname from catalog left join catalog_images on catalog.catalog_image_id = catalog_images.catalog_images_id WHERE active = true and lang_id = ? and site_id = ? and parent_id = ? limit 50 offset ?";

		// query = "select catalog_id , lable FROM catalog WHERE active = true and
		// site_id = " + site_id + " limit 50 offset " + offset;

		try {

			queryManager.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getLangId(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogParentId(), offset });
			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div style='overflow-y:auto; width:100%; height:200px;' >\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
				table.append(
						"<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" >№ </TD>" + "<TD WIDTH=\"77%\" >"
								+ authorizationPageBeanId.getLocalization(applicationContext)
										.getString("section_catalog")
								+ "  </TD>"
								+ "<TD WIDTH=\"10%\" ><a href =\"ProductUserPost.jsp?action=add\"><font color='white' >"
								+ authorizationPageBeanId.getLocalization(applicationContext).getString("add_catalog")
								+ "</font></a> </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");

			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" >№ </TD>" + "<TD WIDTH=\"77%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");
			}

			for (int i = 0; queryManager.rows().size() > i; i++) {
				rows[i][0] = (String) queryManager.getValueAt(i, 0);
				rows[i][1] = (String) queryManager.getValueAt(i, 1);
				rows[i][2] = (String) queryManager.getValueAt(i, 2);
				rows[i][3] = (String) queryManager.getValueAt(i, 3);
				folder = isFolder(rows[i][0]);
				image = folder
						? "<img alt='"
								+ authorizationPageBeanId.getLocalization(applicationContext)
										.getString("this_is_folder_catalog")
								+ "'  width='22' height='22' src ='images/folder.png' ></img>"
						: "<img alt='Нет вложений' width='22' height='22' src ='images/file.png' ></img>";

				urlimgParent = "<a   href=\"ProductUserPost.jsp?parent_id=" + rows[i][0] + "\" >" + image + "</a>";
				urlParent = "<a   href=\"ProductUserPost.jsp?parent_id=" + rows[i][0] + "\" >" + rows[i][1] + "</a>";

				if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>"
							+ "<TD algin=\"rigth\" ><a href =\"ProductUserPost.jsp?action=edit&row=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("edit_catalog")
							+ "</a> </TD>" + "<TD algin=\"rigth\" ><a href =\"ProductUserPost.jsp?del=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("del_catalog")
							+ "</a> </TD>" + "</TR>\n");
				} else {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>" + "<TD algin=\"rigth\" ></TD>"
							+ "<TD algin=\"rigth\" ></TD>" + "</TR>\n");
				}

			}

			// table.append("<TR>" + "<TD>Страница "+ getPageNumber() +"</TD>" + "<TD>
			// Открыть страницу <a href=\""+ jspPage+"?offset=0" + "\">1</a>.<a href=\""+
			// jspPage+"?offset=50" + "\">2</a>.<a href=\""+ jspPage+"?offset=20" +
			// "\">3</a>.<a href=\""+ jspPage+"?offset=30" + "\">4</a>.<a href=\""+
			// jspPage+"?offset=40" + "\">5</a>.<a href=\""+ jspPage+"?offset=50" +
			// "\">6</a>.<a href=\""+ jspPage+"?offset=60" + "\">7</a></TD>" + "<TD><a
			// href=\"" + listup + "\">>></a> " +
			// authorizationPageBeanId.getLocalization(applicationContext).getString("next_page")
			// + " </TD>" + "<TD><a href=\"" + listdown + "\"><<</a> </TD>" + "</TR>\n");

			table.append("</tbody>\n");
			table.append("</TABLE>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div>\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			table.append("<TR>" + "<TD  align='left' WIDTH='33%' >"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("page_catalog") + " "
					+ getPageNumber() + " </TD> <TD align='center' WIDTH='34%' > "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("open_page_catalog")
					+ " <a href=\"" + jspPage + "?offset=0" + "\">1</a>.<a href=\"" + jspPage + "?offset=50"
					+ "\">2</a>.<a href=\"" + jspPage + "?offset=100" + "\">3</a>.<a href=\"" + jspPage + "?offset=150"
					+ "\">4</a>.<a href=\"" + jspPage + "?offset=200" + "\">5</a>.<a href=\"" + jspPage + "?offset=250"
					+ "\">6</a>.<a href=\"" + jspPage + "?offset=300" + "\">7</a></TD>"
					+ "<TD WIDTH='33%' align='right' ><a href=\"" + listup + "\"> >> </a> "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("next_page") + " <a href=\""
					+ listdown + "\"> << </a>  </TD>" + "</TR>\n");
			table.append("</tbody>\n");
			table.append("</table>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return table.toString();
	}

	public String getNavigator(String jspPage, AuthorizationPageBean authorizationPageBeanId) {

		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		String urlParent = "";
		String urlimgParent = "";
		// String jspPage = "ProductPostCre.jsp" ;
		boolean folder = false;
		String image = "";

		cururl = jspPage + "?offset=" + offset;

		listup = jspPage + "?offset=" + (offset + 50);

		if (offset - 50 < 0)
			listdown = jspPage + "?offset=0";
		else
			listdown = jspPage + "?offset=" + (offset - 50);

		StringBuffer table = new StringBuffer();

		queryManager = new QueryManager();

		String query = "";

		query = "select catalog_id, lable, catalog_image_id, catalog_images.imgname from catalog left join catalog_images on catalog.catalog_image_id = catalog_images.catalog_images_id WHERE active = true and lang_id = ? and site_id = ? and parent_id = ? limit 50 offset ?";

		// query = "select catalog_id , lable FROM catalog WHERE active = true and
		// site_id = " + site_id + " limit 50 offset " + offset;

		try {

			queryManager.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getLangId(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogParentId(), offset });
			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div style='overflow-y:auto; width:100%; height:200px;' >\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" > </TD>" + "<TD WIDTH=\"77%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"10%\" ><a href =\"" + jspPage + "?action=add\"><font color='white' >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("add_catalog")
						+ "</font></a> </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");

			} else {
				table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"3%\" ></TD>" + "<TD WIDTH=\"77%\" >"
						+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
						+ "  </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");
			}

			for (int i = 0; queryManager.rows().size() > i; i++) {
				rows[i][0] = (String) queryManager.getValueAt(i, 0);
				rows[i][1] = (String) queryManager.getValueAt(i, 1);
				rows[i][2] = (String) queryManager.getValueAt(i, 2);
				rows[i][3] = (String) queryManager.getValueAt(i, 3);
				folder = isFolder(rows[i][0]);
				image = folder
						? "<img alt='"
								+ authorizationPageBeanId.getLocalization(applicationContext)
										.getString("this_is_folder_catalog")
								+ "'  width='22' height='22' src ='images/folder.png' ></img>"
						: "<img alt='Нет вложений' width='22' height='22' src ='images/file.png' ></img>";

				urlimgParent = "<a   href=\"" + jspPage + "?parent_id=" + rows[i][0] + "\" >" + image + "</a>";
				urlParent = "<a   href=\"" + jspPage + "?parent_id=" + rows[i][0] + "\" >" + rows[i][1] + "</a>";

				if (authorizationPageBeanId.getRoleId() == 2 && isWriteble) {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>" + "<TD algin=\"rigth\" ><a href =\""
							+ jspPage + "?action=edit&row=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("edit_catalog")
							+ "</a> </TD>" + "<TD algin=\"rigth\" ><a href =\"" + jspPage + "?del=" + i + "\">"
							+ authorizationPageBeanId.getLocalization(applicationContext).getString("del_catalog")
							+ "</a> </TD>" + "</TR>\n");
				} else {
					table.append("<TR id='" + rows[i][0] + "'  onMouseOver=\"setColor( '#DFE3EF' , '" + rows[i][0]
							+ "' )\"  onMouseOut=\"setColor( 'white' , '" + rows[i][0]
							+ "' )\"   onMouseDown=\"selected( '#FFEFFF' , '" + rows[i][0] + "' )\"  >" + "<TD>"
							+ urlimgParent + "</TD>" + "<TD>" + urlParent + "</TD>" + "<TD algin=\"rigth\" ></TD>"
							+ "<TD algin=\"rigth\" ></TD>" + "</TR>\n");
				}

			}

			// table.append("<TR>" + "<TD>Страница "+ getPageNumber() +"</TD>" + "<TD>
			// Открыть страницу <a href=\""+ jspPage+"?offset=0" + "\">1</a>.<a href=\""+
			// jspPage+"?offset=50" + "\">2</a>.<a href=\""+ jspPage+"?offset=20" +
			// "\">3</a>.<a href=\""+ jspPage+"?offset=30" + "\">4</a>.<a href=\""+
			// jspPage+"?offset=40" + "\">5</a>.<a href=\""+ jspPage+"?offset=50" +
			// "\">6</a>.<a href=\""+ jspPage+"?offset=60" + "\">7</a></TD>" + "<TD><a
			// href=\"" + listup + "\">>></a> " +
			// authorizationPageBeanId.getLocalization(applicationContext).getString("next_page")
			// + " </TD>" + "<TD><a href=\"" + listdown + "\"><<</a> </TD>" + "</TR>\n");

			table.append("</tbody>\n");
			table.append("</TABLE>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

			table.append("<div class='box'>\n");
			table.append("<div class='body'>\n");
			table.append("<div>\n");
			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");
			table.append("<TR>" + "<TD  align='left' WIDTH='33%' >"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("page_catalog") + " "
					+ getPageNumber() + " </TD> <TD align='center' WIDTH='34%' > "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("open_page_catalog")
					+ " <a href=\"" + jspPage + "?offset=0" + "\">1</a>.<a href=\"" + jspPage + "?offset=50"
					+ "\">2</a>.<a href=\"" + jspPage + "?offset=100" + "\">3</a>.<a href=\"" + jspPage + "?offset=150"
					+ "\">4</a>.<a href=\"" + jspPage + "?offset=200" + "\">5</a>.<a href=\"" + jspPage + "?offset=250"
					+ "\">6</a>.<a href=\"" + jspPage + "?offset=300" + "\">7</a></TD>"
					+ "<TD WIDTH='33%' align='right' ><a href=\"" + listup + "\"> >> </a> "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("next_page")
					+ " 50 <a href=\"" + listdown + "\"> << </a>  </TD>" + "</TR>\n");
			table.append("</tbody>\n");
			table.append("</table>\n");
			table.append("</div>\n");
			table.append("</div>\n");
			table.append("</div>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return table.toString();
	}

	String getPageNumber() {
		if (offset == 0)
			return "1";
		if (offset == 50)
			return "2";
		if (offset == 100)
			return "3";
		if (offset == 150)
			return "4";
		if (offset == 200)
			return "5";
		if (offset == 250)
			return "6";
		if (offset == 300)
			return "7";
		if (offset == 350)
			return "8";
		if (offset == 400)
			return "9";
		if (offset == 450)
			return "10";
		if (offset == 500)
			return "11";
		if (offset == 550)
			return "12";
		if (offset == 600)
			return "13";
		if (offset == 650)
			return "14";
		if (offset == 700)
			return "15";
		if (offset == 750)
			return "16";
		if (offset == 800)
			return "17";
		if (offset == 850)
			return "18";
		if (offset == 900)
			return "19";
		if (offset == 950)
			return "20";
		if (offset == 1000)
			return "21";
		return "0";
	}

	/**
	 * For forum functionality
	 *
	 * @return
	 */

	public String getNavigatorMainSiteRead(AuthorizationPageBean authorizationPageBeanId) {

		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		String urlParent = "";
		String jspPage = "ProductPostCre.jsp";

		cururl = "ProductUserPost.jsp?offset=" + offset;

		listup = "ProductUserPost.jsp?offset=" + (offset + 50);
		if (offset - 50 < 0)
			listdown = "ProductUserPost.jsp?offset=0";
		else
			listdown = "ProductUserPost.jsp?offset=" + (offset - 50);

		StringBuffer table = new StringBuffer();

		queryManager = new QueryManager();

		String query = "";

		query = "select catalog_id, lable, catalog_image_id, catalog_images.imgname from catalog left join catalog_images on catalog.catalog_image_id = catalog_images.catalog_images_id WHERE active = true and  site_id = " + SiteType.MAIN_SITE + " and parent_id = ? limit 50 offset ?";

		// query = "select catalog_id , lable FROM catalog WHERE active = true and
		// site_id = " + site_id + " limit 50 offset " + offset;

		try {

			queryManager.executeQueryWithArgs(query, new Object[] { authorizationPageBeanId.getCatalogId(), offset });

			table.append("<table class=\"columns\">\n");
			table.append("<tbody>\n");

			table.append("<TR BGCOLOR=\"#8CACBB\" >" + "<TD WIDTH=\"10%\" >№ </TD>" + "<TD WIDTH=\"70%\" >"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("section_catalog")
					+ "  </TD>" + "<TD WIDTH=\"10%\" ></TD>" + "<TD WIDTH=\"10%\" ></TD>" + "</TR>\n");

			for (int i = 0; queryManager.rows().size() > i; i++) {
				rows[i][0] = (String) queryManager.getValueAt(i, 0);
				rows[i][1] = (String) queryManager.getValueAt(i, 1);
				rows[i][2] = (String) queryManager.getValueAt(i, 2);
				rows[i][3] = (String) queryManager.getValueAt(i, 3);

				// urlParent = "<a href=\"catalog_list.jsp?parent_id="+ rows[i][0] +"\"
				// >"+rows[i][1]+"</a>";
				urlParent = "<a href=\"ProductUserPost.jsp?parent_id=" + rows[i][0] + "\" >" + rows[i][1] + "</a>";

				table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + urlParent + "</TD>"
						+ "<TD algin=\"rigth\" ></TD>" + "<TD algin=\"rigth\" ></TD>" + "</TR>\n");
			}

			table.append("<TR>" + "<TD>"
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("page_catalog") + " "
					+ getPageNumber() + "</TD>" + "<TD> "
					+ authorizationPageBeanId.getLocalization(applicationContext).getString("open_page_catalog")
					+ " <a href=\"" + jspPage + "?offset=0" + "\">1</a>.<a href=\"" + jspPage + "?offset=50"
					+ "\">2</a>.<a href=\"" + jspPage + "?offset=20" + "\">3</a>.<a href=\"" + jspPage + "?offset=30"
					+ "\">4</a>.<a href=\"" + jspPage + "?offset=40" + "\">5</a>.<a href=\"" + jspPage + "?offset=50"
					+ "\">6</a>.<a href=\"" + jspPage + "?offset=60" + "\">7</a></TD>" + "<TD><a href=\"" + listup
					+ "\">>></a> " + authorizationPageBeanId.getLocalization(applicationContext).getString("next_page")
					+ " </TD>" + "<TD><a href=\"" + listdown + "\"><<</a>  </TD>" + "</TR>\n");

			table.append("</tbody>\n");
			table.append("</TABLE>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}
		return table.toString();
	}

	public void delete(String selectedCatalogId, AuthorizationPageBean authorizationPageBeanId) {
		if (selectedCatalogId.startsWith("-") || selectedCatalogId.equals("2"))
			return;
		queryManager = new QueryManager();
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

	public boolean isFolder(String parentId) {
		QueryManager queryManager = new QueryManager();
		String query = "";
		long count = 0;
		try {

			query = "select count(catalog_id) as number  FROM catalog WHERE  parent_id  = " + parentId;
			queryManager.executeQuery(query);
			if (queryManager.rows().size() > 0) {
				count = Long.parseLong((String) queryManager.getValueAt(0, 0));
			}
		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return count == 0 ? false : true;
	}

	public String getCatalogPath(String catalogId) {
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		String lable = "";
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();

		try {

			while (doWhile) {
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + catalogId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentId = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					catalogId = "" + parentId;
					doWhile = true;
					path.append("/" + lable);
				} else
					doWhile = false;
			}

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

	public String getCatalogPath(AuthorizationPageBean authorizationPageBeanId) {
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long catalogId = 0;
		String lable = "";
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();
		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = Long.parseLong(authorizationPageBeanId.getCatalogId());
		if (parentId == 0)
			return "/";

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentId = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					path.insert(0, "/" + lable);
				} else
					doWhile = false;
				// Adp.commit();
			}

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

	public String getCatalogUrlPath(AuthorizationPageBean authorizationPageBeanId) {
		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		long catalogId = 0;
		String lable = "";
		String lableLast = authorizationPageBeanId.getLocalization(applicationContext)
				.getString("section_of_list_catalog");
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();
		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = authorizationPageBeanId.getCatalogParentId();
		if (parentId == 0)
			return lableLast;
		String item = "";

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// path.insert(0,"/" + lable) ;
					item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentId + "\" >" + "/" + lable + "</a>";
					path.insert(0, item);
					parentId = parentIdLast;

				} else
					doWhile = false;
				// Adp.commit();
			}

			item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentIdLast + "\" >" + "/" + lableLast + "</a>";
			path.insert(0, item);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

	public String getCatalogUrlPath(String jspPage, AuthorizationPageBean authorizationPageBeanId) {
		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		long catalogId = 0;
		String lable = "";
		String lableLast = authorizationPageBeanId.getLocalization(applicationContext)
				.getString("section_of_list_catalog");
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();
		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = authorizationPageBeanId.getCatalogParentId();
		if (parentId == 0)
			return lableLast;
		String item = "";

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// path.insert(0,"/" + lable) ;
					item = "<a href=\"" + jspPage + "?parent_id=" + parentId + "\" >" + "/" + lable + "</a>";
					path.insert(0, item);
					parentId = parentIdLast;

				} else
					doWhile = false;
				// Adp.commit();
			}

			item = "<a href=\"" + jspPage + "?parent_id=" + parentIdLast + "\" >" + "/" + lableLast + "</a>";
			path.insert(0, item);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

	public String getCatalogUrlPathMain(AuthorizationPageBean authorizationPageBeanId) {
		// if( localization == null ) localization =
		// PropertyResourceBundle.getBundle("localization", locale);
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		long catalogId = 0;
		String lable = "";
		String lableLast = authorizationPageBeanId.getLocalization(applicationContext)
				.getString("section_of_list_catalog");
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();
		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = Long.parseLong(authorizationPageBeanId.getCatalogId());
		if (parentId == 0)
			return lableLast;
		String item = "";

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// path.insert(0,"/" + lable) ;
					item = "<a href=\"ProductUserPost.jsp?parent_id=" + parentId + "\" >" + "/" + lable + "</a>";
					path.insert(0, item);
					parentId = parentIdLast;

				} else
					doWhile = false;
				// Adp.commit();
			}

			item = "<a href=\"ProductUserPost.jsp?parent_id=" + parentIdLast + "\" >" + "/" + lableLast + "</a>";
			path.insert(0, item);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		return path.toString();
	}

	public String getXMLDBList(String pagejsp, String name, String selectedCd, String query) {

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

	/**
	 * Output path (catalog) for navigation by ctalog warrning parent 0 replace at
	 * -2
	 *
	 * @param pagejsp
	 * @param name
	 * @param selected_cd
	 * @return
	 */

	public String getCatalogXMLUrlPath(String pagejsp, String name, String selectedCd,
			AuthorizationPageBean authorizationPageBeanId) {
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		String lable = "";
		String lableLast = authorizationPageBeanId.getLocalization(applicationContext)
				.getString("section_of_list_catalog");
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();

		StringBuffer rowItem = new StringBuffer();
		rowItem.append("<" + name + ">\n");
		rowItem.append("<" + name + "-item>");
		rowItem.append("<selected>" + selectedCd + "</selected>");
		rowItem.append("<item>" + lableLast + "</item>");
		rowItem.append("<code>" + -2 + "</code>");
		rowItem.append("<url>" + pagejsp + "=" + -2 + "</url>");
		rowItem.append("</" + name + "-item>\n");
		rowItem.append("</" + name + ">\n");

		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = Long.parseLong(selectedCd);
		if (parentId == 0)
			return rowItem.toString();
		// String item = "" ;

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentId +"\" >"+"/" +
					// lable + "</a>" ;
					rowItem = new StringBuffer();
					rowItem.append("<" + name + "-item>");
					rowItem.append("<selected>" + selectedCd + "</selected>");
					rowItem.append("<item>" + lable + "</item>");
					parentId = parentId == 0 ? -2 : parentId;
					rowItem.append("<code>" + parentId + "</code>");
					rowItem.append("<url>" + pagejsp + "=" + parentId + "</url>");
					rowItem.append("</" + name + "-item>\n");
					path.insert(0, rowItem);
					parentId = parentIdLast;
				} else
					doWhile = false;
				// Adp.commit();
			}

			rowItem = new StringBuffer();
			rowItem.append("<" + name + "-item>");
			rowItem.append("<selected>" + selectedCd + "</selected>");
			rowItem.append("<item>" + lableLast + "</item>");
			parentIdLast = parentIdLast == 0 ? -2 : parentIdLast;
			rowItem.append("<code>" + parentIdLast + "</code>");
			rowItem.append("<url>" + pagejsp + "=" + parentIdLast + "</url>");
			rowItem.append("</" + name + "-item>\n");
			path.insert(0, rowItem);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		path.insert(0, "<" + name + ">\n");
		path.append("</" + name + ">\n");
		return path.toString();
	}

	/**
	 * Output path (catalog) for navigation by ctalog warrning parent 0 replace at
	 * -2
	 *
	 * @param pagejsp
	 * @param name
	 * @param catalogReferenceId
	 * @return
	 */

	public String getCatalogXMLUrlPath(String pagejsp, String name, long catalogReferenceId, String catalogId,
			AuthorizationPageBean authorizationPageBeanId) {
		queryManager = new QueryManager();
		String query = "";
		long parentId = 0;
		long parentIdLast = 0;
		String lable = "";
		String lableLast = authorizationPageBeanId.getLocalization(applicationContext)
				.getString("section_of_list_catalog");
		boolean doWhile = true;
		StringBuffer path = new StringBuffer();

		StringBuffer rowItem = new StringBuffer();
		if (catalogId.compareTo("-2") != 0) {
			rowItem.append("<" + name + ">\n");
			rowItem.append("<" + name + "-item>");
			// rowItem.append("<selected>" + selected_cd + "</selected>");
			rowItem.append("<selected>-2</selected>");
			rowItem.append("<item>" + lableLast + "</item>");
			rowItem.append("<code>" + -2 + "</code>");
			rowItem.append("<url>" + pagejsp + "=" + -2 + "</url>");
			rowItem.append("</" + name + "-item>\n");
			rowItem.append("</" + name + ">\n");
		} else {
			rowItem = new StringBuffer();
			rowItem.append("<" + name + ">\n");
			rowItem.append("</" + name + ">\n");
		}

		if (authorizationPageBeanId.getCatalogId().length() > 0)
			parentId = catalogReferenceId;
		if (parentId == 0)
			return rowItem.toString();
		// String item = "" ;

		try {

			while (doWhile) {
				// Adp.BeginTransaction();
				query = "select parent_id , lable   FROM catalog WHERE  catalog_id  = " + parentId;
				queryManager.executeQuery(query);
				if (queryManager.rows().size() > 0) {
					if (((String) queryManager.getValueAt(0, 0)).length() > 0)
						parentIdLast = Long.parseLong((String) queryManager.getValueAt(0, 0));
					lable = (String) queryManager.getValueAt(0, 1);
					doWhile = true;
					// item = "<a href=\"ProductPostCre.jsp?parent_id=" + parentId +"\" >"+"/" +
					// lable + "</a>" ;
					rowItem = new StringBuffer();
					rowItem.append("<" + name + "-item>");
					rowItem.append("<selected>" + catalogReferenceId + "</selected>");
					rowItem.append("<item>" + lable + "</item>");
					parentId = parentId == 0 ? -2 : parentId;
					rowItem.append("<code>" + parentId + "</code>");
					rowItem.append("<url>" + pagejsp + "=" + parentId + "</url>");
					rowItem.append("</" + name + "-item>\n");
					path.insert(0, rowItem);
					parentId = parentIdLast;
				} else
					doWhile = false;
				// Adp.commit();
			}

			rowItem = new StringBuffer();
			rowItem.append("<" + name + "-item>");
			rowItem.append("<selected>" + catalogReferenceId + "</selected>");
			rowItem.append("<item>" + lableLast + "</item>");
			parentIdLast = parentIdLast == 0 ? -2 : parentIdLast;
			rowItem.append("<code>" + parentIdLast + "</code>");
			rowItem.append("<url>" + pagejsp + "=" + parentIdLast + "</url>");
			rowItem.append("</" + name + "-item>\n");
			path.insert(0, rowItem);

		} catch (SQLException ex) {
			// Adp.rollback();
			log.error(query, ex);
		} catch (Exception ex) {
			// Adp.rollback();
			log.error(ex);
		} finally {
			queryManager.close();
		}

		path.insert(0, "<" + name + ">\n");
		path.append("</" + name + ">\n");
		return path.toString();
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


	public long stringToLong(String s) {
		long i;
		try {
			i = Long.parseLong(s);
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

//	public void setRoleId(int roleId) {
//		this.roleId = roleId;
//	}
//
//	public int getRoleId() {
//		return roleId;
//	}

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}

//	public String getCatalog_id() {
//		return catalog_id;
//	}
//
//	public void setCatalog_id(String catalog_id) {
//		this.catalog_id = catalog_id;
//	}

//	public String getSite_id() {
//		return site_id;
//	}
//
//	public void setSite_id(String site_id) {
//		this.site_id = site_id;
//	}

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

	public String getHolddate() {
		return holddate;
	}

	public void setHolddate(String holddate) {
		this.holddate = holddate;
	}

//	public String getParent_id() {
//		return parent_id;
//	}
//
//	public void setParent_id(String parent_id) {
//		this.parent_id = parent_id;
//	}

	transient ServletContext applicationContext = null;

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return applicationContext;
	}

	public void setServletContext(ServletContext applicationContext) {
		this.applicationContext = applicationContext;

	}

}
