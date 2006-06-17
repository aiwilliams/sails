package org.opensails.spyglass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
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

	public void testNumberOfGenerationsBack() throws Exception {
		assertEquals(0, SpyGlass.numberOfGenerationsBack(SpyGlassTests.class, SpyGlassTests.class));
		assertEquals(1, SpyGlass.numberOfGenerationsBack(SpyGlassTests.class, TestCase.class));
		assertEquals(2, SpyGlass.numberOfGenerationsBack(SpyGlassTests.class, Assert.class));
		assertEquals(3, SpyGlass.numberOfGenerationsBack(SpyGlassTests.class, Object.class));
		assertEquals(2, SpyGlass.numberOfGenerationsBack(SpyGlassTests.class, Test.class));

		assertEquals(0, SpyGlass.numberOfGenerationsBack(List.class, List.class));
		assertEquals(1, SpyGlass.numberOfGenerationsBack(List.class, Collection.class));
		assertEquals(2, SpyGlass.numberOfGenerationsBack(List.class, Iterable.class));
		assertEquals(-1, SpyGlass.numberOfGenerationsBack(List.class, Object.class));
	}

	public class InnerClass {
		protected class InnerInnerClass {}
	}
}