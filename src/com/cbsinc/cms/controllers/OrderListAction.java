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
import com.cbsinc.cms.OrderListBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.OrderFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of OrderList.jsp: lists the user's orders with paging and search by date or status. searchquery=3 (all site orders) requires an operational role (pass 33).
 */
@PageController(jspName = "OrderList.jsp")
public class OrderListAction implements IAction {

	OrderFaced orderFaced;
	SimpleDateFormat formatter;

	public boolean isInternet = true;

	public OrderListAction() {
	}

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		doGet(request, response, servletContext);
	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;
		OrderListBean orderListBean = null;
		orderFaced = ServiceLocator.getInstance().getOrderFaced();
		session = request.getSession();
		orderListBean = (OrderListBean) session.getAttribute("orderListBeanId");
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		if (authorizationPageBeanId == null || orderListBean == null || orderFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("offset") != null && Validation.isNonNegativeInteger(request.getParameter("offset"))) {
			orderListBean.setOffset(Integer.parseInt(request.getParameter("offset")));
		}
		if (request.getParameter("searchquery") != null && Validation.isNonNegativeInteger(request.getParameter("searchquery"))) {
			if (!orderListBean.getSearchquery().equals(request.getParameter("searchquery")))
				orderListBean.setOffset(0);
			orderListBean.setSearchquery(request.getParameter("searchquery"));
		}

		if (orderListBean.getSearchquery().equals("2")) {

			if (request.getParameter("datefrom") != null) {
				if (Validation.isNonNegativeInteger(request.getParameter("datefrom")))
					orderListBean.setDateFrom(Long.parseLong(request.getParameter("datefrom")));
				else if (isDatePattern(request.getParameter("date_format")))
					orderListBean.setDateFrom(request.getParameter("datefrom"), request.getParameter("date_format"),
							request.getLocale());
			}
			if (request.getParameter("dateto") != null) {
				if (Validation.isNonNegativeInteger(request.getParameter("dateto")))
					orderListBean.setDateTo(Long.parseLong(request.getParameter("dateto")));
				else if (isDatePattern(request.getParameter("date_format")))
					orderListBean.setDateTo(request.getParameter("dateto"), request.getParameter("date_format"),
							request.getLocale());
			}
			orderListBean.setSelectOrderlistXML(orderFaced.getOrderlistByDate(authorizationPageBeanId.getIntUserID(),
					orderListBean, request.getLocale(), authorizationPageBeanId.getRoleId(),
					authorizationPageBeanId.getSiteId()));
			orderListBean.setSearchquery("0");
			return;
		}

		if (orderListBean.getSearchquery().equals("3")
				&& !SiteRole.canProcessOrders(authorizationPageBeanId.getRoleId())) {
			// site-wide search by status is for staff; customers get their own list
			orderListBean.setSearchquery("0");
		}
		if (orderListBean.getSearchquery().equals("3")) {

			if (request.getParameter("order_paystatus") != null) {
				orderListBean.setOrderPaystatusId(request.getParameter("order_paystatus"));
			}
			if (request.getParameter("order_status") != null) {
				orderListBean.setDeliverystatusId(request.getParameter("order_status"));
			}

			orderListBean.setSelectOrderlistXML(orderFaced.getOrderlistByStatus(authorizationPageBeanId.getSiteId(),
					orderListBean, request.getLocale()));
			orderListBean.setSelectPaystatus(orderFaced.getXMLDBList("OrderList.jsp?order_paystatus", "paystatus",
					orderListBean.getOrderPaystatusId(),
					"select  paystatus_id , lable  from  paystatus  where  active  = true"));
			orderListBean.setSelectDeliverystatus(orderFaced.getXMLDBList("OrderList.jsp?order_deliverystatus",
					"deliverystatus", orderListBean.getDeliverystatusId(),
					"select  deliverystatus_id , lable  from  deliverystatus  where  active  = true and lang = '"
							+ authorizationPageBeanId.getLocale() + "' "));
			orderListBean.setSearchquery("0");
			return;
		}

		orderListBean.setSelectOrderlistXML(
				orderFaced.getOrderlist(authorizationPageBeanId.getIntUserID(), orderListBean, request.getLocale()));
		orderListBean.setSelectPaystatus(orderFaced.getXMLDBList("OrderList.jsp?order_paystatus", "paystatus",
				orderListBean.getOrderPaystatusId(),
				"select  paystatus_id , lable  from  paystatus  where  active  = true"));
		orderListBean.setSelectDeliverystatus(orderFaced.getXMLDBList("OrderList.jsp?order_deliverystatus",
				"deliverystatus", orderListBean.getDeliverystatusId(),
				"select  deliverystatus_id , lable  from  deliverystatus  where  active  = true and lang = '"
						+ authorizationPageBeanId.getLocale() + "' "));

		orderListBean.setSelectMenuCatalog(orderFaced.getMenuXMLDBList("Productlist.jsp?catalog_id", "menu",
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
