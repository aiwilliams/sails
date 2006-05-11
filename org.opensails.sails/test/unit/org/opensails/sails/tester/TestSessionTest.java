package org.opensails.sails.tester;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.browser.TesterSession;

public class TestSessionTest extends TestCase {
	public void testAssertContains() {
		TesterSession testSession = new TesterSession(new SailsFunctionalTester());
		try {
			testSession.assertContains("heloo");
			throw new RuntimeException("session doesn't have entry for key");
		} catch (AssertionFailedError expected) {}

		testSession.setAttribute("heloo", "goodbye");
		testSession.assertContains("heloo");
	}

	public void testAssertExcludes() {
		TesterSession testSession = new TesterSession(new SailsFunctionalTester());
		testSession.assertExcludes("heloo");

		testSession = new TesterSession(new SailsFunctionalTester());
		testSession.setAttribute("heloo", "goodbye");
		try {
			testSession.assertExcludes("heloo");
			throw new RuntimeException("session has entry for key");
		} catch (AssertionFailedError expected) {}
	}

	public void testNullSession() {
		TesterSession testSession = new TesterSession(new SailsFunctionalTester());
		try {
			testSession.assertContains("xxx");
			throw new RuntimeException("no session");
		} catch (AssertionFailedError expected) {}
	}
}
