package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.PartialsController;
import org.opensails.sails.http.ContentType;
import org.opensails.sails.tester.Page;

public class PartialsTest extends TestCase {
	public void testPartialResult() throws Exception {
		DockTester tester = new DockTester();
		Page page = tester.get(PartialsController.class, "renderPartial");
		page.assertContentType(ContentType.TEXT_HTML);
		page.assertContains("partial content");

		page = tester.get(PartialsController.class, "renderOtherPartial");
		page.assertContentType(ContentType.TEXT_HTML);
		page.assertContains("other partial content");
	}
}
