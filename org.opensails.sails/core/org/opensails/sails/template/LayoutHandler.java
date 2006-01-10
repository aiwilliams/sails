package org.opensails.sails.template;

import java.lang.annotation.Annotation;

import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.oem.BehaviorHandlerAdapter;

public class LayoutHandler extends BehaviorHandlerAdapter {
	protected BehaviorInstance<?> layout;

	@SuppressWarnings("unchecked")
	public boolean add(BehaviorInstance instance) {
		if (layout == null || layout.getElementType().compareTo(instance.getElementType()) > 0) layout = instance;
		return false;
	}

	@Override
	public boolean beforeAction(ActionInvocation invocation) {
		String finalLayout = null;
		Class<? extends Annotation> type = layout.getAnnotation().annotationType();
		if (type == Layout.class) finalLayout = ((Layout) layout.getAnnotation()).value();
		else if (type != NoLayout.class) throw new IllegalArgumentException(String.format("%s only understands the layout annotations %s, %s", LayoutHandler.class, Layout.class, NoLayout.class));

		TemplateActionResult actionResult = new TemplateActionResult(invocation.event);
		actionResult.setLayout(finalLayout);
		invocation.setResult(actionResult);
		return true;
	}
}
