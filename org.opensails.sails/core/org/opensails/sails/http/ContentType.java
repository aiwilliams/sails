package org.opensails.sails.http;

public class ContentType extends HttpHeader {
	public static final ContentType APP_OCTET_STREAM = new ContentType("application/octet-stream");
	public static final String HEADER_NAME = "Content-Type";
	public static final ContentType TEXT_HTML = new ContentType("text/html");

	public ContentType(String value) {
		super(HEADER_NAME, value);
	}
}
