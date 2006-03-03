package org.opensails.sails.processors;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateRenderFailedException;
import org.opensails.viento.IBinding;

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

        IEventContextContainer container = result.getContainer();
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
