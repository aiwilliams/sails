package org.opensails.sails.form;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.opensails.sails.SailsException;

public class FileUpload {
	private FileItem item;

	protected FileUpload() {}

	public FileUpload(FileItem item) {
		this.item = item;
	}

	public byte[] byteContent() {
		return item.get();
	}

	public String filename() {
		return item.getName();
	}

	public String getContentType() {
		return item.getContentType();
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

	@Override
	public String toString() {
		return filename();
	}
}