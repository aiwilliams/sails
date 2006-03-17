package org.opensails.sails.html;

import junit.framework.TestCase;

import org.opensails.sails.url.ExternalUrl;

public class LinkTest extends TestCase {
	public void testEquals_HashCode() throws Exception {
		Link linkOne = new Link();
		Link linkTwo = new Link();
		assertEquals(linkOne, linkTwo);

		linkOne.href(new ExternalUrl("a"));
		assertFalse(linkOne.equals(linkTwo));

		linkTwo.href(new ExternalUrl("a"));
		assertEquals(linkOne, linkTwo);
	}
}
