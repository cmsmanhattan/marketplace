package com.cbsinc.cms.controllers;

import com.cbsinc.cms.utils.FileNames;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.annotations.PageController;
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

/**
 * Controller of SelectCatalogImage.jsp: picks the image of a catalogue.
 */
@PageController(jspName = "SelectCatalogImage.jsp")
public class SelectCatalogImageAction extends TemplateAction {

	/**
	 * Template-method hook: processes the request and fills the beans; called by both doGet and doPost.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		PublisherBean publisherBeanId = getPublisherBean();
		ProductPostAllFaced productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		AuthorizationPageBean authorizationPageBeanId = getAuthorizationPageBean();

		StringBuffer sbuff = new StringBuffer();

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		if (request.getParameter("catalog_images_id") != null) {
			String catalogImageId = request.getParameter("catalog_images_id");
			if (catalogImageId != null) {
				publisherBeanId.setCatalogImageId(catalogImageId);
				productPostAllFaced.setCatalogImageNameByImageID(catalogImageId, publisherBeanId);
			}
		}

		publisherBeanId.setSelectCatalogImages(productPostAllFaced.getComboBoxWithJavaScriptBigImage("catalog_images_id",
				publisherBeanId.getCatalogImageId(), "SELECT catalog_images_id,imgname FROM catalog_images WHERE user_id = "
						+ authorizationPageBeanId.getIntUserID() + " ORDER BY catalog_images_id DESC ",
				"onChange=\"changeImage()\"", publisherBeanId));

		if (publisherBeanId.getCatalogImgname().lastIndexOf(".") != -1) {
			sbuff = new StringBuffer();
			sbuff.append("imgcatalog/");
			sbuff.append(publisherBeanId.getCatalogImageId());
			sbuff.append(FileNames.extensionWithDot(publisherBeanId.getCatalogImgname()));
			publisherBeanId.setSelectCatalogImageUrl(sbuff.toString());
		} else {
			publisherBeanId.setSelectCatalogImageUrl("images/empty.gif");
		}

	}

}
