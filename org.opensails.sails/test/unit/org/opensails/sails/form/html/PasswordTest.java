package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class PasswordTest extends WidgetTestCase {
	public void testRender() {
		assertRender("<input name=\"name\" type=\"password\" value=\"\" />", "$password(name)");
		assertRender("<input name=\"name\" type=\"password\" value=\"hellomate\" />", "$password(name).value(hellomate)");
		assertRender("<input name=\"name\" type=\"password\" value=\"hellomateagain\" />", "$password(name).value(hellomate).value(hellomateagain)");
		assertRender("<input id=\"id\" name=\"name\" type=\"password\" value=\"\" />", "$password(name, id)");
	}
}
