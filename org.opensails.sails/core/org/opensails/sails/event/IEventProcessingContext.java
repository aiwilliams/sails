package org.opensails.sails.event;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.event.oem.IActionEventProcessor;

public interface IEventProcessingContext<P extends IActionEventProcessor> {

	/**
	 * @return the result decided by the processing context. This may be null,
	 *         in which case the Sails framework will decide the result.
	 */
	IActionResult getActionResult();

	/**
	 * @return the container that created this
	 */
	IScopedContainer getContainer();

	/**
	 * @return the ISailsEvent for this processing context
	 */
	ISailsEvent getEvent();

	/**
	 * @return the IActionEventProcessor for this processing context
	 */
	P getEventProcessor();

	/**
	 * Called before the context is utilized
	 * 
	 * @param event
	 * @param processor
	 */
	void setEventContext(ISailsEvent event, P processor);

	<T extends IActionResult> T setResult(T result);

	/**
	 * @param identifier
	 * @return the path to the template to render for this identifier.
	 */
	String getTemplatePath(String identifier);
}