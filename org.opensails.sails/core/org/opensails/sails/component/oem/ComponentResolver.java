package org.opensails.sails.component.oem;

import java.util.*;

import org.opensails.sails.adapter.*;
import org.opensails.sails.component.*;
import org.opensails.sails.template.*;
import org.opensails.sails.util.*;
import org.opensails.viento.*;

public class ComponentResolver implements IComponentResolver {
	protected final IAdapterResolver adapterResolver;
	protected final List<IClassResolver<? extends IComponentImpl>> classResolvers;
	protected final Map<String, Component<?>> cache;
	protected final ITemplateRenderer<IBinding> renderer;

	public ComponentResolver(IAdapterResolver adapterResolver, ITemplateRenderer<IBinding> renderer) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.renderer = renderer;
		this.adapterResolver = adapterResolver;
		this.classResolvers = new ArrayList<IClassResolver<? extends IComponentImpl>>();
		this.cache = new HashMap<String, Component<?>>();
	}

	public void push(IClassResolver<IComponentImpl> classResolver) {
		classResolvers.add(0, classResolver);
	}

	@SuppressWarnings("unchecked")
	public IComponent resolve(String identifier) {
		Component<?> component = cache.get(identifier);
		if (component == null) {
			Class<?> implementation = null;
			for (IClassResolver<?> resolver : classResolvers) {
				implementation = resolver.resolve(identifier);
				if (implementation != null) break;
			}
			cache.put(identifier, component = new Component(implementation, adapterResolver, renderer));
		}
		return component;
	}

	public boolean resolvesNamespace(String namespace) {
		return "Component".equals(namespace);
	}
}
