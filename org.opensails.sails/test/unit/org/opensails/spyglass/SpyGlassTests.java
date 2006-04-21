package org.opensails.spyglass;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class SpyGlassTests extends TestCase {
	public void testMethodsNamedInHierarchy() throws Exception {
		ClassKey anonymous = new ClassKey() {
			@SuppressWarnings("unused")
			public void publicMethod() {}
		};
		Method[] methods = SpyGlass.methodsNamedInHeirarchy(anonymous.getClass(), "publicMethod");
		assertEquals(1, methods.length);
	}
}
