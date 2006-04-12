package org.opensails.rigging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SimpleContainer implements IContainer {
    protected Map<Class<?>, List<InstantiationListener<?>>> instantiationListeners;
    protected MapComponentResolverResolver mapResolverResolver;
    protected List<IComponentResolverResolver> resolverResolvers;

    public SimpleContainer() {
        resolverResolvers = new ArrayList<IComponentResolverResolver>();
        push(mapResolverResolver = new MapComponentResolverResolver());
        instantiationListeners = new HashMap<Class<?>, List<InstantiationListener<?>>>();
    }

    public Collection<Object> allInstances(boolean shouldInstantiate) {
        return allInstances(Object.class, shouldInstantiate);
    }

    public <T> Collection<T> allInstances(Class<T> type, boolean shouldInstantiate) {
        return allInstances(resolverResolvers, type, shouldInstantiate);
    }

    public <T> T broadcast(Class<T> type, boolean shouldInstantiate) {
        return broadcast(type, allInstances(type, shouldInstantiate));
    }

    public boolean contains(Class key) {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            if (resolverResolver.canResolve(key, this)) return true;
        return false;
    }

    public void dispose() {
        broadcast(Disposable.class, false).dispose();
    }

    public <T> T instance(Class<T> key) {
        ComponentResolver componentResolver = resolver(key);
        if (componentResolver == null) return null;
        return (T) componentResolver.instance();
    }

    public <T> T instance(Class<T> key, Class defaultImplementation) {
        if (!contains(key)) register(key, defaultImplementation);
        return instance(key);
    }

    public void notifyInstantiationListeners(Class<?> type, Object instance) {
        for (InstantiationListener listener : instantiationListenersForType(type))
            listener.instantiated(instance);
    }

    public void push(IComponentResolverResolver resolver) {
        resolverResolvers.add(0, resolver);
    }

    public <T> void register(Class<? super T> key, T instance) {
        registerResolver(key, new ComponentInstance(instance));
    }

    public <T> void register(Class<T> implementation) {
        register(implementation, implementation);
    }

    public <T> void register(Class<T> key, Class<? extends T> implementation) {
        registerResolver(key, new ComponentImplementation(key, implementation, this));
    }

    public <T> void register(T instance) {
        register((Class<T>) instance.getClass(), instance);
    }

    public void registerAll(SimpleContainer anotherContainer) {
        mapResolverResolver.putAll(anotherContainer.mapResolverResolver);
    }

    public <T> void registerInstantiationListener(Class<T> type, InstantiationListener<T> listener) {
        instantiationListenersForType(type).add(listener);
    }

    public <T> void registerResolver(Class<T> key, ComponentResolver resolver) {
        mapResolverResolver.put(key, resolver);
    }

    public <T> ComponentResolver resolver(Class<T> key) {
        for (IComponentResolverResolver resolverResolver : resolverResolvers) {
            ComponentResolver resolver = resolverResolver.resolve(key, this);
            if (resolver != null) return resolver;
        }
        return null;
    }

    public void start() {
        broadcast(Startable.class, true).start();
    }

    public void stop() {
        broadcast(Stoppable.class, false).stop();
    }

    protected <T> Collection<T> allInstances(Collection<? extends IComponentResolverResolver> resolvers, Class<T> type, boolean shouldInstantiate) {
        List<T> instances = new ArrayList<T>();
        for (IComponentResolverResolver resolverResolver : resolvers)
            for (Class key : resolverResolver.keySet()) {
                ComponentResolver resolver = resolverResolver.resolve(key, this);
                if (type.isAssignableFrom(resolver.type()) && (resolver.isInstantiated() || shouldInstantiate)) instances.add((T) resolver.instance());
            }
        return instances;
    }

    protected <T> T broadcast(Class<T> type, Collection<T> objects) {
        return Broadcast.to(type, objects);
    }

    protected <T> List<InstantiationListener<?>> instantiationListenersForType(Class<T> type) {
        if (!instantiationListeners.containsKey(type)) instantiationListeners.put(type, new ArrayList<InstantiationListener<?>>());
        return (List<InstantiationListener<?>>) instantiationListeners.get(type);
    }
}
