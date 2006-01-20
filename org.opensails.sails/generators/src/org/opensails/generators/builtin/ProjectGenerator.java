package org.opensails.generators.builtin;

import org.opensails.ezfile.EzPath;
import org.opensails.generators.IGenerator;
import org.opensails.generators.IGeneratorContext;
import org.opensails.generators.IGeneratorTarget;
import org.opensails.viento.Binding;

public class ProjectGenerator implements IGenerator {
	public String projectName;
	public String rootPackage;

	public ProjectGenerator() {}

	public void execute(IGeneratorTarget target, IGeneratorContext context) {
		Binding binding = new Binding();
		binding.put("application", this);

		target.cd(projectName);
		target.mkdir(getViewsPath());
		target.mkdir(getStylesPath());
		target.mkdir(getScriptsPath());
		target.mkdir(getControllersPackagePath());
		target.mkdir(getMixinsPackagePath());

		target.save(context.render("web.xml", binding), "app/WEB-INF/web.xml");
		target.save(context.render("Configurator.java", binding), getRootPackagePath(), "Configurator.java");
	}

	public String getConfiguratorClass() {
		return rootPackage + ".Configurator";
	}

	public String getContextPath() {
		return "app";
	}

	public String getControllersPackagePath() {
		return EzPath.join(getRootPackagePath(), "controllers");
	}

	public String getMixinsPackagePath() {
		return EzPath.join(getRootPackagePath(), "mixins");
	}

	public String getRootPackagePath() {
		return EzPath.join("src", rootPackage.replace('.', '/'));
	}

	public String getScriptsPath() {
		return "app/scripts";
	}

	public String getStylesPath() {
		return "app/styles";
	}

	public String getViewsPath() {
		return "app/views";
	}
}
