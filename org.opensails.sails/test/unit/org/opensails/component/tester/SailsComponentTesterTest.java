package org.opensails.component.tester;

import java.util.List;

import junit.framework.TestCase;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.configurator.ApplicationPackage;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.configurator.oem.DefaultPackageDescriptor;
import org.opensails.sails.template.Require;

public class SailsComponentTesterTest extends TestCase {
	public void testIt() throws Exception {
		SailsComponentTester t = new SailsComponentTester(Configurator.class);
		TestComponent<Component> c = t.component(Component.class);
		IEventContextContainer requestContainer = c.getRequestContainer();
		assertNotNull(requestContainer.getParent());
		Require registeredBefore = requestContainer.instance(Require.class);
		c.initialize();
		assertNotNull("Request remains connected to parent until result processing occurs", requestContainer.getParent());
		assertSame(registeredBefore, c.getRequestContainer().instance(Require.class));
		c.render();
		assertNull("Request container should be orphaned after render to avoid memory leak", requestContainer.getParent());
		assertSame(registeredBefore, c.getRequestContainer().instance(Require.class));
	}

	public static class Configurator extends SailsConfigurator {
		@Override
		public IPackageDescriptor createPackageDescriptor() {
			return new DefaultPackageDescriptor(getApplicationPackage()) {
				@Override
				protected void addComponentPackages(List<ApplicationPackage> componentPackages) {
					componentPackages.add(new ApplicationPackage(getApplicationPackage(), Component.class));
				}
			};
		}
	}
}
