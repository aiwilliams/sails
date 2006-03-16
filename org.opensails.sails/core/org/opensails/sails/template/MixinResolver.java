package org.opensails.sails.template;

import org.opensails.rigging.IContainer;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.resolvers.CompositeClassResolver;

public class MixinResolver implements IMixinResolver {
	protected IControllerImpl controller;
	protected final CompositeClassResolver resolvers;
	private final IContainer container;

	public MixinResolver(IContainer container) {
		this.container = container;
		this.resolvers = new CompositeClassResolver();
	}

	public MixinResolver(ISailsEvent event) {
		this(event.getContainer());
	}

	@SuppressWarnings("unchecked")
	public Object methodMissing(String methodName, Object[] args) throws NoSuchMethodException {
		Class clazz = resolvers.resolve(methodName);
		if (clazz != null) {
			Object instance = container.instance(clazz, clazz);
			if (instance instanceof IMixinMethod) return ((IMixinMethod) instance).invoke(args);
			return instance;
		}
		throw new NoSuchMethodException("Could not resolve a mixin for " + methodName);
	}

	@SuppressWarnings("unchecked")
	public void push(IClassResolver<?> resolver) {
		resolvers.push(resolver);
	}
}
