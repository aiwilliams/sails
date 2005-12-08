package org.opensails.sails.form;

import java.util.*;

import junit.framework.*;

public class FormFieldsTest extends TestCase {
	public void testGet_EachOfThem() throws Exception {
		String string = "value";
		String[] array = new String[] { string };

		Map<String, String[]> back = new HashMap<String, String[]>();
		back.put("stringArray", array);

		FormFields map = new FormFields(back);
		assertEquals(string, map.value("stringArray"));
		assertEquals(array, map.values("stringArray"));
	}

	public void testSet_EachOfThem() throws Exception {
		FormFields map = new FormFields();
		map.setValue("something", "else");
		assertEquals("else", map.value("something"));
	}

	public void testQuick() {
		FormFields fields = FormFields.quick("one", "one");
		assertEquals("one", fields.value("one"));
		assertTrue(Arrays.equals(new String[] { "one" }, fields.values("one")));
	}
}
