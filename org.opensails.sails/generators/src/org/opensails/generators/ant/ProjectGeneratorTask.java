package org.opensails.generators.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.opensails.ezfile.RealEzFileSystem;
import org.opensails.generators.IGeneratorTarget;
import org.opensails.generators.builtin.DefaultProjectDescriptor;
import org.opensails.generators.builtin.ProjectGenerator;
import org.opensails.generators.oem.DiskGeneratorTarget;
import org.opensails.generators.oem.GeneratorContext;

public class ProjectGeneratorTask extends Task {
	protected IGeneratorTarget target;
	protected DefaultProjectDescriptor projectDescriptor;

	@Override
	public void init() throws BuildException {
		projectDescriptor = new DefaultProjectDescriptor();
	}

	/**
	 * Set the directory into which the generator should emit.
	 * 
	 * @param dir
	 */
	public void setDir(File dir) {
		target = new DiskGeneratorTarget(dir.getAbsolutePath());
	}

	/**
	 * Set the name of the project that will be generated.
	 * 
	 * @param name
	 */
	public void setProjectName(String name) {
		projectDescriptor.setProjectName(name);
	}

	/**
	 * Set the Java package that will be the root for the application. This
	 * should use dots.
	 * 
	 * @param dotPath
	 */
	public void setRootPackage(String dotPath) {
		projectDescriptor.setRootPackage(dotPath);
	}

	@Override
	public void execute() throws BuildException {
		new ProjectGenerator(projectDescriptor).execute(target, new GeneratorContext(new RealEzFileSystem("generators")));
	}
}
