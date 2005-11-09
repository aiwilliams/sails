package org.opensails.sails.helper.oem;

import junit.framework.TestCase;

import org.opensails.sails.mixins.RenderMixin;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateBindingFixture;

public class RenderMixinTest extends TestCase {
	ITemplateBinding bindingGiven;
	ITemplateBinding createdBinding;
	String templateIdentifierGiven;

	public void testPartial() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		ITemplateBinding binding = TemplateBindingFixture.create();
		RenderMixin render = new RenderMixin(event, binding, new ITemplateRenderer<ITemplateBinding>() {
			public ITemplateBinding createBinding(ITemplateBinding parent) {
				return createdBinding = TemplateBindingFixture.create();
			}

			public StringBuilder render(String templateIdentifier, ITemplateBinding binding) {
				templateIdentifierGiven = templateIdentifier;
				bindingGiven = binding;
				return new StringBuilder();
			}

			public StringBuilder render(String templateIdentifier, ITemplateBinding binding, StringBuilder target) {
				return null;
			}

			public boolean templateExists(String templateIdentifier) {
				return false;
			}
		});

		render.invoke("templateName").toString();
		assertEquals(event.getControllerName() + "/templateName", templateIdentifierGiven);
		assertNotNull(bindingGiven);
		assertSame("Templates have the same binding", binding, bindingGiven);

		render.invoke().partial("myController/templateName").toString();
		assertEquals("myController/_templateName", templateIdentifierGiven);
		assertNotNull(bindingGiven);
		assertSame("Partials have a local binding", createdBinding, bindingGiven);
	}
}
