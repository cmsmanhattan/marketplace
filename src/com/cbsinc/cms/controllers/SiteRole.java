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

public interface SiteRole {

	final static String GUEST = "user";
	final static String GUEST_PASSWORD = "user";
	final static int GUEST_ID = 1;
	final static int GUEST_ROLE_ID = 0;
	final static int MEMBER_ROLE_ID = 1;
	final static String ADMINISTRATOR = "admin";
	final static int ADMINISTRATOR_ID = 2;
	final static int ADMINISTRATOR_ROLE_ID = 2;
	/**
	 * Operational staff (roles table: 3 = Shipping, 4 = Fullfilment). They
	 * process orders of their own site - view any order of the site and move it
	 * through payment / delivery statuses - but cannot edit products, prices or
	 * money, and never pay for or take over a customer's order. Before pass 33
	 * these ids were declared as CONTENT_MANAGER / CUSTOMER and used nowhere.
	 */
	final static String SHIPPING = "shipping";
	final static int SHIPPING_ROLE_ID = 3;
	final static String FULFILLMENT = "fulfillment";
	final static int FULFILLMENT_ROLE_ID = 4;

	/** Full control over the site: products, orders, money, users. */
	static boolean isAdministrator(long roleId) {
		return roleId == ADMINISTRATOR_ROLE_ID;
	}

	/** Administrator or operational staff (3, 4). */
	static boolean isStaff(long roleId) {
		return roleId == ADMINISTRATOR_ROLE_ID || roleId == SHIPPING_ROLE_ID || roleId == FULFILLMENT_ROLE_ID;
	}

	/**
	 * May open any order of the site and change its payment / delivery status.
	 * Kept separate from {@link #isStaff} so the two can diverge later.
	 */
	static boolean canProcessOrders(long roleId) {
		return isStaff(roleId);
	}

}
