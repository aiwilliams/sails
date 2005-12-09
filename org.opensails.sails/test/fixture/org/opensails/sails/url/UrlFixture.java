package org.opensails.sails.url;

public class UrlFixture {
	public static IUrl create(final String url) {
		return new ExternalUrl(null, url);
	}
}
