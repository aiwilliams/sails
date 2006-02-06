package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class FlashController extends BaseController {
	public void index() {
		flash("hello", "world");
		event.getSession(true); // session should be created, flash stored
	}

	public void display() {
		renderString(flash("hello"));
	}
}
