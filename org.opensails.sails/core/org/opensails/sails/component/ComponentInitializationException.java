package org.opensails.sails.component;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;

public class ComponentInitializationException extends SailsException {
	private static final long serialVersionUID = -9070657945543666220L;

	protected final ISailsEvent event;
	protected final IComponent component;
	protected final Object[] args;

	public ComponentInitializationException(ISailsEvent event, IComponent component, Object[] args, Throwable cause) {
		super(cause);
		this.event = event;
		this.component = component;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return component.getImplementation() + " threw exception during initialization";
	}
}
