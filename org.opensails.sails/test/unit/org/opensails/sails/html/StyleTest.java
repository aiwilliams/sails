package org.opensails.sails.html;

import org.opensails.widget.tester.WidgetTestCase;

public class StyleTest extends WidgetTestCase {
	public void testInline() throws Exception {
		assertRender("<style type=\"text/css\">\nstuff\n</style>", "$style [[stuff]]");
	}

	public void testLink() throws Exception {
		assertRender("<link href=\"http://blah\" type=\"text/css\" rel=\"stylesheet\" />", "$style('http://blah')");
	}
}
