package org.opensails.sails.processors;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.controller.oem.TemplateActionResult;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;

public class TemplateActionResultProcessor implements IActionResultProcessor<TemplateActionResult> {
	protected final ITemplateRenderer renderer;

	public TemplateActionResultProcessor(ITemplateRenderer renderer) {
		this.renderer = renderer;
	}

	@SuppressWarnings("unchecked")
	public void process(TemplateActionResult result) {
		ITemplateBinding binding = result.getBinding();
		binding.mixin(result.getController());
		IHelperResolver helperResolver = result.getContainer().instance(IHelperResolver.class);
		binding.mixin(helperResolver);

		StringBuilder output = new StringBuilder();
		result.write(renderer.render(result.getIdentifier(), binding, output).toString());
	}
}
