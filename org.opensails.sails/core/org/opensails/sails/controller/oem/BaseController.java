package org.opensails.sails.controller.oem;

import java.util.*;

import javax.servlet.http.*;

import org.opensails.rigging.*;
import org.opensails.sails.action.*;
import org.opensails.sails.action.oem.*;
import org.opensails.sails.controller.*;
import org.opensails.sails.event.*;
import org.opensails.sails.form.*;
import org.opensails.sails.mixins.*;
import org.opensails.sails.model.*;
import org.opensails.sails.model.oem.*;
import org.opensails.sails.oem.*;
import org.opensails.sails.util.*;
import org.opensails.viento.*;

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

	/**
	 * Exposes the object as that lower-camel name of the object's class.
	 * 
	 * @see #exposeModel(String, Object)
	 * @param model
	 */
	protected void exposeModel(Object model) {
		if (model == null) return;
		exposeModel(ClassHelper.lowerCamelName(model.getClass()), model);
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
		getContainer().instance(IFormValueModel.class).expose(name, model);
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
	protected boolean updateModel(Object modelInstance) {
		exposeModel(modelInstance);
		SimpleContainer formContainer = event.getContainer().makeChildUnscoped();
		formContainer.register(IModelContext.class, new SingleModelContext(modelInstance));
		HtmlForm formInstance = formContainer.instance(HtmlForm.class, HtmlForm.class);
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

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action) {
		return setResult(new RedirectActionResult(event, controller, action));
	}

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action, List<?> parameters) {
		return setResult(new RedirectActionResult(event, controller, action, parameters));
	}

	protected TemplateActionResult renderIndex() {
		return renderTemplate("index");
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
