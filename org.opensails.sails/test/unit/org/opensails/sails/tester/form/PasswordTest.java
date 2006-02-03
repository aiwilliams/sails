package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class PasswordTest extends TestCase {
	public void testLabeled() throws Exception {
		Password password = new Password("<form><label for=\"it\">hehe</label><input name='my.text' type=\"password\" id=\"it\" value=\"das value\" /></form>", "my.text");
		password.labeled("hehe").value("das value");

		password = new Password("<form><label for=\"it\"></label><input name=\'my.text\' type=\"password\" id=\"it\" value=\"das value\" /></form>", "my.text");
		try {
			password.labeled("hehe");
			throw new RuntimeException("label has blank content");
		} catch (AssertionFailedError expected) {}
		try {
			password.value("hehe");
			throw new RuntimeException("value is different");
		} catch (AssertionFailedError expected) {}
	}
}

