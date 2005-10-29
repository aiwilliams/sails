package org.opensails.rigging;

import java.util.Arrays;

import junit.framework.TestCase;

public class HierarchicalContainerTest extends TestCase {
	HierarchicalContainer parent = new HierarchicalContainer();
	HierarchicalContainer child = parent.makeChild();
	
	public void testChild() throws Exception {
		assertSame(parent, child.getParent());

		parent.register(ShamComponent.class);
		assertTrue(child.contains(ShamComponent.class));
		assertFalse(child.containsLocally(ShamComponent.class));
		assertNotNull(child.instance(ShamComponent.class));
		
		child.register(ShamComponentWithDependencies.class);
		assertTrue(child.contains(ShamComponentWithDependencies.class));
		assertTrue(child.containsLocally(ShamComponentWithDependencies.class));
		assertNotNull(child.instance(ShamComponentWithDependencies.class).dependency);
		
		child.register(ShamComponent.class);
		assertNotSame(parent.instance(ShamComponent.class), child.instance(ShamComponent.class));
	}

    public void testDispose() throws Exception {
		child.register(ShamDisposable.class);
		parent.dispose();
		assertTrue(child.instance(ShamDisposable.class).disposed);
	}

	public void testInstance_UnsatisfiableDependencies() throws Exception {
        parent.register(ShamComponentWithDependencies.class);
        try {
            child.instance(ShamComponentWithDependencies.class);
            fail("We need to know if our components' dependencies can't be met. If something is registered, and I ask for it, it shant be null.");
        } catch (UnsatisfiableDependenciesException expected) {
            assertTrue(Arrays.equals(new Class<?>[] {ShamComponent.class}, expected.getMinimumDependencies()));
        }
    }

    public void testInstance_WithDefaultImplementation_DontOverrideParent() throws Exception {
        parent.register(ShamComponent.class);
        assertTrue(child.contains(ShamComponent.class));
        assertFalse(child.containsLocally(ShamComponent.class));
        assertNotNull(child.instance(ShamComponent.class, ShamComponent.class));
        assertFalse(child.containsLocally(ShamComponent.class));
    }

	public void testParent() throws Exception {
		assertNull("Shouldn't throw exception", parent.instance(String.class));
		child.register(ShamComponent.class);
		assertNull(parent.instance(ShamComponent.class));
	}
	
	public void testRemoveChild() throws Exception {
		parent.register(ShamComponent.class);
		
		parent.removeChild(child);
		assertNull(child.getParent());
		assertNull(child.instance(ShamComponent.class));
	}
	
	public void testStart() throws Exception {
		child.register(ShamStartable.class);
		parent.start();
		assertTrue(child.instance(ShamStartable.class).started);
	}
	
	public void testStop() throws Exception {
		child.register(ShamStoppable.class);
		parent.stop();
		assertTrue(child.instance(ShamStoppable.class).stopped);
	}
}
