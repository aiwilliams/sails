package org.opensails.tool.tester;

import junit.framework.TestCase;

public abstract class ToolTestCase extends TestCase {
	protected ToolTester t;

	public ToolTestCase() {
		t = new ToolTester();
	}

	public void assertRender(String expected, String actualUsage) {
		t.assertRender(expected, actualUsage);
	}

	public void assertRenderMatches(String pattern, String actualUsage) {
		t.assertRenderMatches(pattern, actualUsage);
	}

	public void assertRenderMatches(String controllerAction, String pattern, String actualUsage) {
		t.assertRenderMatches(pattern, t.event(controllerAction, actualUsage));
	}
}
