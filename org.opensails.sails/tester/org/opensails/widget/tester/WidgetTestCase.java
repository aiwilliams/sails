package org.opensails.widget.tester;

import junit.framework.TestCase;

public abstract class WidgetTestCase extends TestCase {
	protected WidgetTester t;

	public WidgetTestCase() {
		t = createTester();
	}

	public void assertRender(String expectedRender, String actualUsage) {
		t.assertRender(expectedRender, actualUsage);
	}

	protected WidgetTester createTester() {
		return new WidgetTester();
	}
	
	public void expose(String name, Object value) {
		t.expose(name, value);
	}
}
