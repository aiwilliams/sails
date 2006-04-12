package org.opensails.functional;

import org.opensails.functional.components.BasicComponent;
import org.opensails.functional.controllers.EventTestController;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.FileSystemResourceResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class FunctionalTestConfigurator extends BaseConfigurator {
	@Override
	public void configure(ComponentResolver<IComponentImpl> componentResolver) {
		componentResolver.push(new PackageClassResolver<IComponentImpl>(SpyGlass.getPackage(BasicComponent.class), "Component"));
	};

	@Override
	public void configure(ControllerResolver controllerResolver) {
		controllerResolver.push(new ControllerPackage(EventTestController.class));
	}

	@Override
	public void configure(ResourceResolver resourceResolver) {
		resourceResolver.push(new FileSystemResourceResolver("test/views"));
		resourceResolver.push(new FileSystemResourceResolver("test"));
	}

	@Override
	protected void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(ShamApplicationStartable.class);
	}
}
