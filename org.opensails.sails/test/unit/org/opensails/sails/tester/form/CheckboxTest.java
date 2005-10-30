package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class CheckboxTest extends TestCase {
	public void testChecked() throws Exception {
		Checkbox element = new Checkbox("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" /></form>", "my.checkbox");
		element.checked(false).labeled("hehe");

		element = new Checkbox("<form><input name='my.checkbox' type=\"checkbox\" checked=\"true\" /></form>", "my.checkbox");
		element.checked(true);
		try {
			element.checked(false);
			throw new RuntimeException("box is checked");
		} catch (AssertionFailedError expected) {}
	}
}
