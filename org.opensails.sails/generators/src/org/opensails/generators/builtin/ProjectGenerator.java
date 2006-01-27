package org.opensails.generators.builtin;

import org.opensails.generators.IGenerator;
import org.opensails.generators.IGeneratorArguments;
import org.opensails.generators.IGeneratorContext;
import org.opensails.generators.IGeneratorTarget;
import org.opensails.viento.Binding;

public class ProjectGenerator implements IGenerator {
	public String projectName;
	public String rootPackage;
	protected final DefaultProjectDescriptor descriptor;

	public ProjectGenerator(IGeneratorArguments arg) {
		descriptor = new DefaultProjectDescriptor(arg.named("name"));
		descriptor.setRootPackage(arg.named("package"));
	}

	public ProjectGenerator(DefaultProjectDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public void execute(IGeneratorTarget target, IGeneratorContext context) {
		Binding binding = new Binding();
		binding.put("application", this);

		target.cd(projectName);
		target.mkdir(descriptor.getViewsPath());
		target.mkdir(descriptor.getStylesPath());
		target.mkdir(descriptor.getScriptsPath());
		target.mkdir(descriptor.getControllersPackagePath());
		target.mkdir(descriptor.getMixinsPackagePath());

		target.save(context.render("web.xml", binding), "app/WEB-INF/web.xml");
		target.save(context.render("Configurator.java", binding), descriptor.getRootPackagePath(), "Configurator.java");
	}
}
