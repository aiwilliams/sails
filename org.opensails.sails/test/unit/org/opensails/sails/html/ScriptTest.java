package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.template.BlockFixture;
import org.opensails.sails.url.UrlFixture;

public class ScriptTest extends TestCase {
	public void testToString_InLine() {
		Script script = new Script();
		script.inline(BlockFixture.create("stuff"));
		assertEquals("<script type=\"text/javascript\">\nstuff\n</script>", script.toString());
	}

	public void testToString_NoModifications() {
		Script script = new Script();
		assertEquals("<script type=\"text/javascript\" />", script.toString());
	}

	public void testToString_Src() {
		Script script = new Script();
		script.src(UrlFixture.create("http://blah"));
		assertEquals("<script type=\"text/javascript\" src=\"http://blah\" />", script.toString());
	}
}
