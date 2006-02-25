package org.opensails.functional.partial;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;

public class PartialTests extends TestCase {
	public void testBasicPartial() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("partialTest", "index");
		page.assertContains("You see the partial?");
	}

	/**
	 * Fails until dotjerky makes Viento do binding scopes a leetle beet better.
	 */
	public void testInLoop() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		Page page = t.get("partialTest", "loop");
		page.assertContains("123");
	}
}
