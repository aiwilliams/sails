package org.opensails.sails.processors;

import org.opensails.rigging.*;
import org.opensails.sails.action.*;
import org.opensails.sails.action.oem.*;
import org.opensails.sails.event.*;
import org.opensails.sails.template.*;
import org.opensails.viento.*;

public class TemplateActionResultProcessor implements IActionResultProcessor<TemplateActionResult> {
	protected final ITemplateRenderer renderer;

	public TemplateActionResultProcessor(ITemplateRenderer renderer) {
		this.renderer = renderer;
	}

	@SuppressWarnings("unchecked")
	public void process(TemplateActionResult result) {
		IBinding binding = result.getBinding();

		IEventProcessingContext processingContext = result.getProcessingContext();
		if (processingContext != null) binding.mixin(processingContext);

		ScopedContainer container = result.getContainer();
		IMixinResolver resolver = container.instance(IMixinResolver.class);
		binding.mixin(resolver);

		StringBuilder content = new StringBuilder();
		try {
			renderer.render(result.getIdentifier(), binding, content);
		} catch (Exception e) {
			throw new TemplateRenderFailedException(result.getIdentifier(), e);
		}
		if (result.hasLayout()) {
			binding.put("contentForLayout", content);
			content = new StringBuilder();
			try {
				renderer.render(result.getLayout(), binding, content);
			} catch (Exception e) {
				throw new TemplateRenderFailedException(result.getLayout(), e);
			}
		}
		result.write(content.toString());
	}
}
