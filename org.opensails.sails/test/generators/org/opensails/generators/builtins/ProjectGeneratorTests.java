package org.opensails.generators.builtins;

import junit.framework.TestCase;

import org.opensails.generator.tester.GeneratorTester;
import org.opensails.generators.MapGeneratorArguments;
import org.opensails.generators.builtin.ProjectGenerator;
import org.opensails.sails.oem.SailsDefaultsConfiguration;

public class ProjectGeneratorTests extends TestCase {
	public void testIt() {
		GeneratorTester<ProjectGenerator> tester = new GeneratorTester<ProjectGenerator>(ProjectGenerator.class);
		tester.execute(MapGeneratorArguments.create("name", "MyProject", "package", "com.abc"));

		tester.assertCreatedIn("MyProject/src/com/abc", "controllers", "mixins", "adapters", "components");
		tester.assertCreatedIn("MyProject/app", "views", SailsDefaultsConfiguration.STYLES, SailsDefaultsConfiguration.IMAGES, SailsDefaultsConfiguration.SCRIPTS, "WEB-INF");

		tester.assertMatches("MyProject/app/WEB-INF/web.xml", "web-app.*?<display-name>MyProject</display-name>.*?com\\.abc\\.Configurator");
		tester.assertMatches("MyProject/src/com/abc/Configurator.java", "package com\\.abc;.*?class Configurator");
	}
}
