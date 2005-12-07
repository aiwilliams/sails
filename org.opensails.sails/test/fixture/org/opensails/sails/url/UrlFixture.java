package org.opensails.sails.url;

public class UrlFixture {
	public static IUrl create(final String url) {
		return new IUrl() {
			public AbsoluteUrl absolute() {
				return new AbsoluteUrl(null, url);
			}

			public String render() {
				return url;
			}

			public IUrl secure() {
				return this;
			}
		};
	}
}
