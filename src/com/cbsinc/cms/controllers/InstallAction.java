package com.cbsinc.cms.controllers;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CreateShopBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.jms.controllers.Message;

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
 * Controller of Install.jsp: first-run installation of the database and the main site.
 */
@PageController(jspName = "Install.jsp")
public class InstallAction implements IAction {

	private HttpSession session;
	// private Message messageMail;
	private AuthorizationPageBean authorizationPageBeanId;
	private AuthorizationPageFaced authorizationPageFaced;
	private CreateShopBean createShopBean = null;

	public InstallAction() {
		createShopBean = new CreateShopBean();
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
	}

	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		session = request.getSession();

		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

		authorizationPageBeanId.setSelectCity(authorizationPageFaced.getXMLDBList("Authorization.jsp?city_id", "city",
				authorizationPageBeanId.getCityId(),
				"select  city_id , name  from  city where country_id =" + authorizationPageBeanId.getCountryId()));
		authorizationPageBeanId.setSelectCountry(authorizationPageFaced.getXMLDBList("Authorization.jsp?country_id",
				"country", authorizationPageBeanId.getCountryId(), "select country_id ,name from country"));

		if (authorizationPageBeanId.getIntUserID() == 1) {
			createShopBean.addSiteMainSitePgV2();
		}

	}

	/**
	 * Processes the submitted form: validates the parameters, applies the requested action through the faced and redirects or sets a message on the bean.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void action(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		session = request.getSession();

		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();

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
		if (request.getParameter("Address") != null)
			authorizationPageBeanId.setAddress(request.getParameter("Address"));
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

		createShopBean.addSite(authorizationPageBeanId);

		Message messageMail = new Message();
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
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("reg.login1.text"));
			authorizationPageFaced.initUserSite(authorizationPageBeanId.getIntUserID(), authorizationPageBeanId);
			response.sendRedirect("Productlist.jsp?offset=0");

		}

		if (authorizationPageBeanId.getIntLogined() == 2 && authorizationPageBeanId.getRezaltReg() == 0
				&& !authorizationPageBeanId.getStrPasswd().equals(SiteRole.GUEST_PASSWORD)) {
			authorizationPageBeanId
					.setStrMessage(authorizationPageBeanId.getLocalization(servletContext).getString("reg.login1.text")
							+ " " + authorizationPageBeanId.getStrLogin() + " "
							+ authorizationPageBeanId.getLocalization(servletContext).getString("reg.login3.wrong"));
			authorizationPageBeanId.setIntUserID(SiteRole.GUEST_ID);
			authorizationPageBeanId.setRoleId(SiteRole.GUEST_ROLE_ID);
			authorizationPageBeanId.setStrPasswd(SiteRole.GUEST_PASSWORD);
			authorizationPageBeanId.setStrLogin(SiteRole.GUEST);
		}

	}

}
