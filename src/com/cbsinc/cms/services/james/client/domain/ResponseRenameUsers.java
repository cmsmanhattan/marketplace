package com.cbsinc.cms.services.james.client.domain;

public class ResponseRenameUsers {

	private String type;
	private String oldUser;
	private String newUser;
	private Status status;
	private String fromStep;
	private String timestamp;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOldUser() {
		return oldUser;
	}

	public void setOldUser(String oldUser) {
		this.oldUser = oldUser;
	}

	public String getNewUser() {
		return newUser;
	}

	public void setNewUser(String newUser) {
		this.newUser = newUser;
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
		return "ResponseRenameUsers [type=" + type + ", oldUser=" + oldUser + ", newUser=" + newUser + ", status="
				+ status + ", fromStep=" + fromStep + ", timestamp=" + timestamp + "]";
	}

}
