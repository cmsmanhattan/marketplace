package com.cbsinc.cms.services.james.client.user.faceds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cbsinc.cms.client.rest.utils.RestTemplateConfig;
import com.cbsinc.cms.exceptions.RestClientException;
import com.cbsinc.cms.services.james.client.domain.ResponseDeleteUserData;
import com.cbsinc.cms.services.james.client.domain.ResponseRenameUsers;
import com.cbsinc.cms.services.james.client.domain.ResponseUserInfo;
import com.cbsinc.cms.services.james.client.domain.ResponseUsers;
import com.cbsinc.cms.services.james.client.user.CreateUserRequest;

public class UserApiClient {

	static private Logger LOGGER = Logger.getLogger(UserApiClient.class);
	static private UserApiClient userApiClient = new UserApiClient();

	private ResourceBundle resourcesCmsSettings = null;

	private String login;
	private String password;
	private String host;
	private String port;

	public static UserApiClient getInstanse() {
		return userApiClient;
	}

	public UserApiClient() {
		if (resourcesCmsSettings == null)
			resourcesCmsSettings = PropertyResourceBundle.getBundle("appconfig");
		login = resourcesCmsSettings.getString("james_login").trim();
		password = resourcesCmsSettings.getString("james_password").trim();
		host = resourcesCmsSettings.getString("james_host").trim();
		port = resourcesCmsSettings.getString("james_port").trim();
	}

	public boolean existUser(String user) {

		boolean result = false;
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", user);

		ResponseEntity<String> response = restTemplate.exchange(endPoint, HttpMethod.HEAD, request, String.class, vars);
		HttpStatus httpStatus = response.getStatusCode();
		switch (httpStatus) {
		case OK:
			result = true;
			break;
		case NOT_FOUND:
			result = false;
			break;

		case BAD_REQUEST:
			result = false;
			break;

		default:
			break;
		}

		return result;

	}

	public void addUser(String userName, String password) throws Exception {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}";
		CreateUserRequest createUserRequest = new CreateUserRequest(password);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", userName);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest, headers);
		try {
			restTemplate.put(endPoint, request, vars);
		} catch (HttpStatusCodeException ex) {
			LOGGER.error(ex.getLocalizedMessage(), ex);
			if (ex.getStatusCode().value() >= 400 && ex.getStatusCode().value() < 500) {
				String json = ex.getResponseBodyAsString();
				throw new RestClientException(json);
			}
		}

	}

	/*
	 * static void addAliaseUser( String userName , String authorizedUsers ) throws
	 * Exception { RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
	 * String endPoint = "http://localhost:8000//users/{user}/authorizedUsers/";
	 * CreateUserRequest createUserRequest = new CreateUserRequest(password) ;
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 *
	 * Map<String, String> vars = new HashMap<>(); vars.put("user", userName);
	 * HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest,
	 * headers); try { restTemplate.put(endPoint , request , vars ); } catch
	 * (HttpStatusCodeException ex) { LOGGER.error(ex.getLocalizedMessage() , ex);
	 * if( ex.getStatusCode().value() >= 400 && ex.getStatusCode().value() < 500 ) {
	 * String json = ex.getResponseBodyAsString(); throw new
	 * RestClientException(json) ; } }
	 *
	 *
	 * }
	 */

	public ResponseRenameUsers renamedUser(String oldUserName, String newUserName) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{oldUserName}/rename/{newUserName}?action=rename";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("oldUserName", oldUserName);
		vars.put("newUserName", oldUserName);
		HttpEntity<Object> request = new HttpEntity<>(headers);
		ResponseRenameUsers responseRenameUsers = restTemplate.postForObject(endPoint, request,
				ResponseRenameUsers.class, vars);
		return responseRenameUsers;
	}

	public void deleteUser(String userName) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", userName);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		restTemplate.delete(endPoint, request, vars);

	}

	public ResponseDeleteUserData deleteUserData(String userName) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}?action=deleteData";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", userName);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		ResponseDeleteUserData responseRenameUsers = restTemplate.postForObject(endPoint, request,
				ResponseDeleteUserData.class, vars);
		return responseRenameUsers;
	}

	public void changeUserPassword(String userName, String password) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}?force ";
		CreateUserRequest createUserRequest = new CreateUserRequest(password);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", userName);
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest, headers);
		restTemplate.put(endPoint, request, vars);

	}

	public ResponseUserInfo getUserInfo(String userName) {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users/{user}/identities?default=true";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> vars = new HashMap<>();
		vars.put("user", userName);
		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseUserInfo deviceInfoRespond = restTemplate.getForObject(endPoint, ResponseUserInfo.class, vars);
		return deviceInfoRespond;

	}

	public List<ResponseUsers> getUsers() {
		RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
		String endPoint = "http://" + host + ":" + port + "/users";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CreateUserRequest> request = new HttpEntity<>(headers);
		ResponseEntity<ResponseUsers[]> response = restTemplate.getForEntity(endPoint, ResponseUsers[].class, request);
		return Arrays.asList(response.getBody());

	}

	public static void main(String[] args) {

		// addUser( "guest@cmsmanhattan.com" , "guest" ) ;
		// addDomain( "cmsmanhattan2.com" ) ;
		// List<String> list = getDomains() ;
		// List<ResponseUsers> list = getUsers();
		// for (ResponseUsers row : list) {
		// System.out.println(row);
		// }

	}

}
