package org.opensails.sails.action.oem;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import org.opensails.sails.event.ISailsEvent;

public class InputStreamActionResult extends AbstractActionResult {

	protected static final int BUFFER_SIZE = 1024;
	protected final InputStream inputStream;

	public InputStreamActionResult(ISailsEvent event, InputStream stream) {
		super(event);
		this.inputStream = stream;
	}

	public void write() {
		try {
			ServletOutputStream outputStream = getEvent().getResponse().getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];

			while (inputStream.read(buffer) != -1)
				outputStream.write(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
