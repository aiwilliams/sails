package org.opensails.sails.component;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;
import org.opensails.viento.IRenderable;

/**
 * A component implementation.
 * <p>
 * An interesting thing about components in Sails is that they can behave like a
 * controller and as small template renderers. When a component is used in a
 * template as part of a response to a request, it's renderThyself method will
 * be invoked. When it is called as a processor for an action, it is expected to
 * provide some type of action result.
 * <p>
 * You must define a method called initialize. It may take 0 or more arguments
 * of whatever types you like. It will be invoked after instantiation.
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
 * @author aiwilliams
 */
public interface IComponentImpl extends IEventProcessingContext<IComponent>, IRenderable {
	/**
	 * Configure the ComponentContainer after creation.
	 * <p>
	 * This is only called when the component is not acting as an
	 * {@link IEventProcessingContext}. Called after the container is set.
	 * 
	 * @param container
	 */
	void configureContainer(ComponentContainer container);

	/*
	 * Called getComponentName() to avoid collisions with component properties.
	 */
	/**
	 * @return the name of the component
	 */
	String getComponentName();

	/**
	 * Called after creation.
	 * <p>
	 * <ol>
	 * <li>When acting as an IEventProcessingContext, the invoked action will
	 * want to reference the container of the event</li>
	 * <li>When acting as a generator in the render of the template of another
	 * action, should be a component-scoped container</li>
	 * </ol>
	 * 
	 * @param container for this component
	 */
	void setContainer(IEventContextContainer container);

	/**
	 * Called after creation, before initialize.
	 * 
	 * @param generator that the component should use to create ids for
	 *        elements.
	 */
	void setIdGenerator(IFormElementIdGenerator generator);

	/**
	 * Called after creation, after
	 * {@link IEventProcessingContext#setEventContext(org.opensails.sails.event.ISailsEvent, org.opensails.sails.event.oem.IActionEventProcessor)}.
	 * <p>
	 * This method is here to prevent component developers from having to take
	 * it in their constructor.
	 * 
	 * @param renderer
	 */
	void setTemplateRenderer(ITemplateRenderer<IBinding> renderer);
}
