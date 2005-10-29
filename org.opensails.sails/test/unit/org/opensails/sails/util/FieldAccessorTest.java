package org.opensails.sails.util;

import junit.framework.TestCase;

public class FieldAccessorTest extends TestCase {
	protected FieldAccessor fieldOneReaderWriter;
	protected MyObject myObject;
	protected MySimilarObject mySimilarObject;
	protected MySuperObject mySuperObject;
	protected FieldAccessor superFieldReaderWriter;

	public void testGet() {
		assertEquals(myObject.fieldOne, fieldOneReaderWriter.get(myObject));
		assertEquals(mySimilarObject.fieldOne, fieldOneReaderWriter.get(mySimilarObject));
		try {
			fieldOneReaderWriter.get(mySuperObject);
			fail("Should throw exception if field isn't there");
		} catch (Exception e) {}

		assertEquals(myObject.superField, superFieldReaderWriter.get(myObject));
		assertEquals(mySuperObject.superField, superFieldReaderWriter.get(myObject));
		try {
			superFieldReaderWriter.get(mySimilarObject);
			fail("Should throw exception if field isn't there");
		} catch (Exception e) {}
	}

	public void testGetField() {
		FieldAccessor helper = new FieldAccessor("fieldOne");

		MyObject object = new MyObject();
		assertNotNull(helper.getField(object));

		helper = new FieldAccessor("superField");
		assertNotNull(helper.getField(object));

		assertNull(helper.getField(new Object()));
	}

	public void testSet() {
		String newValue = "newValue";
		fieldOneReaderWriter.set(myObject, newValue);
		assertEquals(newValue, myObject.fieldOne);
		fieldOneReaderWriter.set(mySimilarObject, newValue);
		assertEquals(newValue, mySimilarObject.fieldOne);
		try {
			fieldOneReaderWriter.set(mySuperObject, newValue);
			fail("Should throw exception if field isn't there");
		} catch (Exception e) {}

		superFieldReaderWriter.set(myObject, newValue);
		assertEquals(newValue, myObject.superField);
		superFieldReaderWriter.set(mySuperObject, newValue);
		assertEquals(newValue, mySuperObject.superField);
		try {
			superFieldReaderWriter.set(mySimilarObject, newValue);
			fail("Should throw exception if field isn't there");
		} catch (Exception e) {}
	}

	@Override
	protected void setUp() {
		fieldOneReaderWriter = new FieldAccessor("fieldOne");
		superFieldReaderWriter = new FieldAccessor("superField");
		myObject = new MyObject();
		mySuperObject = new MySuperObject();
		mySimilarObject = new MySimilarObject();
	}

	public class MyObject extends MySuperObject {
		private String fieldOne = "fieldOneInitialValue";
	}

	public class MySimilarObject {
		protected String fieldOne = "fieldOneInitialValue";
	}

	public class MySuperObject {
		protected String superField = "superFieldInitialValue";
	}
}
