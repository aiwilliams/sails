package org.opensails.sails.form;

import java.util.Arrays;

import junit.framework.TestCase;

public class FormFieldsTest extends TestCase {
	public void testGet_EachOfThem() throws Exception {
		String string = "value";
		String[] array = new String[] { string };

		FormFields map = FormFields.quick("stringArray", array);
		assertEquals(string, map.value("stringArray"));
		assertNull(map.value("somethingNotThere"));
		assertEquals(array, map.values("stringArray"));
		assertNull(map.values("somethingNotThere"));
	}

	public void testQuick() {
		FormFields fields = FormFields.quick("one", "one");
		assertEquals("one", fields.value("one"));
		assertTrue(Arrays.equals(new String[] { "one" }, fields.values("one")));
	}

	public void testSet_EachOfThem() throws Exception {
		FormFields map = new FormFields();
		map.setValue("something", "else");
		assertEquals("else", map.value("something"));
	}
}
