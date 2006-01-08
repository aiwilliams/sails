package org.opensails.sails.action.oem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.http.ContentDisposition;
import org.opensails.sails.http.ContentLength;
import org.opensails.sails.http.ContentTransferEncoding;

public class FileSendActionResult extends InputStreamActionResult {
	protected ContentDisposition disposition = ContentDisposition.ATTACHMENT;
	protected ContentTransferEncoding encoding = ContentTransferEncoding.BINARY;
	protected File file;

	protected ContentLength length;

	public FileSendActionResult(ISailsEvent event, InputStream stream) {
		super(event, stream);
	}

	public FileSendActionResult(ISailsEvent event, String path) {
		super(event);
		this.file = new File(path);
		if (!file.exists() || !file.canRead()) throw new IllegalArgumentException(String.format("File does not exist or cannot be read [%s]", path));
	}

	public void setDisposition(ContentDisposition disposition) {
		this.disposition = disposition;
	}

	public void setEncoding(ContentTransferEncoding encoding) {
		this.encoding = encoding;
	}

	@Override
	protected InputStream getInputStream() {
		try {
			return file == null ? inputStream : new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new SailsException(String.format("There is no file to send to client located at [%s]"), e);
		}
	}

	@Override
	protected void setHeaders(HttpServletResponse response) {
		super.setHeaders(response);
		setHeader(response, disposition);
		setHeader(response, encoding);
		if (length == null && file != null) length = new ContentLength(file.length());
		setHeader(response, length);
	}
}
