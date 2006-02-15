package org.opensails.sails.form;

import java.util.Arrays;
import java.util.Map;

import junit.framework.TestCase;

import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.tester.form.TestFileUpload;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.Quick;

public class FormFieldsTest extends TestCase {
	public void testAddFieldValue() {
		FormFields map = new FormFields();
		map.addFieldValue("key", "one");
		map.addFieldValue("key", "two");
		CollectionAssert.containsOnly(Quick.list("one", "two"), map.values("key"));
	}

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

	@SuppressWarnings("unchecked")
	public void testValue_FileUpload() throws Exception {
		TestFileUpload upload = new TestFileUpload("theFileName.test", "content");
		FormFields fields = new FormFields();
		Map<String, Object> backingMap = (Map<String, Object>) ClassHelper.readField(fields, "backingMap", false);
		backingMap.put("myFileUpload", upload);
		assertEquals("content", fields.valueAs("myFileUpload", FieldType.STRING));
		assertSame(upload, fields.valueAs("myFileUpload", FieldType.FILE_UPLOAD));
		assertEquals("theFileName.test", fields.value("myFileUpload"));
	}
}
