package com.cbsinc.cms.dto.pages.order;

import java.util.List;

import com.cbsinc.cms.dto.Admin;
import com.cbsinc.cms.dto.CityItem;
import com.cbsinc.cms.dto.CountryItem;
import com.cbsinc.cms.dto.MenuItem;

public class CMSItemsOrderPageModel {

	String version;
	String name;
	String roleId;
	String title;
	Admin admin;

	String subjectSite;
	String siteName;
	String host;
	String login;
	String passwdord;
	String message;
	String shopingUrl;
	String balans;
	String shipmentPhone;
	String contactPerson;
	String shipmentEmail;

	String firstname;
	String lastname;
	String company;
	String email;
	String phone;
	String mphone;
	String fax;
	String icq;
	String website;
	String question;
	String answer;
	String countryId;
	String cityId;
	String site;

	List<ProductInCart> list;

	String emptyPage;
	String emptyBasket;
	String quantityProduct;
	String offset;

	String next;
	String prev;

	String action;
	String imgname;
	String imageId;
	String imgUrl;
	String orderEndAmount;
	String orderAmount;
	String orderTax;
	String orderId;
	String orderCurrencyId;
	String orderPaystatus;
	String orderStatus;
	String orderStatusLable;
	String deliveryAmoun;
	String deliveryTimeend;
	String deliveryLong;
	String deliveryStart;
	String cardsName;
	String cityFullname;
	String countryFullname;
	String currencyLable;

	String shipmentAddress;
	String shipmentFax;
	String shipmentDescription;
	String cityName;
	String countryName;

	String countryTelcode;
	String currencyRate;
	String cityTelcode;
	String cdate;
	String paystatusLable;

	String internet;

	List<MenuItem> menu;

	List<CountryItem> country;

	List<CityItem> city;

	List<PaystatusItem> paystatus;

	List<DeliverystatusItem> deliverystatus;

}
