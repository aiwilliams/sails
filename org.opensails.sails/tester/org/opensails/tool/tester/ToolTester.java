package org.opensails.tool.tester;

import junit.framework.AssertionFailedError;

import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;
import org.opensails.sails.tester.browser.TesterGetEvent;

/**
 * Provides an environment to test tools.
 * <p>
 * The challenge in testing tools is this: how do you provide for the fact that
 * tools are created within the context of a running application?
 * 
 * @author aiwilliams
 */
public class ToolTester {
	public Browser browser;

	public ToolTester() {
		this(SailsConfigurator.class);
	}

	public ToolTester(Class<? extends SailsConfigurator> configurator) {
		browser = new SailsTestApplication(configurator).openBrowser();
	}

	public void assertRender(String expected, String actualUsage) {
		assertRender(event(actualUsage), expected);
	}

	public void assertRender(TesterGetEvent event, String expected) throws AssertionFailedError {
		Page page = browser.get(event);
		page.assertContains(expected);
	}

	public void assertRenderMatches(String pattern, GetEvent event) {
		Page page = browser.get(event);
		page.assertMatches(pattern);
	}

	public void assertRenderMatches(String pattern, String actualUsage) {
		assertRenderMatches(pattern, event(actualUsage));
	}

	/**
	 * Causes an HttpSession to be created for the active Browser. Useful when
	 * testing your tool when there is a session.
	 */
	public void createSession() {
		browser.getSession(true);
	}

	/**
	 * Disables cookies in the active Browser. Useful when testing your tool
	 * when the browser doesn't allow cookies.
	 */
	public void disableCookies() {
		browser.disableCookies();
	}

	public TesterGetEvent event(String actualUsage) {
		return event("tool/test", actualUsage);
	}

	public TesterGetEvent event(String controllerAction, String actualUsage) {
		return browser.createVirtualEvent(controllerAction, actualUsage);
	}

}
