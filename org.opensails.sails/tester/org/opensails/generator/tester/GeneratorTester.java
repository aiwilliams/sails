package org.opensails.generator.tester;

import static junit.framework.Assert.assertTrue;

import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzPath;
import org.opensails.ezfile.RealEzFileSystem;
import org.opensails.generators.IGenerator;
import org.opensails.generators.IGeneratorTarget;
import org.opensails.generators.oem.GeneratorContext;
import org.opensails.generators.oem.MemoryGeneratorTarget;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.RegexHelper;

public class GeneratorTester<G extends IGenerator> {
	public G generator;

	private IGeneratorTarget target;
	private GeneratorContext context;

	public GeneratorTester(Class<G> generatorClass) {
		generator = ClassHelper.instantiate(generatorClass);
		target = new MemoryGeneratorTarget();
		context = new GeneratorContext(new RealEzFileSystem("generators/project"));
	}

	public void execute() {
		generator.execute(target, context);
		target.reset();
	}

	public void assertCreatedIn(String targetRelativeRoot, String... expected) {
		for (String expect : expected) {
			String expectedPath = EzPath.join(targetRelativeRoot, expect);
			EzFile file = target.file(expectedPath);
			assertTrue(String.format("Expected to have been created by the generator [%s]", file), file.exists());
		}
	}

	public void assertMatches(String targetRelativeFile, String regex) {
		EzFile file = target.file(targetRelativeFile);
		assertTrue(String.format("File does not exist to ensure match [%s]", file), file.exists());
		assertTrue(String.format("File content does not match [%s]", file), RegexHelper.containsMatch(file.text(), regex));
	}
}
