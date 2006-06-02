package org.opensails.sails.url;

import org.opensails.sails.event.ISailsEvent;

/**
 * An IUrl that is relative to the web application context.
 * 
 * @author aiwilliams
 */
public class ContextUrl<T extends ContextUrl> extends AbstractUrl<T> {
	protected String url;
	protected AdaptingQueryParameters queryParams;

	/**
	 * @param event
	 * @param url context relative
	 */
	public ContextUrl(ISailsEvent event) {
		super(event);
		this.queryParams = new AdaptingQueryParameters(event);
	};

	/**
	 * @param event
	 * @param url context relative, no query parameters
	 */
	public ContextUrl(ISailsEvent event, String url) {
		this(event);
		this.url = url;
	}

	/**
	 * @return the path relative to the context, not including query parameters
	 */
	public String getContextRelativePath() {
		return url;
	}

	public String getQueryParameter(String name) {
		return queryParams.get(name);
	};

	public ContextUrl<T> secure() {
		this.secure = true;
		return this;
	}

	/**
	 * Adds support for setting query parameters from Java Objects.
	 * 
	 * @param name
	 * @param value
	 */
	public void setQueryParameter(String name, Object value) {
		queryParams.set(name, value);
	}

	public void setQueryParameter(String name, String value) {
		queryParams.set(name, value);
	}

	@Override
	protected String doRender() {
		return event.getRequest().getContextPath() + "/" + url;
	}

	@Override
	protected String renderAbsoluteUrl() {
		return doRenderUrl(event.getUrl().getAbsoluteContextUrl());
	}

	private String doRenderUrl(String contextPath) {
		StringBuilder u = new StringBuilder();
		u.append(contextPath);
		u.append("/");
		u.append(url);
		u.append(queryParams);
		return u.toString();
	}
}
