package org.opensails.generators.builtin;

import org.opensails.ezfile.EzPath;

public class DefaultProjectDescriptor {
	protected String rootPackage;
	protected final String projectName;

	public DefaultProjectDescriptor(String projectName) {
		this.projectName = projectName;
	}

	public String getConfiguratorClass() {
		return getRootPackage() + ".Configurator";
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

	public String getRootPackage() {
		return rootPackage;
	}

	public String getRootPackagePath() {
		return EzPath.join("src", getRootPackage().replace('.', '/'));
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

	public void setRootPackage(String dotSeparated) {
		rootPackage = dotSeparated;
	}
}
