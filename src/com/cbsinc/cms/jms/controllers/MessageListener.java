package com.cbsinc.cms.jms.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public interface MessageListener {

	public static String messageQuery = null;

	// public String getMessageQuery ();

	public void onMessage(Message message, ServletContext applicationContext, HttpSession httpSession);

}
