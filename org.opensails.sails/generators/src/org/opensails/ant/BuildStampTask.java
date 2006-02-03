package org.opensails.ant;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.tools.ant.*;

public class BuildStampTask extends Task {
	private File file;
	private String javaUtilDateFormat;

	public BuildStampTask() {
		file = new File("build.stamp");
		javaUtilDateFormat = "yyyyMMdd_HHmm";
	}

	@Override
	public void execute() throws BuildException {
		if (file == null) throw new BuildException("Must specify file (build timestamp)");
		save(getFormattedDate());
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFormat(String javaUtilDateFormat) {
		this.javaUtilDateFormat = javaUtilDateFormat;
	}

	private String getFormattedDate() {
		SimpleDateFormat format = new SimpleDateFormat(javaUtilDateFormat);
		return format.format(new Date());
	}

	private void save(String content) {
		Writer writer = null;
		try {
			file.createNewFile();
			writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write(content);
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
}
