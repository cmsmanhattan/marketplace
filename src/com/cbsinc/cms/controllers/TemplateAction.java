package com.cbsinc.cms.controllers;

import com.cbsinc.cms.AccountHistoryBean;
import com.cbsinc.cms.AccountHistoryDetalBean;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.CatalogAddBean;
import com.cbsinc.cms.CatalogEditBean;
import com.cbsinc.cms.CatalogListBean;
import com.cbsinc.cms.OperationAmountBean;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.PayBean;
import com.cbsinc.cms.PayGatewayBean;
import com.cbsinc.cms.PayGatewayListBean;
import com.cbsinc.cms.PrePayBean;
import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.RequestFilter;
import com.cbsinc.cms.jms.controllers.Message;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Base class for controllers written as a template method: doGet/doPost call {@link #action}, and typed getters expose the session beans.
 */
public abstract class TemplateAction implements IAction {

	private HttpSession session = null;

	/**
	 * @return the http servlet request
	 */
	public HttpServletRequest getHttpServletRequest() {
		return RequestFilter.getRequest();
	}

	/**
	 * @return the servlet context
	 */
	public ServletContext getServletContext() {
		return RequestFilter.getServletContext();
	}

	/**
	 * @return the http servlet rsponse
	 */
	public HttpServletResponse getHttpServletRsponse() {
		return RequestFilter.getResponse();
	}

	// private Optional<ProductlistBean> productlistBean = Optional.empty();

	/**
	 * Handles POST by running {@link #action}.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		session = request.getSession();
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
	}

	/**
	 * Handles GET by running {@link #action}.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		session = request.getSession();
		action(request, response, servletContext);
		if (response.isCommitted())
			return;
	}

	/**
	 * Template-method hook: processes the request and fills the beans; called by both doGet and doPost.
	 *
	 * @throws Exception on persistence or rendering failure
	 */
	public abstract void action(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContextOpts) throws Exception;

	/**
	 * @return the message mail
	 */
	public Message getMessageMail() {
		return new Message();
	}

	/**
	 * @return the publisher bean
	 */
	public PublisherBean getPublisherBean() {
		return (PublisherBean) session.getAttribute("publisherBeanId");
	}

	/**
	 * @return the catalog list bean
	 */
	public CatalogListBean getCatalogListBean() {
		return (CatalogListBean) session.getAttribute("catalogListBeanId");
	}

	/**
	 * @return the catalog edit bean
	 */
	public CatalogEditBean getCatalogEditBean() {
		return (CatalogEditBean) session.getAttribute("catalogEditBeanId");
	}

	/**
	 * @return the catalog add bean
	 */
	public CatalogAddBean getCatalogAddBean() {
		return (CatalogAddBean) session.getAttribute("catalogAddBeanId");
	}

	/**
	 * @return the authorization page bean
	 */
	public AuthorizationPageBean getAuthorizationPageBean() {
		return (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
	}

	/**
	 * @return the account history detal bean
	 */
	public AccountHistoryDetalBean getAccountHistoryDetalBean() {
		return (AccountHistoryDetalBean) session.getAttribute("accountHistoryDetalBeanId");
	}

	/**
	 * @return the account history bean
	 */
	public AccountHistoryBean getAccountHistoryBean() {
		return (AccountHistoryBean) session.getAttribute("accountHistoryBeanId");
	}

	/**
	 * @return the order bean
	 */
	public OrderBean getOrderBean() {
		return (OrderBean) session.getAttribute("orderBeanId");
	}

	/**
	 * @return the pay bean
	 */
	public PayBean getPayBean() {
		return (PayBean) session.getAttribute("payBeanId");
	}

	/**
	 * @return the operation amount bean
	 */
	public OperationAmountBean getOperationAmountBean() {
		return (OperationAmountBean) session.getAttribute("operationAmountBeanId");
	}

	/**
	 * @return the pre pay bean
	 */
	public PrePayBean getPrePayBean() {
		return (PrePayBean) session.getAttribute("prePayBeanId");
	}

	/**
	 * @return the pay gateway list bean
	 */
	public PayGatewayListBean getPayGatewayListBean() {
		return (PayGatewayListBean) session.getAttribute("payGatewayListBeanId");
	}

	/**
	 * @return the pay gateway bean
	 */
	public PayGatewayBean getPayGatewayBean() {
		return (PayGatewayBean) session.getAttribute("payGatewayBeanId");
	}

}
