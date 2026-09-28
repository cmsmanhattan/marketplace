package com.cbsinc.cms.services.james.client.domain;

public class ResponseDeleteUserData {

	private String type;
	private String username;

	private Status status;
	private String fromStep;
	private String timestamp;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFromStep() {
		return fromStep;
	}

	public void setFromStep(String fromStep) {
		this.fromStep = fromStep;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ResponseDeleteUserData [type=" + type + ", username=" + username + ", status=" + status + ", fromStep="
				+ fromStep + ", timestamp=" + timestamp + "]";
	}

}
