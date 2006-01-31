package org.opensails.generators.builtin;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.oem.SailsDefaultsConfiguration;

public class DefaultProjectDescriptor {
	protected String rootPackage;
	protected final String projectName;

	public DefaultProjectDescriptor(String projectName) {
		this.projectName = projectName;
	}

	public String getAdaptersPackagePath() {
		return EzPath.join(getRootPackagePath(), "adapters");
	}

	public String getComponentsPackagePath() {
		return EzPath.join(getRootPackagePath(), "components");
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

	public String getImagesPath() {
		return EzPath.join(getContextPath(), SailsDefaultsConfiguration.IMAGES);
	}

	public String getMixinsPackagePath() {
		return EzPath.join(getRootPackagePath(), "mixins");
	}

	public String getProjectName() {
		return projectName;
	}

	public String getRootPackage() {
		return rootPackage;
	}

	public String getRootPackagePath() {
		return EzPath.join("src", getRootPackage().replace('.', '/'));
	}

	public String getScriptsPath() {
		return EzPath.join(getContextPath(), SailsDefaultsConfiguration.SCRIPTS);
	}

	public String getStylesPath() {
		return EzPath.join(getContextPath(), SailsDefaultsConfiguration.STYLES);
	}

	public String getViewsPath() {
		return EzPath.join(getContextPath(), "views");
	}

	public void setRootPackage(String dotSeparated) {
		rootPackage = dotSeparated;
	}
}
