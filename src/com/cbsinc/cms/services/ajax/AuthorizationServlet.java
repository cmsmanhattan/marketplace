package com.cbsinc.cms.services.ajax;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
 * собственностью.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Class
 *
 * @web.servlet name="authorization" display-name="Name for authorization"
 *              description="Description for authorization"
 * @web.servlet-mapping url-pattern="/authorization"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class AuthorizationServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	transient ResourceBundle setupResources = null;

	public AuthorizationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig", response.getLocale());
		// HttpSession session = request.getSession(false);
		HttpSession session = request.getSession();

		AuthorizationPageFaced authorizationPageFaced = null;
		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AuthorizationPageBean AuthorizationPageBeanId = (AuthorizationPageBean) session
				.getAttribute("authorizationPageBeanId");

		if (request.getParameter("Login") != null)
			AuthorizationPageBeanId.setStrLogin(request.getParameter("Login"));
		if (request.getParameter("Passwd1") != null)
			AuthorizationPageBeanId.setStrPasswd(request.getParameter("Passwd1"));
		if (request.getParameter("Passwd2") != null)
			AuthorizationPageBeanId.setStrCPasswd(request.getParameter("Passwd2"));
		if (request.getParameter("FName") != null)
			AuthorizationPageBeanId.setStrFirstName(request.getParameter("FName"));
		if (request.getParameter("LName") != null)
			AuthorizationPageBeanId.setStrLastName(request.getParameter("LName"));
		if (request.getParameter("Company") != null)
			AuthorizationPageBeanId.setStrCompany(request.getParameter("Company"));
		if (request.getParameter("EMail") != null)
			AuthorizationPageBeanId.setStrEMail(request.getParameter("EMail"));
		if (request.getParameter("Phone") != null)
			AuthorizationPageBeanId.setStrPhone(request.getParameter("Phone"));
		if (request.getParameter("MPhone") != null)
			AuthorizationPageBeanId.setStrMPhone(request.getParameter("MPhone"));
		if (request.getParameter("Fax") != null)
			AuthorizationPageBeanId.setStrFax(request.getParameter("Fax"));
		if (request.getParameter("Icq") != null)
			AuthorizationPageBeanId.setStrIcq(request.getParameter("Icq"));
		if (request.getParameter("Website") != null)
			AuthorizationPageBeanId.setStrWebsite(request.getParameter("Website"));
		if (request.getParameter("Question") != null)
			AuthorizationPageBeanId.setStrQuestion(request.getParameter("Question"));
		if (request.getParameter("Answer") != null)
			AuthorizationPageBeanId.setStrAnswer(request.getParameter("Answer"));
		if (request.getParameter("country_id") != null)
			AuthorizationPageBeanId.setCountryId(request.getParameter("country_id"));
		if (request.getParameter("city_id") != null)
			AuthorizationPageBeanId.setCityId(request.getParameter("city_id"));
		if (request.getParameter("currency_id") != null)
			AuthorizationPageBeanId.setCurrencyId(request.getParameter("currency_id"));
		if (request.getParameter("site_id") != null) {
			AuthorizationPageBeanId.setSiteId(request.getParameter("site_id"), authorizationPageFaced);
		}

		response.getWriter().write("<document>");
		response.getWriter()
				.write(authorizationPageFaced.getAjaxDBList("Authorization.jsp?city_id", "city",
						AuthorizationPageBeanId.getCityId(),
						"select  city_id , name  from  city where country_id ="
								+ AuthorizationPageBeanId.getCountryId() + " and locale = '"
								+ AuthorizationPageBeanId.getLocale() + "' "));
		response.getWriter().write(authorizationPageFaced.getAjaxDBList("Authorization.jsp?country_id", "country",
				AuthorizationPageBeanId.getCountryId(),
				"select country_id ,name from country where locale = '" + AuthorizationPageBeanId.getLocale() + "' "));
		response.getWriter().write(authorizationPageFaced.getAjaxDBList("Authorization.jsp?currency_id", "currency",
				"3", "SELECT currency_id , currency_desc FROM currency  WHERE active = true"));
		response.getWriter().write(authorizationPageFaced.getAjaxDBList("Authorization.jsp?site_id", "site",
				AuthorizationPageBeanId.getSiteId(), "SELECT  site_id, host FROM  site WHERE  active = true"));
		response.getWriter().write("</document>");
	}

}
