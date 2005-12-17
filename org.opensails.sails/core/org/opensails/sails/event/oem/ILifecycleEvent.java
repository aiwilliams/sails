package org.opensails.sails.event.oem;

import javax.servlet.http.HttpSession;

import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.ISailsEvent;

/**
 * This is the interface of an ISailsEvent as needed by the framework itself to
 * manage the lifecycle of an event. It helps to keep the interface that is
 * exposed to the rest of the application a bit less cluttered.
 * 
 * @author aiwilliams
 */
public interface ILifecycleEvent extends ISailsEvent {
	/**
	 * Called right before {@link #visit(Controller))
	 */
	void beginDispatch();

	/**
	 * If an exception is thrown processing this event, this is called after
	 * exception processing occurs. Otherwise, called after
	 * {@link #visit(Controller)).
	 */
	void endDispatch();

	/**
	 * Invoked when a session is created for the request of this event.
	 * 
	 * @param session
	 */
	void sessionCreated(HttpSession session);

	/**
	 * This exists to make the Dispatcher a bit cleaner. Implementations call
	 * the most specific process methods.
	 * 
	 * @param eventProcessor
	 * @return result of event processing
	 */
	IActionResult visit(IActionEventProcessor eventProcessor);
}
