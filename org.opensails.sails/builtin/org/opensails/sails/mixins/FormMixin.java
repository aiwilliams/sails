package org.opensails.sails.mixins;

import java.util.Collection;

import org.opensails.sails.SailsException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.ValidationErrors;
import org.opensails.sails.form.html.Checkbox;
import org.opensails.sails.form.html.FileInput;
import org.opensails.sails.form.html.Form;
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
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.util.Quick;
import org.opensails.spyglass.SpyObject;
import org.opensails.viento.MethodMissing;

public class FormMixin implements MethodMissing {
	protected IFormElementIdGenerator idGenerator;
	protected HtmlForm form;
	protected ContainerAdapterResolver adapterResolver;
	protected ISailsEvent event;

	/**
	 * @param idGenerator
	 * @param adapterResolver used in
	 *        {@link org.opensails.sails.form.html.FormElement}s that accept
	 *        and adapt parameters
	 * @param form
	 */
	public FormMixin(ISailsEvent event, IFormElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver, HtmlForm form) {
		this.event = event;
		this.idGenerator = idGenerator;
		this.adapterResolver = adapterResolver;
		this.form = form;
	}

	public Form action(String action) {
		return actionUrl(new ActionUrl(event, action));
	}

	public Form action(String controller, String action) {
		return actionUrl(new ActionUrl(event, controller, action));
	}

	public Form actionUrl(IUrl actionUrl) {
		return new Form().actionUrl(actionUrl);
	}

	public Checkbox checkbox(String propertyPath) {
		Object modelValue = form.value(propertyPath);
		return new Checkbox(propertyPath).checked(Boolean.valueOf((String) modelValue));
	}

	@SuppressWarnings("unchecked")
	public Checkbox checkbox(String propertyPath, Object checkedValue) {
		try {
			IAdapter valueAdapter = adapterResolver.resolve(checkedValue.getClass());
			Object modelValue = form.value(propertyPath);
			String checkedValueString = (String) valueAdapter.forWeb(new AdaptationTarget<Object>((Class<Object>) checkedValue.getClass()), checkedValue);
			boolean checked = false;
			if (modelValue instanceof String) checked = checkedValueString.equals(modelValue);
			else if (modelValue instanceof String[]) {
				checked = Quick.list((String[]) modelValue).contains(checkedValueString);
			}
			return new Checkbox(propertyPath).value(checkedValue).id(idGenerator.idForNameValue(propertyPath, checkedValueString)).checked(checked);
		} catch (ClassCastException e) {
			throw new SailsException("A checkbox value must resolve to a String");
		}
	}

	public String end() {
		return "</form>";
	}

	public ValidationErrors errorsFor(String modelName) {
		ValidationErrors errorsFor = form.getValidationContext().errorsFor(modelName);
		return errorsFor.isEmpty() ? ValidationErrors.NULL : errorsFor;
	}

	public FileInput file(String name) {
		return new FileInput(name).value(form.value(name));
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

	public Object methodMissing(String methodName, Object[] args) {
		return new SpyObject<HtmlForm>(form).invoke(methodName, args);
	}

	public Password password(String name) {
		// It's probably a security error to return the value in the response.
		return new Password(name);
	}

	@SuppressWarnings("unchecked")
	public Radio radio(String name, Object value) {
		try {
			Object modelValue = form.value(name);
			String stringValue = (String) adapterResolver.resolve(value.getClass()).forWeb(new AdaptationTarget<Object>((Class<Object>) value.getClass()), value);
			boolean checked = stringValue.equals(modelValue);
			return new Radio(name, stringValue, idGenerator.idForNameValue(name, stringValue)).checked(checked);
		} catch (ClassCastException e) {
			throw new SailsException("A checkbox value must resolve to a String");
		}
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

	public Form start() {
		return new Form();
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
