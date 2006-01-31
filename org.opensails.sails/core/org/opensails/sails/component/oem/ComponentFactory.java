package org.opensails.sails.component.oem;

import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.util.ClassHelper;
import org.opensails.viento.Name;

/**
 * Used only when a component is created from another template - not when
 * invoked through a callback.
 * 
 * @author aiwilliams
 */
public class ComponentFactory {
	private final ISailsEvent event;
	private final IComponent component;

	public ComponentFactory(IComponent component, ISailsEvent event) {
		this.component = component;
		this.event = event;
	}

	@Name("new")
	public IComponentImpl create(Object... args) {
		IComponentImpl impl = component.createInstance(event);
		// TODO: Provide more information on failure
		ClassHelper.callMethod(impl, "initialize", args);
		return impl;
	}
}
