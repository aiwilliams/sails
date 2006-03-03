package org.opensails.sails.event.oem;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.ActionResultFixture;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.action.oem.ShamActionResultProcessor;
import org.opensails.sails.action.oem.ShamResultProcessor;
import org.opensails.sails.util.ClassResolverAdapter;

public class ActionResultProcessorResolverTest extends TestCase {
    int classResolveCount = 0;
    IActionResultProcessor instance = new ShamResultProcessor();

    public void testConstructor_WrongScope() throws Exception {
        try {
            new ActionResultProcessorResolver(new RequestContainer(new ApplicationContainer()));
            fail("These resolvers are Sails Application scoped (servlet), as I can't think of a reason you would want it otherwise");
        } catch (IllegalArgumentException expected) {}
    }

    public void testIt() throws Exception {
        ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(new ApplicationContainer() {
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
        ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(new ApplicationContainer());
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
