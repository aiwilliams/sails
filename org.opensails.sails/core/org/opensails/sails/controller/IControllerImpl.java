package org.opensails.sails.controller;

import org.opensails.rigging.*;
import org.opensails.sails.*;

/**
 * A controller implementation.
 * 
 * You will see that there are two things,
 * {@link org.opensails.sails.controller.IController} and
 * {@link IControllerImpl}. The first is the controller 'meta class'. I think
 * there may be a better name for it. It is what the framework interacts with,
 * and is used throughout to do cool things. The second represents the interface
 * of what developers write all the time - the actual classes that have action
 * code and make declarations about things with annotations.
 * 
 * An IControllerImpl can answer the IController which represents it to the
 * framework. There is only ONE
 * {@link org.opensails.sails.controller.IController} for a particular name and
 * many {@link IControllerImpl}s. Every request causes an IControllerImpl
 * instance to be created.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IControllerImpl {
	/**
	 * @return the result decided by the controller implementation. This may be
	 *         null, in which case the Sails framework will decide the result.
	 */
	IActionResult getActionResult();

	/**
	 * @return the container that created this
	 */
	ScopedContainer getContainer();

	/**
	 * @return the IController for this implementation
	 */
	IController getController();

	/**
	 * Called before the controller is invoked, but only if it is invoked
	 * 
	 * @param event
	 * @param controller
	 */
	void setEventContext(ISailsEvent event, IController controller);
}
