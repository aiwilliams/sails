package org.opensails.sails.tools;

import org.opensails.tool.tester.ToolTestCase;

public class ScriptToolTest extends ToolTestCase {
	public void testBuiltin() throws Exception {
		assertRenderMatches("<script .*?src=\".*?common.*?myscript.js\"></script>", "$script.builtin(myscript)");
	}

	public void testScript() throws Exception {
		assertRenderMatches("<script .*?src=\".*?myscript.js\"></script>", "$script(myscript)");
	}

	public void testScript_WithBlock() throws Exception {
		assertRender("<script type=\"text/javascript\">\n some script \n</script>", "$script [[ some script ]]");
	}
}
