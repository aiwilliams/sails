package org.opensails.functional;

import org.opensails.functional.controllers.EventTestController;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.FileSystemResourceResolver;
import org.opensails.sails.oem.ResourceResolver;

public class FunctionalTestConfigurator extends BaseConfigurator {
	@Override
	public void configure(ControllerResolver controllerResolver) {
		controllerResolver.push(new ControllerPackage(EventTestController.class));
	};

	@Override
	public void configure(ResourceResolver resourceResolver) {
		resourceResolver.push(new FileSystemResourceResolver("test/views"));
	}
}
