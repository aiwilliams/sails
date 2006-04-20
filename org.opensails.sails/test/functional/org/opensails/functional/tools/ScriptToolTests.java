package org.opensails.functional.tools;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.ScriptList;

/**
 * These tests are for the ScriptTool being used within one event.
 * 
 * @author aiwilliams
 */
public class ScriptToolTests extends TestCase {

	public void testScript() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		ScriptList scripts = t.getTemplated(scriptTemplate()).scripts();
		scripts.assertContains("applicationScript", 0);
		scripts.assertContains("applicationScript.js", 3);
		scripts.assertContains("builtinScript", 0);
		scripts.assertContains("builtinScript.js", 3);
	}

	private String scriptTemplate() {
		StringBuilder template = new StringBuilder();
		template.append("$script('applicationScript')");
		template.append("$script('applicationScript.js')");
		template.append("$script.builtin('builtinScript')");
		template.append("$script.builtin('builtinScript.js')");
		template.append("$require.script('applicationScript')");
		template.append("$require.script('applicationScript.js')");
		template.append("$require.script.builtin('builtinScript')");
		template.append("$require.script.builtin('builtinScript.js')");
		template.append("$require.output");
		return template.toString();
	}
}
