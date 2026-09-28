package com.cbsinc.cms.controllers;

import com.cbsinc.cms.utils.Validation;
import java.io.File;
import java.util.Enumeration;

import com.cbsinc.cms.AccountHistoryBean;
import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.OrderBean;
import com.cbsinc.cms.annotations.PageController;
import com.cbsinc.cms.annotations.PutActionRequestMapping;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.OrderFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller of Order.jsp: the customer's basket and checkout, and the order view for operational staff.
 *
 * <p>Access rules (pass 33): the owner of an order may edit and pay for it; users with role 2, 3 or 4 may open any order of the same site and change its pay/delivery status, which never moves money or ownership. Any other user is refused.</p>
 */
@PageController(jspName = "Order.jsp")
public class OrderAction implements IAction {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(OrderAction.class);
	/**
	 * FIX: the buyer and the seller got no in-app notice when an order was
	 * saved or its status changed (orders_hist only fed the date/status
	 * reports). Also tells both sides when the order is sent to a resolution
	 * center.
	 */
	private void notifyOrderParties(OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId,
			HttpServletRequest request) {
		try {
			com.cbsinc.cms.faceds.NotificationsFaced nf = new com.cbsinc.cms.faceds.NotificationsFaced();
			nf.notifyOrderChanged(orderBeanId.getOrderId(), authorizationPageBeanId.getSiteId(),
					authorizationPageBeanId.getIntUserID());
			String center = request.getParameter("resolution_center_id");
			if (orderBeanId.isResolutionCenterEnabled() && center != null && center.matches("-?\\d{1,18}")
					&& !"0".equals(center) && !"-1".equals(center))
				nf.notifyResolutionChanged(orderBeanId.getOrderId(), authorizationPageBeanId.getSiteId(),
						"sent to " + orderBeanId.getResolutionCenterName(), authorizationPageBeanId.getIntUserID());
		} catch (Exception ex) {
			log.error("notifyOrderParties", ex);
		}
	}

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


	AuthorizationPageFaced authorizationPageFaced;

	OrderFaced orderFaced = null;
	ProductlistFaced productlistFaced = null;

	public boolean isInternet = true;

	public OrderAction() {
	}


	/**
	 * Mapped to action=add with PUT. Note: the forms submit POST, so this mapping never fires; the live path is the private dispatch below.
	 */
	@PutActionRequestMapping(action = "add")
	public void addOrder(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception
	{

		OrderBean orderBeanId;
		// Map messageMail;
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
		orderBeanId = new OrderBean();
		request.setAttribute("orderBeanId", orderBeanId);
		orderBeanId = (OrderBean) request.getAttribute("orderBeanId");
		if (authorizationPageBeanId == null || accountHistoryBeanId == null || orderBeanId == null)
			return;

		addPosition(request, servletContext, orderBeanId, authorizationPageBeanId);

		response(request, orderBeanId, authorizationPageBeanId);
	}



	/**
	 * Handles POST by delegating to {@link #doGet}.
	 *
	 * @throws Exception on failure
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {
		request.getQueryString();
		Cookie cookie = new Cookie("payment_page", ((HttpServletRequest) request).getRequestURI());
		cookie.setMaxAge(60 * 60); // 1 hour
		response.addCookie(cookie);
		doGet(request, response, servletContext);
	}

	/**
	 * Copies the checkout form fields (shipping address, contact, shipping company, ...) from the request into the order bean.
	 *
	 * @param request current request
	 * @param orderBeanId bean to fill
	 * @throws Exception on encoding failure
	 */
	public void mappingForm(HttpServletRequest request, OrderBean orderBeanId) throws Exception {
		if (request.getParameter("city_id") != null)
			orderBeanId.setCityId(request.getParameter("city_id"));
		if (request.getParameter("country_id") != null)
			orderBeanId.setCountryId(request.getParameter("country_id"));
		if (request.getParameter("order_currency_id") != null)
			orderBeanId.setOrderCurrencyId(request.getParameter("order_currency_id"));
		if (request.getParameter("delivery_amoun") != null)
			orderBeanId.setDeliveryAmoun(request.getParameter("delivery_amoun"));
		if (request.getParameter("delivery_timeend") != null)
			orderBeanId.setDeliveryTimeend(request.getParameter("delivery_timeend"));
		if (request.getParameter("end_amount") != null)
			orderBeanId.setEndAmount(request.getParameter("end_amount"));
		if (request.getParameter("order_amount") != null)
			orderBeanId.setOrderAmount(request.getParameter("order_amount"));
		if (request.getParameter("order_tax") != null)
			orderBeanId.setOrderTax(request.getParameter("order_tax"));
		if (request.getParameter("order_delivery_long") != null)
			orderBeanId.setOrderDeliveryLong(request.getParameter("order_delivery_long"));
		if (request.getParameter("order_paystatus") != null)
			orderBeanId.setOrderPaystatus(request.getParameter("order_paystatus"));
		if (request.getParameter("order_status") != null)
			orderBeanId.setDeliverystatusId(request.getParameter("order_status"));

		// Carrier choice. If the form did not send it (first visit, or a page
		// built before the field existed) keep what the order already has.
		// FrontControllers already guarantees *_id parameters are numeric.
		if (request.getParameter("shipping_company_id") != null)
			orderBeanId.setShippingCompanyId(request.getParameter("shipping_company_id"));
		else if ("0".equals(orderBeanId.getShippingCompanyId()))
			orderFaced.readShippingCompany(orderBeanId);
		orderFaced.loadShippingCompany(orderBeanId);

		// Resolution center (dispute) - only when the migration added the columns.
		orderBeanId.setResolutionCenterEnabled(orderFaced.resolutionColumnsPresent());
		if (orderBeanId.isResolutionCenterEnabled()) {
			if (request.getParameter("resolution_center_id") != null)
				orderBeanId.setResolutionCenterId(request.getParameter("resolution_center_id"));
			else if ("0".equals(orderBeanId.getResolutionCenterId()))
				orderFaced.readResolutionCenter(orderBeanId);
			orderFaced.loadResolutionCenter(orderBeanId);
		}

		if (request.getParameter("cards_name") != null)
			orderBeanId.setCardsName(request.getParameter("cards_name"));
		if (request.getParameter("city_fullname") != null)
			orderBeanId.setCityFullname(request.getParameter("city_fullname"));

		if (request.getParameter("currency_lable") != null)
			orderBeanId.setCurrencyLable(request.getParameter("currency_lable"));
		if (request.getParameter("shipment_address") != null)
			orderBeanId.setShipmentAddress(request.getParameter("shipment_address"));

		if (request.getParameter("contact_person") != null)
			orderBeanId.setContactPerson(request.getParameter("contact_person"));

		if (request.getParameter("shipment_phone") != null)
			orderBeanId.setShipmentPhone(request.getParameter("shipment_phone"));

		if (request.getParameter("shipment_email") != null)
			orderBeanId.setShipmentEmail(request.getParameter("shipment_email"));

		if (request.getParameter("shipment_fax") != null)
			orderBeanId.setShipmentFax(request.getParameter("shipment_fax"));
		if (request.getParameter("shipment_description") != null)
			orderBeanId.setShipmentDescription(request.getParameter("shipment_description"));
		if (request.getParameter("city_name") != null)
			orderBeanId.setCityName(request.getParameter("city_name"));
		if (request.getParameter("country_name") != null)
			orderBeanId.setCountryName(request.getParameter("country_name"));
		if (request.getParameter("country_telcode") != null)
			orderBeanId.setCountryTelcode(request.getParameter("country_telcode"));
		if (request.getParameter("currency_rate") != null)
			orderBeanId.setCurrencyRate(request.getParameter("currency_rate"));
		if (request.getParameter("order_city_telcode") != null)
			orderBeanId.setOrderCityTelcode(request.getParameter("order_city_telcode"));
		if (request.getParameter("delivery_start") != null)
			orderBeanId.setDeliveryStart(request.getParameter("delivery_start"));
		if (request.getParameter("country_telcode") != null)
			orderBeanId.setCountryTelcode(request.getParameter("country_telcode"));
		if (request.getParameter("currency_rate") != null)
			orderBeanId.setCountryTelcode(request.getParameter("currency_rate"));
		if (request.getParameter("order_city_telcode") != null)
			orderBeanId.setOrderCityTelcode(request.getParameter("order_city_telcode"));
		if (request.getParameter("delivery_start") != null)
			orderBeanId.setDeliveryStart(request.getParameter("delivery_start"));
		if (request.getParameter("cdate") != null)
			orderBeanId.setCdate(request.getParameter("cdate"));
		if (request.getParameter("imei") != null)
			orderBeanId.setImei(request.getParameter("imei"));

	}

	/**
	 * Handles GET: resolves the order to show (own basket or order_id), applies the access rules, dispatches the requested action and renders the products and shipping data.
	 *
	 * @throws Exception on persistence failure
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws Exception {

		OrderBean orderBeanId;

		// Map messageMail;
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
		if (productlistFaced == null)
			productlistFaced = ServiceLocator.getInstance().getProductlistFaced();




		if (authorizationPageFaced == null)
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		authorizationPageBeanId = (AuthorizationPageBean) session.getAttribute("authorizationPageBeanId");
		accountHistoryBeanId = (AccountHistoryBean) session.getAttribute("accountHistoryBeanId");
		orderBeanId = new OrderBean();
		request.setAttribute("orderBeanId", orderBeanId);
		orderBeanId = (OrderBean) request.getAttribute("orderBeanId");
		if (authorizationPageBeanId == null || accountHistoryBeanId == null || orderBeanId == null)
			return;

		request.setCharacterEncoding("UTF-8");

		orderBeanId.isInternet = isInternet;
		orderBeanId.setBalans(orderFaced.getBalans(authorizationPageBeanId.getIntUserID()));
		orderBeanId.setUserID("" + authorizationPageBeanId.getIntUserID());

		if (request.getParameter("offset") != null)
			orderBeanId.setOffset(orderFaced.stringToInt(request.getParameter("offset")));

		if (request.getParameter("create_order") != null) {
			String createOrder = request.getParameter("create_order");
			if (createOrder.compareTo("true") == 0)
				orderFaced.createOrder(authorizationPageBeanId.getCurrencyId(), orderBeanId);
			// orderFaced.createOrder("1",orderBeanId );
		}

//		InitOrder
		if (request.getParameter("order_id") != null) {
			if (!Validation.isNonNegativeInteger(request.getParameter("order_id")))
				return;
			orderBeanId.setOrderId(request.getParameter("order_id"));
			authorizationPageBeanId.setCurrentOrderId(Long.parseLong(orderBeanId.getOrderId()));
		} else if (request.getParameter("account_history_id") != null) {
			String accountHistoryId = request.getParameter("account_history_id");
			if (!Validation.isNonNegativeInteger(accountHistoryId))
				return;
			String orderId = orderFaced.getOrderByAccount(accountHistoryId);
			orderBeanId.setOrderId(orderId);
			authorizationPageBeanId.setCurrentOrderId(Long.parseLong(orderBeanId.getOrderId()));
		} else if (orderBeanId.getOrderId().length() == 0) {

			if (authorizationPageBeanId.getCurrentOrderId() > 0) {
				orderBeanId.setOrderId("" + authorizationPageBeanId.getCurrentOrderId());
				orderFaced.getProducts(request, orderBeanId);
			} else {
				orderFaced.createOrder(authorizationPageBeanId.getCurrencyId(), orderBeanId);
				authorizationPageBeanId.setCurrentOrderId(Long.parseLong(orderBeanId.getOrderId()));

			}
		}

		boolean foreignOrder = false;
		if (orderBeanId.getOrderId().length() > 0) {
			int access = checkOrderAccess(orderBeanId, authorizationPageBeanId);
			if (access < 0) {
				log.warn("user " + authorizationPageBeanId.getIntUserID() + " (role "
						+ authorizationPageBeanId.getRoleId() + ") denied access to order " + orderBeanId.getOrderId());
				authorizationPageBeanId.setStrMessage("You have no access to order N " + orderBeanId.getOrderId() + ".");
				authorizationPageBeanId.setCurrentOrderId(0);
				orderBeanId.setOrderId("");
				response(request, orderBeanId, authorizationPageBeanId);
				return;
			}
			foreignOrder = access > 0;
		}

		mappingForm(request, orderBeanId);

		if (request.getParameter("action") != null) {
			orderBeanId.setAction(request.getParameter("action"));
			dispatchAction(request, response, servletContext, orderBeanId, authorizationPageBeanId, foreignOrder);
		}

		response(request, orderBeanId, authorizationPageBeanId);

	}



	// ------------------------------------------------------------------
	// Business methods, one per form action (same idea as in
	// PostProductOfferAction): doGet only resolves the order and delegates.
	// ------------------------------------------------------------------

	/** Routes the `action` form field to the business method. */
	/**
	 * Ownership check for an order opened by id. Returns 0 when the order is the
	 * caller's own (or does not exist yet), 1 when it belongs to another user
	 * of the same site and the caller may process orders (administrator,
	 * shipping, fulfillment), and -1 when access must be refused. Before pass 33
	 * there was no check at all: any logged-in member could open, edit and
	 * re-status any order by guessing its id.
	 */
	private int checkOrderAccess(OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId)
			throws Exception {
		long[] owner = orderFaced.getOrderOwner(orderBeanId.getOrderId());
		if (owner == null || owner[0] < 0 || owner[0] == authorizationPageBeanId.getIntUserID())
			return 0;
		if (!SiteRole.canProcessOrders(authorizationPageBeanId.getRoleId()))
			return -1;
		long mySite;
		try {
			mySite = Long.parseLong(authorizationPageBeanId.getSiteId());
		} catch (NumberFormatException e) {
			return -1;
		}
		if (owner[1] != mySite)
			return -1;
		// act on the customer's order as the customer's: keep orders.user_id and
		// show the customer's balance, never the operator's
		orderBeanId.setUserID("" + owner[0]);
		orderBeanId.setBalans(orderFaced.getBalans(owner[0]));
		return 1;
	}

	/**
	 * Runs the requested action: add/del position (owner only), status (owner or staff, via setStatusOnly), save/checkout (owner only).
	 *
	 * @param foreignOrder true when the caller is staff acting on someone else's order
	 */
	private void dispatchAction(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId, boolean foreignOrder)
			throws Exception {
		String action = orderBeanId.getAction();
		long roleId = authorizationPageBeanId.getRoleId();
		if (foreignOrder) {
			// Somebody else's order, opened by staff of the same site. Staff moves
			// it through statuses; only an administrator may also edit its content.
			if ("status".equals(action)) {
				orderFaced.setStatusOnly(orderBeanId);
				notifyOrderParties(orderBeanId, authorizationPageBeanId, request);
				return;
			}
			if (!SiteRole.isAdministrator(roleId)) {
				authorizationPageBeanId.setStrMessage("Only the order status can be changed here.");
				return;
			}
			if ("save".equals(action)) {
				// never let an administrator check out (and pay for) a customer's order
				authorizationPageBeanId.setStrMessage("A customer's order cannot be checked out on their behalf.");
				return;
			}
		}
		if ("add".equals(action)) {
			addPosition(request, servletContext, orderBeanId, authorizationPageBeanId);
		} else if ("del".equals(action)) {
			removePosition(request, orderBeanId, authorizationPageBeanId);
		} else if ("status".equals(action)) {
			changeOrderStatus(request, orderBeanId, authorizationPageBeanId);
		} else if ("save".equals(action)) {
			if (isCheckoutAllowed(orderBeanId, authorizationPageBeanId))
				checkoutOrder(request, response, servletContext, orderBeanId, authorizationPageBeanId);
			else if (authorizationPageBeanId.getStrLogin().compareTo("user") == 0)
				authorizationPageBeanId.setStrMessage(
						authorizationPageBeanId.getLocalization(servletContext).getString("order.forpay.text"));
		}
	}

	/** action=add: put a product (`position`, `quantity`) into the order. */
	private void addPosition(HttpServletRequest request, ServletContext servletContext, OrderBean orderBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("position") == null)
			return;
		if (!SiteRole.isAdministrator(authorizationPageBeanId.getRoleId()) && orderBeanId.getDeliverystatusId().equals("1")) {
			authorizationPageBeanId.setStrMessage("To make a new order you have to login again and use button exit.");
			return;
		}
		int quantity = 1;
		if (Validation.isNonNegativeInteger(request.getParameter("quantity")))
			quantity = Integer.parseInt(request.getParameter("quantity"));
		String rezult = orderFaced.addPosition(request.getParameter("position"), quantity, orderBeanId);
		if (rezult.compareTo("-1") == 0)
			authorizationPageBeanId.setStrMessage(
					authorizationPageBeanId.getLocalization(servletContext).getString("order.badcurrency.text"));
	}

	/** action=del: remove a product (`position`) from the order. */
	private void removePosition(HttpServletRequest request, OrderBean orderBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (request.getParameter("position") == null)
			return;
		if (!SiteRole.isAdministrator(authorizationPageBeanId.getRoleId()) && orderBeanId.getDeliverystatusId().equals("1")) {
			authorizationPageBeanId.setStrMessage("You can not dot remove item because the order is in process stage delivery.");
			return;
		}
		orderFaced.deleteOrder(request.getParameter("position"), orderBeanId);
	}

	/**
	 * action=status: change payment / delivery status. NOTE: setSave() debits
	 * the customer's account, so the balance is checked first for non-admins.
	 */
	private void changeOrderStatus(HttpServletRequest request, OrderBean orderBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		float balans = orderBeanId.getBalans();
		float orderEndAmount = Float.parseFloat(orderBeanId.getEndAmount());
		long roleId = authorizationPageBeanId.getRoleId();
		if (!SiteRole.isAdministrator(roleId) && 0 > balans - orderEndAmount) {
			authorizationPageBeanId.setStrMessage("You have less money for Payment of order  N "
					+ orderBeanId.getOrderId() + " , Please click link \"Pay\" that payment order .");
			orderBeanId.setOrderPaystatus("1");
			return;
		}
		int result = orderFaced.setSave(orderBeanId);
		notifyOrderParties(orderBeanId, authorizationPageBeanId, request);
		switch (result) {
		case 5:
			authorizationPageBeanId.setStrMessage("Please enter phone number.");
			break;
		case 6:
			authorizationPageBeanId.setStrMessage("Please enter contact person.");
			break;
		}
	}

	/** action=save is a checkout only for a logged-in customer with a fresh order, or for an admin. */
	private boolean isCheckoutAllowed(OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId) {
		return (authorizationPageBeanId.getStrLogin().compareTo("user") != 0
				&& orderBeanId.getDeliverystatusId().equals("0"))
				|| SiteRole.isAdministrator(authorizationPageBeanId.getRoleId());
	}

	/**
	 * action=save: checkout - save the order, mail the shop and the customer,
	 * notify both parties and forward to the payment page.
	 */
	private void checkoutOrder(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (authorizationPageBeanId.getRoleId() == SiteRole.MEMBER_ROLE_ID)
			orderBeanId.setDeliverystatusId("" + OrderDeliveryStatus.BILL_HAS_SENT_TO_CLIENT.getCode());

		AuthorizationPageBean ownerShop = authorizationPageFaced
				.getAuthorizationBeanOfRoleAdmin(authorizationPageBeanId.getSiteId());
		Message messageMail = buildOrderMailFields(request, orderBeanId, authorizationPageBeanId);
		messageMail.put("@ShopEmail", ownerShop.getStrEMail());

		saveOrder(orderBeanId, authorizationPageBeanId);
		notifyOrderParties(orderBeanId, authorizationPageBeanId, request);
		sendOrderMails(request, servletContext, authorizationPageBeanId, ownerShop, messageMail);

		authorizationPageBeanId.setStrMessage("Order was not paid yet.");
		RequestDispatcher dispatcher = request.getRequestDispatcher("fdencrypting.jsp");
		dispatcher.forward(request, response);
	}

	/** Site 2 keeps the old "no debit on checkout" behaviour. */
	private void saveOrder(OrderBean orderBeanId, AuthorizationPageBean authorizationPageBeanId) throws Exception {
		if (authorizationPageBeanId.getSiteId().compareTo("2") == 0)
			orderFaced.setSaveWithOutDeductMoney(orderBeanId);
		else
			orderFaced.setSave(orderBeanId);
	}

	/** Placeholders for the ShopOrder.txt / ClientOrder.txt mail templates. */
	private Message buildOrderMailFields(HttpServletRequest request, OrderBean orderBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		Message messageMail = new Message();
		messageMail.put("@FirstName", authorizationPageBeanId.getStrFirstName());
		messageMail.put("@LastName", authorizationPageBeanId.getStrLastName());
		messageMail.put("@NumberOfOrder", orderBeanId.getOrderId());
		messageMail.put("@ContactPerson", orderBeanId.getContactPerson());
		messageMail.put("@Balans", "" + orderBeanId.getBalans());
		messageMail.put("@Phone", orderBeanId.getShipmentPhone());
		messageMail.put("@Address", orderBeanId.getShipmentAddress());
		messageMail.put("@City", orderBeanId.getCityFullname());
		messageMail.put("@Contry", orderBeanId.getCountryFullname());
		messageMail.put("@CustomerEmail", orderBeanId.getShipmentEmail());
		messageMail.put("@CustomerFax", orderBeanId.getShipmentFax());
		messageMail.put("@CustomerCommentariy", orderBeanId.getShipmentDescription());
		messageMail.put("@ProductCount", "" + orderFaced.getProductsListSize(request, orderBeanId));
		messageMail.put("@Amount", "" + orderBeanId.getEndAmount());
		messageMail.put("@Currency", "" + orderBeanId.getCurrencyLable());
		messageMail.put("@IPAddress", "" + request.getRemoteAddr());
		messageMail.put("@HTTPHEAD", "" + requestHeadersAsText(request));
		messageMail.put("@SiteId", authorizationPageBeanId.getSiteId());
		return messageMail;
	}

	/** All request headers, one per line (goes into the shop's order mail). */
	private String requestHeadersAsText(HttpServletRequest request) {
		StringBuilder userOs = new StringBuilder();
		Enumeration en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String headerName = (String) en.nextElement();
			userOs.append(headerName).append("=").append(request.getHeader(headerName)).append("\n");
		}
		return userOs.toString();
	}

	/** Queue the order confirmation to the shop owner and to the customer. */
	private void sendOrderMails(HttpServletRequest request, ServletContext servletContext,
			AuthorizationPageBean authorizationPageBeanId, AuthorizationPageBean ownerShop, Message messageMail)
			throws Exception {
		String sitePath = (String) request.getSession().getAttribute("site_path");
		String mailDir = sitePath + File.separatorChar + "mail" + File.separatorChar;
		String shopOrder = mailDir + "ShopOrder.txt";
		String clientOrder = mailDir + "ClientOrder.txt";
		String attachFile = mailDir + "info.txt";
		String subject = authorizationPageBeanId.getLocalization(servletContext).getString("order.order_number.text");

		MessageSender mqSender = new MessageSender(request.getSession(), SendMailMessageBean.messageQuery);
		Message message = new Message();
		message.put("to", ownerShop.getStrEMail());
		message.put("subject", subject);
		message.put("pathmessage", shopOrder);
		message.put("attachFile", attachFile);
		message.put("fields", messageMail);
		mqSender.send(message);

		message = new Message();
		message.put("to", authorizationPageBeanId.getStrEMail());
		message.put("mailfrom", ownerShop.getStrEMail());
		message.put("subject", subject);
		message.put("pathmessage", clientOrder);
		message.put("attachFile", attachFile);
		message.put("fields", messageMail);
		mqSender.send(message);
	}

	private void response(HttpServletRequest request, OrderBean orderBeanId,
			AuthorizationPageBean authorizationPageBeanId) throws Exception {
		orderBeanId.setProductList(orderFaced.getProducts(request, orderBeanId));
		orderBeanId.setEmptyBasket(orderFaced.getProductsListSize(request, orderBeanId) == 0);
		orderBeanId.setQuantityProduct(orderFaced.getProductsListSize(request, orderBeanId));
		orderBeanId.setSelectCountry(orderFaced.getXMLDBList("Order.jsp?country_id", "country",
				orderBeanId.getCountryId(), "select country_id ,name from country   where  locale = '"
						+ authorizationPageBeanId.getLocale() + "' "));
		orderBeanId.setSelectCity(orderFaced.getXMLDBList("Order.jsp?city_id", "city", orderBeanId.getCityId(),
				"select  city_id , name  from  city where country_id =" + orderBeanId.getCountryId()
						+ " and locale = '" + authorizationPageBeanId.getLocale() + "' "));
		orderBeanId.setSelectPaystatus(
				orderFaced.getXMLDBList("Order.jsp?order_paystatus", "paystatus", orderBeanId.getOrderPaystatus(),
						"select  paystatus_id , lable  from  paystatus  where  active  = true"));
		orderBeanId.setSelectShippingCompany(orderFaced.getShippingCompanyList(orderBeanId));
		if (orderBeanId.isResolutionCenterEnabled())
			orderBeanId.setSelectResolutionCenter(orderFaced.getResolutionCenterList(orderBeanId));
		orderBeanId.setSelectDeliverystatus(orderFaced.getXMLDBList("Order.jsp?order_deliverystatus", "deliverystatus",
				orderBeanId.getDeliverystatusId(),
				"select  deliverystatus_id , lable  from  deliverystatus  where  active  = true and lang = '"
						+ authorizationPageBeanId.getLocale() + "' "));

		if (orderBeanId.getCityId().equals("0"))
			orderBeanId.setCityId(authorizationPageBeanId.getCityId());
		if (orderBeanId.getCountryId().equals("0"))
			orderBeanId.setCountryId(authorizationPageBeanId.getCountryId());
		if (orderBeanId.getContactPerson().length() == 0)
			orderBeanId.setContactPerson(
					authorizationPageBeanId.getStrFirstName() + " " + authorizationPageBeanId.getStrLastName());
		if (orderBeanId.getShipmentEmail().length() == 0)
			orderBeanId.setShipmentEmail(authorizationPageBeanId.getStrEMail());
		if (orderBeanId.getShipmentPhone().length() == 0)
			orderBeanId.setShipmentPhone(authorizationPageBeanId.getStrPhone());

		orderBeanId.newArrivalItems = productlistFaced.getNewArrivalItems(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(), authorizationPageBeanId);


		orderBeanId.recommentedItems = productlistFaced.getRecommentedItems(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getCatalogId(),  authorizationPageBeanId);

		orderBeanId.sponsoredBySellers = productlistFaced.getSponsoredBySellersItems(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getCatalogId(),  authorizationPageBeanId);

		orderBeanId.recentlyReviewd = productlistFaced.getRecentlyReviewedItems(
				authorizationPageBeanId.getIntUserID(), authorizationPageBeanId.getSiteId(),
				authorizationPageBeanId.getCatalogId(),  authorizationPageBeanId);

		orderBeanId.setSelectMenuCatalog(orderFaced.getMenuXMLDBList("Productlist.jsp?catalog_id", "menu",
				authorizationPageBeanId.getCatalogId(),
				"select catalog_id , lable , parent_id  from catalog   where  active = true and parent_id = -2 and site_id = "
						+ authorizationPageBeanId.getSiteId() + " and lang_id = "
						+ authorizationPageBeanId.getLangId()
						+ " or parent_id in (select catalog_id   from catalog   where  active = true and site_id = "
						+ authorizationPageBeanId.getSiteId() + "  and parent_id = -2 )"));



	}







}
