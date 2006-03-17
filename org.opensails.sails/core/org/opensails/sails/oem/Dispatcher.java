package org.opensails.sails.oem;

import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.event.*;
import org.opensails.sails.event.oem.*;

public class Dispatcher {
	protected final ISailsApplication application;
	protected final IActionEventProcessorResolver eventProcessorResolver;
	protected final IEventConfigurator eventConfigurator;
	protected final IActionResultProcessorResolver resultProcessorResolver;

	public Dispatcher(ISailsApplication application, IEventConfigurator configurator, IActionEventProcessorResolver eventProcessorResolver, IActionResultProcessorResolver resultProcessorResolver) {
		this.application = application;
		this.eventConfigurator = configurator;
		this.eventProcessorResolver = eventProcessorResolver;
		this.resultProcessorResolver = resultProcessorResolver;
	}

	public void dispatch(ExceptionEvent event) {
		event.beginDispatch();
		process(event);
		event.endDispatch();
	}

	public void dispatch(GetEvent event) {
		dispatch((ILifecycleEvent) event);
	}

	public void dispatch(ILifecycleEvent event) {
		beginDispatch(event);
		try {
			process(event);
		} catch (Throwable t) {
			dispatch(new ExceptionEvent(event, t));
		}
		endDispatch(event);
	}

	public void dispatch(PostEvent event) {
		dispatch((ILifecycleEvent) event);
	}

	protected void beginDispatch(ILifecycleEvent event) {
		configureContainer(event);
		event.beginDispatch();
	}

	protected void configureContainer(ILifecycleEvent event) {
		eventConfigurator.configure(event, event.getContainer());
	}

	protected void endDispatch(ILifecycleEvent event) {
		event.endDispatch();
	}

	@SuppressWarnings("unchecked")
	protected void process(ILifecycleEvent event) {
		IActionEventProcessor eventProcessor = eventProcessorResolver.resolve(event.getProcessorName());
		// delegate to event so that it calls most specific process
		IActionResult result = event.visit(eventProcessor);
		resultProcessorResolver.resolve(result).process(result);
	}
}
