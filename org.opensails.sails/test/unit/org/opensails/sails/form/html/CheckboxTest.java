/*
 * Created on May 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import junit.framework.TestCase;

/**
 * Checkboxes are rendered with an accompanying hidden field when there is only
 * one for a name. If there are multiples, then it is assumed that they
 * represent multiple selections for a name, and where there are none, there are
 * none. Actually, that could be a problem: How does the framework know that you
 * meant 'nothing selected' and not 'this wasn't exposed in the UI'.
 * 
 * @author aiwilliams
 */
public class CheckboxTest extends TestCase {
	public void testBoolean() throws Exception {
		Checkbox checkbox = new Checkbox("name");
		checkbox.value("hello, mate");
		checkbox.getBoolean();
		assertEquals("<input id=\"name-hello_mate\" name=\"name\" type=\"checkbox\" value=\"hello, mate\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"false\" />", checkbox.toString());
	}
	
	public void testId() {
		Checkbox checkbox = new Checkbox("name").id("someId");
		assertEquals("<input id=\"someId\" name=\"name\" type=\"checkbox\" value=\"true\" />", checkbox.toString());
	}

	public void testChecked() throws Exception {
		Checkbox checkbox = new Checkbox("name", "custom");
		checkbox.checked();
		assertEquals("<input id=\"name-custom\" name=\"name\" type=\"checkbox\" value=\"custom\" checked=\"checked\" />", checkbox.toString());
		checkbox.checked(false);
		assertEquals("<input id=\"name-custom\" name=\"name\" type=\"checkbox\" value=\"custom\" />", checkbox.toString());
	}

	public void testLabel() throws Exception {
		Checkbox checkbox = new Checkbox("name.besure");
		// setting the label implies that an id is required and desired
		checkbox.label("hello");
		String id = FormElement.idForNameAndValue("name.besure", "true");
		assertEquals("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"true\" /><label for=\"" + id + "\">hello</label>", checkbox.toString());

		checkbox = new Checkbox("name.besure").label("hello").value("optionOne");
		id = FormElement.idForNameAndValue("name.besure", "optionOne");
		assertEquals("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"optionOne\" /><label for=\"" + id + "\">hello</label>", checkbox.toString());

		checkbox = new Checkbox("name.besure").label("hello").getBoolean();
		// TODO should this just be idForName since for boolean checkboxes the value is always true?
		id = FormElement.idForNameAndValue("name.besure", "true");
		assertEquals("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"true\" /><label for=\"" + id
				+ "\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"false\" />", checkbox.toString());
	}

	public void testToString() throws Exception {
		Checkbox checkbox = new Checkbox("name");
		assertEquals("<input id=\"name-true\" name=\"name\" type=\"checkbox\" value=\"true\" />", checkbox.toString());

		checkbox = new Checkbox("name");
		checkbox.value("myvalue");
		assertEquals("<input id=\"name-myvalue\" name=\"name\" type=\"checkbox\" value=\"myvalue\" />", checkbox.toString());

		checkbox = new Checkbox("name");
		checkbox.value(false);
		assertEquals("<input id=\"name-false\" name=\"name\" type=\"checkbox\" value=\"false\" />", checkbox.toString());
	}
}
