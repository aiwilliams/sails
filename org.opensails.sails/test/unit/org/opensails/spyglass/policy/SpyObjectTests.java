package org.opensails.spyglass.policy;

import junit.framework.TestCase;

import org.opensails.spyglass.SpyObject;

public class SpyObjectTests extends TestCase {
	public static class MyObject {
		protected String getterOnly = "originalValue";
		protected String setterOnly = "originalValue";
		
		public String getGetterOnly() {
			return getterOnly;
		}
		
		public void setSetterOnly(String value) {
			setterOnly = value;
		}
	}

	public void testIt() throws Exception {
		MyObject myObject = new MyObject();
		SpyObject<MyObject> spy = SpyObject.create(myObject);
		spy.write("getterOnly", "newValue");
		assertEquals("newValue", myObject.getterOnly);
		spy.write("getterOnly", null);
		assertEquals(null, myObject.getterOnly);
		
		assertEquals("originalValue", spy.read("setterOnly"));
		myObject.setterOnly = null;
		assertEquals(null, spy.read("setterOnly"));
	}
}
