package com.cbsinc.cms.controllers;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.jms.controllers.AddUserToMailMessageJamesBean;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;

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
 * Controller of RegPage.jsp: new user registration; validation codes come from AuthorizationPageFaced.isRegCorrect.
 */
@PageController(jspName = "RegPage.jsp")
public class RegistrationAction implements IAction {

	HttpSession session;
	AuthorizationPageFaced authorizationPageFaced = null;
	AuthorizationPageBean authorizationPageBeanId = null;
	String createCabinet = "";
	String genCode = "";

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

		String sessionId = authorizationPageFaced.getCokieSessionId((HttpServletRequest) request,
				(HttpServletResponse) response);
		int rezaltReg = authorizationPageFaced.isRegCorrect(authorizationPageBeanId.getStrLogin(),
				authorizationPageBeanId.getStrPasswd(), authorizationPageBeanId.getStrCPasswd(),
				authorizationPageBeanId.getStrFirstName(), authorizationPageBeanId.getStrLastName(),
				authorizationPageBeanId.getStrCompany(), authorizationPageBeanId.getStrEMail(),
				authorizationPageBeanId.getStrPhone(), authorizationPageBeanId.getStrMPhone(),
				authorizationPageBeanId.getStrFax(), authorizationPageBeanId.getStrIcq(),
				authorizationPageBeanId.getStrWebsite(), authorizationPageBeanId.getStrQuestion(),
				authorizationPageBeanId.getStrAnswer(), null, authorizationPageBeanId.getCountryId(),
				authorizationPageBeanId.getCityId(), authorizationPageBeanId.getCurrencyId(), sessionId,
				authorizationPageBeanId);
		authorizationPageBeanId.setRezaltReg(rezaltReg);
		if (rezaltReg == 0) {

			if (authorizationPageFaced.getResourcesCmsSettings().getString("james_register").contains("true")) {
				MessageSender mqSender = new MessageSender(request.getSession(),
						AddUserToMailMessageJamesBean.messageQuery);
				Message message = new Message();
				message.put("user_login", authorizationPageBeanId.getStrLogin());
				message.put("user_password", authorizationPageBeanId.getStrPasswd());
				message.put("user_domain", authorizationPageBeanId.getHost());
				mqSender.send(message);
			}

			authorizationPageFaced.initUserSite(authorizationPageBeanId.getIntUserID(), authorizationPageBeanId);
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("welcom_after_registration"));

			if (createCabinet.equals("true"))
				servletContext.getRequestDispatcher("Productlist.jsp?action=create_site").forward(request, response);
			else {
				response.sendRedirect("Productlist.jsp?offset=0");
			}

			return;
		} else {

			switch (rezaltReg) {
			case 1:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.login.empty"));
				break;
			case 2:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.password1.empty"));
				break;
			case 12:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.password1.short"));
				break;
			case 10:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.password1.empty"));
				break;
			case 13:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.login.not_english"));
				break;
			case 11:
				authorizationPageBeanId.setStrMessage("select city");
				break;
			case 3:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.firstname.empty"));
				break;
			case 4:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.lastname.empty"));
				break;
			case 5:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.email.empty"));
				break;
			case 6:
				authorizationPageBeanId.setStrMessage("Field Question  is empty");
				break;
			case 7:
				authorizationPageBeanId.setStrMessage("Field Answer  is empty");
				break;
			case 8:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.email.wrong"));
				break;
			case 9:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.password.wrong"));
				break;
			case -1:
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("reg.login1.text")
								+ authorizationPageBeanId.getStrLogin()
								+ authorizationPageBeanId.getLocalization(servletContext).getString("reg.login2.text"));
				break;

			}

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

		authorizationPageBeanId.setIntUserID(1);
		authorizationPageBeanId.setRoleId(1);
		authorizationPageBeanId.setStrPasswd("");
		authorizationPageBeanId.setStrLogin("");
		// response.sendRedirect("RegPage.jsp");

	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
	}

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		session = request.getSession();
		genCode = (String) request.getSession().getAttribute("gen_number");
		authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0);

		if (request.getParameter("Login") != null)
			authorizationPageBeanId.setStrLogin(request.getParameter("Login"));
		if (request.getParameter("Passwd1") != null)
			authorizationPageBeanId.setStrPasswd(request.getParameter("Passwd1"));
		if (request.getParameter("Passwd2") != null)
			authorizationPageBeanId.setStrCPasswd(request.getParameter("Passwd2"));
		if (request.getParameter("FName") != null)
			authorizationPageBeanId.setStrFirstName(request.getParameter("FName"));
		if (request.getParameter("LName") != null)
			authorizationPageBeanId.setStrLastName(request.getParameter("LName"));
		if (request.getParameter("Company") != null)
			authorizationPageBeanId.setStrCompany(request.getParameter("Company"));
		if (request.getParameter("EMail") != null)
			authorizationPageBeanId.setStrEMail(request.getParameter("EMail"));
		if (request.getParameter("Phone") != null)
			authorizationPageBeanId.setStrPhone(request.getParameter("Phone"));
		if (request.getParameter("MPhone") != null)
			authorizationPageBeanId.setStrMPhone(request.getParameter("MPhone"));
		if (request.getParameter("Fax") != null)
			authorizationPageBeanId.setStrFax(request.getParameter("Fax"));
		if (request.getParameter("Icq") != null)
			authorizationPageBeanId.setStrIcq(request.getParameter("Icq"));
		if (request.getParameter("Website") != null)
			authorizationPageBeanId.setStrWebsite(request.getParameter("Website"));
		if (request.getParameter("Question") != null)
			authorizationPageBeanId.setStrQuestion(request.getParameter("Question"));
		if (request.getParameter("Answer") != null)
			authorizationPageBeanId.setStrAnswer(request.getParameter("Answer"));
		if (request.getParameter("country_id") != null)
			authorizationPageBeanId.setCountryId(request.getParameter("country_id"));
		if (request.getParameter("city_id") != null)
			authorizationPageBeanId.setCityId(request.getParameter("city_id"));
		if (request.getParameter("currency_id") != null)
			authorizationPageBeanId.setCurrencyId(request.getParameter("currency_id"));
		if (request.getParameter("site_id") != null) {
			authorizationPageBeanId.setSiteId(request.getParameter("site_id"), authorizationPageFaced);
		}

	}

}
