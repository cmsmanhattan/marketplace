package com.cbsinc.cms;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class XmltoJsonFilter implements Filter {

	public void destroy() {
		// do nothing
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (res instanceof HttpServletResponse) {
			HttpServletResponse response = (HttpServletResponse) res;
			if ("json".equals(req.getParameter("format"))) {
				response.setContentType("application/json");
				XmlToJsonResponseWrapper wrappedResponse = new XmlToJsonResponseWrapper(response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
			} else {
				chain.doFilter(req, res);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}
}
