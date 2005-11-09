package org.opensails.sails.controller.oem;

import javax.servlet.http.HttpSession;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.template.ITemplateBinding;

public class BaseController implements IControllerImpl {
	protected IController controller;
	protected ISailsEvent event;

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public IController getController() {
		return controller;
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

	protected ITemplateBinding getBinding() {
		return getContainer().instance(ITemplateBinding.class);
	}

	protected Object getSessionAttribute(String key) {
		HttpSession session = event.getSession(false);
		if (session != null) return session.getAttribute(key);
		return null;
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
		return setResult(new TemplateActionResult(event, template));
	}

	protected <T extends IActionResult> T setResult(T result) {
		getContainer().register(IActionResult.class, result);
		return result;
	}

	protected void setSessionAttribute(String key, Object value) {
		event.getSession(true).setAttribute(key, value);
	}

	protected UrlforMixin urlfor() {
		return getContainer().instance(UrlforMixin.class);
	}
}
