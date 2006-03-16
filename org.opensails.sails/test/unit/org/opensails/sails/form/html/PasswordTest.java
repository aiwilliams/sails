package org.opensails.sails.form.html;

import junit.framework.TestCase;

import org.opensails.widget.tester.WidgetTester;

public class PasswordTest extends TestCase {
	public void testRender() {
		WidgetTester t = new WidgetTester();
		t.assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"\" />", "$password(name)");
		t.assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"hellomate\" />", "$password(name).value(hellomate)");
		t.assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"hellomateagain\" />", "$password(name).value(hellomate).value(hellomateagain)");
		t.assertEquals("<input id=\"id\" name=\"name\" type=\"password\" value=\"\" />", "$password(name, id)");
	}
}
