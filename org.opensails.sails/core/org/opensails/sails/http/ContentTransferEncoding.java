package org.opensails.sails.http;

public class ContentTransferEncoding extends HttpHeader {
	public static final ContentTransferEncoding BINARY = new ContentTransferEncoding("binary");
	public static final String HEADER_NAME = "Content-Transfer-Encoding";

	public ContentTransferEncoding(String value) {
		super(HEADER_NAME, value);
	}
}
