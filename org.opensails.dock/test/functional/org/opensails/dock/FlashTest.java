package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.FlashController;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.SailsTester;

public class FlashTest extends TestCase {
	public void testIt() throws Exception {
		SailsTester app = new DockTester();
		Page page = app.get(FlashController.class);
		page.assertContains("world");
		page.flash().assertContains("hello", "world");
	}
}
