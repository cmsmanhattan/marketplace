package com.cbsinc.cms;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class ItemDescriptionBean implements java.io.Serializable {

	private static final long serialVersionUID = -4538742703993275854L;

	static private Logger log = Logger.getLogger(ItemDescriptionBean.class);

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

	private String offerAmount = "0";

	private String auctionBidAmount = "0";

	private String auctionMaxBidAmount = "0";

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
	private String strShowOffer = "false";
	/** "true" when the current user watches this product (subscription table). */
	private String strSubscribed = "false";
	private String strShowAction = "false";
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

	public Boolean firstOpen = true;

	public List columnOne = new LinkedList();

	public List attachedFiles = new LinkedList();
	public List descriptionTab = new LinkedList();
	public List columnTwo = new LinkedList();
	public List reviewMessages = new LinkedList();
	public List newArrivalItems = new LinkedList();
	public List newsItems = new LinkedList();
	public List footerLinksList = new LinkedList();

	public List  recommentedItems  = new LinkedList();

	public List  sponsoredBySellers  = new LinkedList();

	public List  recentlyReviewd  = new LinkedList();

	public String[][] bottomRows = new String[10][2];

	public String[][] ext1Rows = new String[20][2];

	public String[][] ext2Rows = new String[20][2];

	public String[][] extFilesRows = new String[20][2];

	public String[][] extTabsRows = new String[20][2];

	public String[][] blogRows = new String[20][2];

	public String[][] newsrows = new String[10][2];

	public String[][] newarrivalrows = new String[10][2];

	private Integer pagecountExt1 = 0;

	private Integer pagecountExt2 = 0;

	private Integer pagecountExtFiles = 0;

	private Integer pagecountExtTabs = 0;

	private Integer pagecountBlog = 0;

	private GetValueTool tool = new GetValueTool();

	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String  creteriaName1  = "";
	private String  creteriaLabel1   = "";
	private String  creteriaName2  = "";
	private String  creteriaLabel2   = "";
	private String  creteriaName3  = "";
	private String  creteriaLabel3   = "";
	private String  creteriaName4  = "";
	private String  creteriaLabel4   = "";
	private String  creteriaName5  = "";
	private String  creteriaLabel5   = "";
	private String  creteriaName6  = "";
	private String  creteriaLabel6  = "";
	private String  creteriaName7  = "";
	private String  creteriaLabel7   = "";
	private String  creteriaName8  = "";
	private String  creteriaLabel8  = "";
	private String  creteriaName9  = "";
	private String  creteriaLabel9  = "";
	private String  creteriaName10  = "";
	private String  creteriaLabel10  = "";





	public String getCreteriaName1() {
		return creteriaName1;
	}

	public void setCreteriaName1(String creteriaName1) {
		this.creteriaName1 = creteriaName1;
	}

	public String getCreteriaLabel1() {
		return creteriaLabel1;
	}

	public void setCreteriaLabel1(String creteriaLabel1) {
		this.creteriaLabel1 = creteriaLabel1;
	}

	public String getCreteriaName2() {
		return creteriaName2;
	}

	public void setCreteriaName2(String creteriaName2) {
		this.creteriaName2 = creteriaName2;
	}

	public String getCreteriaLabel2() {
		return creteriaLabel2;
	}

	public void setCreteriaLabel2(String creteriaLabel2) {
		this.creteriaLabel2 = creteriaLabel2;
	}

	public String getCreteriaName3() {
		return creteriaName3;
	}

	public void setCreteriaName3(String creteriaName3) {
		this.creteriaName3 = creteriaName3;
	}

	public String getCreteriaLabel3() {
		return creteriaLabel3;
	}

	public void setCreteriaLabel3(String creteriaLabel3) {
		this.creteriaLabel3 = creteriaLabel3;
	}

	public String getCreteriaName4() {
		return creteriaName4;
	}

	public void setCreteriaName4(String creteriaName4) {
		this.creteriaName4 = creteriaName4;
	}

	public String getCreteriaLabel4() {
		return creteriaLabel4;
	}

	public void setCreteriaLabel4(String creteriaLabel4) {
		this.creteriaLabel4 = creteriaLabel4;
	}

	public String getCreteriaName5() {
		return creteriaName5;
	}

	public void setCreteriaName5(String creteriaName5) {
		this.creteriaName5 = creteriaName5;
	}

	public String getCreteriaLabel5() {
		return creteriaLabel5;
	}

	public void setCreteriaLabel5(String creteriaLabel5) {
		this.creteriaLabel5 = creteriaLabel5;
	}

	public String getCreteriaName6() {
		return creteriaName6;
	}

	public void setCreteriaName6(String creteriaName6) {
		this.creteriaName6 = creteriaName6;
	}

	public String getCreteriaLabel6() {
		return creteriaLabel6;
	}

	public void setCreteriaLabel6(String creteriaLabel6) {
		this.creteriaLabel6 = creteriaLabel6;
	}

	public String getCreteriaName7() {
		return creteriaName7;
	}

	public void setCreteriaName7(String creteriaName7) {
		this.creteriaName7 = creteriaName7;
	}

	public String getCreteriaLabel7() {
		return creteriaLabel7;
	}

	public void setCreteriaLabel7(String creteriaLabel7) {
		this.creteriaLabel7 = creteriaLabel7;
	}

	public String getCreteriaName8() {
		return creteriaName8;
	}

	public void setCreteriaName8(String creteriaName8) {
		this.creteriaName8 = creteriaName8;
	}

	public String getCreteriaLabel8() {
		return creteriaLabel8;
	}

	public void setCreteriaLabel8(String creteriaLabel8) {
		this.creteriaLabel8 = creteriaLabel8;
	}

	public String getCreteriaName9() {
		return creteriaName9;
	}

	public void setCreteriaName9(String creteriaName9) {
		this.creteriaName9 = creteriaName9;
	}

	public String getCreteriaLabel9() {
		return creteriaLabel9;
	}

	public void setCreteriaLabel9(String creteriaLabel9) {
		this.creteriaLabel9 = creteriaLabel9;
	}

	public String getCreteriaName10() {
		return creteriaName10;
	}

	public void setCreteriaName10(String creteriaName10) {
		this.creteriaName10 = creteriaName10;
	}

	public String getCreteriaLabel10() {
		return creteriaLabel10;
	}

	public void setCreteriaLabel10(String creteriaLabel10) {
		this.creteriaLabel10 = creteriaLabel10;
	}

	public ItemDescriptionBean() {
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


	public String getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(String offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getAuctionBidAmount() {
		return auctionBidAmount;
	}

	public void setAuctionBidAmount(String auctionBidAmount) {
		this.auctionBidAmount = auctionBidAmount;
	}


	public String getAuctionMaxBidAmount() {
		return auctionMaxBidAmount;
	}

	public void setAuctionMaxBidAmount(String auctionMaxBidAmount) {
		this.auctionMaxBidAmount = auctionMaxBidAmount;
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

	public void setBalans(Float balans) {
		this.balans = String.valueOf(balans);
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


	public String getStrSubscribed() {
		return strSubscribed;
	}

	public void setStrSubscribed(String strSubscribed) {
		this.strSubscribed = strSubscribed;
	}

	public String getStrShowOffer() {
		return strShowOffer;
	}

	public void setStrShowOffer(String strShowOffer) {
		this.strShowOffer = strShowOffer;
	}

	public String getStrShowAction() {
		return strShowAction;
	}

	public void setStrShowAction(String strShowAction) {
		this.strShowAction = strShowAction;
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

	public boolean isFirstOpen() {
		return firstOpen;
	}

	public void setFirstOpen(boolean firstOpen) {
		this.firstOpen = firstOpen;
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param userId
	 * @param siteId
	 * @param productRefenceId
	 * @return
	 */

	public String getProductInfoAttchedFiles(long userId, String siteId, String productRefenceId) {

		extFilesRows = new String[tool.getRowCount(attachedFiles)][2];
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

			pagecountExtFiles = tool.getRowCount(attachedFiles);
			for (int i = 0; tool.getRowCount(attachedFiles) > i; i++) {
				extFilesRows[i][0] = (String) tool.getValueAt(attachedFiles, i, 0);
				extFilesRows[i][1] = tool.getValueAt(attachedFiles, i, 7) == null ? ""
						: tool.getValueAt(attachedFiles, i, 7);
				String productName = (String) tool.getValueAt(attachedFiles, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(attachedFiles, i, 0);
				// product_url = "ProductInfo.jsp?ext_files_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(attachedFiles, i, 0);

				String productIconurl = (String) tool.getValueAt(attachedFiles, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(attachedFiles, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extFilesRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(attachedFiles, i, 2);
				String productVersion = (String) tool.getValueAt(attachedFiles, i, 3);
				String productCost = (String) tool.getValueAt(attachedFiles, i, 4);
				String currencyId = (String) tool.getValueAt(attachedFiles, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(attachedFiles, i, 12);
				String productFulldescription = (String) tool.getValueAt(attachedFiles, i, 14);
				String user1Id = (String) tool.getValueAt(attachedFiles, i, 16);
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
	 * Set data in JSP
	 *
	 * @param userId
	 * @param siteId
	 * @param productRefenceId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getProductInfoAttchedFiles}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getExtProductInfoFilesProductlist(long userId, String siteId, String productRefenceId) {
		return getProductInfoAttchedFiles(userId, siteId, productRefenceId);
	}

	/**
	 * Extention info 1 for policy page
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */

	public String getProductInfoColumnOne(long userId, String siteId, String productReferenceId) {

		ext1Rows = new String[tool.getRowCount(columnOne)][2];
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

			pagecountExt1 = tool.getRowCount(columnOne);
			for (int i = 0; tool.getRowCount(columnOne) > i; i++) {
				ext1Rows[i][0] = (String) tool.getValueAt(columnOne, i, 0);
				ext1Rows[i][1] = tool.getValueAt(columnOne, i, 7) == null ? "" : tool.getValueAt(columnOne, i, 7);

				String fileExist1 = "";
				if (ext1Rows[i][1].length() > 0)
					fileExist1 = "true";
				// rows[i][1] = tool.getValueAt(Adp,i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				String productName = (String) tool.getValueAt(columnOne, i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(columnOne, i, 0);
				//// strSoftURL = "downloadservlet?row=" + i ;
				// product_url = "ProductInfo.jsp?ext1_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(columnOne, i, 0);

				String productIconurl = (String) tool.getValueAt(columnOne, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(columnOne, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(columnOne, i, 2);
				String productVersion = (String) tool.getValueAt(columnOne, i, 3);
				String productCost = (String) tool.getValueAt(columnOne, i, 4);
				String currencyId = (String) tool.getValueAt(columnOne, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();
				imageId = (String) tool.getValueAt(columnOne, i, 12);
				String productFulldescription = (String) tool.getValueAt(columnOne, i, 14);
				String user1Id = (String) tool.getValueAt(columnOne, i, 16);
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
	 * Set data in jsp
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getProductInfoColumnOne}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getExtProductInfoOneProductlist(long userId, String siteId, String productReferenceId) {
		return getProductInfoColumnOne(userId, siteId, productReferenceId);
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */

	public String getProductInfoDescriptionTabs(long userId, String siteId, String productReferenceId) {

		extTabsRows = new String[tool.getRowCount(descriptionTab)][2];
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

			pagecountExtTabs = tool.getRowCount(descriptionTab);
			for (int i = 0; tool.getRowCount(descriptionTab) > i; i++) {
				extTabsRows[i][0] = (String) tool.getValueAt(descriptionTab, i, 0);
				extTabsRows[i][1] = tool.getValueAt(descriptionTab, i, 7) == null ? ""
						: tool.getValueAt(descriptionTab, i, 7);
				String productName = (String) tool.getValueAt(descriptionTab, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(descriptionTab, i, 0);
				// product_url = "ProductInfo.jsp?ext_tabls_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(descriptionTab, i, 0);

				String productIconurl = (String) tool.getValueAt(attachedFiles, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(descriptionTab, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extTabsRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(descriptionTab, i, 2);
				String productVersion = (String) tool.getValueAt(descriptionTab, i, 3);
				String productCost = (String) tool.getValueAt(descriptionTab, i, 4);
				String currencyId = (String) tool.getValueAt(descriptionTab, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(descriptionTab, i, 12);
				String productFulldescription = (String) tool.getValueAt(descriptionTab, i, 14);
				String user1Id = (String) tool.getValueAt(descriptionTab, i, 16);
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
	 * Set data in jsp
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getProductInfoDescriptionTabs}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getExtProductInfoTabsProductlist(long userId, String siteId, String productReferenceId) {
		return getProductInfoDescriptionTabs(userId, siteId, productReferenceId);
	}

	/**
	 * Extention info 2 for policy page
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */

	public String getProductInfoColumnTwo(long userId, String siteId, String productReferenceId) {

		ext2Rows = new String[tool.getRowCount(columnTwo)][2];
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

			pagecountExt2 = tool.getRowCount(columnTwo);
			for (int i = 0; tool.getRowCount(columnTwo) > i; i++) {
				ext2Rows[i][0] = (String) tool.getValueAt(columnTwo, i, 0);
				ext2Rows[i][1] = tool.getValueAt(columnTwo, i, 7) == null ? "" : tool.getValueAt(columnTwo, i, 7);
				String fileExist2 = "";
				if (ext2Rows[i][1].length() > 0)
					fileExist2 = "true";

				String productName = (String) tool.getValueAt(columnTwo, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(columnTwo, i, 0);
				// product_url = "ProductInfo.jsp?ext2_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(columnTwo, i, 0);

				String productIconurl = (String) tool.getValueAt(columnTwo, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(columnTwo, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(columnTwo, i, 2);
				String productVersion = (String) tool.getValueAt(columnTwo, i, 3);
				String productCost = (String) tool.getValueAt(columnTwo, i, 4);
				String currencyId = (String) tool.getValueAt(columnTwo, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(columnTwo, i, 12);
				String productFulldescription = (String) tool.getValueAt(columnTwo, i, 14);
				String user1Id = (String) tool.getValueAt(columnTwo, i, 16);
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

	/**
	 * Set data in jsp
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getProductInfoColumnTwo}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getExtProductInfoTwoProductlist(long userId, String siteId, String productReferenceId) {
		return getProductInfoColumnTwo(userId, siteId, productReferenceId);
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
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */

	public String getProductInfoReviewMessages(long userId, String siteId, String productReferenceId) {

		blogRows = new String[tool.getRowCount(reviewMessages)][2];
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

			pagecountBlog = tool.getRowCount(reviewMessages);
			for (int i = 0; tool.getRowCount(reviewMessages) > i; i++) {
				blogRows[i][0] = (String) tool.getValueAt(reviewMessages, i, 0);
				blogRows[i][1] = tool.getValueAt(reviewMessages, i, 7) == null ? ""
						: tool.getValueAt(reviewMessages, i, 7);
				String productName = (String) tool.getValueAt(reviewMessages, i, 1);
				String productUrl = "ProductInfo.jsp?blog_row=" + i;

				imgUrl = (String) tool.getValueAt(reviewMessages, i, 13);
				String productIconurl = "";
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(reviewMessages, i, 2);
				String productVersion = (String) tool.getValueAt(reviewMessages, i, 3);
				String productCost = (String) tool.getValueAt(reviewMessages, i, 4);
				String currencyId = (String) tool.getValueAt(reviewMessages, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(reviewMessages, i, 12);
				String productFulldescription = (String) tool.getValueAt(reviewMessages, i, 14);
				String user1Id = (String) tool.getValueAt(reviewMessages, i, 16);

				String strCDate = (String) tool.getValueAt(reviewMessages, i, 17);
				// if(strCDate.length() > 10) strCDate = strCDate.substring(0,10) ;
				String statistic = (String) tool.getValueAt(reviewMessages, i, 18);
				String firstName = (String) tool.getValueAt(reviewMessages, i, 19);
				String lastName = (String) tool.getValueAt(reviewMessages, i, 20);
				String company = (String) tool.getValueAt(reviewMessages, i, 21);

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

	/**
	 * set data in jsp
	 *
	 * @param userId
	 * @param siteId
	 * @param productReferenceId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getProductInfoReviewMessages}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getBlogExtProductInfoProductlist(long userId, String siteId, String productReferenceId) {
		return getProductInfoReviewMessages(userId, siteId, productReferenceId);
	}

	/**
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	public String getNewArrivalItems(long userId, String siteId) {

		this.siteId = siteId; // ? bad
		newarrivalrows = new String[tool.getRowCount(newArrivalItems)][2];
		StringBuffer table = new StringBuffer();
		table.append("<new_arrival_list>\n");

		try {
			for (int i = 0; tool.getRowCount(newArrivalItems) > i; i++) {
				newarrivalrows[i][0] = (String) tool.getValueAt(newArrivalItems, i, 0);
				newarrivalrows[i][1] = tool.getValueAt(newArrivalItems, i, 7) == null ? ""
						: tool.getValueAt(newArrivalItems, i, 7);

				String productName = (String) tool.getValueAt(newArrivalItems, i, 1);
				/// product_url = "ProductInfo.jsp?news=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(newArrivalItems, i, 0);

				String productIconurl = (String) tool.getValueAt(newArrivalItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(newArrivalItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newarrivalrows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(newArrivalItems, i, 2);
				String productVersion = (String) tool.getValueAt(newArrivalItems, i, 3);
				String productCost = (String) tool.getValueAt(newArrivalItems, i, 4);
				String currencyId = (String) tool.getValueAt(newArrivalItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(newArrivalItems, i, 12);
				String productFulldescription = (String) tool.getValueAt(newArrivalItems, i, 14);
				String user1Id = (String) tool.getValueAt(newArrivalItems, i, 16);

				table.append("<new_arrival>\n");
				table.append("<product_id>" + newarrivalrows[i][0] + "</product_id>\n");
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
				table.append("</new_arrival>\n");
			}
			table.append("</new_arrival_list>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	public String getRecommentedItems() {

		String[][] co1Rows  = new String[tool.getRowCount(recommentedItems)][2];
		String productName = "" ;
		String productUrl = "" ;
		String productIconurl = "" ;
		String productDescription = "" ;
		String productVersion = "" ;
		String productCost = "" ;
		String currencyId = "" ;
		String imageId = "" ;
		String productFulldescription = "" ;
		String user1Id = "" ;

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

			 Integer pagecountCo1 = tool.getRowCount(recommentedItems);

			for (int i = 0;tool.getRowCount(recommentedItems) > i; i++) {
				co1Rows[i][0] = (String)tool.getValueAt(recommentedItems, i, 0);
				co1Rows[i][1] =tool.getValueAt(recommentedItems, i, 7) == null ? ""
						:tool.getValueAt(recommentedItems, i, 7);
				productName = (String)tool.getValueAt(recommentedItems, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String)tool.getValueAt(recommentedItems, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String)tool.getValueAt(recommentedItems, i, 0);

				productIconurl = (String)tool.getValueAt(recommentedItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String)tool.getValueAt(recommentedItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (co1Rows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String)tool.getValueAt(recommentedItems, i, 2);
				productVersion = (String)tool.getValueAt(recommentedItems, i, 3);
				productCost = (String)tool.getValueAt(recommentedItems, i, 4);
				currencyId = (String)tool.getValueAt(recommentedItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String)tool.getValueAt(recommentedItems, i, 12);
				productFulldescription = (String)tool.getValueAt(recommentedItems, i, 14);
				user1Id = (String)tool.getValueAt(recommentedItems, i, 16);

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
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	public String getSponsoredBySellersItems() {


		String[][] sponsoredBySellersRows = new String[tool.getRowCount(sponsoredBySellers)][2];

		String productName = "" ;
		String productUrl = "" ;
		String productIconurl = "" ;
		String productDescription = "" ;
		String productVersion = "" ;
		String productCost = "" ;
		String currencyId = "" ;
		String imageId = "" ;
		String productFulldescription = "" ;
		String user1Id = "" ;

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

			Integer pagecountSponsoredBySellers = tool.getRowCount(sponsoredBySellers);

			for (int i = 0; tool.getRowCount(sponsoredBySellers) > i; i++) {
				sponsoredBySellersRows[i][0] = (String) tool.getValueAt(sponsoredBySellers, i, 0);
				sponsoredBySellersRows[i][1] = tool.getValueAt(sponsoredBySellers, i, 7) == null ? ""
						: tool.getValueAt(sponsoredBySellers, i, 7);
				productName = (String) tool.getValueAt(sponsoredBySellers, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(sponsoredBySellers, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(sponsoredBySellers, i, 0);

				productIconurl = (String) tool.getValueAt(sponsoredBySellers, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(sponsoredBySellers, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (sponsoredBySellersRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(sponsoredBySellers, i, 2);
				productVersion = (String) tool.getValueAt(sponsoredBySellers, i, 3);
				productCost = (String) tool.getValueAt(sponsoredBySellers, i, 4);
				currencyId = (String) tool.getValueAt(sponsoredBySellers, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(sponsoredBySellers, i, 12);
				productFulldescription = (String) tool.getValueAt(sponsoredBySellers, i, 14);
				user1Id = (String) tool.getValueAt(sponsoredBySellers, i, 16);
				table.append("<sponsored>\n");
				table.append("<productId>" + sponsoredBySellersRows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageType>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageType>\n");
				table.append("<iconType>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconType>\n");
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
	 *
	 * @param strUser_id
	 * @param site_id
	 * @return
	 */
	public String getRecentlyReviewedItems() {


		String[][] sponsoredBySellersRows = new String[tool.getRowCount(sponsoredBySellers)][2];

		String productName = "" ;
		String productUrl = "" ;
		String productIconurl = "" ;
		String productDescription = "" ;
		String productVersion = "" ;
		String productCost = "" ;
		String currencyId = "" ;
		String imageId = "" ;
		String productFulldescription = "" ;
		String user1Id = "" ;

		StringBuffer table = new StringBuffer();
		table.append("<recentlyReviewedItems>\n");
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

			Integer pagecountSponsoredBySellers = tool.getRowCount(sponsoredBySellers);

			for (int i = 0; tool.getRowCount(sponsoredBySellers) > i; i++) {
				sponsoredBySellersRows[i][0] = (String) tool.getValueAt(sponsoredBySellers, i, 0);
				sponsoredBySellersRows[i][1] = tool.getValueAt(sponsoredBySellers, i, 7) == null ? ""
						: tool.getValueAt(sponsoredBySellers, i, 7);
				productName = (String) tool.getValueAt(sponsoredBySellers, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(sponsoredBySellers, i, 0);
				productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(sponsoredBySellers, i, 0);

				productIconurl = (String) tool.getValueAt(sponsoredBySellers, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(sponsoredBySellers, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (sponsoredBySellersRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(sponsoredBySellers, i, 2);
				productVersion = (String) tool.getValueAt(sponsoredBySellers, i, 3);
				productCost = (String) tool.getValueAt(sponsoredBySellers, i, 4);
				currencyId = (String) tool.getValueAt(sponsoredBySellers, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(sponsoredBySellers, i, 12);
				productFulldescription = (String) tool.getValueAt(sponsoredBySellers, i, 14);
				user1Id = (String) tool.getValueAt(sponsoredBySellers, i, 16);
				table.append("<reviewed>\n");
				table.append("<productId>" + sponsoredBySellersRows[i][0] + "</productId>\n");
				table.append("<rowId>" + i + "</rowId>\n");
				table.append("<file_exist>" + fileExist1 + "</file_exist>\n");
				table.append("<name>" + productName + "</name>\n");
				table.append("<icon>" + productIconurl + "</icon>\n");
				table.append("<image>" + imgUrl + "</image>\n");
				table.append("<bigImageType>" + imgUrl.substring(imgUrl.indexOf(".") + 1) + "</bigImageType>\n");
				table.append("<iconType>" + productIconurl.substring(productIconurl.indexOf(".") + 1) + "</iconType>\n");
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
				table.append("</reviewed>\n");
			}
			table.append("</recentlyReviewedItems>\n");
		} catch (Exception ex) {
			log.error(ex);
		}
		return table.toString();
	}

	/**
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	public String getNewslist(long userId, String siteId) {

		this.siteId = siteId; // ? bad
		newsrows = new String[tool.getRowCount(newArrivalItems)][2];
		StringBuffer table = new StringBuffer();
		table.append("<newslist>\n");

		try {
			for (int i = 0; tool.getRowCount(newArrivalItems) > i; i++) {
				newsrows[i][0] = (String) tool.getValueAt(newArrivalItems, i, 0);
				newsrows[i][1] = tool.getValueAt(newArrivalItems, i, 7) == null ? ""
						: tool.getValueAt(newArrivalItems, i, 7);

				String productName = (String) tool.getValueAt(newArrivalItems, i, 1);
				/// product_url = "ProductInfo.jsp?news=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(newArrivalItems, i, 0);

				String productIconurl = (String) tool.getValueAt(newArrivalItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(newArrivalItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newsrows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(newArrivalItems, i, 2);
				String productVersion = (String) tool.getValueAt(newArrivalItems, i, 3);
				String productCost = (String) tool.getValueAt(newArrivalItems, i, 4);
				String currencyId = (String) tool.getValueAt(newArrivalItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(newArrivalItems, i, 12);
				String productFulldescription = (String) tool.getValueAt(newArrivalItems, i, 14);
				String user1Id = (String) tool.getValueAt(newArrivalItems, i, 16);

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

	public String getFooterLinksList(long userId, String siteId) {

		bottomRows = new String[tool.getRowCount(footerLinksList)][2];
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
			pagecountBottom = tool.getRowCount(footerLinksList);

			for (int i = 0; tool.getRowCount(footerLinksList) > i; i++) {
				bottomRows[i][0] = (String) tool.getValueAt(footerLinksList, i, 0);
				bottomRows[i][1] = tool.getValueAt(footerLinksList, i, 7) == null ? ""
						: tool.getValueAt(footerLinksList, i, 7);
				productName = (String) tool.getValueAt(footerLinksList, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(footerLinksList, i, 0);
				// product_url = "ProductInfo.jsp?co1_row=" + i;
				productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(footerLinksList, i, 0);

				productIconurl = (String) tool.getValueAt(footerLinksList, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(footerLinksList, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (bottomRows[i][1].length() > 0)
					fileExist1 = "true";

				productDescription = (String) tool.getValueAt(footerLinksList, i, 2);
				productVersion = (String) tool.getValueAt(footerLinksList, i, 3);
				productCost = (String) tool.getValueAt(footerLinksList, i, 4);
				currencyId = (String) tool.getValueAt(footerLinksList, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(footerLinksList, i, 12);
				productFulldescription = (String) tool.getValueAt(footerLinksList, i, 14);
				user1Id = (String) tool.getValueAt(footerLinksList, i, 16);

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

	/**
	 * Set data in jsp
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	@Deprecated
	/**
	 * Identical in every respect to {@link #getFooterLinksList}: same
	 * parameters, same source collection, same generated element names. The two
	 * bodies were byte-for-byte copies, so any change made to one silently left
	 * the other behind. Both names are called from existing pages, so neither
	 * can be removed; this one now delegates.
	 */
	public String getBottomList(long userId, String siteId) {
		return getFooterLinksList(userId, siteId);
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


	// RESTORED: these five methods are called by Policy.jsp (a reachable page:
	// @PageController(jspName="Policy.jsp") + sendRedirect + policy_url links in 50 XSL
	// templates). They were removed by an earlier pass as "dead" based on Java-only
	// analysis, which cannot see JSP scriptlet calls.

	public String getExtPolicyFilesProductlist(long userId, String siteId, String productRefenceId) {

		extFilesRows = new String[tool.getRowCount(attachedFiles)][2];
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

			pagecountExtFiles = tool.getRowCount(attachedFiles);
			for (int i = 0; tool.getRowCount(attachedFiles) > i; i++) {
				extFilesRows[i][0] = (String) tool.getValueAt(attachedFiles, i, 0);
				extFilesRows[i][1] = tool.getValueAt(attachedFiles, i, 7) == null ? ""
						: tool.getValueAt(attachedFiles, i, 7);
				String productName = (String) tool.getValueAt(attachedFiles, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(attachedFiles, i, 0);
				// product_url = "ProductInfo.jsp?ext_files_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(attachedFiles, i, 0);

				String productIconurl = (String) tool.getValueAt(attachedFiles, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(attachedFiles, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extFilesRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(attachedFiles, i, 2);
				String productVersion = (String) tool.getValueAt(attachedFiles, i, 3);
				String productCost = (String) tool.getValueAt(attachedFiles, i, 4);
				String currencyId = (String) tool.getValueAt(attachedFiles, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(attachedFiles, i, 12);
				String productFulldescription = (String) tool.getValueAt(attachedFiles, i, 14);
				String user1Id = (String) tool.getValueAt(attachedFiles, i, 16);
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

	public String getExtPolicyOneProductlist(long userId, String siteId, String productReferenceId) {

		ext1Rows = new String[tool.getRowCount(columnOne)][2];
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

			pagecountExt1 = tool.getRowCount(columnOne);
			for (int i = 0; tool.getRowCount(columnOne) > i; i++) {
				ext1Rows[i][0] = (String) tool.getValueAt(columnOne, i, 0);
				ext1Rows[i][1] = tool.getValueAt(columnOne, i, 7) == null ? "" : tool.getValueAt(columnOne, i, 7);

				String fileExist1 = "";
				if (ext1Rows[i][1].length() > 0)
					fileExist1 = "true";
				// rows[i][1] = tool.getValueAt(Adp,i, 7) ; //== null
				// ?"":Adp.getValueAt(i, 7) ;

				String productName = (String) tool.getValueAt(columnOne, i, 1);
				// strSoftURL = "downloadservlet?row=" + i + "&dev=html" ;;
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(columnOne, i, 0);
				//// strSoftURL = "downloadservlet?row=" + i ;
				// product_url = "ProductInfo.jsp?ext1_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(columnOne, i, 0);

				String productIconurl = (String) tool.getValueAt(columnOne, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(columnOne, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(columnOne, i, 2);
				String productVersion = (String) tool.getValueAt(columnOne, i, 3);
				String productCost = (String) tool.getValueAt(columnOne, i, 4);
				String currencyId = (String) tool.getValueAt(columnOne, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				// Currency curr = CurrencyHash.getCurrency(currency_id);
				// if(curr == null) throw new
				// java.lang.UnsupportedOperationException("Currency curr == null
				// ");
				// currency_cd = curr.getCode();
				// currency_cd = curr.getCode();
				imageId = (String) tool.getValueAt(columnOne, i, 12);
				String productFulldescription = (String) tool.getValueAt(columnOne, i, 14);
				String user1Id = (String) tool.getValueAt(columnOne, i, 16);
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

	public String getExtPolicyTabsProductlist(long userId, String siteId, String productReferenceId) {

		extTabsRows = new String[tool.getRowCount(descriptionTab)][2];
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

			pagecountExtTabs = tool.getRowCount(descriptionTab);
			for (int i = 0; tool.getRowCount(descriptionTab) > i; i++) {
				extTabsRows[i][0] = (String) tool.getValueAt(descriptionTab, i, 0);
				extTabsRows[i][1] = tool.getValueAt(descriptionTab, i, 7) == null ? ""
						: tool.getValueAt(descriptionTab, i, 7);
				String productName = (String) tool.getValueAt(descriptionTab, i, 1);
				String attacheFile = "downloadservletbyrowid?productid="
						+ (String) tool.getValueAt(descriptionTab, i, 0);
				// product_url = "ProductInfo.jsp?ext_tabls_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(descriptionTab, i, 0);

				String productIconurl = (String) tool.getValueAt(attachedFiles, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(descriptionTab, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";
				String fileExist1 = "";
				if (extTabsRows[i][1].length() > 0)
					fileExist1 = "true";

				String productDescription = (String) tool.getValueAt(descriptionTab, i, 2);
				String productVersion = (String) tool.getValueAt(descriptionTab, i, 3);
				String productCost = (String) tool.getValueAt(descriptionTab, i, 4);
				String currencyId = (String) tool.getValueAt(descriptionTab, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(descriptionTab, i, 12);
				String productFulldescription = (String) tool.getValueAt(descriptionTab, i, 14);
				String user1Id = (String) tool.getValueAt(descriptionTab, i, 16);
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

	public String getExtPolicyTwoProductlist(long userId, String siteId, String productReferenceId) {

		ext2Rows = new String[tool.getRowCount(columnTwo)][2];
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

			pagecountExt2 = tool.getRowCount(columnTwo);
			for (int i = 0; tool.getRowCount(columnTwo) > i; i++) {
				ext2Rows[i][0] = (String) tool.getValueAt(columnTwo, i, 0);
				ext2Rows[i][1] = tool.getValueAt(columnTwo, i, 7) == null ? "" : tool.getValueAt(columnTwo, i, 7);
				String fileExist2 = "";
				if (ext2Rows[i][1].length() > 0)
					fileExist2 = "true";

				String productName = (String) tool.getValueAt(columnTwo, i, 1);
				String attacheFile = "downloadservletbyrowid?productid=" + (String) tool.getValueAt(columnTwo, i, 0);
				// product_url = "ProductInfo.jsp?ext2_row=" + i;
				String productUrl = "ProductInfo.jsp?policy_byproductid=" + (String) tool.getValueAt(columnTwo, i, 0);

				String productIconurl = (String) tool.getValueAt(columnTwo, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(columnTwo, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(columnTwo, i, 2);
				String productVersion = (String) tool.getValueAt(columnTwo, i, 3);
				String productCost = (String) tool.getValueAt(columnTwo, i, 4);
				String currencyId = (String) tool.getValueAt(columnTwo, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(columnTwo, i, 12);
				String productFulldescription = (String) tool.getValueAt(columnTwo, i, 14);
				String user1Id = (String) tool.getValueAt(columnTwo, i, 16);
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

	public String getBlogExtPolicyProductlist(long userId, String siteId, String productReferenceId) {

		blogRows = new String[tool.getRowCount(reviewMessages)][2];
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

			pagecountBlog = tool.getRowCount(reviewMessages);
			for (int i = 0; tool.getRowCount(reviewMessages) > i; i++) {
				blogRows[i][0] = (String) tool.getValueAt(reviewMessages, i, 0);
				blogRows[i][1] = tool.getValueAt(reviewMessages, i, 7) == null ? ""
						: tool.getValueAt(reviewMessages, i, 7);
				String productName = (String) tool.getValueAt(reviewMessages, i, 1);
				String productUrl = "ProductInfo.jsp?blog_row=" + i;

				imgUrl = (String) tool.getValueAt(reviewMessages, i, 13);
				String productIconurl = "";
				if (imgUrl != null)
					productIconurl = imgUrl;
				else
					productIconurl = "images/Folder.jpg";

				String productDescription = (String) tool.getValueAt(reviewMessages, i, 2);
				String productVersion = (String) tool.getValueAt(reviewMessages, i, 3);
				String productCost = (String) tool.getValueAt(reviewMessages, i, 4);
				String currencyId = (String) tool.getValueAt(reviewMessages, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(reviewMessages, i, 12);
				String productFulldescription = (String) tool.getValueAt(reviewMessages, i, 14);
				String user1Id = (String) tool.getValueAt(reviewMessages, i, 16);

				String strCDate = (String) tool.getValueAt(reviewMessages, i, 17);
				// if(strCDate.length() > 10) strCDate = strCDate.substring(0,10) ;
				String statistic = (String) tool.getValueAt(reviewMessages, i, 18);
				String firstName = (String) tool.getValueAt(reviewMessages, i, 19);
				String lastName = (String) tool.getValueAt(reviewMessages, i, 20);
				String company = (String) tool.getValueAt(reviewMessages, i, 21);

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

}
