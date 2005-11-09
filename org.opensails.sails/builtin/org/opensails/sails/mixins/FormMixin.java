package org.opensails.sails.mixins;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.html.Checkbox;
import org.opensails.sails.form.html.Radio;
import org.opensails.sails.form.html.Select;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.Text;
import org.opensails.sails.form.html.TextArea;

public class FormMixin {
	protected final ContainerAdapterResolver adapterResolver;
	protected final ISailsEvent event;
	protected final IFormElementIdGenerator idGenerator;

	/**
	 * @param event
	 * @param idGenerator
	 * @param adapterResolver used in
	 *        {@link org.opensails.sails.form.html.FormElement}s that accept
	 *        and adapt parameters
	 */
	public FormMixin(ISailsEvent event, IFormElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver) {
		this.event = event;
		this.idGenerator = idGenerator;
		this.adapterResolver = adapterResolver;
	}

	public FormMixin(ISailsEvent event, IFormElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver, HtmlForm form) {
		this(event, idGenerator, adapterResolver);
	}

	public Checkbox checkbox(String name) {
		return new Checkbox(name);
	}

	public String end() {
		return "</form>";
	}

	public Radio radio(String name) {
		return new Radio(name, name, idGenerator.idForName(name));
	}

	public Select select(String name) {
		return new Select(name, idGenerator.idForName(name));
	}

	public String start() {
		return "<form method=\"post\">";
	}

	public Submit submit(String valueAttribute) {
		return new Submit(valueAttribute, adapterResolver).value(valueAttribute);
	}

	public Text text(String name) {
		return new Text(name);
	}

	public TextArea textarea(String name) {
		return new TextArea(name, idGenerator.idForName(name));
	}
}
