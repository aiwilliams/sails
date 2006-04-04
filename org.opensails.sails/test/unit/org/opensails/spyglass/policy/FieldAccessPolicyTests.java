package org.opensails.spyglass.policy;

import junit.framework.TestCase;

import org.opensails.spyglass.SpyGlass;

public class FieldAccessPolicyTests extends TestCase {
	@SuppressWarnings("unused")
	private String privateField;
	protected String protectedField;
	public String publicField;
	String defaultField;
	
	public void testPrivate() throws Exception {
		assertTrue(FieldAccessPolicy.PRIVATE.canAccess(SpyGlass.getField(getClass(), "privateField")));
		assertTrue(FieldAccessPolicy.PRIVATE.canAccess(SpyGlass.getField(getClass(), "protectedField")));
		assertTrue(FieldAccessPolicy.PRIVATE.canAccess(SpyGlass.getField(getClass(), "publicField")));
		assertTrue(FieldAccessPolicy.PRIVATE.canAccess(SpyGlass.getField(getClass(), "defaultField")));
	}

	public void testProtected() throws Exception {
		assertFalse(FieldAccessPolicy.PROTECTED.canAccess(SpyGlass.getField(getClass(), "privateField")));
		assertTrue(FieldAccessPolicy.PROTECTED.canAccess(SpyGlass.getField(getClass(), "protectedField")));
		assertTrue(FieldAccessPolicy.PROTECTED.canAccess(SpyGlass.getField(getClass(), "publicField")));
		assertFalse(FieldAccessPolicy.PROTECTED.canAccess(SpyGlass.getField(getClass(), "defaultField")));
	}
	
	public void testPublic() throws Exception {
		assertFalse(FieldAccessPolicy.PUBLIC.canAccess(SpyGlass.getField(getClass(), "privateField")));
		assertFalse(FieldAccessPolicy.PUBLIC.canAccess(SpyGlass.getField(getClass(), "protectedField")));
		assertTrue(FieldAccessPolicy.PUBLIC.canAccess(SpyGlass.getField(getClass(), "publicField")));
		assertFalse(FieldAccessPolicy.PUBLIC.canAccess(SpyGlass.getField(getClass(), "defaultField")));
	}
}
