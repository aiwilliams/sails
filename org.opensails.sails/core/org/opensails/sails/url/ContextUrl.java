package org.opensails.sails.url;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;

public class ContextUrl extends AbstractUrl<ContextUrl> {
	protected String url;

	/**
	 * @param event
	 * @param url context relative
	 */
	public ContextUrl(ISailsEvent event, String url) {
		super(event);
		this.url = url;
	};

	public String getContextRelativePath() {
		return url;
	}

	public ContextUrl secure() {
		throw new SailsException("Are you sure you want to make a context relative url secure? File a bug report if you think so. We didn't at the time we wrote this. Thank you for using Sails. :)");
	}

	@Override
	protected String doRender() {
		return event.getRequest().getContextPath() + "/" + url;
	}

	@Override
	protected String renderAbsoluteUrl() {
		return String.format("%s/%s", event.getEventUrl().getAbsoluteContextUrl(), url);
	}
}
