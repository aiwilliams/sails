package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import junit.framework.TestCase;

import org.opensails.sails.component.NotForJs;

public class SpyGlassTests extends TestCase {
	@SuppressWarnings("unchecked")
	public void testFieldsAnnotated() throws Exception {
		@SuppressWarnings("unused")
		ClassKey anonymous = new ClassKey() {
			public String one;
			@NotForJs
			public String two;
		};
		SpyClass spy = new SpyClass(anonymous.getClass());
		Collection<Field> fields = spy.getFieldsAnnotated(NotForJs.class);
		assertEquals(1, fields.size());
		assertEquals("two", fields.iterator().next().getName());

		fields = spy.getFieldsNotAnnotated(NotForJs.class);
		// The containing instance, of course
		assertEquals(2, fields.size());
		assertEquals("one", fields.iterator().next().getName());
	}

	public void testLowerCamelName_InnerClasses() throws Exception {
		assertEquals("innerClass", SpyGlass.lowerCamelName(InnerClass.class));
		assertEquals("innerInnerClass", SpyGlass.lowerCamelName(InnerClass.InnerInnerClass.class));
	}

	public void testMethodsNamedInHierarchy() throws Exception {
		ClassKey anonymous = new ClassKey() {
			@SuppressWarnings("unused")
			public void publicMethod() {}
		};
		Method[] methods = SpyGlass.methodsNamedInHeirarchy(anonymous.getClass(), "publicMethod");
		assertEquals(1, methods.length);
	}

	public class InnerClass {
		protected class InnerInnerClass {}
	}
}