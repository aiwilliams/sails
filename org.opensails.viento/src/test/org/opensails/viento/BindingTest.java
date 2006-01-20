package org.opensails.viento;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class BindingTest extends TestCase {
	Binding binding = new Binding();
	ShamObject target = new ShamObject();

	public void testCall_Simple() throws Exception {
		try {
			binding.call("key");
			fail("Should throw exception");
		} catch (ResolutionFailedException e) {
		}
		
		binding.put("key", target);
		assertSame(target, binding.call("key"));
		
		assertEquals("one", binding.call(target, "one"));
	}
	
	public void testCall_SuperClassMethod() throws Exception {
		assertEquals("one", binding.call(new ShamSubclass(), "one"));
	}
	
	public void testCall_Arguments() throws Exception {
		assertEquals("two", binding.call(target, "two", new Object[] {new HashSet()}));
		assertEquals("three", binding.call(target, "three", new Object[] {new Integer(3)}));
		
		assertEquals("two", binding.call(target, "two", new Object[] {null}));
		try {
			assertEquals("three", binding.call(target, "three", new Object[] {null}));
			fail("Cannot pass null to a primitive type.");
		} catch (ResolutionFailedException expected) {
		}
	}
	
	public void testTopLevelMixin() throws Exception {
		binding.mixin(target);
		assertEquals("one", binding.call("one"));
		assertEquals("three", binding.call("three", new Object[] {new Integer(3)}));
	}
	
	public void testTypeMixin() throws Exception {
		binding.mixin(String.class, target);
		assertEquals("mixin", binding.call("string", "mixin"));
		assertEquals("str", binding.call("string", "sansSuffix", new Object[] {"ing"}));
		
		binding.mixin(Set.class, target);
		assertEquals("two", binding.call(new HashSet(), "two"));
	}
	
	public void testTypeMixin_Object() throws Exception {
		binding.mixin(Object.class, target);
		assertEquals("mixin", binding.call("string", "mixin"));
	}
	
	public void testTypeMixin_Parent() throws Exception {
		binding.mixin(String.class, target);
		Binding child = new Binding(binding);
		assertEquals("mixin", child.call("string", "mixin"));
	}
	
	public void testCustomName() throws Exception {
		binding.mixin(target);
		assertEquals("$", binding.call("$"));
	}
	
	public void testBeans() throws Exception {
		assertEquals("property", binding.call(target, "property"));
		assertEquals(false, binding.call(target, "boolean"));
	}
	
	public void testParent() throws Exception {
		binding.setExceptionHandler(new ShamExceptionHandler());
		Binding child = new Binding(binding);
		binding.put("one", new ShamObject());
		assertNotNull(child.call("one"));
		
		child.put("one", "overrides");
		assertEquals("overrides", child.call("one"));
		
		assertEquals("here", child.call("notHere"));
	}
	
	public void testException() throws Exception {
		binding.setExceptionHandler(new ShamExceptionHandler());
		assertEquals("here", binding.call(new ShamObject(), "exception"));
	}
	
	public void testMethodMissing() throws Exception {
		assertEquals("methodMissing", ((Object[])binding.call(new ShamMethodMissing(), "method"))[0]);
		assertEquals("methodMissing", ((Object[])binding.call(new ShamMethodMissingNoInterface(), "method"))[0]);
	}
	
	/**
	 * There was a bug where the method being invoked was included in the argument array
	 * passed to method missing - it is the first argument to methodMissing.
	 */
	public void testMethodMissing_TopLevelMixin() throws Exception {
		binding.mixin(new ShamMethodMissing());
		Object[] result = (Object[]) binding.call("method");
		assertEquals("methodMissing", result[0]);
		assertTrue("Expected no arguments but was " + result[1], Arrays.equals(new Object[0], (Object[]) result[1]));
	}

	public void testPolymorphism() throws Exception {
		assertEquals("object", binding.call(target, "polymorphism", new Object[] {new Object()}));
		assertEquals("string", binding.call(target, "polymorphism", new Object[] {new String()}));
	}
	
	public void testMultipleNames() throws Exception {
		assertEquals("aliased", binding.call(target, "first"));
		assertEquals("aliased", binding.call(target, "second"));
	}
	
	public void testPublicFields() throws Exception {
		assertEquals("field", binding.call(target, "field"));
	}
	
	class ShamMethodMissing implements MethodMissing {
		public Object methodMissing(String methodName, Object[] args) {
			return new Object[] {"methodMissing", args};
		}
	}
	
	class ShamMethodMissingNoInterface {
	    public Object methodMissing(String methodName, Object[] args) {
	    	return new Object[] {"methodMissing", args};
	    }
	}
	
	class ShamObject {
		public String field = "field";
		public String one() {
			return "one";
		}
		
		public String two(Set set) {
			return "two";
		}
		
		public String three(int i) {
			return "three";
		}
		
		@Name("$")
		public String dollar() {
			return "$";
		}
		
		@Name({"first", "second"})
		public String aliased() {
			return "aliased";
		}
		
		public String mixin(String target) {
			return "mixin";
		}
		
		public String sansSuffix(String target, String suffix) {
			if (target.endsWith(suffix))
				return target.substring(0, target.length() - suffix.length());
			return target;
		}
		
		public String getProperty() {
			return "property";
		}
		
		public boolean isBoolean() {
			return false;
		}
		
		public String exception() {
			throw new RuntimeException("here");
		}
		
		public String exception(Object target) {
			throw new RuntimeException("here");
		}
		
		public String polymorphism(String string) {
			return "string";
		}
		
		public String polymorphism(Object object) {
			return "object";
		}
	}
	
	class ShamSubclass extends ShamObject {
	}
}
