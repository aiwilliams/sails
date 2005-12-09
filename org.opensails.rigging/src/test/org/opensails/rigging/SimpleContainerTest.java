package org.opensails.rigging;

import java.util.Arrays;
import java.util.Set;

import junit.framework.TestCase;

public class SimpleContainerTest extends TestCase {
    SimpleContainer container = new SimpleContainer();
    boolean resolverResolverCalled;
    boolean listenerCalled;
    
    public void testContains() throws Exception {
        assertFalse(container.contains(ShamComponent.class));
        container.register(ShamComponent.class);
        assertTrue(container.contains(ShamComponent.class));
    }
    
    public void testDispose() throws Exception {
        container.register(ShamDisposable.class);
        container.dispose();
        assertFalse("Should not instantiate", container.instance(ShamDisposable.class).disposed);
        container.dispose();
        assertTrue(container.instance(ShamDisposable.class).disposed);
    }

    public void testInstance() throws Exception {
        assertNull(container.instance(ShamComponent.class));

        container.register(ShamComponent.class);
        ShamComponent instance = container.instance(ShamComponent.class);
        assertNotNull(instance);
    }

    public void testInstance_Cacheing() throws Exception {
        container.register(ShamComponent.class);
        ShamComponent instance = container.instance(ShamComponent.class);
        assertNotNull(instance);
        assertSame(instance, container.instance(ShamComponent.class));
    }

    public void testInstance_UnsatisfiableDependencies() throws Exception {
        container.register(ShamComponentWithDependencies.class);
        try {
            container.instance(ShamComponentWithDependencies.class);
            fail("We need to know if our components' dependencies can't be met. If something is registered, and I ask for it, it shant be null.");
        } catch (UnsatisfiableDependenciesException expected) {
            assertTrue(Arrays.equals(new Class<?>[] {ShamComponent.class}, expected.getMinimumDependencies()));
        }
    }
    
    public void testInstance_WithDefaultImplementation() throws Exception {
        assertFalse(container.contains(ShamComponent.class));
        assertNotNull(container.instance(ShamComponent.class, ShamComponent.class));
        assertTrue(container.contains(ShamComponent.class));
    }

    public void testRegister_ComponentResolver() throws Exception {
        final ShamComponent component = new ShamComponent();
        container.register(ShamComponent.class, new ComponentResolver() {
            public Object instance() {
                return component;
            }

			public boolean isInstantiated() {
				return true;
			}

			public Class<?> type() {
				return ShamComponent.class;
			}
        });

        assertSame(component, container.instance(ShamComponent.class));
    }

    public void testRegister_Instance() throws Exception {
        ShamComponent instance = new ShamComponent();
        container.register(ShamComponent.class, instance);

        assertSame(instance, container.instance(ShamComponent.class));
    }

    public void testRegister_Instance_InferKey() throws Exception {
        ShamSubclassingComponent instance = new ShamSubclassingComponent();
        container.register(instance);

        assertSame(instance, container.instance(ShamSubclassingComponent.class));
        assertNull(container.instance(ShamComponent.class));
    }

    public void testRegister_Subclass() throws Exception {
        container.register(ShamComponent.class, ShamSubclassingComponent.class);
        ShamComponent instance = container.instance(ShamComponent.class);
        assertTrue(instance instanceof ShamSubclassingComponent);

        assertNull(container.instance(ShamSubclassingComponent.class));
    }

    public void testResolverResolving() throws Exception {
        container.push(new IComponentResolverResolver() {
            public Set<Class> keySet() {
                return null;
            }

            public ComponentResolver resolve(Class key, SimpleContainer container) {
                resolverResolverCalled = true;
                return null;
            }

            public boolean canResolve(Class key, SimpleContainer container) {
                return true;
            }
        });
        
        container.instance(String.class);
        assertTrue(resolverResolverCalled);
    }

    public void testResolvesDependencies() throws Exception {
        container.register(ShamComponent.class);
        container.register(ShamComponentWithDependencies.class);

        ShamComponentWithDependencies component = container.instance(ShamComponentWithDependencies.class);
        assertNotNull(component.dependency);
        assertSame(component.dependency, container.instance(ShamComponent.class));
    }

    public void testStart() throws Exception {
        container.register(ShamStartable.class);
        container.start();
        assertTrue(container.instance(ShamStartable.class).started);
    }
    
    public void testStart_Implementation() throws Exception {
		container.register(ShamComponent.class, BreaksStart.class);
		container.start();
		assertTrue(((BreaksStart)container.instance(ShamComponent.class)).called);
	}

    public void testStop() throws Exception {
        container.register(ShamStoppable.class);
        container.stop();
        assertFalse("Should not instantiate", container.instance(ShamStoppable.class).stopped);
        container.stop();
        assertTrue(container.instance(ShamStoppable.class).stopped);
    }
    
    public void testRegisterAll() throws Exception {
    	container.register(ShamStartable.class);
    	container.register(ShamStoppable.class);
    	container.register(ShamComponent.class);
    	
		SimpleContainer anotherContainer = new SimpleContainer();
		anotherContainer.register(ShamComponent.class, ShamSubclassingComponent.class);
		anotherContainer.register(ShamStartable.class, new ShamStartable());
		
		container.registerAll(anotherContainer);
		
		assertNotNull(container.instance(ShamStoppable.class));
		assertTrue(container.instance(ShamComponent.class) instanceof ShamSubclassingComponent);
		assertSame(anotherContainer.instance(ShamStartable.class), container.instance(ShamStartable.class));
	}
   
    public void testAllInstances() throws Exception {
		container.register(ShamComponent.class, new ShamComponent());
		container.register(ShamSubclassingComponent.class);
		assertEquals(1, container.allInstances(ShamComponent.class, false).size());
		assertEquals(2, container.allInstances(ShamComponent.class, true).size());
		assertEquals(2, container.allInstances(ShamComponent.class, false).size());
		
		assertEquals(1, container.allInstances(ShamSubclassingComponent.class, false).size());
	}
    
    public void testBroadcast() throws Exception {
		container.register(IShoelace.class, Shoelace.class);
		container.broadcast(IShoelace.class, false).tie();
		
		IShoelace shoelace = container.instance(IShoelace.class);
		assertFalse(shoelace.isTied());

		container.broadcast(IShoelace.class, false).tie();
		assertTrue(shoelace.isTied());

		container.register(IShoelace.class, Shoelace.class);
		container.broadcast(IShoelace.class, true).tie();
		IShoelace anotherShoelace = container.instance(IShoelace.class);
		assertTrue(anotherShoelace.isTied());
		assertNotSame(shoelace, anotherShoelace);
	}
    
    public void testInstantiationListeners() throws Exception {
		container.register(ShamComponent.class);
		listenerCalled = false;
		container.registerInstantiationListener(ShamComponent.class, new InstantiationListener<ShamComponent>() {
			public void instantiated(ShamComponent newInstance) {
				listenerCalled = true;
			}
		});
		
		container.instance(ShamComponent.class);
		assertTrue(listenerCalled);
		
		listenerCalled = false;
		container.instance(ShamComponent.class);
		assertFalse(listenerCalled);
		
		container.register(new ShamSubclassingComponent());
		container.registerInstantiationListener(ShamSubclassingComponent.class, new InstantiationListener<ShamSubclassingComponent>() {
			public void instantiated(ShamSubclassingComponent newInstance) {
				listenerCalled = true;
			}
		});
		container.instance(ShamSubclassingComponent.class);
		assertFalse(listenerCalled);
	}
    
    public void testBroadcast_ThreadSafety() throws Exception {
		container.register(Shoelace.class);
		container.register(ShamSubclassingComponent.class);
		container.registerInstantiationListener(Shoelace.class, new InstantiationListener<Shoelace>() {
			public void instantiated(Shoelace newInstance) {
				container.register(ShamComponent.class);
			}
		});
		container.broadcast(IShoelace.class, true).untie();
		assertNotNull(container.instance(ShamComponent.class));
	}
    
    public static interface IModifiesContainer {
    	void go(SimpleContainer container);
    }
    
    public static class ModifiesContainer implements IModifiesContainer {
    	public void go(SimpleContainer container) {
    		container.register(ShamComponent.class);
    	}
    }

    public static interface IShoelace {
    	void tie(); 
    	void untie();
    	boolean isTied();
    }
    
    public static class Shoelace implements IShoelace {
    	public boolean tied = false;
    	
    	public void tie() {
    		tied = true;
    	}

		public void untie() {
			tied = false;
		}
		
		public boolean isTied() {
			return tied;
		}
    }
    
    public static class BreaksStart extends ShamComponent implements Startable {
    	boolean called = false;
    	public void start() {
    		called = true;
    	}
    }
}
