package org.opensails.sails.component;

import org.opensails.sails.event.IActionEventProcessingContext;

/**
 * A component implementation.
 * <p>
 * You will see that there are two things,
 * {@link org.opensails.sails.component.IComponent} and {@link IComponentImpl}.
 * The first is the component 'meta class'. I think there may be a better name
 * for it. It is what the framework interacts with, and is used throughout to do
 * cool things. The second represents the interface of what developers write all
 * the time - the actual classes that have code and make declarations about
 * things with annotations.
 * <p>
 * An IComponentImpl can answer the IComponent which represents it to the
 * framework. There is only ONE IComponent for a particular name and many
 * IComponent instances: every request causes one to be created.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IComponentImpl extends IActionEventProcessingContext<IComponent> {}
