package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class RequireMixinTest extends TestCase {
	ControllerResolver controllerResolver;
	ISailsEvent event;

	public void testIt() throws Exception {
		TestingBinding binding = new TestingBinding();
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public boolean exists(IUrl applicationUrl) {
				if (applicationUrl.render().contains("script")) return true;
				return false;
			}
		}, binding, controllerResolver);

		String expected = "<script type=\"text/javascript\" src=\".*?/script.js\"></script>";
		require.component("mycomponent");
		require.component("mycomponent");
		assertTrue(require.output().toString().trim().matches(expected));

		require.component("home");
		assertTrue(binding.call("home") instanceof HomeController);
	}

	@Override
	protected void setUp() throws Exception {
		event = SailsEventFixture.sham();
		controllerResolver = new ControllerResolver(new AdapterResolver());
		controllerResolver.push(new ControllerPackage("org.opensails.sails.fixture.controllers"));
	}

	String componentRootUrl() {
		return event.resolve(UrlType.CONTEXT, "components/mycomponent").render();
	}
}
