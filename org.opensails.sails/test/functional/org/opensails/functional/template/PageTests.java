package org.opensails.functional.template;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.TemplateTestController;
import org.opensails.sails.tester.Page;

public class PageTests extends TestCase {
	public void testExposed() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(TemplateTestController.class);
		Page page = t.get("exposeMethod");
		page.exposed("inMethod").assertEquals("inMethodValue");
	}

	public void testCache_Fragment() {
		SailsFunctionalTester t = new SailsFunctionalTester(TemplateTestController.class);

		TemplateTestController.RENDERED_IN_TEMPLATE = "Value on first call";
		t.registerTemplate("templateTest/cacheFragment", "$cache.fragment [[$renderedInTemplate]]");
		t.get("cacheFragment").assertEquals("Value on first call");

		TemplateTestController.RENDERED_IN_TEMPLATE = "Value on second call";
		t.get("cacheFragment").assertEquals("Value on first call");

		t.get("expireFragment");
		t.get("cacheFragment").assertEquals("Value on second call");
	}
}
