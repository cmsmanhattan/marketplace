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
public interface SpecialCatalog {

	public static long ROOT_CATALOG = 0;

	public static long OUTPUT_PAGES_FROM_NEWS_CATALOG = -1;

	public static long NEW_ARRIVALS_CATALOG = -1;

	public static long HOME_PAGE_CATALOG = -2;

	// вывод всех с сортировкой по ID
	public static long OUTPUT_PAGES_SORT_BY_SOFT_ID = -2;

	// forum mess
	public static long OUTPUT_PAGES_AREA_FROM_USERSITE_TO_MAIN_SITE = -3;

	public static long NEW_DESIGNS_CATALOG = -3;

	// posted message is for aprove admin
	public static long CONTENT_WAITING_FOR_APROVEMENT = -4;

	// posted message is no aprove admin
	public static long CONTENT_REJECTED_PUBLICATION = -5;

	// вывод всех с сортировкой по дате создания
	public static long OUTPUT_PAGES_SORT_BY_CREATED_DATE = -6;

	// вывод всех по рейтингу
	public static long OUTPUT_PAGES_SORT_BY_RATING = -7;

	// вывод новинок которые ввел один пользователь из своего кабинета
	public static long OUTPUT_PAGES_NEW_USER_CONTENT_FOR_APROVEMENT_SORT_BY_DATE = -9;

	// вывод наиболее посещаемых страниц
	public static long OUTPUT_PAGES_SORT_BY_VISIT_STATISTICS = -10;

	// Вывод служебных страниц
	public static long FOR_EXTERNAL_PAGE = -11;

	public static long OFFERS_CATALOG = -12;
	public static long AUCTION_BID_CATALOG = -13;

	public static long SHIPPING_DESIGNS_CATALOG = -14;
	public static long RESOLUTION_CENTER_DESIGNS_CATALOG = -15;

	// Used in site menu for resolution center. These are catalog ids on the
	// resolution-center site (site_id -4): "Statuses" (-19) and its three
	// children. Catalog ids are per site, so -20..-22 here and the carrier
	// statuses below (-20..-25 on the carrier sites) do not collide.
	// Aligned with sql/init.sql; the previous values (-16/-17/-18) had no rows.
	public static long RESOLUTION_STATUS = -19;
	public static long RESOLUTION_CENTER_NEW = -20;
	public static long RESOLUTION_CENTER_INPROCESS = -21;
	public static long RESOLUTION_CENTER_SOLVED = -22;

	// Used in site menu for shipping
	public static long SHIPPING_NEW = -20;
	public static long SHIPPING_PRINTED_LABEL = -21;
	public static long SHIPPING_SHIPPED = -22;
	public static long SHIPPING_INDELIVERY = -23;
	public static long SHIPPING_OUTOFF_DELIVERY = -24;
	public static long SHIPPING_ISSUE_SOLVED = -25;
	public static long SHIPPING_DELIVERED = -26;
	public static long SHIPPING_STATUS = -27;



}
