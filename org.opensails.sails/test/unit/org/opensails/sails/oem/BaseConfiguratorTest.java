package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateBinding;

public class BaseConfiguratorTest extends TestCase {
	/*
	 * Test method for
	 * 'org.opensails.sails.oem.BaseConfigurator.configure(ISailsEvent,
	 * ScopedContainer)'
	 */
	public void testConfigureISailsEventScopedContainer() {
		BaseConfigurator configurator = new BaseConfigurator();
		GetEvent event = SailsEventFixture.actionGet();
		ScopedContainer eventContainer = event.getContainer();
		configurator.configure(event, eventContainer);

		assertSame("The event container is needed by various components (i.e. HtmlForm)", eventContainer, eventContainer.instance(ScopedContainer.class));
		assertNotNull(eventContainer.instance(IMixinResolver.class));
		assertNotNull(eventContainer.instance(ITemplateBinding.class));
	}

}
