package org.opensails.sails.controller.oem;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.opensails.rigging.ScopedContainer;
import org.opensails.rigging.SimpleContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.FileSendActionResult;
import org.opensails.sails.action.oem.InputStreamActionResult;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormValueModel;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.model.IModelContext;
import org.opensails.sails.model.oem.SingleModelContext;
import org.opensails.sails.oem.Flash;
import org.opensails.sails.util.ClassHelper;
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

	protected void layout(String templateIdentifier) {
		getTemplateResult().setLayout(templateIdentifier);
	}

	/**
	 * @param index
	 * @return the value of the action parameter at index
	 * @see #params()
	 */
	protected String param(int index) {
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
	protected String[] params() {
		return event.getActionParameters().strings();
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

	protected UrlforMixin urlfor() {
		return getContainer().instance(UrlforMixin.class, UrlforMixin.class);
	}
}
