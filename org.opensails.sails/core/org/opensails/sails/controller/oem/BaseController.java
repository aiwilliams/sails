package org.opensails.sails.controller.oem;

import javax.servlet.http.HttpSession;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.oem.Flash;
import org.opensails.viento.IBinding;

public class BaseController implements IControllerImpl {
	protected IController controller;
	protected ISailsEvent event;
	protected IActionResult result;

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public IController getController() {
		return controller;
	}

	public IActionResult result() {
		return result;
	}

	public void set(ISailsEvent event, IController controller) {
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

	/**
	 * Renders content to the client. A template will not be rendered, of
	 * course.
	 * 
	 * @param content
	 * @return the StringActionResult with content
	 */
	protected StringActionResult renderString(String content) {
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
