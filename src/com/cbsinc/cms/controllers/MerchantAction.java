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

import com.cbsinc.cms.AccountHistoryBean;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.MerchantBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.OrderFaced;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of Merchant.jsp: the seller's merchant settings.
 */
@PageController(jspName = "Merchant.jsp")
public class MerchantAction implements IAction {

	private AuthorizationPageFaced authorizationPageFaced;

	private OrderFaced orderFaced = null;

	public boolean isInternet = true;

	public MerchantAction() {
	}

	/**
	 * Copies the merchant form fields from the request into the bean.
	 */
	public void mappingForm(HttpServletRequest request, MerchantBean merchantBean) throws Exception {

		if (request.getParameter("c_name") != null)
			merchantBean.setCName(request.getParameter("c_name"));

		if (request.getParameter("trademark") != null)
			merchantBean.setTradeMark(request.getParameter("trademark"));
		if (request.getParameter("biz_description") != null)
			merchantBean.setBizDescription(request.getParameter("biz_description"));
		if (request.getParameter("formation_date") != null)
			merchantBean.setFormationDate(request.getParameter("formation_date"));
		if (request.getParameter("taxid") != null)
			merchantBean.setTaxid(request.getParameter("taxid"));
		if (request.getParameter("biz_address") != null)
			merchantBean.setBizAddress(request.getParameter("biz_address"));
		if (request.getParameter("biz_city") != null)
			merchantBean.setBizCity(request.getParameter("biz_city"));
		if (request.getParameter("biz_country") != null)
			merchantBean.setBizCountry(request.getParameter("biz_country"));
		if (request.getParameter("biz_zip") != null)
			merchantBean.setBizZip(request.getParameter("biz_zip"));
		if (request.getParameter("biz_phone") != null)
			merchantBean.setBizPhone(request.getParameter("biz_phone"));
		if (request.getParameter("biz_email") != null)
			merchantBean.setBizEmail(request.getParameter("biz_email"));

		if (request.getParameter("f_o_name") != null)
			merchantBean.setFoName(request.getParameter("f_o_name"));
		if (request.getParameter("f_o_address") != null)
			merchantBean.setFoAddress(request.getParameter("f_o_address"));
		if (request.getParameter("f_o_city") != null)
			merchantBean.setFoCity(request.getParameter("f_o_city"));
		if (request.getParameter("f_o_country") != null)
			merchantBean.setFoCountry(request.getParameter("f_o_country"));
		if (request.getParameter("f_o_zip") != null)
			merchantBean.setFoZip(request.getParameter("f_o_zip"));
		if (request.getParameter("f_o_ownership") != null)
			merchantBean.setFoOwnership(request.getParameter("f_o_ownership"));
		if (request.getParameter("f_o_phone") != null)
			merchantBean.setFoPhone(request.getParameter("f_o_phone"));
		if (request.getParameter("f_o_email") != null)
			merchantBean.setFoEmail(request.getParameter("f_o_email"));

		if (request.getParameter("s_o_name") != null)
			merchantBean.setSoName(request.getParameter("s_o_name"));
		if (request.getParameter("s_o_address") != null)
			merchantBean.setSoAddress(request.getParameter("s_o_address"));
		if (request.getParameter("s_o_city") != null)
			merchantBean.setSoCity(request.getParameter("s_o_city"));
		if (request.getParameter("s_o_country") != null)
			merchantBean.setSoCountry(request.getParameter("s_o_country"));
		if (request.getParameter("s_o_zip") != null)
			merchantBean.setSoZip(request.getParameter("s_o_zip"));
		if (request.getParameter("s_o_ownership") != null)
			merchantBean.setSoOwnership(request.getParameter("s_o_ownership"));
		if (request.getParameter("s_o_phone") != null)
			merchantBean.setSoPhone(request.getParameter("s_o_phone"));
		if (request.getParameter("s_o_email") != null)
			merchantBean.setSoEmail(request.getParameter("s_o_email"));

		if (request.getParameter("bank_name") != null)
			merchantBean.setBankName(request.getParameter("bank_name"));
		if (request.getParameter("bank_account_number") != null)
			merchantBean.setBankAccountNumber(request.getParameter("bank_account_number"));
		if (request.getParameter("bank_routing_number") != null)
			merchantBean.setBankRoutingNumber(request.getParameter("bank_routing_number"));
		if (request.getParameter("bank_address") != null)
			merchantBean.setBankAddress(request.getParameter("bank_address"));
		if (request.getParameter("bank_city") != null)
			merchantBean.setBankCity(request.getParameter("bank_city"));
		if (request.getParameter("bank_country") != null)
			merchantBean.setBankCountry(request.getParameter("bank_country"));
		if (request.getParameter("bank_zip") != null)
			merchantBean.setBankZip(request.getParameter("bank_zip"));
		if (request.getParameter("bank_phone") != null)
			merchantBean.setBankPhone(request.getParameter("bank_phone"));
		// if( request.getParameter("redirect") !=null ) redirect =
		// request.getParameter("redirect");
	}

	/**
	 * Handles POST: runs {@link #action} to process the submitted form (returns early if it redirected), then reloads the beans for the view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		MerchantBean merchantBean = null;
		AuthorizationPageBean authorizationPageBeanId;
		AccountHistoryBean accountHistoryBeanId;
		HttpSession session;

		if (request.getRemoteAddr().startsWith("192."))
			isInternet = false;
		if (request.getRemoteAddr().startsWith("10."))
			isInternet = false;

		session = request.getSession();
		if (orderFaced == null)
			orderFaced = ServiceLocator.getInstance().getOrderFaced();
		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		accountHistoryBeanId = (AccountHistoryBean) session.getAttribute("accountHistoryBeanId");
		merchantBean = (MerchantBean) session.getAttribute("merchantBean");

		if (authorizationPageBeanId == null || accountHistoryBeanId == null || merchantBean == null)
			return;

		request.setCharacterEncoding("UTF-8");

		mappingForm(request, merchantBean);

		Message messageMail = new Message();
		messageMail.put("@FirstName", authorizationPageBeanId.getStrFirstName());
		messageMail.put("@LastName", authorizationPageBeanId.getStrLastName());
		messageMail.put("@CName", merchantBean.getCName());

		messageMail.put("@TradeMark", merchantBean.getTradeMark());
		messageMail.put("@BizDescription", merchantBean.getBizDescription());
		messageMail.put("@FormationDate", merchantBean.getFormationDate());
		messageMail.put("@Taxid", merchantBean.getTaxid());
		messageMail.put("@BizAddress", merchantBean.getBizAddress());
		messageMail.put("@BizCity", merchantBean.getBizCity());
		messageMail.put("@BizCountry", merchantBean.getBizCountry());
		messageMail.put("@BizZip", merchantBean.getBizZip());
		messageMail.put("@BizPhone", merchantBean.getBizPhone());
		messageMail.put("@BizEmail", merchantBean.getBizEmail());

		messageMail.put("@FoName", merchantBean.getFoName());
		messageMail.put("@FoAddress", merchantBean.getFoAddress());
		messageMail.put("@FoCity", merchantBean.getFoCity());
		messageMail.put("@FoCountry", merchantBean.getFoCountry());
		messageMail.put("@FoZip", merchantBean.getFoZip());
		messageMail.put("@FoOwnership", merchantBean.getFoOwnership());
		messageMail.put("@FoPhone", merchantBean.getFoPhone());
		messageMail.put("@FoEmail", merchantBean.getFoEmail());

		messageMail.put("@SoName", merchantBean.getSoName());
		messageMail.put("@SoAddress", merchantBean.getSoAddress());
		messageMail.put("@SoCity", merchantBean.getSoCity());
		messageMail.put("@SoCountry", merchantBean.getSoCountry());
		messageMail.put("@SoZip", merchantBean.getSoZip());
		messageMail.put("@SoOwnership", merchantBean.getSoOwnership());
		messageMail.put("@SoPhone", merchantBean.getSoPhone());
		messageMail.put("@SoEmail", merchantBean.getSoEmail());

		messageMail.put("@BankName", merchantBean.getBankName());
		messageMail.put("@BankAccountNumber", merchantBean.getBankAccountNumber());
		messageMail.put("@BankRoutingNumber", merchantBean.getBankRoutingNumber());
		messageMail.put("@BankAddress", merchantBean.getBankAddress());
		messageMail.put("@BankCountry", merchantBean.getBankCountry());
		messageMail.put("@BankCity", merchantBean.getBankCity());
		messageMail.put("@BankZip", merchantBean.getBankZip());

		String sitePath = (String) request.getSession().getAttribute("site_path");
		String merchant = sitePath + File.separatorChar + "mail" + File.separatorChar + "merchant.txt";

		System.out.println("merchant path: " + merchant);

		AuthorizationPageBean ownerShop = authorizationPageFaced
				.getAuthorizationBeanOfRoleAdmin(authorizationPageBeanId.getSiteId());
		// messageMail.put("@ShopEmail", ownerShop.getStrEMail() ) ;

		MessageSender mqSender = new MessageSender(request.getSession(), SendMailMessageBean.messageQuery);
		Message message = new Message();
		message.put("to", ownerShop.getStrEMail());
		message.put("subject", "Merchant application");
		message.put("pathmessage", merchant);
		message.put("fields", messageMail);
		mqSender.send(message);

		authorizationPageBeanId.setStrMessage(" - Application was sent to manager.");
	}


	/**
	 * Handles GET: loads the page beans from the session, applies the request parameters and fills them for the XSL/JSP view.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
