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

		// assert for case where there is a hidden meta field for false
		element = new Checkbox("<form><input name='my.checkbox' type=\"checkbox\" /><input name=\"form.meta.cb.my.checkbox\" type=\"hidden\" value=\"false\" /></form>", "my.checkbox");
		element.checked(false);
		try {
			element.checked(true);
			throw new RuntimeException("box is not checked");
		} catch (AssertionFailedError expected) {}
	}
}
