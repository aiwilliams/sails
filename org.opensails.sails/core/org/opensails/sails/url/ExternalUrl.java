package org.opensails.sails.url;

import org.opensails.sails.event.ISailsEvent;

public class ExternalUrl extends AbsoluteUrl {
	public ExternalUrl(ISailsEvent event, String absoluteHref) {
		super(event, absoluteHref);
	}

	public ExternalUrl(String absoluteHref) {
		super(null, absoluteHref);
	}
}
