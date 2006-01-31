package org.opensails.sails.component;

import org.opensails.sails.action.IAction;
import org.opensails.sails.component.oem.ComponentFactory;
import org.opensails.sails.controller.NoImplementationException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.IActionEventProcessor;

/**
 * A component implementation.
 * <p>
 * You will see that there are two things, IComponent and IComponentImpl. The
 * first is the component 'meta class'. I think there may be a better name for
 * it. It is what the framework interacts with, and is used throughout to do
 * cool things. The second represents the interface of what developers write all
 * the time - the actual classes that have code and make declarations about
 * things with annotations.
 * <p>
 * An IComponentImpl can answer the IComponent which represents it to the
 * framework. There is only ONE IComponent for a particular name and many
 * IComponent instances: every request causes one to be created.
 * 
 * @see org.opensails.sails.component.IComponentImpl
 * @author Adam 'Programmer' Williams
 */
public interface IComponent extends IActionEventProcessor {
	ComponentFactory createFactory(ISailsEvent event);

	/**
	 * @param event
	 * @see #hasImplementation()
	 * @return an IComponentImpl instance bound to event
	 * @throws NoImplementationException when there is no IComponentImpl
	 *         associated with this
	 */
	IComponentImpl createInstance(ISailsEvent event) throws NoImplementationException;

	/**
	 * @param name of action
	 * @return an IAction for name - there may or may not be code or a template
	 *         behind this action
	 */
	IAction getAction(String name);

	/**
	 * @return the IComponentImpl that implements the code behind this - may be
	 *         null
	 */
	Class<? extends IComponentImpl> getImplementation();

	/**
	 * @return true if this has an implementation
	 */
	boolean hasImplementation();
}