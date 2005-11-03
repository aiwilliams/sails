/*
 * Created on Jan 30, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.servletapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class ShamHttpServletRequest implements HttpServletRequest {
	public static final String DELETE = "DELETE";
	public static final String GET = "GET";
	public static final String POST = "POST";

	protected Map<String, Object> attributes = new HashMap<String, Object>();
	protected String contextPath = "/shamcontext";
	protected Map<String, String> headers = new HashMap<String, String>();

	protected HttpSession httpSession;
	protected String method;

	// Note that all values, if present, are String[]s
	protected Map<String, Object> parameters = new HashMap<String, Object>();
	protected String pathInfo;
	protected int port = 80;
	protected Principal principal;

	protected String scheme = "http";
	protected String serverName = "localhost";
	protected String servletPath = "/shamservlet";

	public ShamHttpServletRequest() {
		this(GET);
	}

	public ShamHttpServletRequest(HttpSession anHttpSession) {
		this(GET, anHttpSession);
	}

	public ShamHttpServletRequest(String method) {
		this(method, null);
	}

	public ShamHttpServletRequest(String method, HttpSession anHttpSession) {
		this.method = method;
		this.httpSession = anHttpSession;
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public Enumeration getAttributeNames() {
		return null;
	}

	public String getAuthType() {
		return null;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public int getContentLength() {
		return 0;
	}

	public String getContentType() {
		return null;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Cookie[] getCookies() {
		return null;
	}

	public long getDateHeader(String name) {
		return 0;
	}

	public String getHeader(String name) {
		return (String) headers.get(name);
	}

	public Enumeration getHeaderNames() {
		return null;
	}

	public Enumeration getHeaders(String name) {
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	public int getIntHeader(String name) {
		return 0;
	}

	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public Enumeration getLocales() {
		return null;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMethod() {
		return method;
	}

	public String getParameter(String key) {
		String[] parameter = (String[]) parameters.get(key);
		if (parameter == null) return null;
		return parameter[0];
	}

	public Map getParameterMap() {
		return Collections.unmodifiableMap(parameters);
	}

	public Enumeration getParameterNames() {
		return Collections.enumeration(parameters.keySet());
	}

	public String[] getParameterValues(String key) {
		return (String[]) parameters.get(key);
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public String getPathTranslated() {
		return null;
	}

	public String getProtocol() {
		return "HTTP/1.1";
	}

	public String getQueryString() {
		StringBuffer query = new StringBuffer();
		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getValue().getClass().isArray()) appendAllValuesToQueryString(query, (String) entry.getKey(), (String[]) entry.getValue());
			else {
				query.append(entry.getKey());
				query.append("=");
				query.append(entry.getValue());
			}
		}
		if (StringUtils.isEmpty(query.toString())) return null;
		return query.toString();
	}

	public BufferedReader getReader() throws IOException {
		return null;
	}

	public String getRealPath(String path) {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getRemoteUser() {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public String getRequestedSessionId() {
		return null;
	}

	public String getRequestURI() {
		return null;
	}

	public StringBuffer getRequestURL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getScheme());
		buffer.append("://");
		buffer.append(getServerName());
		if (!(80 == getServerPort())) {
			buffer.append(":");
			buffer.append(getServerPort());
		}
		if (getContextPath() != null && !"".equals(getContextPath())) {
			buffer.append(getContextPath());
			buffer.append(getServletPath());
			buffer.append("/");
			if (getPathInfo() != null && !"".equals(getPathInfo())) buffer.append(getPathInfo());
		}
		return buffer;
	}

	public String getScheme() {
		return scheme;
	}

	public String getServerName() {
		return serverName;
	}

	public int getServerPort() {
		return port;
	}

	public String getServletPath() {
		return servletPath;
	}

	public HttpSession getSession() {
		return httpSession;
	}

	public HttpSession getSession(boolean create) {
		if (httpSession == null && create) httpSession = new ShamHttpSession();
		return httpSession;
	}

	public Principal getUserPrincipal() {
		return principal;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	public boolean isRequestedSessionIdValid() {
		return false;
	}

	public boolean isSecure() {
		return false;
	}

	public boolean isUserInRole(String role) {
		return false;
	}

	public void removeAttribute(String name) {}

	public void reset() {
		attributes.clear();
		parameters.clear();
		headers.clear();
		servletPath = null;
		contextPath = null;
	}

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {}

	/**
	 * The context path with or without slashes.
	 */
	public void setContextPath(String aContextPath) {
		contextPath = aContextPath;
		if (!contextPath.startsWith("/")) contextPath = "/" + contextPath;
	}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	/**
	 * Gaurds the contract of getParameter() and getParameterMap()
	 */
	public void setParameter(String parameterName, String parameterValue) {
		if (parameterValue == null) parameters.put(parameterName, parameterValue);
		else parameters.put(parameterName, new String[] { parameterValue });
	}

	/**
	 * Replaces the parameters completely. Your values will be converted to
	 * String[]s to maintain the HttpServletRequest API.
	 * 
	 * @param parameters
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
		for (Entry<String, Object> entry : parameters.entrySet()) {
			Object value = entry.getValue();
			if (value == null) value = StringUtils.EMPTY;
			if (!String[].class.equals(value.getClass())) entry.setValue(new String[] { (String) value });
		}
	}

	/**
	 * Gaurds the contract of getParameters() and getParameterMap()
	 */
	public void setParameters(String parameterName, String[] parameterValues) {
		if (parameterValues == null || parameterValues.length == 0) parameters.put(parameterName, null);
		else parameters.put(parameterName, parameterValues);
	}

	/**
	 * The path info without a leading or trailing slash.
	 */
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	public void setScheme(String schemeLikeHttp) {
		this.scheme = schemeLikeHttp;
	}

	public void setServerName(String nameLikeLocalhost) {
		this.serverName = nameLikeLocalhost;
	}

	public void setServerPort(int port) {
		this.port = port;
	}

	/**
	 * The servlet path with or without slashes.
	 */
	public void setServletPath(String aServletPath) {
		servletPath = aServletPath;
		if (!servletPath.startsWith("/")) servletPath = "/" + servletPath;
	}

	public void setUserPrincipal(Principal aPrincipal) {
		principal = aPrincipal;
	}

	/**
	 * @param parameters to update from. Note that the values, if not already a
	 *        String[], will be converted to one. This maintains the
	 *        HttpServletRequest API.
	 */
	public void updateParameters(Map newParameters) {
		for (Iterator iter = newParameters.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = newParameters.get(key);
			if (value instanceof String[]) setParameters(key, (String[]) value);
			else setParameter(key, (String) value);
		}
	}

	private void appendAllValuesToQueryString(StringBuffer query, String key, String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			query.append(key);
			query.append("=");
			query.append(strings[i]);
			if (i < strings.length - 1) query.append("&");
		}
	}
}