package org.opensails.sails.fixture.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class HomeController extends BaseController {
	public void index() {
		expose("firstName", field("firstName"));
	}
}
