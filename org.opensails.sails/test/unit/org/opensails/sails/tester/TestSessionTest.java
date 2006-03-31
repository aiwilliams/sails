package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.browser.TestSession;

public class TestSessionTest extends TestCase {
	public void testAssertContains() {
		TestSession testSession = new TestSession(new SailsFunctionalTester());
		try {
			testSession.assertContains("heloo");
			throw new RuntimeException("session doesn't have entry for key");
		} catch (AssertionFailedError expected) {}

		testSession.setAttribute("heloo", "goodbye");
		testSession.assertContains("heloo");
	}

	public void testAssertExcludes() {
		TestSession testSession = new TestSession(new SailsFunctionalTester());
		testSession.assertExcludes("heloo");

		testSession = new TestSession(new SailsFunctionalTester());
		testSession.setAttribute("heloo", "goodbye");
		try {
			testSession.assertExcludes("heloo");
			throw new RuntimeException("session has entry for key");
		} catch (AssertionFailedError expected) {}
	}

	public void testNullSession() {
		TestSession testSession = new TestSession(new SailsFunctionalTester());
		try {
			testSession.assertContains("xxx");
			throw new RuntimeException("no session");
		} catch (AssertionFailedError expected) {}
	}
}
