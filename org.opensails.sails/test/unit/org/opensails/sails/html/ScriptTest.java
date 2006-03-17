package org.opensails.sails.html;

import org.opensails.widget.tester.WidgetTestCase;

/**
 * Outputs the closing tag all the time, due to browsers practically requiring
 * it to work correctly.
 */
public class ScriptTest extends WidgetTestCase {
	public void testRender_InLine() {
		assertRender("<script type=\"text/javascript\">\nstuff\n</script>", "$script [[stuff]]");
	}

	public void testRender_NoModifications() {
		assertRender("<script type=\"text/javascript\"></script>", "$script");
	}

	public void testRender_Src() {
		assertRender("<script type=\"text/javascript\" src=\"http://blah\"></script>", "$script('http://blah')");
	}
}
