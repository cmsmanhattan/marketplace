package com.cbsinc.cms.services.james.client.domain;

public class Bcc {

	private String emailerName = "";
	private String mailAddress = "";

	public String getEmailerName() {
		return emailerName;
	}

	public void setEmailerName(String emailerName) {
		this.emailerName = emailerName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@Override
	public String toString() {
		return "Bcc [emailerName=" + emailerName + ", mailAddress=" + mailAddress + "]";
	}

}
