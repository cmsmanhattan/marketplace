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

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CatalogAddBean;
import com.cbsinc.cms.CatalogEditBean;
import com.cbsinc.cms.CatalogListBean;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.annotations.PostActionRequestMapping;
import com.cbsinc.cms.annotations.PutActionRequestMapping;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of PostProductBidAction.jsp: auction bids on a product. Every action verifies that the bid belongs to the caller or that the caller is the seller (pass 9).
 */
@PageController(jspName = "PostProductBidAction.jsp")
public class PostProductBidAction implements IAction {

	//private ProductPostAllFaced productPostAllFaced;
	private boolean isCriteriaByCatalog = false;
	private String notselected = "";
	private transient ResourceBundle setupResources = null;

	public PostProductBidAction() {

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");
		isCriteriaByCatalog = setupResources.getString("is_criteria_by_catalog").equals("true");

	}

	/**
	 * action=pay: pays for a won lot.
	 */
	@PostActionRequestMapping(action = "pay")
	public void payWinningBid(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		if (authorizationPageBeanId.getRoleId() == SiteRole.GUEST_ROLE_ID) {
			authorizationPageBeanId.setStrMessage("Please login to access the option.");
			response.sendRedirect("Authorization.jsp?Login=");
			return ;
		}

		publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());
		String positionId = request.getParameter("product_id");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		productPostAllFaced.initPage(positionId,  publisherBeanId, authorizationPageBeanId) ;
		authorizationPageBeanId.setStrMessage("The win bid was directed to pay ");
		response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId);
	}

	/**
	 * action=cancel_action: cancels the caller's own bid.
	 */
	@PostActionRequestMapping(action = "cancel_action")
	public void cancelAuctionBid(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		if (authorizationPageBeanId.getRoleId() == SiteRole.GUEST_ROLE_ID) {
			authorizationPageBeanId.setStrMessage("Please login to access the option.");
			response.sendRedirect("Authorization.jsp?Login=");
			return ;
		}

		//publisherBeanId.setSite_id(authorizationPageBeanId.getSite_id()); // init must rewrite it . it does not make any sense
		String positionId = request.getParameter("position");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		// FIX: any logged-in user could cancel anybody's bid; only the bidder
		// or the seller may.
		if (productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getIntUserID(), false).length() == 0
				&& productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(),
						authorizationPageBeanId.getIntUserID(), true).length() == 0) {
			authorizationPageBeanId.setStrMessage("This bid is not yours.");
			response.sendRedirect("ActionBids.jsp");
			return;
		}
		String cancelledProduct = productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getIntUserID(), false);
		boolean byBidder = cancelledProduct.length() > 0;
		if (!byBidder)
			cancelledProduct = productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getIntUserID(), true);
		long bidder = productPostAllFaced.bidUserId(positionId);
		productPostAllFaced.initPage(positionId,  publisherBeanId, authorizationPageBeanId) ;
		productPostAllFaced.updateAuctionBidStatus(publisherBeanId,authorizationPageBeanId,AuctionBidStatus.PRODUCT_AUCTION_BID_CANCELED);
		{
		com.cbsinc.cms.faceds.NotificationsFaced nf = new com.cbsinc.cms.faceds.NotificationsFaced();
			String name = nf.productName(cancelledProduct);
			if (byBidder)
				nf.notify(nf.productSellerId(cancelledProduct), authorizationPageBeanId.getSiteId(), "bid_cancelled",
						"A bid on \"" + name + "\" was cancelled by " + authorizationPageBeanId.getStrLogin(), cancelledProduct, null, null);
			else
				nf.notify(bidder, authorizationPageBeanId.getSiteId(), "bid_cancelled",
						"Your bid on \"" + name + "\" was cancelled by the seller", cancelledProduct, null, null);
		}
		authorizationPageBeanId.setStrMessage("The bid was canceled successfully");
		response.sendRedirect("ActionBids.jsp");

	}

	/**
	 * action=accept_win_bid: seller accepts the highest bid as winner.
	 */
	@PostActionRequestMapping(action = "accept_win_bid")
	public void acceptWinningBid(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		if (authorizationPageBeanId.getRoleId() == SiteRole.GUEST_ROLE_ID) {
			authorizationPageBeanId.setStrMessage("Please login to access the option.");
			response.sendRedirect("Authorization.jsp?Login=");
			return ;
		}

		publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());
		String positionId = request.getParameter("product_id"); // despite the name this is the bid id
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		// FIX: any logged-in user could declare his own bid the winner and mark
		// every other bid as lost; only the product's seller (or site owner)
		// may, and only for a bid that is still submitted.
		if (productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getIntUserID(), true).length() == 0) {
			authorizationPageBeanId.setStrMessage("Only the seller can accept a bid.");
			response.sendRedirect("ActionBids.jsp");
			return;
		}
		String wonProduct = productPostAllFaced.auctionBidOwnedBy(positionId, authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getIntUserID(), true);
		long winner = productPostAllFaced.bidUserId(positionId);
		java.util.List<Long> losers = new com.cbsinc.cms.faceds.NotificationsFaced().liveBidders(wonProduct, winner);
		productPostAllFaced.initPage(positionId,  publisherBeanId, authorizationPageBeanId) ;
		productPostAllFaced.setupAuctionWinningBidStatus(publisherBeanId,authorizationPageBeanId);
		{
		com.cbsinc.cms.faceds.NotificationsFaced nf = new com.cbsinc.cms.faceds.NotificationsFaced();
			String name = nf.productName(wonProduct);
			nf.notify(winner, authorizationPageBeanId.getSiteId(), "bid_won",
					"Your bid on \"" + name + "\" has won. Please proceed to payment", wonProduct, null, null);
			for (Long u : losers)
				nf.notify(u.longValue(), authorizationPageBeanId.getSiteId(), "bid_lost",
						"The auction for \"" + name + "\" is over; your bid did not win", wonProduct, null, null);
			long[] skip = new long[losers.size() + 2];
			skip[0] = authorizationPageBeanId.getIntUserID(); skip[1] = winner;
			for (int k = 0; k < losers.size(); k++) skip[k + 2] = losers.get(k).longValue();
			nf.notifySubscribers(wonProduct, authorizationPageBeanId.getSiteId(), "followed_sold",
					"The auction for \"" + name + "\" that you follow is over: the item was sold", null, skip);
		}
		authorizationPageBeanId.setStrMessage("The bid won successfully");
		response.sendRedirect("ActionBids.jsp");

	}

	/**
	 * action=do_bid: places a bid above the current maximum.
	 */
	@PostActionRequestMapping(action = "do_bid")
	public void placeAuctionBid(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		if (authorizationPageBeanId.getRoleId() == SiteRole.GUEST_ROLE_ID) {
			authorizationPageBeanId.setStrMessage("Please login to access the option.");
			response.sendRedirect("Authorization.jsp?Login=");
			return ;
		}

		//publisherBeanId.setSite_id(authorizationPageBeanId.getSite_id()); // init must rewrite it . it does not make any sense
		String positionId = request.getParameter("product_id");
		String price = request.getParameter("price");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		// FIX: price was unvalidated (a non-number crashed with 500, a negative
		// or lower bid was accepted), the product was not checked to be an
		// auction of this site, and the seller could bid on his own item.
		if (positionId == null || !positionId.matches("\\d{1,18}") || price == null
				|| !price.trim().matches("\\d{1,12}(\\.\\d{1,2})?")) {
			authorizationPageBeanId.setStrMessage("Please enter a valid bid amount.");
			response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + (positionId == null ? "" : positionId.replaceAll("\\D", "")));
			return;
		}
		price = price.trim();
		double currentMax = productPostAllFaced.auctionMaxBidIfBiddable(positionId, authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getIntUserID());
		if (currentMax < 0) {
			authorizationPageBeanId.setStrMessage("Bidding is not available for this item.");
			response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId);
			return;
		}
		if (Double.parseDouble(price) <= currentMax) {
			authorizationPageBeanId.setStrMessage("Your bid must be higher than the current bid " + currentMax);
			response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId);
			return;
		}
		productPostAllFaced.initPage(positionId,  publisherBeanId, authorizationPageBeanId) ;
		if (!positionId.equals(publisherBeanId.getSoftId())) { // initPage found no row: never bid on a stale session id
			publisherBeanId.setSoftId("-1");
			authorizationPageBeanId.setStrMessage("Bidding is not available for this item.");
			response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId);
			return;
		}
		publisherBeanId.setStrSoftCost(price) ;
		java.util.List<Long> outbid = new com.cbsinc.cms.faceds.NotificationsFaced().liveBidders(positionId, authorizationPageBeanId.getIntUserID());
		productPostAllFaced.addAuctionBid(publisherBeanId,authorizationPageBeanId);
		{ // notify the seller and everybody who has just been outbid
		com.cbsinc.cms.faceds.NotificationsFaced nf = new com.cbsinc.cms.faceds.NotificationsFaced();
			String name = nf.productName(positionId);
			nf.notify(nf.productSellerId(positionId), authorizationPageBeanId.getSiteId(), "bid",
					"New bid " + price + " on \"" + name + "\" from " + authorizationPageBeanId.getStrLogin(), positionId, null, price);
			for (Long u : outbid)
				nf.notify(u.longValue(), authorizationPageBeanId.getSiteId(), "outbid",
						"You have been outbid on \"" + name + "\": the bid is now " + price, positionId, null, price);
			long[] skip = new long[outbid.size() + 2];
			skip[0] = authorizationPageBeanId.getIntUserID(); skip[1] = nf.productSellerId(positionId);
			for (int k = 0; k < outbid.size(); k++) skip[k + 2] = outbid.get(k).longValue();
			nf.notifySubscribers(positionId, authorizationPageBeanId.getSiteId(), "followed_bid",
					"New bid " + price + " on \"" + name + "\" that you follow", price, skip);
		}

		productPostAllFaced.subscribe(positionId,authorizationPageBeanId);

		authorizationPageBeanId.setStrMessage("The bid was submitted successfully");
		response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + positionId + "&bid=" + price);

	}

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		CatalogListBean catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		CatalogEditBean catalogEditBeanId = (CatalogEditBean) session.getAttribute("catalogEditBeanId");
		CatalogAddBean catalogAddBeanId = (CatalogAddBean) session.getAttribute("catalogAddBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		// FIX: action() ends with an admin-only check that redirects but could not stop
		// the caller; the legacy save/add/edit branches below used to run anyway.
		if (response.isCommitted())
			return;

		readDisplayFlags(request, publisherBeanId);

		if (request.getParameter("action") != null) {
			publisherBeanId.setAction(request.getParameter("action"));
			if (addCatalog(request, response, publisherBeanId, catalogAddBeanId, authorizationPageBeanId))
				return;
			if (editCatalog(request, response, publisherBeanId, catalogListBeanId, catalogEditBeanId,
					authorizationPageBeanId))
				return;
			if (saveCard(request, response, publisherBeanId, authorizationPageBeanId, productPostAllFaced))
				return;
		} else
			publisherBeanId.setAction("");
//				 end novigator action ---

	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		CatalogListBean catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		CatalogEditBean catalogEditBeanId = (CatalogEditBean) session.getAttribute("catalogEditBeanId");
		CatalogAddBean catalogAddBeanId = (CatalogAddBean) session.getAttribute("catalogAddBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();

		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		// FIX: action() ends with an admin-only check that redirects but could not stop
		// the caller; the legacy save/add/edit branches below used to run anyway.
		if (response.isCommitted())
			return;

		productPostAllFaced.initPage(request.getParameter("product_id"), publisherBeanId, authorizationPageBeanId);

		if (productPostAllFaced.isLimmitPostedMessages(authorizationPageBeanId, false)
				&& publisherBeanId.getSoftId().compareTo("-1") == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("global_has_limmit_forsite"));
			response.sendRedirect("PostManager.jsp");
			return;
		}

		loadCriteriaLabels(publisherBeanId, productPostAllFaced);
		loadCriteriaLists(publisherBeanId, productPostAllFaced);

		if (request.getParameter("action") != null) {
			publisherBeanId.setAction(request.getParameter("action"));
			if (addCatalog(request, response, publisherBeanId, catalogAddBeanId, authorizationPageBeanId))
				return;
			if (editCatalogOnGet(request, response, publisherBeanId, catalogListBeanId, catalogEditBeanId,
					authorizationPageBeanId))
				return;
		} else
			publisherBeanId.setAction("");

		boolean jsfAdmin = false;
		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		String jsfAdminKey = authorizationPageFaced.getResourcesCmsSettings().getString("jsf_admin");
		if (jsfAdminKey == null || jsfAdminKey.equals(""))
			jsfAdmin = false;
		jsfAdminKey = jsfAdminKey.trim();
		jsfAdmin = jsfAdminKey.equals("true");
		publisherBeanId.setNameOfPage("PostProductBidAction.jsp");
		if (jsfAdmin)
			response.sendRedirect("publisher/index.hrml");
	}

	/** action=add: create a catalogue folder for the cards. */
	private boolean addCatalog(HttpServletRequest request, HttpServletResponse response,
			PublisherBean publisherBeanId, CatalogAddBean catalogAddBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (publisherBeanId.getAction().compareTo("add") == 0) {

			if (request.getParameter("name") != null) {
				catalogAddBeanId.setName(request.getParameter("name"));
			} else
				catalogAddBeanId.setName("");

			if (request.getMethod().toUpperCase().compareTo("POST") == 0) {
				catalogAddBeanId.addCatalog(authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("PostProductBidAction.jsp");
				return true;
			}
		}
		return false;
	}

	/** action=edit: rename / re-image / move a catalogue folder. */
	private boolean editCatalog(HttpServletRequest request, HttpServletResponse response,
			PublisherBean publisherBeanId, CatalogListBean catalogListBeanId,
			CatalogEditBean catalogEditBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (publisherBeanId.getAction().compareTo("edit") == 0) {

			if (request.getParameter("row") != null) {
				int index = catalogListBeanId.stringToInt(request.getParameter("row"));
				catalogEditBeanId.setIndxSelect(index);
			}
			if (request.getParameter("name") != null) {
				catalogEditBeanId.setName(request.getParameter("name"));
			}

			if (request.getParameter("catalogImageId") != null) {
				long catalogImageId = catalogListBeanId.stringToLong(request.getParameter("catalogImageId"));
				catalogEditBeanId.setCatalogImageId(catalogImageId);
			}

			if (request.getParameter("catalog_id") != null) {
				authorizationPageBeanId.setCatalogId(request.getParameter("catalog_id"));
			}

			if (request.getMethod().toUpperCase().compareTo("POST") == 0) {
				catalogEditBeanId.editCatalog(authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("PostProductBidAction.jsp");
				return true;
			}

		}
		return false;
	}

	/** action=save: insert a new card or update the edited one. */
	private boolean saveCard(HttpServletRequest request, HttpServletResponse response,
			PublisherBean publisherBeanId, AuthorizationPageBean authorizationPageBeanId,
			ProductPostAllFaced productPostAllFaced) throws Exception {
		if (publisherBeanId.getAction().compareTo("save") == 0) {

			if (request.getParameter("bigimage_id") == null) {
				publisherBeanId.setBigimageId("-1");
			}
			if (request.getParameter("image_id") == null) {
				publisherBeanId.setImageId("-1");
			}

			publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());

			productPostAllFaced.addAuctionBid(publisherBeanId, authorizationPageBeanId);

			publisherBeanId.setAction("");
			response.sendRedirect("Productlist.jsp?offset=" + 0 );

		}
		return false;
	}

	/** action=edit: rename / re-image / move a catalogue folder. (GET variant) */
	private boolean editCatalogOnGet(HttpServletRequest request, HttpServletResponse response,
			PublisherBean publisherBeanId, CatalogListBean catalogListBeanId,
			CatalogEditBean catalogEditBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (publisherBeanId.getAction().compareTo("edit") == 0) {

			if (request.getParameter("row") != null) {
				int index = catalogListBeanId.stringToInt(request.getParameter("row"));
				catalogEditBeanId.setIndxSelect(index);
			}
			if (request.getParameter("name") != null) {
				catalogEditBeanId.setName(request.getParameter("name"));
			}

			if (request.getParameter("catalog_id") != null) {
				authorizationPageBeanId.setCatalogId(request.getParameter("catalog_id"));
			}

			if (request.getMethod().toUpperCase().compareTo("POST") == 0) {
				catalogEditBeanId.editCatalog(authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("PostProductBidAction.jsp");
				return true;
			}

		}
		return false;
	}

	/** Card display checkboxes: ratings, review block, offers, auction. */
	private void readDisplayFlags(HttpServletRequest request, PublisherBean publisherBeanId) throws Exception {
		if (request.getParameter("show_rating1") != null) {
			publisherBeanId.setStrShowRatimg1Checked("CHECKED");
			publisherBeanId.setStrShowRatimg1("true");
		} else {
			publisherBeanId.setStrShowRatimg1Checked("");
			publisherBeanId.setStrShowRatimg1("false");
		}

		if (request.getParameter("show_blog") != null) {
			publisherBeanId.setShowForumChecked("CHECKED");
			publisherBeanId.setStrShowForum("true");
		} else {
			publisherBeanId.setShowForumChecked("");
			publisherBeanId.setStrShowForum("false");
		}
	}

	/** Catalogue navigator: parent_id / row / del / offset. */
	private void readCatalogNavigation(HttpServletRequest request, CatalogListBean catalogListBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("parent_id") != null) {
			authorizationPageBeanId.setCatalogParentId(request.getParameter("parent_id"));
		}

		if (request.getParameter("row") != null) {
			int index = catalogListBeanId.stringToInt(request.getParameter("row"));
			catalogListBeanId.setIndxSelect(index);
		}
		if (request.getParameter("del") != null) {
			int index = catalogListBeanId.stringToInt(request.getParameter("del"));
			String catalogId = catalogListBeanId.rows[index][0];
			if (catalogId != null)
				catalogListBeanId.delete(catalogId, authorizationPageBeanId);
			request.setAttribute("del", null);
		}

		if (request.getParameter("offset") != null) {
			catalogListBeanId.setOffset(catalogListBeanId.stringToInt(request.getParameter("offset")));
		} else
			catalogListBeanId.setOffset(0);
	}

	/** creteria1_id .. creteria10_id chosen on the form. */
	private void readCriteria(HttpServletRequest request, PublisherBean publisherBeanId) throws Exception {
		if (request.getParameter("creteria1_id") != null)
			publisherBeanId.setCreteria1Id(request.getParameter("creteria1_id"));
		if (request.getParameter("creteria2_id") != null)
			publisherBeanId.setCreteria2Id(request.getParameter("creteria2_id"));
		if (request.getParameter("creteria3_id") != null)
			publisherBeanId.setCreteria3Id(request.getParameter("creteria3_id"));
		if (request.getParameter("creteria4_id") != null)
			publisherBeanId.setCreteria4Id(request.getParameter("creteria4_id"));
		if (request.getParameter("creteria5_id") != null)
			publisherBeanId.setCreteria5Id(request.getParameter("creteria5_id"));
		if (request.getParameter("creteria6_id") != null)
			publisherBeanId.setCreteria6Id(request.getParameter("creteria6_id"));
		if (request.getParameter("creteria7_id") != null)
			publisherBeanId.setCreteria7Id(request.getParameter("creteria7_id"));
		if (request.getParameter("creteria8_id") != null)
			publisherBeanId.setCreteria8Id(request.getParameter("creteria8_id"));
		if (request.getParameter("creteria9_id") != null)
			publisherBeanId.setCreteria9Id(request.getParameter("creteria9_id"));
		if (request.getParameter("creteria10_id") != null)
			publisherBeanId.setCreteria10Id(request.getParameter("creteria10_id"));
	}

	/** All card fields posted by the form: names, catalogue, type, price, currency, descriptions, images, files. */
	private void readCardFields(HttpServletRequest request, PublisherBean publisherBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		String softname = request.getParameter("softname");
		if (softname != null) {
			publisherBeanId.setStrSoftName(softname);
		}

		String softname2 = request.getParameter("softname2");
		if (softname2 != null) {
			publisherBeanId.setStrSoftName2(softname2);
		}

		String jspUrl = request.getParameter("jsp_url");
		if (jspUrl != null) {
			publisherBeanId.setJspUrl(jspUrl);
		}

		String catalogId = request.getParameter("catalog_id");
		if (catalogId != null) {
			authorizationPageBeanId.setCatalogId(catalogId);
		}

		if (request.getParameter("type_id") != null) {
			publisherBeanId.setTypeId(request.getParameter("type_id"));
		}

		String softcost = request.getParameter("softcost");
		if (softcost != null) {
			publisherBeanId.setStrSoftCost(softcost);
		}

		String currencyId = request.getParameter("currency_id");
		if (currencyId != null) {
			publisherBeanId.setStrCurrency(currencyId);
		}

		String description = request.getParameter("description");
		if (description != null) {
			publisherBeanId.setStrSoftDescription(description);
		}

		String fulldescription = request.getParameter("fulldescription");
		if (fulldescription != null) {
			publisherBeanId.setProductFulldescription(fulldescription);
		}

		String imagename = request.getParameter("imagename");
		if (imagename != null) {
			publisherBeanId.setImgname(imagename);
		}

		String imageId = request.getParameter("image_id");
		if (imageId != null) {
			publisherBeanId.setImageId(imageId);
		}

		if (request.getParameter("portlettype_id") != null) {
			publisherBeanId.setPortlettypeId(request.getParameter("portlettype_id"));
		}

		String fileId = request.getParameter("file_id");
		if (fileId != null) {
			publisherBeanId.setFileId(fileId);
		}

		String filename = request.getParameter("filename");
		if (filename != null) {
			publisherBeanId.setFilename(filename);
		}

		String bigimagename = request.getParameter("bigimagename");
		if (bigimagename != null) {
			publisherBeanId.setBigimgname(bigimagename);
		}

		String bigimageId = request.getParameter("bigimage_id");
		if (bigimageId != null) {
			publisherBeanId.setBigimageId(bigimageId);
		}

		if (request.getParameter("salelogic_id") != null)
			publisherBeanId.setPrognameId(request.getParameter("salelogic_id"));
	}

	/** Labels of the ten criteria for this site. */
	private void loadCriteriaLabels(PublisherBean publisherBeanId, ProductPostAllFaced productPostAllFaced) throws Exception {
		publisherBeanId.setCriteria1Label(
				productPostAllFaced.getOneLabel("select  label   from creteria1   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria2Label(
				productPostAllFaced.getOneLabel("select  label   from creteria2   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria3Label(
				productPostAllFaced.getOneLabel("select  label   from creteria3   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria4Label(
				productPostAllFaced.getOneLabel("select  label   from creteria4   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria5Label(
				productPostAllFaced.getOneLabel("select  label   from creteria5   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria6Label(
				productPostAllFaced.getOneLabel("select  label   from creteria6   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria7Label(
				productPostAllFaced.getOneLabel("select  label   from creteria7   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria8Label(
				productPostAllFaced.getOneLabel("select  label   from creteria8   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria9Label(
				productPostAllFaced.getOneLabel("select  label   from creteria9   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setCriteria10Label(
				productPostAllFaced.getOneLabel("select  label   from creteria10   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));

	}

	/** The ten dependent criteria dropdowns (each list is filtered by the previous choice). */
	private void loadCriteriaLists(PublisherBean publisherBeanId, ProductPostAllFaced productPostAllFaced) throws Exception {
		publisherBeanId.setSelectCreteria1Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria1_id",
				publisherBeanId.getCreteria1Id(), notselected,
				"select creteria1_id , name   from creteria1   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)));
		publisherBeanId.setSelectCreteria2Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria2_id",
				publisherBeanId.getCreteria2Id(), notselected,
				"select creteria2_id , name   from creteria2   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria1Id() + " ) "));
		publisherBeanId.setSelectCreteria3Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria3_id",
				publisherBeanId.getCreteria3Id(), notselected,
				"select creteria3_id , name   from creteria3   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria2Id() + " ) "));
		publisherBeanId.setSelectCreteria4Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria4_id",
				publisherBeanId.getCreteria4Id(), notselected,
				"select creteria4_id , name   from creteria4   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria3Id() + " ) "));
		publisherBeanId.setSelectCreteria5Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria5_id",
				publisherBeanId.getCreteria5Id(), notselected,
				"select creteria5_id , name   from creteria5   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria4Id() + " ) "));
		publisherBeanId.setSelectCreteria6Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria6_id",
				publisherBeanId.getCreteria6Id(), notselected,
				"select creteria6_id , name   from creteria6   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria5Id() + " ) "));
		publisherBeanId.setSelectCreteria7Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria7_id",
				publisherBeanId.getCreteria7Id(), notselected,
				"select creteria7_id , name   from creteria7   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria6Id() + " ) "));
		publisherBeanId.setSelectCreteria8Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria8_id",
				publisherBeanId.getCreteria8Id(), notselected,
				"select creteria8_id , name   from creteria8   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria7Id() + " ) "));
		publisherBeanId.setSelectCreteria9Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria9_id",
				publisherBeanId.getCreteria9Id(), notselected,
				"select creteria9_id , name   from creteria9   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria8Id() + " ) "));
		publisherBeanId.setSelectCreteria10Id(productPostAllFaced.getComboBoxAutoSubmitLocale("creteria10_id",
				publisherBeanId.getCreteria10Id(), notselected,
				"select creteria10_id , name   from creteria10   where  active = true "
						+ publisherBeanId.getPartCriteria(publisherBeanId.getSiteId(), isCriteriaByCatalog)
						+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria9Id() + " ) "));
	}

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		CatalogListBean catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		if (publisherBeanId == null || catalogListBeanId == null || authorizationPageBeanId == null
				|| productPostAllFaced == null)
			return;

		if (notselected.length() == 0)
			notselected = authorizationPageBeanId.getLocalization(servletContext).getString("notselected");

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());
//		 Start Novigator ---
		readCatalogNavigation(request, catalogListBeanId, authorizationPageBeanId);

//		 End Novigator ---

//		 start novigator action ---

		readCriteria(request, publisherBeanId);

		readCardFields(request, publisherBeanId, authorizationPageBeanId);

		if (authorizationPageBeanId.getIntUserID() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("session_time_out"));
			response.sendRedirect("Authorization.jsp");
		} else
			publisherBeanId.setUserId("" + authorizationPageBeanId.getIntUserID());

		if (authorizationPageBeanId.getRoleId() != 2) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("post_message_notaccess_admin"));
			response.sendRedirect("Authorization.jsp");
		}

	}

}
