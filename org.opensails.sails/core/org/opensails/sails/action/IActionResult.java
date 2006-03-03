package org.opensails.sails.action;

import org.opensails.rigging.*;
import org.opensails.sails.event.*;

public interface IActionResult {
	/**
	 * @return the container of the ISailsEvent that this is the result of
	 *         processing
	 */
	IScopedContainer getContainer();

	/**
	 * @return the controller implementation instance, if there was one - may be
	 *         null
	 */
	IEventProcessingContext getProcessingContext();

	/**
	 * @return the event this is the result of processing
	 */
	ISailsEvent getEvent();
}
