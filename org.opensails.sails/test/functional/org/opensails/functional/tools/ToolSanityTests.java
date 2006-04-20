package org.opensails.functional.tools;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;

/**
 * This ensures that every available built-in tool can be found. Please test all
 * of the features of an individual tool separately.
 * 
 * @author aiwilliams
 */
public class ToolSanityTests extends TestCase {
	public void testEach() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		t.addTemplate("shared/template", "in here");
		t.getTemplated(createTemplate()).assertRenders();
	}

	private CharSequence createTemplate() {
		StringBuilder builder = new StringBuilder();
		builder.append("$script('test') $script.builtin('test')");
		builder.append("$require.script('test')");
		builder.append("$form.start");
		builder.append("$link.action('test')");
		builder.append("$urlfor.action('test')");
		builder.append("$render('shared/template')");
		builder.append("$flash(key)");
		builder.append("$style('test')");
		builder.append("$image('test')");
		return builder;
	}
}
