package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class TextTest extends WidgetTestCase {
	public void testRender() {
		assertRender("<input id=\"name\" name=\"name\" type=\"text\" value=\"\" />", "$text(name)");
		assertRender("<input id=\"name\" name=\"name\" type=\"text\" value=\"hellomate\" />", "$text(name).value(hellomate)");
		assertRender("<input id=\"name\" name=\"name\" type=\"text\" value=\"hellomateagain\" />", "$text(name).value(hellomate).value(hellomateagain)");
		assertRender("<input id=\"id\" name=\"name\" type=\"text\" value=\"\" />", "$text(name).id(id)");
	}
}
