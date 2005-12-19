package org.opensails.sails.template;

import junit.framework.TestCase;

import org.opensails.sails.html.Link;
import org.opensails.sails.html.Script;
import org.opensails.sails.url.UrlFixture;

public class RequireTest extends TestCase {
	public void testLink() throws Exception {
		Link linkA1 = new Link(UrlFixture.create("a"));
		Link linkA2 = new Link(UrlFixture.create("b"));

		Require require = new Require();
		require.link(linkA1);
		require.link(linkA2);
		require.link(new Link(UrlFixture.create("a")));

		assertEquals(String.format("%s\n%s", linkA1, linkA2), require.output().links());
	}

	public void testScript() throws Exception {
		Script scriptA1 = new Script(UrlFixture.create("a"));
		Script scriptB1 = new Script(UrlFixture.create("b"));

		Require require = new Require();
		require.script(scriptA1);
		require.script(scriptB1);
		// if 'a' is required again, make sure it doesn't lose it's position as
		// first requirement
		require.script(new Script(UrlFixture.create("a")));

		assertEquals(String.format("%s\n%s", scriptA1, scriptB1), require.output().scripts());
	}

	public void testScript_ModificationAfterAddition() {
		Script scriptA = new Script(UrlFixture.create("beforeMod"));
		Script scriptB = new Script(UrlFixture.create("beforeMod"));
		Require require = new Require();
		require.script(scriptA);
		require.script(scriptB);
		scriptA.src(UrlFixture.create("afterMod"));
		String scripts = require.output().scripts();
		assertTrue(scripts.contains("afterMod"));
		assertTrue(scripts.contains("beforeMod"));
	}
}
