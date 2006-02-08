package org.opensails.functional.controllers;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.controller.oem.BaseController;

public class EventTestController extends BaseController {
	// These will be set from the form fields, if they are available
	public String stringField;
	public int intField;
	
	// This will not be set, even if in form fields
	protected float floatField;
	
	public IActionResult actionReturnsResult() {
		return new StringActionResult(event, "string rendered");
	}

	public void differentTemplate() {
		renderTemplate("different/template");
	}

	public void parameterGet(boolean one, String two) {
		expose("argOne", one);
		expose("argTwo", two);
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
