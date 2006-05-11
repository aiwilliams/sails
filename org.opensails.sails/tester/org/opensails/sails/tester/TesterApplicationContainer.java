package org.opensails.sails.tester;

import org.opensails.rigging.ComponentImplementation;
import org.opensails.rigging.ComponentInstance;
import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.MapComponentResolverResolver;
import org.opensails.rigging.Startable;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.util.Quick;

public class TesterApplicationContainer extends ApplicationContainer implements ITesterScopedContainer {
	MapComponentResolverResolver injections = new MapComponentResolverResolver();
	MapComponentResolverResolver injectionsSinceLastStart = new MapComponentResolverResolver();

	public TesterApplicationContainer() {
		super.push(injections);
	}

	public ITesterScopedContainer getContainerInHierarchy(Enum scope) {
		return (ITesterScopedContainer) super.getContainerInHierarchy(scope);
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

	public void startInjections() {
		broadcast(Startable.class, allInstances(Quick.list(injectionsSinceLastStart), Startable.class, true)).start();
		injectionsSinceLastStart = new MapComponentResolverResolver();
	}
}
