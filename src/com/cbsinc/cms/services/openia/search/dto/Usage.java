package com.cbsinc.cms.services.openia.search.dto;

public class Usage {

	private String promptTokens = "";
	private String totalTokens = "";

	public String getPromptTokens() {
		return promptTokens;
	}

	public void setPromptTokens(String promptTokens) {
		this.promptTokens = promptTokens;
	}

	public String getTotalTokens() {
		return totalTokens;
	}

	public void setTotalTokens(String totalTokens) {
		this.totalTokens = totalTokens;
	}

	@Override
	public String toString() {
		return "Usage [prompt_tokens=" + promptTokens + ", total_tokens=" + totalTokens + "]";
	}

}
