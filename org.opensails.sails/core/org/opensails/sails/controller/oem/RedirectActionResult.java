package org.opensails.sails.controller.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.Sails;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class RedirectActionResult implements IActionResult {
	protected final ISailsEvent event;
	protected final String redirectToAction;
	protected final Class<? extends IControllerImpl> redirectToController;

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction) {
		this.event = event;
		this.redirectToController = redirectToController;
		this.redirectToAction = redirectToAction;
	}

	public ScopedContainer getContainer() {
		return event.getContainer();
	}

	public IControllerImpl getController() {
		return null;
	}

	public ISailsEvent getEvent() {
		return event;
	}

	public IUrl getRedirectUrl() {
		return event.resolve(UrlType.CONTROLLER, Sails.controllerName(redirectToController) + "/" + redirectToAction);
	}
}
