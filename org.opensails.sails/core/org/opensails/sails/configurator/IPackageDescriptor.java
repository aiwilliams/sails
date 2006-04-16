package org.opensails.sails.configurator;

import java.util.List;

public interface IPackageDescriptor {
	List<ApplicationPackage> getAdapterPackages();

	ApplicationPackage getApplicationPackage();

	List<ApplicationPackage> getComponentPackages();

	List<ApplicationPackage> getControllerPackages();

	List<ApplicationPackage> getMixinPackages();

	List<ApplicationPackage> getResultProcessorPackages();

	List<ApplicationPackage> getToolPackages();
}
