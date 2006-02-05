package org.opensails.sails.tester.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class TesterController extends BaseController {
	public void redirect() {
		redirectAction(TesterController.class, "otherAction");
	}
}
