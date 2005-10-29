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
 * Filter used to redirect a root context URL (i.e., "/context" or "/context/"
 * to the Sails application servlet (typically, "/context/app"). This servlet is
 * mapped to "/" and must have a &lt;init-parameter&;gt;
 * <code>redirect-path</code> that is the application servlet's path (i.e.,
 * "/app"). If no value is specified, then "/app" is used. The path is always
 * relative to the servlet context, and should always begin with a leading
 * slash.
 * 
 * <p>
 * Filters are only available in Servlet API 2.3 and above.
 * 
 * <p>
 * Servlet API 2.4 is expected to allow a servlets in the welcome list
 * (equivalent to index.html or index.jsp), at which point this filter should no
 * longer be necessary. Unfortunately, testing proves that is still is.
 */

public class RedirectFilter implements Filter {
    public static final String REDIRECT_PATH_PARAM = "redirect-path";

    protected String redirectPath;

    public void destroy() {
        redirectPath = null;
    }

    /**
     * This filter intercepts the "default" servlet, whose job is to provide
     * access to standard resources packaged within the web application context.
     * It redirects requests to the default servlet to the Sails application
     * servlet.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpServletResponse hresponse = (HttpServletResponse) response;
        String servletPath = hrequest.getServletPath();
        String defaultServletPath = "/";
        if (servletPath.equals(defaultServletPath)) hresponse.sendRedirect(hrequest.getContextPath() + redirectPath);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        redirectPath = config.getInitParameter(REDIRECT_PATH_PARAM);
        if (redirectPath == null) redirectPath = "/app";
    }
}
