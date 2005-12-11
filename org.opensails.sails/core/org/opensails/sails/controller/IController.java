package org.opensails.sails.controller;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.oem.IActionEventProcessor;

/**
 * A controller descriptor, if you will.
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
 * framework. There is only ONE IController for a particular name and many
 * IControllerImpl. Every request causes an IControllerImpl instance to be
 * created.
 * 
 * @author aiwilliams
 */
public interface IController extends IActionEventProcessor {
	/**
	 * @param event
	 * @see #hasImplementation()
	 * @return an IControllerImpl instance bound to event
	 * @throws NoImplementationException when there is no IControllerImpl
	 *         associated with this
	 */
	IControllerImpl createInstance(ISailsEvent event) throws NoImplementationException;

	/**
	 * @param name of action
	 * @return an IAction for name - there may or may not be code or a template
	 *         behind this action
	 */
	IAction getAction(String name);

	/**
	 * @return the IControllerImpl that implements the code behind this - may be
	 *         null
	 */
	Class<? extends IControllerImpl> getImplementation();

	/**
	 * @return true if this has an IControllerImpl
	 */
	boolean hasImplementation();
}