package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

public class SearchBean implements java.io.Serializable {

	/**
	 *
	 */

	/**
	 *
	 */

	/**
	 *
	 */
	transient private static final long serialVersionUID = -7111281278620430928L;

	/**
	 *
	 */
	transient static private Logger log = Logger.getLogger(SearchBean.class);

	private String searchValueArg = "";

	public String[][] rows = new String[10][2];

	// public String[][] news_rows = new String[10][2] ;
	public String[][] co1Rows = new String[10][2];

	public String[][] co2Rows = new String[10][2];

	// public String[][] allquery_rows = new String[10][2];

	private transient GetValueTool tool = new GetValueTool();

	public List allqueryAdp = new LinkedList();

	public List Adp = new LinkedList();

	public List co1Adp = new LinkedList();

	public List co2Adp = new LinkedList();

	private List tmpAdp = new LinkedList();

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String type1Id = "1";

	private String type2Id = "1";

	// private int type_id = 1 ;
	private Long roleId = new Long(0);

	private String phonetypeId = "-1";

	private String prognameId = "-1";

	private String phonemodelId = "-1";

	private String licenceId = "-1";

	private String imgname;

	private String imageId;

	private String imgUrl;

	private String imgUrl2;

	private String user1Id;

	private String user2Id;

	private String cururl;

	// private String catalog_id = "-2";

	private String catalogParentId = "0";

	// private String site_id = "0";

	private String postManager = "";

	private String currencyId = "";

	private String currencyId2 = "";

	private String currencyCd = "";

	private String currencyDesc = "";

	private String currencyDesc2 = "";

	private String productName = "";

	private String productName2 = "";

	private String productUrl = "";

	private String productUrl2 = "";

	private String productImgurl = "";

	private String productIconurl = "";

	private String productIconurl2 = "";

	private String productDescription = "";

	private String productDescription2 = "";

	private String productFulldescription = "";

	private String productVersion = "";

	private String productCost = "";

	private String productCost2 = "";

	private String productCurrency = "";

	private String productOwner = "";

	private String productId = "";

	private String action = "";

	private Integer searchquery = 0;

	private Integer pagecount = 0;

	private String portlettypeId = "0";

	private Integer pagecountBlog = 0;

	private Integer pagecountCo1 = 0;

	private Integer pagecountCo2 = 0;

	private String queryProductlist = "";

	private String selectCurrencyCd = "";
	private String selectPath = "";

	private String color = "";

	private String allFoundProducts = "";

	String dialog = "true";
	String advancedSearchOpen = "true";
	String forumOpen = "true";

	public Boolean isInternet = true;

	public SearchBean() {

	}

	public String getProductsIdlist(int count) {
		String productsId = "";
		try {
			rows = new String[tool.getRowCount(Adp)][2];
			if (rows.length < count)
				return "";
			for (int i = 0; rows.length > i; i++) {
				productsId = productsId + "_" + rows[i][0];
				if (i == (count - 1))
					break;
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return productsId.substring(1);
	}

	public String getQuantityProducts() {
		return "" + tool.getRowCount(allqueryAdp);
	}

	public String getProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		rows = new String[tool.getRowCount(Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<list>\n");
		cururl = "Productlist.jsp?offset=" + offset;
		listup = "Productlist.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "Productlist.jsp?offset=0";
		else
			listdown = "Productlist.jsp?offset=" + (offset - 10);

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecount = tool.getRowCount(Adp);

			for (int i = 0; tool.getRowCount(Adp) > i; i = i + 2) {
				rows[i][0] = (String) tool.getValueAt(Adp, i, 0);
				rows[i][1] = tool.getValueAt(Adp, i, 7) == null ? "" : tool.getValueAt(Adp, i, 7);
				// rows[i][1] = Adp.getValueAt(i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				productName = (String) tool.getValueAt(Adp, i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				// strSoftURL = "downloadservlet?row=" + i ;
				/////////////// product_url = "ProductInfo.jsp?row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(Adp, i, 0);
				// licy_byproductid

				imgUrl = (String) tool.getValueAt(Adp, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) tool.getValueAt(Adp, i, 2);
				productVersion = (String) tool.getValueAt(Adp, i, 3);
				productCost = (String) tool.getValueAt(Adp, i, 4);
				currencyId = (String) tool.getValueAt(Adp, i, 5);

				CurrencyHash currencyHash = CurrencyHash.getInstance();

				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();

				// phonetype_id = (String)tool.getValueAt(Adp,i,10) ;
				// phonetype_id2 = (String)tool.getValueAt(Adp,i,10) ;
				// progname_id = (String)tool.getValueAt(Adp,i,11) ;
				imageId = (String) tool.getValueAt(Adp, i, 12);
				productFulldescription = (String) tool.getValueAt(Adp, i, 14);
				// product_bigimgurl = (String)tool.getValueAt(Adp,i,15) ;
				user1Id = (String) tool.getValueAt(Adp, i, 16);
				// cdate = (Date)Adp.getValueObjectAt(i,17) ;
				// statistic = (String)tool.getValueAt(Adp,i,18) ;
				// user1_id = (String)tool.getValueAt(Adp,i,16) ;
				// private String statistic = "0" ;
				// private Date cdate =null ;

				type1Id = (String) tool.getValueAt(Adp, i, 8);

				color = (String) tool.getValueAt(Adp, i, 40);

				table.append("<product>\n");

				table.append("<rigth>\n");
				table.append("<product_id>" + rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<type_id>" + type1Id + "</type_id>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
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
				table.append("<color>" + color + "</color>\n");
				table.append("</rigth>\n");

				if (tool.getRowCount(Adp) > (i + 1)) {
					/// product_url2 = "ProductInfo.jsp?row=" + (i + 1);
					productUrl2 = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(Adp, i + 1, 0);

					rows[i + 1][0] = (String) tool.getValueAt(Adp, i + 1, 0);
					rows[i + 1][1] = tool.getValueAt(Adp, i + 1, 7) == null ? "" : tool.getValueAt(Adp, i + 1, 7);
					// rows[i+1][1] = (String)tool.getValueAt(Adp,i+1,7) ;
					productName2 = (String) tool.getValueAt(Adp, i + 1, 1);
					imgUrl2 = (String) tool.getValueAt(Adp, i + 1, 13);
					if (imgUrl2 != null)
						productIconurl2 = imgUrl2;
					else
						productIconurl2 = "images/Folder.jpg";
					productDescription2 = (String) tool.getValueAt(Adp, i + 1, 2);
					productCost2 = (String) tool.getValueAt(Adp, i + 1, 4);
					currencyId2 = (String) tool.getValueAt(Adp, i + 1, 5);
					// CurrencyHash currencyHash = CurrencyHash.getInstance() ;
					currencyDesc2 = currencyHash.getCurrencyDecs(currencyId2);
					user2Id = (String) tool.getValueAt(Adp, i + 1, 16);
					type2Id = (String) tool.getValueAt(Adp, i + 1, 8);
					color = (String) tool.getValueAt(Adp, i + 1, 40);

					table.append("<left>\n");
					table.append("<product_id>" + rows[i + 1][0] + "</product_id>\n");
					table.append("<row_id>" + (i + 1) + "</row_id>\n");
					table.append("<name>" + productName2 + "</name>\n");
					table.append("<type_id>" + type2Id + "</type_id>\n");
					table.append("<icon>" + productIconurl2 + "</icon>\n");
					table.append("<image></image>\n");
					table.append("<user_id>" + user2Id + "</user_id>\n");
					// Referece to pruduct
					table.append("<policy_url>" + productUrl2 + "</policy_url>\n");
					table.append("<item_info>" + productUrl + "</item_info>\n");
					table.append("<description>" + productDescription2 + "</description>\n");
					// table.append("<fulldescription>" + product_fulldescription +
					// "</fulldescription>\n") ;
					table.append("<amount>" + productCost2 + "</amount>\n");
					table.append("<currency>\n");
					table.append("<code>" + currencyCd + "</code>\n");
					table.append("<description>dollar us</description>\n");
					table.append("</currency>\n");
					table.append("<version>" + currencyDesc2 + "</version>\n");
					table.append("<color>" + color + "</color>\n");
					table.append("</left>\n");
				}

				table.append("</product>\n");
			}
			table.append("</list>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	public String getProductSimpleList(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		rows = new String[tool.getRowCount(Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<product_list>\n");
		cururl = "Productlist.jsp?offset=" + offset;
		listup = "Productlist.jsp?offset=" + (offset + 10);
		if (offset - 10 < 0)
			listdown = "Productlist.jsp?offset=0";
		else
			listdown = "Productlist.jsp?offset=" + (offset - 10);

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecount = tool.getRowCount(Adp);

			for (int i = 0; tool.getRowCount(Adp) > i; i++) {
				rows[i][0] = (String) tool.getValueAt(Adp, i, 0);
				rows[i][1] = tool.getValueAt(Adp, i, 7) == null ? "" : tool.getValueAt(Adp, i, 7);
				// rows[i][1] = tool.getValueAt(Adp,i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				productName = (String) tool.getValueAt(Adp, i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				// strSoftURL = "downloadservlet?row=" + i ;
				///// product_url = "ProductInfo.jsp?row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(Adp, i, 0);

				imgUrl = (String) tool.getValueAt(Adp, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) tool.getValueAt(Adp, i, 2);
				productVersion = (String) tool.getValueAt(Adp, i, 3);
				productCost = (String) tool.getValueAt(Adp, i, 4);
				currencyId = (String) tool.getValueAt(Adp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();

				// phonetype_id = (String)tool.getValueAt(Adp,i,10) ;
				// phonetype_id2 = (String)tool.getValueAt(Adp,i,10) ;
				// progname_id = (String)tool.getValueAt(Adp,i,11) ;
				imageId = (String) tool.getValueAt(Adp, i, 12);
				productFulldescription = (String) tool.getValueAt(Adp, i, 14);
				// product_bigimgurl = (String)tool.getValueAt(Adp,i,15) ;
				user1Id = (String) tool.getValueAt(Adp, i, 16);
				// cdate = (Date)Adp.getValueObjectAt(i,17) ;
				// statistic = (String)tool.getValueAt(Adp,i,18) ;
				// user1_id = (String)tool.getValueAt(Adp,i,16) ;
				// private String statistic = "0" ;
				// private Date cdate =null ;
				color = (String) tool.getValueAt(Adp, i, 40);

				type1Id = (String) tool.getValueAt(Adp, i, 8);

				table.append("<product>\n");
				table.append("<product_id>" + rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<type_id>" + type1Id + "</type_id>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
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
				table.append("<color>" + color + "</color>\n");
				table.append("</product>\n");
			}
			table.append("</product_list>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	String getEmptyProductList() {
		StringBuffer table = new StringBuffer();
		table.append("<list>\n");
		table.append("</list>\n");
		return table.toString();
	}

	public String getProduct(String softId) throws SQLException {

		StringBuffer table = new StringBuffer();
		table.append("<list>\n");
		cururl = "Productlist.jsp?offset=" + offset; // + "&catalog_id=" +
														// catalog_id +
														// "&phonetype_id=" +
														// phonetype_id +
														// "&licence_id=" +
														// licence_id ;
		listup = "Productlist.jsp?offset=" + (offset + 10); // + "&catalog_id="
															// + catalog_id +
															// "&phonetype_id="
															// + phonetype_id +
															// "&licence_id=" +
															// licence_id ;
		if (offset - 10 < 0)
			listdown = "Productlist.jsp?offset=0"; // &catalog_id=" +
													// catalog_id +
													// "&phonetype_id=" +
													// phonetype_id +
													// "&licence_id=" +
													// licence_id ;
		else
			listdown = "Productlist.jsp?offset=" + (offset - 10); // +
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

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecount = tool.getRowCount(tmpAdp);

			for (int i = 0; tool.getRowCount(tmpAdp) > i; i++) {
				rows[i][0] = (String) tool.getValueAt(tmpAdp, i, 0);
				rows[i][1] = tool.getValueAt(tmpAdp, i, 7) == null ? "" : tool.getValueAt(tmpAdp, i, 7);
				productName = (String) tool.getValueAt(tmpAdp, i, 1);
				productUrl = "ProductInfo.jsp?row=" + i;

				imgUrl = (String) tool.getValueAt(tmpAdp, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) tool.getValueAt(tmpAdp, i, 2);
				productVersion = (String) tool.getValueAt(tmpAdp, i, 3);
				productCost = (String) tool.getValueAt(tmpAdp, i, 4);
				currencyId = (String) tool.getValueAt(tmpAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(tmpAdp, i, 12);
				productFulldescription = (String) tool.getValueAt(tmpAdp, i, 14);
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();

	}

	/*
	 * Здесь наверное ошибка public String getProduct(String _soft_id) throws
	 * SQLException {
	 *
	 * StringBuffer table = new StringBuffer(); table.append("<list>\n"); cururl =
	 * "Productlist.jsp?offset=" + offset; // + "&catalog_id=" + // catalog_id + //
	 * "&phonetype_id=" + // phonetype_id + // "&licence_id=" + // licence_id ;
	 * listup = "Productlist.jsp?offset=" + (offset + 10); // + "&catalog_id=" // +
	 * catalog_id + // "&phonetype_id=" // + phonetype_id + // "&licence_id=" + //
	 * licence_id ; if (offset - 10 < 0) listdown = "Productlist.jsp?offset=0"; //
	 * &catalog_id=" + // catalog_id + // "&phonetype_id=" + // phonetype_id + //
	 * "&licence_id=" + // licence_id ; else listdown = "Productlist.jsp?offset=" +
	 * (offset - 10); // + // "&catalog_id=" // + // catalog_id // + //
	 * "&phonetype_id=" // + // phonetype_id // + // "&licence_id=" // + //
	 * licence_id // ;
	 *
	 *
	 * try { if (roleId == 2) setPost_manager("PostManager.jsp"); else
	 * setPost_manager(""); pagecount = tool.getRowCount(tmpAdp);
	 *
	 * for (int i = 0; tool.getRowCount(tmpAdp) > i; i++) { ext1_rows[i][0] =
	 * (String) tool.getValueAt( tmpAdp,i, 0); ext1_rows[i][1] = tool.getValueAt(
	 * tmpAdp,i, 7) == null ? "" : tool.getValueAt( tmpAdp,i, 7); product_name =
	 * (String) tool.getValueAt( tmpAdp,i, 1); product_url =
	 * "ProductInfo.jsp?co1_row=" + i;
	 *
	 * img_url = (String) tool.getValueAt( tmpAdp,i, 13); if (img_url != null)
	 * product_iconurl = img_url; else product_iconurl = "images/Folder.jpg";
	 * product_description = (String) tool.getValueAt( tmpAdp,i, 2); product_version
	 * = (String) tool.getValueAt( tmpAdp,i, 3); product_cost = (String)
	 * tool.getValueAt( tmpAdp,i, 4); currency_id = (String) tool.getValueAt(
	 * tmpAdp,i, 5); currency_desc = CurrencyHash.getCurrency_decs(currency_id);
	 * image_id = (String) tool.getValueAt( tmpAdp,i, 12); product_fulldescription =
	 * (String) tool.getValueAt( tmpAdp,i, 14); } } catch (Exception ex) {
	 * log.error(ex); } return table.toString();
	 *
	 * }
	 *
	 */

	public String getCoOneProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		co1Rows = new String[tool.getRowCount(co1Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<coproductlist1>\n");
		cururl = "Productlist.jsp?offset=" + offset; // + "&catalog_id=" +
														// catalog_id +
														// "&phonetype_id=" +
														// phonetype_id +
														// "&licence_id=" +
														// licence_id ;
		listup = "Productlist.jsp?offset=" + (offset + 10); // + "&catalog_id="
															// + catalog_id +
															// "&phonetype_id="
															// + phonetype_id +
															// "&licence_id=" +
															// licence_id ;
		if (offset - 10 < 0)
			listdown = "Productlist.jsp?offset=0"; // &catalog_id=" +
													// catalog_id +
													// "&phonetype_id=" +
													// phonetype_id +
													// "&licence_id=" +
													// licence_id ;
		else
			listdown = "Productlist.jsp?offset=" + (offset - 10); // +
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

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecountCo1 = tool.getRowCount(co1Adp);

			for (int i = 0; tool.getRowCount(co1Adp) > i; i++) {
				co1Rows[i][0] = (String) tool.getValueAt(co1Adp, i, 0);
				co1Rows[i][1] = tool.getValueAt(co1Adp, i, 7) == null ? "" : tool.getValueAt(co1Adp, i, 7);
				productName = (String) tool.getValueAt(co1Adp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(co1Adp, i, 0);
				// product_url = "ProductInfo.jsp?co1_row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(co1Adp, i, 0);

				productIconurl = (String) tool.getValueAt(co1Adp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(co1Adp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (co1Rows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(co1Adp, i, 2);
				productVersion = (String) tool.getValueAt(co1Adp, i, 3);
				productCost = (String) tool.getValueAt(co1Adp, i, 4);
				currencyId = (String) tool.getValueAt(co1Adp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(co1Adp, i, 12);
				productFulldescription = (String) tool.getValueAt(co1Adp, i, 14);
				user1Id = (String) tool.getValueAt(co1Adp, i, 16);

				table.append("<coproduct1>\n");
				table.append("<product_id>" + co1Rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append(
						"<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				// Referece to pruduct
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
				table.append("<item_info>" + productUrl + "</item_info>\n");
				table.append("<product_url>" + attacheFile + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				// table.append("<fulldescription>" + product_fulldescription +
				// "</fulldescription>\n") ;
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</coproduct1>\n");
			}
			table.append("</coproductlist1>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	public String getCoTwoProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		co2Rows = new String[tool.getRowCount(co2Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<coproductlist2>\n");
		cururl = "Productlist.jsp?offset=" + offset; // + "&catalog_id=" +
														// catalog_id +
														// "&phonetype_id=" +
														// phonetype_id +
														// "&licence_id=" +
														// licence_id ;
		listup = "Productlist.jsp?offset=" + (offset + 10); // + "&catalog_id="
															// + catalog_id +
															// "&phonetype_id="
															// + phonetype_id +
															// "&licence_id=" +
															// licence_id ;
		if (offset - 10 < 0)
			listdown = "Productlist.jsp?offset=0"; // &catalog_id=" +
													// catalog_id +
													// "&phonetype_id=" +
													// phonetype_id +
													// "&licence_id=" +
													// licence_id ;
		else
			listdown = "Productlist.jsp?offset=" + (offset - 10); // +
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

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecountCo2 = tool.getRowCount(co2Adp);

			for (int i = 0; tool.getRowCount(co2Adp) > i; i++) {
				co2Rows[i][0] = (String) tool.getValueAt(co2Adp, i, 0);
				co2Rows[i][1] = tool.getValueAt(co2Adp, i, 7) == null ? "" : tool.getValueAt(co2Adp, i, 7);
				productName = (String) tool.getValueAt(co2Adp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(co2Adp, i, 0);
				// product_url = "ProductInfo.jsp?co2_row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(co2Adp, i, 0);

				productIconurl = (String) tool.getValueAt(co2Adp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(co2Adp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (co2Rows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(co2Adp, i, 2);
				productVersion = (String) tool.getValueAt(co2Adp, i, 3);
				productCost = (String) tool.getValueAt(co2Adp, i, 4);
				currencyId = (String) tool.getValueAt(co2Adp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(co2Adp, i, 12);
				productFulldescription = (String) tool.getValueAt(co2Adp, i, 14);
				user1Id = (String) tool.getValueAt(co2Adp, i, 16);
				table.append("<coproduct2>\n");
				table.append("<product_id>" + co2Rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append(
						"<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				// Referece to pruduct
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
				table.append("<item_info>" + productUrl + "</item_info>\n");
				table.append("<product_url>" + attacheFile + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</coproduct2>\n");
			}
			table.append("</coproductlist2>\n");
		} catch (Exception ex) {
			log.error(ex);
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
			log.error(ex);
		}
		return i;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getRoleId() {
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

	public String getPostManager() {
		return postManager;
	}

	public void setPostManager(String postManager) {
		this.postManager = postManager;
	}

	public String getListup() {
		return listup;
	}

	public void setListup(String listup) {
		this.listup = listup;
	}

	public String getListdown() {
		return listdown;
	}

	public void setListdown(String listdown) {
		this.listdown = listdown;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCd() {
		return currencyCd;
	}

	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductImgurl() {
		return productImgurl;
	}

	public void setProductImgurl(String productImgurl) {
		this.productImgurl = productImgurl;
	}

	public String getProductIconurl() {
		return productIconurl;
	}

	public void setProductIconurl(String productIconurl) {
		this.productIconurl = productIconurl;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	public String getProductCurrency() {
		return productCurrency;
	}

	public void setProductCurrency(String productCurrency) {
		this.productCurrency = productCurrency;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getTrueValue(String tmp1, String tmp2, boolean b) {
		if (b)
			return tmp1;
		else
			return tmp2;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductFulldescription() {
		return productFulldescription;
	}

	public void setProductFulldescription(String productFulldescription) {
		this.productFulldescription = productFulldescription;
	}

	public int getSearchquery() {
		return searchquery;
	}

	public void setSearchquery(int searchquery) {
		this.searchquery = searchquery;
	}

	public String getSearchValueArg() {
		return searchValueArg;
	}

	public void setSearchValueArg(String searchValueArg) {
		// FIX (SQL injection in the public search box): this value is spliced
		// into LIKE '%...%' by string concatenation in ProductlistFaced. It is
		// escaped here, at the one point every request path passes through.
		// See Validation.escapeSqlLiteral for why this is interim.
		this.searchValueArg = com.cbsinc.cms.utils.Validation.escapeSqlLiteral(searchValueArg);
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public String getPortlettypeId() {
		return portlettypeId;
	}

	public void setPortlettypeId(String portlettypeId) {
		this.portlettypeId = portlettypeId;
	}

	public int getPagecountCo1() {
		return pagecountCo1;
	}

	public void setPagecountCo1(int pagecountCo1) {
		this.pagecountCo1 = pagecountCo1;
	}

	public int getPagecountCo2() {
		return pagecountCo2;
	}

	public void setPagecountCo2(int pagecountCo2) {
		this.pagecountCo2 = pagecountCo2;
	}

//	public String getCreteria1_id() {
//		return creteria1_id;
//	}
//
//	public void setCreteria1_id(String creteria1_id) {
//		this.creteria1_id = creteria1_id;
//	}
//
//	public String getCreteria10_id() {
//		return creteria10_id;
//	}
//
//	public void setCreteria10_id(String creteria10_id) {
//		this.creteria10_id = creteria10_id;
//	}
//
//	public String getCreteria2_id() {
//		return creteria2_id;
//	}
//
//	public void setCreteria2_id(String creteria2_id) {
//		this.creteria2_id = creteria2_id;
//	}
//
//	public String getCreteria3_id() {
//		return creteria3_id;
//	}
//
//	public void setCreteria3_id(String creteria3_id) {
//		this.creteria3_id = creteria3_id;
//	}
//
//	public String getCreteria4_id() {
//		return creteria4_id;
//	}
//
//	public void setCreteria4_id(String creteria4_id) {
//		this.creteria4_id = creteria4_id;
//	}
//
//	public String getCreteria5_id() {
//		return creteria5_id;
//	}
//
//	public void setCreteria5_id(String creteria5_id) {
//		this.creteria5_id = creteria5_id;
//	}
//
//	public String getCreteria6_id() {
//		return creteria6_id;
//	}
//
//	public void setCreteria6_id(String creteria6_id) {
//		this.creteria6_id = creteria6_id;
//	}
//
//	public String getCreteria7_id() {
//		return creteria7_id;
//	}
//
//	public void setCreteria7_id(String creteria7_id) {
//		this.creteria7_id = creteria7_id;
//	}
//
//	public String getCreteria8_id() {
//		return creteria8_id;
//	}
//
//	public void setCreteria8_id(String creteria8_id) {
//		this.creteria8_id = creteria8_id;
//	}
//
//	public String getCreteria9_id() {
//		return creteria9_id;
//	}
//
//	public void setCreteria9_id(String creteria9_id) {
//		this.creteria9_id = creteria9_id;
//	}
//
//	public String getDayfrom_id() {
//		return dayfrom_id;
//	}
//
//	public void setDayfrom_id(String dayfrom_id) {
//		this.dayfrom_id = dayfrom_id;
//	}
//
//	public String getDayto_id() {
//		return dayto_id;
//	}
//
//	public void setDayto_id(String dayto_id) {
//		this.dayto_id = dayto_id;
//	}
//
//	public String getMountfrom_id() {
//		return mountfrom_id;
//	}
//
//	public void setMountfrom_id(String mountfrom_id) {
//		this.mountfrom_id = mountfrom_id;
//	}
//
//	public String getMountto_id() {
//		return mountto_id;
//	}
//
//	public void setMountto_id(String mountto_id) {
//		this.mountto_id = mountto_id;
//	}
//
//	public String getYearfrom_id() {
//		return yearfrom_id;
//	}
//
//	public void setYearfrom_id(String yearfrom_id) {
//		this.yearfrom_id = yearfrom_id;
//	}
//
//	public String getYearto_id() {
//		return yearto_id;
//	}
//
//	public void setYearto_id(String yearto_id) {
//		this.yearto_id = yearto_id;
//	}

	public String getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(String user1Id) {
		this.user1Id = user1Id;
	}

	public String getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(String user2Id) {
		this.user2Id = user2Id;
	}

	public String getCatalogParentId() {
		return catalogParentId;
	}

	public void setCatalogParentId(String catalogParentId) {
		this.catalogParentId = catalogParentId;
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

	public String getType1Id() {
		return type1Id;
	}

	public void setType1Id(String type1Id) {
		this.type1Id = type1Id;
	}

	public String getType2Id() {
		return type2Id;
	}

	public void setType2Id(String type2Id) {
		this.type2Id = type2Id;
	}

//	public String getFromCost() {
//		return fromCost;
//	}
//
//	public void setFromCost(String fromCost) {
//		this.fromCost = fromCost;
//	}
//
//	public String getToCost() {
//		return toCost;
//	}
//
//	public void setToCost(String toCost) {
//		this.toCost = toCost;
//	}
//
//	public java.util.Calendar getCalendar() {
//		return calendar;
//	}
//
//	public void setCalendar(java.util.Calendar calendar) {
//		this.calendar = calendar;
//	}

	public String getQueryProductlist() {
		return queryProductlist;
	}

	public void setQueryProductlist(String queryProductlist) {
		this.queryProductlist = queryProductlist;
	}

	public String getSelectCurrencyCd() {
		return selectCurrencyCd;
	}

	public void setSelectCurrencyCd(String selectCurrencyCd) {
		this.selectCurrencyCd = selectCurrencyCd;
	}

	public String getSelectPath() {
		return selectPath;
	}

	public void setSelectPath(String selectPath) {
		this.selectPath = selectPath;
	}

	public String getAllFoundProducts() {
		return allFoundProducts;
	}

	public void setAllFoundProducts(String allFoundProducts) {
		this.allFoundProducts = allFoundProducts;
	}

	public int getPagecountBlog() {
		return pagecountBlog;
	}

	public void setPagecountBlog(int pagecountBlog) {
		this.pagecountBlog = pagecountBlog;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public String getAdvancedSearchOpen() {
		return advancedSearchOpen;
	}

	public void setAdvancedSearchOpen(String advancedSearchOpen) {
		this.advancedSearchOpen = advancedSearchOpen;
	}

	public String getForumOpen() {
		return forumOpen;
	}

	public void setForumOpen(String forumOpen) {
		this.forumOpen = forumOpen;
	}
}
