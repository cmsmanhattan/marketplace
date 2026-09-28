package com.cbsinc.cms.controllers;

import java.util.Map;

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
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Login controller (Authorization.jsp): signs the user in by login/password, switches the active site, and prepares the mail placeholders. Credentials are verified by AuthorizationPageFaced with parameterised queries and PBKDF2 hashes.
 */
@PageController(jspName = "Authorization.jsp")
public class AuthorizationAction extends TemplateAction {

	/**
	 * Reads Login/Passwd1/site_id, verifies the credentials and either fills the session bean or sets an error message.
	 *
	 * @throws Exception on failure
	 */
	@Override
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContextOpts)
			throws Exception {

		AuthorizationPageFaced authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		AuthorizationPageBean authorizationPageBeanId = getAuthorizationPageBean();
		Map messageMail = getMessageMail();
		ResourceBundle localeResource = PropertyResourceBundle.getBundle("localization", response.getLocale());

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		if (request.getParameter("Login") != null)
			authorizationPageBeanId.setStrLogin(request.getParameter("Login"));
		if (request.getParameter("Passwd1") != null)
			authorizationPageBeanId.setStrPasswd(request.getParameter("Passwd1"));
		if (request.getParameter("Message") != null)
			authorizationPageBeanId.setStrMessage("" + request.getParameter("Message"));
		if (request.getParameter("site_id") != null) {
			authorizationPageBeanId.setSiteId(request.getParameter("site_id"), authorizationPageFaced);
		}

		messageMail.clear();
		messageMail.put("@FirstName", authorizationPageBeanId.getStrFirstName());
		messageMail.put("@LastName", authorizationPageBeanId.getStrLastName());
//		    messageMail.put("@NumberOfOrder", orderBeanId.getOrder_id()  ) ;
//		    messageMail.put("@ContactPerson", orderBeanId.getContact_person()  ) ;
		messageMail.put("@Balans", "" + authorizationPageFaced.getBalans(authorizationPageBeanId.getIntUserID()));
		messageMail.put("@Phone", authorizationPageBeanId.getStrPhone());
//		    messageMail.put("@Address", orderBeanId.getshipment_address() ) ;
		messageMail.put("@City", authorizationPageBeanId.getStrCity());
		messageMail.put("@Contry", authorizationPageBeanId.getStrCountry());
		messageMail.put("@CustomerEmail", authorizationPageBeanId.getStrEMail());
		messageMail.put("@CustomerFax", authorizationPageBeanId.getStrFax());

//		    messageMail.put("@CustomerCommentariy", orderBeanId.getshipment_description() ) ;
//		    messageMail.put("@ProductCount", "" +  orderBeanId.getProductsListSize(request)  ) ;
		String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
				(HttpServletResponse) response);

		if (authorizationPageFaced.isLoginCorrect(authorizationPageBeanId.getStrLogin(),
				authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId, sessionId)
				&& authorizationPageBeanId.getStrLogin().length() != 0) {

			authorizationPageBeanId.setStrMessage(localeResource.getString("reg.login1.text"));
			authorizationPageFaced.initUserSite(authorizationPageBeanId.getIntUserID(), authorizationPageBeanId);

			response.sendRedirect("Productlist.jsp?offset=0");
			return;

		}

		if (authorizationPageBeanId.getIntLogined() == 2 && authorizationPageBeanId.getRezaltReg() == 0
				&& !authorizationPageBeanId.getStrPasswd().equals(SiteRole.GUEST_PASSWORD)) {
			authorizationPageBeanId.setStrMessage(localeResource.getString("reg.login1.text") + " "
					+ authorizationPageBeanId.getStrLogin() + " " + localeResource.getString("reg.login3.wrong"));
			authorizationPageBeanId.setIntUserID(SiteRole.GUEST_ID);
			authorizationPageBeanId.setRoleId(SiteRole.GUEST_ROLE_ID);
			authorizationPageBeanId.setStrPasswd(SiteRole.GUEST_PASSWORD);
			authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
		}

		authorizationPageBeanId.setSelectCity(authorizationPageFaced.getXMLDBList("Authorization.jsp?city_id", "city",
				authorizationPageBeanId.getCityId(),
				"select  city_id , name  from  city where country_id =" + authorizationPageBeanId.getCountryId()
						+ " and locale = '" + authorizationPageBeanId.getLocale() + "' "));
		authorizationPageBeanId.setSelectCountry(authorizationPageFaced.getXMLDBList("Authorization.jsp?country_id",
				"country", authorizationPageBeanId.getCountryId(),
				"select country_id ,name from country  where locale = '" + authorizationPageBeanId.getLocale() + "' "));
		authorizationPageBeanId.setSelectCurrency(authorizationPageFaced.getXMLDBList("Authorization.jsp?currency_id",
				"currency", "3", "SELECT currency_id , currency_desc FROM currency  WHERE active = true"));
		authorizationPageBeanId.setSelectSite(authorizationPageFaced.getXMLDBList("Authorization.jsp?site_id", "site",
				authorizationPageBeanId.getSiteId(), "SELECT  site_id, host FROM  site WHERE  active = true"));
		authorizationPageBeanId.setSelectMenuCatalog(authorizationPageFaced.getMenuXMLDBList(
				"Productlist.jsp?catalog_id", "menu", authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable , parent_id  from catalog   where  active = true and parent_id = -2 and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and lang_id = "
						+ authorizationPageBeanId.getLangId()
						+ " or parent_id in (select catalog_id   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + "  and parent_id = -2 )"));

	}

}
