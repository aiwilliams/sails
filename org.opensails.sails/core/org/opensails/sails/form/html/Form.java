package org.opensails.sails.form.html;

import org.opensails.sails.html.AbstractHtmlElement;
import org.opensails.sails.url.IUrl;

public class Form extends AbstractHtmlElement<Form> {

	public Form() {
		super("form");
	}

	public Form actionUrl(IUrl actionUrl) {
		return attribute("action", actionUrl.render());
	}

}
