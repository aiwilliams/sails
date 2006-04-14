package org.opensails.sails.form.html;

import org.opensails.sails.url.IUrl;

public class Form extends FormElement<Form> {

	public Form(String name) {
		super("form", name);
		method("post");
	}

	public Form action(IUrl actionUrl) {
		return action(actionUrl.render());
	}

	public Form action(String actionUrl) {
		return attribute("action", actionUrl);
	}

	public Form method(String httpMethod) {
		return attribute("method", httpMethod);
	}

}
