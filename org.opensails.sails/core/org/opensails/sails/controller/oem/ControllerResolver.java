package org.opensails.sails.controller.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.resolvers.CompositeClassResolver;

public class ControllerResolver implements IControllerResolver {
	protected final IAdapterResolver adapterResolver;
	protected final CompositeClassResolver<IControllerImpl> classResolvers;
	protected final Map<String, Controller> controllerCache;

	public ControllerResolver(IAdapterResolver adapterResolver) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.adapterResolver = adapterResolver;
		this.classResolvers = new CompositeClassResolver<IControllerImpl>();
		this.controllerCache = new HashMap<String, Controller>();
	}

	public void push(IClassResolver<IControllerImpl> controllerClassResolver) {
		classResolvers.push(controllerClassResolver);
	}

	@SuppressWarnings("unchecked")
	public IController resolve(String controllerIdentifier) {
		Controller controller = controllerCache.get(controllerIdentifier);
		if (controller == null) {
			Class<? extends IControllerImpl> controllerImplementation = classResolvers.resolve(controllerIdentifier);
			controllerCache.put(controllerIdentifier, controller = new Controller(controllerImplementation, adapterResolver));
		}
		return controller;
	}

	public boolean resolvesNamespace(String namespace) {
		return NAMESPACE.equals(namespace);
	}
}
