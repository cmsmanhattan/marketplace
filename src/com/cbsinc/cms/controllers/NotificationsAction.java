package com.cbsinc.cms.controllers;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
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

import com.cbsinc.cms.utils.Validation;
import java.text.SimpleDateFormat;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.NotificationsBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.annotations.PostActionRequestMapping;
import com.cbsinc.cms.faceds.NotificationsFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of Notifications.jsp: shows the event notifications of the current user (bids, offers, order changes, followed products) with paging and search.
 */
@PageController(jspName = "Notifications.jsp")
public class NotificationsAction implements IAction {

	NotificationsFaced notificationsFaced;
	SimpleDateFormat formatter;

	public boolean isInternet = true;

	public NotificationsAction() {
	}


	/**
	 * Mapped to action=unsubscribe: stops following the product given by product_id and returns to its page.
	 */
	@PostActionRequestMapping(action = "unsubscribe")
	public void subscribeAction(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		HttpSession session = request.getSession();
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		String positionId = request.getParameter("product_id");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		productPostAllFaced.unsubscribe(positionId,authorizationPageBeanId);
		authorizationPageBeanId.setStrMessage("The product was subscribed successfully");
		response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId);
	}

	/**
	 * Handles POST by delegating to {@link #doGet}.
	 *
	 * @throws Exception on failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		doGet(request, response, servletContext);
	}

	/**
	 * Handles GET: loads the page beans from the session/request, reads and validates the request parameters and fills the beans for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;
		NotificationsBean notificationsBean = null;
		notificationsFaced = ServiceLocator.getInstance().getNotificationsFaced();
		session = request.getSession();
		notificationsBean = (NotificationsBean) session.getAttribute("notificationsBeanId");
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		if (authorizationPageBeanId == null || notificationsBean == null || notificationsFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");
		readPagingParameters(request, notificationsBean);
		if (searchByDate(request, notificationsBean, authorizationPageBeanId))
			return;

		if (searchByStatus(request, notificationsBean, authorizationPageBeanId))
			return;

		loadEventList(request, notificationsBean, authorizationPageBeanId);
		populateLists(notificationsBean, authorizationPageBeanId);
	}

	/** offset and searchquery (offset resets when the search mode changes). */
	private void readPagingParameters(HttpServletRequest request, NotificationsBean notificationsBean) throws Exception {
		if (request.getParameter("offset") != null && Validation.isNonNegativeInteger(request.getParameter("offset"))) {
			notificationsBean.setOffset(Integer.parseInt(request.getParameter("offset")));
		}
		if (request.getParameter("searchquery") != null && Validation.isNonNegativeInteger(request.getParameter("searchquery"))) {
			if (!notificationsBean.getSearchquery().equals(request.getParameter("searchquery")))
				notificationsBean.setOffset(0);
			notificationsBean.setSearchquery(request.getParameter("searchquery"));
		}

	}

	/** searchquery=2: order history between datefrom and dateto. */
	private boolean searchByDate(HttpServletRequest request, NotificationsBean notificationsBean,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (notificationsBean.getSearchquery().equals("2")) {

			if (request.getParameter("datefrom") != null) {
				if (Validation.isNonNegativeInteger(request.getParameter("datefrom")))
					notificationsBean.setDateFrom(Long.parseLong(request.getParameter("datefrom")));
				else if (isDatePattern(request.getParameter("date_format")))
					notificationsBean.setDateFrom(request.getParameter("datefrom"), request.getParameter("date_format"),
							request.getLocale());
			}
			if (request.getParameter("dateto") != null) {
				if (Validation.isNonNegativeInteger(request.getParameter("dateto")))
					notificationsBean.setDateTo(Long.parseLong(request.getParameter("dateto")));
				else if (isDatePattern(request.getParameter("date_format")))
					notificationsBean.setDateTo(request.getParameter("dateto"), request.getParameter("date_format"),
							request.getLocale());
			}

			notificationsBean.setSelectOrderlistXML(notificationsFaced.getNotificationByDate(authorizationPageBeanId.getIntUserID(),
					notificationsBean, request.getLocale(), authorizationPageBeanId.getRoleId(),
					authorizationPageBeanId.getSiteId()));

			notificationsBean.setSearchquery("0");
			return true;
		}
		return false;
	}

	/** searchquery=3: order history filtered by payment / delivery status. */
	private boolean searchByStatus(HttpServletRequest request, NotificationsBean notificationsBean,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (notificationsBean.getSearchquery().equals("3")) {

			if (request.getParameter("order_paystatus") != null) {
				notificationsBean.setOrderPaystatusId(request.getParameter("order_paystatus"));
			}
			if (request.getParameter("order_status") != null) {
				notificationsBean.setDeliverystatusId(request.getParameter("order_status"));
			}

			notificationsBean.setSelectOrderlistXML(notificationsFaced.getNotificationByStatus(authorizationPageBeanId.getSiteId(),
					notificationsBean, request.getLocale()));
			notificationsBean.setSelectPaystatus(notificationsFaced.getXMLDBList("Notifications.jsp?order_paystatus", "paystatus",
					notificationsBean.getOrderPaystatusId(),
					"select  paystatus_id , lable  from  paystatus  where  active  = true"));
			notificationsBean.setSelectDeliverystatus(notificationsFaced.getXMLDBList("Notifications.jsp?order_deliverystatus",
					"deliverystatus", notificationsBean.getDeliverystatusId(),
					"select  deliverystatus_id , lable  from  deliverystatus  where  active  = true and lang = '"
							+ authorizationPageBeanId.getLocale() + "' "));
			notificationsBean.setSearchquery("0");
			return true;
		}
		return false;
	}

	/** Default list: carrier site -> orders assigned to it, resolution-center site -> disputes, otherwise the user's own notifications. */
	private void loadEventList(HttpServletRequest request, NotificationsBean notificationsBean,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (notificationsFaced.isShippingCompanySite(authorizationPageBeanId.getSiteId())) {
			// This site is a carrier's site (shipping_company.site_id): its
			// notifications are the orders customers assigned to the carrier.
			notificationsBean.setSelectOrderlistXML(notificationsFaced
					.getCarrierNotifications(authorizationPageBeanId.getSiteId(), notificationsBean, request.getLocale()));
		} else if (new com.cbsinc.cms.faceds.OrderFaced().resolutionColumnsPresent()
				&& notificationsFaced.isResolutionCenterSite(authorizationPageBeanId.getSiteId())) {
			// This site is a resolution center's site: its notifications are
			// the orders customers opened a dispute on.
			notificationsBean.setSelectOrderlistXML(notificationsFaced.getResolutionCenterNotifications(
					authorizationPageBeanId.getSiteId(), notificationsBean, request.getLocale()));
		} else {
			notificationsBean.setSelectOrderlistXML(notificationsFaced.getNotificatios( notificationsBean, authorizationPageBeanId, request.getLocale()));
		}
	}

	/** Payment-status, delivery-status and menu dropdowns. */
	private void populateLists(NotificationsBean notificationsBean, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		notificationsBean.setSelectPaystatus(notificationsFaced.getXMLDBList("Notifications.jsp?order_paystatus", "paystatus",notificationsBean.getOrderPaystatusId(),
				"select  paystatus_id , lable  from  paystatus  where  active  = true"));
		notificationsBean.setSelectDeliverystatus(notificationsFaced.getXMLDBList("Notifications.jsp?order_deliverystatus",
				"deliverystatus", notificationsBean.getDeliverystatusId(),
				"select  deliverystatus_id , lable  from  deliverystatus  where  active  = true and lang = '"
						+ authorizationPageBeanId.getLocale() + "' "));

		notificationsBean.setSelectMenuCatalog(notificationsFaced.getMenuXMLDBList("Productlist.jsp?catalog_id", "menu",
				authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable , parent_id  from catalog   where  active = true and parent_id = -2 and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and lang_id = "
						+ authorizationPageBeanId.getLangId()
						+ " or parent_id in (select catalog_id   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + "  and parent_id = -2 )"));

	}



	/**
	 * Tells whether the string matches the date pattern expected by the search form.
	 *
	 * @param tmp value to test
	 * @return true if it parses as a date
	 */
	public boolean isDatePattern(String tmp) {
		if (tmp == null || tmp.length() == 0)
			return false;
		String IntField = "dm/yMDY:.";
		for (int i = 0; i < tmp.length(); i++) {

			if (IntField.indexOf(tmp.charAt(i)) == -1) {
				if (tmp.charAt(i) != '-' && i != 0)
					return false;
			}
		}
		return true;
	}
}
