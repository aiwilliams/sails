package org.opensails.sails.template;

import org.opensails.rigging.IContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.resolvers.CompositeClassResolver;
import org.opensails.viento.MethodMissing;

public class ToolResolver implements MethodMissing {
	protected final CompositeClassResolver resolvers;
	private final IContainer container;

	public ToolResolver(IContainer container) {
		this.container = container;
		this.resolvers = new CompositeClassResolver();
	}

	public ToolResolver(ISailsEvent event) {
		this(event.getContainer());
	}

	@SuppressWarnings("unchecked")
	public Object methodMissing(String methodName, Object[] args) {
		Class clazz = resolvers.resolve(methodName);
		if (clazz == null) return null;
		Object instance = container.instance(clazz, clazz);
		if (instance instanceof IMixinMethod) return ((IMixinMethod) instance).invoke(args);
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void push(IClassResolver<?> resolver) {
		resolvers.push(resolver);
	}
}
