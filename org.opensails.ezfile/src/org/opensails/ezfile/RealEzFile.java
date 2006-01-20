package org.opensails.ezfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class RealEzFile implements EzFile {
	protected File file;

	public RealEzFile(File file) {
		if (file.exists() && !file.isFile()) throw new IllegalArgumentException("Not a file " + file);
		this.file = file;
	}

	public RealEzFile(String fileName) {
		this(new File(fileName));
	}

	/**
	 * BDUF nop
	 */
	public EzDir asDir() {
		return null;
	}

	public boolean exists() {
		return file.exists();
	}

	public String getPath() {
		return file.getPath();
	}

	public boolean isDirectory() {
		return false;
	}

	public void mkdirs() {
		file.mkdirs();
	}

	/**
	 * BDUF nop
	 */
	public EzDir parent() {
		return null;
	}

	public void save(String text) {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()));
			writer.write(text);
		} catch (Exception e) {
			throw new RuntimeException("Unable to save to file.", e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (Exception ex) {}
			}
		}
	}

	public String text() {
		try {
			long length = file.length();
			if (length > Integer.MAX_VALUE) throw new RuntimeException("Need to handle larger files");

			StringBuffer content = new StringBuffer((int) length);
			FileReader reader = new FileReader(file);
			int chr = reader.read();
			while (chr != -1) {
				content.append((char) chr);
				chr = reader.read();
			}
			return content.toString();
		} catch (Exception e) {
			throw new RuntimeException("Failed to read text from file", e);
		}
	}

	/**
	 * BDUF nop
	 */
	public void touch() {}
}
