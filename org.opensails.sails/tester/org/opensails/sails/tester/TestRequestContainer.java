package org.opensails.sails.tester;

import java.util.HashSet;
import java.util.Set;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.RequestContainer;

public class TestRequestContainer extends RequestContainer {
	protected Set<Class> injections = new HashSet<Class>();

	public TestRequestContainer(ScopedContainer parent) {
		super(parent);
	}

	@Override
	// to make public
	public void bind(ISailsEvent event) {
		super.bind(event);
	}

	public <T> void inject(Class<? extends T> key, Class<? extends T> implementation) {
		super.register(key, implementation);
		injections.add(key);
	}

	public <T> void inject(Class<? super T> key, T instance) {
		super.register(key, instance);
		injections.add(key);
	}

	@Override
	public <T> void register(Class<? extends T> key, Class<? extends T> implementation) {
		if (!injections.contains(key)) super.register(key, implementation);
	}

	@Override
	public <T> void register(Class<? super T> key, T instance) {
		if (!injections.contains(key)) super.register(key, instance);
	}
}
