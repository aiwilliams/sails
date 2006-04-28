package org.opensails.sails.url;

import org.opensails.functional.SailsFunctionalTester;

import junit.framework.TestCase;

public class AbsoluteUrlTest extends TestCase {
	SailsFunctionalTester t = new SailsFunctionalTester();
	AbsoluteUrl url = t.actionUrl("myAction").absolute();

	public void testAbsolute() {
		assertSame(url, url.absolute());
	}

	public void testSetQueryParameter() throws Exception {
		url.setQueryParameter("a", "b");
		url.setQueryParameter("c", "d e");
		assertEquals("b", url.getQueryParameter("a"));
		assertEquals("d e", url.getQueryParameter("c"));
		assertEquals("http://localhost/shamcontext/shamservlet/home/myAction?a=b&c=d+e", url.renderThyself());
	}
}
