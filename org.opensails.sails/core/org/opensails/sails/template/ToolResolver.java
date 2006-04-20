package org.opensails.sails.template;

import java.lang.reflect.Method;

import org.opensails.rigging.IContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.CompositeClassResolver;
import org.opensails.viento.CallableMethod;
import org.opensails.viento.IObjectResolver;
import org.opensails.viento.ObjectReference;
import org.opensails.viento.TopLevelMethodKey;
import org.opensails.viento.TopLevelMixin;

public class ToolResolver implements IObjectResolver {
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
	public void push(IClassResolver<?> resolver) {
		resolvers.push(resolver);
	}

	@SuppressWarnings("unchecked")
	public CallableMethod find(TopLevelMethodKey key) {
		Class clazz = resolvers.resolve(key.methodName);
		if (clazz == null) return null;
		Object instance = container.instance(clazz, clazz);
		Method invoke = SpyGlass.getMethod(instance.getClass(), "invoke");
		if (invoke != null) return new TopLevelMixin(invoke, instance);
		return new ObjectReference(instance);
	}
}
