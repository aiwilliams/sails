package org.opensails.sails.controller.oem;

import org.opensails.sails.controller.IControllerImpl;

/**
 * A controller descriptor, if you will.
 * 
 * You will see that there are two things,
 * {@link org.opensails.sails.controller.oem.IController} and
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
public interface IController {
	IAction getAction(String name);

	Class<? extends IControllerImpl> getImplementation();
}