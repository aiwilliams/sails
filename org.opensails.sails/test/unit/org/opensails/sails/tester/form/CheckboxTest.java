package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.tester.html.MultipleElementOccurrencesException;

public class CheckboxTest extends TestCase {
	public void testChecked() throws Exception {
		Checkbox element = new Checkbox("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" /></form>", "my.checkbox");
		element.assertChecked(false).assertLabeled("hehe");

		element = new Checkbox("<form><input name='my.checkbox' type=\"checkbox\" checked=\"true\" /></form>", "my.checkbox");
		element.assertChecked(true);
		try {
			element.assertChecked(false);
			throw new RuntimeException("box is checked");
		} catch (AssertionFailedError expected) {}

		// assert for case where there is a hidden meta field for false
		element = new Checkbox("<form><input name='my.checkbox' type=\"checkbox\" /><input name=\"form.meta.cb.my.checkbox\" type=\"hidden\" value=\"false\" /></form>", "my.checkbox");
		element.assertChecked(false);
		try {
			element.assertChecked(true);
			throw new RuntimeException("box is not checked");
		} catch (AssertionFailedError expected) {}
	}

	public void testMultiCheckbox() throws Exception {
		try {
			new Checkbox("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" checked=\"true\" /><input name='my.checkbox' type=\"checkbox\" id=\"it_1\" value=\"das other value\" /></form>", "my.checkbox");
			throw new RuntimeException("more than one for name, don't know which to use");
		} catch (MultipleElementOccurrencesException expected) {}

		Checkbox element = new Checkbox("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" checked=\"true\" /><input name='my.checkbox' type=\"checkbox\" id=\"it_1\" value=\"das other value\" /></form>", "my.checkbox", "das other value");
		element.assertUnchecked();
		try {
			element.assertValue("das value");
			throw new RuntimeException("specific checkbox");
		} catch (AssertionFailedError expected) {}
		element.assertValue("das other value");
	}

	public void testSanity() throws Exception {
		new Checkbox("perty\">textareaValue</textarea><input id=\"model_checkboxProperty\" name=\"model.checkboxProperty\" type=\"checkbox\" value=\"1\" /><label for=\"model_checkboxProperty\">Checkbox</label><input name=\"form.meta.cb.model.checkboxProperty\" type=\"hidden\" value=\"0\" /><input id=\"model_checkboxList", "model.checkboxProperty");
	}
}
