package org.opensails.sails.template;

import junit.framework.TestCase;

import org.opensails.sails.html.Script;
import org.opensails.sails.template.Require;
import org.opensails.sails.url.UrlFixture;

public class RequireTest extends TestCase {
	public void testScript() throws Exception {
		Script scriptA1 = new Script(UrlFixture.create("a"));
		Script scriptB1 = new Script(UrlFixture.create("b"));

		Require require = new Require();
		require.script(scriptA1);
		require.script(scriptB1);
		require.script(new Script(UrlFixture.create("a")));

		assertEquals(String.format("%s\n%s", scriptA1, scriptB1), require.output());
	}
}
