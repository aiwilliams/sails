package org.opensails.generators.builtin;

import org.opensails.generators.IGenerator;
import org.opensails.generators.IGeneratorArguments;
import org.opensails.generators.IGeneratorContext;
import org.opensails.generators.IGeneratorTarget;
import org.opensails.viento.Binding;

public class ProjectGenerator implements IGenerator {
	protected final DefaultProjectDescriptor descriptor;

	public ProjectGenerator(DefaultProjectDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public ProjectGenerator(IGeneratorArguments arg) {
		descriptor = new DefaultProjectDescriptor(arg.named("name"));
		descriptor.setRootPackage(arg.named("package"));
	}

	public void execute(IGeneratorTarget target, IGeneratorContext context) {
		Binding binding = new Binding();
		binding.put("application", descriptor);

		target.cd(descriptor.getProjectName());
		target.mkdir(descriptor.getViewsPath());
		target.mkdir(descriptor.getStylesPath());
		target.mkdir(descriptor.getScriptsPath());
		target.mkdir(descriptor.getImagesPath());
		target.mkdir(descriptor.getControllersPackagePath());
		target.mkdir(descriptor.getComponentsPackagePath());
		target.mkdir(descriptor.getMixinsPackagePath());
		target.mkdir(descriptor.getAdaptersPackagePath());

		target.save(context.render("web.xml", binding), "app/WEB-INF/web.xml");
		target.save(context.render("Configurator.java", binding), descriptor.getRootPackagePath(), "Configurator.java");
	}
}
