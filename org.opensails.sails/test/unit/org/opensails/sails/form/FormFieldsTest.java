package org.opensails.sails.form;

import junit.framework.TestCase;

import org.opensails.sails.tester.browser.ShamFileUpload;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.Quick;

public class FormFieldsTest extends TestCase {
	public void testAddFieldValue() {
		FormFields fields = new FormFields(new ShamHttpServletRequest());
		fields.addFieldValue("key", "one");
		fields.addFieldValue("key", "two");
		CollectionAssert.containsOnly(Quick.list("one", "two"), fields.values("key"));
	}

	/**
	 * The value setters will not modify the request, as it is immutable in a
	 * real container.
	 */
	public void testSetValue() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		FormFields fields = new FormFields(request);

		fields.setValue("something", "else");
		assertEquals("else", fields.value("something"));
		assertNull(request.getParameter("something"));

		fields.setValues("stringArray");
		CollectionAssert.containsOnly(new String[0], fields.values("stringArray"));
		assertNull(fields.value("stringArray"));
		assertNull(request.getParameter("stringArray"));

		ShamFileUpload fileUpload = new ShamFileUpload("zNamen", "zContenten");
		try {
			fields.setValue("fileParam", fileUpload);
			fail("Why would anyone want to do this in real life? See the testing form fields class if you are writing tests.");
		} catch (IllegalArgumentException expected) {}
	}

	public void testValue() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setParameter("stringParam", "stringParamValue");
		request.setParameters("stringArrayParam", "valueOne", "valueTwo");

		FormFields fields = new FormFields(request);

		assertEquals("stringParamValue", fields.value("stringParam"));
		CollectionAssert.containsOnly(new String[] { "stringParamValue" }, fields.values("stringParam"));

		assertEquals("valueOne", fields.value("stringArrayParam"));
		CollectionAssert.containsOnlyOrdered(new String[] { "valueOne", "valueTwo" }, fields.values("stringArrayParam"));

		assertNull(fields.value("huh?"));
		CollectionAssert.containsOnly(new String[0], fields.values("huh?"));
	}

	public void testValue_Empty() throws Exception {
		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setParameter("emptyStringParam", "");
		request.setParameters("emptyStringArrayParam", new String[0]);

		FormFields fields = new FormFields(request);

		assertNull(fields.value("emptyStringParam"));
		CollectionAssert.containsOnly(new String[] { "" }, fields.values("emptyStringParam"));

		assertNull(fields.value("emptyStringArrayParam"));
		CollectionAssert.containsOnly(new String[0], fields.values("emptyStringArrayParam"));
	}

	@SuppressWarnings("unchecked")
	public void testValue_Multipart() throws Exception {
		ShamFileUpload upload = new ShamFileUpload("theFileName.test", "content");

		ShamHttpServletRequest request = new ShamHttpServletRequest();
		request.setParameter("myFileUpload", upload);

		FormFields fields = new FormFields(request);
		assertEquals("theFileName.test", fields.value("myFileUpload"));
		CollectionAssert.containsOnlyOrdered(new String[] { "theFileName.test" }, fields.values("myFileUpload"));

		// ensure proper container-time operation
		fields.backingMap.put("setParameterNotUsedFile", new ShamFileUpload("fileNameAgain.txt", "file content, yeah"));
		assertEquals("fileNameAgain.txt", fields.value("setParameterNotUsedFile"));
		CollectionAssert.containsOnlyOrdered(new String[] { "fileNameAgain.txt" }, fields.values("setParameterNotUsedFile"));
	}
}
