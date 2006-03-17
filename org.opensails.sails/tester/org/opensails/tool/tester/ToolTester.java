package org.opensails.tool.tester;

import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;
import org.opensails.sails.tester.browser.TestGetEvent;

/**
 * Provides an environment to test tools.
 * <p>
 * The challenge in testing tools is this: how do you provide for the fact that
 * tools are created within the context of a running application?
 * 
 * @author aiwilliams
 */
public class ToolTester {
	private Browser browser;

	public ToolTester() {
		this(BaseConfigurator.class);
	}

	public ToolTester(Class<? extends BaseConfigurator> configurator) {
		browser = new SailsTestApplication(configurator).openBrowser();
	}

	public void assertRender(String expected, String actualUsage) {
		TestGetEvent event = browser.createVirtualEvent("tool/test", actualUsage);
		Page page = browser.get(event);
		page.assertContains(expected);
	}

	public void assertRenderMatches(String pattern, String actualUsage) {
		TestGetEvent event = browser.createVirtualEvent("tool/test", actualUsage);
		Page page = browser.get(event);
		page.assertMatches(pattern);
	}

}
