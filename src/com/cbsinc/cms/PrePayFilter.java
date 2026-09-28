package com.cbsinc.cms;

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

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filter class
 *
 * @web.filter name="PrePay" display-name="Name for PrePay"
 *             description="Description for PrePay"
 * @web.filter-mapping url-pattern="/PrePay.jsp"
 * @web.filter-init-param name="A parameter" value="A value"
 */
public class PrePayFilter implements Filter {
	private FilterConfig filterConfig;
	private String xsltFileName;
	static private Logger log = Logger.getLogger(PrePayFilter.class);

	/**
	 * This method is called once when the filter is first loaded.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession hsession = ((HttpServletRequest) request).getSession(false);
		AuthorizationPageBean authorizationPageBeanId;
		if (hsession != null) {
			if (hsession.getAttribute("authorizationPageBeanId") instanceof AuthorizationPageBean) {
				authorizationPageBeanId = ((AuthorizationPageBean) hsession.getAttribute("authorizationPageBeanId"));
				if (authorizationPageBeanId.getStrLogin().length() == 0) {
					((HttpServletResponse) response).sendRedirect("index.jsp");
					return;
				}

			} else {
				((HttpServletResponse) response).sendRedirect("index.jsp");
				return;
			}

		} else {
			((HttpServletResponse) response).sendRedirect("index.jsp");
			return;
		}

		StringBuffer buffString = new StringBuffer("/xsl/");
		buffString.append(authorizationPageBeanId.getSiteDir().trim());
		buffString.append("/prepay.xsl");
		this.xsltFileName = filterConfig.getServletContext().getRealPath(buffString.toString());
		if (this.xsltFileName == null || !new File(this.xsltFileName).exists()) {
			throw new UnavailableException("Unable to locate stylesheet: " + this.xsltFileName, 30);
		}

		Source styleSource = new StreamSource(new File(xsltFileName));
		byte[] htmlBytes;
		String htmlData = "";
		ServletOutputStream out = response.getOutputStream();
		CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, responseWrapper);
		// Get response from servlet
		StringReader sr = new StringReader(new String(responseWrapper.getData()).trim());
		Source xmlSource = new StreamSource((Reader) sr);

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(styleSource);
			CharArrayWriter caw = new CharArrayWriter();
			StreamResult result = new StreamResult(caw);
			transformer.transform(xmlSource, result);
			htmlData = caw.toString();
			htmlBytes = htmlData.getBytes("UTF-8");
			response.setContentLength(htmlBytes.length);
			out.write(htmlBytes);
			out.flush();
		} catch (Exception ex) {
			log.error(ex);
			out.println(ex.toString());
		} finally {
			out.close();
		}

	}

	/**
	 * The counterpart to the init( ) method.
	 */
	public void destroy() {
		this.filterConfig = null;
	}
}
