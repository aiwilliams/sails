package org.opensails.generators.builtins;

import junit.framework.TestCase;

import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.RealEzFileSystem;
import org.opensails.ezfile.memory.MemoryEzFileSystem;
import org.opensails.generators.GeneratorContext;
import org.opensails.generators.builtin.ProjectGenerator;
import org.opensails.sails.util.RegexHelper;

public class ProjectGeneratorTests extends TestCase {
	public void testIt() {
		ProjectGenerator generator = new ProjectGenerator("MyProject");
		MemoryEzFileSystem workspace = new MemoryEzFileSystem();
		generator.generate(workspace, new GeneratorContext(new RealEzFileSystem("generators/project")));
		assertTrue(workspace.dir("MyProject/app/views").exists());
		assertTrue(workspace.dir("MyProject/app/styles").exists());
		assertTrue(workspace.dir("MyProject/app/scripts").exists());

		EzFile file = workspace.file("MyProject/app/WEB-INF/web.xml");
		assertTrue(file.exists());
		assertTrue(RegexHelper.containsMatch(file.text(), "web-app.*?<display-name>MyProject</display-name>"));
	}
}
