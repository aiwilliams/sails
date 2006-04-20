package org.opensails.mixin.tester;

import org.opensails.sails.configurator.ApplicationPackage;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.util.BleedingEdgeException;
import org.opensails.spyglass.SpyGlass;

public class MixinTester {

	public MixinTester(Class<? extends SailsConfigurator> configuratorClass) {
		SailsConfigurator configurator = SpyGlass.instantiate(configuratorClass);
		IPackageDescriptor descriptor = configurator.createPackageDescriptor();
		for (ApplicationPackage applicationPackage : descriptor.getMixinPackages()) {
			throw new BleedingEdgeException("Need a MixinResolver that is an IMethodResolver");
			// new
			// PackageClassResolver<Object>(applicationPackage.getPackageName(),
			// "Mixin");

		}
	}

	public void expose(String key, Object object) {
		throw new BleedingEdgeException("implement");
	}

	public void assertEquals(String expected, String vientoTemplate) {
		throw new BleedingEdgeException("implement");
	}

}
