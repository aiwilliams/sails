package org.opensails.sails.configurator.oem;

import java.util.List;

import org.opensails.sails.configurator.ApplicationPackage;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.util.Quick;

public class DefaultPackageDescriptor implements IPackageDescriptor {
	protected ApplicationPackage applicationPackage;

	public DefaultPackageDescriptor(ApplicationPackage applicationRootPackage) {
		this.applicationPackage = applicationRootPackage;
	}

	public List<ApplicationPackage> getAdapterPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "adapters"));
		addAdapterPackages(list);
		return list;
	}

	public ApplicationPackage getApplicationPackage() {
		return applicationPackage;
	}

	public List<ApplicationPackage> getComponentPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "components"));
		addComponentPackages(list);
		return list;
	}

	public List<ApplicationPackage> getControllerPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "controllers"));
		addControllerPackages(list);
		return list;
	}

	public List<ApplicationPackage> getMixinPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "mixins"));
		addMixinPackages(list);
		return list;
	}

	public List<ApplicationPackage> getResultProcessorPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "processors"));
		addProcessorPackages(list);
		return list;
	}

	public List<ApplicationPackage> getToolPackages() {
		List<ApplicationPackage> list = Quick.list(new ApplicationPackage(applicationPackage, "tools"));
		addToolPackages(list);
		return list;
	}

	protected void addAdapterPackages(List<ApplicationPackage> adapterPackages) {}

	protected void addComponentPackages(List<ApplicationPackage> componentPackages) {}

	protected void addControllerPackages(List<ApplicationPackage> controllerPackages) {}

	protected void addMixinPackages(List<ApplicationPackage> mixinPackages) {}

	protected void addProcessorPackages(List<ApplicationPackage> processorPackages) {}

	protected void addToolPackages(List<ApplicationPackage> toolsPackages) {}

}
