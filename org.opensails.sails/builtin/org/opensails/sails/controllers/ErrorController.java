package org.opensails.sails.controllers;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.event.ISailsEventConfigurator;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.util.ClassHelper;

public class ErrorController extends BaseController {

	public void exception(ExceptionEvent event) {
		expose("exception", event.getException());
		expose("packageRoot", ClassHelper.getPackage(event.getContainer().instance(ISailsEventConfigurator.class)));
	}
}
