package org.opensails.sails.tester.form;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.form.HtmlForm;

public class FormTest extends TestCase {
	public void testConstructor() throws Exception {
		Form form = new Form("<html> bal sd sd < sdls > L >> <form ds dd sj> fd  d <form  </form> sdf", null);
		assertEquals("<form ds dd sj> fd  d <form  </form>", form.getSource());

		form = new Form("page after processing", new HtmlForm(null, null, null, null) {
			@Override
			public boolean isValid() {
				return true;
			}
		});
		form.validated();

		form = new Form("page after processing", new HtmlForm(null, null, null, null) {
			@Override
			public String getErrorMessages() {
				return "messages";
			}

			@Override
			public boolean isValid() {
				return false;
			}
		});
		try {
			form.validated();
			throw new RuntimeException("If HtmlForm is invalid, fail");
		} catch (AssertionFailedError expected) {}
	}

	public void testSubmit() throws Exception {
		Form form = new Form("<form><input type=\"submit\"   value=\"valueValue\"    /></form>", null);
		form.submit("valueValue");
		try {
			form.submit("someOther");
			throw new RuntimeException("Assertion should fail");
		} catch (AssertionFailedError expected) {}
	}

	public void testText() throws Exception {
		Form form = new Form("<form><input type=\"text\" name=\"the.name\"  value=\"valueValue\"    /></form>", null);
		form.text("the.name");
		try {
			form.text("the.wrong.name");
			throw new RuntimeException("Assertion should fail");
		} catch (AssertionFailedError expected) {}
	}
}
