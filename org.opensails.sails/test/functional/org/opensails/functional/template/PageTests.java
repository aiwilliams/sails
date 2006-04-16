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
}
