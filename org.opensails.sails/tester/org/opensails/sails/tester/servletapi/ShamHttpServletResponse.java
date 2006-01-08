/*
 * Created on Jan 30, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.tester.servletapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.http.ContentType;

public class ShamHttpServletResponse implements HttpServletResponse {
	public boolean cookiesSupported = true;
	private boolean committed;
	private boolean getOutputStreamCalled, getWriterCalled;
	private int status;
	private boolean wasReset;
	protected List<String> encodedRedirectUrls = new ArrayList<String>();
	// The encoded urls are stored in different lists so that there is no
	// question whether the correct encode method was used.
	protected List<String> encodedUrls = new ArrayList<String>();
	protected Map<String, String> headers = new HashMap<String, String>();
	protected ByteArrayOutputStream outputStream;
	protected String redirectDestination;
	protected boolean sendRedirectWasCalled;

	public ShamHttpServletResponse() {
		super();
		outputStream = new ByteArrayOutputStream();
	}

	public void addCookie(Cookie cookie) {}

	public void addDateHeader(String name, long date) {}

	public void addHeader(String name, String value) {}

	public void addIntHeader(String name, int value) {}

	public boolean containsHeader(String name) {
		return false;
	}

	public String encodeRedirectUrl(String url) {
		throw new UnsupportedOperationException("This is deprecated, so let us not use it");
	}

	public String encodeRedirectURL(String url) {
		encodedRedirectUrls.add(url);
		// According to API, encoding of session only occurs if cookies are not
		// supported by browser.
		if (cookiesSupported) return url;
		return url + ";sessionencoded=ShamSession";
	}

	/**
	 * @deprecated
	 */
	public String encodeUrl(String url) {
		throw new UnsupportedOperationException("This is deprecated, so let us not use it");
	}

	public String encodeURL(String url) {
		encodedUrls.add(url);
		// According to API, encoding of session only occurs if cookies are not
		// supported by browser.
		if (cookiesSupported) return url;
		return url + ";sessionencoded=ShamSession";
	}

	public void flushBuffer() throws IOException {
		outputStream.flush();
		committed = true;
	}

	public int getBufferSize() {
		return outputStream.size();
	}

	public String getCharacterEncoding() {
		return null;
	}

	public String getContentType() {
		return headers.get(ContentType.HEADER_NAME);
	}

	public String getHeader(String name) {
		return (String) headers.get(name);
	}

	public Map getHeaders() {
		return headers;
	}

	public Locale getLocale() {
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (getOutputStreamCalled || getWriterCalled) throw new IllegalStateException("Implementing contract of interface ServletResponse: the output stream can be obtained only once");
		ServletOutputStream servletOutputStream = new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				outputStream.write(b);
			}
		};
		getOutputStreamCalled = true;
		return servletOutputStream;
	}

	/**
	 * @return the URL this response was redirected to, null if not redirected
	 * @see #wasRedirected()
	 */
	public String getRedirectDestination() {
		return redirectDestination;
	}

	public int getStatus() {
		return status;
	}

	public PrintWriter getWriter() throws IOException {
		if (getWriterCalled || getOutputStreamCalled) throw new IllegalStateException("Implementing contract of interface ServletResponse: getWriter can be called only once");
		PrintWriter printWriter = new PrintWriter(outputStream) {
			@Override
			public void write(String s) {
				try {
					outputStream.write(s.getBytes());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
		getWriterCalled = true;
		return printWriter;

	}

	public String getWrittenContent() {
		return stripNullCharacters(outputStream.toString());
	}

	public boolean isCommitted() {
		return committed;
	}

	public void reset() {
		if (committed) throw new IllegalStateException("Commited response cannot be reset");
		resetBuffer();
		headers.clear();
		wasReset = true;
	}

	public void resetBuffer() {
		outputStream.reset();
	}

	public void sendError(int sc) throws IOException {
		status = sc;
	}

	public void sendError(int sc, String msg) throws IOException {}

	public void sendRedirect(String location) throws IOException {
		setHeader("Location", location);
		committed = true;
		setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		redirectDestination = location;
		sendRedirectWasCalled = true;
	}

	public boolean sendRedirectWasCalled() {
		return sendRedirectWasCalled;
	}

	public void setBufferSize(int size) {}

	public void setCharacterEncoding(String arg0) {
	// TODO Auto-generated method stub

	}

	public void setContentLength(int len) {}

	public void setContentType(String type) {
		setHeader(ContentType.HEADER_NAME, type);
	}

	public void setDateHeader(String name, long date) {}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	public void setIntHeader(String name, int value) {}

	public void setLocale(Locale loc) {}

	public void setStatus(int sc) {
		status = sc;
	}

	public void setStatus(int sc, String sm) {
		throw new RuntimeException("This is a depricated method");
	}

	public boolean wasRedirected() {
		return null != redirectDestination;
	}

	public boolean wasReset() {
		return wasReset;
	}

	public boolean wasUrlEncoded(String url) {
		return encodedUrls.contains(url);
	}

	public boolean wasUrlRedirectEncoded(String url) {
		return encodedRedirectUrls.contains(url);
	}

	protected String stripNullCharacters(String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++)
			if (string.charAt(i) != '\0') sb.append(string.charAt(i));
		return sb.toString();
	}
}