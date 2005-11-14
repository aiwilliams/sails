package org.opensails.sails.tester;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.opensails.rigging.ComponentImplementation;
import org.opensails.rigging.ComponentInstance;
import org.opensails.rigging.ComponentResolver;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.RequestContainer;

public class TestRequestContainer extends RequestContainer {
	protected Map<Class, ComponentResolver> injections = new HashMap<Class, ComponentResolver>();

	public TestRequestContainer(ScopedContainer parent) {
		super(parent);
	}

	@SuppressWarnings("unchecked")
	public TestRequestContainer(ScopedContainer parent, Map<Class, ComponentResolver> injections) {
		super(parent);
		for (Entry injection : injections.entrySet())
			inject((Class) injection.getKey(), (ComponentResolver) injection.getValue());
	}

	@Override
	// to make public
	public void bind(ISailsEvent event) {
		super.bind(event);
	}

	public void inject(Class key, ComponentResolver resolver) {
		super.register(key, resolver);
		injections.put(key, resolver);
	}

	public <T> void inject(Class<? extends T> key, Class<? extends T> implementation) {
		ComponentImplementation resolver = new ComponentImplementation(implementation, this);
		super.register(key, resolver);
		injections.put(key, resolver);
	}

	public <T> void inject(Class<? super T> key, T instance) {
		ComponentInstance resolver = new ComponentInstance(instance);
		super.register(key, resolver);
		injections.put(key, resolver);
	}

	@Override
	public void register(Class<?> key, ComponentResolver resolver) {
		if (!injections.containsKey(key)) super.register(key, resolver);
	}

	@Override
	public <T> void register(Class<? extends T> key, Class<? extends T> implementation) {
		if (!injections.containsKey(key)) super.register(key, implementation);
	}

	@Override
	public <T> void register(Class<? super T> key, T instance) {
		if (!injections.containsKey(key)) super.register(key, instance);
	}
}
