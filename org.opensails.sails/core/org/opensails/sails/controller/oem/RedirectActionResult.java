package org.opensails.sails.controller.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.Sails;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class RedirectActionResult extends AbstractActionResult {
	protected final String redirectToAction;
	protected final Class<? extends IControllerImpl> redirectToController;

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction) {
		super(event);
		this.redirectToController = redirectToController;
		this.redirectToAction = redirectToAction;
	}

	public IUrl getRedirectUrl() {
		return event.resolve(UrlType.CONTROLLER, Sails.controllerName(redirectToController) + "/" + redirectToAction);
	}
}
