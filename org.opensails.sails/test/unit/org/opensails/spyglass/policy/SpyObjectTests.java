package org.opensails.spyglass.policy;

import junit.framework.TestCase;

import org.opensails.spyglass.SpyObject;

public class SpyObjectTests extends TestCase {
	MyObject myObject = new MyObject();
	SpyObject<MyObject> spy = SpyObject.create(myObject);

	public void testIt() throws Exception {
		spy.write("getterOnly", "newValue");
		assertEquals("newValue", myObject.getterOnly);
		spy.write("getterOnly", null);
		assertEquals(null, myObject.getterOnly);

		assertEquals("originalValue", spy.read("setterOnly"));
		myObject.setterOnly = null;
		assertEquals(null, spy.read("setterOnly"));
	}

	public void testSetter_Autoboxing() throws Exception {
		spy.write("primitiveInt", 1);
		assertEquals(1, spy.read("primitiveInt"));
		assertEquals(1, myObject.primitiveInt);

		spy.write("primitiveInt", new Integer(2));
		assertEquals(2, spy.read("primitiveInt"));
		assertEquals(2, myObject.primitiveInt);
	}

	public static class MyObject {
		protected String getterOnly = "originalValue";
		protected String setterOnly = "originalValue";
		protected int primitiveInt = -1;

		public String getGetterOnly() {
			return getterOnly;
		}

		public void setPrimitiveInt(int value) {
			primitiveInt = value;
		}

		public void setSetterOnly(String value) {
			setterOnly = value;
		}
	}
}
