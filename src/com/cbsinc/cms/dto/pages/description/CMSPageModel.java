package com.cbsinc.cms.dto.pages.description;

import java.util.List;

import com.cbsinc.cms.dto.Admin;
import com.cbsinc.cms.dto.Bottom;
import com.cbsinc.cms.dto.CatalogItem;
import com.cbsinc.cms.dto.CurrenciesItem;
import com.cbsinc.cms.dto.MenuItem;
import com.cbsinc.cms.dto.News;
import com.cbsinc.cms.dto.ParentItem;
import com.cbsinc.cms.dto.Product;
import com.cbsinc.cms.dto.ProductBlog;

public class CMSPageModel {

	String version;
	String name;
	String title;

	String subjectSite;
	String siteName;
	String host;
	String domain;

	Admin admin;

	String roleId;
	String userSiteId;
	String internet;
	String login;
	String shopingUrl;
	String message;
	String balans;
	String toAccountHistory;
	String toLogin;
	String toRegistration;
	String toOrder;
	String toOrderHist;
	String toPay;
	String ownerUserId;
	String siteId;
	String showBlog;
	String showRating1;
	String showRating2;
	String showRating3;

	ProductDescription product;

	String showStar1;
	String showStar2;
	String showStar3;
	String showStar4;
	String showStar5;
	String showStar6;
	String showStar7;
	String showStar8;
	String showStar9;
	String showStar10;

	List<CurrenciesItem> currencies;

	List<Product> extpolicyProductlist1;

	List<Product> extpolicyProductlist2;

	List<Product> extpolicyFileList;

	List<Product> extpolicyListTabs;

	List<ProductBlog> productBlogList;

	List<News> newslist;

	List<Bottom> bottomlist;

	String emptyPageExt1;

	String emptyPageExt2;

	List<CatalogItem> catalog;

	List<ParentItem> parent;

	List<MenuItem> menu;

}
