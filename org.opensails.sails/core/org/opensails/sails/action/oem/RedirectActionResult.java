package org.opensails.sails.action.oem;

import java.util.List;

import org.opensails.sails.Sails;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;

public class RedirectActionResult extends AbstractActionResult {

	protected ActionUrl redirectUrl;

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController) {
		this(event, redirectToController, null, null);
	}

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction) {
		this(event, redirectToController, redirectToAction, null);
	}

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction, List<?> parameters) {
		super(event);
		redirectUrl = new ActionUrl(event);
		redirectUrl.setController(Sails.controllerName(redirectToController));
		redirectUrl.setAction(redirectToAction);
		redirectUrl.setParameters(parameters);
	}

	public IUrl getRedirectUrl() {
		return redirectUrl;
	}
}
