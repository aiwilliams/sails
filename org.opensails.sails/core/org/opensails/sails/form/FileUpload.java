package org.opensails.sails.form;

import java.io.*;

import org.apache.commons.fileupload.*;
import org.opensails.sails.*;

public class FileUpload {
	private final FileItem item;

	public FileUpload(FileItem item) {
		this.item = item;
	}

	public byte[] byteContent() {
		return item.get();
	}

	public String getFileName() {
		return String.format("FileUpload: %s", item.getName());
	}

	public InputStream streamContent() {
		try {
			return item.getInputStream();
		} catch (IOException e) {
			throw new SailsException(String.format("Failure obtaining input stream on %s", item), e);
		}
	}

	public String stringContent() {
		return item.getString();
	}

	public String stringContent(String encoding) {
		try {
			return item.getString(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new SailsException(String.format("Failure obtaining encoded content on %s", item), e);
		}
	}
	
	public String filename() {
		return item.getName();
	}
	
	@Override
	public String toString() {
		return filename();
	}
}