package org.opensails.sails.action.oem;

import java.util.List;

import org.opensails.sails.Sails;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.AbsoluteUrl;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.url.IUrl;

public class RedirectActionResult extends AbstractActionResult {

	protected IUrl redirectUrl;

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController) {
		this(event, redirectToController, null, null);
	}

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction) {
		this(event, redirectToController, redirectToAction, null);
	}

	public RedirectActionResult(ISailsEvent event, Class<? extends IControllerImpl> redirectToController, String redirectToAction, List<?> parameters) {
		super(event);
		ActionUrl actionUrl = new ActionUrl(event);
		actionUrl.setController(Sails.controllerName(redirectToController));
		actionUrl.setAction(redirectToAction);
		actionUrl.setParameters(parameters);
		this.redirectUrl = actionUrl;
	}

	public RedirectActionResult(ISailsEvent event, String url) {
		super(event);
		this.redirectUrl = new AbsoluteUrl(event, url);
	}

	public IUrl getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * Sets a URL query param, {@link Sails.QueryParam#ORIGIN}, to contain
	 * the URL of the originating event. Useful for tracking what redirected the
	 * browser.
	 */
	public void rememberOrigin() {
		redirectUrl.setQueryParameter(Sails.QueryParam.ORIGIN, event.getUrl().getAbsoluteUrl());
	}
}
