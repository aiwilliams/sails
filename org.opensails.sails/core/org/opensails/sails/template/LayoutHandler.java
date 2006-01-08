package org.opensails.sails.template;

import org.opensails.sails.action.oem.ActionInvokation;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.oem.BehaviorHandlerAdapter;

public class LayoutHandler extends BehaviorHandlerAdapter<Layout> {
	protected BehaviorInstance<Layout> layout;

	public boolean add(BehaviorInstance<Layout> instance) {
		if (layout == null || layout.getElementType().compareTo(instance.getElementType()) > 0) layout = instance;
		return false;
	}

	@Override
	public boolean beforeAction(ActionInvokation invokation) {
		TemplateActionResult actionResult = new TemplateActionResult(invokation.event);
		actionResult.setLayout(layout.getAnnotation().value());
		invokation.setResult(actionResult);
		return true;
	}
}
