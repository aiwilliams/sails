package org.opensails.rigging;

import junit.framework.TestCase;

public class ScopedContainerTest extends TestCase {
	ScopedContainer first = new ScopedContainer(ShamScope.FIRST);
	ScopedContainer second = first.makeChild();
	ScopedContainer third = second.makeChild();

	public void testMakeChild() throws Exception {
		assertEquals(ShamScope.SECOND, second.getScope());
		assertSame(first, second.getParent());
		assertEquals(ShamScope.THIRD, third.getScope());
		assertSame(second, third.getParent());

		try {
			third.makeChild();
			fail("Expected an exception");
		} catch (NotEnoughScopesException expected) {
		}
	}

	public void testMakeChildUnscoped() throws Exception {
		assertEquals(third, third.makeChildUnscoped().getParent());
	}

	public void testGetConainerInHierarchy() throws Exception {
		assertSame(third, third.getContainerInHierarchy(ShamScope.THIRD));
		assertSame(second, third.getContainerInHierarchy(ShamScope.SECOND));
		assertSame(first, third.getContainerInHierarchy(ShamScope.FIRST));

		assertNull(second.getContainerInHierarchy(ShamScope.THIRD));
		assertSame(second, second.getContainerInHierarchy(ShamScope.SECOND));
		assertSame(first, second.getContainerInHierarchy(ShamScope.FIRST));
	}

	public void testMakeChild_Scope() throws Exception {
		ScopedContainer child = first.makeChild(ShamScope.THIRD);
		assertSame(first, child.getParent());
		assertNull(child.getContainerInHierarchy(ShamScope.SECOND));
		assertSame(first, child.getContainerInHierarchy(ShamScope.FIRST));
	}

	enum ShamScope {
		FIRST, SECOND, THIRD,
	}
}
