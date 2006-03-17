package org.opensails.sails.template;

import junit.framework.TestCase;

import org.opensails.sails.html.Link;
import org.opensails.sails.html.Script;
import org.opensails.sails.url.ExternalUrl;

public class RequireTest extends TestCase {
	public void testLink() throws Exception {
		Link linkA1 = new Link(new ExternalUrl("a"));
		Link linkA2 = new Link(new ExternalUrl("b"));

		Require require = new Require();
		require.link(linkA1);
		require.link(linkA2);
		require.link(new Link(new ExternalUrl("a")));

		assertEquals(String.format("%s\n%s", linkA1, linkA2), require.output().links());
	}

	public void testScript() throws Exception {
		Script scriptA1 = new Script(new ExternalUrl("a"));
		Script scriptB1 = new Script(new ExternalUrl("b"));

		Require require = new Require();
		require.script(scriptA1);
		require.script(scriptB1);
		// if 'a' is required again, make sure it doesn't lose it's position as
		// first requirement
		require.script(new Script(new ExternalUrl("a")));

		assertEquals(String.format("%s\n%s", scriptA1, scriptB1), require.output().scripts());
	}

	public void testScript_ModificationAfterAddition() {
		Script scriptA = new Script(new ExternalUrl("beforeMod"));
		Script scriptB = new Script(new ExternalUrl("beforeMod"));
		Require require = new Require();
		require.script(scriptA);
		require.script(scriptB);
		scriptA.src(new ExternalUrl("afterMod"));
		String scripts = require.output().scripts();
		assertTrue(scripts.contains("afterMod"));
		assertTrue(scripts.contains("beforeMod"));
	}
}
