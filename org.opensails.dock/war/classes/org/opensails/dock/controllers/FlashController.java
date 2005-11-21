package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class FlashController extends BaseController {
	public void index() {
		flash("hello", "world");
	}
}
