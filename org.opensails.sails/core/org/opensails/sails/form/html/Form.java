package org.opensails.sails.form.html;

import org.opensails.sails.html.AbstractHtmlElement;
import org.opensails.sails.url.IUrl;

public class Form extends AbstractHtmlElement<Form> {

	public Form() {
		super("form");
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
