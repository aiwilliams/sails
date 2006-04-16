package org.opensails.functional.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class TemplateTestController extends BaseController {
	public void exposeMethod() {
		expose("inMethod", "inMethodValue");
	}
}
