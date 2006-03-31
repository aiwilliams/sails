package org.opensails.sails.form;

import java.util.Map;

import junit.framework.TestCase;

import org.opensails.sails.tester.browser.ShamFileUpload;
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

		FormFields map = new FormFields();
		map.setValues("stringArray", array);
		assertEquals(string, map.value("stringArray"));
		assertNull(map.value("somethingNotThere"));
		assertEquals(array, map.values("stringArray"));
		assertNull(map.values("somethingNotThere"));
	}

	public void testSet_EachOfThem() throws Exception {
		FormFields map = new FormFields();
		map.setValue("something", "else");
		assertEquals("else", map.value("something"));
	}

	@SuppressWarnings("unchecked")
	public void testValue_FileUpload() throws Exception {
		ShamFileUpload upload = new ShamFileUpload("theFileName.test", "content");
		FormFields fields = new FormFields();
		ClassHelper.writeDeclaredField(fields, "multipartContent", true);
		Map<String, Object> backingMap = (Map<String, Object>) ClassHelper.readField(fields, "backingMap", false);
		backingMap.put("myFileUpload", upload);
		assertSame(upload, fields.getValue("myFileUpload"));
		assertEquals("content", fields.value("myFileUpload"));
	}
}
