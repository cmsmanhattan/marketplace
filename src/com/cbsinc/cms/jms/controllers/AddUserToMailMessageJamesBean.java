package com.cbsinc.cms.jms.controllers;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.services.james.client.domain.faceds.DomainApiClient;
import com.cbsinc.cms.services.james.client.user.faceds.UserApiClient;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class AddUserToMailMessageJamesBean extends AbstractMessageBean {

	private Logger log = Logger.getLogger(SendMailMessageBean.class);

	static public String messageQuery = "mq_adduser_to_mail";

	private ResourceBundle resourcesCmsSettings = null;

	private String login;
	private String password;
	private String host;
	private String defaultDomain;

	public AddUserToMailMessageJamesBean() {
		if (resourcesCmsSettings == null)
			resourcesCmsSettings = PropertyResourceBundle.getBundle("appconfig");
		login = resourcesCmsSettings.getString("james_login").trim();
		password = resourcesCmsSettings.getString("james_password").trim();
		host = resourcesCmsSettings.getString("james_host").trim();
		defaultDomain = resourcesCmsSettings.getString("james_domain").trim();

	}

	public void onMessage(com.cbsinc.cms.jms.controllers.Message message, ServletContext applicationContext,
			HttpSession httpSession) {

		String userLogin = "";
		String userPassword = "";
		String userDomain = "";
		if (message.get("user_login") instanceof String)
			userLogin = (String) message.get("user_login");
		if (message.get("user_domain") instanceof String)
			userDomain = (String) message.get("user_domain");
		if (message.get("user_password") instanceof String)
			userPassword = (String) message.get("user_password");

		try {

			// UserApiClient.getInstanse().existUser(user_login + "@" + user_domain ) ;
			if (userDomain.equals("localhost"))
				userDomain = defaultDomain;

			if (DomainApiClient.getInstanse().existDomain(userDomain)) {
				UserApiClient.getInstanse().addUser(userLogin + "@" + userDomain, userPassword);
			} else {
				DomainApiClient.getInstanse().addDomain(userDomain);
				UserApiClient.getInstanse().addUser(userLogin + "@" + userDomain, userPassword);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

	}

	public static void main(String[] args) throws Exception {

		String userLogin = "gust11";
		String userPassword = "gust11";
		String userDomain = "cmsmanhattan3.com";

		if (DomainApiClient.getInstanse().existDomain(userDomain)) {
			UserApiClient.getInstanse().addUser(userLogin + "@" + userDomain, userPassword);
		} else {
			DomainApiClient.getInstanse().addDomain(userDomain);
			UserApiClient.getInstanse().addUser(userLogin + "@" + userDomain, userPassword);
		}

	}

}
