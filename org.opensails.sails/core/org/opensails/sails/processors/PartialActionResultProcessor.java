package org.opensails.sails.processors;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.oem.PartialActionResult;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;

public class PartialActionResultProcessor implements IActionResultProcessor<PartialActionResult> {
	protected final ITemplateRenderer renderer;

	public PartialActionResultProcessor(ITemplateRenderer renderer) {
		this.renderer = renderer;
	}

	@SuppressWarnings("unchecked")
	public void process(PartialActionResult result) {
		IHelperResolver helperResolver = result.getContainer().instance(IHelperResolver.class);
		ITemplateBinding binding = result.getBinding();
		binding.mixin(helperResolver);

		StringBuilder output = new StringBuilder();
		result.write(renderer.render(result.getIdentifier(), binding, output).toString());
	}
}
