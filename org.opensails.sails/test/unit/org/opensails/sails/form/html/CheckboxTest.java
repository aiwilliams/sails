package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

/**
 * Checkboxes are rendered with an accompanying hidden field when there is only
 * one for a name. If there are multiples, then it is assumed that they
 * represent multiple selections for a name, and where there are none, there are
 * none. Actually, that could be a problem: How does the framework know that you
 * meant 'nothing selected' and not 'this wasn't exposed in the UI'.
 * 
 * @author aiwilliams
 */
public class CheckboxTest extends WidgetTestCase {
	public void testBoolean() throws Exception {
		String expected = "<input id=\"name-hello_mate\" name=\"name\" type=\"checkbox\" value=\"hello, mate\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"false\" />";
		assertRender(expected, "$checkbox(name).value('hello, mate').boolean");
	}

	public void testChecked() throws Exception {
		String expected = "<input id=\"name-custom\" name=\"name\" type=\"checkbox\" value=\"custom\" checked=\"checked\" />";
		assertRender(expected, "$checkbox(name).value(custom).checked");

		expected = "<input id=\"name-custom\" name=\"name\" type=\"checkbox\" value=\"custom\" />";
		assertRender(expected, "$checkbox(name).value(custom).checked(false)");
	}

	public void testId() {
		assertRender("<input id=\"someId\" name=\"name\" type=\"checkbox\" value=\"true\" />", "$checkbox(name).id(someId)");
	}

	public void testLabel() throws Exception {
		// setting the label implies that an id is required and desired
		String id = FormElement.idForNameAndValue("name.besure", "true");
		assertRender("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"true\" /><label for=\"" + id + "\">hello</label>", "$checkbox('name.besure').label(hello)");

		id = FormElement.idForNameAndValue("name.besure", "optionOne");
		assertRender("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"optionOne\" /><label for=\"" + id + "\">hello</label>", "$checkbox('name.besure').label(hello).value(optionOne)");

		// TODO should this just be idForName since for boolean checkboxes the
		// value is always true?
		id = FormElement.idForNameAndValue("name.besure", "true");
		assertRender("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"true\" /><label for=\"" + id
				+ "\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"false\" />", "$checkbox('name.besure').label(hello).boolean");
	}

	public void testRender() throws Exception {
		assertRender("<input id=\"name-true\" name=\"name\" type=\"checkbox\" value=\"true\" />", "$checkbox(name)");
		assertRender("<input id=\"name-myvalue\" name=\"name\" type=\"checkbox\" value=\"myvalue\" />", "$checkbox(name).value(myvalue)");
		assertRender("<input id=\"name-false\" name=\"name\" type=\"checkbox\" value=\"false\" />", "$checkbox(name).value(false)");
	}
}
