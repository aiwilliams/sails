package org.opensails.sails.controllers;

import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.event.oem.ExceptionEvent;

public class ErrorController extends BaseController {
	public void exception(ExceptionEvent event) {
		expose("exception", event.getException());
		expose("packageRoot", getEvent().getApplication().getPackageName());
	}
}
