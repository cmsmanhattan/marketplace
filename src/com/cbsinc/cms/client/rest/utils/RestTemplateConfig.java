package com.cbsinc.cms.client.rest.utils;

import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {

	static final private RestTemplate restTemplate = new RestTemplate();

	private RestTemplateConfig() {

	}

	public static RestTemplate getRestTemplate() {
		return restTemplate;

	}

}
