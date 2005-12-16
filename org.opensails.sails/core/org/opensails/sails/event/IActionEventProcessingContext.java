package org.opensails.sails.event;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.oem.IActionEventProcessor;

public interface IActionEventProcessingContext<P extends IActionEventProcessor> {

	/**
	 * @return the result decided by the processing context. This may be null,
	 *         in which case the Sails framework will decide the result.
	 */
	IActionResult getActionResult();

	/**
	 * @return the IActionEventProcessor for this processing context
	 */
	P getEventProcessor();

	/**
	 * @return the container that created this
	 */
	ScopedContainer getContainer();

	/**
	 * Called before the context is utilized
	 * 
	 * @param event
	 * @param processor
	 */
	void setEventContext(ISailsEvent event, P processor);
}