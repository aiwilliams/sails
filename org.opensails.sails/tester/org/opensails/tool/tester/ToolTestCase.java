package org.opensails.tool.tester;

import junit.framework.TestCase;

public abstract class ToolTestCase extends TestCase {
	protected ToolTester t;

	public ToolTestCase() {
		t = new ToolTester();
	}

	public void assertRender(String pattern, String actualUsage) {
		t.assertRender(pattern, actualUsage);
	}

	public void assertRenderMatches(String pattern, String actualUsage) {
		t.assertRenderMatches(pattern, actualUsage);
	}
}
