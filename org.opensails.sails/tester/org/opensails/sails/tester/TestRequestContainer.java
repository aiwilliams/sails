package org.opensails.sails.tester;

import java.util.HashMap;
import java.util.Map;

import org.opensails.rigging.ComponentImplementation;
import org.opensails.rigging.ComponentInstance;
import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;

public class TestRequestContainer extends RequestContainer implements ITestScopedContainer {
    protected Map<Class<?>, ComponentResolver> injections = new HashMap<Class<?>, ComponentResolver>();

    public TestRequestContainer(IScopedContainer parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    public TestRequestContainer(IScopedContainer parent, Map<Class<?>, ComponentResolver> injections) {
        super(parent);
        for (Map.Entry<Class<?>, ComponentResolver> injection : injections.entrySet())
            inject(injection.getKey(), injection.getValue());
    }

    @Override
    // to make public
    public void bind(ISailsEvent event) {
        super.bind(event);
    }

    @Override
    public ITestScopedContainer getContainerInHierarchy(Enum scope) {
        return (ITestScopedContainer) super.getContainerInHierarchy(scope);
    }

    public void inject(Class<?> key, ComponentResolver resolver) {
        super.registerResolver(key, resolver);
        injections.put(key, resolver);
    }

    public <T> void inject(Class<? super T> key, Class<T> implementation) {
        ComponentImplementation resolver = new ComponentImplementation(implementation, this);
        super.registerResolver(key, resolver);
        injections.put(key, resolver);
    }

    public <T> void inject(Class<? super T> key, T instance) {
        ComponentInstance resolver = new ComponentInstance(instance);
        super.registerResolver(key, resolver);
        injections.put(key, resolver);
    }

    @Override
    public <T> void register(Class<T> key, Class<? extends T> implementation) {
        if (!injections.containsKey(key)) super.register(key, implementation);
    }

    @Override
    public <T> void registerResolver(Class<T> key, ComponentResolver resolver) {
        if (!injections.containsKey(key)) super.registerResolver(key, resolver);
    }
}
