package org.opensails.dock.controllers;

import org.opensails.dock.model.User;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.controller.oem.TemplateActionResult;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.form.html.SelectModel;
import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.oem.SingleModelContext;

public class FormController extends BaseController {
	public void basicPost(User user) {
		if (!formToModel(user)) render("basic");
	}

	public SelectModel exampleSelectModel() {
		return new ListSelectModel("option one", "option two");
	}

	protected boolean formToModel(User user) {
		ScopedContainer container = event.getContainer();
		container.register(IModelContext.class, new SingleModelContext(user));
		HtmlForm form = container.instance(HtmlForm.class, HtmlForm.class);
		return form.isValid();
	}

	/**
	 * Renders the named template. Uses the Controller of the currentEvent in
	 * the templateIdentifier.
	 * 
	 * @param template
	 */
	protected void render(String template) {
		getContainer().register(IActionResult.class, new TemplateActionResult(event, template));
	}
}
