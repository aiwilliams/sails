package org.opensails.sails.model.oem;

import junit.framework.TestCase;

import org.opensails.sails.adapter.AdaptationTarget;

public class DotPropertyAccessorTest extends TestCase {
	public void testGetAdaptationTarget() throws Exception {
		DotPropertyAccessor<Object> accessor = new DotPropertyAccessor<Object>(new DotPropertyPath("object.nullProperty"));
		AdaptationTarget adaptationTarget = accessor.getAdaptationTarget(new MyObject());
		assertEquals(String.class, adaptationTarget.getTargetClass());
	}

	public void testIt() throws Exception {
		DotPropertyAccessor<Object> accessor = new DotPropertyAccessor<Object>(new DotPropertyPath(" object.nullProperty"));
		MyObject myObject = new MyObject();
		assertNull(accessor.get(myObject));
		myObject.nullProperty = "not null";
		assertEquals("not null", accessor.get(myObject));
	}

	public static class MyObject {
		protected String nullProperty;
	}
}