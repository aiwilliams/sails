package org.opensails.sails.html;

import junit.framework.TestCase;

public class UnderscoreIdGeneratorTests extends TestCase {
	UnderscoreIdGenerator generator = new UnderscoreIdGenerator();

	public void testIdForName() throws Exception {
		assertEquals("some_name", generator.idForName("some.name"));
		assertEquals("some_name-1", generator.idForName("some.name"));
		assertEquals("somename", generator.idForName("somename"));
	}

	public void testIdForNameValue() throws Exception {
		assertEquals("some_name-value", generator.idForNameValue("some.name", "value"));
		assertEquals("some_name-value-1", generator.idForNameValue("some.name", "value"));
		assertEquals("some_name", generator.idForNameValue("some.name", ""));
		assertEquals("value", generator.idForNameValue("", "value"));
		assertEquals("some_name-value_with_spaces_and_dots_and_commas", generator.idForNameValue("some.name", "value, with spaces.and.dots,and,commas"));
	}
}
