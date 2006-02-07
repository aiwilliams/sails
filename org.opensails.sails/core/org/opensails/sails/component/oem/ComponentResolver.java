package org.opensails.sails.component.oem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.util.IClassResolver;
import org.opensails.viento.IBinding;

public class ComponentResolver implements IComponentResolver {
	protected final IAdapterResolver adapterResolver;
	protected final Map<String, Component<?>> cache;
	protected final List<IClassResolver<? extends IComponentImpl>> classResolvers;
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
			cache.put(identifier, component = new Component(identifier, implementation, adapterResolver, renderer));
		}
		return component;
	}

	public boolean resolvesNamespace(String namespace) {
		return "Component".equals(namespace);
	}
}
