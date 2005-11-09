package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class AjaxController extends BaseController {
	public void exception() {
		throw new RuntimeException("Simulated exception in ajax call");
	}
}
