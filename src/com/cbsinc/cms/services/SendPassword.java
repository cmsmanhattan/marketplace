package com.cbsinc.cms.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cbsinc.cms.AuthorizationPageBean;
import com.cbsinc.cms.QueryManager;
import com.cbsinc.cms.jms.controllers.Message;
import com.cbsinc.cms.jms.controllers.MessageSender;
import com.cbsinc.cms.jms.controllers.SendMailMessageBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Class
 *
 * @web.servlet name="sendpassword" display-name="Name for Upload"
 *              description="Description for Upload"
 * @web.servlet-mapping url-pattern="/sendpassword"
 */
public class SendPassword extends HttpServlet {
	/**
	 * <p>
	 * Title: Content Manager System
	 * </p>
	 * <p>
	 * Description: System building web application develop by Konstantin Grabko.
	 * Konstantin Grabko is Owner and author this code. Программный код написан
	 * Грабко Константином Владимировичем и является его интеллектуальной
	 * собственностью.
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2008
	 * </p>
	 * <p>
	 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
	 * </p>
	 *
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	private static final long serialVersionUID = 1L;

	static private Logger log = Logger.getLogger(SendPassword.class);

	private String strLogin = "";

	private String strPasswd = "";

	private String strFirstName = "";

	private String strLastName = "";

	AuthorizationPageBean authorizationPageBean;

	// Initialize global variables
	transient ResourceBundle resources;

	public void init() throws ServletException {

	}

	// Process the HTTP Get request

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("authorizationPageBeanId") instanceof AuthorizationPageBean) {

			if (resources == null)
				resources = PropertyResourceBundle.getBundle("localization", response.getLocale());
			String sitePath = (String) request.getSession().getAttribute("site_path");
			String passwordFile = sitePath + "\\mail\\password.txt";
			String attachFile = sitePath + "\\mail\\info.txt";
			Message messageMail = (Message) request.getSession().getAttribute("messageMail");

			String email = request.getParameter("email");
			authorizationPageBean = (AuthorizationPageBean) request.getSession()
					.getAttribute("authorizationPageBeanId");

			// Object object = getServletContext().getAttribute("sendMailAgent");
//			if (object instanceof SendMailAgent) {
//				sendMailAgent = (SendMailAgent) object;
//			}

			if (getPassowrd(email, authorizationPageBean.getSiteId())) {
				messageMail.put("@Password", strPasswd);
				messageMail.put("@Login", strLogin);
				MessageSender mqSender = null;
				try {
					mqSender = new MessageSender(request.getSession(), SendMailMessageBean.messageQuery);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = new Message();
				message.put("to", email);
				message.put("subject", resources.getString("send_password.your_password"));
				message.put("pathmessage", passwordFile);
				message.put("fields", messageMail);
				message.put("attachFile", attachFile);
				mqSender.send(message);
				// System.out.println("rezalt: "+ sendMailAgent.putMessageInPool(email,
				// resources.getString("send_password.your_password"),passwordFile, attachFile,
				// messageMail));
			}

		}

		response.sendRedirect("Authorization.jsp");
		return;
	}

	// Clean up resources
	public void destroy() {

	}

	public boolean getPassowrd(String email, String siteId) {
		QueryManager Adp = null;
		String query;
		try {
			if (email == null)
				return false;
			if (email.length() == 0)
				return false;
			Adp = new QueryManager();
			// FIX (SQL injection on the unauthenticated "forgot password" form):
			// the e-mail field was concatenated into the query. Bound as a
			// parameter; site_id is numeric and bound as a Long.
			query = "SELECT user_id , login , passwd , first_name , last_name   FROM tuser  where  e_mail = ?  and  site_id = ?";
			Adp.executeQueryWithArgs(query, new Object[] { email, Long.valueOf(siteId) });
			if (Adp.rows().size() > 0) {
				long userId = Long.parseLong((String) Adp.getValueAt(0, 0));
				strLogin = (String) Adp.getValueAt(0, 1);
				strFirstName = (String) Adp.getValueAt(0, 3);
				strLastName = (String) Adp.getValueAt(0, 4);
				// Passwords are stored hashed and cannot be recovered. Issue a random
				// temporary password: store its hash, e-mail the clear value once.
				strPasswd = com.cbsinc.cms.utils.PasswordHash.generateTemporary();
				Adp.beginTransaction();
				try (java.sql.PreparedStatement ps = Adp.getCurrentConnection()
						.prepareStatement("UPDATE tuser set passwd = ? where user_id = ?")) {
					ps.setString(1, com.cbsinc.cms.utils.PasswordHash.hash(strPasswd));
					ps.setLong(2, userId);
					ps.executeUpdate();
				}
				Adp.commit();
			} else
				return false;
		} catch (Exception ex) {
			log.error(ex);
			log.debug(ex);
			// Adp.rollback();
			return false;
		} finally {
			try {
				if (Adp != null) {
					if (Adp.getCurrentConnection().isClosed())
						Adp.close();
				}
			} catch (SQLException ex1) {
				log.error(ex1);
			}
		}
		return true;
	}

}
