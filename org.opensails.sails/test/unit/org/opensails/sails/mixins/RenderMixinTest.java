package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateBindingFixture;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.IBinding;

public class RenderMixinTest extends TestCase {
	IBinding bindingGiven;
	IBinding createdBinding;
	String templateIdentifierGiven;

	public void testPartial() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		IBinding binding = TemplateBindingFixture.create();
		RenderMixin render = new RenderMixin(event, binding, new ITemplateRenderer<IBinding>() {
			public IBinding createBinding(IBinding parent) {
				return createdBinding = TemplateBindingFixture.create();
			}

			public StringBuilder render(IUrl templateUrl, IBinding binding) {
				return null;
			}

			public StringBuilder render(String templateIdentifier, IBinding binding) {
				templateIdentifierGiven = templateIdentifier;
				bindingGiven = binding;
				return new StringBuilder();
			}

			public StringBuilder render(String templateIdentifier, IBinding binding, StringBuilder target) {
				return null;
			}

			public StringBuilder renderString(String templateContent, IBinding binding) {
				return null;
			}

			public boolean templateExists(String templateIdentifier) {
				return false;
			}
		});

		render.invoke("templateName").toString();
		assertEquals(event.getProcessorName() + "/templateName", templateIdentifierGiven);
		assertNotNull(bindingGiven);
		assertSame("Templates have the same binding", binding, bindingGiven);

		render.invoke().partial("components/myComponent/templateName").toString();
		assertEquals("components/myComponent/_templateName", templateIdentifierGiven);
		assertNotNull(bindingGiven);
		assertSame("Partials have a local binding", createdBinding, bindingGiven);
	}
}
