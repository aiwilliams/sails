package org.opensails.sails.controller.oem;

import java.util.List;

import org.opensails.sails.Sails;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.oem.AbstractEventProcessingContext;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.sails.oem.FragmentKey;
import org.opensails.sails.oem.HostFragmentKey;
import org.opensails.sails.template.CacheType;
import org.opensails.sails.template.Cached;

public class BaseController extends AbstractEventProcessingContext<IController> implements IControllerImpl {
	/**
	 * Expires the fragment of action for the current hostname.
	 * <p>
	 * When an action method is annotated with {@link Cached} as
	 * {@link CacheType#ACTION}, you can expire that action's cached output.
	 * 
	 * @param action on this
	 */
	protected void expireAction(String... actions) {
		FragmentCache fragmentCache = event.instance(FragmentCache.class);
		for (String action : actions)
			fragmentCache.expire(new HostFragmentKey(event, action));
	}

	/**
	 * Expires the anonymous fragment of actions on controller.
	 * <p>
	 * If you use $cache.fragment [[ ... ]] you will have an anonymous fragment
	 * for that action. This will expire that and leave named fragments alone.
	 * 
	 * @param controller
	 * @param action
	 */
	protected void expireFragment(Class<? extends IControllerImpl> controller, String... actions) {
		FragmentCache fragmentCache = event.instance(FragmentCache.class);
		for (String action : actions)
			fragmentCache.expire(new FragmentKey(controller, action));
	}

	/**
	 * Expires the named fragment of action on controller.
	 * 
	 * @param controller
	 * @param action
	 * @param name
	 */
	protected void expireFragment(Class<? extends IControllerImpl> controller, String action, String name) {
		event.instance(FragmentCache.class).expire(new FragmentKey(controller, action, name));
	}

	/**
	 * Expires the anonymous fragment of actions on this controller.
	 * 
	 * @see #expireFragment(Class, String)
	 * @param action
	 */
	protected void expireFragment(String... actions) {
		expireFragment(getClass(), actions);
	}

	/**
	 * Expires the named fragment of action on this controller.
	 * 
	 * @see #expireFragment(Class, String, String)
	 * @param action
	 * @param name
	 */
	protected void expireFragment(String action, String name) {
		expireFragment(getClass(), action, name);
	}

	/**
	 * Expires all fragments of actions on controller
	 * 
	 * @param controller
	 * @param action
	 */
	protected void expireFragments(Class<? extends IControllerImpl> controller, String... actions) {
		FragmentCache cache = event.instance(FragmentCache.class);
		for (String action : actions)
			for (FragmentKey key : cache.keys(String.format("^%s/%s.*", Sails.controllerName(controller), action)))
				cache.expire(key);
	}

	/**
	 * Expires all fragments of action on this controller.
	 * 
	 * @param action
	 */
	protected void expireFragments(String... actions) {
		expireFragments(getClass(), actions);
	}

	protected void layout(String templateIdentifier) {
		getTemplateResult().setLayout(templateIdentifier);
	}

	protected RedirectActionResult redirect(String absoluteUrl) {
		return setResult(new RedirectActionResult(event, absoluteUrl));
	}

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action) {
		return setResult(new RedirectActionResult(event, controller, action));
	}

	protected RedirectActionResult redirectAction(Class<? extends IControllerImpl> controller, String action, List<?> parameters) {
		return setResult(new RedirectActionResult(event, controller, action, parameters));
	}

	protected RedirectActionResult redirectAction(String action) {
		return setResult(new RedirectActionResult(event, getClass(), action));
	}

	protected RedirectActionResult redirectAction(String action, List<?> parameters) {
		return setResult(new RedirectActionResult(event, getClass(), action, parameters));
	}

	protected RedirectActionResult redirectController(Class<? extends IControllerImpl> controller) {
		return setResult(new RedirectActionResult(event, controller));
	}

	protected TemplateActionResult renderIndex() {
		return renderTemplate("index");
	}
}
