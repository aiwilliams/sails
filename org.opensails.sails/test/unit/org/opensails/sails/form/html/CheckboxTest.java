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
		assertRender("<input id=\"theid\" name=\"name.besure\" type=\"checkbox\" value=\"1\" /><label for=\"theid\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"0\" />", "$checkbox('name.besure').id('theid').label(hello)");
		assertRender("<input id=\"theid\" name=\"name.besure\" type=\"checkbox\" value=\"checkedValue\" /><label for=\"theid\">hello</label><input name=\"form.meta.cb.name.besure\" type=\"hidden\" value=\"uncheckedValue\" />", "$checkbox('name.besure', checkedValue, uncheckedValue).id('theid').label(hello)");
	}

	public void testRender() throws Exception {
		assertRender("<input name=\"name\" type=\"checkbox\" value=\"1\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />", "$checkbox(name)");
		assertRender("<input name=\"name\" type=\"checkbox\" value=\"myvalue\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"yourvalue\" />", "$checkbox(name, myvalue, yourvalue)");
	}

	public void testRender_Checked() throws Exception {
		String expected = "<input name=\"name\" type=\"checkbox\" value=\"1\" checked=\"checked\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />";
		assertRender(expected, "$checkbox(name).checked");

		expected = "<input name=\"name\" type=\"checkbox\" value=\"1\" /><input name=\"form.meta.cb.name\" type=\"hidden\" value=\"0\" />";
		assertRender(expected, "$checkbox(name).checked(false)");
	}
}
