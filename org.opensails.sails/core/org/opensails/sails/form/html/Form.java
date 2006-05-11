package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.html.HtmlGenerator;
import org.opensails.sails.url.IUrl;

public class Form extends FormElement<Form> {

	public Form(String name) {
		super("form", name);
		method("post");
	}

	public Form action(IUrl actionUrl) {
		return action(actionUrl.renderThyself());
	}

	public Form action(String actionUrl) {
		return attribute("action", actionUrl);
	}

	public Form method(String httpMethod) {
		return attribute("method", httpMethod);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	@Override
	protected void renderEndTag(HtmlGenerator generator) throws IOException {
	// no end tag
	}
}
