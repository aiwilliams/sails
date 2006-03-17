package org.opensails.sails.form.html;

import org.opensails.widget.tester.WidgetTestCase;

public class LabelTest extends WidgetTestCase {
	public void testRender() throws Exception {
		assertRender("<label for=\"myid\"></label>", "$label($text(name, myid))");
		assertRender("<label for=\"myid\">Hello</label>", "$label($text(name, myid)).text(Hello)");
	}
}
