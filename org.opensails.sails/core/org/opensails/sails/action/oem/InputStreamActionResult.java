package org.opensails.sails.action.oem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.http.HttpHeader;

public class InputStreamActionResult extends AbstractActionResult {
	protected static final int DEFAULT_BUFFER_SIZE = 4096;

	protected int bufferSize = DEFAULT_BUFFER_SIZE;
	protected ContentType contentType = ContentType.APP_OCTET_STREAM;
	protected InputStream inputStream;

	public InputStreamActionResult(ISailsEvent event, InputStream stream) {
		this(event);
		this.inputStream = stream;
	}

	// allows for greater subclass control
	protected InputStreamActionResult(ISailsEvent event) {
		super(event);
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public void write() {
		setHeaders(getEvent().getResponse());
		doWrite(getInputStream());
	}

	protected void doWrite(InputStream contentStream) {
		if (contentStream == null) throw new NullPointerException("Cannot write null content stream");
		try {
			OutputStream outputStream = getEvent().getResponseOutputStream();
			byte[] buffer = new byte[bufferSize];
			while (contentStream.read(buffer) != -1)
				outputStream.write(buffer);
		} catch (IOException e) {
			throw new SailsException("Failure writing content of InputStream to client", e);
		}
	}

	protected InputStream getInputStream() {
		return inputStream;
	}

	protected void setHeader(HttpServletResponse response, HttpHeader header) {
		if (header == null) return;
		response.setHeader(header.name(), header.value());
	}

	protected void setHeaders(HttpServletResponse response) {
		setHeader(response, contentType);
	}
}
