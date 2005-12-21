package org.opensails.functional.controllers;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.controller.oem.BaseController;

public class EventTestController extends BaseController {
	public IActionResult actionReturnsResult() {
		return new StringActionResult(event, "string rendered");
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
