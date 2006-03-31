package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.FlashController;
import org.opensails.sails.tester.Page;

public class FlashTest extends TestCase {
	public void testIt() throws Exception {
		DockTester app = new DockTester();
		Page page = app.get(FlashController.class);
		page.assertContains("world");
		page.flash().assertContains("hello", "world");
	}
}
