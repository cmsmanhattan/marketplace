package com.cbsinc.cms.services.openia.search.dto;

public class RequestAISearch {

	private String input = "";
	private String model = "text-embedding-3-small";
	private String encodingFormat = "base64";

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getEncodingFormat() {
		return encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	@Override
	public String toString() {
		return "RequestAISearch [input=" + input + ", model=" + model + ", encoding_format=" + encodingFormat + "]";
	}

}
