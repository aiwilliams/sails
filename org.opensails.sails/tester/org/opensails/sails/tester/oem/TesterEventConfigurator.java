package org.opensails.sails.tester.oem;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.opensails.rigging.ComponentImplementation;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.configurator.oem.DefaultEventConfigurator;
import org.opensails.sails.configurator.oem.RequiredEventConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.viento.IBinding;

/**
 * Wraps the real IEventConfigurator of the application so that some testability
 * instrumentation can happen.
 * <p>
 * It is important that configuration only happens once. This configurator
 * allows for the configuration of an event before it is dispatched to the
 * application. In order to guarantee a single configuration, once this has seen
 * a particular event, it will do nothing on subsequent calls with that event.
 * <p>
 * This understands that the {@link RequiredEventConfigurator} is the one being
 * delegated to. As such, it must implement the
 * {@link #configure(ISailsEvent, IEventContextContainer)} method in a manner
 * very similar to that of the RequiredEventConfigurator - it determines the
 * type of the container and calls the appropriate method on itself.
 * 
 * @author aiwilliams
 */
public class TesterEventConfigurator extends DefaultEventConfigurator {
	/*
	 * Let's forget about events no one else cares about anymore.
	 */
	protected WeakHashMap<ISailsEvent, List<Class>> configured = new WeakHashMap<ISailsEvent, List<Class>>();
	protected IEventConfigurator delegate;

	public TesterEventConfigurator(IEventConfigurator delegate) {
		this.delegate = delegate;
	}

	@Override
	public void configure(ISailsEvent event, ComponentContainer componentContainer) {}

	@Override
	public void configure(ISailsEvent event, IBinding binding) {
		delegate.configure(event, binding);
	}

	@Override
	public void configure(ISailsEvent event, IEventContextContainer eventContainer) {
		if (isConfigured(event, IEventContextContainer.class)) return;

		delegate.configure(event, eventContainer);
		if (eventContainer instanceof RequestContainer) configure(event, (RequestContainer) eventContainer);
		if (eventContainer instanceof ComponentContainer) configure(event, (ComponentContainer) eventContainer);
	}

	@Override
	public void configure(ISailsEvent event, RequestContainer requestContainer) {
		/*
		 * Expose the same instance as two types.
		 * 
		 * As far as instantiation listeners go, no application code should be
		 * listening for the TestingBinding, but those that do care about the
		 * IBinding should hear.
		 */
		ComponentImplementation bindingComponent = new ComponentImplementation(IBinding.class, TestingBinding.class, requestContainer);
		requestContainer.registerResolver(IBinding.class, bindingComponent);
		requestContainer.registerResolver(TestingBinding.class, bindingComponent);
	}

	private boolean isConfigured(ISailsEvent event, Class what) {
		List<Class> alreadyConfigured = configured.get(event);
		if (alreadyConfigured == null) {
			ArrayList<Class> configuredFor = new ArrayList<Class>();
			configuredFor.add(what);
			configured.put(event, configuredFor);
			return false;
		} else if (!alreadyConfigured.contains(what)) {
			alreadyConfigured.add(what);
			return false;
		} else return true;
	}
}
