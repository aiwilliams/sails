package org.opensails.sails.url;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;

public class ServletUrl implements IUrl {
	protected final ISailsEvent event;
	protected String url;

	/**
	 * @param event
	 * @param url context relative
	 */
	public ServletUrl(ISailsEvent event, String url) {
		this.event = event;
		this.url = url;
	};

	public AbsoluteUrl absolute() {
		StringBuilder builder = new StringBuilder();
		builder.append(event.getEventUrl().getAbsolutServletUrl());
		appendUrl(builder);
		return new AbsoluteUrl(event, builder.toString());
	}

	public String render() {
		StringBuilder builder = new StringBuilder();
		builder.append(event.getRequest().getContextPath() + event.getRequest().getServletPath());
		appendUrl(builder);
		return builder.toString();
	}

	public IUrl secure() {
		throw new SailsException("Are you sure you want to make a context relative url secure? File a bug report if you think so. We didn't at the time we wrote this. Thank you for using Sails. :)");
	}

	@Override
	public String toString() {
		return render();
	}

	protected void appendUrl(StringBuilder builder) {
		if (url != null) {
			builder.append("/");
			builder.append(url);
		}
	}
}
