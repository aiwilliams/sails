package org.opensails.sails.helper.oem;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.helper.IHelperMethod;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.util.IClassResolver;

public class HelperResolver implements IHelperResolver {
	protected IControllerImpl controller;
	protected final ISailsEvent event;
	protected final List<IClassResolver> resolvers;

	public HelperResolver(ISailsEvent event) {
		this.event = event;
		this.resolvers = new ArrayList<IClassResolver>();
	}

	@SuppressWarnings("unchecked")
	public Object methodMissing(String methodName, Object[] args) throws NoSuchMethodException {
		for (IClassResolver resolver : resolvers) {
			Class helperClass = resolver.resolve(methodName);
			if (helperClass != null) {
				Object helperInstance = event.getContainer().instance(helperClass, helperClass);
				if (helperInstance instanceof IHelperMethod) return ((IHelperMethod) helperInstance).invoke(args);
				return helperInstance;
			}
		}
		throw new NoSuchMethodException("Could not resolve a helper for " + methodName);
	}

	public void push(IClassResolver resolver) {
		resolvers.add(0, resolver);
	}
}
