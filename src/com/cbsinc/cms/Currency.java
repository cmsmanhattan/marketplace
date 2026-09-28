package com.cbsinc.cms;

public class Currency implements java.io.Serializable {

	private static final long serialVersionUID = -5089556736903083959L;

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

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyLabe() {
		return currencyLabe;
	}

	public void setCurrencyLabe(String currencyLabe) {
		this.currencyLabe = currencyLabe;
	}

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

	private String currencyId = "";

	private String currencyLabe = "";

	private String currencyDesc = "";

	private Float rate = Float.valueOf(0);

	private String code = "";

	private String date = "";

	public Currency() {
	}

	public Currency(String currencyId, String currencyLabe, String currencyDesc, float rate, String code,
			String date) {
		this.rate = rate;
		this.code = code;
		this.date = date;
		this.currencyId = currencyId;
		this.currencyLabe = currencyLabe;
		this.currencyDesc = currencyDesc;
	}

	public Currency(float rate, String code, String date) {
		this.rate = rate;
		this.code = code;
		this.date = date;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
