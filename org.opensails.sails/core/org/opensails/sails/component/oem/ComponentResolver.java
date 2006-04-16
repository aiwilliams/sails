package org.opensails.sails.component.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.resolvers.CompositeClassResolver;
import org.opensails.viento.IBinding;

public class ComponentResolver implements IComponentResolver {
	protected final IAdapterResolver adapterResolver;
	protected final Map<String, Component> cache;
	protected final CompositeClassResolver<IComponentImpl> classResolvers;
	protected final ITemplateRenderer<IBinding> renderer;
	protected final IEventConfigurator eventConfigurator;

	public ComponentResolver(IAdapterResolver adapterResolver, IEventConfigurator eventConfigurator, ITemplateRenderer<IBinding> renderer) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.renderer = renderer;
		this.adapterResolver = adapterResolver;
		this.eventConfigurator = eventConfigurator;
		this.classResolvers = new CompositeClassResolver<IComponentImpl>();
		this.cache = new HashMap<String, Component>();
	}

	public void push(IClassResolver<IComponentImpl> resolver) {
		classResolvers.push(resolver);
	}

	@SuppressWarnings("unchecked")
	public IComponent resolve(String identifier) {
		Component<?> component = cache.get(identifier);
		if (component == null) {
			Class<IComponentImpl> implementation = classResolvers.resolve(identifier);
			cache.put(identifier, component = new Component(identifier, implementation, eventConfigurator, adapterResolver, renderer));
		}
		return component;
	}

	public boolean resolvesNamespace(String namespace) {
		return "Component".equals(namespace);
	}
}
