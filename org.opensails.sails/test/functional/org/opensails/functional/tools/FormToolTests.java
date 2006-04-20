package org.opensails.functional.tools;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.TestGetEvent;

public class FormToolTests extends TestCase {
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

		String[] expected = new String[] { "id=\"the_name\"", "id=\"the_name-1\"", "id=\"the_name-thevalue\"", "id=\"the_name-thevalue-1\"", "id=\"the_name-2\"" };

		Page page = t.getTemplated(template);
		page.assertContainsInOrder(expected);
	}
}
