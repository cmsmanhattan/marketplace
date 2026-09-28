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
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.cbsinc.cms.faceds.AuthorizationPageFaced;
import com.cbsinc.cms.faceds.OrderFaced;
import com.cbsinc.cms.faceds.ProductInfoFaced;
import com.cbsinc.cms.faceds.ProductPostAllFaced;
import com.cbsinc.cms.faceds.ProductlistFaced;

/**
 * Superseded version of ServiceLocator, kept for reference.
 */
public class ServiceLocator_old {

	private static ServiceLocator_old me;
	InitialContext context = null;

	private ServiceLocator_old() throws Exception {
		try {
			context = new InitialContext();
			context.rebind("authorizationPageFaced", new AuthorizationPageFaced());
			context.rebind("orderFaced", new OrderFaced());
			context.rebind("productInfoFaced", new ProductInfoFaced());
			context.rebind("productlistFaced", new ProductlistFaced());
			context.rebind("productPostAllFaced", new ProductPostAllFaced());
		} catch (NamingException ne) {
			throw new Exception(ne);
		}
	}

	// Returns the instance of ServiceLocator class
	/**
	 * @return the singleton
	 */
	public static ServiceLocator_old getInstance() throws Exception {
		if (me == null) {
			me = new ServiceLocator_old();
		}
		return me;
	}

	// Converts the serialized string into EJBHandle
	// then to EJBObject.
	/**
	 * @return the authorization page faced
	 */
	public AuthorizationPageFaced getAuthorizationPageFaced() throws Exception {
		AuthorizationPageFaced authorizationPageFaced = null;
		try {
			authorizationPageFaced = (AuthorizationPageFaced) context.lookup("authorizationPageFaced");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return authorizationPageFaced;
	}

	/**
	 * @return the order faced
	 */
	public OrderFaced getOrderFaced() throws Exception {
		OrderFaced orderFaced = null;
		try {
			orderFaced = (OrderFaced) context.lookup("orderFaced");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return orderFaced;
	}

	/**
	 * @return the product info faced
	 */
	public ProductInfoFaced getProductInfoFaced() throws Exception {
		ProductInfoFaced productInfoFaced = null;
		try {
			productInfoFaced = (ProductInfoFaced) context.lookup("productInfoFaced");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return productInfoFaced;
	}

	/**
	 * @return the productlist faced
	 */
	public ProductlistFaced getProductlistFaced() throws Exception {
		ProductlistFaced productlistFaced = null;
		try {
			productlistFaced = (ProductlistFaced) context.lookup("productlistFaced");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return productlistFaced;
	}

	/**
	 * @return the product post all faced
	 */
	public ProductPostAllFaced getProductPostAllFaced() throws Exception {
		ProductPostAllFaced productPostAllFaced = null;
		try {
			productPostAllFaced = (ProductPostAllFaced) context.lookup("productPostAllFaced");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return productPostAllFaced;
	}

}
