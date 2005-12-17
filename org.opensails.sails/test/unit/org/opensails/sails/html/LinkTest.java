package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.url.UrlFixture;

public class LinkTest extends TestCase {
	public void testEquals_HashCode() throws Exception {
		Link linkOne = new Link();
		Link linkTwo = new Link();
		assertEquals(linkOne, linkTwo);

		linkOne.href(UrlFixture.create("a"));
		assertFalse(linkOne.equals(linkTwo));

		linkTwo.href(UrlFixture.create("a"));
		assertEquals(linkOne, linkTwo);
	}
}
