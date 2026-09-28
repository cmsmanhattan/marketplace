package com.cbsinc.cms.controllers;

import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.NotificationsFaced;
import com.cbsinc.cms.faceds.OrderFaced;
import com.cbsinc.cms.faceds.ProductInfoFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;
import com.cbsinc.cms.faceds.PurchaseFaced;

/**
 * Singleton registry of the faced (business-logic) instances shared by the controllers.
 */
public class ServiceLocator {

	private static ServiceLocator me;

	private AuthorizationPageFaced authorizationPageFaced = null;
	private OrderFaced orderFaced = null;
	private NotificationsFaced notificationsFaced = null ;
	private PurchaseFaced purchaseFaced = null;
	private ProductInfoFaced productInfoFaced = null;
	private ProductlistFaced productlistFaced = null;
	private ProductPostAllFaced productPostAllFaced = null;

	private ServiceLocator() {
		authorizationPageFaced = new AuthorizationPageFaced();
		orderFaced = new OrderFaced();
		notificationsFaced = new NotificationsFaced();
		productInfoFaced = new ProductInfoFaced();
		productlistFaced = new ProductlistFaced();
		productPostAllFaced = new ProductPostAllFaced();
		purchaseFaced = new PurchaseFaced();
	}

	// Returns the instance of ServiceLocator class
	/**
	 * @return the singleton
	 */
	public static ServiceLocator getInstance() throws Exception {

		synchronized (ServiceLocator.class) {
			if (me == null)
				me = new ServiceLocator();
		}

		return me;
	}

	// Converts the serialized string into EJBHandle
	// then to EJBObject.
	/**
	 * @return the authorization page faced
	 */
	public AuthorizationPageFaced getAuthorizationPageFaced() throws Exception {

		return authorizationPageFaced;
	}

	/**
	 * @return the notifications faced
	 */
	public NotificationsFaced getNotificationsFaced() throws Exception {

		return notificationsFaced;
	}

	/**
	 * @return the order faced
	 */
	public OrderFaced getOrderFaced() throws Exception {

		return orderFaced;
	}

	/**
	 * @return the purchase faced
	 */
	public PurchaseFaced getPurchaseFaced() throws Exception {

		return purchaseFaced;
	}

	/**
	 * @return the product info faced
	 */
	public ProductInfoFaced getProductInfoFaced() throws Exception {

		return productInfoFaced;
	}

	/**
	 * @return the productlist faced
	 */
	public ProductlistFaced getProductlistFaced() throws Exception {

		return productlistFaced;
	}

	/**
	 * @return the product post all faced
	 */
	public ProductPostAllFaced getProductPostAllFaced() throws Exception {

		return productPostAllFaced;
	}

}
