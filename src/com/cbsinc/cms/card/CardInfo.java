package com.cbsinc.cms.card;

public class CardInfo implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String country;

	private String cardnumber;

	private String bankname;

	private String bin;

	private String cardtype;

	private String cardsubtype;

	public String getCountry() {
		return country;
	}

	public void setCountry(String value) {
		country = value;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String value) {
		cardnumber = value;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String value) {
		bankname = value;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String value) {
		bin = value;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String value) {
		cardtype = value;
	}

	public String getCardsubtype() {
		return cardsubtype;
	}

	public void setCardsubtype(String value) {
		cardsubtype = value;
	}

}
