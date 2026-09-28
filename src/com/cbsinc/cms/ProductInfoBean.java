package com.cbsinc.cms;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProductInfoBean implements java.io.Serializable {

	private static final long serialVersionUID = -4538742603892275854L;

	static private Logger log = Logger.getLogger(ProductInfoBean.class);

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

	private Long portlettypeId = new Long(0);

	private Long intUserID = new Long(0);

	private Integer roleId = 0;

	private String imgname;

	private String imageId = "0";

	private String imgUrl;

	private String cururl;

	private String strLogin = "";

	private String curencyName = "$";

	private Integer intRowId = 0;

	private String catalogId = "1";

	private String licenceId = "1";

	private String siteId = "0";

	private String productName;

	private String productURL;

	private String imgURL;

	private String bigimgURL;

	private String productDescription;

	private String productVersion;

	private String productCost = "0";

	private String currencyCd = "";

	private String owner;

	private String productId = "0";

	private String currencyDesc = "";

	private String fileExist = "";

	private String typePage = "";

	private String statistic = "0";

	private String strCDate = null;

	private String selectCurrencies = "";

	private String balans = "0";

	private String parentProductId = "0";

	private String rating1Xml = "";

	private String backUrl = "";

	public Boolean internet = true;

//	 CHECKED
	private String strShowForum = "false";
	private String strShowRatimg1 = "false";
	private String strShowRatimg2 = "false";
	private String strShowRatimg3 = "false";
	private String strSoftName2 = "";
	private String amount1 = "0";
	private String amount2 = "0";
	private String amount3 = "0";
	private String jspUrl = "";
	private String strSearch2 = "";

	private String creatorInfoUserId = "0";

	public Boolean fistOpen = true;

	public List ext1Adp = new LinkedList();

	public List extFilesAdp = new LinkedList();
	public List extTabsAdp = new LinkedList();
	public List ext2Adp = new LinkedList();
	public List blogExtAdp = new LinkedList();
	public List newsAdp = new LinkedList();
	public List bottomAdp = new LinkedList();

	public String[][] bottomRows = new String[10][2];

	public String[][] ext1Rows = new String[20][2];

	public String[][] ext2Rows = new String[20][2];

	public String[][] extFilesRows = new String[20][2];

	public String[][] extTabsRows = new String[20][2];

	public String[][] blogRows = new String[20][2];

	public String[][] newsrows = new String[10][2];

	private Integer pagecountExt1 = 0;

	private Integer pagecountExt2 = 0;

	private Integer pagecountExtFiles = 0;

	private Integer pagecountExtTabs = 0;

	private Integer pagecountBlog = 0;

	private GetValueTool tool = new GetValueTool();

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	public ProductInfoBean() {
		// calendarCDate = java.util.Calendar.getInstance();
	}

	public Long getPortlettypeId() {
		return portlettypeId;
	}

	public void setPortlettypeId(Long portlettypeId) {
		this.portlettypeId = portlettypeId;
	}

	// --------- Business logic functionality start -----

	public int stringToInt(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = 0;
		}
		return i;
	}

	// --------- Business logic functionality end -----

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
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

	public String getStrLogin() {
		return strLogin;
	}

	public void setStrLogin(String strLogin) {
		this.strLogin = strLogin;
	}

	public String getCurencyName() {
		return curencyName;
	}

	public void setCurencyName(String curencyName) {
		this.curencyName = curencyName;
	}

	public void setIntUserID(long intUserID) {
		this.intUserID = intUserID;
	}

	public long getIntUserID() {
		return intUserID;
	}

	public void setRowId(int intRowId) {
		this.intRowId = intRowId;
	}

	public int getRowId() {
		return intRowId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
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

	public String getCurrencyCd() {
		return currencyCd;
	}

	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

	public void setIntRowId(int intRowId) {
		this.intRowId = intRowId;
	}

	public int getIntRowId() {
		return intRowId;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getBigimgURL() {
		return bigimgURL;
	}

	public void setBigimgURL(String bigimgURL) {
		this.bigimgURL = bigimgURL;
	}

	public String getFileExist() {
		return fileExist;
	}

	public void setFileExist(String fileExist) {
		this.fileExist = fileExist;
	}

	public String getTypePage() {
		return typePage;
	}

	public void setTypePage(String typePage) {
		this.typePage = typePage;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public String getStrCDate() {
		return strCDate;
	}

	public void setStrCDate(String strCDate) {
		this.strCDate = strCDate;
	}

	public String getSelectCurrencies() {
		return selectCurrencies;
	}

	public void setSelectCurrencies(String selectCurrencies) {
		this.selectCurrencies = selectCurrencies;
	}

	public String getBalans() {
		return balans;
	}

	public void setBalans(String balans) {
		this.balans = balans;
	}

	public String getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getRating1Xml() {
		return rating1Xml;
	}

	public void setRating1Xml(String rating1Xml) {
		this.rating1Xml = rating1Xml;
	}

	public String getAmount1() {
		return amount1;
	}

	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}

	public String getAmount2() {
		return amount2;
	}

	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}

	public String getAmount3() {
		return amount3;
	}

	public void setAmount3(String amount3) {
		this.amount3 = amount3;
	}

	public boolean isInternet() {
		return internet;
	}

	public void setInternet(boolean isInternet) {
		this.internet = isInternet;
	}

	public String getJspUrl() {
		return jspUrl;
	}

	public void setJspUrl(String jspUrl) {
		this.jspUrl = jspUrl;
	}

	public String getStrSearch2() {
		return strSearch2;
	}

	public void setStrSearch2(String strSearch2) {
		this.strSearch2 = strSearch2;
	}

	public String getStrShowForum() {
		return strShowForum;
	}

	public void setStrShowForum(String strShowForum) {
		this.strShowForum = strShowForum;
	}

	public String getStrShowRatimg1() {
		return strShowRatimg1;
	}

	public void setStrShowRatimg1(String strShowRatimg1) {
		this.strShowRatimg1 = strShowRatimg1;
	}

	public String getStrShowRatimg2() {
		return strShowRatimg2;
	}

	public void setStrShowRatimg2(String strShowRatimg2) {
		this.strShowRatimg2 = strShowRatimg2;
	}

	public String getStrShowRatimg3() {
		return strShowRatimg3;
	}

	public void setStrShowRatimg3(String strShowRatimg3) {
		this.strShowRatimg3 = strShowRatimg3;
	}

	public String getStrSoftName2() {
		return strSoftName2;
	}

	public void setStrSoftName2(String strSoftName2) {
		this.strSoftName2 = strSoftName2;
	}

	public String getCreatorInfoUserId() {
		return creatorInfoUserId;
	}

	public void setCreatorInfoUserId(String creatorInfoUserId) {
		this.creatorInfoUserId = creatorInfoUserId;
	}

	public boolean isFistOpen() {
		return fistOpen;
	}

	public void setFistOpen(boolean fistOpen) {
		this.fistOpen = fistOpen;
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id
	 * @return
	 */

	public String getExtProductInfoFilesProductlist(String strUserId, String siteId, String treeId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		extFilesRows = new String[tool.getRowCount(extFilesAdp)][2];
		StringBuffer table = new StringBuffer();
		table.append("<extpolicy_file_list>\n");
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

			pagecountExtFiles = tool.getRowCount(extFilesAdp);
			for (int i = 0; tool.getRowCount(extFilesAdp) > i; i++) {
				extFilesRows[i][0] = (String) tool.getValueAt(extFilesAdp, i, 0);
				extFilesRows[i][1] = tool.getValueAt(extFilesAdp, i, 7) == null ? ""
						: tool.getValueAt(extFilesAdp, i, 7);
				String productName = (String) tool.getValueAt(extFilesAdp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(extFilesAdp, i, 0);
				// product_url = "ProductInfo.jsp?ext_files_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(extFilesAdp, i, 0);

				String productIconurl = (String) tool.getValueAt(extFilesAdp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(extFilesAdp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extFilesRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(extFilesAdp, i, 2);
				String productVersion = (String) tool.getValueAt(extFilesAdp, i, 3);
				String productCost = (String) tool.getValueAt(extFilesAdp, i, 4);
				String currencyId = (String) tool.getValueAt(extFilesAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(extFilesAdp, i, 12);
				String productFulldescription = (String) tool.getValueAt(extFilesAdp, i, 14);
				String user1Id = (String) tool.getValueAt(extFilesAdp, i, 16);
				String imageType = imgUrl.substring(imgUrl.indexOf(".") + 1);

				table.append("<extpolicy_file>\n");
				table.append("<product_id>" + extFilesRows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<image_type>" + imageType + "</image_type>\n");
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
				table.append("</extpolicy_file>\n");
			}
			table.append("</extpolicy_file_list>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	/**
	 * Extention info 1 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id
	 * @return
	 */

	public String getExtProductInfoOneProductlist(String strUserId, String siteId, String treeId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		ext1Rows = new String[tool.getRowCount(ext1Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<extpolicy_productlist1>\n");
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

			pagecountExt1 = tool.getRowCount(ext1Adp);
			for (int i = 0; tool.getRowCount(ext1Adp) > i; i++) {
				ext1Rows[i][0] = (String) tool.getValueAt(ext1Adp, i, 0);
				ext1Rows[i][1] = tool.getValueAt(ext1Adp, i, 7) == null ? "" : tool.getValueAt(ext1Adp, i, 7);

				String fileExist1 = "";
				if (ext1Rows[i][1].length() > 0)
					fileExist1 = "true";
				// rows[i][1] = tool.getValueAt(Adp,i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				String productName = (String) tool.getValueAt(ext1Adp, i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(ext1Adp, i, 0);
				//// strSoftURL = "downloadservlet?row=" + i ;
				// product_url = "ProductInfo.jsp?ext1_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(ext1Adp, i, 0);

				String productIconurl = (String) tool.getValueAt(ext1Adp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(ext1Adp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(ext1Adp, i, 2);
				String productVersion = (String) tool.getValueAt(ext1Adp, i, 3);
				String productCost = (String) tool.getValueAt(ext1Adp, i, 4);
				String currencyId = (String) tool.getValueAt(ext1Adp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();
				imageId = (String) tool.getValueAt(ext1Adp, i, 12);
				String productFulldescription = (String) tool.getValueAt(ext1Adp, i, 14);
				String user1Id = (String) tool.getValueAt(ext1Adp, i, 16);
				table.append("<extpolicy_product1>\n");
				table.append("<product_id>" + ext1Rows[i][0] + "</product_id>\n");
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
				table.append("</extpolicy_product1>\n");
			}
			table.append("</extpolicy_productlist1>\n");
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

	public String getExtProductInfoTabsProductlist(String strUserId, String siteId, String treeId) {

		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		extTabsRows = new String[tool.getRowCount(extTabsAdp)][2];
		StringBuffer table = new StringBuffer();
		table.append("<extpolicy_list_tabs>\n");
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

			pagecountExtTabs = tool.getRowCount(extTabsAdp);
			for (int i = 0; tool.getRowCount(extTabsAdp) > i; i++) {
				extTabsRows[i][0] = (String) tool.getValueAt(extTabsAdp, i, 0);
				extTabsRows[i][1] = tool.getValueAt(extTabsAdp, i, 7) == null ? ""
						: tool.getValueAt(extTabsAdp, i, 7);
				String productName = (String) tool.getValueAt(extTabsAdp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(extTabsAdp, i, 0);
				// product_url = "ProductInfo.jsp?ext_tabls_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(extTabsAdp, i, 0);

				String productIconurl = (String) tool.getValueAt(extFilesAdp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(extTabsAdp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extTabsRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(extTabsAdp, i, 2);
				String productVersion = (String) tool.getValueAt(extTabsAdp, i, 3);
				String productCost = (String) tool.getValueAt(extTabsAdp, i, 4);
				String currencyId = (String) tool.getValueAt(extTabsAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(extTabsAdp, i, 12);
				String productFulldescription = (String) tool.getValueAt(extTabsAdp, i, 14);
				String user1Id = (String) tool.getValueAt(extTabsAdp, i, 16);
				table.append("<extpolicy_tab>\n");
				table.append("<product_id>" + extTabsRows[i][0] + "</product_id>\n");
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
				table.append("</extpolicy_tab>\n");
			}
			table.append("</extpolicy_list_tabs>\n");
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

	public String getExtProductInfoTwoProductlist(String strUserId, String siteId, String treeId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		ext2Rows = new String[tool.getRowCount(ext2Adp)][2];

		StringBuffer table = new StringBuffer();
		table.append("<extpolicy_productlist2>\n");
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

			pagecountExt2 = tool.getRowCount(ext2Adp);
			for (int i = 0; tool.getRowCount(ext2Adp) > i; i++) {
				ext2Rows[i][0] = (String) tool.getValueAt(ext2Adp, i, 0);
				ext2Rows[i][1] = tool.getValueAt(ext2Adp, i, 7) == null ? "" : tool.getValueAt(ext2Adp, i, 7);
				String fileExist2 = "";
				if (ext2Rows[i][1].length() > 0)
					fileExist2 = "true";

				String productName = (String) tool.getValueAt(ext2Adp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(ext2Adp, i, 0);
				// product_url = "ProductInfo.jsp?ext2_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(ext2Adp, i, 0);

				String productIconurl = (String) tool.getValueAt(ext2Adp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(ext2Adp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(ext2Adp, i, 2);
				String productVersion = (String) tool.getValueAt(ext2Adp, i, 3);
				String productCost = (String) tool.getValueAt(ext2Adp, i, 4);
				String currencyId = (String) tool.getValueAt(ext2Adp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(ext2Adp, i, 12);
				String productFulldescription = (String) tool.getValueAt(ext2Adp, i, 14);
				String user1Id = (String) tool.getValueAt(ext2Adp, i, 16);
				table.append("<extpolicy_product2>\n");
				table.append("<product_id>" + ext2Rows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<file_exist>" + fileExist2 + "</file_exist>\n");
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
				table.append("</extpolicy_product2>\n");
			}
			table.append("</extpolicy_productlist2>\n");
		} catch (Exception ex) {
			log.error(ex);
		}

		return table.toString();
	}

	public String[][] getExtFilesRows() {
		return extFilesRows;
	}

	public void setExtFilesRows(String[][] extFilesRows) {
		this.extFilesRows = extFilesRows;
	}

	public String[][] getExtTabsRows() {
		return extTabsRows;
	}

	public void setExtTabsRows(String[][] extTabsRows) {
		this.extTabsRows = extTabsRows;
	}

	public int getPagecountExt1() {
		return pagecountExt1;
	}

	public void setPagecountExt1(int pagecountExt1) {
		this.pagecountExt1 = pagecountExt1;
	}

	public int getPagecountExt2() {
		return pagecountExt2;
	}

	public void setPagecountExt2(int pagecountExt2) {
		this.pagecountExt2 = pagecountExt2;
	}

	public int getPagecountExtFiles() {
		return pagecountExtFiles;
	}

	public void setPagecountExtFiles(int pagecountExtFiles) {
		this.pagecountExtFiles = pagecountExtFiles;
	}

	public int getPagecountExtTabs() {
		return pagecountExtTabs;
	}

	public void setPagecountExtTabs(int pagecountExtTabs) {
		this.pagecountExtTabs = pagecountExtTabs;
	}

	public String getPostManager() {
		return postManager;
	}

	public void setPostManager(String postManager) {
		this.postManager = postManager;
	}

	public int getPagecountBlog() {
		return pagecountBlog;
	}

	public void setPagecountBlog(int pagecountBlog) {
		this.pagecountBlog = pagecountBlog;
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param strUser_id
	 * @param site_id
	 * @param tree_id
	 * @return
	 */

	public String getBlogExtProductInfoProductlist(String strUserId, String siteId, String treeId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		blogRows = new String[tool.getRowCount(blogExtAdp)][2];
		StringBuffer table = new StringBuffer();
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

			pagecountBlog = tool.getRowCount(blogExtAdp);
			for (int i = 0; tool.getRowCount(blogExtAdp) > i; i++) {
				blogRows[i][0] = (String) tool.getValueAt(blogExtAdp, i, 0);
				blogRows[i][1] = tool.getValueAt(blogExtAdp, i, 7) == null ? "" : tool.getValueAt(blogExtAdp, i, 7);
				String productName = (String) tool.getValueAt(blogExtAdp, i, 1);
				String productUrl = "ProductInfo.jsp?blog_row=" + i;

				imgUrl = (String) tool.getValueAt(blogExtAdp, i, 13);
				String productIconurl = "";
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(blogExtAdp, i, 2);
				String productVersion = (String) tool.getValueAt(blogExtAdp, i, 3);
				String productCost = (String) tool.getValueAt(blogExtAdp, i, 4);
				String currencyId = (String) tool.getValueAt(blogExtAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(blogExtAdp, i, 12);
				String productFulldescription = (String) tool.getValueAt(blogExtAdp, i, 14);
				String user1Id = (String) tool.getValueAt(blogExtAdp, i, 16);

				String strCDate = (String) tool.getValueAt(blogExtAdp, i, 17);
				// if(strCDate.length() > 10) strCDate = strCDate.substring(0,10) ;
				String statistic = (String) tool.getValueAt(blogExtAdp, i, 18);
				String firstName = (String) tool.getValueAt(blogExtAdp, i, 19);
				String lastName = (String) tool.getValueAt(blogExtAdp, i, 20);
				String company = (String) tool.getValueAt(blogExtAdp, i, 21);

				table.append("<product_blog>\n");
				table.append("<product_id>" + blogRows[i][0] + "</product_id>\n");
				table.append("<row_id>" + i + "</row_id>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image></image>\n");
				table.append("<user_id>" + user1Id + "</user_id>\n");
				table.append("<author>" + firstName + " " + lastName + "</author>\n");
				table.append("<company>" + company + "</company>\n");
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

	public String getNewslist(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";
		this.siteId = siteId;
		newsrows = new String[tool.getRowCount(newsAdp)][2];
		StringBuffer table = new StringBuffer();
		table.append("<newslist>\n");

		try {
			for (int i = 0; tool.getRowCount(newsAdp) > i; i++) {
				newsrows[i][0] = (String) tool.getValueAt(newsAdp, i, 0);
				newsrows[i][1] = tool.getValueAt(newsAdp, i, 7) == null ? "" : tool.getValueAt(newsAdp, i, 7);

				String productName = (String) tool.getValueAt(newsAdp, i, 1);
				/// product_url = "ProductInfo.jsp?news=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(newsAdp, i, 0);

				String productIconurl = (String) tool.getValueAt(newsAdp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(newsAdp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newsrows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(newsAdp, i, 2);
				String productVersion = (String) tool.getValueAt(newsAdp, i, 3);
				String productCost = (String) tool.getValueAt(newsAdp, i, 4);
				String currencyId = (String) tool.getValueAt(newsAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(newsAdp, i, 12);
				String productFulldescription = (String) tool.getValueAt(newsAdp, i, 14);
				String user1Id = (String) tool.getValueAt(newsAdp, i, 16);

				table.append("<news>\n");
				table.append("<product_id>" + newsrows[i][0] + "</product_id>\n");
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
				table.append("<item_info>" + productUrl + "</item_info>\n");
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

	public String getBottomList(String strUserId, String siteId) {
		if (strUserId == null || strUserId.length() == 0)
			strUserId = "0";

		bottomRows = new String[tool.getRowCount(bottomAdp)][2];
		String productName;
		int pagecountBottom;
		String productUrl;
		String productIconurl;
		String productDescription;
		String productVersion;
		String productCost;
		String currencyId;
		String productFulldescription;
		String user1Id;
		StringBuffer table = new StringBuffer();

		table.append("<bottomlist>\n");

		try {
			if (roleId == 2)
				setPostManager("PostManager.jsp");
			else
				setPostManager("");
			pagecountBottom = tool.getRowCount(bottomAdp);

			for (int i = 0; tool.getRowCount(bottomAdp) > i; i++) {
				bottomRows[i][0] = (String) tool.getValueAt(bottomAdp, i, 0);
				bottomRows[i][1] = tool.getValueAt(bottomAdp, i, 7) == null ? "" : tool.getValueAt(bottomAdp, i, 7);
				productName = (String) tool.getValueAt(bottomAdp, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(bottomAdp, i, 0);
				// product_url = "ProductInfo.jsp?co1_row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(bottomAdp, i, 0);

				productIconurl = (String) tool.getValueAt(bottomAdp, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(bottomAdp, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (bottomRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(bottomAdp, i, 2);
				productVersion = (String) tool.getValueAt(bottomAdp, i, 3);
				productCost = (String) tool.getValueAt(bottomAdp, i, 4);
				currencyId = (String) tool.getValueAt(bottomAdp, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(bottomAdp, i, 12);
				productFulldescription = (String) tool.getValueAt(bottomAdp, i, 14);
				user1Id = (String) tool.getValueAt(bottomAdp, i, 16);

				table.append("<bottom>\n");
				table.append("<product_id>" + bottomRows[i][0] + "</product_id>\n");
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
				table.append("</bottom>\n");
			}
			table.append("</bottomlist>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	private String postManager = "";
	private String selectTreeCatalog = "";
	private String selectCatalogXMLUrlPath = "";

	public String getSelectTreeCatalog() {
		return selectTreeCatalog;
	}

	public void setSelectTreeCatalog(String selectTreeCatalog) {
		this.selectTreeCatalog = selectTreeCatalog;
	}

	public String getSelectCatalogXMLUrlPath() {
		return selectCatalogXMLUrlPath;
	}

	public void setSelectCatalogXMLUrlPath(String catalogXMLUrlPath) {
		this.selectCatalogXMLUrlPath = catalogXMLUrlPath;
	}

	public String getTrueValue(String tmp1, String tmp2, boolean b) {
		if (b)
			return tmp1;
		else
			return tmp2;
	}

	private String selectMenuCatalog;

	public String getSelectMenuCatalog() {
		return selectMenuCatalog;
	}

	public void setSelectMenuCatalog(String selectMenuCatalog) {
		this.selectMenuCatalog = selectMenuCatalog;
	}

}
