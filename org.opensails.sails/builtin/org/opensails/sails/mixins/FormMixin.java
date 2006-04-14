package org.opensails.sails.mixins;

import java.util.Collection;
import java.util.List;

import org.opensails.sails.SailsException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.ValidationErrors;
import org.opensails.sails.form.html.Checkbox;
import org.opensails.sails.form.html.FileInput;
import org.opensails.sails.form.html.Form;
import org.opensails.sails.form.html.Hidden;
import org.opensails.sails.form.html.ISelectModel;
import org.opensails.sails.form.html.Label;
import org.opensails.sails.form.html.ListSelectModel;
import org.opensails.sails.form.html.Password;
import org.opensails.sails.form.html.Radio;
import org.opensails.sails.form.html.Select;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.Text;
import org.opensails.sails.form.html.Textarea;
import org.opensails.sails.html.IElementIdGenerator;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;
import org.opensails.sails.util.Quick;
import org.opensails.spyglass.SpyObject;
import org.opensails.viento.MethodMissing;

public class FormMixin implements MethodMissing /* , IMixinMethod<Form> */{
	protected IElementIdGenerator idGenerator;
	protected HtmlForm form;
	protected ContainerAdapterResolver adapterResolver;
	protected ISailsEvent event;
	protected List<String> ids;

	/**
	 * @param idGenerator
	 * @param adapterResolver used in
	 *        {@link org.opensails.sails.form.html.FormElement}s that accept
	 *        and adapt parameters
	 * @param form
	 */
	public FormMixin(ISailsEvent event, IElementIdGenerator idGenerator, ContainerAdapterResolver adapterResolver, HtmlForm form) {
		this.event = event;
		this.idGenerator = idGenerator;
		this.adapterResolver = adapterResolver;
		this.form = form;
	}

	public Form action(IUrl actionUrl) {
		return new Form("unnamed").action(actionUrl);
	}

	public Form action(String action) {
		return action(new ActionUrl(event, action));
	}

	public Form action(String controller, String action) {
		return action(new ActionUrl(event, controller, action));
	}

	/**
	 * Creates a checkbox having name and the value of '1', indicating checked.
	 * <p>
	 * So, the id is generated from the provided name. If you would like the id
	 * to be generated from a value, you must provide that when creating your
	 * checkbox. That is, see {@link #checkbox(String, Object)}.
	 * 
	 * @param name
	 * @return a checkbox having name and checked if model property is true or 1
	 */
	public Checkbox checkbox(String name) {
		Object modelValue = form.value(name);
		return new Checkbox(name).checked(Boolean.valueOf((String) modelValue)).id(idGenerator.idForName(name));
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
		return new FileInput(name).value(form.value(name)).id(idGenerator.idForName(name));
	}

	/**
	 * Creates a hidden having name.
	 * <p>
	 * This id is not set as manipulating them in JavaScript is not usually
	 * needed. If you want one, just call {@link Hidden#id(String)}.
	 * 
	 * @param name
	 * @return a hidden having name and it's value in the model
	 */
	public Hidden hidden(String name) {
		return new Hidden(name).value(form.value(name));
	}

	/**
	 * @param name
	 * @param value
	 * @return a unique id for name and value
	 */
	public String idFor(String name, String value) {
		return idGenerator.idForNameValue(name, value);
	}

	// For supporting $form('name', 'id'), but not until l8r
	// public Form invoke(Object... args) {
	// if (args == null || args.length == 0) return start();
	// if (args.length == 1 & args[0].getClass() == String.class) return new
	// Form((String) args[0]);
	// else if (args.length == 2 & args[0].getClass() == String.class &
	// args[1].getClass() == String.class) return new Form((String)
	// args[0]).id((String) args[1]);
	// throw new IllegalArgumentException("You may construct a form using $form,
	// $form('name'), $form('name', 'value'), or $form.start");
	// }

	public Label label(String forId, String text) {
		return new Label(forId).text(text);
	}

	public Object methodMissing(String methodName, Object[] args) {
		return new SpyObject<HtmlForm>(form).invoke(methodName, args);
	}

	public Password password(String name) {
		// It's probably a security error to return the value in the response.
		return new Password(name).id(idGenerator.idForName(name));
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
	 * {@link #select(String, ISelectModel)} helper instead.
	 * 
	 * @param name
	 * @return a new Select
	 */
	@SuppressWarnings("unchecked")
	public <M, W> Select<M> select(String name) {
		return new Select<M>(name).id(idGenerator.idForName(name)).selected(form.<M, W> value(name));
	}

	public <M, W> Select<M> select(String name, Collection<M> model) {
		return select(name, new ListSelectModel<M>(model));
	}

	@SuppressWarnings("unchecked")
	public <M, W> Select<M> select(String name, ISelectModel<M> model) {
		return new Select<M>(name).id(idGenerator.idForName(name)).model(model).selected(form.<M, W> value(name));
	}

	/**
	 * @return A form that is programmed to post back to the action of the event
	 *         present at render. &lt;form method="post"
	 *         action="/context/servlet/currentController/currentAction"&gt;
	 */
	public Form start() {
		return action(event.resolve(UrlType.CONTROLLER, String.format("%s/%s", event.getProcessorName(), event.getActionName())));
	}

	public Submit submit() {
		return submit("Submit");
	}

	public Submit submit(String valueAttribute) {
		return new Submit(valueAttribute, adapterResolver).value(valueAttribute).id(idGenerator.idForLabel(valueAttribute));
	}

	public Text text(String name) {
		return new Text(name).value(form.value(name)).id(idGenerator.idForName(name));
	}

	public Textarea textarea(String name) {
		return new Textarea(name).id(idGenerator.idForName(name)).value(form.value(name));
	}
}
