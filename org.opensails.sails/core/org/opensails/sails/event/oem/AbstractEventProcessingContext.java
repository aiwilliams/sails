package org.opensails.sails.event.oem;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.FileSendActionResult;
import org.opensails.sails.action.oem.InputStreamActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IValidationError;
import org.opensails.sails.form.ValidationContext;
import org.opensails.sails.form.ValidationErrors;
import org.opensails.sails.model.ModelContext;
import org.opensails.sails.oem.Flash;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.sails.oem.FragmentKey;
import org.opensails.sails.tools.UrlforTool;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.spyglass.SpyGlass;
import org.opensails.viento.IBinding;

public abstract class AbstractEventProcessingContext<P extends IActionEventProcessor> implements IEventProcessingContext<P> {
	protected ISailsEvent event;
	protected P processor;
	protected IActionResult result;

	/**
	 * Provides access to the ValidationErrors of a model.
	 * <p>
	 * Every model instance that is exposed can have multiple
	 * {@link IValidationError}s associated with it. These may be added by the
	 * {@link IValidationEngine} during {@link #updateModels()}, or an action
	 * may obtain the ValidationErrors and add some.
	 * <p>
	 * The errors for a model may be displayed in views using the
	 * {@link HtmlForm#errorsFor(String)} and other methods on that class.
	 * 
	 * @param modelName
	 * @return the ValidationErrors for modelName
	 */
	public ValidationErrors errors(String modelName) {
		return getContainer().instance(ValidationContext.class).errorsFor(modelName);
	}

	public IActionResult getActionResult() {
		return result;
	}

	public IEventContextContainer getContainer() {
		return event.getContainer();
	}

	public ISailsEvent getEvent() {
		return event;
	}

	public P getEventProcessor() {
		return processor;
	}

	public String getTemplatePath(String identifier) {
		if (TemplateActionResult.CONTROLLER_ACTION_PATTERN.matcher(identifier).find()) return identifier;
		else return String.format("%s/%s", event.getProcessorName(), identifier);
	}

	/**
	 * @param index
	 * @return the value of the action parameter at index
	 * @see #params()
	 */
	public String param(int index) {
		return event.getActionParameters().stringAt(index);
	}

	/**
	 * Provides access to the raw action parameters.
	 * <p>
	 * You may obtain these this way if you like, but the best way is to declare
	 * them in your action method signature, like so:
	 * 
	 * <pre>
	 * public void myActionMethod(String one, String two, String etc) {}
	 * </pre>
	 * 
	 * Of course, you can declare those as any type for which there is an
	 * IAdapterAvailable, including all primitives and primitive arrays.
	 * Remember, action parameters are not url parameters: they are the 'slash
	 * values' after the controller/action. Url query parameters are named, and
	 * therefore available as {@link #field(String) fields}.
	 * 
	 * @return the action parameters before typecasting
	 */
	public String[] params() {
		return event.getActionParameters().strings();
	}

	/**
	 * This most certainly only reports values found in the event url (query
	 * string) of the url. It <strong>will not</strong> answer any values from
	 * a form post.
	 * 
	 * @param name
	 * @return the value of a url query parameter
	 */
	public String queryParam(String name) {
		return event.getUrl().getQueryParameter(name);
	}

	public void setEventContext(ISailsEvent event, P processor) {
		this.event = event;
		this.processor = processor;
	}

	public <T extends IActionResult> T setResult(T result) {
		this.result = result;
		return result;
	}

	protected void expireFragment(String action) {
		expireFragment(getClass(), action);
	}

	protected void expireFragment(Class<? extends IEventProcessingContext> context, String action) {
		event.instance(FragmentCache.class).expire(new FragmentKey(context, action));
	}

	/**
	 * Exposes the object as key for use in the rendered template
	 * 
	 * @param key, available as $key in template
	 * @param object
	 */
	protected void expose(String key, Object object) {
		getBinding().put(key, object);
	}

	/**
	 * Exposes the object as that lower-camel name of the object's class.
	 * 
	 * @see #exposeModel(String, Object)
	 * @param model
	 */
	protected void exposeModel(Object model) {
		if (model == null) return;
		exposeModel(SpyGlass.lowerCamelName(model.getClass()), model);
	}

	/**
	 * Exposes the object as name for use in forms and otherwise.
	 * <p>
	 * Contrast this to {@link #expose(String, Object)}, which only exposes for
	 * use in the template.
	 * 
	 * @param name
	 * @param model
	 */
	protected void exposeModel(String name, Object model) {
		expose(name, model);
		getContainer().instance(ModelContext.class).expose(name, model);
	}

	protected String field(String name) {
		return event.getFieldValue(name);
	}

	protected String[] fields(String name) {
		return event.getFieldValues(name);
	}

	protected FileUpload file(String name) {
		return event.getFileUpload(name);
	}

	/**
	 * @param key
	 * @return the value in the flash for key
	 */
	protected Object flash(Object key) {
		return getContainer().instance(Flash.class).get(key);
	}

	/**
	 * Places the value in the flash as the provided key. In templates, this is
	 * accessed as $flash(key).
	 */
	protected void flash(Object key, Object value) {
		getContainer().instance(Flash.class).put(key, value);
	}

	/**
	 * Flash is placed in session. Session will be created if needed.
	 * 
	 * @see #flash(Object, Object)
	 */
	protected void flashSession(Object key, Object value) {
		event.getSession(true);
		flash(key, value);
	}

	/**
	 * @return the IBinding appropriate to the scope of this
	 */
	protected IBinding getBinding() {
		return getContainer().instance(IBinding.class);
	}

	/**
	 * @param clazz the Class of the attribute
	 * @return the value for the full name of the Class, null if not present or
	 *         HttpSession is null
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getSessionAttribute(Class<T> clazz) {
		return (T) getSessionAttribute(clazz.getName());
	}

	/**
	 * @param name
	 * @return the value for name, null if not present or HttpSession is null
	 */
	protected Object getSessionAttribute(String name) {
		HttpSession session = event.getSession(false);
		if (session != null) return session.getAttribute(name);
		return null;
	}

	protected TemplateActionResult getTemplateResult() {
		if (result != null) {
			if (TemplateActionResult.class.isAssignableFrom(result.getClass())) return (TemplateActionResult) result;
		}
		return setResult(new TemplateActionResult(event));
	}

	/**
	 * Adds the mixin to the Viento binding of the current event.
	 * <p>
	 * Mixins allow you to extend the template language! Create public methods
	 * that return Strings or other objects on your mixin, make them take
	 * instances of the type target. In your templates, you can then do things
	 * like $anInstanceOfTarget.someMixinMethod.
	 * 
	 * @param target
	 * @param mixin
	 */
	protected void mixin(Class target, Object mixin) {
		getBinding().mixin(target, mixin);
	}

	/**
	 * Renders content to the client. A template will not be rendered, of
	 * course.
	 * 
	 * @param content
	 * @return the StringActionResult with content
	 */
	protected StringActionResult renderString(Object content) {
		return setResult(new StringActionResult(event, content));
	}

	/**
	 * Renders the identified template.
	 * <p>
	 * You can use two forms: "action" only, or "controller/action".
	 * 
	 * @param template
	 * @return the TemplateActionResult for template
	 */
	protected TemplateActionResult renderTemplate(String template) {
		TemplateActionResult result = getTemplateResult();
		result.setTemplate(template);
		return result;
	}

	protected InputStreamActionResult sendData(InputStream stream) {
		return setResult(new InputStreamActionResult(event, stream));
	}

	protected FileSendActionResult sendFile(File file) {
		return setResult(new FileSendActionResult(event, file));
	}

	protected FileSendActionResult sendFile(String path) {
		return setResult(new FileSendActionResult(event, path));
	}

	/**
	 * @param clazz full name is used as attribute name
	 * @param value can be null
	 * @return the existing value replaced by new value
	 * @throws NullPointerException if name class is null
	 */
	protected <T> Object setSessionAttribute(Class<? super T> name, T value) {
		return setSessionAttribute(name.getName(), value);
	}

	/**
	 * Allows placement of <em>non-null</em> object into HttpSession.
	 * 
	 * @param value full name of getClass is used as attribute name
	 * @return the existing value replaced by new value
	 * @throws NullPointerException if value is null
	 */
	protected Object setSessionAttribute(Object value) {
		return setSessionAttribute(value.getClass().getName(), value);
	}

	/**
	 * Causes an HttpSession to be created if it doesn't already exist and sets
	 * the provided attribute.
	 * 
	 * @param name
	 * @param value can be null
	 * @return the existing value replaced by new value
	 */
	protected Object setSessionAttribute(String name, Object value) {
		HttpSession s = event.getSession(true);
		Object existing = s.getAttribute(name);
		s.setAttribute(name, value);
		return existing;
	}

	/**
	 * Updates the given model from the posted form (or query parameters).
	 * <p>
	 * This is really just a convenience method. Please look at {@link HtmlForm}
	 * to learn more about ways to extend form processing.
	 */
	protected boolean updateModel(Object model) {
		exposeModel(model);
		return updateModels();
	}

	protected boolean updateModel(String name, Object model) {
		exposeModel(name, model);
		return updateModels();
	}

	/**
	 * Assumes there are models in the current ModelContext that need to be
	 * updated from the FormFields of the current event.
	 * 
	 * @return true if their were no validation errors
	 */
	protected boolean updateModels() {
		HtmlForm formInstance = getContainer().instance(HtmlForm.class, HtmlForm.class);
		return formInstance.updateModels(getEvent().getFormFields());
	}

	protected UrlforTool urlfor() {
		return getContainer().instance(UrlforTool.class, UrlforTool.class);
	}
}
