package org.opensails.sails.controller.oem;

import java.util.*;

import org.opensails.sails.adapter.*;
import org.opensails.sails.controller.*;
import org.opensails.sails.util.*;

public class ControllerResolver implements IControllerResolver {
	protected final IAdapterResolver adapterResolver;
	protected final List<IClassResolver<IControllerImpl>> classResolvers;
	protected final Map<String, Controller> controllerCache;

	public ControllerResolver(IAdapterResolver adapterResolver) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.adapterResolver = adapterResolver;
		this.classResolvers = new ArrayList<IClassResolver<IControllerImpl>>();
		this.controllerCache = new HashMap<String, Controller>();
	}

	public void push(IClassResolver<IControllerImpl> controllerClassResolver) {
		classResolvers.add(0, controllerClassResolver);
	}

	@SuppressWarnings("unchecked")
	public IController resolve(String controllerIdentifier) {
		Controller controller = controllerCache.get(controllerIdentifier);
		if (controller == null) {
			Class<? extends IControllerImpl> controllerImplementation = null;
			for (IClassResolver<IControllerImpl> resolver : classResolvers) {
				controllerImplementation = resolver.resolve(controllerIdentifier);
				if (controllerImplementation != null) break;
			}
			controllerCache.put(controllerIdentifier, controller = new Controller(controllerImplementation, adapterResolver));
		}
		return controller;
	}

	public boolean resolvesNamespace(String namespace) {
		return "Controller".equals(namespace);
	}
}
