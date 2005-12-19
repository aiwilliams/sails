package org.opensails.sails.template;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.IBehaviorHandler;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.oem.IActionEventProcessor;

public class LayoutHandler implements IBehaviorHandler<Layout> {
	protected BehaviorInstance<Layout> layout;

	public void add(BehaviorInstance<Layout> instance) {
		if (layout == null || layout.getElementType().compareTo(instance.getElementType()) > 0) layout = instance;
	}

	public void afterAction(IEventProcessingContext<? extends IActionEventProcessor> context) {}

	public void beforeAction(IEventProcessingContext<? extends IActionEventProcessor> context) {
		TemplateActionResult actionResult = new TemplateActionResult(context.getEvent());
		actionResult.setLayout(layout.getAnnotation().value());
		context.setResult(actionResult);
	}
}
