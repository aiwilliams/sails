package org.opensails.sails.event;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.oem.IActionEventProcessor;

public interface IEventProcessingContext<P extends IActionEventProcessor> {

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