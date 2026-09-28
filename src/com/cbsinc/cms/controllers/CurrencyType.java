package com.cbsinc.cms.controllers;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code. You can not use it and you
 * cannot change it without written permission from Konstantin Grabko Email:
 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002-2025
 * </p>
 * <p>
 * Company: CENTER BUSINESS SOLUTIONS INC
 * </p>
 *
 * INSERT INTO site VALUES
 * (2,'www.siteforyou.irr.bz',2,1,'shops.online-spb.com','shops.online-spb.com','John
 * Smit','111-1234','','','','');
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

public interface CurrencyType {

	final static String USD = "U.S Dollar";
	final static String EUR = " EURO";
	final static String RUB = "RF Ruble";
	final static String CNY = "China money";
	final static String JPY = "Japan Yen";
	final static String TRY = "Turkey Lira";
	final static String MXN = "Mexico Peso";
	final static String CAD = "Canada Dollar";
	final static String GBP = "United Kingdom Pound";
	final static String BRL = "Brazil Real";
	final static String INR = "India Rupee";
	final static String BTC = "Bitcoin";
	final static String LTC = "Litecoin";
	final static String ETH = "Ethereum";
	final static String PYUSD = "PayPal Dollar";
	final static String CBDC = "U.S Digital Dollar";
	final static String UNKNOWN = "ERROR";

}
