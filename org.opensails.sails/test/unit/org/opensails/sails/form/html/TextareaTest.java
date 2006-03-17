package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class TextareaTest extends WidgetTestCase {
	public void testRender() throws Exception {
		assertRender("<textarea name=\"name\"></textarea>", "$textarea(name)");
		assertRender("<textarea name=\"name\">heloo</textarea>", "$textarea(name).value(heloo)");
		assertRender("<textarea id=\"id\" name=\"name\"></textarea>", "$textarea(name).id(id)");
	}
}
