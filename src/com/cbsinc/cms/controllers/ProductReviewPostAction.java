package com.cbsinc.cms.controllers;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

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

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of ProductReviewPost.jsp: posts a product review; a review may only update the caller's own review (pass 7).
 */
@PageController(jspName = "ProductReviewPost.jsp")
public class ProductReviewPostAction implements IAction {

	ProductPostAllFaced productPostAllFaced;

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		HttpSession session = request.getSession();
		String genCode = (String) request.getSession().getAttribute("gen_number");
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		if (publisherBeanId == null || authorizationPageBeanId == null || genCode == null)
			return;

		String path = ((HttpServletRequest) request).getRequestURI();
		if (request.getParameter("gen_number") != null) {
			String val1 = request.getParameter("gen_number").trim();

			if (!val1.equals(genCode.trim())) {
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("wrong_gen_number"));
				response.sendRedirect(path);
				return;
			}
		} else {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("wrong_gen_number"));
			response.sendRedirect(path);
			return;
			// request.
		}

		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		// FIX: action() redirects users without access but could not stop the caller;
		// the insert/update below used to run anyway.
		if (response.isCommitted())
			return;

		if (request.getParameter("bigimage_id") == null) {
			publisherBeanId.setBigimageId("-1");
		}
		if (request.getParameter("image_id") == null) {
			publisherBeanId.setImageId("-1");
		}

		publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());
		// FIX: a review is always a review, whatever portlettype_id the form sent
		publisherBeanId.setPortlettypeId("" + Layout.REVIEW_MESSAGES);
		// FIX: the card comes from the form (parent_id), not only from session
		// state, and must be a real, active card of this site with the review
		// block enabled.
		String candidate = request.getParameter("parent_id");
		if (candidate == null || !candidate.matches("\\d{1,18}"))
			candidate = "" + authorizationPageBeanId.getLastProductId();
		String parentId = productPostAllFaced.resolveReviewParent(candidate, authorizationPageBeanId.getSiteId());
		if (parentId.length() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("post_forum_notaccess"));
			response.sendRedirect("ProductInfo.jsp");
			return;
		}
		// FIX: the session PublisherBean keeps the soft_id of whatever card was
		// last opened for editing; posting a review used to UPDATE that card,
		// turning it into a review of another product. Only an existing review
		// of this very card, written by this user, may be updated.
		if (publisherBeanId.getSoftId().compareTo("-1") != 0 && !productPostAllFaced.isOwnReview(
				publisherBeanId.getSoftId(), parentId, authorizationPageBeanId.getIntUserID()))
			publisherBeanId.setSoftId("-1");
		if (publisherBeanId.getSoftId().compareTo("-1") == 0)
			productPostAllFaced.insertRowWithParent(parentId, publisherBeanId, authorizationPageBeanId);
		else
			productPostAllFaced.updateRowWithParent(parentId, publisherBeanId, authorizationPageBeanId);
		publisherBeanId.setSoftId("-1");
		response.sendRedirect("ProductInfo.jsp?policy_byproductid=" + parentId);
	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		// FIX: action() redirects users without access but could not stop the caller;
		// the insert/update below used to run anyway.
		if (response.isCommitted())
			return;
		// FIX: opening the review form without product_id must start a NEW card;
		// initPage(null) finds no row and left the previous soft_id in the session.
		if (request.getParameter("product_id") == null || !request.getParameter("product_id").matches("\\d{1,18}")) {
			publisherBeanId.setSoftId("-1");
			publisherBeanId.setStrSoftName("");
			publisherBeanId.setStrSoftDescription("");
			publisherBeanId.setProductFulldescription("");
		} else
			productPostAllFaced.initPage(request.getParameter("product_id"), publisherBeanId, authorizationPageBeanId);
		String parent = request.getParameter("parent_id");
		if (parent != null && parent.matches("\\d{1,18}"))
			authorizationPageBeanId.setLastProductId(Long.parseLong(parent));

		if (authorizationPageBeanId.getRoleId() == 2) {

			boolean jsfAdmin = false;
			AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
			String jsfAdminKey = authorizationPageFaced.getResourcesCmsSettings().getString("jsf_admin");
			if (jsfAdminKey == null || jsfAdminKey.equals(""))
				jsfAdmin = false;
			jsfAdminKey = jsfAdminKey.trim();
			jsfAdmin = jsfAdminKey.equals("true");
			publisherBeanId.setNameOfPage("ProductReviewPost.jsp");
			if (jsfAdmin)
				response.sendRedirect("publisher/index.hrml");

		}
	}

	/** All card fields posted by the form: names, catalogue, type, price, currency, descriptions, images, files. */
	private void readCardFields(HttpServletRequest request, PublisherBean publisherBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		String softname = request.getParameter("softname");
		if (softname != null) {
			publisherBeanId.setStrSoftName(softname);
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

		String filename = request.getParameter("filename");
		if (filename != null) {
			publisherBeanId.setSample(filename);
		} else {
			publisherBeanId.setSample("");
		}
		filename = null;

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

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;
		PublisherBean publisherBeanId = null;
		session = request.getSession();

		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		if (publisherBeanId == null || authorizationPageBeanId == null || productPostAllFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		readCardFields(request, publisherBeanId, authorizationPageBeanId);
		// post_forum_notaccess
		if (authorizationPageBeanId.getIntUserID() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("session_time_out"));
			response.sendRedirect("ProductInfo.jsp");
			return;
		} else
			publisherBeanId.setUserId("" + authorizationPageBeanId.getIntUserID());

		if (authorizationPageBeanId.getRoleId() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("post_forum_notaccess"));
			response.sendRedirect("ProductInfo.jsp");
			return;
		}

		if (authorizationPageBeanId.getStrLogin().compareTo("user") == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("post_forum_notaccess"));
			response.sendRedirect("ProductInfo.jsp");
			return;
		}

	}

}
