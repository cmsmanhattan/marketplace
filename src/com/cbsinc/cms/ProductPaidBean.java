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

public class ProductPaidBean implements java.io.Serializable {

	private static final long serialVersionUID = -361863527175082390L;

	static private Logger log = Logger.getLogger(ItemDescriptionBean.class);

	public String[][] rows = new String[10][2];

	transient public QueryManager Adp;

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String typeId = "1";

	private Integer roleId = 0;

	private String phonetypeId = "-1";

	private String prognameId = "-1";

	private String phonemodelId = "-1";

	private String licenceId = "-1";

	private String imgname;

	private String imageId;

	private String cururl;

	private String catalogId = "-1";

	private String siteId = "0";

	private String productId = "0";

	private String rowId = "0";

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	// Access sample property
	// Access sample property

	public String getProductPaid(String strUserId, String basketId) {
		String description;
		String name;

		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		/*
		 * listup = "Softlisting.jsp?offset=" + (offset + 10) + "&type_id=" + type_id ;
		 * if( offset - 10 < 0 ) listdown = "Softlisting.jsp?offset=0&type_id=" +
		 * type_id ; else listdown = "Softlisting.jsp?offset=" + (offset - 10) +
		 * "&type_id=" + type_id ;
		 */

		cururl = "multilangdescription_list.jsp?offset=" + offset + "&catalog_id=" + catalogId + "&phonetype_id="
				+ phonetypeId + "&licence_id=" + licenceId;

		listup = "multilangdescription_list.jsp?offset=" + (offset + 10); // +
																			// "&catalog_id="
																			// +
																			// catalog_id
																			// +
																			// "&phonetype_id="
																			// +
																			// phonetype_id
																			// +
																			// "&licence_id="
																			// +
																			// licence_id
																			// ;
		if (offset - 10 < 0)
			listdown = "multilangdescription_list.jsp?offset=0"; // &catalog_id="
																	// +
																	// catalog_id
																	// +
																	// "&phonetype_id="
																	// +
																	// phonetype_id
																	// +
																	// "&licence_id="
																	// +
																	// licence_id
																	// ;
		else
			listdown = "multilangdescription_list.jsp?offset=" + (offset - 10); // +
																				// "&catalog_id="
																				// +
																				// catalog_id
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

		// query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" ,
		// \"soft\".\"image_id\" , \"images\".\"img_url\" FROM \"soft\" LEFT
		// JOIN \"images\" ON \"soft\".\"image_id\" = \"images\".\"image_id\"
		// WHERE \"soft\".\"catalog_id\" = " + catalog_id + " and
		// \"soft\".\"phonetype_id\" = " + phonetype_id + " and
		// \"soft\".\"licence_id\" = " + licence_id + " and
		// \"soft\".\"phonemodel_id\" = " + phonemodel_id + " limit 10 offset "
		// + offset ;
		/*
		 * SELECT public.soft.soft_id, public.soft.name , public.soft.description ,
		 * public.multilangdescription.name , public.multilangdescription.description ,
		 * public.soft.version, public.soft.cost, public.soft.currency,
		 * public.soft.serial_nubmer FROM public.basket INNER JOIN public.soft ON
		 * public.basket.product_id = public.soft.soft_id INNER JOIN
		 * public.multilangdescription ON public.basket.product_id =
		 * public.multilangdescription.soft_cd WHERE public.basket.basket_id = 0 AND
		 * public.multilangdescription.lang_cd = 0
		 *
		 */
		query = "SELECT  " + "  public.multilangdescription.multilangdescription_id, "
				+ "  public.multilangdescription.soft_cd, " + "  public.multilangdescription.name, "
				+ "  public.multilangdescription.description, " + "  public.multilangdescription.ative " + " FROM "
				+ "  public.multilangdescription " + " WHERE " + "  public.multilangdescription.ative = true and "
				+ "  public.multilangdescription.soft_cd = " + getProductId() + "" + "  ORDER BY "
				+ "  public.multilangdescription.multilangdescription_id ASC ";

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

		try {
			Adp.executeQuery(query);

			table.append(
					"<TABLE ALIGN=\"CENTER\" WIDTH=\"100%\"   border=\"1\"  CELLSPACING=\"0\" CELLPADDING=\"2\">\n");

			if (roleId == 2) {
				table.append("<TR BGCOLOR=\"#808080\" >" + "<TD>id </TD>" + "<TD>soft_id  </TD>" + "<TD>name </TD>"
						+ "<TD>description </TD>" + "<TD><a href =\"multilangdescription_add.jsp\">add</a> </TD>"
						+ "</TR>\n");
				// "<TD height=\"*\" border=\"0\" >" + this.getComboBox("type_id",
				// "" + type_id ,"select type_id , type_lable from typesoft where
				// active = true") + "<input type=\"submit\" name=\"Submit\"
				// value=\"OK\"></TD>" +
				// postManager
			} else {
				table.append("<TR BGCOLOR=\"#808080\" >" + "<TD height=\"20%\" > id </TD>"
						+ "<TD height=\"40%\" > soft_id </TD>" + "<TD height=\"5%\" > name </TD>"
						+ "<TD height=\"5%\" > description </TD>"
						+ "<TD><a href =\"multilangdescription_add.jsp\">add</a> </TD>" + "</TR>\n");
				// "<TD height=\"*\" border=\"0\" >" + this.getComboBox("type_id",
				// "" + type_id ,"select type_id , type_lable from typesoft where
				// active = true") + "<input type=\"submit\" name=\"Submit\"
				// value=\"OK\"></TD>" +
			}
			// "<TD><A HREF=\"/JspPostPrediction.jsp?offset=" + offset +
			// "&sport_cd=" + sport_cd + "\" ><img SRC=\"images/post.gif\"
			// border=\"0\" alt=\"Post\" ></A></TD>" +

			if (Adp.rows().size() < 10) {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "</TR>\n");
			} else {
				table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\"" + listup
						+ "\">Next 10</a>  </TD>" + "</TR>\n");
			}
			for (int i = 0; Adp.rows().size() > i; i++) {
				rows[i][0] = (String) Adp.getValueAt(i, 0);
				rows[i][1] = (String) Adp.getValueAt(i, 1);
				productId = (String) Adp.getValueAt(i, 1);
				name = (String) Adp.getValueAt(i, 2);
				description = (String) Adp.getValueAt(i, 3);
				table.append("<TR>" + "<TD>" + rows[i][0] + "</TD>" + "<TD>" + rows[i][1] + "</TD>" + "<TD>" + name
						+ "</TD>" + "<TD>" + description + "</TD>" + "<TD><a href =\"multilangdescription_edit.jsp?row="
						+ i + "&name=" + name + "&description=" + description + "&product_cd=" + productId
						+ "\">edit</a> </TD>" + "</TR>\n");
			}

			// listup = Adp.rows.
			/*
			 * if( Adp.rows().size() < 10 ) { table.append("<TR>" + "<TD></TD>" + "<TD>
			 * </TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></
			 * TD>" + "</TR>\n"); } else {
			 */
			table.append("<TR>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD></TD>" + "<TD><a href=\""
					+ listdown + "\">Back 10</a>  </TD>" + "</TR>\n");
			// }

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

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setPhonetypeId(String phonetypeId) {
		this.phonetypeId = phonetypeId;
	}

	public String getPhonetypeId() {
		return phonetypeId;
	}

	public void setPrognameId(String prognameId) {
		this.prognameId = prognameId;
	}

	public String getPrognameId() {
		return prognameId;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageId() {
		return imageId;
	}

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}

	public String getPhonemodelId() {
		return phonemodelId;
	}

	public void setPhonemodelId(String phonemodelId) {
		this.phonemodelId = phonemodelId;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
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

}
