package org.opensails.sails.controllers;

import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.spyglass.SpyGlass;

public class ErrorController extends BaseController {
	public void exception(ExceptionEvent event) {
		expose("exception", event.getException());
		expose("packageRoot", SpyGlass.getPackage(event.getContainer().instance(IEventConfigurator.class)));
	}
}
