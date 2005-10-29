package org.opensails.sails.url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class EventUrl implements IEventUrl {
	protected String action;
	protected String controller;
	protected String[] parameters;
	protected final HttpServletRequest request;

	public EventUrl(HttpServletRequest req) {
		this.request = req;
		initialize(req, readPathInfo(req));
	}

	public String getAbsoluteContextUrl() {
		int serverPort = request.getServerPort();
		StringBuilder buffer = new StringBuilder(request.getScheme());
		buffer.append("://");
		buffer.append(request.getServerName());
		if (serverPort != 80) {
			buffer.append(":");
			buffer.append(serverPort);
		}
		buffer.append(request.getContextPath());
		return buffer.toString();
	}

	public String getAbsoluteUrl() {
		StringBuilder url = new StringBuilder();
		url.append(getAbsolutServletUrl());
		if (request.getPathInfo() != null) url.append(request.getPathInfo());
		return url.toString();
	}

	public String getAbsolutServletUrl() {
		return getAbsoluteContextUrl() + request.getServletPath();
	}

	public String getAction() {
		return action;
	}

	public String[] getActionParameters() {
		return parameters;
	}

	public String getActionUrl() {
		StringBuilder url = new StringBuilder();
		url.append("/");
		url.append(getController());
		url.append("/");
		url.append(getAction());
		if (getActionParameters().length > 0) {
			url.append("/");
			url.append(StringUtils.join(getActionParameters(), '/'));
		}
		return url.toString();
	}

	public String getController() {
		return controller;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	protected String[] getCleanPathInfo(String pathInfo) {
		if (pathInfo == null) return ArrayUtils.EMPTY_STRING_ARRAY;
		if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		if (pathInfo.equals(StringUtils.EMPTY)) return ArrayUtils.EMPTY_STRING_ARRAY;
		return massagePathInfo(pathInfo.split("/"));
	}

	protected String getDefaultActionName() {
		return "index";
	}

	protected String getDefaultControllerName() {
		return "home";
	}

	protected void initialize(HttpServletRequest request, String[] pathInfo) {
		this.controller = loadControllerName(request, pathInfo);
		this.action = loadActionName(request, pathInfo);
		this.parameters = loadActionParameters(request, pathInfo);
	}

	protected String loadActionName(HttpServletRequest request, String[] pathInfo) {
		if (pathInfo.length < 2) return getDefaultActionName();
		else return pathInfo[1];
	}

	protected String[] loadActionParameters(HttpServletRequest request, String[] pathInfo) {
		if (pathInfo.length <= 2) return ArrayUtils.EMPTY_STRING_ARRAY;
		String[] parameters = new String[pathInfo.length - 2];
		System.arraycopy(pathInfo, 2, parameters, 0, parameters.length);
		return parameters;
	}

	protected String loadControllerName(HttpServletRequest request, String[] pathInfo) {
		if (pathInfo.length < 1) return getDefaultControllerName();
		else return pathInfo[0];
	}

	/**
	 * Provides the opportunity to mangle the pathInfo before it gets
	 * interpreted by {@link #initialize(HttpServletRequest, String[])}. This
	 * can return a new String[] or the one given.
	 * 
	 * This implementation removes any encoded session information as created by
	 * {@link HttpServletResponse#encodeURL(java.lang.String)}or
	 * {@link HttpServletResponse#encodeRedirectURL(java.lang.String)}.
	 * 
	 * @param pathInfo everything after the context/servlet part of a URL
	 */
	protected String[] massagePathInfo(String[] pathInfo) {
		if (pathInfo.length == 0) return pathInfo;
		for (int i = 0; i < pathInfo.length; i++) {
			String part = pathInfo[i];
			if (part.indexOf(';') > 0) pathInfo[i] = part.substring(0, part.indexOf(';'));
		}
		return pathInfo;
	}

	protected String[] readPathInfo(HttpServletRequest request) {
		return getCleanPathInfo(request.getPathInfo());
	}
}
