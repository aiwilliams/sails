package org.opensails.sails.tester.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class TesterController extends BaseController {
	public void exception() {
		throw new RuntimeException("Simulated runtime exception");
	}
	
	public void redirect() {
		redirectAction(TesterController.class, "otherAction");
	}
	
	public void otherAction() {
		renderString("Made it!");
	}
}
