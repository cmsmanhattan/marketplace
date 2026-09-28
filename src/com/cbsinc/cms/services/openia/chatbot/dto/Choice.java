package com.cbsinc.cms.services.openia.chatbot.dto;

public class Choice {

	public Choice() {
	}

	public Choice(Integer index, Message message, Integer logprobs, String finishReason) {
		super();
		this.index = index;
		this.message = message;
		this.logprobs = logprobs;
		this.finishReason = finishReason;
	}

	private Integer index;
	private Message message;
	private Integer logprobs;
	private String finishReason;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Integer getLogprobs() {
		return logprobs;
	}

	public void setLogprobs(Integer logprobs) {
		this.logprobs = logprobs;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	@Override
	public String toString() {
		return "Choice [index=" + index + ", message=" + message + ", logprobs=" + logprobs + ", finish_reason="
				+ finishReason + "]";
	}

}
