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
	public void testId() {
		assertRender("<input id=\"someId\" name=\"name\" type=\"checkbox\" value=\"1\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />", "$checkbox(name).id(someId)");
	}

	public void testLabel() throws Exception {
		// setting the label implies that an id is required and desired
		String id = FormElement.idForName("name.besure");
		assertRender("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"1\" /><label for=\"" + id
				+ "\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"0\" />", "$checkbox('name.besure').label(hello)");

		id = FormElement.idForName("name.besure");
		assertRender("<input id=\"" + id + "\" name=\"name.besure\" type=\"checkbox\" value=\"checkedValue\" /><label for=\"" + id
				+ "\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"uncheckedValue\" />", "$checkbox('name.besure', checkedValue, uncheckedValue).label(hello)");
	}

	public void testRender() throws Exception {
		assertRender("<input id=\"name\" name=\"name\" type=\"checkbox\" value=\"1\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />", "$checkbox(name)");
		assertRender("<input id=\"name\" name=\"name\" type=\"checkbox\" value=\"myvalue\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"yourvalue\" />", "$checkbox(name, myvalue, yourvalue)");
	}

	public void testRender_Checked() throws Exception {
		String expected = "<input id=\"name\" name=\"name\" type=\"checkbox\" value=\"1\" checked=\"checked\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />";
		assertRender(expected, "$checkbox(name).checked");

		expected = "<input id=\"name\" name=\"name\" type=\"checkbox\" value=\"1\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />";
		assertRender(expected, "$checkbox(name).checked(false)");
	}
}
