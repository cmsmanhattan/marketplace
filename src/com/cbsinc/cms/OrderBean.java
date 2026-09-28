package com.cbsinc.cms;

import java.text.NumberFormat;
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

public class OrderBean implements java.io.Serializable {

	transient private static final long serialVersionUID = 2520644735859729809L;
	static private Logger log = Logger.getLogger(OrderBean.class);

	public List  newArrivalItems = new LinkedList();
	public List  recommentedItems  = new LinkedList();
	public List  sponsoredBySellers  = new LinkedList();
	public List  recentlyReviewd  = new LinkedList();
	private GetValueTool tool = new GetValueTool();
	private String postManager = "" ;
	private String cururl;

	public String getPostManager() {
		return postManager;
	}

	public void setPostManager(String postManager) {
		this.postManager = postManager;
	}

	public String getCururl() {
		return cururl;
	}

	public void setCururl(String cururl) {
		this.cururl = cururl;
	}


	private String listup = "";

	private String listdown = "";

	private Integer offset = 0;

	private String typeId = "1";

	private Integer roleId = 0;

	private String phonetypeId = "1";

	private String prognameId = "1";

	private String imgname;

	private String imageId;

	private String imgUrl;

	private String action;

	private String orderId = "";

	private String userId;

	private String cityId = "0";

	private String countryId = "0";

	private String orderCurrencyId = "0";

	private String deliveryAmoun = "0";

	private String deliveryTimeend = "";

	private String endAmount = "0";

	private String orderAmount = "0";

	private String orderTax = "0";

	private String orderDeliveryLong = "0";

	private String orderPaystatus = "0";

	private String productName = "";

	private String productDescription = "";

	private String cardsName = "";

	private String cityFullname = "";

	private String countryFullname = "";

	private String currencyLable = "";

	private String shipmentAddress = "";

	private String shipmentPhone = "";

	private String contactPerson = "";

	private String shipmentEmail = "";

	private String shipmentFax = "";

	private String shipmentDescription = "";

	private String cityName = "";

	private String countryName = "";

	private String countryTelcode = "0";

	private String currencyRate = "0";

	private String productCost = "0";

	private String productWeight = "0";

	private String productCount = "0";

	private String shipmentZip = "0";

	private String orderCityTelcode = "";

	private String deliveryStart = "";

	private String cdate = "";

	private String productCurrencyCd = "";

	private String productCurrencyLable = "";

	private String basketId = "";

	private String paystatusLable = "";

	private String accountHistoryId = "";

	private String imei = "0";

	private String phonemodelId = "";

	private Integer pagecount = 0;

	private Float balans = Float.valueOf(0);

	private String strBalans = "0";
	private String productList = "";
	private String selectCountry = "";
	private String selectCity = "";
	private String selectPaystatus = "";

	private Boolean emptyBasket = false;

	private Integer quantityProduct = 0;

	public Boolean isInternet = true;

	private String deliverystatusId = "0";

	private String selectDeliverystatus = "";

	// Shipping company chosen by the customer (shipping_company table). Each
	// carrier is a site of its own (shipping_company.site_id), so the order
	// becomes visible on that carrier's site through the change_status trigger
	// that copies orders.shipping_company_id into shipping_tracking / orders_hist.
	private String shippingCompanyId = "0";
	private String selectShippingCompany = "";
	private String shippingCompanyName = "";
	private String shippingCompanySiteId = "0";
	private String shippingCompanyHost = "";

	// Resolution center chosen by the customer for a dispute on this order
	// (resolution_center table; each center owns a site). Written only when
	// sql/resolution_center_migration.sql has been applied. resolution_status_id
	// is the catalog id of the status folder on the center's site (New /
	// In process / Solved), 0 = no dispute.
	private String resolutionCenterId = "0";
	private String resolutionStatusId = "0";
	private String selectResolutionCenter = "";
	private String resolutionCenterName = "";
	private String resolutionCenterSiteId = "0";
	private String resolutionCenterHost = "";
	private String resolutionStatusLable = "";
	private boolean resolutionCenterEnabled = false;

	private String orderStatus = "";

	private String postOwnerId = "0";

	NumberFormat nf;

	public OrderBean() {
		nf = NumberFormat.getInstance();
		nf.setGroupingUsed(true);
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setUserID(String strUserID) {
		this.userId = strUserID;
	}

	public String getUserID() {
		return userId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getOrderCurrencyId() {
		return orderCurrencyId;
	}

	public void setOrderCurrencyId(String orderCurrencyId) {
		this.orderCurrencyId = orderCurrencyId;
	}

	public String getDeliveryAmoun() {
		return deliveryAmoun;
	}

	public void setDeliveryAmoun(String deliveryAmoun) {
		this.deliveryAmoun = deliveryAmoun;
	}

	public String getDeliveryTimeend() {
		return deliveryTimeend;
	}

	public void setDeliveryTimeend(String deliveryTimeend) {
		this.deliveryTimeend = deliveryTimeend;
	}

	public String getEndAmount() {
		// if( end_amount.length() == 0 ) end_amount = "0" ;
		return endAmount;
	}

	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}

	public String getOrderAmount() {
		// if( order_amount.length() == 0 ) order_amount = "0" ;
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTax() {
		// if( order_tax.length() == 0 ) order_tax = "0" ;
		return orderTax;
	}

	public void setOrderTax(String orderTax) {
		this.orderTax = orderTax;
	}

	public String getOrderDeliveryLong() {
		// if( order_delivery_long.length() == 0 ) order_delivery_long = "0" ;
		return orderDeliveryLong;
	}

	public void setOrderDeliveryLong(String orderDeliveryLong) {
		this.orderDeliveryLong = orderDeliveryLong;
	}

	public String getOrderPaystatus() {
		return orderPaystatus;
	}

	public void setOrderPaystatus(String orderPaystatus) {
		this.orderPaystatus = orderPaystatus;
	}

	/*
	 * public String getshipping_lable() { return shipping_lable; } public void
	 * setshipping_lable(String shipping_lable) { this.shipping_lable =
	 * shipping_lable; } public String getshipping_description() { return
	 * shipping_description; } public void setshipping_description(String
	 * shipping_description) { this.shipping_description = shipping_description; }
	 */
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getCardsName() {
		return cardsName;
	}

	public void setCardsName(String cardsName) {
		this.cardsName = cardsName;
	}

	public String getCityFullname() {
		return cityFullname;
	}

	public void setCityFullname(String cityFullname) {
		this.cityFullname = cityFullname;
	}

	public String getCountryFullname() {
		return countryFullname;
	}

	public void setCountryFullname(String countryFullname) {
		this.countryFullname = countryFullname;
	}

	public String getCurrencyLable() {
		return currencyLable;
	}

	public void setCurrencyLable(String currencyLable) {
		this.currencyLable = currencyLable;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShipmentAddress() {
		return shipmentAddress;
	}

	public void setShipmentAddress(String shipmentAddress) {
		this.shipmentAddress = shipmentAddress;
	}

	public String getShipmentPhone() {
		return shipmentPhone;
	}

	public void setShipmentPhone(String shipmentPhone) {
		this.shipmentPhone = shipmentPhone;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getShipmentEmail() {
		return shipmentEmail;
	}

	public void setShipmentEmail(String shipmentEmail) {
		this.shipmentEmail = shipmentEmail;
	}

	public String getShipmentFax() {
		return shipmentFax;
	}

	public void setShipmentFax(String shipmentFax) {
		this.shipmentFax = shipmentFax;
	}

	public String getShipmentDescription() {
		return shipmentDescription;
	}

	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryTelcode() {
		return countryTelcode;
	}

	public void setCountryTelcode(String countryTelcode) {
		this.countryTelcode = countryTelcode;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	public String getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(String productWeight) {
		this.productWeight = productWeight;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getShipmentZip() {
		return shipmentZip;
	}

	public void setShipmentZip(String shipmentZip) {
		this.shipmentZip = shipmentZip;
	}

	public String getOrderCityTelcode() {
		return orderCityTelcode;
	}

	public void setOrderCityTelcode(String orderCityTelcode) {
		this.orderCityTelcode = orderCityTelcode;
	}

	public String getDeliveryStart() {
		return deliveryStart;
	}

	public void setDeliveryStart(String deliveryStart) {
		this.deliveryStart = deliveryStart;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getProductCurrencyCd() {
		return productCurrencyCd;
	}

	public void setProductCurrencyCd(String productCurrencyCd) {
		this.productCurrencyCd = productCurrencyCd;
	}

	public String getProductCurrencyLable() {
		return productCurrencyLable;
	}

	public void setProductCurrencyLable(String productCurrencyLable) {
		this.productCurrencyLable = productCurrencyLable;
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

	public String getPaystatusLable() {
		return paystatusLable;
	}

	public void setPaystatusLable(String paystatusLable) {
		this.paystatusLable = paystatusLable;
	}

	public String getTrueValue(String tmp1, String tmp2, boolean b) {
		if (b)
			return tmp1;
		else
			return tmp2;
	}

	public String getAccountHistoryId() {
		return accountHistoryId;
	}

	public void setAccountHistoryId(String accountHistoryId) {
		this.accountHistoryId = accountHistoryId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhonemodelId() {
		return phonemodelId;
	}

	public void setPhonemodelId(String phonemodelId) {
		this.phonemodelId = phonemodelId;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public float getBalans() {
		return balans;
	}

	public void setBalans(float balans) {
		this.balans = balans;
	}

	public String getSelectCity() {
		return selectCity;
	}

	public void setSelectCity(String selectCity) {
		this.selectCity = selectCity;
	}

	public String getSelectCountry() {
		return selectCountry;
	}

	public void setSelectCountry(String selectCountry) {
		this.selectCountry = selectCountry;
	}

	public String getSelectPaystatus() {
		return selectPaystatus;
	}

	public void setSelectPaystatus(String selectPaystatus) {
		this.selectPaystatus = selectPaystatus;
	}

	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public int getQuantityProduct() {
		return quantityProduct;
	}

	public void setQuantityProduct(int quantityProduct) {
		this.quantityProduct = quantityProduct;
	}

	public boolean isEmptyBasket() {
		return emptyBasket;
	}

	public void setEmptyBasket(boolean emptyBasket) {
		this.emptyBasket = emptyBasket;
	}

	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String getStrBalans() {
		strBalans = nf.format(balans);
		return strBalans;
	}

	public void setStrBalans(String strBalans) {
		balans = Float.parseFloat(strBalans);
		this.strBalans = strBalans;
	}

	public String getDeliverystatusId() {
		return deliverystatusId;
	}

	public void setDeliverystatusId(String deliverystatusId) {
		this.deliverystatusId = deliverystatusId;
	}

	public String getSelectDeliverystatus() {
		return selectDeliverystatus;
	}

	public void setSelectDeliverystatus(String selectDeliverystatus) {
		this.selectDeliverystatus = selectDeliverystatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPostOwnerId() {
		return postOwnerId;
	}

	public void setPostOwnerId(String postOwnerId) {
		this.postOwnerId = postOwnerId;
	}

	private String selectMenuCatalog;

	public String getSelectMenuCatalog() {
		return selectMenuCatalog;
	}

	public void setSelectMenuCatalog(String selectMenuCatalog) {
		this.selectMenuCatalog = selectMenuCatalog;
	}

	/**
	 *
	 * @param userId
	 * @param siteId
	 * @return
	 */
	public String getNewArrivalItems( AuthorizationPageBean authorizationPageBeanId ) {


		String[][] newarrivalrows = new String[tool.getRowCount(newArrivalItems)][2];
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
		String currencyDesc = "" ;

		if (authorizationPageBeanId.getRoleId() == 2)
			setPostManager("PostManager.jsp");
		else
			setPostManager("");

		StringBuffer table = new StringBuffer();
		table.append("<new_arrival_list>\n");

		try {
			for (int i = 0; tool.getRowCount(newArrivalItems) > i; i++) {
				newarrivalrows[i][0] = (String) tool.getValueAt(newArrivalItems, i, 0);
				newarrivalrows[i][1] = tool.getValueAt(newArrivalItems, i, 7) == null ? ""
						: tool.getValueAt(newArrivalItems, i, 7);

				 productName = (String) tool.getValueAt(newArrivalItems, i, 1);
				/// product_url = "ProductInfo.jsp?news=" + i;
				 productUrl = "ProductInfo.jsp?policy_byproductid="
						+ (String) tool.getValueAt(newArrivalItems, i, 0);

				 productIconurl = (String) tool.getValueAt(newArrivalItems, i, 13);
				if (productIconurl == null)
					productIconurl = "images/Folder.jpg";

				imgUrl = (String) tool.getValueAt(newArrivalItems, i, 15);
				if (imgUrl == null)
					imgUrl = "images/Folder.jpg";

				String fileExist1 = "";
				if (newarrivalrows[i][1].length() > 0)
					fileExist1 = "true";

				 productDescription = (String) tool.getValueAt(newArrivalItems, i, 2);
				 productVersion = (String) tool.getValueAt(newArrivalItems, i, 3);
				 productCost = (String) tool.getValueAt(newArrivalItems, i, 4);
				 currencyId = (String) tool.getValueAt(newArrivalItems, i, 5);
				CurrencyHash currencyHash = CurrencyHash.getInstance();
				 currencyDesc = currencyHash.getCurrencyDecs(currencyId);
				imageId = (String) tool.getValueAt(newArrivalItems, i, 12);
				 productFulldescription = (String) tool.getValueAt(newArrivalItems, i, 14);
				 user1Id = (String) tool.getValueAt(newArrivalItems, i, 16);

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
				table.append("<code>" + currencyId + "</code>\n");
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

	public String getRecommentedItems(AuthorizationPageBean authorizationPageBeanId) {

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
		String currencyDesc = "" ;

		StringBuffer table = new StringBuffer();
		table.append("<recommentedItems>\n");
		String cururl = "Productlist.jsp?offset=" + offset; // + "&catalog_id=" +
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
			if (authorizationPageBeanId.getRoleId() == 2)
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
				table.append("<code>" + currencyId + "</code>\n");
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
	public String getSponsoredBySellersItems(AuthorizationPageBean authorizationPageBeanId) {


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
		String currencyDesc = "" ;

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
			if (authorizationPageBeanId.getRoleId() == 2)
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
				table.append("<code>" + currencyId + "</code>\n");
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
	public String getRecentlyReviewedItems(AuthorizationPageBean authorizationPageBeanId) {


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
		String currencyDesc = "" ;

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
			if (authorizationPageBeanId.getRoleId() == 2)
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
				table.append("<code>" + currencyId + "</code>\n");
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



	public String getShippingCompanyId() {
		return shippingCompanyId;
	}

	public void setShippingCompanyId(String shippingCompanyId) {
		this.shippingCompanyId = shippingCompanyId == null || shippingCompanyId.isEmpty() ? "0"
				: shippingCompanyId;
	}

	/** XML option list built by OrderFaced.getXMLDBList for the order template. */
	public String getSelectShippingCompany() {
		return selectShippingCompany;
	}

	public void setSelectShippingCompany(String selectShippingCompany) {
		this.selectShippingCompany = selectShippingCompany;
	}

	public String getShippingCompanyName() {
		return shippingCompanyName;
	}

	public void setShippingCompanyName(String shippingCompanyName) {
		this.shippingCompanyName = shippingCompanyName == null ? "" : shippingCompanyName;
	}

	/** site.site_id of the carrier's own site in this system. */
	public String getShippingCompanySiteId() {
		return shippingCompanySiteId;
	}

	public void setShippingCompanySiteId(String shippingCompanySiteId) {
		this.shippingCompanySiteId = shippingCompanySiteId == null ? "0" : shippingCompanySiteId;
	}

	/** site.host of the carrier's site, for a link from the order page. */
	public String getShippingCompanyHost() {
		return shippingCompanyHost;
	}

	public void setShippingCompanyHost(String shippingCompanyHost) {
		this.shippingCompanyHost = shippingCompanyHost == null ? "" : shippingCompanyHost;
	}


	public String getResolutionCenterId() {
		return resolutionCenterId;
	}

	public void setResolutionCenterId(String v) {
		this.resolutionCenterId = v == null || v.isEmpty() ? "0" : v;
	}

	public String getResolutionStatusId() {
		return resolutionStatusId;
	}

	public void setResolutionStatusId(String v) {
		this.resolutionStatusId = v == null || v.isEmpty() ? "0" : v;
	}

	public String getSelectResolutionCenter() {
		return selectResolutionCenter;
	}

	public void setSelectResolutionCenter(String v) {
		this.selectResolutionCenter = v == null ? "" : v;
	}

	public String getResolutionCenterName() {
		return resolutionCenterName;
	}

	public void setResolutionCenterName(String v) {
		this.resolutionCenterName = v == null ? "" : v;
	}

	public String getResolutionCenterSiteId() {
		return resolutionCenterSiteId;
	}

	public void setResolutionCenterSiteId(String v) {
		this.resolutionCenterSiteId = v == null ? "0" : v;
	}

	public String getResolutionCenterHost() {
		return resolutionCenterHost;
	}

	public void setResolutionCenterHost(String v) {
		this.resolutionCenterHost = v == null ? "" : v;
	}

	public String getResolutionStatusLable() {
		return resolutionStatusLable;
	}

	public void setResolutionStatusLable(String v) {
		this.resolutionStatusLable = v == null ? "" : v;
	}

	/** true when the orders table has the resolution columns (migration applied). */
	public boolean isResolutionCenterEnabled() {
		return resolutionCenterEnabled;
	}

	public void setResolutionCenterEnabled(boolean v) {
		this.resolutionCenterEnabled = v;
	}

}
