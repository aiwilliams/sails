package org.opensails.sails.processors;

import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.PartialActionResult;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;

public class PartialActionResultProcessor implements IActionResultProcessor<PartialActionResult> {
	protected final ITemplateRenderer renderer;

	public PartialActionResultProcessor(ITemplateRenderer renderer) {
		this.renderer = renderer;
	}

	@SuppressWarnings("unchecked")
	public void process(PartialActionResult result) {
		IMixinResolver resolver = result.getContainer().instance(IMixinResolver.class);
		IBinding binding = result.getBinding();
		binding.mixin(resolver);

		StringBuilder output = new StringBuilder();
		result.write(renderer.render(result.getIdentifier(), binding, output).toString());
	}
}
