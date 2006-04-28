package org.opensails.sails.html;

import org.opensails.sails.url.IUrl;

public class Link extends AbstractHtmlElement<Link> {
	public static final String NAME = "link";

	public Link() {
		super(NAME);
	}

	public Link(IUrl href) {
		this();
		href(href);
	}

	public void href(IUrl url) {
		attribute("href", url.renderThyself());
	}

	public void rel(String relationship) {
		attribute("rel", relationship);
	}
}
