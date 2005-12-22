package org.opensails.sails.controller.oem;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.opensails.rigging.ScopedContainer;
import org.opensails.rigging.SimpleContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.FormContext;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.oem.SingleModelContext;
import org.opensails.sails.oem.Flash;
import org.opensails.viento.IBinding;

public class BaseController implements IControllerImpl {
	protected IController controller;
	protected ISailsEvent event;
	protected IActionResult result;

	public IActionResult getActionResult() {
		return result;
	}

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public ISailsEvent getEvent() {
		return event;
	}

	public IController getEventProcessor() {
		return controller;
	}

	public void setEventContext(ISailsEvent event, IController controller) {
		this.event = event;
		this.controller = controller;
	}

	public <T extends IActionResult> T setResult(T result) {
		this.result = result;
		return result;
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

	// TODO: Don't make child - use factory see
	// http://trac.opensails.org/sails/ticket/79
	// TODO: Write tests outside of Dock
	protected boolean formToModel(Object modelInstance) {
		FormContext formContext = event.getContainer().instance(FormContext.class, FormContext.class);
		SimpleContainer formContainer = event.getContainer().makeChildUnscoped();
		formContainer.register(IModelContext.class, new SingleModelContext(modelInstance));
		HtmlForm formInstance = formContainer.instance(HtmlForm.class, HtmlForm.class);
		formContext.put(formInstance);
		return formInstance.isValid();
	}

	protected IBinding getBinding() {
		return getContainer().instance(IBinding.class);
	}

	/**
	 * @param clazz the Class of the attribute
	 * @return the value for the full name of the Class, null if not present or
	 *         HttpSession is null
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getSessionAttribute(Class<? super T> clazz) {
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

	protected void layout(String templateIdentifier) {
		getTemplateResult().setLayout(templateIdentifier);
	}

	// TODO: Don't make child - use factory see
	// http://trac.opensails.org/sails/ticket/79
	// TODO: Write tests outside of Dock
	protected void modelToForm(Object modelInstance) {
		if (modelInstance == null) return;
		FormContext formContext = event.getContainer().instance(FormContext.class, FormContext.class);
		SimpleContainer formContainer = event.getContainer().makeChildUnscoped();
		formContainer.register(IModelContext.class, new SingleModelContext(modelInstance));
		formContext.put(formContainer.instance(HtmlForm.class, HtmlForm.class));
	}

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action) {
		return setResult(new RedirectActionResult(event, controller, action));
	}

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action, List<?> parameters) {
		return setResult(new RedirectActionResult(event, controller, action, parameters));
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
	 * Renders the named template. Uses the Controller of the currentEvent in
	 * the templateIdentifier.
	 * 
	 * @param template
	 * @return the TemplateActionResult for template
	 */
	protected TemplateActionResult renderTemplate(String template) {
		TemplateActionResult result = getTemplateResult();
		// TODO: Forein behavior - move into TemplateActionResult
		result.setTemplate(String.format("%s/%s", event.getProcessorName(), template));
		return result;
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

	protected UrlforMixin urlfor() {
		return getContainer().instance(UrlforMixin.class);
	}
}
