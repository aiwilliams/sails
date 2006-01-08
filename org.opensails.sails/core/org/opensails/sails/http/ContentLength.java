package org.opensails.sails.http;

public class ContentLength extends HttpHeader {
	public static final String HEADER_NAME = "Content-Length";

	public ContentLength(int value) {
		super(HEADER_NAME, String.valueOf(value));
	}

	public ContentLength(long value) {
		super(HEADER_NAME, String.valueOf(value));
	}

	public ContentLength(String value) {
		super(HEADER_NAME, value);
	}
}
