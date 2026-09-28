package com.cbsinc.cms.services.james.client.domain.faceds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cbsinc.cms.client.rest.utils.RestTemplateConfig;
import com.cbsinc.cms.exceptions.RestClientException;
import com.cbsinc.cms.services.james.client.user.CreateUserRequest;

public class DomainApiClient {

	static private Logger LOGGER = Logger.getLogger(DomainApiClient.class);
	static private DomainApiClient domainApiClient = new DomainApiClient();

	private ResourceBundle resourcesCmsSettings = null;

	private String login;
	private String password;
	private String jamesHost;
	private String port;

	public static DomainApiClient getInstanse() {
		return domainApiClient;
	}

	private DomainApiClient() {
		if (resourcesCmsSettings == null)
			resourcesCmsSettings = PropertyResourceBundle.getBundle("appconfig");
		login = resourcesCmsSettings.getString("james_login").trim();
		password = resourcesCmsSettings.getString("james_password").trim();
		jamesHost = resourcesCmsSettings.getString("james_host").trim();
		port = resourcesCmsSettings.getString("james_port").trim();

	}

	public void addDomain(String host) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.put(endPoint, request, vars);

	}

	public boolean existDomain(String host) throws RestClientException {

		boolean result = false;
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		HttpEntity<String> request = new HttpEntity<>(headers);
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class, request);
			HttpStatus httpStatus = response.getStatusCode();
			switch (httpStatus) {
			case NO_CONTENT:
				result = true;
				break;
			default:
				break;
			}

		} catch (HttpStatusCodeException ex) {
			result = false;

		}

		return result;

	}

	public List<String> getDomains() {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		ResponseEntity<String[]> response = restTemplate.getForEntity(endPoint, String[].class, request);
		return Arrays.asList(response.getBody());

	}

	public List<String> getDomainAliases(String host) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}/aliases";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);

		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		ResponseEntity<String[]> response = restTemplate.getForEntity(endPoint, String[].class, request, vars);
		return Arrays.asList(response.getBody());

	}

	public void addDomainAliase(String host, String alias) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}/aliases/{alias}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		vars.put("alias", alias);

		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.put(endPoint, request, vars);

	}

	public void deleteDomainAliase(String host, String alias) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}/aliases/{alias}";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		vars.put("alias", alias);

		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.delete(endPoint, request, vars);

	}

	public void deleteDomain(String host) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.delete(endPoint, request, vars);

	}

	public void deleteAllUserDataForDomain(String host) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + jamesHost + ":" + port + "/domains/{host}?action=deleteData";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("host", host);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.delete(endPoint, request, vars);

	}

	public static void main(String[] args) {

		// addUser( "guest@cmsmanhattan.com" , "guest" ) ;
		// addDomain( "cmsmanhattan2.com" ) ;
		// List<String> list = getDomains() ;
		// List<ResponseUsers> list = getUsers();
		// for (ResponseUsers row : list) {
		// System.out.println(row);
		/// }

		// boolean d = getInstanse().existDomain("cmsmanhattan2.com");

	}

}
