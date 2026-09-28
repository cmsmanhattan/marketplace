/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * You can not use it and you cannot change it without written permission from Konstantin Grabko
 * Email: konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002-2025
 * </p>
 * <p>
 * Company: CENTER BUSINESS SOLUTIONS INC
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

package com.cbsinc.cms;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.cbsinc.cms.controllers.SiteRole;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filter class
 *
 * @web.filter name="Auth" display-name="Name for Auth" description="Description
 *             for Auth"
 * @web.filter-mapping url-pattern="/catalog_list.jsp"
 * @web.filter-mapping url-pattern="/catalog_add.jsp"
 * @web.filter-mapping url-pattern="/catalog_edit.jsp"
 * @web.filter-mapping url-pattern="/CreateShop.jsp"
 * @web.filter-mapping url-pattern="/CMusicPost.jsp"
 * @web.filter-mapping url-pattern="/ProductPost.jsp"
 * @web.filter-mapping url-pattern="/PayGatewaySetup.jsp"
 * @web.filter-mapping url-pattern="/SelectFile.jsp"
 * @web.filter-mapping url-pattern="/SelectBigImage.jsp"
 * @web.filter-mapping url-pattern="/SelectImage.jsp"
 * @web.filter-mapping url-pattern="/PayGatewayList.jsp"
 * @web.filter-mapping url-pattern="/uploadservlet"
 * @web.filter-mapping url-pattern="/bigimageservletupload"
 * @web.filter-mapping url-pattern="/downloadservlet"
 * @web.filter-mapping url-pattern="/downloadservletbyodrder"
 * @web.filter-mapping url-pattern="/imageservlet"
 * @web.filter-mapping url-pattern="/imageservletupload"
 * @web.filter-mapping url-pattern="/uploadservletxsl"
 * @web.filter-mapping url-pattern="/fileservletupload"
 *
 */

public class AuthFilter implements Filter {
	/**
	 *
	 */
	static final long serialVersionUID = 1L;

	private FilterConfig filterConfig;

	static private Logger log = Logger.getLogger(AuthFilter.class);

	/**
	 * Minimum value of AuthorizationPageBean.getRoleId() required to pass this
	 * filter. Defaults to SiteRole.MEMBER_ROLE_ID (1), i.e. any registered user;
	 * guests are level 0. Override per deployment with a filter init-param:
	 *
	 * <pre>
	 * &lt;init-param&gt;
	 *   &lt;param-name&gt;minAccessLevel&lt;/param-name&gt;
	 *   &lt;param-value&gt;2&lt;/param-value&gt;
	 * &lt;/init-param&gt;
	 * </pre>
	 */
	private long minAccessLevel = SiteRole.MEMBER_ROLE_ID;

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		String configured = filterConfig.getInitParameter("minAccessLevel");
		if (configured != null && configured.trim().length() > 0) {
			try {
				minAccessLevel = Long.parseLong(configured.trim());
			} catch (NumberFormatException e) {
				log.error("minAccessLevel is not a number: " + configured + ", keeping " + minAccessLevel, e);
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		try {

			HttpSession hsession = ((HttpServletRequest) request).getSession(false);
			if (hsession == null) {
				deny((HttpServletResponse) response);
				return;
			}

			Object attribute = hsession.getAttribute("authorizationPageBeanId");
			if (!(attribute instanceof AuthorizationPageBean)) {
				deny((HttpServletResponse) response);
				return;
			}

			AuthorizationPageBean authorizationPageBeanId = (AuthorizationPageBean) attribute;

			// FIX: the previous test was getStrLogin().length() == 0.
			// FrontControllers assigns every anonymous visitor
			// setStrLogin(SiteRole.GUEST) (= "user") and setRoleId(0), so the
			// login string is NEVER empty and this filter admitted every guest to
			// every protected page. Authenticate on the identity and the access
			// level instead of on the presence of a login string.
			String login = authorizationPageBeanId.getStrLogin();
			if (login == null || login.length() == 0 || SiteRole.GUEST.equals(login)) {
				deny((HttpServletResponse) response);
				return;
			}

			if (authorizationPageBeanId.getIntUserID() == SiteRole.GUEST_ID
					|| authorizationPageBeanId.getIntUserID() == 0) {
				deny((HttpServletResponse) response);
				return;
			}

			if (authorizationPageBeanId.getRoleId() < minAccessLevel) {
				deny((HttpServletResponse) response);
				return;
			}

			filterChain.doFilter(request, response);
		} catch (ServletException sx) {
			log.error(sx);
			logQuietly(sx);
		} catch (IOException iox) {
			log.error(iox);
			logQuietly(iox);
		}
	}

	/**
	 * Sends an unauthenticated caller back to the front page.
	 */
	private void deny(HttpServletResponse response) throws IOException {
		if (response.isCommitted())
			return;
		response.sendRedirect("index.jsp");
	}

	/**
	 * FIX: the old code called servletContext.log(e.getMessage()) directly.
	 * getMessage() is null for a NullPointerException and for most
	 * ClassCastExceptions, and the container log call then hides the real
	 * failure behind a second one. Fall back to the exception itself.
	 */
	private void logQuietly(Exception e) {
		if (filterConfig == null)
			return;
		String message = e.getMessage();
		if (message == null)
			message = e.getClass().getName();
		filterConfig.getServletContext().log(message, e);
	}

	public void destroy() {
	}
}
