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
import com.cbsinc.cms.CatalogListBean;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Content-management controller of FooterLinksListPost.jsp: edits the footer link list.
 */
@PageController(jspName = "FooterLinksListPost.jsp")
public class FooterLinksListPostAction implements IAction {

	// ResourceBundle resources = null ;
	ProductPostAllFaced productPostAllFaced;
	transient ResourceBundle setupResources = null;

	public FooterLinksListPostAction() {

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");

	}

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		HttpSession session = request.getSession();
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		if (request.getParameter("action") != null) {
			publisherBeanId.setAction(request.getParameter("action"));
			if (saveCard(request, response, servletContext, publisherBeanId))
				return;
		} else
			publisherBeanId.setAction("");

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
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		action(request, response, servletContext);
		if (response.isCommitted())
			return;
		productPostAllFaced.initPage(request.getParameter("product_id"), publisherBeanId, AuthorizationPageBeanId);
		// if insert and limmit not add message
		if (productPostAllFaced.isLimmitPostedMessages(AuthorizationPageBeanId, false)
				&& publisherBeanId.getSoftId().compareTo("-1") == 0) {
			AuthorizationPageBeanId.setStrMessage(
					AuthorizationPageBeanId.getLocalization(servletContext).getString("global_has_limmit_forsite"));
			response.sendRedirect("PostManager.jsp");
			return;
		}

		if (request.getParameter("action") != null) {

			publisherBeanId.setAction(request.getParameter("action"));

		} else
			publisherBeanId.setAction("");

		boolean jsfAdmin = false;
		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		String jsfAdminKey = authorizationPageFaced.getResourcesCmsSettings().getString("jsf_admin");
		if (jsfAdminKey == null || jsfAdminKey.equals(""))
			jsfAdmin = false;
		jsfAdminKey = jsfAdminKey.trim();
		jsfAdmin = jsfAdminKey.equals("true");
		publisherBeanId.setNameOfPage("FooterLinksListPost.jsp");
		if (jsfAdmin)
			response.sendRedirect("publisher/index.hrml");

	}

	/** action=save: insert a new card or update the edited one. */
	private boolean saveCard(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, PublisherBean publisherBeanId) throws Exception {
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) request.getSession()
				.getAttribute("authorizationPageBeanId");
		if (publisherBeanId.getAction().compareTo("save") == 0) {

			if (request.getParameter("bigimage_id") == null) {
				publisherBeanId.setBigimageId("-1");
			}
			if (request.getParameter("image_id") == null) {
				publisherBeanId.setImageId("-1");
			}

			publisherBeanId.setSiteId(AuthorizationPageBeanId.getSiteId());

			publisherBeanId.setStrSoftDescription("");
			if (publisherBeanId.getSoftId().compareTo("-1") == 0) {
				if (productPostAllFaced.isLimmitPostedMessages(AuthorizationPageBeanId, true)) {
					AuthorizationPageBeanId.setStrMessage(AuthorizationPageBeanId.getLocalization(servletContext)
							.getString("global_has_limmit_forsite"));
					response.sendRedirect("PostManager.jsp");
					return true;
				}

				productPostAllFaced.saveDescSoft(publisherBeanId, AuthorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("Productlist.jsp?offset=" + 0);
			} else {
				productPostAllFaced.updateDescSoft(publisherBeanId, AuthorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("Productlist.jsp?offset=" + AuthorizationPageBeanId.getOffsetLastPage()
						+ "&catalog_id=" + AuthorizationPageBeanId.getCatalogId());
			}
		}
		return false;
	}

	/** Catalogue navigator: parent_id / row / del / offset. */
	private void readCatalogNavigation(HttpServletRequest request, PublisherBean publisherBeanId,
			CatalogListBean catalogListBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("parent_id") != null) {

			authorizationPageBeanId.setCatalogParentId(request.getParameter("parent_id"));
		}

		if (request.getParameter("type_id") != null) {
			publisherBeanId.setTypeId(request.getParameter("type_id"));
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
		}
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

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		PublisherBean publisherBeanId;
		CatalogListBean catalogListBeanId;
		AuthorizationPageBean authorizationPageBeanId;
		HttpSession session;

		session = request.getSession();
		publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();

		if (publisherBeanId == null || catalogListBeanId == null || authorizationPageBeanId == null
				|| productPostAllFaced == null)
			return;

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		readCatalogNavigation(request, publisherBeanId, catalogListBeanId, authorizationPageBeanId);
//		 End Novigator ---

		readCriteria(request, publisherBeanId);

		if (request.getParameter("insert") != null) {
			if (request.getParameter("insert").compareTo("true") == 0)
				publisherBeanId.setSoftId("-1");

		}

		readCardFields(request, publisherBeanId, authorizationPageBeanId);

		if (authorizationPageBeanId.getIntUserID() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("session_time_out"));
			response.sendRedirect("Authorization.jsp");
		} else
			publisherBeanId.setUserId("" + authorizationPageBeanId.getIntUserID());

		if (authorizationPageBeanId.getRoleId() != 2) {
			authorizationPageBeanId
					.setStrMessage("You don't have access to add position , send mail to grabko@mail.ru for access");
			response.sendRedirect("Authorization.jsp");
		}

	}

}
