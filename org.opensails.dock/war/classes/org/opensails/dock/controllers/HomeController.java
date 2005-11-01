package org.opensails.dock.controllers;

import org.opensails.dock.model.User;
import org.opensails.sails.controller.oem.BaseController;

public class HomeController extends BaseController {
	public void helloName(String who) {
		expose("who", who);
		renderTemplate("index");
	}

	public void helloUser(User who) {
		expose("who", who.getFirstName());
		renderTemplate("index");
	}

	public void index() {
		index("World");
	}

	public void index(String who) {
		expose("who", who);
	}
}
