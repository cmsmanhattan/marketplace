package com.cbsinc.cms.card;

public class RateInfo implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String currency;

	private String date;

	private String rate;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String value) {
		currency = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String value) {
		date = value;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String value) {
		rate = value;
	}

}
