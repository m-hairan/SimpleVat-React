package com.simplevat.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BasicUrlFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		String requestURI = hreq.getRequestURI();
		requestURI = requestURI.replace(hreq.getContextPath(), "");
		HttpSession session = hreq.getSession();
		Breadcrumb breadcrumb = (Breadcrumb)session.getAttribute("breadcrumb");
		if(breadcrumb == null){
			breadcrumb = new Breadcrumb();
			session.setAttribute("breadcrumb", breadcrumb);
		}
		breadcrumb.addUri(requestURI);
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

}
