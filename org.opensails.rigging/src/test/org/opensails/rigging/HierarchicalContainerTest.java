package org.opensails.rigging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.opensails.rigging.SimpleContainerTest.IShoelace;
import org.opensails.rigging.SimpleContainerTest.Shoelace;

public class HierarchicalContainerTest extends TestCase {
	HierarchicalContainer parent = new HierarchicalContainer();
	IHierarchicalContainer child = parent.makeChild();
	
	public void testAllInstances() throws Exception {
		child.register(ShamComponent.class, new ShamComponent());
		parent.register(ShamSubclassingComponent.class);
		assertEquals(1, parent.allInstances(false).size());
		assertEquals(2, parent.allInstances(true).size());
		assertEquals(2, parent.allInstances(false).size());
	}

	public void testBroadcast_ThreadSafety() throws Exception {
		child.register(Shoelace.class);
		child.registerInstantiationListener(Shoelace.class, new InstantiationListener<Shoelace>() {
			public void instantiated(Shoelace newInstance) {
				parent.makeChild();
			}
		});
		parent.broadcast(IShoelace.class, true).untie();
	}

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
	
    public void testMakeLocal() throws Exception {
		parent.register(List.class, ArrayList.class);
		List parentInstance = parent.instance(List.class);
		
		child.makeLocal(List.class);
		List childInstance = child.instance(List.class);
		
		assertNotSame(parentInstance, childInstance);
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
}
