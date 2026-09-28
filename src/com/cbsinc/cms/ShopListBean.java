package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ShopListBean implements java.io.Serializable {

	/**
	 *
	 */
	transient private static final long serialVersionUID = 4782598326136441203L;

	transient static private Logger log = Logger.getLogger(WebControls.class);
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

	private Boolean searchquery = false;

	private String searchValueArg = "";

	public String[][] rows = new String[10][2];

	public String[][] newsrows = new String[10][2];

	transient public QueryManager Adp;

	transient public QueryManager newsAdp;

	private Integer offset = 0;

	private String imgUrl;

	private String imgUrl2;

	private String catalogId = "-1";

	private String currencyId = "";

	private String currencyId2 = "";

	private String currencyCd = "";

	private String currencyDesc = "";

	private String currencyDesc2 = "";

	private String productName = "";

	private String productName2 = "";

	private String productUrl = "";

	private String productUrl2 = "";

	private String productIconurl = "";

	private String productIconurl2 = "";

	private String productDescription = "";

	private String productDescription2 = "";

	private String productCost = "";

	private String productCost2 = "";

	public ShopListBean() {
	}

	public String getProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		// openlist
		StringBuffer table = new StringBuffer();
		table.append("<list>\n");
		if (offset - 10 < 0) {
		} else {
		}
		Adp = new QueryManager();
		String query = "";

		// if( searchquery) query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" ,
		// \"soft\".\"image_id\" , \"images\".\"img_url\" ,
		// \"soft\".\"fulldescription\" , \"big_images\".\"img_url\" FROM
		// \"soft\" LEFT JOIN \"images\" ON \"soft\".\"image_id\" =
		// \"images\".\"image_id\" LEFT JOIN \"big_images\" ON
		// \"soft\".\"bigimage_id\" = \"big_images\".\"big_images_id\" WHERE
		// \"soft\".\"site_id\" = " + site_id+ " and \"soft\".\"name\" LIKE '%"
		// + searchValueArg +"' ORDER BY \"soft\".\"soft_id\" DESC limit 10
		// offset " + offset ;
		// else query = "SELECT \"soft\".\"soft_id\",
		// \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\",
		// \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\",
		// \"soft\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" ,
		// \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\" ,
		// \"soft\".\"image_id\" , \"images\".\"img_url\" ,
		// \"soft\".\"fulldescription\" , \"big_images\".\"img_url\" FROM
		// \"soft\" LEFT JOIN \"images\" ON \"soft\".\"image_id\" =
		// \"images\".\"image_id\" LEFT JOIN \"big_images\" ON
		// \"soft\".\"bigimage_id\" = \"big_images\".\"big_images_id\" WHERE
		// \"soft\".\"catalog_id\" = " + catalog_id + " and \"soft\".\"active\"
		// = true and \"soft\".\"site_id\" = " + site_id+ " ORDER BY
		// \"soft\".\"soft_id\" DESC limit 10 offset " + offset ;

		if (searchquery)
			query = "SELECT  \"soft\".\"soft_id\", \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\", \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\", \"file\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" , \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\"  , \"soft\".\"image_id\" , \"images\".\"img_url\" , \"soft\".\"fulldescription\" , \"big_images\".\"img_url\" FROM \"soft\" LEFT  JOIN \"images\" ON \"soft\".\"image_id\" = \"images\".\"image_id\"  LEFT  JOIN \"big_images\" ON \"soft\".\"bigimage_id\" = \"big_images\".\"big_images_id\"  LEFT  JOIN file  ON soft.file_id = file.file_id   WHERE  \"soft\".\"site_id\" = "
					+ siteId + " and \"soft\".\"name\" LIKE '%" + searchValueArg
					+ "' ORDER BY \"soft\".\"soft_id\" DESC limit 10 offset " + offset;
		else
			query = "SELECT  \"soft\".\"soft_id\", \"soft\".\"name\",\"soft\".\"description\", \"soft\".\"version\", \"soft\".\"cost\", \"soft\".\"currency\", \"soft\".\"serial_nubmer\", \"file\".\"file_id\", \"soft\".\"type_id\", \"soft\".\"active\" , \"soft\".\"phonetype_id\" , \"soft\".\"progname_id\"  , \"soft\".\"image_id\" , \"images\".\"img_url\" , \"soft\".\"fulldescription\" , \"big_images\".\"img_url\"  FROM \"soft\" LEFT  JOIN \"images\" ON \"soft\".\"image_id\" = \"images\".\"image_id\"  LEFT  JOIN \"big_images\" ON \"soft\".\"bigimage_id\" = \"big_images\".\"big_images_id\"  LEFT  JOIN file  ON soft.file_id = file.file_id  WHERE \"soft\".\"catalog_id\" = "
					+ catalogId + " and  \"soft\".\"active\" = true  and \"soft\".\"site_id\" = " + siteId
					+ "   ORDER BY \"soft\".\"soft_id\" DESC limit 10 offset " + offset;

		// "LEFT JOIN file ON soft.file_id = file.file_id " +

		try {
			Adp.executeQuery(query);

			for (int i = 0; Adp.rows().size() > i; i = i + 2) {
				rows[i][0] = (String) Adp.getValueAt(i, 0);
				rows[i][1] = Adp.getValueAt(i, 7) == null ? "" : Adp.getValueAt(i, 7);
				// rows[i][1] = Adp.getValueAt(i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				productName = (String) Adp.getValueAt(i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				// strSoftURL = "downloadservlet?row=" + i ;
				productUrl = "ProductInfo.jsp?row=" + i;

				imgUrl = (String) Adp.getValueAt(i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) Adp.getValueAt(i, 2);
				productCost = (String) Adp.getValueAt(i, 4);
				currencyId = (String) Adp.getValueAt(i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();

				table.append("<product>\n");

				table.append("<rigth>\n");
				table.append("<product_id>" + rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				// Referece to pruduct
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
				table.append("<item_info>" + productUrl + "</item_info>\n");
				table.append("<description>" + productDescription + "</description>\n");
				// table.append("<fulldescription>" + product_fulldescription +
				// "</fulldescription>\n") ;
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</rigth>\n");

				if (Adp.rows().size() > (i + 1)) {
					productUrl2 = "ProductInfo.jsp?row=" + (i + 1);
					rows[i + 1][0] = (String) Adp.getValueAt(i + 1, 0);
					rows[i + 1][1] = Adp.getValueAt(i + 1, 7) == null ? "" : Adp.getValueAt(i + 1, 7);
					// rows[i+1][1] = (String)Adp.getValueAt(i+1,7) ;
					productName2 = (String) Adp.getValueAt(i + 1, 1);
					imgUrl2 = (String) Adp.getValueAt(i + 1, 13);
					if (imgUrl2 != null)
						productIconurl2 = imgUrl2;
					else
						productIconurl2 = "images/Folder.jpg";
					productDescription2 = (String) Adp.getValueAt(i + 1, 2);
					productCost2 = (String) Adp.getValueAt(i + 1, 4);
					currencyId2 = (String) Adp.getValueAt(i + 1, 5);
					currencyDesc2 = currencyHash.getCurrencyDecs(currencyId2);
					table.append("<left>\n");
					table.append("<product_id>" + rows[i + 1][0] + "</product_id>\n");
					table.append("<row_id>" + (i + 1) + "</row_id>\n");
					table.append("<name>" + productName2 + "</name>\n");
					table.append("<icon>" + productIconurl2 + "</icon>\n");
					table.append("<image></image>\n");
					table.append("<policy_url>" + productUrl2 + "</policy_url>\n");
					table.append("<item_info>" + productUrl + "</item_info>\n");
					table.append("<description>" + productDescription2 + "</description>\n");
					table.append("<amount>" + productCost2 + "</amount>\n");
					table.append("<currency>\n");
					table.append("<code>" + currencyCd + "</code>\n");
					table.append("<description>dollar us</description>\n");
					table.append("</currency>\n");
					table.append("<version>" + currencyDesc2 + "</version>\n");
					table.append("</left>\n");
				}

				table.append("</product>\n");
			}

			table.append("</list>\n");

		} catch (SQLException ex) {
			log.error(query, ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			Adp.close();
		}

		return table.toString();
	}

}
