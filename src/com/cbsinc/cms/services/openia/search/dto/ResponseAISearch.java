package com.cbsinc.cms.services.openia.search.dto;

import java.util.Arrays;

public class ResponseAISearch {

	private String object;
	private Data[] data;
	private String model;
	private Usage usage;

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	@Override
	public String toString() {
		return "ResponseAISearch [object=" + object + ", data=" + Arrays.toString(data) + ", model=" + model
				+ ", usage=" + usage + "]";
	}

}
