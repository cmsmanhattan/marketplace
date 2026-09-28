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
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@PageController( jspName = "Co1ProductUserPost.jsp" )
/**
 * User-side variant of Co1ProductPostAction for the seller's own products.
 */
public class Co1ProductPostUserAction implements IAction {

	boolean isCriteriaByCatalog = false;
	ProductPostAllFaced productPostAllFaced;
	transient ResourceBundle setupResources = null;

	public Co1ProductPostUserAction() {
		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");
		isCriteriaByCatalog = setupResources.getString("is_criteria_by_catalog").equals("true");
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
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");
		CatalogListBean catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		CatalogEditBean catalogEditBeanI = (CatalogEditBean) session.getAttribute("catalogEditBeanId");
		CatalogAddBean catalogAddBeanId = (CatalogAddBean) session.getAttribute("catalogAddBeanId");

		// POST
		if (request.getParameter("action") != null) {
			publisherBeanId.setAction(request.getParameter("action"));
			if (addCatalog(request, response, publisherBeanId, catalogAddBeanId, authorizationPageBeanId))
				return;
			if (editCatalog(request, response, publisherBeanId, catalogListBeanId, authorizationPageBeanId,
					catalogEditBeanI))
				return;
			if (saveCard(request, response, servletContext, publisherBeanId, authorizationPageBeanId,
					productPostAllFaced))
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
		AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		String notselected = authorizationPageBeanId.getLocalization(servletContext).getString("notselected");
		CatalogListBean catalogListBeanId = (CatalogListBean) session.getAttribute("catalogListBeanId");
		CatalogEditBean catalogEditBeanI = (CatalogEditBean) session.getAttribute("catalogEditBeanId");
		CatalogAddBean catalogAddBeanId = (CatalogAddBean) session.getAttribute("catalogAddBeanId");

		action(request, response, servletContext);
		if (response.isCommitted())
			return;

		productPostAllFaced.initPage(request.getParameter("product_id"), publisherBeanId, authorizationPageBeanId);
		// if insert and limmit not add message
		if (productPostAllFaced.isLimmitPostedMessages(authorizationPageBeanId, false)
				&& publisherBeanId.getSoftId().compareTo("-1") == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("global_has_limmit_forsite"));
			response.sendRedirect("PostManager.jsp");
			return;
		}

		loadCriteriaLabels(publisherBeanId, productPostAllFaced, isCriteriaByCatalog);
		loadCriteriaLists(publisherBeanId, productPostAllFaced, notselected, isCriteriaByCatalog);

		if (request.getParameter("action") != null) {
			publisherBeanId.setAction(request.getParameter("action"));
			if (addCatalog(request, response, publisherBeanId, catalogAddBeanId, authorizationPageBeanId))
				return;
			if (editCatalog(request, response, publisherBeanId, catalogListBeanId, authorizationPageBeanId,
					catalogEditBeanI))
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
		publisherBeanId.setNameOfPage("Co1ProductPost.jsp");
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
				response.sendRedirect("ProductPostCre.jsp");
				return true;
			}
		}
		return false;
	}

	/** action=edit: rename / re-image / move a catalogue folder. */
	private boolean editCatalog(HttpServletRequest request, HttpServletResponse response,
			PublisherBean publisherBeanId, CatalogListBean catalogListBeanId,
			AuthorizationPageBean authorizationPageBeanId, CatalogEditBean catalogEditBeanI) throws Exception {
		if (publisherBeanId.getAction().compareTo("edit") == 0) {

			if (request.getParameter("row") != null) {
				int index = catalogListBeanId.stringToInt(request.getParameter("row"));
				catalogEditBeanI.setIndxSelect(index);
			}
			if (request.getParameter("name") != null) {
				catalogEditBeanI.setName(request.getParameter("name"));
			}

			if (request.getParameter("catalog_id") != null) {
				authorizationPageBeanId.setCatalogId(request.getParameter("catalog_id"));
			}

			if (request.getMethod().toUpperCase().compareTo("POST") == 0) {
				catalogEditBeanI.editCatalog(authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("ProductPostCre.jsp");
				return true;
			}

		}
		return false;
	}

	/** action=save: insert a new card or update the edited one (with all its criteria and display flags). */
	private boolean saveCard(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, PublisherBean publisherBeanId,
			AuthorizationPageBean authorizationPageBeanId,
			ProductPostAllFaced productPostAllFaced) throws Exception {
		if (publisherBeanId.getAction().compareTo("save") == 0) {

			if (request.getParameter("bigimage_id") == null) {
				publisherBeanId.setBigimageId("-1");
			}
			if (request.getParameter("image_id") == null) {
				publisherBeanId.setImageId("-1");
			}

			publisherBeanId.setSiteId(authorizationPageBeanId.getSiteId());

			if (publisherBeanId.getSoftId().compareTo("-1") == 0) {

				if (productPostAllFaced.isLimmitPostedMessages(authorizationPageBeanId, true)) {
					authorizationPageBeanId.setStrMessage(authorizationPageBeanId.getLocalization(servletContext)
							.getString("global_has_limmit_forsite"));
					response.sendRedirect("PostManager.jsp");
					return true;
				}

				productPostAllFaced.saveDescSoft(publisherBeanId, authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect(
						"Productlist.jsp?offset=" + 0 + "&catalog_id=" + authorizationPageBeanId.getCatalogId());
			} else {
				productPostAllFaced.updateDescSoft(publisherBeanId, authorizationPageBeanId);
				publisherBeanId.setAction("");
				response.sendRedirect("Productlist.jsp?offset=" + authorizationPageBeanId.getOffsetLastPage()
						+ "&catalog_id=" + authorizationPageBeanId.getCatalogId());
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

	/** All card fields posted by the form: names, catalogue, type, price, currency, descriptions, images, files, sale logic. */
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

	/** Labels of the ten criteria for this site. */
	private void loadCriteriaLabels(PublisherBean publisherBeanId, ProductPostAllFaced productPostAllFaced,
			boolean isCriteriaByCatalog) throws Exception {
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
	private void loadCriteriaLists(PublisherBean publisherBeanId, ProductPostAllFaced productPostAllFaced,
			String notselected, boolean isCriteriaByCatalog) throws Exception {
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

		if (authorizationPageBeanId.getRoleId() == 0) {
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("post_message_notaccess_admin"));
			response.sendRedirect("Authorization.jsp");
		}

	}

}
