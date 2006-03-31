package org.opensails.sails.mixins;

import java.io.IOException;
import java.util.Collection;

import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.html.Checkbox;
import org.opensails.sails.form.html.FileInput;
import org.opensails.sails.form.html.Hidden;
import org.opensails.sails.form.html.Label;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.form.html.Password;
import org.opensails.sails.form.html.Radio;
import org.opensails.sails.form.html.Select;
import org.opensails.sails.form.html.SelectModel;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.Text;
import org.opensails.sails.form.html.Textarea;
import org.opensails.sails.util.BleedingEdgeException;

public class FormMixin {
	protected IFormElementIdGenerator idGenerator;
	protected HtmlForm form;
	protected ContainerAdapterResolver adapterResolver;

	/**
	 * @param idGenerator
	 * @param adapterResolver used in
	 *        {@link org.opensails.sails.form.html.FormElement}s that accept
	 *        and adapt parameters
	 * @param form
	 */
	public FormMixin(IFormElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver, HtmlForm form) {
		this.idGenerator = idGenerator;
		this.adapterResolver = adapterResolver;
		this.form = form;
	}

	public Checkbox checkbox(String propertyPath) {
		Object modelValue = form.value(propertyPath);
		return new Checkbox(propertyPath).checked(Boolean.valueOf((String) modelValue));
	}

	public Checkbox checkbox(String propertyPath, String value) {
		Object modelValue = form.value(propertyPath);
		boolean checked = value.equals(modelValue);
		return new Checkbox(propertyPath).value(value).id(idGenerator.idForNameValue(propertyPath, value)).checked(checked);
	}

	public String end() {
		return "</form>";
	}

	public FileInput file(String name) {
		return new FileInput(name).value(form.value(name));
	}

	public String getErrorMessageOn(String name, String propertyPath) {
		throw new BleedingEdgeException("implement: delegate to ValidationContext");
	}

	public String getErrorMessagesFor(String name) throws IOException {
		throw new BleedingEdgeException("implement");
		// StringWriter writer = new StringWriter();
		// HtmlGenerator messages = new HtmlGenerator(writer);
		// IValidationEntry entry = validationContext.getEntry(name);
		// if (!entry.getFailures().isEmpty()) {
		// messages.openTag("div", "errorExplanation");
		// messages.classAttribute("errorExplanation");
		// messages.closeTag();
		//
		// messages.beginTag("h2");
		// messages.write(Inflector.pluralize(entry.getFailures().size(),
		// "error"));
		// messages.write(" prohibited this ");
		// messages.write(SpyGlass.getName(entry.getModel().getClass()));
		// messages.write(" from being saved");
		// messages.endTag("h2");
		//
		// messages.beginTag("ul");
		// for (IValidationFailure failure : entry.getFailures())
		// messages.tag("li", failure.getMessage());
		// messages.endTag("ul");
		//
		// messages.endTag("div");
		// }
		// return writer.toString();
	}

	public Hidden hidden(String name) {
		return new Hidden(name).value(form.value(name));
	}

	public String idFor(String name, String value) {
		return idGenerator.idForNameValue(name, value);
	}

	public Label label(String forId, String text) {
		return new Label(forId).text(text);
	}

	public Password password(String name) {
		// It's probably a security error to return the value in the response.
		return new Password(name);
	}

	public Radio radio(String name) {
		return new Radio(name, name);
	}

	/**
	 * If you call one of the {@link Select#model()} methods after creating this
	 * Select, the value in the model, as obtained through the HtmlForm, will no
	 * longer be selected. You may want to use the
	 * {@link #select(String, SelectModel)} helper instead.
	 * 
	 * @param name
	 * @return a new Select
	 */
	public Select select(String name) {
		return new Select(name).id(idGenerator.idForName(name)).selected(form.value(name));
	}

	public Select select(String name, Collection<? extends Object> model) {
		return select(name, new ListSelectModel(model));
	}

	public Select select(String name, SelectModel model) {
		return new Select(name).id(idGenerator.idForName(name)).model(model).selected(form.value(name));
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
		return new Text(name).value(form.value(name));
	}

	public Textarea textarea(String name) {
		return new Textarea(name).id(idGenerator.idForName(name)).value(form.value(name));
	}
}
