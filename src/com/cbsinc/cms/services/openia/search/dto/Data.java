package com.cbsinc.cms.services.openia.search.dto;

public class Data {

	private String object;
	private String index;
	private String embedding;

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getEmbedding() {
		return embedding;
	}

	public void setEmbedding(String embedding) {
		this.embedding = embedding;
	}

	@Override
	public String toString() {
		return "Data [object=" + object + ", index=" + index + ", embedding=" + embedding + "]";
	}

}
