package org.opensails.dock;

import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;

public class DockTester extends Browser {
	public DockTester() {
		super(new SailsTestApplication(DockConfigurator.class));
	}
}
