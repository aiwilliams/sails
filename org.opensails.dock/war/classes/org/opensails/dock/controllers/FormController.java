package org.opensails.dock.controllers;

import org.opensails.dock.model.User;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.form.html.SelectModel;
import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.oem.SingleModelContext;

public class FormController extends BaseController {
	public void actionOne() {
		renderString("actionOne() invoked");
	}

	public void basicPost(User user) {
		if (!formToModel(user)) renderTemplate("basic");
	}

	public SelectModel exampleSelectModel() {
		return new ListSelectModel("option one", "option two");
	}

	protected boolean formToModel(User user) {
		RequestContainer container = event.getContainer();
		container.register(IModelContext.class, new SingleModelContext(user));
		HtmlForm form = container.instance(HtmlForm.class, HtmlForm.class);
		return form.isValid();
	}
}
