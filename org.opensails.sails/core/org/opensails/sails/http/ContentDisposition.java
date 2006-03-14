package org.opensails.sails.http;

public class ContentDisposition extends HttpHeader {
	public static final String HEADER_NAME = "Content-Disposition";

	public static final ContentDisposition ATTACHMENT = new ContentDisposition("attachment");
	public static final ContentDisposition INLINE = new ContentDisposition("inline");

	public ContentDisposition(String value) {
		super(HEADER_NAME, value);
	}
}
