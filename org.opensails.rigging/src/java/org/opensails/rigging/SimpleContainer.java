package org.opensails.rigging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SimpleContainer {
    protected MapComponentResolverResolver mapResolverResolver;
    protected List<IComponentResolverResolver> resolverResolvers;
	protected Map<Class<?>, List<InstantiationListener<?>>> instantiationListeners;

    public SimpleContainer() {
        resolverResolvers = new ArrayList<IComponentResolverResolver>();
        mapResolverResolver = new MapComponentResolverResolver();
        push(mapResolverResolver);
        instantiationListeners = new HashMap<Class<?>, List<InstantiationListener<?>>>();
    }

    public boolean contains(Class key) {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            if (resolverResolver.canResolve(key, this)) return true;
        return false;
    }
    
    /**
     * Only disposes things that are already instantiated.
     */
    public void dispose() {
    		broadcast(Disposable.class, false).dispose();
    }

    public <T> T instance(Class<T> key) {
        ComponentResolver componentResolver = resolveResolver(key);
        if (componentResolver == null) return null;
        return (T) componentResolver.instance();
    }

    public <T> T instance(Class<T> key, Class defaultImplementation) {
        if (!contains(key)) register(key, defaultImplementation);
        return instance(key);
    }

    public void push(IComponentResolverResolver resolver) {
        resolverResolvers.add(0, resolver);
    }

    public void register(Class<?> implementation) {
        register(implementation, implementation);
    }

    public void register(Class<?> key, ComponentResolver resolver) {
        mapResolverResolver.put(key, resolver);
    }

    public <T> void register(Class<? extends T> key, Class<? extends T> implementation) {
        register(key, new ComponentImplementation(implementation, this));
    }

    public <T> void register(Class<? super T> key, T instance) {
        register(key, new ComponentInstance(instance));
    }

    public <T> void register(T instance) {
        register((Class<T>) instance.getClass(), instance);
    }

    /**
     * Starts everything, even if it has to be instantiated.
     */
    public void start() {
        broadcast(Startable.class, true).start();
    }

    /**
     * Only stops things that are already instantiated.
     */
    public void stop() {
    		broadcast(Stoppable.class, false).stop();
    }

    protected <T> ComponentResolver resolveResolver(Class<T> key) {
        for (IComponentResolverResolver resolverResolver : resolverResolvers) {
            ComponentResolver resolver = resolverResolver.resolve(key, this);
            if (resolver != null) return resolver;
        }
        return null;
    }

    /**
	 * Registers all anotherContainer's component resolvers with this container.
	 * Does not currently do anything with the miscellaneous resolverResolvers.
	 * 
	 * @param anotherContainer
	 */
	public void registerAll(SimpleContainer anotherContainer) {
		mapResolverResolver.componentResolvers.putAll(anotherContainer.mapResolverResolver.componentResolvers);
	}

	public <T> T broadcast(Class<T> type, boolean shouldInstantiate) {
		return Broadcast.to(type, allInstances(type, shouldInstantiate));
	}

	public Collection<Object> allInstances(boolean shouldInstantiate) {
		return allInstances(Object.class, shouldInstantiate);
	}
	
	/**
	 * Even if shouldInstantiate is true, it won't instantiate objects that do not subclass type. 
	 * @return all instances that subclass the given type.
	 */
	public <T> Collection<T> allInstances(Class<T> type, boolean shouldInstantiate) {
		List<T> instances = new ArrayList<T>();
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            for (Class key : new ArrayList<Class>(resolverResolver.keySet())) {
            	ComponentResolver resolver = resolverResolver.resolve(key, this);
                if (type.isAssignableFrom(resolver.type()) && (resolver.isInstantiated() || shouldInstantiate))
                	instances.add((T) resolver.instance());
            }
        return instances;
    }

	public <T> void registerInstantiationListener(Class<T> type, InstantiationListener<T> listener) {
		instantiationListenersForType(type).add(listener);
	}
	
	protected <T> List<InstantiationListener<?>> instantiationListenersForType(Class<T> type) {
		if (!instantiationListeners.containsKey(type))
			instantiationListeners.put(type, new ArrayList<InstantiationListener<?>>());
			
		return (List<InstantiationListener<?>>) instantiationListeners.get(type);
	}
	
	protected void notifyInstantiationListeners(Class<?> type, Object instance) {
		for (InstantiationListener listener : instantiationListenersForType(type))
			listener.instantiated(instance);
	}
}
