package org.opensails.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.opensails.viento.Binding;
import org.opensails.viento.MethodMissing;
import org.opensails.viento.VientoTemplate;

public class VientoTask extends Task {
	private File file;

	@Override
	public void execute() throws BuildException {
		try {
			VientoTemplate template = new VientoTemplate(new FileInputStream(file));
			save(template.render(createBinding()));
		} catch (FileNotFoundException e) {
			throw new BuildException("File does not exist", e);
		}
	}

	public void setFile(File file) {
		this.file = file;
	}

	private Binding createBinding() {
		Binding binding = new Binding();
		binding.mixin(new NodeMixin());
		return binding;
	}

	private void save(String content) {
		Writer writer = null;
		try {
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

	public class Node implements MethodMissing {
		private final String name;

		public Node(String name) {
			this.name = name;
		}

		public Object methodMissing(String methodName, Object[] args) {
			return new Node(String.format("%s.%s", name, methodName));
		}

		@Override
		public String toString() {
			return getProject().getProperty(name);
		}
	}

	public class NodeMixin implements MethodMissing {
		public Object methodMissing(String methodName, Object[] args) {
			return new Node(methodName);
		}
	}
}
