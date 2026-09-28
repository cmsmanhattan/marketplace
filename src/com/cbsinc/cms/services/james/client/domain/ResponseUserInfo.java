package com.cbsinc.cms.services.james.client.domain;

public class ResponseUserInfo {

	private String name;
	private String email;
	private Status id;
	private String mayDelete;
	private String textSignature;
	private String htmlSignature;
	private String sortOrder;
	private Bcc bcc;
	private ReplyTo replyTo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getId() {
		return id;
	}

	public void setId(Status id) {
		this.id = id;
	}

	public String getMayDelete() {
		return mayDelete;
	}

	public void setMayDelete(String mayDelete) {
		this.mayDelete = mayDelete;
	}

	public String getTextSignature() {
		return textSignature;
	}

	public void setTextSignature(String textSignature) {
		this.textSignature = textSignature;
	}

	public String getHtmlSignature() {
		return htmlSignature;
	}

	public void setHtmlSignature(String htmlSignature) {
		this.htmlSignature = htmlSignature;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Bcc getBcc() {
		return bcc;
	}

	public void setBcc(Bcc bcc) {
		this.bcc = bcc;
	}

	public ReplyTo getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(ReplyTo replyTo) {
		this.replyTo = replyTo;
	}

	@Override
	public String toString() {
		return "ResponseUserInfo [name=" + name + ", email=" + email + ", id=" + id + ", mayDelete=" + mayDelete
				+ ", textSignature=" + textSignature + ", htmlSignature=" + htmlSignature + ", sortOrder=" + sortOrder
				+ ", bcc=" + bcc + ", replyTo=" + replyTo + "]";
	}

}
