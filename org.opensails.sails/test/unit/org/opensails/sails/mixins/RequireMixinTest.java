package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.ComponentPackage;
import org.opensails.viento.IBinding;

public class RequireMixinTest extends TestCase {
	ComponentResolver componentResolver;

	ISailsEvent event;

	public void testIt() throws Exception {
		TestingBinding binding = new TestingBinding();
		RequireMixin require = new RequireMixin(event, new ResourceResolver() {
			@Override
			public boolean exists(IUrl applicationUrl) {
				if (applicationUrl.render().contains("script")) return true;
				return false;
			}
		}, binding, componentResolver);

		String expected = "<script type=\"text/javascript\" src=\".*?/script.js\"></script>";
		require.component("mycomponent");
		require.component("mycomponent");
		assertTrue(require.output().toString().trim().matches(expected));
	}

	@Override
	protected void setUp() throws Exception {
		event = SailsEventFixture.sham();
		componentResolver = new ComponentResolver(new AdapterResolver(), new ShamTemplateRenderer());
		componentResolver.push(new ComponentPackage<IComponentImpl>("org.opensails.sails.fixture.controllers", "Component"));
	}

	String componentRootUrl() {
		return event.resolve(UrlType.CONTEXT, "components/mycomponent").render();
	}

	public class ShamTemplateRenderer implements ITemplateRenderer {
		public IBinding createBinding(IBinding parent) {
			return null;
		}

		public StringBuilder render(String templateIdentifier, IBinding binding) {
			return null;
		}

		public StringBuilder render(String templateIdentifier, IBinding binding, StringBuilder target) {
			return null;
		}

		public boolean templateExists(String templateIdentifier) {
			return false;
		}
	}
}
