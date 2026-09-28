package com.cbsinc.cms.services.openia.chatbot.dto;

public class Message {

	private String role = "";
	private String content = "";

	public Message(String role, String content) {
		super();
		this.role = role;
		this.content = content;
	}

	public Message() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Messages [role=" + role + ", content=" + content + "]";
	}

}
