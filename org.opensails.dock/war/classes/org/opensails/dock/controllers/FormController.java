package org.opensails.dock.controllers;

import org.opensails.dock.model.User;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.form.html.SelectModel;

public class FormController extends BaseController {
	public void actionOne() {
		renderString("actionOne() invoked");
	}

	public void basicPost(User user) {
		if (!updateModel(user)) renderTemplate("basic");
	}

	public SelectModel exampleSelectModel() {
		return new ListSelectModel("option one", "option two");
	}

	public void multipartPost() {
		expose("simpleText", String.format("You entered [%s]", field("simpleText")));
		expose("someFile", file("someFile"));
	}
}
