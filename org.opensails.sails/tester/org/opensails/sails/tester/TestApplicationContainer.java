package org.opensails.sails.tester;

import org.opensails.rigging.ComponentImplementation;
import org.opensails.rigging.ComponentInstance;
import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.MapComponentResolverResolver;
import org.opensails.rigging.Startable;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.util.Quick;

public class TestApplicationContainer extends ApplicationContainer implements ITestScopedContainer {
    MapComponentResolverResolver injections = new MapComponentResolverResolver();
    MapComponentResolverResolver injectionsSinceLastStart = new MapComponentResolverResolver();

    public TestApplicationContainer() {
        super.push(injections);
    }

    public ITestScopedContainer getContainerInHierarchy(Enum scope) {
        return (ITestScopedContainer) super.getContainerInHierarchy(scope);
    }

    public void inject(Class<?> key, ComponentResolver resolver) {
        injections.put(key, resolver);
        injectionsSinceLastStart.put(key, resolver);
    }

    public <T> void inject(Class<? super T> key, Class<T> implementation) {
        ComponentImplementation resolver = new ComponentImplementation(key, implementation, this);
        injections.put(key, resolver);
        injectionsSinceLastStart.put(key, resolver);
    }

    public <T> void inject(Class<? super T> key, T instance) {
        ComponentInstance resolver = new ComponentInstance(instance);
        injections.put(key, resolver);
        injectionsSinceLastStart.put(key, resolver);
    }

    @Override
    public void start() {
        broadcast(Startable.class, allInstances(Quick.list(injectionsSinceLastStart), Startable.class, true)).start();
        injectionsSinceLastStart = new MapComponentResolverResolver();
    }
}
