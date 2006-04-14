package org.opensails.spyglass.policy;

import junit.framework.TestCase;

import org.opensails.spyglass.SpyGlass;

public class MethodAccessPolicyTests extends TestCase {
	public void publicMethod() {}
	protected void protectedMethod() {}
	@SuppressWarnings("unused")
	private void privateMethod() {}
	void defaultMethod() {};

	public void testPrivate() throws Exception {
		assertTrue(MethodInvocationPolicy.PRIVATE.canInvoke(SpyGlass.getMethod(getClass(), "privateMethod")));
		assertTrue(MethodInvocationPolicy.PRIVATE.canInvoke(SpyGlass.getMethod(getClass(), "protectedMethod")));
		assertTrue(MethodInvocationPolicy.PRIVATE.canInvoke(SpyGlass.getMethod(getClass(), "publicMethod")));
		assertTrue(MethodInvocationPolicy.PRIVATE.canInvoke(SpyGlass.getMethod(getClass(), "defaultMethod")));
	}

	public void testProtected() throws Exception {
		assertFalse(MethodInvocationPolicy.PROTECTED.canInvoke(SpyGlass.getMethod(getClass(), "privateMethod")));
		assertTrue(MethodInvocationPolicy.PROTECTED.canInvoke(SpyGlass.getMethod(getClass(), "protectedMethod")));
		assertTrue(MethodInvocationPolicy.PROTECTED.canInvoke(SpyGlass.getMethod(getClass(), "publicMethod")));
		assertFalse(MethodInvocationPolicy.PROTECTED.canInvoke(SpyGlass.getMethod(getClass(), "defaultMethod")));
	}

	public void testPublic() throws Exception {
		assertFalse(MethodInvocationPolicy.PUBLIC.canInvoke(SpyGlass.getMethod(getClass(), "privateMethod")));
		assertFalse(MethodInvocationPolicy.PUBLIC.canInvoke(SpyGlass.getMethod(getClass(), "protectedMethod")));
		assertTrue(MethodInvocationPolicy.PUBLIC.canInvoke(SpyGlass.getMethod(getClass(), "publicMethod")));
		assertFalse(MethodInvocationPolicy.PUBLIC.canInvoke(SpyGlass.getMethod(getClass(), "defaultMethod")));
	}
}
