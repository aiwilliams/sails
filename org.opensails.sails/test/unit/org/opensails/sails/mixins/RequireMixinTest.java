package org.opensails.sails.mixins;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.controller.oem.MissingComponentDependenciesException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.ComponentPackage;

public class RequireMixinTest extends TestCase {
	ControllerResolver controllerResolver;
	ISailsEvent event;

	public void testComponent_MissingDependenciesFile() {
		TestingBinding binding = new TestingBinding();
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public InputStream resolve(String identifier) {
				return null;
			}
		}, binding, controllerResolver);
		try {
			require.component("somecomponent");
			fail("Let the developer know as soon as possible that there is no component for the name they declared");
		} catch (MissingComponentDependenciesException expected) {}
	}

	public void testIt() throws Exception {
		TestingBinding binding = new TestingBinding();
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public InputStream resolve(String identifier) {
				if (identifier.equals("components/mycomponent/.component")) return new ByteArrayInputStream("one.js\ntwo.js\none.css\n/from/context.js".getBytes());
				if (identifier.equals("components/home/.component")) return new ByteArrayInputStream("".getBytes());
				return null;
			}
		}, binding, controllerResolver);

		String expected = "<script type=\"text/javascript\" src=\"" + componentRootUrl() + "/one.js\"></script><script type=\"text/javascript\" src=\"" + componentRootUrl()
				+ "/two.js\"></script><link href=\"" + componentRootUrl()
				+ "/one.css\" type=\"text/css\" rel=\"stylesheet\" /><script type=\"text/javascript\" src=\"/shamcontext/from/context.js\"></script>";
		require.component("mycomponent");
		require.component("mycomponent");
		assertEquals(expected, require.output());

		require.component("home");
		assertTrue(binding.call("home") instanceof HomeController);
	}

	@Override
	protected void setUp() throws Exception {
		event = SailsEventFixture.sham();
		controllerResolver = new ControllerResolver(new AdapterResolver());
		controllerResolver.push(new ComponentPackage<IControllerImpl>("org.opensails.sails.fixture.controllers", "Controller"));
	}

	String componentRootUrl() {
		return event.resolve(UrlType.CONTEXT, "components/mycomponent").render();
	}
}
