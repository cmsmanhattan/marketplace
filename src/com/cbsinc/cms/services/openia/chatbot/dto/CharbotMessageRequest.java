package com.cbsinc.cms.services.openia.chatbot.dto;

import java.util.Arrays;

public class CharbotMessageRequest {

	public CharbotMessageRequest() {
	}

	public CharbotMessageRequest(String model, Message[] messages) {
		super();
		this.model = model;
		this.messages = messages;
	}

	private String model = "";
	private Message[] messages = new Message[] {};

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "CharbotMessageRequest [model=" + model + ", messages=" + Arrays.toString(messages) + "]";
	}

}
