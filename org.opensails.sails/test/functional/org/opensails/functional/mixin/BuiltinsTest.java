package org.opensails.functional.mixin;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.ScriptList;
import org.opensails.sails.tester.browser.TestGetEvent;

public class BuiltinsTest extends TestCase {
	public void testForm() {
		SailsFunctionalTester t = new SailsFunctionalTester();
		TestGetEvent event = t.createVirtualEvent("mc/ma", "$form.start");
		Page page = t.get(event);
		page.assertContains("method=\"post\"");
		page.assertMatches("action=\"http://.*?/mc/ma\"");
	}

	public void testForm_IdGeneration() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		
		StringBuilder template = new StringBuilder();
		template.append("$form.checkbox('the.name')");
		template.append("$form.checkbox('the.name')");
		template.append("$form.radio('the.name', 'thevalue')");
		template.append("$form.checkbox('the.name', 'thevalue')");
		template.append("$form.text('the.name').value('stuff')");

		String[] expected = new String[] {
			"id=\"the_name\"",
			"id=\"the_name-1\"",
			"id=\"the_name-thevalue\"",
			"id=\"the_name-thevalue-1\"",
			"id=\"the_name-2\""
		};

		Page page = t.getTemplated(template);
		page.assertContainsInOrder(expected);
	}

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
