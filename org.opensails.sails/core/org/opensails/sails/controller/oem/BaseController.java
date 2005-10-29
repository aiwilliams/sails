package org.opensails.sails.controller.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.template.ITemplateBinding;

public class BaseController implements IController {
	protected ISailsEvent event;

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public void set(ISailsEvent event) {
		this.event = event;
	}

	protected void expose(String key, Object object) {
		getBinding().put(key, object);
	}

	protected String field(String name) {
		return event.getFieldValue(name);
	}

	protected String[] fields(String name) {
		return event.getFieldValues(name);
	}

	protected ITemplateBinding getBinding() {
		return getContainer().instance(ITemplateBinding.class);
	}

	protected void set(IActionResult result) {
		getContainer().register(IActionResult.class, result);
	}
}
