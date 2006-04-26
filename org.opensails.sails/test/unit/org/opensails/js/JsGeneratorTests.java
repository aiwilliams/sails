package org.opensails.js;

import org.opensails.sails.ApplicationScope;
import org.opensails.viento.IRenderable;

import junit.framework.TestCase;

public class JsGeneratorTests extends TestCase {

	public void testGenerators() throws Exception {
		assertRender("methodName('argOne',[2])", Js.methodCall("methodName", "argOne", Js.array(2)));
		assertRender("['asdf',1.2,'COMPONENT']", Js.array("asdf", 1.2, ApplicationScope.COMPONENT));
		assertRender("{one:'as\\'\\ndf',two:2}", Js.object("one", "as'\ndf", "two", 2));
		assertRender("$('asdf')", Js.node("asdf"));
		assertRender("asdf", Js.literal("asdf"));
	}
	
	protected void assertRender(String expected, IRenderable actual) {
		assertEquals(expected, actual.renderThyself());
	}
}
