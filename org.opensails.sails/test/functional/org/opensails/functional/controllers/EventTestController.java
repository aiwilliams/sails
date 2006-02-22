package org.opensails.functional.controllers;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.controller.oem.BaseController;

public class EventTestController extends BaseController {
	// These will be set from the form fields, if they are available
	public ExampleEnum enumField;
	public int intField;
	public String stringField;

	// This will not be set, even if in form fields
	protected float floatField;

	public IActionResult actionReturnsResult() {
		return new StringActionResult(event, "string rendered");
	}

	public void differentTemplate() {
		renderTemplate("different/template");
	}

	public void enumParameter(ExampleEnum enumParameter) {
		renderString(String.format("Field: %s Param: %s", enumField, enumParameter));
	}

	public void parameterGet(boolean one, String two, ExampleEnum three) {
		expose("argOne", one);
		expose("argTwo", two);
		expose("argThree", three);
	}

	public void parameterPost(String one, int two) {
		expose("postedValue", field("postedField"));
		expose("argOne", one);
		expose("argTwo", two);
	}

	public void simpleGet() {}

	public void simplePost() {
		expose("postedValue", field("postedField"));
	}
}
