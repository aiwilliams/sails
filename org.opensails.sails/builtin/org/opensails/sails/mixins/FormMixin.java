package org.opensails.sails.mixins;

import org.opensails.rigging.WhenNotInstantiated;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.IFormValueModel;
import org.opensails.sails.form.NullFormValueModel;
import org.opensails.sails.form.html.Checkbox;
import org.opensails.sails.form.html.FormElement;
import org.opensails.sails.form.html.Hidden;
import org.opensails.sails.form.html.Label;
import org.opensails.sails.form.html.Password;
import org.opensails.sails.form.html.Radio;
import org.opensails.sails.form.html.Select;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.Text;
import org.opensails.sails.form.html.TextArea;

public class FormMixin {
	protected final ContainerAdapterResolver adapterResolver;
	protected final ISailsEvent event;
	protected final IFormElementIdGenerator idGenerator;
	protected final IFormValueModel valueModel;

	/**
	 * @param event
	 * @param idGenerator
	 * @param adapterResolver used in
	 *        {@link org.opensails.sails.form.html.FormElement}s that accept
	 *        and adapt parameters
	 */
	public FormMixin(ISailsEvent event, IFormElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver, @WhenNotInstantiated(NullFormValueModel.class)
	IFormValueModel valueModel) {
		this.event = event;
		this.idGenerator = idGenerator;
		this.adapterResolver = adapterResolver;
		this.valueModel = valueModel;
	}

	public Checkbox checkbox(String name) {
		Object object = valueModel.value(name);
		if (object == null || object.getClass() == String[].class) return new Checkbox(name, (String[]) object);
		else return new Checkbox(name, new String[] { (String) object });
	}

	public String end() {
		return "</form>";
	}

	public FileInput file(String name) {
		return new FileInput(name).value(valueModel.value(name));
	}

	public Hidden hidden(String name) {
		return new Hidden(name);
	}

	public String idFor(String name, String value) {
		return FormElement.idForNameAndValue(name, value);
	}

	public Label label(String forId, String text) {
		return new Label(forId).text(text);
	}

	public Radio radio(String name) {
		return new Radio(name, name);
	}

	public Password password(String name) {
		// It's probably a security error to return the value in the response.
		return new Password(name); // .value(valueModel.value(name));
	}

	public Select select(String name) {
		return new Select(name).id(idGenerator.idForName(name));
	}

	public String start() {
		return "<form method=\"post\">";
	}

	public Submit submit() {
		return submit("Submit");
	}

	public Submit submit(String valueAttribute) {
		return new Submit(valueAttribute, adapterResolver).value(valueAttribute);
	}

	public Text text(String name) {
		return new Text(name).value(valueModel.value(name));
	}

	public TextArea textarea(String name) {
		return new TextArea(name).id(idGenerator.idForName(name)).value(valueModel.value(name));
	}
}
