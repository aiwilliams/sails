package org.opensails.generators.builtin;

import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzFileSystem;
import org.opensails.generators.IGeneratorContext;
import org.opensails.viento.Binding;

public class ProjectGenerator {
	public class Application {
		String name;
		Context context;

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

		public String getName() {
			return name;
		}

		public String getViewsPath() {
			return "/app/views";
		}
	}

	public class Context {
		public String getPath() {
			return "app";
		}
	}

	private final String projectName;

	public ProjectGenerator(String projectName) {
		this.projectName = projectName;
	}

	public void generate(EzFileSystem workspace, IGeneratorContext context) {
		Application application = new Application(projectName);
		Binding binding = new Binding();
		binding.put("application", application);

		workspace.mkdir(projectName + application.getViewsPath());
		workspace.mkdir(projectName + "/app/styles");
		workspace.mkdir(projectName + "/app/scripts");

		EzFile file = workspace.file(projectName + "/app/WEB-INF/web.xml");
		file.save(context.render("web.xml", binding));
	}
}
