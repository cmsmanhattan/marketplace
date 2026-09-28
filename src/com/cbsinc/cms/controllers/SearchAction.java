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
import com.cbsinc.cms.SearchBean;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductInfoFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@PageController( jspName = "Productlist.jsp" )
/**
 * Search controller: full-text and criteria search over the catalogue.
 */
public class SearchAction implements IAction {

	ProductPostAllFaced productPostAllFaced;
	AuthorizationPageFaced authorizationPageFaced;

	public SearchAction() {
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
		SearchBean searchBeanId;
		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;
		String notselected = "";
		boolean isInternet = true;
		productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

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
		searchBeanId = new SearchBean();
		request.setAttribute("searchBeanId", searchBeanId);

		if (productInfoFaced == null || searchBeanId == null || authorizationPageBeanId == null
				|| authorizationPageFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");

		searchBeanId.isInternet = isInternet;
		authorizationPageBeanId.setBalance(ServiceLocator.getInstance().getAuthorizationPageFaced()
				.getStrBalans(authorizationPageBeanId.getIntUserID()));

		readSearchParameters(request, searchBeanId, authorizationPageBeanId);
		applyLocale(request, servletContext, searchBeanId, authorizationPageBeanId);
		resolveSearchValue(request, searchBeanId);

		if (dispatchAction(request, response, servletContext, session, searchBeanId,
				authorizationPageBeanId, productlistFaced))
			return;

		loadProductlist(searchBeanId, authorizationPageBeanId, productlistFaced);
		populateSearchLists(searchBeanId, productlistFaced);
	}



	/** Routes the action form field (and create_site_by_id) to the business methods; true = response already sent. */
	private boolean dispatchAction(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, HttpSession session, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (deletePosition(request, response, servletContext, searchBeanId, authorizationPageBeanId,
				productlistFaced))
			return true;
		if (upPosition(request, response, servletContext, searchBeanId, authorizationPageBeanId, productlistFaced))
			return true;
		if (colorPosition(request, response, servletContext, searchBeanId, authorizationPageBeanId,
				productlistFaced))
			return true;
		if (editPosition(request, response, searchBeanId, authorizationPageBeanId))
			return true;
		if (createSite(request, response, servletContext, searchBeanId, authorizationPageBeanId))
			return true;
		if (createSiteById(request, response, servletContext, searchBeanId, authorizationPageBeanId))
			return true;
		if (loginUserSite(request, response, servletContext, session, searchBeanId, authorizationPageBeanId))
			return true;
		if (logoff(request, response, servletContext, session, searchBeanId, authorizationPageBeanId))
			return true;
		return false;
	}

	/** Copy the search form fields (offset, catalog, dates, cost range, criteria 1-10, search value / mode) into the beans. */
	private void readSearchParameters(HttpServletRequest request, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("offset") != null && Validation.isNonNegativeInteger(request.getParameter("offset"))) {
			searchBeanId.setOffset(searchBeanId.stringToInt(request.getParameter("offset")));
			authorizationPageBeanId.setOffsetLastPage(Long.parseLong(request.getParameter("offset")));
		}

		if (request.getParameter("catalog_id") != null && Validation.isNonNegativeInteger(request.getParameter("catalog_id"))) {
			if (authorizationPageBeanId.getCatalogId().compareTo(request.getParameter("catalog_id")) != 0) {
				searchBeanId.setOffset(0);
			}
			authorizationPageBeanId.setCatalogId(request.getParameter("catalog_id"));

		}
		searchBeanId.setRoleId(authorizationPageBeanId.getRoleId());

		// Open or closed dialog window
		if (request.getParameter("dialog") != null)
			searchBeanId.setDialog(request.getParameter("dialog"));
		if (request.getParameter("is_advanced_search_open") != null)
			searchBeanId.setAdvancedSearchOpen(request.getParameter("is_advanced_search"));
		if (request.getParameter("is_forum_open") != null)
			searchBeanId.setForumOpen(request.getParameter("is_forum_open"));

		if (request.getParameter("fromcost") != null && Validation.isDecimal(request.getParameter("fromcost")))
			authorizationPageBeanId.setStrFromCost(request.getParameter("fromcost").trim());
		if (request.getParameter("tocost") != null && Validation.isDecimal(request.getParameter("tocost")))
			authorizationPageBeanId.setStrToCost(request.getParameter("tocost").trim());

		if (request.getParameter("action") != null) {
			searchBeanId.setAction(request.getParameter("action"));
		}
		if (request.getParameter("search_value") != null)
			searchBeanId.setSearchValueArg(request.getParameter("search_value"));
		if (request.getParameter("searchquery") != null && Validation.isNonNegativeInteger(request.getParameter("searchquery")))
			searchBeanId.setSearchquery(Integer.parseInt(request.getParameter("searchquery")));
		else if (request.getParameter("offset") == null)
			searchBeanId.setSearchquery(0);

	}

	/** ?locale= switches the user language when allowed. */
	private void applyLocale(HttpServletRequest request, ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		String clLocale = request.getParameter("locale");
		if (clLocale != null) {

			if (isAllowLocale(clLocale)) {

				if (authorizationPageBeanId.getLocale().compareTo(clLocale) != 0) {
					searchBeanId.setOffset(0);
					authorizationPageBeanId.setCatalogId("" + SpecialCatalog.OUTPUT_PAGES_SORT_BY_SOFT_ID);
				}

				authorizationPageBeanId.setLocale(clLocale, servletContext);
			}

		}

	}

	/** Choose the effective search string by search mode: 2 = by first letter, 1 = full text (criteria reset), 0 = none. */
	private void resolveSearchValue(HttpServletRequest request, SearchBean searchBeanId) throws Exception {
		if (searchBeanId.getSearchquery() == 2 && request.getParameter("search_char") != null
				&& request.getParameter("search_char").length() > 0) {
			// if new char then start with 0
			if (searchBeanId.getSearchValueArg().compareTo(request.getParameter("search_char")) != 0) {
				searchBeanId.setOffset(0);
			}

			searchBeanId.setSearchValueArg(request.getParameter("search_char"));
		} else if (searchBeanId.getSearchquery() == 1 && request.getParameter("search_value") != null
				&& request.getParameter("search_value").length() > 0) {
			searchBeanId.setSearchValueArg(request.getParameter("search_value"));
		} else if (searchBeanId.getSearchquery() == 0) {
			searchBeanId.setSearchValueArg("");
		}
	}

	/** action=del: the product owner or a site administrator (role 2) removes a product. */
	private boolean deletePosition(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (searchBeanId.getAction().compareTo("del") == 0) {
			searchBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
				servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
				return true;
			}

			if (request.getParameter("product_id") != null && Validation.isNonNegativeInteger(request.getParameter("product_id"))) {

				if (authorizationPageBeanId.getRoleId() != 2) {
					int userId = productlistFaced.getWhoseProduct(request.getParameter("product_id"));
					if (authorizationPageBeanId.getIntUserID() != userId) {
						authorizationPageBeanId.setStrMessage(
								"Access denny  for login " + authorizationPageBeanId + " , You must be owner message");
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
			ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (searchBeanId.getAction().compareTo("up_position") == 0) {
			int userId = 0;
			searchBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
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
						servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
						return true;
					}
				}

				productlistFaced.upPosition(request.getParameter("product_id"));
			}

		}

		return false;
	}

	/** action=set_color: highlight a product. */
	private boolean colorPosition(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId, ProductlistFaced productlistFaced) throws Exception {
		if (searchBeanId.getAction().compareTo("set_color") == 0) {
			searchBeanId.setAction("");
			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage("Access denny  for login user , You must be membership");
				servletContext.getRequestDispatcher("/Authorization.jsp").forward(request, response);
				return true;
			}

			if (request.getParameter("product_id") != null) {

				if (authorizationPageBeanId.getRoleId() != 2) {
					int userId = productlistFaced.getWhoseProduct(request.getParameter("product_id"));
					if (authorizationPageBeanId.getIntUserID() != userId) {
						authorizationPageBeanId.setStrMessage(
								"Access denny  for login " + authorizationPageBeanId + " , You must be owner message");
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
	private boolean editPosition(HttpServletRequest request, HttpServletResponse response, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (searchBeanId.getAction().compareTo("edit") == 0) {

			if (Validation.isNonNegativeInteger(request.getParameter("product_id"))) {
				searchBeanId.setAction("");

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
					response.sendRedirect("ProductPostCre.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("news") == 0) {
					response.sendRedirect("ProductPostCre.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("co1") == 0) {
					response.sendRedirect("RecommentedItemPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("co2") == 0) {
					response.sendRedirect("SponsoredBySellersItemPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext1") == 0) {
					response.sendRedirect("Ext1ProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext2") == 0) {
					response.sendRedirect("Ext2ProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_files") == 0) {
					response.sendRedirect("ExtFilesProductPost.jsp?product_id=" + request.getParameter("product_id"));
					return true;
				}
				if (request.getParameter("element").compareTo("ext_tabls") == 0) {
					response.sendRedirect("ExtTabsProductPost.jsp?product_id=" + request.getParameter("product_id"));
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

			}
		}

		return false;
	}

	/** action=create_site: create the seller's own shop site. */
	private boolean createSite(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (searchBeanId.getAction().compareTo("create_site") == 0) {
			searchBeanId.setAction("");
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
				authorizationPageBeanId.setStrMessage("не регистрированный пользователь  не может создовать магазин");
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

		}

		return false;
	}

	/** create_site_by_id=...: register the new shop host in Tomcat and log into it. */
	private boolean createSiteById(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("create_site_by_id") != null) {
			searchBeanId.setAction("");
			authorizationPageFaced.getCreateShopBean().setLogin(authorizationPageBeanId.getStrLogin());
			authorizationPageFaced.getCreateShopBean().setPasswd(authorizationPageBeanId.getStrPasswd());
			authorizationPageFaced.getCreateShopBean().setAddress("no created");

			String domain = authorizationPageBeanId.getSiteDir()
					.substring(authorizationPageBeanId.getSiteDir().indexOf("."));

			authorizationPageFaced.getCreateShopBean().setCompanyName(authorizationPageBeanId.getStrCompany());
			authorizationPageFaced.getCreateShopBean().setSiteDir(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean()
					.setHost("www." + authorizationPageBeanId.getStrLogin() + ".irr.bz");
			authorizationPageFaced.getCreateShopBean().setNickSite(authorizationPageBeanId.getStrLogin() + domain);
			authorizationPageFaced.getCreateShopBean().setSubjectSite("Cabinet");
			authorizationPageFaced.getCreateShopBean().setPerson(
					authorizationPageBeanId.getStrFirstName() + " " + authorizationPageBeanId.getStrLastName());
			authorizationPageFaced.getCreateShopBean().setPhone(authorizationPageBeanId.getStrPhone());

			if (authorizationPageBeanId.getRoleId() == 0) {
				authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(servletContext)
						.getString("you_can_not_to_create_shop"));
				servletContext.getRequestDispatcher("/Authorization.jsp?Login=").forward(request, response);
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
				messageMail.put("@Shop", "http://www.siteoneclick.com/Productlist.jsp?site="
						+ authorizationPageFaced.getCreateShopBean().getSiteId());
				messageMail.put("@Policy", "http://www.siteoneclick.com/Policy.pdf");

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

			authorizationPageBeanId.setUserSite(authorizationPageFaced.getCreateShopBean().getSiteId());

			response.sendRedirect("Productlist.jsp?action=login_usersite");
			return true;
		}

		return false;
	}

	/** action=login_usersite: switch the session to the seller's own site. */
	private boolean loginUserSite(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, HttpSession session, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (searchBeanId.getAction().compareTo("login_usersite") == 0) {
			searchBeanId.setAction("");
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
					searchBeanId.setOffset(0);
				}
			} else
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("you_not_have_site"));

		}

///////+++
		if (request.getParameter("site") != null && Validation.isNonNegativeInteger(request.getParameter("site"))) {

			searchBeanId.setAction("");
			String site = request.getParameter("site");

			if (authorizationPageBeanId.getSiteId().compareTo(site) != 0) {
				searchBeanId.setOffset(0);
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
				searchBeanId.setOffset(0);
			} else {
				authorizationPageBeanId.setStrPasswd(SiteRole.GUEST);
				authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
				authorizationPageBeanId.setSiteId(site, authorizationPageFaced);
				if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
						authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, "")
						&& authorizationPageBeanId.getStrLogin().length() != 0) {
					searchBeanId.setOffset(0);
				}
			}

		}

		if (request.getParameter("logoff_site") != null) {
			searchBeanId.setAction("");
			String cokieSessionId = (String) session.getAttribute("cokie_session_id");
			authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
			authorizationPageBeanId.setStrPasswd(SiteRole.GUEST);
			authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
			authorizationPageBeanId.setSiteId(request.getParameter("logoff_site"), authorizationPageFaced);
			if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
					authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, "")
					&& authorizationPageBeanId.getStrLogin().length() != 0) {

				searchBeanId.setOffset(0);
			}
		}

		return false;
	}

	/** action=logoff / logoff_usersite: end the session. */
	private boolean logoff(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, HttpSession session, SearchBean searchBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (searchBeanId.getAction().compareTo("logoff") == 0
				|| searchBeanId.getAction().compareTo("logoff_usersite") == 0) {
			String siteId = authorizationPageBeanId.getSiteId();
			String cokieSessionId = (String) session.getAttribute("cokie_session_id");
			session.invalidate();
			authorizationPageFaced.clearCookieFromBD(authorizationPageBeanId, cokieSessionId);
			servletContext.getRequestDispatcher("/Productlist.jsp?site=" + siteId).forward(request, response);
			return true;
		}
		return false;
	}

	/** Run the catalogue query (with criteria filter) plus the recommended / sponsored / new / top-review / footer lists and criteria labels. */
	private void loadProductlist(SearchBean searchBeanId, AuthorizationPageBean authorizationPageBeanId,
			ProductlistFaced productlistFaced) throws Exception {
		searchBeanId.Adp = productlistFaced.getSearchList("" + authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), Long.parseLong(authorizationPageBeanId.getCatalogId()),
				searchBeanId, authorizationPageBeanId);
		productlistFaced.getQuantitySearch(searchBeanId);
		searchBeanId.co1Adp = productlistFaced.getCoOneSearchDirect(authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogId(), searchBeanId,
				authorizationPageBeanId);
		searchBeanId.co2Adp = productlistFaced.getCoTwoSearchDirect("" + authorizationPageBeanId.getIntUserID(),
				authorizationPageBeanId.getSiteId(), authorizationPageBeanId.getCatalogId(), searchBeanId,
				authorizationPageBeanId);

		authorizationPageBeanId.setCatalogParentId("" + productlistFaced.getCatalogParentId(authorizationPageBeanId));

	}

	/** Fill every dropdown of the search form (currency, catalog tree, menu, criteria 1-10, dates). */
	private void populateSearchLists(SearchBean searchBeanId, ProductlistFaced productlistFaced) throws Exception {
		searchBeanId.setSelectCurrencyCd(productlistFaced.getXMLDBList("Productlist.jsp?currency_cd", "currencies",
				searchBeanId.getCurrencyCd(),
				"SELECT currency_cd , currency_desc  FROM currency  WHERE active = true"));

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
