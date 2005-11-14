package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateBinding;

public class BaseConfiguratorTest extends TestCase {
	public void testConfigureISailsEventScopedContainer() {
		BaseConfigurator configurator = new BaseConfigurator();
		GetEvent event = SailsEventFixture.actionGet();
		RequestContainer eventContainer = event.getContainer();
		configurator.configure(event, eventContainer);

		assertSame("The event container is needed by various components (i.e. HtmlForm)", eventContainer, eventContainer.instance(ScopedContainer.class));
		assertNotNull(eventContainer.instance(IMixinResolver.class));
		assertNotNull(eventContainer.instance(ITemplateBinding.class));
	}

}
