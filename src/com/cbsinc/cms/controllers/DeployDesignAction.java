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
import java.io.File;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.ProductlistBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductInfoFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of DeployDesign.jsp: creates a new shop site from a design template and mails the owner.
 */
@PageController(jspName = "DeployDesign.jsp")
public class DeployDesignAction implements IAction {

	private AuthorizationPageFaced authorizationPageFaced;

	public DeployDesignAction() {
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
		ProductlistBean productlistBeanId;
		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;
		boolean isCriteriaByCatalog = false;
		String notselected = "";
		boolean isInternet = true;
		ServiceLocator.getInstance().getProductPostAllFaced();
		authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

		isCriteriaByCatalog = authorizationPageFaced.getResourcesCmsSettings().getString("is_criteria_by_catalog")
				.equals("true");

		if (request.getRemoteAddr().startsWith("192."))
			isInternet = false;
		if (request.getRemoteAddr().startsWith("10."))
			isInternet = false;
		session = request.getSession();
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		if (notselected.length() == 0)
			notselected = authorizationPageBeanId.getLocalization(servletContext).getString("notselected");

		ProductlistFaced productlistFaced = ServiceLocator.getInstance().getProductlistFaced();
		ProductInfoFaced productInfoFaced = ServiceLocator.getInstance().getProductInfoFaced();
		productlistBeanId = new ProductlistBean();
		request.setAttribute("productlistBeanId", productlistBeanId);

		if (productInfoFaced == null || productlistBeanId == null || authorizationPageBeanId == null
				|| authorizationPageFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");

		productlistBeanId.isInternet = isInternet;
		authorizationPageBeanId.setBalance(ServiceLocator.getInstance().getAuthorizationPageFaced()
				.getStrBalans(authorizationPageBeanId.getIntUserID()));

		readSearchParameters(request, productlistBeanId, authorizationPageBeanId);
		applyLocale(request, servletContext, productlistBeanId, authorizationPageBeanId);
		resolveSearchValue(request, productlistBeanId, authorizationPageBeanId);

		if (dispatchAction(request, response, servletContext, session, productlistBeanId,
				authorizationPageBeanId, productlistFaced))
			return;

		loadProductlist(productlistBeanId, authorizationPageBeanId, productlistFaced, isCriteriaByCatalog);
		populateSearchLists(productlistBeanId, authorizationPageBeanId, productlistFaced,
				isCriteriaByCatalog, notselected);
	}



	/** Routes the action form field (and create_site_by_id) to the business methods; true = response already sent. */
	private boolean dispatchAction(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, HttpSession session, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (deletePosition(request, response, servletContext, productlistBeanId, authorizationPageBeanId,
				productlistFaced))
			return true;
		if (upPosition(request, response, servletContext, productlistBeanId, authorizationPageBeanId,
				productlistFaced))
			return true;
		if (colorPosition(request, response, servletContext, productlistBeanId, authorizationPageBeanId,
				productlistFaced))
			return true;
		if (editPosition(request, response, productlistBeanId, authorizationPageBeanId))
			return true;
		if (createSite(request, response, servletContext, productlistBeanId, authorizationPageBeanId))
			return true;
		if (createSiteById(request, response, servletContext, productlistBeanId, authorizationPageBeanId))
			return true;
		if (loginUserSite(request, response, servletContext, session, productlistBeanId, authorizationPageBeanId))
			return true;
		if (logoff(response, session, productlistBeanId, authorizationPageBeanId))
			return true;
		return false;
	}

	/** Copy the search form fields (offset, catalog, dates, cost range, criteria 1-10, search value / mode) into the beans. */
	private void readSearchParameters(HttpServletRequest request, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("offset") != null && Validation.isNonNegativeInteger(request.getParameter("offset"))) {
			productlistBeanId.setOffset(productlistBeanId.stringToInt(request.getParameter("offset")));
			authorizationPageBeanId.setOffsetLastPage(Long.parseLong(request.getParameter("offset")));
		}

		if (request.getParameter("catalog_id") != null && Validation.isNonNegativeInteger(request.getParameter("catalog_id"))) {
			if (authorizationPageBeanId.getCatalogId().compareTo(request.getParameter("catalog_id")) != 0) {
				productlistBeanId.setOffset(0);
			}
			authorizationPageBeanId.setCatalogId(request.getParameter("catalog_id"));
			authorizationPageBeanId.setCatalogParentId(request.getParameter("catalog_id"));

		}
		productlistBeanId.setRoleId(authorizationPageBeanId.getRoleId());

		// Open or closed dialog window
		if (request.getParameter("dialog") != null)
			productlistBeanId.setDialog(request.getParameter("dialog"));
		if (request.getParameter("is_advanced_search_open") != null)
			productlistBeanId.setAdvancedSearchOpen(request.getParameter("is_advanced_search"));
		if (request.getParameter("is_forum_open") != null)
			productlistBeanId.setForumOpen(request.getParameter("is_forum_open"));

		if (request.getParameter("dayfrom_id") != null && Validation.isNonNegativeInteger(request.getParameter("dayfrom_id")))
			authorizationPageBeanId.setDayfromId(Integer.parseInt(request.getParameter("dayfrom_id")));
		if (request.getParameter("mountfrom_id") != null && Validation.isNonNegativeInteger(request.getParameter("mountfrom_id")))
			authorizationPageBeanId.setMountfromId(Integer.parseInt(request.getParameter("mountfrom_id")));
		if (request.getParameter("yearfrom_id") != null && Validation.isNonNegativeInteger(request.getParameter("yearfrom_id")))
			authorizationPageBeanId.setYearfromId(Integer.parseInt(request.getParameter("yearfrom_id")));

		if (request.getParameter("fromcost") != null && Validation.isDecimal(request.getParameter("fromcost")))
			authorizationPageBeanId.setStrFromCost(request.getParameter("fromcost").trim());
		if (request.getParameter("tocost") != null && Validation.isDecimal(request.getParameter("tocost")))
			authorizationPageBeanId.setStrToCost(request.getParameter("tocost").trim());

		if (request.getParameter("dayto_id") != null && Validation.isNonNegativeInteger(request.getParameter("dayto_id")))
			authorizationPageBeanId.setDaytoId(Integer.parseInt(request.getParameter("dayto_id")));
		if (request.getParameter("mountto_id") != null && Validation.isNonNegativeInteger(request.getParameter("mountto_id")))
			authorizationPageBeanId.setMounttoId(Integer.parseInt(request.getParameter("mountto_id")));
		if (request.getParameter("yearto_id") != null && Validation.isNonNegativeInteger(request.getParameter("yearto_id")))
			authorizationPageBeanId.setYeartoId(Integer.parseInt(request.getParameter("yearto_id")));

		if (request.getParameter("action") != null) {
			productlistBeanId.setAction(request.getParameter("action"));
		}
		if (request.getParameter("creteria1_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria1_id")))
			authorizationPageBeanId.setStrCreteria1Id(request.getParameter("creteria1_id"));
		if (request.getParameter("creteria2_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria2_id")))
			authorizationPageBeanId.setStrCreteria2Id(request.getParameter("creteria2_id"));
		if (request.getParameter("creteria3_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria3_id")))
			authorizationPageBeanId.setStrCreteria3Id(request.getParameter("creteria3_id"));
		if (request.getParameter("creteria4_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria4_id")))
			authorizationPageBeanId.setStrCreteria4Id(request.getParameter("creteria4_id"));
		if (request.getParameter("creteria5_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria5_id")))
			authorizationPageBeanId.setStrCreteria5Id(request.getParameter("creteria5_id"));
		if (request.getParameter("creteria6_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria6_id")))
			authorizationPageBeanId.setStrCreteria6Id(request.getParameter("creteria6_id"));
		if (request.getParameter("creteria7_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria7_id")))
			authorizationPageBeanId.setStrCreteria7Id(request.getParameter("creteria7_id"));
		if (request.getParameter("creteria8_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria8_id")))
			authorizationPageBeanId.setStrCreteria8Id(request.getParameter("creteria8_id"));
		if (request.getParameter("creteria9_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria9_id")))
			authorizationPageBeanId.setStrCreteria9Id(request.getParameter("creteria9_id"));
		if (request.getParameter("creteria10_id") != null && Validation.isNonNegativeInteger(request.getParameter("creteria10_id")))
			authorizationPageBeanId.setStrCreteria10Id(request.getParameter("creteria10_id"));
		if (request.getParameter("search_value") != null)
			productlistBeanId.setSearchValueArg(request.getParameter("search_value"));
		if (request.getParameter("searchquery") != null && Validation.isNonNegativeInteger(request.getParameter("searchquery")))
			productlistBeanId.setSearchquery(Integer.parseInt(request.getParameter("searchquery")));
		else if (request.getParameter("offset") == null)
			productlistBeanId.setSearchquery(0);

	}

	/** ?locale= switches the user language when allowed. */
	private void applyLocale(HttpServletRequest request, ServletContext servletContext,
			ProductlistBean productlistBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		String clLocale = request.getParameter("locale");
		if (clLocale != null) {

			if (isAllowLocale(clLocale)) {

				if (authorizationPageBeanId.getLocale().compareTo(clLocale) != 0) {
					productlistBeanId.setOffset(0);
					authorizationPageBeanId.setCatalogId("" + SpecialCatalog.OUTPUT_PAGES_SORT_BY_SOFT_ID);
				}

				authorizationPageBeanId.setLocale(clLocale, servletContext);
			}

		}

	}

	/** Choose the effective search string by search mode: 2 = by first letter, 1 = full text (criteria reset), 0 = none. */
	private void resolveSearchValue(HttpServletRequest request, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (productlistBeanId.getSearchquery() == 2 && request.getParameter("search_char") != null
				&& request.getParameter("search_char").length() > 0) {
			// if new char then start with 0
			if (productlistBeanId.getSearchValueArg().compareTo(request.getParameter("search_char")) != 0) {
				productlistBeanId.setOffset(0);
			}

			productlistBeanId.setSearchValueArg(request.getParameter("search_char"));
		} else if (productlistBeanId.getSearchquery() == 1 && request.getParameter("search_value") != null
				&& request.getParameter("search_value").length() > 0) {
				productlistBeanId.setSearchValueArg(request.getParameter("search_value"));
				authorizationPageBeanId.setStrCreteria1Id("0");
				authorizationPageBeanId.setStrCreteria2Id("0");
				authorizationPageBeanId.setStrCreteria3Id("0");
				authorizationPageBeanId.setStrCreteria4Id("0");
				authorizationPageBeanId.setStrCreteria5Id("0");
				authorizationPageBeanId.setStrCreteria6Id("0");
				authorizationPageBeanId.setStrCreteria7Id("0");
				authorizationPageBeanId.setStrCreteria8Id("0");
				authorizationPageBeanId.setStrCreteria9Id("0");
				authorizationPageBeanId.setStrCreteria10Id("0");
		} else if (productlistBeanId.getSearchquery() == 0) {
			productlistBeanId.setSearchValueArg("");
		}
	}

	/** action=del: the product owner or a site administrator (role 2) removes a product. */
	private boolean deletePosition(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (productlistBeanId.getAction().compareTo("del") == 0) {
			productlistBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
				// response.sendRedirect("Authorization.jsp" );
				servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
				return true;
			}

			if (request.getParameter("product_id") != null && Validation.isNonNegativeInteger(request.getParameter("product_id"))) {

				if (authorizationPageBeanId.getRoleId() != 2) {
					int userId = productlistFaced.getWhoseProduct(request.getParameter("product_id"));
					if (authorizationPageBeanId.getIntUserID() != userId) {
						authorizationPageBeanId.setStrMessage(
								"Access denny  for login " + authorizationPageBeanId + " , You must be owner message");
						// response.sendRedirect("Authorization.jsp" );
						servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
						return true;
					}
				}

				if (authorizationPageBeanId.getSiteId()
						.compareTo(productlistFaced.getSiteByProduct(request.getParameter("product_id"))) == 0)
					productlistFaced.deletePosition(request.getParameter("product_id"));
				else
					authorizationPageBeanId.setStrMessage(
							"Access denny  for login " + authorizationPageBeanId + " , You must be owner message");
			}
		}

		return false;
	}

	/** action=up_position: bump a product to the top of the list. */
	private boolean upPosition(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (productlistBeanId.getAction().compareTo("up_position") == 0) {
			int userId = 0;
			productlistBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
				// response.sendRedirect("Authorization.jsp" );
				servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
				return true;
			}

			if (request.getParameter("product_id") != null) {

				if (authorizationPageBeanId.getRoleId() != 2) {
					userId = productlistFaced.getWhoseProduct(request.getParameter("product_id"));
					if (userId == -1)
						return true; // Пользователя для этой записи не сущетсвует
					if (authorizationPageBeanId.getIntUserID() != userId) {
						authorizationPageBeanId.setStrMessage("Access denny  for login "
								+ authorizationPageBeanId.getStrLogin() + " , You must be owner message");
						// response.sendRedirect("Authorization.jsp" );
						servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
						return true;
					}
				}

				/*
				 * float balans = authorizationPageFaced.getBalans(user_id) ; float cost =
				 * productlistFaced.getProductCost(request.getParameter("product_id")) ; if(cost
				 * > balans) { authorizationPageBeanId.setStrMessage("Access denny  for login "+
				 * authorizationPageBeanId.getStrLogin() +" , You must be owner message") ;
				 * response.sendRedirect("Authorization.jsp" ); return true; }
				 */

				productlistFaced.upPosition(request.getParameter("product_id"));
			}

			// request.setAttribute("product_id",null);
			// request.setAttribute("action",null);
		}

		return false;
	}

	/** action=set_color: highlight a product. */
	private boolean colorPosition(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (productlistBeanId.getAction().compareTo("set_color") == 0) {
			productlistBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
				// response.sendRedirect("Authorization.jsp" );
				servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
				return true;
			}

			if (request.getParameter("product_id") != null) {

				if (authorizationPageBeanId.getRoleId() != 2) {
					int userId = productlistFaced.getWhoseProduct(request.getParameter("product_id"));
					if (authorizationPageBeanId.getIntUserID() != userId) {
						authorizationPageBeanId.setStrMessage(
								"Access denny  for login " + authorizationPageBeanId + " , You must be owner message");
						// response.sendRedirect("Authorization.jsp" );
						servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
						return true;
					}
				}
				productlistFaced.colorPosition(request.getParameter("product_id"), "#FCF8CF");
			}
		}

		return false;
	}

	/** action=edit: open the right editor for the chosen element of a product. */
	private boolean editPosition(HttpServletRequest request, HttpServletResponse response,
			ProductlistBean productlistBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (productlistBeanId.getAction().compareTo("edit") == 0) {

			if (Validation.isNonNegativeInteger(request.getParameter("product_id"))) {
				productlistBeanId.setAction("");

				// authorizationPageBeanId.setCatalog_id(productlistFaced.getCatalogId(request.getParameter("product_id")));

//		        	  productPostAllFaced.initPage(request.getParameter("product_id"),  SoftPostBeanId , authorizationPageBeanId);
				// Для того чтобы правильно отрывался раздел когда открываеш из другово раздела
				// authorizationPageBeanId.setCatalogParent_id("" +
				// productlistFaced.getCatalogParentId(authorizationPageBeanId));

				if (request.getParameter("element").compareTo("forum") == 0) {
					response.sendRedirect("ProductUserPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("userinfo") == 0) {
					response.sendRedirect(
							"ProductUserPostWithOutCatalog.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("edit_biz_info") == 0) {
					response.sendRedirect(
							"ProductUserPostBusiness.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("edit_transport") == 0) {
					response.sendRedirect(
							"ProductUserPostTransport.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("edit_realty") == 0) {
					response.sendRedirect("ProductUserPostRealty.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("addvideo") == 0) {
					response.sendRedirect("ProductUserPostVideo.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("addmusics") == 0) {
					response.sendRedirect("ProductUserPostMusic.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext1_user") == 0) {
					response.sendRedirect("Ext1ProductPostUser.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext2_user") == 0) {
					response.sendRedirect("Ext2ProductPostUser.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_files_user") == 0) {
					response.sendRedirect(
							"ExtFilesProductPostUser.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_ofice_files_user") == 0) {
					response.sendRedirect(
							"ExtOficeFilesProductPostUser.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_music_files_user") == 0) {
					response.sendRedirect(
							"ExtOficeFilesProductPostUser.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("product") == 0) {
					response.sendRedirect("ProductPostCre.jsp?product_id=" + request.getParameter("product_id")
							+ "&parent_id=" + authorizationPageBeanId.getCatalogId());
					return true;
				}
				if (request.getParameter("element").compareTo("news") == 0) {
					response.sendRedirect(
							"NewArrivalProductPostCre.jsp?product_id=" + request.getParameter("product_id")
									+ "&parent_id=" + authorizationPageBeanId.getCatalogId());
					return true;
				}
				// if(request.getParameter("element").compareTo("news") == 0 )
				// response.sendRedirect("ProductPost.jsp") ;
				// @Deprecated - Co1ProductPost.js

				if (request.getParameter("element").compareTo("co1") == 0) {
					response.sendRedirect("RecommentedItemPost.jsp?product_id=" + request.getParameter("product_id")
							+ "&parent_id=" + authorizationPageBeanId.getCatalogId());
					return true;
				}
				if (request.getParameter("element").compareTo("co2") == 0) {
					response.sendRedirect("SponsoredBySellersItemPost.jsp?product_id=" + request.getParameter("product_id")
							+ "&parent_id=" + authorizationPageBeanId.getCatalogId());
					return true;
				}
				if (request.getParameter("element").compareTo("bottom") == 0) {
					response.sendRedirect("FooterLinksListPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				// old one
				if (request.getParameter("element").compareTo("ext1") == 0) {
					response.sendRedirect("Ext1ProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext1") == 0) {
					response.sendRedirect(
							"ProductInfoColumnOnePost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				// old one
				if (request.getParameter("element").compareTo("ext2") == 0) {
					response.sendRedirect("Ext2ProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext2") == 0) {
					response.sendRedirect(
							"ProductInfoColumnTwoPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				// old one
				if (request.getParameter("element").compareTo("ext_files") == 0) {
					response.sendRedirect("ExtFilesProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_files") == 0) {
					response.sendRedirect(
							"ProductInfoAttchedFilesPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				// old one
				if (request.getParameter("element").compareTo("ext_tabls") == 0) {
					response.sendRedirect("ExtTabsProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_tabls") == 0) {
					response.sendRedirect(
							"ProductInfoDescriptionTabsPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_service_page") == 0) {
					response.sendRedirect("ServicePagePost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("blog") == 0) {
					authorizationPageBeanId.setLastProductId(Long.parseLong(request.getParameter("product_parent_id")));
					response.sendRedirect("ProductReviewPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}

//		        	  {
//		        		 productInfoFaced.mergeProductInfoBean(authorizationPageBeanId.getIntUserID(), request.getParameter("product_parent_id") , productInfoBeanId) ;
//		        		 productInfoBeanId.setType_page("product_parent_id") ;
//		        		 productInfoBeanId.setBack_url("ProductInfo.jsp?policy_byproductid=" + request.getParameter("product_parent_id")) ;
//		        		 productInfoBeanId.setIntUserID(authorizationPageBeanId.getIntUserID());
//		        		 response.sendRedirect("ProductReviewPost.jsp?product_id="+request.getParameter("product_id"));
//		        		 return ;
//		        	  }

			}
		}
//        else
//       {
//        	SoftPostBeanId.setSoft_id("-1") ;
//       }

		return false;
	}

	/** action=create_site: create the seller's own shop site. */
	private boolean createSite(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (productlistBeanId.getAction().compareTo("create_site") == 0) {
			productlistBeanId.setAction("");
			authorizationPageFaced.getCreateShopBean().setLogin(authorizationPageBeanId.getStrLogin());
			authorizationPageFaced.getCreateShopBean().setPasswd(authorizationPageBeanId.getStrPasswd());
			authorizationPageFaced.getCreateShopBean().setAddress("no created");

			String domain = authorizationPageBeanId.getSiteDir()
					.substring(authorizationPageBeanId.getSiteDir().indexOf("."));

			authorizationPageFaced.getCreateShopBean().setCompanyName(authorizationPageBeanId.getStrCompany());
			authorizationPageFaced.getCreateShopBean().setSiteDir(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean().setNickSite(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean().setSubjectSite("Cabinet");
			authorizationPageFaced.getCreateShopBean().setPerson(
					authorizationPageBeanId.getStrFirstName() + " " + authorizationPageBeanId.getStrLastName());
			authorizationPageFaced.getCreateShopBean().setPhone(authorizationPageBeanId.getStrPhone());

			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(servletContext)
						.getString("you_can_not_to_create_shop"));
				// response.sendRedirect("Authorization.jsp?Login=newuser" );
				servletContext.getRequestDispatcher("/Authorization.jsp?Login=newuser").forward(request, response);
				return true;
			}
			authorizationPageFaced.getCreateShopBean().addSite(authorizationPageBeanId.getIntUserID());
			servletContext
					.getRequestDispatcher(
							"/Authorization.jsp?site_id=" + authorizationPageFaced.getCreateShopBean().getSiteId()
									+ "&Login=" + authorizationPageFaced.getCreateShopBean().getLogin())
					// FIX (credential leak): the clear-text password used to be appended as
					// "&Passwd1=..." on the forward URL, which lands in access logs, the
					// forward query-string attribute and any downstream Referer. It is
					// redundant: the session bean already carries the password, and
					// AuthorizationAction only overwrites it when the parameter is present.
					.forward(request, response);
			return true;
			// response.sendRedirect("Authorization.jsp?site_id=" +
			// authorizationPageFaced.getCreateShopBean().getSite_id() + "&Login=" +
			// authorizationPageFaced.getCreateShopBean().getLogin()+ "&Passwd1=" +
			// authorizationPageFaced.getCreateShopBean().getPasswd() );

		}

		// if( productlistBeanId.getAction().compareTo("create_site2") == 0 )
		return false;
	}

	/** create_site_by_id=...: register the new shop host in Tomcat and log into it. */
	private boolean createSiteById(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("create_site_by_id") != null) {
			productlistBeanId.setAction("");
			authorizationPageFaced.getCreateShopBean().setLogin(authorizationPageBeanId.getStrLogin());
			authorizationPageFaced.getCreateShopBean().setPasswd(authorizationPageBeanId.getStrPasswd());
			authorizationPageFaced.getCreateShopBean().setAddress("no created");

			String domain = authorizationPageBeanId.getSiteDir()
					.substring(authorizationPageBeanId.getSiteDir().indexOf("."));

			authorizationPageFaced.getCreateShopBean().setCompanyName(authorizationPageBeanId.getStrCompany());
			authorizationPageFaced.getCreateShopBean().setSiteDir(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean()
					.setHost("www." + authorizationPageBeanId.getStrLogin() + ".yourgroup.net");
			authorizationPageFaced.getCreateShopBean().setNickSite(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean().setSubjectSite("internet shop");
			authorizationPageFaced.getCreateShopBean().setPerson(
					authorizationPageBeanId.getStrFirstName() + " " + authorizationPageBeanId.getStrLastName());
			authorizationPageFaced.getCreateShopBean().setPhone(authorizationPageBeanId.getStrPhone());

			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(servletContext)
						.getString("you_can_not_to_create_shop"));
				servletContext.getRequestDispatcher("/Authorization.jsp?Login=").forward(request, response);
				// response.sendRedirect("Authorization.jsp?Login=" );
				return true;
			}

			if (authorizationPageBeanId.getLangId() == 1)
				authorizationPageFaced.getCreateShopBean().addShopWithExtract(authorizationPageBeanId,
						request.getParameter("create_site_by_id"), servletContext);
			else
				authorizationPageFaced.getCreateShopBean().addShopWithExtractEn(authorizationPageBeanId,
						request.getParameter("create_site_by_id"), servletContext);

			// authorizationPageFaced.getCreateShopBean().addShopWithExtract_allLang(authorizationPageBeanId,request.getParameter("create_site_by_id"),servletContext);

			if (authorizationPageBeanId.getUserSite().equals("-1")) {
				Message messageMail = new Message();
				String sitePath = (String) request.getSession().getAttribute("site_path");
				String shop = sitePath + File.separatorChar + "mail" + File.separatorChar + "newshop.txt";
				String policy = sitePath + File.separatorChar + "mail" + File.separatorChar + "Policy.pdf";
				messageMail.put("@FirstName", authorizationPageBeanId.getStrFirstName());
				messageMail.put("@LastName", authorizationPageBeanId.getStrLastName());
				messageMail.put("@EmailPassword", authorizationPageBeanId.getEmailPassword());
				messageMail.put("@Password", authorizationPageBeanId.getStrPasswd());
				messageMail.put("@Login", authorizationPageBeanId.getStrLogin());
				// messageMail.put("@Shop", "http://www.siteforyou.net/Productlist.jsp?site=" +
				// authorizationPageFaced.getCreateShopBean().getSite_id() ) ;
				// messageMail.put("@Policy", "http://www.siteforyou.net/Policy.pdf" ) ;

				MessageSender mqSender = new MessageSender(request.getSession(), SendMailMessageBean.messageQuery);
				Message message = new Message();
				message.put("to", authorizationPageBeanId.getStrEMail());
				message.put("subject", "My Internet shop ");
				message.put("pathmessage", shop);
				message.put("fields", messageMail);
				// message.put("@Policy", "http://www.siteoneclick.com/Policy.pdf" ) ;
				// message.put("attachFile", policy ) ;
				mqSender.send(message);
			}

			// authorizationPageBeanId.
			authorizationPageBeanId.setUserSite(authorizationPageFaced.getCreateShopBean().getSiteId());

			// get current tomcat server, engine and context objects
			// MBeanServer mBeanServer = MBeanServerFactory.findMBeanServer(null).get(0);
			// ObjectName name = new ObjectName("Catalina", "type", "Server");
			// Server server = (Server) mBeanServer.getAttribute(name, "managedResource");
			// Service[] services = server.findServices();
			// StandardEngine engine = (StandardEngine) services[0].getContainer();
			// StandardContext context = (StandardContext)
			// engine.findChild(engine.getDefaultHost()).findChild(servletContext.getContextPath());
			// Mapper mapper = context.getMapper();
			// mapper.addHostAlias(engine.getDefaultHost(),
			// authorizationPageFaced.getCreateShopBean().getHost());

			// response.sendRedirect("Authorization.jsp?site_id=" +
			// authorizationPageFaced.getCreateShopBean().getSite_id() + "&Login=" +
			// authorizationPageFaced.getCreateShopBean().getLogin()+ "&Passwd1=" +
			// authorizationPageFaced.getCreateShopBean().getPasswd() );
			response.sendRedirect("Productlist.jsp?action=login_usersite");
			return true;
		}

		return false;
	}

	/** action=login_usersite: switch the session to the seller's own site. */
	private boolean loginUserSite(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, HttpSession session, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (productlistBeanId.getAction().compareTo("login_usersite") == 0) {
			productlistBeanId.setAction("");
			authorizationPageBeanId.setCatalogId("-2");
			if (authorizationPageBeanId.getUserSite().compareTo("-1") != 0) {
				String cokieSessionId = (String) session.getAttribute("cokie_session_id");
				authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
				authorizationPageBeanId.setSiteId(authorizationPageBeanId.getUserSite(), authorizationPageFaced);
				String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
						(HttpServletResponse) response);
				if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
						authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, sessionId)
						&& authorizationPageBeanId.getStrLogin().length() != 0) {
					// response.sendRedirect("Productlist.jsp?offset=0" );
					// servletContext.getRequestDispatcher("/Productlist.jsp?offset=0").forward(
					// request, response);
					productlistBeanId.setOffset(0);
				}
			} else
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("you_not_have_site"));

		}

///////+++
		if (request.getParameter("site") != null && Validation.isNonNegativeInteger(request.getParameter("site"))) {

			productlistBeanId.setAction("");
			String site = request.getParameter("site");

			if (authorizationPageBeanId.getSiteId().compareTo(site) != 0) {
				productlistBeanId.setOffset(0);
				authorizationPageBeanId.setCatalogId("" + SpecialCatalog.OUTPUT_PAGES_SORT_BY_SOFT_ID);
			}

			String cokieSessionId = (String) session.getAttribute("cokie_session_id");
			authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
			authorizationPageBeanId.setSiteId(site, authorizationPageFaced);
			String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
					(HttpServletResponse) response);
			if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
					authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, sessionId)
					&& authorizationPageBeanId.getStrLogin().length() != 0) {
				// response.sendRedirect("Authorization.jsp?site_id=" +
				// authorizationPageFaced.getCreateShopBean().getSite_id() + "&Login=" +
				// authorizationPageFaced.getCreateShopBean().getLogin()+ "&Passwd1=" +
				// authorizationPageFaced.getCreateShopBean().getPasswd() );
				// productlistBeanId.setSite_id(request.getParameter("site"));
				// response.sendRedirect("Productlist.jsp?offset=0" );
				// servletContext.getRequestDispatcher("/Productlist.jsp?offset=0").forward(
				// request, response);
				productlistBeanId.setOffset(0);
			} else {
				authorizationPageBeanId.setStrPasswd(SiteRole.GUEST);
				authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
				authorizationPageBeanId.setSiteId(site, authorizationPageFaced);
				if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
						authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, "")
						&& authorizationPageBeanId.getStrLogin().length() != 0) {
					// response.sendRedirect("Productlist.jsp?offset=0" );
					// servletContext.getRequestDispatcher("/Productlist.jsp?offset=0").forward(
					// request, response);
					productlistBeanId.setOffset(0);
					// return ;
					// response.sendRedirect("Authorization.jsp?site_id=" +
					// authorizationPageFaced.getCreateShopBean().getSite_id() + "&Login=" +
					// authorizationPageFaced.getCreateShopBean().getLogin()+ "&Passwd1=" +
					// authorizationPageFaced.getCreateShopBean().getPasswd() );
				}
			}

		}

///////+++//
		if (request.getParameter("logoff_site") != null) {
			productlistBeanId.setAction("");
			String cokieSessionId = (String) session.getAttribute("cokie_session_id");
			authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
			authorizationPageBeanId.setStrPasswd(SiteRole.GUEST);
			authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
			authorizationPageBeanId.setSiteId(request.getParameter("logoff_site"), authorizationPageFaced);
			if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
					authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, "")
					&& authorizationPageBeanId.getStrLogin().length() != 0) {

				// response.sendRedirect("Productlist.jsp?offset=0" );
				// servletContext.getRequestDispatcher("/Productlist.jsp?offset=0").forward(
				// request, response);
				productlistBeanId.setOffset(0);
				// return ;
				// response.sendRedirect("Authorization.jsp?site_id=" +
				// authorizationPageFaced.getCreateShopBean().getSite_id() + "&Login=" +
				// authorizationPageFaced.getCreateShopBean().getLogin()+ "&Passwd1=" +
				// authorizationPageFaced.getCreateShopBean().getPasswd() );
			}
		}

		return false;
	}

	/** action=logoff / logoff_usersite: end the session. */
	private boolean logoff(HttpServletResponse response, HttpSession session, ProductlistBean productlistBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (productlistBeanId.getAction().compareTo("logoff") == 0
				|| productlistBeanId.getAction().compareTo("logoff_usersite") == 0) {
			String siteId = authorizationPageBeanId.getSiteId();
			String cokieSessionId = (String) session.getAttribute("cokie_session_id");
			session.invalidate();
			authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
			// clearCookieFromBD(AuthorizationPageBean authorizationBean)
			response.sendRedirect(
					"Productlist.jsp?site=" + siteId + "&" + "locale=" + authorizationPageBeanId.getLocale());
			// servletContext.getRequestDispatcher("/Productlist.jsp?site=" + site_id + "&"
			// + "locale=" + authorizationPageBeanId.getLocale() ).forward( request,
			// response);
			return true;
		}
		return false;
	}

	/** Run the catalogue query (with criteria filter) plus the recommended / sponsored / new / top-review / footer lists and criteria labels. */
	private void loadProductlist(ProductlistBean productlistBeanId, AuthorizationPageBean authorizationPageBeanId,
			ProductlistFaced productlistFaced, boolean isCriteriaByCatalog) throws Exception {
		productlistBeanId.productList = productlistFaced.getProductlist(authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), Long.parseLong(authorizationPageBeanId.getCatalogId()),
				productlistBeanId, authorizationPageBeanId);
		productlistFaced.getQuantityProducts(productlistBeanId);
		productlistBeanId.recommentedItems = productlistFaced.getCoOneProductlist(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getCatalogId(), productlistBeanId, authorizationPageBeanId);
		productlistBeanId.sponsoredBySellers = productlistFaced.getCoTwoProductlist(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getCatalogId(), productlistBeanId, authorizationPageBeanId);
		productlistBeanId.newArrivalItems = productlistFaced.getNewslist(authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), authorizationPageBeanId);


		productlistBeanId.topItemReview = productlistFaced.getBlogTopProductlist(authorizationPageBeanId.getSiteId(),
				productlistBeanId, authorizationPageBeanId);

		productlistBeanId.footerLinksList = productlistFaced.getBottomlist(authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), authorizationPageBeanId);

		authorizationPageBeanId.setCatalogParentId("" + productlistFaced.getCatalogParentId(authorizationPageBeanId));

		productlistBeanId.setCriteria1Label(productlistFaced
				.getOneLabel("select  label   from creteria1   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria2Label(productlistFaced
				.getOneLabel("select  label   from creteria2   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria3Label(productlistFaced
				.getOneLabel("select  label   from creteria3   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria4Label(productlistFaced
				.getOneLabel("select  label   from creteria4   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria5Label(productlistFaced
				.getOneLabel("select  label   from creteria5   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria6Label(productlistFaced
				.getOneLabel("select  label   from creteria6   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria7Label(productlistFaced
				.getOneLabel("select  label   from creteria7   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria8Label(productlistFaced
				.getOneLabel("select  label   from creteria8   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria9Label(productlistFaced
				.getOneLabel("select  label   from creteria9   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId.setCriteria10Label(productlistFaced
				.getOneLabel("select  label   from creteria10   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));

	}

	/** Fill every dropdown of the search form (currency, catalog tree, menu, criteria 1-10, dates). */
	private void populateSearchLists(ProductlistBean productlistBeanId, AuthorizationPageBean authorizationPageBeanId,
			ProductlistFaced productlistFaced, boolean isCriteriaByCatalog, String notselected) throws Exception {
		productlistBeanId.setSelectCurrencyCd(productlistFaced.getXMLDBList("Productlist.jsp?currency_cd",
				"currencies", productlistBeanId.getCurrencyCd(),
				"SELECT currency_cd , currency_desc  FROM currency  WHERE active = true"));
		productlistBeanId.setSelectTreeCatalog(productlistFaced.getTreeXMLDBList("Productlist.jsp?catalog_id",
				"catalog", authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable   from catalog   where  active = true and lang_id = "
						+ authorizationPageBeanId.getLangId() + " and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and parent_id = "
						+ authorizationPageBeanId.getCatalogParentId(),
				"select catalog_id , lable   from catalog   where  active = true and parent_id = "
						+ authorizationPageBeanId.getCatalogId()));

		productlistBeanId.setSelectMenuCatalog(productlistFaced.getMenuXMLDBList("Productlist.jsp?catalog_id", "menu",
				authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable , parent_id  from catalog   where  active = true and parent_id = -2 and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and lang_id = "
						+ authorizationPageBeanId.getLangId()
						+ " or parent_id in (select catalog_id   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + "  and parent_id = -2 )"));

		productlistBeanId.setSelectCreteria1Id(productlistFaced.getXMLDBCriteriaListLocale(
				"Productlist.jsp?creteria1_id", "creteria1", "" + authorizationPageBeanId.getCreteria1Id(),
				notselected, "select creteria1_id , name   from creteria1   where  active = true " + productlistBeanId
						.getPartCriteria(authorizationPageBeanId.getSiteId(), isCriteriaByCatalog)));
		productlistBeanId
				.setSelectCreteria2Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria2_id",
						"creteria2", "" + authorizationPageBeanId.getCreteria2Id(), notselected,
						"select creteria2_id , name   from creteria2   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria1Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria3Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria3_id",
						"creteria3", "" + authorizationPageBeanId.getCreteria3Id(), notselected,
						"select creteria3_id , name   from creteria3   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria2Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria4Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria4_id",
						"creteria4", "" + authorizationPageBeanId.getCreteria4Id(), notselected,
						"select creteria4_id , name   from creteria4   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria3Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria5Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria5_id",
						"creteria5", "" + authorizationPageBeanId.getCreteria5Id(), notselected,
						"select creteria5_id , name   from creteria5   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria4Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria6Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria6_id",
						"creteria6", "" + authorizationPageBeanId.getCreteria6Id(), notselected,
						"select creteria6_id , name   from creteria6   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria5Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria7Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria7_id",
						"creteria7", "" + authorizationPageBeanId.getCreteria7Id(), notselected,
						"select creteria7_id , name   from creteria7   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria6Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria8Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria8_id",
						"creteria8", "" + authorizationPageBeanId.getCreteria8Id(), notselected,
						"select creteria8_id , name   from creteria8   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria7Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria9Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria9_id",
						"creteria9", "" + authorizationPageBeanId.getCreteria9Id(), notselected,
						"select creteria9_id , name   from creteria9   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria8Id()
								+ " ) "));
		productlistBeanId
				.setSelectCreteria10Id(productlistFaced.getXMLDBCriteriaListLocale("Productlist.jsp?creteria10_id",
						"creteria10", "" + authorizationPageBeanId.getCreteria10Id(), notselected,
						"select creteria10_id , name   from creteria10   where  active = true "
								+ productlistBeanId.getPartCriteria(authorizationPageBeanId.getSiteId(),
										isCriteriaByCatalog)
								+ " and ( link_id = 0 or link_id = " + authorizationPageBeanId.getCreteria9Id()
								+ " ) "));

		productlistBeanId.setSelectDayfromId(productlistFaced.getXMLListDateDay("Productlist.jsp?dayfrom_id",
				"dayfrom", "" + authorizationPageBeanId.getDayfromId()));
		productlistBeanId.setSelectMountfromId(productlistFaced.getXMLListDateMount("Productlist.jsp?mountfrom_id",
				"mountfrom", "" + authorizationPageBeanId.getMountfromId()));
		productlistBeanId.setSelectYearfromId(productlistFaced.getXMLListDateYear("Productlist.jsp?yearfrom_id",
				"yearfrom", "" + authorizationPageBeanId.getYearfromId()));

		productlistBeanId.setSelectDaytoId(productlistFaced.getXMLListDateDay("Productlist.jsp?dayto_id", "dayto",
				"" + authorizationPageBeanId.getDaytoId()));
		productlistBeanId.setSelectMounttoId(productlistFaced.getXMLListDateMount("Productlist.jsp?mountto_id",
				"mountto", "" + authorizationPageBeanId.getMounttoId()));
		productlistBeanId.setSelectYeartoId(productlistFaced.getXMLListDateYear("Productlist.jsp?yearto_id", "yearto",
				"" + authorizationPageBeanId.getYeartoId()));

	}

	/**
	 * Tells whether the locale is one the site supports.
	 *
	 * @return true if allowed
	 */
	public boolean isAllowLocale(String locale) {
		if (locale == null)
			return false;
		String[] IntField = { "en", "ru" };
		for (int i = 0; i < IntField.length; i++) {
			if (IntField[i].compareTo(locale) == 0) {
				return true;
			}
		}
		return false;
	}

}
