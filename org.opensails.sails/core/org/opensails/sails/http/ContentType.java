package org.opensails.sails.http;

public class ContentType {
	public static final String HEADER_KEY = "Content-Type";

	public static final ContentType TEXT_HTML = new ContentType("text/html");

	protected String httpContentType;

	public ContentType(String httpContentType) {
		this.httpContentType = httpContentType;
	}

	public String toHttpValue() {
		return httpContentType;
	}

	@Override
	public String toString() {
		return toHttpValue();
	}
}
