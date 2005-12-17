package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.url.UrlFixture;

public class StyleTest extends TestCase {
	public void testEquals_HashCode() throws Exception {
		Style inlineOne = new Style(InlineContentFixture.create("stuff"));
		Style inlineTwo = new Style(InlineContentFixture.create(new String("stuff")));
		assertEquals(inlineOne, inlineOne);
		assertEquals(inlineOne, inlineTwo);
		assertEquals(inlineOne.hashCode(), inlineOne.hashCode());
		assertEquals(inlineOne.hashCode(), inlineTwo.hashCode());

		Style linkedOne = new Style(UrlFixture.create("a"));
		Style linkedTwo = new Style(UrlFixture.create("a"));
		assertEquals(linkedOne, linkedOne);
		assertEquals(linkedOne, linkedTwo);
		assertEquals(linkedOne.hashCode(), linkedOne.hashCode());
		assertEquals(linkedOne.hashCode(), linkedTwo.hashCode());

		assertFalse(inlineOne.equals(linkedOne));
		assertFalse(inlineOne.hashCode() == linkedOne.hashCode());
	}

	public void testInline() throws Exception {
		Style style = new Style(InlineContentFixture.create("stuff"));
		assertEquals("<style type=\"text/css\">\nstuff\n</style>", style.toString());

		style = new Style(UrlFixture.create("a"));
		assertEquals("<link href=\"a\" type=\"text/css\" rel=\"stylesheet\" />", style.toString());
	}
}
