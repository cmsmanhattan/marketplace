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
 * Controller of SelectBigImage.jsp: picks a large image for a product.
 */
@PageController(jspName = "SelectBigImage.jsp")
public class SelectBigImageAction extends TemplateAction {

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
		;
		StringBuffer sbuff = new StringBuffer();

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		if (request.getParameter("bigimage_id") != null) {
			String bigimageId = request.getParameter("bigimage_id");
			if (bigimageId != null) {
				publisherBeanId.setBigimageId(bigimageId);
				productPostAllFaced.setBigImageNameByImageID(bigimageId, publisherBeanId);
			}
		}

		publisherBeanId.setSelectBigImages(productPostAllFaced.getComboBoxWithJavaScriptBigImage("bigimage_id",
				publisherBeanId.getBigimageId(), "SELECT big_images_id,imgname FROM big_images WHERE user_id = "
						+ authorizationPageBeanId.getIntUserID() + " ORDER BY big_images_id DESC ",
				"onChange=\"changeImage()\"", publisherBeanId));

		if (publisherBeanId.getBigimgname().lastIndexOf(".") != -1) {
			sbuff = new StringBuffer();
			sbuff.append("big_imgpositions/");
			sbuff.append(publisherBeanId.getBigimageId());
			sbuff.append(FileNames.extensionWithDot(publisherBeanId.getBigimgname()));
			publisherBeanId.setSelectBigImageUrl(sbuff.toString());
		} else {
			publisherBeanId.setSelectBigImageUrl("images/empty.gif");
		}

	}

}
