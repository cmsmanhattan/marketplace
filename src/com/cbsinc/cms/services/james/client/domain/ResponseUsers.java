package com.cbsinc.cms.services.james.client.domain;

public class ResponseUsers {

	private String username = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ResponseUsers [username=" + username + "]";
	}

}
