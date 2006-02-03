package org.opensails.sails.form.html;

import junit.framework.TestCase;

public class PasswordTest extends TestCase {
	public void testToString() {
		Password password = new Password("name");
		assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"\" />", password.toString());

		password = new Password("name").value("hellomate");
		assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"hellomate\" />", password.toString());

		password.value("hellomateagain");
		assertEquals("<input id=\"name\" name=\"name\" type=\"password\" value=\"hellomateagain\" />", password.toString());

		password = new Password("name", "id");
		assertEquals("<input id=\"id\" name=\"name\" type=\"password\" value=\"\" />", password.toString());
	}

}

