package org.opensails.text;

import junit.framework.TestCase;

public class DelimitedTextTest extends TestCase {
	public void testAppend() throws Exception {
		DelimitedText t = new DelimitedText(",");
		t.append("hello");
		assertEquals("hello", t.toString());
		t.append("hello");
		assertEquals("hello,hello", t.toString());
		t.append("hello", false);
		assertEquals("hello,hellohello", t.toString());
	}
}
