package com.cbsinc.cms.services.james.client.user;

public class CreateUserRequest {

	private String password = "";

	public CreateUserRequest(String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
