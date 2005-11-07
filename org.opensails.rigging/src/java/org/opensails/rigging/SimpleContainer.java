package org.opensails.rigging;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SimpleContainer {
    protected MapComponentResolverResolver mapResolverResolver;
    protected List<IComponentResolverResolver> resolverResolvers;

    public SimpleContainer() {
        resolverResolvers = new ArrayList<IComponentResolverResolver>();
        mapResolverResolver = new MapComponentResolverResolver();
        push(mapResolverResolver);
    }

    public boolean contains(Class key) {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            if (resolverResolver.canResolve(key, this)) return true;
        return false;
    }

    public void dispose() {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            for (Class type : resolverResolver.keySet())
                if (Disposable.class.isAssignableFrom(type)) ((Disposable) instance(type)).dispose();
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

    public void start() {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            for (Class type : resolverResolver.keySet())
                if (Startable.class.isAssignableFrom(type)) ((Startable) instance(type)).start();
    }

    public void stop() {
        for (IComponentResolverResolver resolverResolver : resolverResolvers)
            for (Class type : resolverResolver.keySet())
                if (Stoppable.class.isAssignableFrom(type)) ((Stoppable) instance(type)).stop();
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
}
