package com.cbsinc.cms.controllers;

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

public interface Layout {

	final static int REGULAR_ITEM = 0;
	final static int RECOMMENDED_ITEMS_ON_MAIN_PAGE = 1;
	final static int SPONSORED_ITEMS_ON_MAIN_PAGE = 2;
	final static int REVIEW_MESSAGES = 3;

	final static int SOFTTYPE_SHOW_ON_MAIN_PAGE = 3;
	final static int FILES_ON_PRODUCTINFO_PAGE = 5;
	final static int TABS_ON_PRODUCTINFO_PAGE = 4;
	final static int PAGE_ABOUT_PAY = 6;

	final static int PORTLET_TYPE_LEFTDOWN = 7;
	final static int PORTLET_TYPE_RIGHTDOWN = 8;
	final static int PORTLET_TYPE_LEFT_ON_PRODUCTINFO_PAGE = 9;
	final static int PORTLET_TYPE_RIGHT_ON_PRODUCTINFO_PAGE = 10;
	final static int PORTLET_TYPE_FORUM_ON_PRODUCTINFO_PAGE = 11;
	final static int PORTLET_TYPE_PAGE_LAYOUT = 12;
	final static int PORTLET_TYPE_MODULE_LAYOUT = 13;
	final static int PORTLET_TYPE_LOGO = 14;
	final static int PORTLET_TYPE_CENTER_ON_DETAIL_PAGE = 15;
	final static int PORTLET_TYPE_SEARCH_PANEL = 16;
	final static int PORTLET_TYPE_NEWS_ON_DETAIL_PAGE = 17;
	final static int FOOTER_MESSAGES = 18;
	final static int PORTLET_TYPE_PRODUCT_OFFER = 19;
	final static int PORTLET_TYPE_PRODUCT_AUCTION_BID = 20;



}
