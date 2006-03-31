package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class SelectTest extends WidgetTestCase {
	public void testRender() throws Exception {
		assertRender("<select name=\"name\" disabled=\"true\"></select>", "$select(name)");
		assertRender("<select id=\"id\" name=\"name\" disabled=\"true\"></select>", "$select(name).id(id)");
	}

	public void testRender_Attributes() {
		expose("model", new ListSelectModel(new Object[] { "one", "two" }));
		String expected = "<select id=\"id\" name=\"name\" myattr=\"myvalue\" myattrempty=\" \"><option value=\"" + SelectModel.NULL_OPTION_VALUE
				+ "\" selected=\"true\"></option><option value=\"one\">one</option><option value=\"two\">two</option></select>";
		// Viento seems to have the map reversed...
		assertRender(expected, "$select(name).model($model).attributes({myattrempty:' ', myattrnull:null, myattr:myvalue}).id(id)");
	}

	public void testRender_SelectModel() {
		expose("model", new ListSelectModel(new Object[] { "one", "two" }));
		String expected = "<select id=\"id\" name=\"name\"><option value=\"" + SelectModel.NULL_OPTION_VALUE
				+ "\" selected=\"true\"></option><option value=\"one\">one</option><option value=\"two\">two</option></select>";
		assertRender(expected, "$select(name).model($model).id(id)");

		expected = "<select id=\"id\" name=\"name\"><option value=\"one\" selected=\"true\">one</option><option value=\"two\">two</option></select>";
		assertRender(expected, "$select(name).model($model).id(id).selected(one)");
	}
}
