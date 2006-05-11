package org.opensails.sails.tester.form;

import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.ValidationContext;
import org.opensails.sails.model.ModelContext;
import org.opensails.sails.tester.html.NoSuchElementError;

public class TesterFormTest extends TesterElementTestCase {

	public void testConstructor() throws Exception {
		TesterForm form = new TesterForm(elementForHtml("<html><form name=\"my form\">stuff</form></html>"), "my form");
		assertEquals("<form name=\"my form\">stuff</form>", form.source());

		form = new TesterForm(elementForHtml("<html><form name=\"my form\" /></html>"), "my form", new HtmlForm(null, null, null, null) {
			@Override
			public boolean isValid() {
				return true;
			}
		});
		form.assertValid();

		form = new TesterForm(elementForHtml("<html><form name=\"my form\" /></html>"), "my form", new HtmlForm(new ValidationContext(new ModelContext(), null), null, null, null) {
			@Override
			public boolean isValid() {
				return false;
			}
		});
		try {
			form.assertValid();
			throw new RuntimeException("If HtmlForm is invalid, fail");
		} catch (TesterElementError expected) {}
	}

	/**
	 * Looks for submit by name first, then by value. Buttons can exist without
	 * a name, which will cause the browser to not submit that button's name and
	 * value.
	 * 
	 * @throws Exception
	 */
	public void testSubmit() throws Exception {
		TesterForm form = new TesterForm(elementForHtml("<form name=\"my form\"><input  name=\"the.name\" type=\"submit\"   value=\"valueValue\"    /><input type=\"submit\" value=\"theValue\" /></form>"), "my form");
		form.submit("the.name"); // look by name first
		form.submit("theValue"); // then by value, if no name
		try {
			form.submit("someOther");
			throw new RuntimeException("Assertion should fail");
		} catch (NoSuchElementError expected) {}
	}

	public void testText() throws Exception {
		TesterForm form = new TesterForm(elementForHtml("<html><form name=\"my form\"><input type=\"text\" name=\"the.name\"  value=\"valueValue\"    /></form></html>"), "my form");
		form.text("the.name");
		try {
			form.text("the.wrong.name");
			throw new RuntimeException("Assertion should fail");
		} catch (NoSuchElementError expected) {}
	}

}
