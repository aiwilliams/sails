package org.opensails.functional.controllers;

import org.opensails.functional.form.Model;
import org.opensails.sails.controller.oem.BaseController;

public class FormTestController extends BaseController {
	public String aFileField;

	public void explicitExpose() {
		exposeModel("model", new Model());
		exposeModel("other", null);
		exposeModel(null);
		renderTemplate("renderModel");
	}

	public void fileUpload() {
		renderString(aFileField);
	}

	public void implicitExpose() {
		exposeModel(new Model());
		renderTemplate("renderModel");
	}

	public void multipart() {
		file("not uploaded, but is multipart");
		renderString("made it here");
	}

	public void postThenRender(Model model) {
		updateModel(model);
		renderTemplate("renderModel");
	}

	public void referenceToMissingProperty() {
		exposeModel("model", new Object());
		renderTemplate("renderModel");
	}
	
	public void validationInAction() {
//		errors("model").addToBase("This should be a complete sentence.");
//		errors("model").add("someProperty", "this is a message");
//		renderString(errors("model"));
	}
}
