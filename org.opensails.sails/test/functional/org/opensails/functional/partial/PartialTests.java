package org.opensails.functional.partial;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.PartialTestController;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.tester.Page;

public class PartialTests extends TestCase {
	public void testBasicPartial() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("partialTest", "index");
		page.assertContains("You see the partial?");
	}

	public void testInLoop() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("partialTest", "loop");
		page.assertContains("123");
	}

	public void testMore() throws Exception {
		SailsFunctionalTester tester = new SailsFunctionalTester();
		Page page = tester.get(PartialTestController.class, "renderPartial");
		page.assertContentType(ContentType.TEXT_HTML);
		page.assertContains("partial content");

		page = tester.get(PartialTestController.class, "renderOtherPartial");
		page.assertContentType(ContentType.TEXT_HTML);
		page.assertContains("other partial content");
	}
}
