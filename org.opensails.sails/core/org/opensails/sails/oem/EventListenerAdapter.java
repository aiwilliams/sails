package org.opensails.sails.oem;

import javax.servlet.http.HttpSession;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.ISailsEventListener;

/**
 * Convenience, for those interested in a subset of events.
 */
public class EventListenerAdapter implements ISailsEventListener {
	public void beginDispatch(ISailsEvent event) {}

	public void endDispatch(ISailsEvent event) {}

	public void sessionCreated(ISailsEvent event, HttpSession session) {}
}
