package org.opensails.sails.mixins;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.fixture.controllers.HomeController;
import org.opensails.sails.oem.AdapterResolver;
import org.opensails.sails.oem.ControllerResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.ComponentPackage;

public class RequireMixinTest extends TestCase {
	public void testIt() throws Exception {
		ISailsEvent event = SailsEventFixture.sham();
		TestingBinding binding = new TestingBinding();
		ControllerResolver controllerResolver = new ControllerResolver(new AdapterResolver());
		controllerResolver.push(new ComponentPackage<IControllerImpl>("org.opensails.sails.fixture.controllers", "Controller"));
		
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public InputStream resolve(String identifier) {
				if (identifier.equals("components/mycomponent/.component"))
					return new ByteArrayInputStream("one.js\ntwo.js\none.css".getBytes());
				if (identifier.equals("components/home/.component"))
					return new ByteArrayInputStream("".getBytes());
				return null;
			}
		}, binding, controllerResolver);
		
		String rootUrl = event.resolve(UrlType.CONTEXT, "components/mycomponent").render();
		String expected = "<script type=\"text/javascript\" src=\"" + rootUrl + "/one.js\"></script><script type=\"text/javascript\" src=\"" + rootUrl
				+ "/two.js\"></script><link href=\"" + rootUrl + "/one.css\" type=\"text/css\" rel=\"stylesheet\" />";
		require.component("mycomponent");
		require.component("mycomponent");
		assertEquals(expected, require.output());

		require.component("home");
		assertTrue(binding.call("home") instanceof HomeController);
	}

}
