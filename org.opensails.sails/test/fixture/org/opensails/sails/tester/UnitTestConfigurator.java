package org.opensails.sails.tester;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.FileSystemResourceResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.ComponentPackage;

public class UnitTestConfigurator extends BaseConfigurator {
	@Override
	public void configure(ControllerResolver controllerResolver) {
		controllerResolver.push(new ComponentPackage<IControllerImpl>(ClassHelper.getPackage(HomeController.class), "Controller"));
	};

	@Override
	public void configure(ResourceResolver resourceResolver) {
		resourceResolver.push(new FileSystemResourceResolver("test/views"));
	}
}
