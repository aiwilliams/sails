package org.opensails.sails.processors;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.oem.PartialActionResult;
import org.opensails.sails.helper.IMixinResolver;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;

public class PartialActionResultProcessor implements IActionResultProcessor<PartialActionResult> {
	protected final ITemplateRenderer renderer;

	public PartialActionResultProcessor(ITemplateRenderer renderer) {
		this.renderer = renderer;
	}

	@SuppressWarnings("unchecked")
	public void process(PartialActionResult result) {
		IMixinResolver resolver = result.getContainer().instance(IMixinResolver.class);
		ITemplateBinding binding = result.getBinding();
		binding.mixin(resolver);

		StringBuilder output = new StringBuilder();
		result.write(renderer.render(result.getIdentifier(), binding, output).toString());
	}
}
