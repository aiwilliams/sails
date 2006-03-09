package org.opensails.dock;

import junit.framework.TestCase;

import org.opensails.dock.controllers.HomeController;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.SailsTester;

public class HelloWorldTest extends TestCase {
	public void testHello() {
		SailsTester hello = new DockTester();
		Page page = hello.get();
		page.assertContains("Hello, World!");
	}

	public void testHelloParameterized() throws Exception {
		SailsTester hello = new DockTester();
		Page page = hello.get(HomeController.class, "index", "bob");
		page.assertContains("Hello, bob!");
	}
}
