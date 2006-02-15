package org.opensails.sails.tester.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.opensails.sails.form.FileUpload;

public class TestFileUpload extends FileUpload {
	protected final String content;
	protected final String name;

	public TestFileUpload(String name, String content) {
		this.name = name;
		this.content = content;
	}

	@Override
	public byte[] byteContent() {
		return content.getBytes();
	}

	@Override
	public String filename() {
		return name;
	}

	@Override
	public String getFileName() {
		return name;
	}

	@Override
	public InputStream streamContent() {
		return new ByteArrayInputStream(content.getBytes());
	}

	@Override
	public String stringContent() {
		return content;
	}

	@Override
	public String stringContent(String encoding) {
		return content;
	}
}
