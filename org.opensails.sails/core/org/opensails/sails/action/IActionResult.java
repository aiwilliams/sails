package org.opensails.sails.action;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;

public interface IActionResult {
	/**
	 * @return the container of the ISailsEvent that this is the result of
	 *         processing
	 */
	ScopedContainer getContainer();

	/**
	 * @return the controller implementation instance, if there was one - may be
	 *         null
	 */
	IControllerImpl getController();

	/**
	 * @return the event this is the result of processing
	 */
	ISailsEvent getEvent();
}
