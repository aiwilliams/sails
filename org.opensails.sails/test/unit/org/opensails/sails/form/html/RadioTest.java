package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class RadioTest extends WidgetTestCase {
	public void testRender() throws Exception {
		assertRender("<input name=\"name\" type=\"radio\" value=\"value\" />", "$radio(name, value)");
		assertRender("<input id=\"id\" name=\"name\" type=\"radio\" value=\"value\" />", "$radio(name, value, id)");
	}
}
