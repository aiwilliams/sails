package org.opensails.functional.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class ComponentTestController extends BaseController {
	public void basic() {
		expose("exposedInBoth", "both from controller");
		expose("exposedInController", "hello from controller");
	}
}
