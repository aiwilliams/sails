package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.viento.IBinding;

public class BaseConfiguratorTest extends TestCase {
    public void testConfigure() {
        BaseConfigurator configurator = new BaseConfigurator();
        SailsApplication sailsApplication = (SailsApplication) SailsApplicationFixture.configured(configurator);
        assertSame(configurator, sailsApplication.configurator);
    }

    public void testConfigureISailsEventScopedContainer() {
        BaseConfigurator configurator = new BaseConfigurator();
        GetEvent event = SailsEventFixture.actionGet();
        IEventContextContainer eventContainer = event.getContainer();
        configurator.configure(event, eventContainer);

        assertSame("The event container is needed by various components (i.e. HtmlForm)", eventContainer, eventContainer.instance(IScopedContainer.class));
        assertSame("The event container is needed by various components (i.e. HtmlForm)", eventContainer, eventContainer.instance(IEventContextContainer.class));
        assertNotNull(eventContainer.instance(IMixinResolver.class));
        assertNotNull(eventContainer.instance(IBinding.class));
    }
}
