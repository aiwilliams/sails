package org.opensails.component.tester;

import junit.framework.TestCase;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.template.Require;
import org.opensails.sails.util.ClassHelper;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class SailsComponentTesterTest extends TestCase {
    public void testIt() throws Exception {
        SailsComponentTester t = new SailsComponentTester(Configurator.class);
        TestComponent<Component> c = t.component(Component.class);
        IEventContextContainer requestContainer = c.getRequestContainer();
        assertNotNull(requestContainer.getParent());
        Require registeredBefore = requestContainer.instance(Require.class);
        c.initialize();
        assertNotNull("Request remains connected to parent until result processing occurs", requestContainer.getParent());
        assertSame(registeredBefore, c.getRequestContainer().instance(Require.class));
        c.render();
        assertNull("Request container should be orphaned after render to avoid memory leak", requestContainer.getParent());
        assertSame(registeredBefore, c.getRequestContainer().instance(Require.class));
    }

    public static class Configurator extends BaseConfigurator {
        @Override
        public void configure(ComponentResolver componentResolver) {
            componentResolver.push(new PackageClassResolver<IComponentImpl>(ClassHelper.getPackage(Component.class)));
        }
    }
}
