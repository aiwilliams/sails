package org.opensails.functional.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class TemplateTestController extends BaseController {
	// Used to test caching. See PageTests.java.
	public static String RENDERED_IN_TEMPLATE;

	public void expireFragment() {
		expireFragment("cacheFragment");
	}

	public void exposeMethod() {
		expose("inMethod", "inMethodValue");
	}

	public String renderedInTemplate() {
		return RENDERED_IN_TEMPLATE;
	}
}
