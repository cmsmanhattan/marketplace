package com.cbsinc.cms.dto.pages;

import java.util.List;

import com.cbsinc.cms.dto.Admin;
import com.cbsinc.cms.dto.Bottom;
import com.cbsinc.cms.dto.CatalogItem;
import com.cbsinc.cms.dto.Creteria1Item;
import com.cbsinc.cms.dto.CurrenciesItem;
import com.cbsinc.cms.dto.DayFromItem;
import com.cbsinc.cms.dto.DayToItem;
import com.cbsinc.cms.dto.MenuItem;
import com.cbsinc.cms.dto.MountFromItem;
import com.cbsinc.cms.dto.MountToItem;
import com.cbsinc.cms.dto.News;
import com.cbsinc.cms.dto.ParentItem;
import com.cbsinc.cms.dto.Product;
import com.cbsinc.cms.dto.ProductBlog;
import com.cbsinc.cms.dto.XslStyle;
import com.cbsinc.cms.dto.YearFromItem;
import com.cbsinc.cms.dto.YearToItem;

public class CMSMainPageModel {

	String version;
	String name;
	String title;
	String reklama;
	String subjectSite;
	String siteName;
	String host;
	String message;
	String login;
	String passwdord;
	String balans;
	String searchValue;
	String searchQuery;
	String fromcost;
	String tocost;
	String ownerUserId;
	String roleId;
	String userSiteId;
	String siteId;
	String path;
	String dialog;
	String isAdvancedSearchOpen;
	String isForumOpen;
	String internet;
	Admin admin;
	XslStyle xslStyle;

	List<Product> productList;

	List<Product> coproductlist1;

	List<Product> coproductlist2;

	List<ProductBlog> productBlogList;

	List<News> newslist;

	List<Bottom> bottomlist;

	String emptyPageCo1;

	String emptyPageCo2;

	String emptyPage;

	String quantityProducts;

	String offset;
	String next;
	String prev;

	String criteria1Label;
	String criteria2Label;
	String criteria3Label;
	String criteria4Label;
	String criteria5Label;
	String criteria6Label;
	String criteria7Label;
	String criteria8Label;
	String criteria9Label;
	String criteria10Label;

	List<CurrenciesItem> currencies;
	List<CatalogItem> catalog;
	List<MenuItem> menu;

	List<Creteria1Item> creteria1;
	List<Creteria1Item> creteria2;
	List<Creteria1Item> creteria3;
	List<Creteria1Item> creteria4;
	List<Creteria1Item> creteria5;
	List<Creteria1Item> creteria6;
	List<Creteria1Item> creteria7;
	List<Creteria1Item> creteria8;
	List<Creteria1Item> creteria9;
	List<Creteria1Item> creteria10;

	List<DayFromItem> dayFrom;
	List<MountFromItem> mountFrom;
	List<YearFromItem> yearFrom;

	List<DayToItem> dayTo;
	List<MountToItem> mountTo;
	List<YearToItem> yearTo;

	List<ParentItem> parent;

}
