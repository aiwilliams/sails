package org.opensails.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class DotToSlashTask extends Task {
	private String name;
	private String value;

	@Override
	public void execute() throws BuildException {
		getProject().setProperty(name, value.replace('.', File.separatorChar));
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
