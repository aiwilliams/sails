package org.opensails.sails.helper.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.html.Select;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.Text;

public class FormHelper {
	protected final ISailsEvent event;
	protected final IFormElementIdGenerator idGenerator;

	public FormHelper(ISailsEvent event, IFormElementIdGenerator idGenerator) {
		this.event = event;
		this.idGenerator = idGenerator;
	}

	public FormHelper(ISailsEvent event, IFormElementIdGenerator idGenerator, HtmlForm form) {
		this(event, idGenerator);
	}

	public String end() {
		return "</form>";
	}

	public Select select(String nameAttribute) {
		return new Select(nameAttribute, idGenerator.idForName(nameAttribute));
	}

	public String start() {
		return "<form method=\"post\">";
	}

	public Submit submit(String valueAttribute) {
		return new Submit(valueAttribute).value(valueAttribute);
	}

	public Text text(String name) {
		return new Text(name);
	}
}
