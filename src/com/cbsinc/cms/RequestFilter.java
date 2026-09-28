package com.cbsinc.cms;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RequestFilter implements Filter {

	private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return localRequest.get();
	}

	public static HttpServletResponse getResponse() {
		return localResponse.get();
	}

	public static HttpSession getSession() {
		HttpServletRequest request = localRequest.get();
		return (request != null) ? request.getSession() : null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		if (servletRequest instanceof HttpServletRequest) {
			localRequest.set((HttpServletRequest) servletRequest);
		}

		if (servletResponse instanceof HttpServletResponse) {
			localResponse.set((HttpServletResponse) servletResponse);
		}

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		String requestURI = request.getRequestURI();
		if (requestURI.startsWith("/imgpositions/") || requestURI.startsWith("/big_imgpositions/") || requestURI.startsWith("/imgcatalog/")
				|| requestURI.startsWith("/files/")) {
			response.setHeader("Access-Control-Allow-Origin", "*");
		}

		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			localRequest.remove();
			localResponse.remove();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	public static ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

}
