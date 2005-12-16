package org.opensails.sails.controller.oem;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.opensails.rigging.ScopedContainer;
import org.opensails.rigging.SimpleContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.form.FileUpload;
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

	public IController getEventProcessor() {
		return controller;
	}

	public void setEventContext(ISailsEvent event, IController controller) {
		this.event = event;
		this.controller = controller;
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
		SimpleContainer container = event.getContainer().makeChildUnscoped();
		container.register(IModelContext.class, new SingleModelContext(modelInstance));
		HtmlForm form = container.instance(HtmlForm.class, HtmlForm.class);
		return form.isValid();
	}

	protected IBinding getBinding() {
		return getContainer().instance(IBinding.class);
	}

	protected Object getSessionAttribute(String key) {
		HttpSession session = event.getSession(false);
		if (session != null) return session.getAttribute(key);
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
		result.setTemplate(String.format("%s/%s", event.getControllerName(), template));
		return result;
	}

	protected <T extends IActionResult> T setResult(T result) {
		this.result = result;
		return result;
	}

	protected void setSessionAttribute(String key, Object value) {
		event.getSession(true).setAttribute(key, value);
	}

	protected UrlforMixin urlfor() {
		return getContainer().instance(UrlforMixin.class);
	}
}
