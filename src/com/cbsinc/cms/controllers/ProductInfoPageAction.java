package com.cbsinc.cms.controllers;

import com.cbsinc.cms.utils.Validation;
import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CatalogListBean;
import com.cbsinc.cms.ItemDescriptionBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.ProductInfoFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;

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

//Use a ProductInfoAction

/**
 * Formerly PolicyAction. Controller for Policy.jsp, the older product
 * information page ("policy" == product info in this code base). The JSP
 * file, its XSL template (policy.xsl) and the request parameter
 * policy_byproductid keep their names because templates and links depend on
 * them.
 */
@Deprecated
@PageController(jspName = "Policy.jsp")
public class ProductInfoPageAction implements IAction {

	transient static private Logger log = Logger.getLogger(ProductInfoPageAction.class);
	private ProductInfoFaced productInfoFaced = null;
	private ProductlistFaced productlistFaced = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		doGet(request, response, servletContext);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		AuthorizationPageBean authorizationPageBeanId;
		ItemDescriptionBean itemDescriptionBeanId = null;
		HttpSession session;
		boolean isInternet = true;

		if (productInfoFaced == null)
			productInfoFaced = ServiceLocator.getInstance().getProductInfoFaced();
		if (productlistFaced == null)
			productlistFaced = ServiceLocator.getInstance().getProductlistFaced();
		itemDescriptionBeanId = new ItemDescriptionBean();
		session = request.getSession();

		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		request.setAttribute("itemDescriptionBeanId", itemDescriptionBeanId);

		if (request.getRemoteAddr().startsWith("192."))
			isInternet = false;
		if (request.getRemoteAddr().startsWith("10."))
			isInternet = false;

		if (authorizationPageBeanId == null || itemDescriptionBeanId == null)
			return;

		if (handleRatingOnly(request, response, itemDescriptionBeanId, authorizationPageBeanId))
			return;

		request.setCharacterEncoding("UTF-8");

		itemDescriptionBeanId.internet = isInternet;

		if (request.getParameter("policy_byproductid") != null
				&& Validation.isNonNegativeInteger(request.getParameter("policy_byproductid"))) {
			showProductPage(request, itemDescriptionBeanId, authorizationPageBeanId);
		} else if (request.getParameter("page") != null && request.getParameter("page").compareTo("about") == 0) {
			showAboutPage(request, itemDescriptionBeanId, authorizationPageBeanId);
		} else if (request.getParameter("page") != null && request.getParameter("page").compareTo("pay") == 0) {
			showPayPage(request, itemDescriptionBeanId, authorizationPageBeanId);
		}

		loadLinkedCards(servletContext, itemDescriptionBeanId, authorizationPageBeanId);
	}

	/** Rating click without a product on the URL: store the vote for the last viewed product and redirect; otherwise go back to the catalogue. */
	private boolean handleRatingOnly(HttpServletRequest request, HttpServletResponse response,
			ItemDescriptionBean itemDescriptionBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("policy_byproductid") == null && request.getParameter("page") == null) {

			if (request.getParameter("rate") != null && Validation.isNonNegativeInteger(request.getParameter("rate"))) {
				itemDescriptionBeanId.setProductId(Long.toString(authorizationPageBeanId.getLastProductId()));
				int rate = Integer.parseInt(request.getParameter("rate"));
				productInfoFaced.setRatring1(rate, itemDescriptionBeanId.getProductId());
				response.sendRedirect("Policy.jsp?policy_byproductid=" + itemDescriptionBeanId.getProductId());
				return true;
			}

			response.sendRedirect("Productlist.jsp?catalog_id=-2");
			return true;
		}
		return false;
	}

	/** ?policy_byproductid=N: load the product card, current max bid / offer amounts, subscription flag, rating and balance; count the view. */
	private void showProductPage(HttpServletRequest request, ItemDescriptionBean itemDescriptionBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		productInfoFaced.setProductInfoBean(authorizationPageBeanId.getIntUserID(),
				request.getParameter("policy_byproductid"), itemDescriptionBeanId);
		itemDescriptionBeanId
				.setBackUrl("Policy.jsp?policy_byproductid=" + itemDescriptionBeanId.getParentProductId());
		itemDescriptionBeanId.setTypePage("policy_byproductid");
		itemDescriptionBeanId.setIntUserID(authorizationPageBeanId.getIntUserID());
		authorizationPageBeanId.setLastProductId(Long.parseLong(itemDescriptionBeanId.getParentProductId()));

		if (request.getParameter("rate") != null && Validation.isNonNegativeInteger(request.getParameter("rate"))) {
			int rate = Integer.parseInt(request.getParameter("rate"));
			productInfoFaced.setRatring1(rate, itemDescriptionBeanId.getProductId());
		}

		itemDescriptionBeanId.setRating1Xml(productInfoFaced.getRatring1XML(itemDescriptionBeanId.getProductId()));
		itemDescriptionBeanId.setBalans("" + productInfoFaced.getBalans(authorizationPageBeanId.getIntUserID()));

		try {
			if (itemDescriptionBeanId.isFirstOpen()) {

				productInfoFaced.setPaymentInfoPage(itemDescriptionBeanId, authorizationPageBeanId,
						request.getRemoteAddr());
				itemDescriptionBeanId.setFirstOpen(false);
			} else
				productInfoFaced.incrementShowPageStatistics(itemDescriptionBeanId);
		} catch (Exception ex) {
			log.error("Billing is not working ", ex);
		}

	}

	/** ?page=about: the site "about" card. */
	private void showAboutPage(HttpServletRequest request, ItemDescriptionBean itemDescriptionBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		productInfoFaced.setProductInfoBeanForAboutPage(authorizationPageBeanId.getSiteId(), itemDescriptionBeanId);// (authorizationPageBeanId.getIntUserID(),
		// request.getParameter("policy_byproductid")
		// ,
		// productInfoBeanId)
		// ;
		authorizationPageBeanId.setLastProductId(Long.parseLong(itemDescriptionBeanId.getParentProductId()));
		itemDescriptionBeanId.setBackUrl("Productlist.jsp");
		itemDescriptionBeanId.setTypePage("about");
		itemDescriptionBeanId.setIntUserID(authorizationPageBeanId.getIntUserID());
		itemDescriptionBeanId.setRating1Xml(productInfoFaced.getRatring1XML(itemDescriptionBeanId.getProductId()));
		itemDescriptionBeanId.setBalans("" + productInfoFaced.getBalans(authorizationPageBeanId.getIntUserID()));

		if (itemDescriptionBeanId.isFirstOpen()) {
			productInfoFaced.setPaymentInfoPage(itemDescriptionBeanId, authorizationPageBeanId,
					request.getRemoteAddr());
			itemDescriptionBeanId.setFirstOpen(false);
		} else
			productInfoFaced.incrementShowPageStatistics(itemDescriptionBeanId);
	}

	/** ?page=pay: the payment-info card. */
	private void showPayPage(HttpServletRequest request, ItemDescriptionBean itemDescriptionBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		productInfoFaced.setProductInfoBeanForPayPageInfo(authorizationPageBeanId.getSiteId(), itemDescriptionBeanId);// (authorizationPageBeanId.getIntUserID(),
		// request.getParameter("policy_byproductid")
		// ,
		// productInfoBeanId)
		// ;
		authorizationPageBeanId.setLastProductId(Long.parseLong(itemDescriptionBeanId.getParentProductId()));
		itemDescriptionBeanId.setBackUrl("Productlist.jsp");
		itemDescriptionBeanId.setTypePage("pay");
		itemDescriptionBeanId.setIntUserID(authorizationPageBeanId.getIntUserID());
		itemDescriptionBeanId.setRating1Xml(productInfoFaced.getRatring1XML(itemDescriptionBeanId.getProductId()));
		itemDescriptionBeanId.setBalans("" + productInfoFaced.getBalans(authorizationPageBeanId.getIntUserID()));

		if (itemDescriptionBeanId.isFirstOpen()) {
			productInfoFaced.setPaymentInfoPage(itemDescriptionBeanId, authorizationPageBeanId,
					request.getRemoteAddr());
			itemDescriptionBeanId.setFirstOpen(false);
		} else
			productInfoFaced.incrementShowPageStatistics(itemDescriptionBeanId);

	}

	/** Catalogue tree / menu, currencies and every linked block: column one/two, files, description tabs, reviews, new arrivals, news. */
	private void loadLinkedCards(ServletContext servletContext, ItemDescriptionBean itemDescriptionBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		itemDescriptionBeanId.setSelectCatalogXMLUrlPath(
				(new CatalogListBean(servletContext)).getCatalogXMLUrlPath("Productlist.jsp?catalog_id", "parent",
						authorizationPageBeanId.getCatalogId(), authorizationPageBeanId));
		itemDescriptionBeanId.setSelectTreeCatalog(productlistFaced.getTreeXMLDBList("Productlist.jsp?catalog_id",
				"catalog", authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and parent_id = "
						+ authorizationPageBeanId.getCatalogParentId(),
				"select catalog_id , lable   from catalog   where  active = true and parent_id = "
						+ authorizationPageBeanId.getCatalogId()));
		itemDescriptionBeanId.setSelectCurrencies(
				productInfoFaced.getXMLDBList("ProductInfo.jsp", "currencies", itemDescriptionBeanId.getCurrencyCd(),
						"SELECT currency_cd , currency_desc  FROM currency  WHERE active = true"));
		itemDescriptionBeanId.columnOne = productlistFaced.getProductInfoColumnOne(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				itemDescriptionBeanId.getParentProductId(), itemDescriptionBeanId);
		itemDescriptionBeanId.columnTwo = productlistFaced.getProductInfoColumnTwo(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				itemDescriptionBeanId.getParentProductId(), itemDescriptionBeanId);
		itemDescriptionBeanId.attachedFiles = productlistFaced.getProductInfoAttchedFiles(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				itemDescriptionBeanId.getParentProductId(), itemDescriptionBeanId);
		itemDescriptionBeanId.descriptionTab = productlistFaced.getProductInfoDescriptionTabs(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				itemDescriptionBeanId.getParentProductId(), itemDescriptionBeanId);
		itemDescriptionBeanId.reviewMessages = productlistFaced.getProductInfoReviewMessages(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				itemDescriptionBeanId.getParentProductId(), itemDescriptionBeanId);
		// if( productInfoBeanId.getPortlettype_id() != Layout.PORTLET_TYPE_BOTTOM )
		itemDescriptionBeanId.newArrivalItems = productlistFaced.getNewArrivalItems(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId);
		itemDescriptionBeanId.footerLinksList = productlistFaced.getFooterLinksList(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId);

		itemDescriptionBeanId.setSelectMenuCatalog(productlistFaced.getMenuXMLDBList("Productlist.jsp?catalog_id",
				"menu", authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable , parent_id  from catalog   where  active = true and parent_id = -2 and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and lang_id = "
						+ authorizationPageBeanId.getLangId()
						+ " or parent_id in (select catalog_id   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + "  and parent_id = -2 )"));

	}


}
