package org.opensails.generators.oem;

import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzFileSystem;
import org.opensails.generators.IGeneratorContext;
import org.opensails.viento.Binding;
import org.opensails.viento.VientoTemplate;

public class GeneratorContext implements IGeneratorContext {
	private final EzFileSystem root;

	public GeneratorContext(EzFileSystem generatorFileSystem) {
		this.root = generatorFileSystem;
	}

	public EzFile template(String path) {
		return root.file("templates/" + path);
	}

	public String render(String path, Binding binding) {
		return new VientoTemplate(template(path).text()).render(binding);
	}
}
