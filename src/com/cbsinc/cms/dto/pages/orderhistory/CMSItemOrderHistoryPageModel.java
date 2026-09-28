package com.cbsinc.cms.dto.pages.orderhistory;

import java.util.List;

import com.cbsinc.cms.dto.MenuItem;

public class CMSItemOrderHistoryPageModel {

	String version;
	String name;

	String title;
	String subjectSite;
	String siteName;
	String host;
	String login;
	String roleId;
	String passwdord;
	String shopingUrl;
	String message;
	String balans;
	String datefromFormated;
	String datetoFormated;
	String datefrom;
	String dateto;
	String dateFormat;

	List<HistoryOrder> list;

	String next;
	String prev;

	List<MenuItem> menu;

}
