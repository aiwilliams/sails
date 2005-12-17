package org.opensails.sails.event.oem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class EventServletRequest extends HttpServletRequestWrapper {
	protected final ILifecycleEvent event;

	public EventServletRequest(ILifecycleEvent event, HttpServletRequest request) {
		super(request);
		this.event = event;
	}

	@Override
	public HttpSession getSession() {
		return doGetSession();
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (!create) return super.getSession(create);
		return doGetSession();
	}

	protected HttpSession doGetSession() {
		HttpSession httpSession = super.getSession(false);
		if (httpSession == null) {
			httpSession = super.getSession(true);
			event.sessionCreated(httpSession);
		}
		return httpSession;
	}
}
