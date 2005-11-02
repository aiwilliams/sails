package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.sails.util.ClassResolverAdapter;

public class ControllerResolverTest extends TestCase {
    protected int resolutionCount = 0;
    ControllerResolver resolver;
    ScopedContainer container;

    @Override
    protected void setUp() throws Exception {
        container = new ScopedContainer(ApplicationScope.SERVLET);
        container.register(IAdapterResolver.class, new IAdapterResolver() {
            public IAdapter resolve(Class<?> targetType, ScopedContainer container) {
                return null;
            }
        });

        resolver = new ControllerResolver(new AdapterResolver());
        resolver.push(new ClassResolverAdapter<IControllerImpl>() {
            @Override
            public Class<? extends IControllerImpl> resolve(String key) {
                resolutionCount++;
                return ShamController.class;
            }
        });
    }

    public void testResolve() throws Exception {
        Controller controller = resolver.resolve("sham");
        assertEquals(ShamController.class, controller.getImplementation());
        assertEquals(1, resolutionCount);
        resolver.resolve("sham");
        assertEquals("Should be cached", 1, resolutionCount);
    }

    public void testResolve_HandlesNull() throws Exception {
        ControllerResolver resolver = new ControllerResolver(new AdapterResolver());
        resolver.push(new ClassResolverAdapter<IControllerImpl>() {
            @Override
            public Class<? extends IControllerImpl> resolve(String key) {
                return null;
            }
        });
        Controller controller = resolver.resolve("sham");
        assertNotNull(controller);
        assertNull(controller.getImplementation());
    }
}
