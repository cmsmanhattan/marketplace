package com.cbsinc.cms;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class CurrencyHash implements java.io.Serializable {

	private static final long serialVersionUID = -8991798235529641089L;
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
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	java.util.Hashtable hashTable;
	private static CurrencyHash currencyHash = null;

	private Logger log = Logger.getLogger(CurrencyHash.class);

	private CurrencyHash() {
		hashTable = new java.util.Hashtable();
		init();

	}

	static public CurrencyHash getInstance() {
		synchronized (CurrencyHash.class) {
			if (currencyHash == null)
				currencyHash = new CurrencyHash();
		}

		return currencyHash;
	}

	private void init() {
		float rate = 0;
		String currencyCd = "";
		String currencyId = "";
		String currencyLable = "";
		String currencyDesc = "";
		String cursdate = "";
		String query = "SELECT  currency_id ,  currency_lable , currency_desc , currency.rate ,  currency_cd ,  cursdate  FROM currency";
		QueryManager Adp = new QueryManager();
		try {
			Adp.executeQuery(query);
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		try {

			for (int i = 0; i < Adp.rows().size(); i++) {
				currencyId = (String) Adp.getValueAt(i, 0);
				currencyLable = (String) Adp.getValueAt(i, 1);
				currencyDesc = (String) Adp.getValueAt(i, 2);
				rate = new Float((String) Adp.getValueAt(i, 3)).floatValue();
				currencyCd = (String) Adp.getValueAt(i, 4);
				cursdate = (String) Adp.getValueAt(i, 5);
				hashTable.put(currencyId,
						new Currency(currencyId, currencyLable, currencyDesc, rate, currencyCd, cursdate));
			}
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		}

	}

	public Currency getCurrency(String currencyId) {
		// if(hashTable.get(currency_id) == null) hashTable.get(key)
		Currency curr = (Currency) hashTable.get(currencyId);
		if (curr == null) {
			curr = (Currency) hashTable.get(0);
			log.error(
					"ERROR: mobilesoft.CurrencyHash.getCurrency(String currency_id) and currency_id = " + currencyId);
		}
		return curr;
	}

	private String getCurrencyId(String currencyCode) {
		for (Object currency : hashTable.values()) {
			if (((Currency) currency).getCode().compareTo(currencyCode) == 0)
				return ((Currency) currency).getCode();
		}

		return "-1";
	}

	public String getCurrencyDecs(String currencyId) {
		if (hashTable.get(currencyId) == null)
			return "";
		return ((Currency) hashTable.get(currencyId)).getCurrencyDesc();
	}

}
