package org.opensails.sails;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;

public class RequestContainer extends ScopedContainer {
	public RequestContainer(ScopedContainer parent) {
		super(parent, ApplicationScope.REQUEST);
		parent.addChild(this);
	}

	public RequestContainer(ScopedContainer parent, ISailsEvent event) {
		this(parent);
		bind(event);
	}

	@SuppressWarnings("unchecked")
	public <T extends IControllerImpl> T create(Class<T> key, ISailsEvent event) {
		IControllerImpl instance = super.instance(key, key);
		register(IControllerImpl.class, instance);
		return (T) instance;
	}

	@Override
	public void dispose() {
		super.dispose();
		getParent().removeChild(this);
	}

	protected void bind(ISailsEvent event) {
		register(ISailsEvent.class, event);
		register(event);
	}
}
