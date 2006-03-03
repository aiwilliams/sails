package org.opensails.rigging;

import java.util.Collection;

public interface IContainer {
    Collection<Object> allInstances(boolean shouldInstantiate);

    /**
     * Even if shouldInstantiate is true, it won't instantiate objects that do
     * not subclass type.
     * 
     * @return all instances that subclass the given type.
     */
    <T> Collection<T> allInstances(Class<T> type, boolean shouldInstantiate);

    <T> T broadcast(Class<T> type, boolean shouldInstantiate);

    /**
     * @param key
     * @return true if this container has a component for key
     */
    boolean contains(Class key);

    /**
     * Only disposes things that are already instantiated.
     */
    void dispose();

    <T> T instance(Class<T> key);

    <T> T instance(Class<T> key, Class defaultImplementation);

    void notifyInstantiationListeners(Class<?> type, Object instance);

    void push(IComponentResolverResolver resolver);

    <T> void register(Class<? super T> key, T instance);

    <T> void register(Class<T> implementation);

    <T> void register(Class<T> key, Class<? extends T> implementation);

    <T> void register(T instance);

    /**
     * Registers all anotherContainer's component resolvers with this container.
     * Does not currently do anything with the miscellaneous resolverResolvers.
     * 
     * @param anotherContainer
     */
    void registerAll(SimpleContainer anotherContainer);

    <T> void registerInstantiationListener(Class<T> type, InstantiationListener<T> listener);

    <T> void registerResolver(Class<T> key, ComponentResolver resolver);

    <T> ComponentResolver resolver(Class<T> key);

    /**
     * Starts everything, even if it has to be instantiated.
     */
    void start();

    /**
     * Only stops things that are already instantiated.
     */
    void stop();
}