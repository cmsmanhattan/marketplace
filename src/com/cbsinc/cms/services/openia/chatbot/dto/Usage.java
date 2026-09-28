package com.cbsinc.cms.services.openia.chatbot.dto;

public class Usage {

	private Integer promptTokens;
	private Integer totalTokens;
	private Integer completionTokens;

	public Integer getPromptTokens() {
		return promptTokens;
	}

	public void setPromptTokens(Integer promptTokens) {
		this.promptTokens = promptTokens;
	}

	public Integer getTotalTokens() {
		return totalTokens;
	}

	public void setTotalTokens(Integer totalTokens) {
		this.totalTokens = totalTokens;
	}

	public Integer getCompletionTokens() {
		return completionTokens;
	}

	public void setCompletionTokens(Integer completionTokens) {
		this.completionTokens = completionTokens;
	}

	@Override
	public String toString() {
		return "Usage [prompt_tokens=" + promptTokens + ", total_tokens=" + totalTokens + ", completion_tokens="
				+ completionTokens + "]";
	}

}
