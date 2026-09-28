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

import com.cbsinc.cms.AuctionBidsBean;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.OffersBean;
import com.cbsinc.cms.ProductlistBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.ProductlistFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of ActionBids.jsp: lists the auction bids placed or received by
 * the current user, with paging and search by date or status.
 *
 * <p>"Action" in the original code, the database (ACTION_BID_STATUS_ID, table
 * actionbidstatus, catalog -13), the JSP name ActionBids.jsp and the
 * request values do_bid / cancel_action / accept_win_bid all mean AUCTION,
 * not an Action controller. Java identifiers were renamed to "auction";
 * database and template names were left as they are.
 */
@PageController(jspName = "ActionBids.jsp")
public class AuctionBidsAction implements IAction {

	ProductlistFaced productlistFaced ;
	SimpleDateFormat formatter;

	public boolean isInternet = true;

	public AuctionBidsAction() {
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
		AuctionBidsBean auctionBidsBeanId = null;
		ProductlistBean productlistBeanId = null ;
		productlistFaced = ServiceLocator.getInstance().getProductlistFaced() ;
		session = request.getSession();
		auctionBidsBeanId = (AuctionBidsBean) request.getAttribute("auctionBidsBeanId");
		productlistBeanId = (ProductlistBean) request.getAttribute("productlistBeanId");

		productlistBeanId = new ProductlistBean();
		request.setAttribute("productlistBeanId", productlistBeanId);
		auctionBidsBeanId = new AuctionBidsBean();
		request.setAttribute("auctionBidsBeanId", auctionBidsBeanId);

		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		if (authorizationPageBeanId == null || auctionBidsBeanId == null || productlistFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("offset") != null && Validation.isNonNegativeInteger(request.getParameter("offset"))) {
			auctionBidsBeanId.setOffset(Integer.parseInt(request.getParameter("offset")));
		}
		if (request.getParameter("searchquery") != null && Validation.isNonNegativeInteger(request.getParameter("searchquery"))) {
			if (!auctionBidsBeanId.getSearchquery().equals(request.getParameter("searchquery")))
				auctionBidsBeanId.setOffset(0);
			auctionBidsBeanId.setSearchquery(request.getParameter("searchquery"));
		}

		if(productlistFaced.isSeller(authorizationPageBeanId)) {
		 productlistBeanId.actionBisResultSet = productlistFaced.getAuctionBidsReceiver(authorizationPageBeanId);
		}
		else
		{
		 productlistBeanId.actionBisResultSet = productlistFaced.getAuctionBidsSubmitter(authorizationPageBeanId);
		}

		auctionBidsBeanId.setSelectAuctionBidsXML(productlistBeanId.getAuctionBids("" + authorizationPageBeanId.getIntUserID(),authorizationPageBeanId.getSiteId()));

		auctionBidsBeanId.setSelectMenuCatalog(productlistFaced.getMenuXMLDBList("Productlist.jsp?catalog_id", "menu",
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
