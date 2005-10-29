package org.opensails.sails.url;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.SailsException;

public class ContextUrl implements IUrl {
	protected String url;

	/**
	 * @param event
	 * @param url context relative
	 */
	public ContextUrl(ISailsEvent event, String url) {
		this.url = event.getRequest().getContextPath() + "/" + url;
	};

	public String render() {
		return url;
	}

	public IUrl secure() {
		throw new SailsException("Are you sure you want to make a context relative url secure? File a bug report if you think so. We didn't at the time we wrote this. Thank you for using Sails. :)");
	}

	@Override
	public String toString() {
		return render();
	}
}
