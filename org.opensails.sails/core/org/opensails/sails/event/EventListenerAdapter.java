package org.opensails.sails.event;

import javax.servlet.http.HttpSession;


/**
 * Convenience, for those interested in a subset of events.
 */
public class EventListenerAdapter implements ISailsEventListener {
	public void beginDispatch(ISailsEvent event) {}

	public void endDispatch(ISailsEvent event) {}

	public void sessionCreated(ISailsEvent event, HttpSession session) {}
}
