package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class SubmitImageTest extends WidgetTestCase {
	public void testRender() {
		assertRender("<input id=\"name\" name=\"name\" type=\"image\" value=\"\" src=\"src\" />", "$submitImage(name, src, null)");
	}
}
