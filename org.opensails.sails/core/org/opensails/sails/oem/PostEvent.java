package org.opensails.sails.oem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensails.sails.ISailsApplication;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.form.FormFields;

public class PostEvent extends AbstractEvent {
	public PostEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, req, resp);
	}

	@Override
	public void beginDispatch() {}

	@Override
	public void endDispatch() {}

	@Override
	public IActionResult visit(Controller controller) {
		return controller.process(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void containerSet() {
		super.containerSet();
		container.register(FormFields.class, new FormFields(req.getParameterMap()));
	}
}
