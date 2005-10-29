package org.opensails.sails.oem;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.oem.ActionResultFixture;
import org.opensails.sails.util.ClassResolverAdapter;

public class ActionResultProcessorResolverTest extends TestCase {
    int classResolveCount = 0;
    IActionResultProcessor instance = new ShamResultProcessor();

    public void testConstructor_WrongScope() throws Exception {
        try {
            new ActionResultProcessorResolver(new ScopedContainer(ApplicationScope.REQUEST));
            fail("These resolvers are Sails Application scoped (servlet), as I can't think of a reason you would want it otherwise");
        } catch (IllegalArgumentException expected) {}
    }

    public void testIt() throws Exception {
        ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(new ScopedContainer(ApplicationScope.SERVLET) {
            @Override
            @SuppressWarnings("unchecked")
            public <T> T instance(Class<T> key) {
                assertEquals(ShamResultProcessor.class, key);
                return (T) instance;
            }
        });
        resolver.push(new ClassResolverAdapter<IActionResultProcessor>() {
            @Override
            public Class<? extends IActionResultProcessor> resolve(Class key) {
                classResolveCount++;
                return ShamResultProcessor.class;
            }
        });
        assertSame(instance, resolver.resolve(ActionResultFixture.template()));
        assertEquals(1, classResolveCount);

        resolver.resolve(ActionResultFixture.template());
        assertEquals("Cache the resolved class", 1, classResolveCount);
    }

    public void testPush() throws Exception {
        ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(new ScopedContainer(ApplicationScope.SERVLET));
        ClassResolverAdapter<IActionResultProcessor> resolverOne = new ClassResolverAdapter<IActionResultProcessor>() {
            @Override
            public Class<? extends IActionResultProcessor> resolve(Class key) {
                throw new AssertionFailedError("This should not be used, as two is pushed on last");
            }
        };
        ClassResolverAdapter<IActionResultProcessor> resolverTwo = new ClassResolverAdapter<IActionResultProcessor>() {
            @Override
            public Class<? extends IActionResultProcessor> resolve(Class key) {
                return ShamActionResultProcessor.class;
            }
        };
        resolver.push(resolverOne);
        resolver.push(resolverTwo);
        resolver.resolve(ActionResultFixture.template());
    }
}
