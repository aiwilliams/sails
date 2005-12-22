package org.opensails.functional.controllers;

import org.opensails.functional.form.Model;
import org.opensails.sails.controller.oem.BaseController;

public class FormTestController extends BaseController {
	public void explicitExpose() {
		exposeModel("model", new Model());
		exposeModel("other", null);
		exposeModel(null);
		renderTemplate("renderModel");
	}

	public void implicitExpose() {
		exposeModel(new Model());
		renderTemplate("renderModel");
	}
}
