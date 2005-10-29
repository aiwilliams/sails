package org.opensails.sails.controller;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;

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
	IController getController();

	/**
	 * @return the event this is the result of processing
	 */
	ISailsEvent getEvent();
}
