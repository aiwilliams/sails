/*
 * Created on Feb 15, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.helper.oem;

import java.awt.event.ActionEvent;
import java.util.List;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

/**
 * A helper that builds application-relative urls.
 */
public class UrlforHelper {
	protected final ISailsEvent event;

	public UrlforHelper(ISailsEvent event) {
		this.event = event;
	}

	/**
	 * Same as {@link #action(String, String)} except that it uses the
	 * controller of the current event.
	 * 
	 * This should not be used to obtain a URL for use in a redirect.
	 * 
	 * @param action the action name on the controller of the event
	 * @see #action(String, String)
	 */
	public ActionUrl action(String action) {
		return action(event.getControllerName(), action);
	}

	/**
	 * Uses the controller of the current event.
	 * 
	 * This should not be used to obtain a URL for use in a redirect.
	 * 
	 * @param action
	 * @param parameters a {@link java.util.List} of parameters to use with the
	 *        action
	 */
	public ActionUrl action(String action, List<?> parameters) {
		return action(event.getControllerName(), action, parameters);
	}

	/**
	 * This should not be used to obtain a URL for use in a redirect.
	 * 
	 * @param controller
	 * @param action
	 * @return an absolute url to action on controller.
	 * @see ActionEvent#extendServletPath(String)
	 * @see ActionEvent#extendServletPathRedirect(String)
	 */
	public ActionUrl action(String controller, String action) {
		ActionUrl url = controller(controller);
		url.setAction(action);
		return url;
	}

	public ActionUrl action(String controller, String action, List<?> parameters) {
		ActionUrl url = action(controller, action);
		url.setParameters(parameters);
		return url;
	}

	public ActionUrl controller() {
		return controller(event.getControllerName());
	}

	/**
	 * This should not be used to obtain a URL for use in a redirect.
	 * 
	 * @return an absolute url to controller.
	 */
	public ActionUrl controller(String controller) {
		ActionUrl url = new ActionUrl(event);
		url.setController(controller);
		return url;
	}

	/**
	 * @return an url to a image file
	 */
	public IUrl image(String imageFile) {
		return event.resolve(UrlType.IMAGE, imageFile);
	}

	/**
	 * @return an url to a script file
	 */
	public IUrl script(String scriptFile) {
		return event.resolve(UrlType.SCRIPT, scriptFile);
	}

	/**
	 * @return an url to a css file
	 */
	public IUrl style(String cssFile) {
		return event.resolve(UrlType.STYLE, cssFile);
	}
}