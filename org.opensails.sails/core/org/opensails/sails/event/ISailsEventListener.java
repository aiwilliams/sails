package org.opensails.sails.event;

import javax.servlet.http.HttpSession;


/**
 * Instances in the containers that implement this will be notified of
 * ISailsEvent events.
 */
public interface ISailsEventListener {
	void beginDispatch(ISailsEvent event);

	void endDispatch(ISailsEvent event);

	void sessionCreated(ISailsEvent event, HttpSession session);
}
