package org.opensails.sails.tester.form;

import org.opensails.sails.tester.html.MultipleOccurrencesException;

public class TesterCheckboxTest extends TesterElementTestCase {
	public void testChecked() throws Exception {
		String html = "<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" /></form>";
		TesterCheckbox element = new TesterCheckbox(elementForHtml(html), "my.checkbox");
		element.assertChecked(false).assertLabeled("hehe");

		html = "<form><input name='my.checkbox' type=\"checkbox\" checked=\"true\" /></form>";
		element = new TesterCheckbox(elementForHtml(html), "my.checkbox");
		element.assertChecked(true);
		try {
			element.assertChecked(false);
			throw new RuntimeException("box is checked");
		} catch (TesterElementError expected) {}

		// assert for case where there is a hidden meta field for false
		html = "<form><input name='my.checkbox' type=\"checkbox\" /><input name=\"form.meta.cb.my.checkbox\" type=\"hidden\" value=\"false\" /></form>";
		element = new TesterCheckbox(elementForHtml(html), "my.checkbox");
		element.assertChecked(false);
		try {
			element.assertChecked(true);
			throw new RuntimeException("box is not checked");
		} catch (TesterElementError expected) {}
	}

	public void testMultiCheckbox() throws Exception {
		try {
			new TesterCheckbox(elementForHtml("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" checked=\"true\" /><input name='my.checkbox' type=\"checkbox\" id=\"it_1\" value=\"das other value\" /></form>"), "my.checkbox");
			throw new RuntimeException("more than one for name, don't know which to use");
		} catch (MultipleOccurrencesException expected) {}

		TesterCheckbox element = new TesterCheckbox(elementForHtml("<form><label for=\"it\">hehe</label><input name='my.checkbox' type=\"checkbox\" id=\"it\" value=\"das value\" checked=\"true\" /><input name='my.checkbox' type=\"checkbox\" id=\"it_1\" value=\"das other value\" /></form>"), "my.checkbox", "das other value");
		element.assertUnchecked();
		try {
			element.assertValue("das value");
			throw new RuntimeException("specific checkbox");
		} catch (TesterElementError expected) {}
		element.assertValue("das other value");
	}
}
