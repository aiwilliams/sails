package org.opensails.generators.builtins;

import junit.framework.TestCase;

import org.opensails.generator.tester.GeneratorTester;
import org.opensails.generators.builtin.ProjectGenerator;

public class ProjectGeneratorTests extends TestCase {
	public void testIt() {
		GeneratorTester<ProjectGenerator> tester = new GeneratorTester<ProjectGenerator>(ProjectGenerator.class);
		tester.generator.projectName = "MyProject";
		tester.generator.rootPackage = "com.abc";
		tester.execute();
		
		tester.assertCreatedIn("MyProject/src/com/abc", "controllers", "mixins");
		tester.assertCreatedIn("MyProject/app", "views", "styles", "scripts", "WEB-INF");
		
		tester.assertMatches("MyProject/app/WEB-INF/web.xml", "web-app.*?<display-name>MyProject</display-name>.*?com\\.abc\\.Configurator");
		tester.assertMatches("MyProject/src/com/abc/Configurator.java", "package com\\.abc;.*?class Configurator");
	}
}
