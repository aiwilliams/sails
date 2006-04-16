package org.opensails.sails.component;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.viento.IBinding;

public class ComponentContainer extends ScopedContainer implements IEventContextContainer {
	public ComponentContainer(IEventContextContainer requestContainer) {
		super(requestContainer, ApplicationScope.COMPONENT);
		register(IBinding.class, instance(IBinding.class).createChild());
	}

	public <T extends IEventProcessingContext> T createEventContext(Class<T> key, ISailsEvent event) {
		T context = instance(key, key);
		instance(IBinding.class).mixin(context);
		return context;
	}
}
