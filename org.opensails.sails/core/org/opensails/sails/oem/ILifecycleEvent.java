package org.opensails.sails.oem;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;

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
	 * This exists to make the Dispatcher a bit cleaner. Implementations call
	 * the most specific Controller#process() methods.
	 * 
	 * @param controller
	 * @return IActionResult of Controller process method
	 */
	IActionResult visit(Controller controller);
}
