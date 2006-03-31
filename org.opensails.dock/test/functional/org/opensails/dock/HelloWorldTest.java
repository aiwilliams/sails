package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.HomeController;
import org.opensails.sails.tester.Page;

public class HelloWorldTest extends TestCase {
	public void testHello() {
		DockTester hello = new DockTester();
		Page page = hello.get();
		page.assertContains("Hello, World!");
	}

	public void testHelloParameterized() throws Exception {
		DockTester hello = new DockTester();
		Page page = hello.get(HomeController.class, "index", "bob");
		page.assertContains("Hello, bob!");
	}
}
