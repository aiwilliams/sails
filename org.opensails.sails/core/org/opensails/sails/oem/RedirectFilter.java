package org.opensails.sails.oem;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Must have an &lt;init-parameter&gt; <code>redirect-path</code>. Defaults
 * to "/app". The path is always relative to the servlet context, and should
 * always begin with a leading slash.
 * 
 * <p>
 * Filters are only available in Servlet API 2.3 and above.
 */
public class RedirectFilter implements Filter {
	public static final String REDIRECT_PATH_PARAM = "redirect-path";

	protected String redirectPath;

	public void destroy() {
		redirectPath = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(httpRequest.getContextPath() + redirectPath);
	}

	public void init(FilterConfig config) throws ServletException {
		redirectPath = config.getInitParameter(REDIRECT_PATH_PARAM);
		if (redirectPath == null) redirectPath = "/app";
	}
}
