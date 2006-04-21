package org.opensails.sails.component;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.viento.IBinding;

/**
 * The container of a component when rendered in an action view.
 * <p>
 * A ComponentContainer is used when a Component is rendered within an action
 * view. When the Component is acting as a Controller, it has a
 * RequestContainer. This container is a child of the RequestContainer during
 * the render of another controller's action view.
 * 
 * @author aiwilliams
 */
public class ComponentContainer extends ScopedContainer implements IEventContextContainer {
	public ComponentContainer(IEventContextContainer requestContainer) {
		super(requestContainer, ApplicationScope.COMPONENT);
		register(IBinding.class, instance(IBinding.class).createChild());
	}

	/**
	 * Creates an instance of the component.
	 * <p>
	 * The key behavior here is that this does not place the
	 * IEventProcessingContext that it creates into the RequestContainer. That
	 * position is held by the acting Controller. If a component is being
	 * invoked as a Controller, this does not get called.
	 */
	public <T extends IEventProcessingContext> T createEventContext(Class<T> key, ISailsEvent event) {
		T context = instance(key, key);
		instance(IBinding.class).mixin(context);
		return context;
	}
}
