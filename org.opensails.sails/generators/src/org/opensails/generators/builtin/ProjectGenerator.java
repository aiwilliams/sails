package org.opensails.generators.builtin;

import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzFileSystem;
import org.opensails.generators.IGeneratorContext;
import org.opensails.viento.Binding;

public class ProjectGenerator {
	public class Application {
		String name;
		Context context;
		String rootPackage;

		public Application(String name) {
			this.name = name;
			this.context = new Context();
		}

		public String getConfiguratorClass() {
			return "junk";
		}

		public Context getContext() {
			return context;
		}

		public String getControllersPackagePath() {
			return "/src/" + rootPackage.replace('.', '/') + "/controllers";
		}

		public String getName() {
			return name;
		}

		public String getViewsPath() {
			return "/app/views";
		}

		public String getMixinsPackagePath() {
			return "/src/" + rootPackage.replace('.', '/') + "/mixins";
		}
	}

	public class Context {
		public String getPath() {
			return "app";
		}
	}

	private String projectName;
	private Application application;

	public ProjectGenerator() {
		application = new Application(projectName);
	}

	public void generate(EzFileSystem workspace, IGeneratorContext context) {
		Binding binding = new Binding();
		binding.put("application", application);

		workspace.mkdir(projectName + application.getViewsPath());
		workspace.mkdir(projectName + "/app/styles");
		workspace.mkdir(projectName + "/app/scripts");
		workspace.mkdir(projectName + application.getControllersPackagePath());
		workspace.mkdir(projectName + application.getMixinsPackagePath());

		EzFile file = workspace.file(projectName + "/app/WEB-INF/web.xml");
		file.save(context.render("web.xml", binding));
	}

	public void setProjectName(String name) {
		projectName = name;
	}

	public void setRootPackage(String fragmentDotSeparated) {
		application.rootPackage = fragmentDotSeparated;
	}
}
