package org.opensails.sails.tester.form;

public class TesterRadioTest extends TesterElementTestCase {
	public void testChecked() throws Exception {
		String html = "<form><label for=\"it\">hehe</label><input name='my.element' type=\"radio\" id=\"it\" value=\"das value\" /></form>";
		TesterRadio element = new TesterRadio(elementForHtml(html), "my.element", "das value");
		element.assertChecked(false).assertLabeled("hehe");

		html = "<form><input name='my.element' type=\"radio\" checked=\"true\" value=\"das value\" /></form>";
		element = new TesterRadio(elementForHtml(html), "my.element", "das value");
		element.assertChecked(true);
		try {
			element.assertChecked(false);
			throw new RuntimeException("checked");
		} catch (TesterElementError expected) {}
	}
}
