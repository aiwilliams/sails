package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class ExceptionController extends BaseController {

	public void index() {
		delegate();
	}
	
	protected void delegate() {
		throw new RuntimeException("Example exception");
	}
}
