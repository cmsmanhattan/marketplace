package com.cbsinc.cms;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cbsinc.cms.annotations.PageXsltView;
import com.cbsinc.cms.annotations.Type;

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
//@PageXsltView(jspName = "Pay.jsp", xsltName = "pay.xsl", responseType = Type.XML)
public class ProductlistBean implements java.io.Serializable {

	private static final long serialVersionUID = -7111281778620430929L;

	static private Logger log = Logger.getLogger(ProductlistBean.class);

	private String searchValueArg = "";

	public String[][] rows = new String[10][2];

	public String[][] footerLinksListRows = new String[10][2];

	public String[][] co1Rows = new String[10][2];

	public String[][] sponsoredBySellersRows = new String[10][2];

	public String[][] blogRows = new String[20][2];

	public String[][] newArrivalRow = new String[10][2];

	public String[][] offerRow = new String[10][2];

	private transient GetValueTool parser = new GetValueTool();

	public List allqueryAdp = new LinkedList();

	public List productList = new LinkedList();

	public List newArrivalItems = new LinkedList();

	public List offerResultSet = new LinkedList();
	public List actionBisResultSet = new LinkedList();

	public List recommentedItems = new LinkedList();

	public List sponsoredBySellers = new LinkedList();

	public List footerLinksList = new LinkedList();

	private List tmpAdp = new LinkedList();

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String type1Id = "1";

	private String type2Id = "1";

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

	private String catalogParentId = "0";

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

	private Integer pagecountSponsoredBySellers = 0;

	private String queryProductlist = "";

	private String criteria1Label = "";
	private String criteria2Label = "";
	private String criteria3Label = "";
	private String criteria4Label = "";
	private String criteria5Label = "";
	private String criteria6Label = "";
	private String criteria7Label = "";
	private String criteria8Label = "";
	private String criteria9Label = "";
	private String criteria10Label = "";

	private String creteria1Name = "";
	private String creteria2Name = "";
	private String creteria3Name = "";
	private String creteria4Name = "";
	private String creteria5Name = "";
	private String creteria6Name = "";
	private String creteria7Name = "";
	private String creteria8Name = "";
	private String creteria9Name = "";
	private String creteria10Name = "";

	private String selectCurrencyCd = "";
	private String selectTreeCatalog = "";
	private String selectMenuCatalog = "";

	private String selectCreteria1Id = "";
	private String selectCreteria2Id = "";
	private String selectCreteria3Id = "";
	private String selectCreteria4Id = "";
	private String selectCreteria5Id = "";
	private String selectCreteria6Id = "";
	private String selectCreteria7Id = "";
	private String selectCreteria8Id = "";
	private String selectCreteria9Id = "";
	private String selectCreteria10Id = "";

	private String selectDayfromId = "";
	private String selectMountfromId = "";
	private String selectYearfromId = "";
	private String selectDaytoId = "";
	private String selectMounttoId = "";
	private String selectYeartoId = "";

	private String selectPath = "";

	private String color = "";

	private String allFoundProducts = "";

	String dialog = "true";
	String advancedSearchOpen = "true";
	String forumOpen = "true";

	public Boolean isInternet = true;

	public List topItemReview = new LinkedList();

	public ProductlistBean() {

	}

	public String getProductsIdlist(int count) {
		String productsId = "";
		try {
			rows = new String[parser.getRowCount(productList)][2];
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
		return "" + parser.getRowCount(allqueryAdp);
	}

	public String getProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		rows = new String[parser.getRowCount(productList)][2];

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
			pagecount = parser.getRowCount(productList);

			for (int i = 0; parser.getRowCount(productList) > i; i = i + 2) {
				rows[i][0] = (String) parser.getValueAt(productList, i, 0);
				rows[i][1] = parser.getValueAt(productList, i, 7) == null ? "" : parser.getValueAt(productList, i, 7);

				productName = (String) parser.getValueAt(productList, i, 1);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(productList, i, 0);

				imgUrl = (String) parser.getValueAt(productList, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) parser.getValueAt(productList, i, 2);
				productVersion = (String) parser.getValueAt(productList, i, 3);
				productCost = (String) parser.getValueAt(productList, i, 4);
				currencyId = (String) parser.getValueAt(productList, i, 5);

				CurrencyHash currencyHash = CurrencyHash.getInstance();

				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(productList, i, 12);
				productFulldescription = (String) parser.getValueAt(productList, i, 14);
				user1Id = (String) parser.getValueAt(productList, i, 16);

				type1Id = (String) parser.getValueAt(productList, i, 8);

				creteria1Name = (String) parser.getValueAt(productList, i, 30);
				creteria2Name = (String) parser.getValueAt(productList, i, 31);
				creteria3Name = (String) parser.getValueAt(productList, i, 32);
				creteria4Name = (String) parser.getValueAt(productList, i, 33);
				creteria5Name = (String) parser.getValueAt(productList, i, 34);
				creteria6Name = (String) parser.getValueAt(productList, i, 35);
				creteria7Name = (String) parser.getValueAt(productList, i, 36);
				creteria8Name = (String) parser.getValueAt(productList, i, 37);
				creteria9Name = (String) parser.getValueAt(productList, i, 38);
				creteria10Name = (String) parser.getValueAt(productList, i, 39);
				color = (String) parser.getValueAt(productList, i, 40);

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
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("<creteria1>" + creteria1Name + "</creteria1>\n");
				table.append("<creteria2>" + creteria2Name + "</creteria2>\n");
				table.append("<creteria3>" + creteria3Name + "</creteria3>\n");
				table.append("<creteria4>" + creteria4Name + "</creteria4>\n");
				table.append("<creteria5>" + creteria5Name + "</creteria5>\n");
				table.append("<creteria6>" + creteria6Name + "</creteria6>\n");
				table.append("<creteria7>" + creteria7Name + "</creteria7>\n");
				table.append("<creteria8>" + creteria8Name + "</creteria8>\n");
				table.append("<creteria9>" + creteria9Name + "</creteria9>\n");
				table.append("<creteria10>" + creteria10Name + "</creteria10>\n");
				table.append("<color>" + color + "</color>\n");
				table.append("</rigth>\n");

				if (parser.getRowCount(productList) > (i + 1)) {
					productUrl2 = "ProductInfo.jsp?policy_byproductid="
							+ (String) parser.getValueAt(productList, i + 1, 0);

					rows[i + 1][0] = (String) parser.getValueAt(productList, i + 1, 0);
					rows[i + 1][1] = parser.getValueAt(productList, i + 1, 7) == null ? ""
							: parser.getValueAt(productList, i + 1, 7);
					productName2 = (String) parser.getValueAt(productList, i + 1, 1);
					imgUrl2 = (String) parser.getValueAt(productList, i + 1, 13);
					if (imgUrl2 != null)
						productIconurl2 = imgUrl2;
					else
						productIconurl2 = "images/Folder.jpg";
					productDescription2 = (String) parser.getValueAt(productList, i + 1, 2);
					productCost2 = (String) parser.getValueAt(productList, i + 1, 4);
					currencyId2 = (String) parser.getValueAt(productList, i + 1, 5);
					currencyDesc2 = currencyHash.getCurrencyDecs(currencyId2);
					user2Id = (String) parser.getValueAt(productList, i + 1, 16);
					type2Id = (String) parser.getValueAt(productList, i + 1, 8);
					creteria1Name = (String) parser.getValueAt(productList, i + 1, 30);
					creteria2Name = (String) parser.getValueAt(productList, i + 1, 31);
					creteria3Name = (String) parser.getValueAt(productList, i + 1, 32);
					creteria4Name = (String) parser.getValueAt(productList, i + 1, 33);
					creteria5Name = (String) parser.getValueAt(productList, i + 1, 34);
					creteria6Name = (String) parser.getValueAt(productList, i + 1, 35);
					creteria7Name = (String) parser.getValueAt(productList, i + 1, 36);
					creteria8Name = (String) parser.getValueAt(productList, i + 1, 37);
					creteria9Name = (String) parser.getValueAt(productList, i + 1, 38);
					creteria10Name = (String) parser.getValueAt(productList, i + 1, 39);
					color = (String) parser.getValueAt(productList, i + 1, 40);

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
					table.append("<amount>" + productCost2 + "</amount>\n");
					table.append("<currency>\n");
					table.append("<code>" + currencyCd + "</code>\n");
					table.append("<description>dollar us</description>\n");
					table.append("</currency>\n");
					table.append("<version>" + currencyDesc2 + "</version>\n");
					table.append("<creteria1>" + creteria1Name + "</creteria1>\n");
					table.append("<creteria2>" + creteria2Name + "</creteria2>\n");
					table.append("<creteria3>" + creteria3Name + "</creteria3>\n");
					table.append("<creteria4>" + creteria4Name + "</creteria4>\n");
					table.append("<creteria5>" + creteria5Name + "</creteria5>\n");
					table.append("<creteria6>" + creteria6Name + "</creteria6>\n");
					table.append("<creteria7>" + creteria7Name + "</creteria7>\n");
					table.append("<creteria8>" + creteria8Name + "</creteria8>\n");
					table.append("<creteria9>" + creteria9Name + "</creteria9>\n");
					table.append("<creteria10>" + creteria10Name + "</creteria10>\n");
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

	public String getProductSimpleList(long strUserId, String siteId) {

		rows = new String[parser.getRowCount(productList)][2];

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
			pagecount = parser.getRowCount(productList);

			for (int i = 0; parser.getRowCount(productList) > i; i++) {
				rows[i][0] = (String) parser.getValueAt(productList, i, 0);
				rows[i][1] = parser.getValueAt(productList, i, 7) == null ? "" : parser.getValueAt(productList, i, 7);

				productName = (String) parser.getValueAt(productList, i, 1);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(productList, i, 0);

				imgUrl = (String) parser.getValueAt(productList, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) parser.getValueAt(productList, i, 2);
				productVersion = (String) parser.getValueAt(productList, i, 3);
				productCost = (String) parser.getValueAt(productList, i, 4);
				currencyId = (String) parser.getValueAt(productList, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(productList, i, 12);
				productFulldescription = (String) parser.getValueAt(productList, i, 14);
				user1Id = (String) parser.getValueAt(productList, i, 16);
				creteria1Name = (String) parser.getValueAt(productList, i, 30);
				creteria2Name = (String) parser.getValueAt(productList, i, 31);
				creteria3Name = (String) parser.getValueAt(productList, i, 32);
				creteria4Name = (String) parser.getValueAt(productList, i, 33);
				creteria5Name = (String) parser.getValueAt(productList, i, 34);
				creteria6Name = (String) parser.getValueAt(productList, i, 35);
				creteria7Name = (String) parser.getValueAt(productList, i, 36);
				creteria8Name = (String) parser.getValueAt(productList, i, 37);
				creteria9Name = (String) parser.getValueAt(productList, i, 38);
				creteria10Name = (String) parser.getValueAt(productList, i, 39);
				color = (String) parser.getValueAt(productList, i, 40);

				type1Id = (String) parser.getValueAt(productList, i, 8);

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
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("<creteria1>" + creteria1Name + "</creteria1>\n");
				table.append("<creteria2>" + creteria2Name + "</creteria2>\n");
				table.append("<creteria3>" + creteria3Name + "</creteria3>\n");
				table.append("<creteria4>" + creteria4Name + "</creteria4>\n");
				table.append("<creteria5>" + creteria5Name + "</creteria5>\n");
				table.append("<creteria6>" + creteria6Name + "</creteria6>\n");
				table.append("<creteria7>" + creteria7Name + "</creteria7>\n");
				table.append("<creteria8>" + creteria8Name + "</creteria8>\n");
				table.append("<creteria9>" + creteria9Name + "</creteria9>\n");
				table.append("<creteria10>" + creteria10Name + "</creteria10>\n");
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
			pagecount = parser.getRowCount(tmpAdp);

			for (int i = 0; parser.getRowCount(tmpAdp) > i; i++) {
				rows[i][0] = (String) parser.getValueAt(tmpAdp, i, 0);
				rows[i][1] = parser.getValueAt(tmpAdp, i, 7) == null ? "" : parser.getValueAt(tmpAdp, i, 7);
				productName = (String) parser.getValueAt(tmpAdp, i, 1);
				productUrl = "ProductInfo.jsp?row=" + i;

				imgUrl = (String) parser.getValueAt(tmpAdp, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";
				productDescription = (String) parser.getValueAt(tmpAdp, i, 2);
				productVersion = (String) parser.getValueAt(tmpAdp, i, 3);
				productCost = (String) parser.getValueAt(tmpAdp, i, 4);
				currencyId = (String) parser.getValueAt(tmpAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(tmpAdp, i, 12);
				productFulldescription = (String) parser.getValueAt(tmpAdp, i, 14);
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();

	}

	@Deprecated
	public String getCoOneProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		co1Rows = new String[parser.getRowCount(recommentedItems)][2];

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
			pagecountCo1 = parser.getRowCount(recommentedItems);

			for (int i = 0; parser.getRowCount(recommentedItems) > i; i++) {
				co1Rows[i][0] = (String) parser.getValueAt(recommentedItems, i, 0);
				co1Rows[i][1] = parser.getValueAt(recommentedItems, i, 7) == null ? ""
						: parser.getValueAt(recommentedItems, i, 7);
				productName = (String) parser.getValueAt(recommentedItems, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(recommentedItems, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(recommentedItems, i, 0);

				productIconurl = (String) parser.getValueAt(recommentedItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(recommentedItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (co1Rows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(recommentedItems, i, 2);
				productVersion = (String) parser.getValueAt(recommentedItems, i, 3);
				productCost = (String) parser.getValueAt(recommentedItems, i, 4);
				currencyId = (String) parser.getValueAt(recommentedItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(recommentedItems, i, 12);
				productFulldescription = (String) parser.getValueAt(recommentedItems, i, 14);
				user1Id = (String) parser.getValueAt(recommentedItems, i, 16);

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
				table.append("<product_url>" + attacheFile + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
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

	public String getRecommentedItems(long userId, String siteId) {

		co1Rows = new String[parser.getRowCount(recommentedItems)][2];

		StringBuffer table = new StringBuffer();
		table.append("<recommentedItems>\n");
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
			pagecountCo1 = parser.getRowCount(recommentedItems);

			for (int i = 0; parser.getRowCount(recommentedItems) > i; i++) {
				co1Rows[i][0] = (String) parser.getValueAt(recommentedItems, i, 0);
				co1Rows[i][1] = parser.getValueAt(recommentedItems, i, 7) == null ? ""
						: parser.getValueAt(recommentedItems, i, 7);
				productName = (String) parser.getValueAt(recommentedItems, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(recommentedItems, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(recommentedItems, i, 0);

				productIconurl = (String) parser.getValueAt(recommentedItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(recommentedItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (co1Rows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(recommentedItems, i, 2);
				productVersion = (String) parser.getValueAt(recommentedItems, i, 3);
				productCost = (String) parser.getValueAt(recommentedItems, i, 4);
				currencyId = (String) parser.getValueAt(recommentedItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(recommentedItems, i, 12);
				productFulldescription = (String) parser.getValueAt(recommentedItems, i, 14);
				user1Id = (String) parser.getValueAt(recommentedItems, i, 16);

				table.append("<recommented>\n");
				table.append("<productId>" + co1Rows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<fileExist>" + fileExist1 + "</fileExist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageYype>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageYype>\n");
				table.append("<iconYype>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconYype>\n");
				table.append("<userId>" + user1Id + "</userId>\n");
				// Referece to pruduct
				table.append("<productInfoUrl>" + productUrl + "</productInfoUrl>\n");
				table.append("<attacheFile>" + attacheFile + "</attacheFile>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</recommented>\n");
			}
			table.append("</recommentedItems>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 * Use method getFooterLinksList()
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	@Deprecated
	public String getBottomList(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		footerLinksListRows = new String[parser.getRowCount(footerLinksList)][2];
		StringBuffer table = new StringBuffer();
		table.append("<bottomlist>\n");

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");

			for (int i = 0; parser.getRowCount(footerLinksList) > i; i++) {
				footerLinksListRows[i][0] = (String) parser.getValueAt(footerLinksList, i, 0);
				footerLinksListRows[i][1] = parser.getValueAt(footerLinksList, i, 7) == null ? ""
						: parser.getValueAt(footerLinksList, i, 7);
				productName = (String) parser.getValueAt(footerLinksList, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(footerLinksList, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(footerLinksList, i, 0);

				productIconurl = (String) parser.getValueAt(footerLinksList, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(footerLinksList, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (footerLinksListRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(footerLinksList, i, 2);
				productVersion = (String) parser.getValueAt(footerLinksList, i, 3);
				productCost = (String) parser.getValueAt(footerLinksList, i, 4);
				currencyId = (String) parser.getValueAt(footerLinksList, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(footerLinksList, i, 12);
				productFulldescription = (String) parser.getValueAt(footerLinksList, i, 14);
				user1Id = (String) parser.getValueAt(footerLinksList, i, 16);

				table.append("<bottom>\n");
				table.append("<product_id>" + footerLinksListRows[i][0] + "</product_id>\n");
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
				table.append("<product_url>" + attacheFile + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</bottom>\n");
			}
			table.append("</bottomlist>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 * It displays footer list link on page button .
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	public String getFooterLinksList(long userId, String siteId) {

		footerLinksListRows = new String[parser.getRowCount(footerLinksList)][2];
		StringBuffer table = new StringBuffer();
		table.append("<footerLinksList>\n");

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");

			for (int i = 0; parser.getRowCount(footerLinksList) > i; i++) {
				footerLinksListRows[i][0] = (String) parser.getValueAt(footerLinksList, i, 0);
				footerLinksListRows[i][1] = parser.getValueAt(footerLinksList, i, 7) == null ? ""
						: parser.getValueAt(footerLinksList, i, 7);
				productName = (String) parser.getValueAt(footerLinksList, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(footerLinksList, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(footerLinksList, i, 0);

				productIconurl = (String) parser.getValueAt(footerLinksList, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(footerLinksList, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (footerLinksListRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(footerLinksList, i, 2);
				productVersion = (String) parser.getValueAt(footerLinksList, i, 3);
				productCost = (String) parser.getValueAt(footerLinksList, i, 4);
				currencyId = (String) parser.getValueAt(footerLinksList, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(footerLinksList, i, 12);
				productFulldescription = (String) parser.getValueAt(footerLinksList, i, 14);
				user1Id = (String) parser.getValueAt(footerLinksList, i, 16);

				table.append("<footerLink>\n");
				table.append("<productId>" + footerLinksListRows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<fileExist>" + fileExist1 + "</fileExist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageType>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageType>\n");
				table.append(
						"<iconType>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconType>\n");
				table.append("<userId>" + user1Id + "</userId>\n");
				// Referece to pruduct
				table.append("<productInfoUrl>" + productUrl + "</productInfoUrl>\n");
				table.append("<attacheFile>" + attacheFile + "</attacheFile>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</footerLink>\n");
			}
			table.append("</footerLinksList>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 * Use sponsored tiems method getSponsoredBySellersItems
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	@Deprecated
	public String getCoTwoProductlist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		sponsoredBySellersRows = new String[parser.getRowCount(sponsoredBySellers)][2];

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
			pagecountSponsoredBySellers = parser.getRowCount(sponsoredBySellers);

			for (int i = 0; parser.getRowCount(sponsoredBySellers) > i; i++) {
				sponsoredBySellersRows[i][0] = (String) parser.getValueAt(sponsoredBySellers, i, 0);
				sponsoredBySellersRows[i][1] = parser.getValueAt(sponsoredBySellers, i, 7) == null ? ""
						: parser.getValueAt(sponsoredBySellers, i, 7);
				productName = (String) parser.getValueAt(sponsoredBySellers, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(sponsoredBySellers, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) parser.getValueAt(sponsoredBySellers, i, 0);

				productIconurl = (String) parser.getValueAt(sponsoredBySellers, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(sponsoredBySellers, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (sponsoredBySellersRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(sponsoredBySellers, i, 2);
				productVersion = (String) parser.getValueAt(sponsoredBySellers, i, 3);
				productCost = (String) parser.getValueAt(sponsoredBySellers, i, 4);
				currencyId = (String) parser.getValueAt(sponsoredBySellers, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(sponsoredBySellers, i, 12);
				productFulldescription = (String) parser.getValueAt(sponsoredBySellers, i, 14);
				user1Id = (String) parser.getValueAt(sponsoredBySellers, i, 16);
				table.append("<coproduct2>\n");
				table.append("<product_id>" + sponsoredBySellersRows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append(
						"<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
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

	/**
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	public String getSponsoredBySellersItems(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		sponsoredBySellersRows = new String[parser.getRowCount(sponsoredBySellers)][2];

		StringBuffer table = new StringBuffer();
		table.append("<sponsoredBySellersItems>\n");
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
			pagecountSponsoredBySellers = parser.getRowCount(sponsoredBySellers);

			for (int i = 0; parser.getRowCount(sponsoredBySellers) > i; i++) {
				sponsoredBySellersRows[i][0] = (String) parser.getValueAt(sponsoredBySellers, i, 0);
				sponsoredBySellersRows[i][1] = parser.getValueAt(sponsoredBySellers, i, 7) == null ? ""
						: parser.getValueAt(sponsoredBySellers, i, 7);
				productName = (String) parser.getValueAt(sponsoredBySellers, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) parser.getValueAt(sponsoredBySellers, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) parser.getValueAt(sponsoredBySellers, i, 0);

				productIconurl = (String) parser.getValueAt(sponsoredBySellers, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(sponsoredBySellers, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (sponsoredBySellersRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(sponsoredBySellers, i, 2);
				productVersion = (String) parser.getValueAt(sponsoredBySellers, i, 3);
				productCost = (String) parser.getValueAt(sponsoredBySellers, i, 4);
				currencyId = (String) parser.getValueAt(sponsoredBySellers, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(sponsoredBySellers, i, 12);
				productFulldescription = (String) parser.getValueAt(sponsoredBySellers, i, 14);
				user1Id = (String) parser.getValueAt(sponsoredBySellers, i, 16);
				table.append("<sponsored>\n");
				table.append("<productId>" + sponsoredBySellersRows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageType>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageType>\n");
				table.append(
						"<iconType>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconType>\n");
				table.append("<userId>" + user1Id + "</userId>\n");
				table.append("<productInfoUrl>" + productUrl + "</productInfoUrl>\n");
				table.append("<attacheFile>" + attacheFile + "</attacheFile>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</sponsored>\n");
			}
			table.append("</sponsoredBySellersItems>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id
	 * @return
	 */

	@Deprecated
	public String getBlogTopProductlist(String siteId) {

		blogRows = new String[parser.getRowCount(topItemReview)][2];
		StringBuilder table = new StringBuilder();
		table.append("<product_blog_list>\n");
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

			pagecountBlog = parser.getRowCount(topItemReview);
			for (int i = 0; parser.getRowCount(topItemReview) > i; i++) {
				blogRows[i][0] = (String) parser.getValueAt(topItemReview, i, 0);
				blogRows[i][1] = parser.getValueAt(topItemReview, i, 7) == null ? ""
						: parser.getValueAt(topItemReview, i, 7);
				productName = (String) parser.getValueAt(topItemReview, i, 1);
				String parent = (String) parser.getValueAt(topItemReview, i, 22);
				String parentTitle = (String) parser.getValueAt(topItemReview, i, 23);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + parent;

				imgUrl = (String) parser.getValueAt(topItemReview, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";

				String fileExist1 = "";
				if (blogRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(topItemReview, i, 2);
				productVersion = (String) parser.getValueAt(topItemReview, i, 3);
				productCost = (String) parser.getValueAt(topItemReview, i, 4);
				currencyId = (String) parser.getValueAt(topItemReview, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(topItemReview, i, 12);
				productFulldescription = (String) parser.getValueAt(topItemReview, i, 14);
				user1Id = (String) parser.getValueAt(topItemReview, i, 16);

				String strCDate = (String) parser.getValueAt(topItemReview, i, 17);
				String statistic = (String) parser.getValueAt(topItemReview, i, 18);
				String firstName = (String) parser.getValueAt(topItemReview, i, 19);
				String lastName = (String) parser.getValueAt(topItemReview, i, 20);
				String company = (String) parser.getValueAt(topItemReview, i, 21);

				table.append("<product_blog>\n");
				table.append("<product_id>" + blogRows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<parent_title>" + parentTitle + "</parent_title>\n");
				table.append("<product_parent_id>" + parent + "</product_parent_id>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<author>" + firstName + " " + lastName + "</author>\n");
				table.append("<company>" + company + "</company>\n");
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("<statistic>" + statistic + "</statistic>\n");
				table.append("<cdate>" + strCDate + "</cdate>\n");
				table.append("</product_blog>\n");
			}
			table.append("</product_blog_list>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	/**
	 * Collect top list of item review
	 *
	 * @param site_id
	 * @return
	 */
	public String getItemReviewTopList(String siteId) {

		blogRows = new String[parser.getRowCount(topItemReview)][2];
		StringBuilder table = new StringBuilder();
		table.append("<itemReviewTopList>\n");
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

			pagecountBlog = parser.getRowCount(topItemReview);
			for (int i = 0; parser.getRowCount(topItemReview) > i; i++) {
				blogRows[i][0] = (String) parser.getValueAt(topItemReview, i, 0);
				blogRows[i][1] = parser.getValueAt(topItemReview, i, 7) == null ? ""
						: parser.getValueAt(topItemReview, i, 7);
				productName = (String) parser.getValueAt(topItemReview, i, 1);
				String parent = (String) parser.getValueAt(topItemReview, i, 22);
				String parentTitle = (String) parser.getValueAt(topItemReview, i, 23);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + parent;

				imgUrl = (String) parser.getValueAt(topItemReview, i, 13);
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";

				String fileExist1 = "";
				if (blogRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(topItemReview, i, 2);
				productVersion = (String) parser.getValueAt(topItemReview, i, 3);
				productCost = (String) parser.getValueAt(topItemReview, i, 4);
				currencyId = (String) parser.getValueAt(topItemReview, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(topItemReview, i, 12);
				productFulldescription = (String) parser.getValueAt(topItemReview, i, 14);
				user1Id = (String) parser.getValueAt(topItemReview, i, 16);

				String strCDate = (String) parser.getValueAt(topItemReview, i, 17);
				String statistic = (String) parser.getValueAt(topItemReview, i, 18);
				String firstName = (String) parser.getValueAt(topItemReview, i, 19);
				String lastName = (String) parser.getValueAt(topItemReview, i, 20);
				String company = (String) parser.getValueAt(topItemReview, i, 21);

				table.append("<itemReview>\n");
				table.append("<productId>" + blogRows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<fileExist>" + fileExist1 + "</fileExist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<parentTitle>" + parentTitle + "</parentTitle>\n");
				table.append("<productReferenceId>" + parent + "</productReferenceId>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<userId>" + user1Id + "</userId>\n");
				table.append("<author>" + firstName + " " + lastName + "</author>\n");
				table.append("<company>" + company + "</company>\n");
				table.append("<productInfoUrl>" + productUrl + "</productInfoUrl>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("<statistic>" + statistic + "</statistic>\n");
				table.append("<cdate>" + strCDate + "</cdate>\n");
				table.append("</itemReview>\n");
			}
			table.append("</itemReviewTopList>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	/**
	 * Use new method getNewArrivalItems();
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	@Deprecated
	public String getNewslist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		// this.site_id = site_id;
		newArrivalRow = new String[parser.getRowCount(newArrivalItems)][2];
		StringBuffer table = new StringBuffer();
		table.append("<newslist>\n");

		try {
			for (int i = 0; parser.getRowCount(newArrivalItems) > i; i++) {
				newArrivalRow[i][0] = (String) parser.getValueAt(newArrivalItems, i, 0);
				newArrivalRow[i][1] = parser.getValueAt(newArrivalItems, i, 7) == null ? ""
						: parser.getValueAt(newArrivalItems, i, 7);

				productName = (String) parser.getValueAt(newArrivalItems, i, 1);

				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(newArrivalItems, i, 0);

				productIconurl = (String) parser.getValueAt(newArrivalItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(newArrivalItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newArrivalRow[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(newArrivalItems, i, 2);
				productVersion = (String) parser.getValueAt(newArrivalItems, i, 3);
				productCost = (String) parser.getValueAt(newArrivalItems, i, 4);
				currencyId = (String) parser.getValueAt(newArrivalItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(newArrivalItems, i, 12);
				productFulldescription = (String) parser.getValueAt(newArrivalItems, i, 14);
				user1Id = (String) parser.getValueAt(newArrivalItems, i, 16);

				table.append("<news>\n");
				table.append("<product_id>" + newArrivalRow[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append(
						"<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<policy_url>" + productUrl + "</policy_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</news>\n");
			}
			table.append("</newslist>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}


	/**
	 * Use new method getNewArrivalItems();
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */

	public String getOffers(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		// this.site_id = site_id;
		String offerStatus = "" ;
		String offerStatusId = "-1" ;

		offerRow = new String[parser.getRowCount(offerResultSet)][2];
		StringBuffer table = new StringBuffer();
		table.append("<offers>\n");

		try {
			for (int i = 0; parser.getRowCount(offerResultSet) > i; i++) {
				offerRow[i][0] = (String) parser.getValueAt(offerResultSet, i, 0);
				offerRow[i][1] = parser.getValueAt(offerResultSet, i, 7) == null ? ""
						: parser.getValueAt(offerResultSet, i, 7);

				productName = (String) parser.getValueAt(offerResultSet, i, 1);

				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(offerResultSet, i, 0);

				productIconurl = (String) parser.getValueAt(offerResultSet, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(offerResultSet, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (offerRow[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(offerResultSet, i, 2);
				productVersion = (String) parser.getValueAt(offerResultSet, i, 3);
				productCost = (String) parser.getValueAt(offerResultSet, i, 4);
				currencyId = (String) parser.getValueAt(offerResultSet, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(offerResultSet, i, 12);
				productFulldescription = (String) parser.getValueAt(offerResultSet, i, 14);
				user1Id = (String) parser.getValueAt(offerResultSet, i, 16);
				offerStatus = (String) parser.getValueAt(offerResultSet, i, 18);
				offerStatusId = (String) parser.getValueAt(offerResultSet, i, 19);
				String counterOfferAmount = parser.getValueAt(offerResultSet, i, 20) == null ? "" : (String) parser.getValueAt(offerResultSet, i, 20);

				table.append("<offer>\n");
				table.append("<product_id>" + offerRow[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<image>" + productIconurl + "</image>\n");
				table.append("<big_image>" + imgUrl + "</big_image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append("<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<product_url>" + productUrl + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<offer_status>" + offerStatus + "</offer_status>\n");
				table.append("<offer_status_id>" + offerStatusId + "</offer_status_id>\n");
				table.append("<counter_offer_amount>" + counterOfferAmount + "</counter_offer_amount>\n");
				table.append("<currency_desc>" + currencyDesc + "</currency_desc>\n");
				table.append("</offer>\n");
			}
			table.append("</offers>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}





	/**
	 * Use new method getNewArrivalItems();
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */

	public String getAuctionBids(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		// this.site_id = site_id;
		String actionbidStatus = "" ;
		String actionbidStatusId = "-1" ;
		offerRow = new String[parser.getRowCount(actionBisResultSet)][2];
		StringBuffer table = new StringBuffer();
		table.append("<actionBids>\n");

		try {
			for (int i = 0; parser.getRowCount(actionBisResultSet) > i; i++) {
				offerRow[i][0] = (String) parser.getValueAt(actionBisResultSet, i, 0);
				offerRow[i][1] = parser.getValueAt(actionBisResultSet, i, 7) == null ? ""
						: parser.getValueAt(actionBisResultSet, i, 7);

				productName = (String) parser.getValueAt(actionBisResultSet, i, 1);

				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(actionBisResultSet, i, 0);

				productIconurl = (String) parser.getValueAt(actionBisResultSet, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(actionBisResultSet, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (offerRow[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(actionBisResultSet, i, 2);
				productVersion = (String) parser.getValueAt(actionBisResultSet, i, 3);
				productCost = (String) parser.getValueAt(actionBisResultSet, i, 4);
				currencyId = (String) parser.getValueAt(actionBisResultSet, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(actionBisResultSet, i, 12);
				productFulldescription = (String) parser.getValueAt(actionBisResultSet, i, 14);
				user1Id = (String) parser.getValueAt(actionBisResultSet, i, 16);
				actionbidStatus = (String) parser.getValueAt(actionBisResultSet, i, 18);
				actionbidStatusId = (String) parser.getValueAt(actionBisResultSet, i, 19);

				table.append("<actionBid>\n");
				table.append("<product_id>" + offerRow[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<image>" + productIconurl + "</image>\n");
				table.append("<big_image>" + imgUrl + "</big_image>\n");
				table.append("<big_image_type>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</big_image_type>\n");
				table.append("<icon_type>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</icon_type>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<product_url>" + productUrl + "</product_url>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<actionbid_status>" + actionbidStatus + "</actionbid_status>\n");
				table.append("<actionbid_status_id>" + actionbidStatusId + "</actionbid_status_id>\n");
				table.append("<currency_desc>" + currencyDesc + "</currency_desc>\n");
				table.append("</actionBid>\n");
			}
			table.append("</actionBids>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 * List new Arrival Items
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	public String getNewArrivalItems(long userId, String siteId) {

		// this.site_id = site_id;
		newArrivalRow = new String[parser.getRowCount(newArrivalItems)][2];
		StringBuffer table = new StringBuffer();
		table.append("<newArrivalItems>\n");

		try {
			for (int i = 0; parser.getRowCount(newArrivalItems) > i; i++) {
				newArrivalRow[i][0] = (String) parser.getValueAt(newArrivalItems, i, 0);
				newArrivalRow[i][1] = parser.getValueAt(newArrivalItems, i, 7) == null ? ""
						: parser.getValueAt(newArrivalItems, i, 7);

				productName = (String) parser.getValueAt(newArrivalItems, i, 1);

				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) parser.getValueAt(newArrivalItems, i, 0);

				productIconurl = (String) parser.getValueAt(newArrivalItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) parser.getValueAt(newArrivalItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newArrivalRow[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) parser.getValueAt(newArrivalItems, i, 2);
				productVersion = (String) parser.getValueAt(newArrivalItems, i, 3);
				productCost = (String) parser.getValueAt(newArrivalItems, i, 4);
				currencyId = (String) parser.getValueAt(newArrivalItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) parser.getValueAt(newArrivalItems, i, 12);
				productFulldescription = (String) parser.getValueAt(newArrivalItems, i, 14);
				user1Id = (String) parser.getValueAt(newArrivalItems, i, 16);

				table.append("<newArrival>\n");
				table.append("<productId>" + newArrivalRow[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<fileExist>" + fileExist1 + "</fileExist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageType>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageType>\n");
				table.append(
						"<iconType>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconType>\n");
				table.append("<userId>" + user1Id + "</userId>\n");
				table.append("<productInfoUrl>" + productUrl + "</productInfoUrl>\n");
				table.append("<description>" + productDescription + "</description>\n");
				table.append("<amount>" + productCost + "</amount>\n");
				table.append("<currency>\n");
				table.append("<code>" + currencyCd + "</code>\n");
				table.append("<description>dollar us</description>\n");
				table.append("</currency>\n");
				table.append("<version>" + currencyDesc + "</version>\n");
				table.append("</newArrival>\n");
			}
			table.append("</newArrivalItems>\n");
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

	// FIX: this was declared as "onggetIntLevelUp()" - a typo from someone
	// typing over "public long getIntLevelUp()". The class therefore had a
	// setter for this property but no reachable getter. Nothing called it, so
	// it compiled and stayed unnoticed.
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
		return pagecountSponsoredBySellers;
	}

	public void setPagecountCo2(int pagecountCo2) {
		this.pagecountSponsoredBySellers = pagecountCo2;
	}

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

	public String getQueryProductlist() {
		return queryProductlist;
	}

	public void setQueryProductlist(String queryProductlist) {
		this.queryProductlist = queryProductlist;
	}

	public String getCriteria1Label() {
		return criteria1Label;
	}

	public void setCriteria1Label(String criteria1Label) {
		this.criteria1Label = criteria1Label;
	}

	public String getCriteria10Label() {
		return criteria10Label;
	}

	public void setCriteria10Label(String criteria10Label) {
		this.criteria10Label = criteria10Label;
	}

	public String getCriteria2Label() {
		return criteria2Label;
	}

	public void setCriteria2Label(String criteria2Label) {
		this.criteria2Label = criteria2Label;
	}

	public String getCriteria3Label() {
		return criteria3Label;
	}

	public void setCriteria3Label(String criteria3Label) {
		this.criteria3Label = criteria3Label;
	}

	public String getCriteria4Label() {
		return criteria4Label;
	}

	public void setCriteria4Label(String criteria4Label) {
		this.criteria4Label = criteria4Label;
	}

	public String getCriteria5Label() {
		return criteria5Label;
	}

	public void setCriteria5Label(String criteria5Label) {
		this.criteria5Label = criteria5Label;
	}

	public String getCriteria6Label() {
		return criteria6Label;
	}

	public void setCriteria6Label(String criteria6Label) {
		this.criteria6Label = criteria6Label;
	}

	public String getCriteria7Label() {
		return criteria7Label;
	}

	public void setCriteria7Label(String criteria7Label) {
		this.criteria7Label = criteria7Label;
	}

	public String getCriteria8Label() {
		return criteria8Label;
	}

	public void setCriteria8Label(String criteria8Label) {
		this.criteria8Label = criteria8Label;
	}

	public String getCriteria9Label() {
		return criteria9Label;
	}

	public void setCriteria9Label(String criteria9Label) {
		this.criteria9Label = criteria9Label;
	}

	public String getSelectCreteria1Id() {
		return selectCreteria1Id;
	}

	public void setSelectCreteria1Id(String selectCreteria1Id) {
		this.selectCreteria1Id = selectCreteria1Id;
	}

	public String getSelectCreteria10Id() {
		return selectCreteria10Id;
	}

	public void setSelectCreteria10Id(String selectCreteria10Id) {
		this.selectCreteria10Id = selectCreteria10Id;
	}

	public String getSelectCreteria2Id() {
		return selectCreteria2Id;
	}

	public void setSelectCreteria2Id(String selectCreteria2Id) {
		this.selectCreteria2Id = selectCreteria2Id;
	}

	public String getSelectCreteria3Id() {
		return selectCreteria3Id;
	}

	public void setSelectCreteria3Id(String selectCreteria3Id) {
		this.selectCreteria3Id = selectCreteria3Id;
	}

	public String getSelectCreteria4Id() {
		return selectCreteria4Id;
	}

	public void setSelectCreteria4Id(String selectCreteria4Id) {
		this.selectCreteria4Id = selectCreteria4Id;
	}

	public String getSelectCreteria5Id() {
		return selectCreteria5Id;
	}

	public void setSelectCreteria5Id(String selectCreteria5Id) {
		this.selectCreteria5Id = selectCreteria5Id;
	}

	public String getSelectCreteria6Id() {
		return selectCreteria6Id;
	}

	public void setSelectCreteria6Id(String selectCreteria6Id) {
		this.selectCreteria6Id = selectCreteria6Id;
	}

	public String getSelectCreteria7Id() {
		return selectCreteria7Id;
	}

	public void setSelectCreteria7Id(String selectCreteria7Id) {
		this.selectCreteria7Id = selectCreteria7Id;
	}

	public String getSelectCreteria8Id() {
		return selectCreteria8Id;
	}

	public void setSelectCreteria8Id(String selectCreteria8Id) {
		this.selectCreteria8Id = selectCreteria8Id;
	}

	public String getSelectCreteria9Id() {
		return selectCreteria9Id;
	}

	public void setSelectCreteria9Id(String selectCreteria9Id) {
		this.selectCreteria9Id = selectCreteria9Id;
	}

	public String getSelectCurrencyCd() {
		return selectCurrencyCd;
	}

	public void setSelectCurrencyCd(String selectCurrencyCd) {
		this.selectCurrencyCd = selectCurrencyCd;
	}

	public String getSelectDayfromId() {
		return selectDayfromId;
	}

	public void setSelectDayfromId(String selectDayfromId) {
		this.selectDayfromId = selectDayfromId;
	}

	public String getSelectDaytoId() {
		return selectDaytoId;
	}

	public void setSelectDaytoId(String selectDaytoId) {
		this.selectDaytoId = selectDaytoId;
	}

	public String getSelectMountfromId() {
		return selectMountfromId;
	}

	public void setSelectMountfromId(String selectMountfromId) {
		this.selectMountfromId = selectMountfromId;
	}

	public String getSelectMounttoId() {
		return selectMounttoId;
	}

	public void setSelectMounttoId(String selectMounttoId) {
		this.selectMounttoId = selectMounttoId;
	}

	public String getSelectPath() {
		return selectPath;
	}

	public void setSelectPath(String selectPath) {
		this.selectPath = selectPath;
	}

	public String getSelectTreeCatalog() {
		return selectTreeCatalog;
	}

	public void setSelectTreeCatalog(String selectTreeCatalog) {
		this.selectTreeCatalog = selectTreeCatalog;
	}

	public String getSelectMenuCatalog() {
		return selectMenuCatalog;
	}

	public void setSelectMenuCatalog(String selectMenuCatalog) {
		this.selectMenuCatalog = selectMenuCatalog;
	}

	public String getSelectYearfromId() {
		return selectYearfromId;
	}

	public void setSelectYearfromId(String selectYearfromId) {
		this.selectYearfromId = selectYearfromId;
	}

	public String getSelectYeartoId() {
		return selectYeartoId;
	}

	public void setSelectYeartoId(String selectYeartoId) {
		this.selectYeartoId = selectYeartoId;
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
