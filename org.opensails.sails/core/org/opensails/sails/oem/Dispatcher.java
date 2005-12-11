package org.opensails.sails.oem;

import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.IActionResultProcessorResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEventConfigurator;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.oem.IControllerResolver;

public class Dispatcher {
	protected final ISailsApplication application;
	protected final IControllerResolver controllerResolver;
	protected final ISailsEventConfigurator eventConfigurator;
	protected final IActionResultProcessorResolver processorResolver;

	public Dispatcher(ISailsApplication application, ISailsEventConfigurator configurator, IControllerResolver controllerResolver, IActionResultProcessorResolver processorResolver) {
		this.application = application;
		this.eventConfigurator = configurator;
		this.controllerResolver = controllerResolver;
		this.processorResolver = processorResolver;
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
		IController controller = controllerResolver.resolve(event.getControllerName());
		// delegate to event so that it calls most specific process
		IActionResult result = event.visit(controller);
		IActionResultProcessor processor = processorResolver.resolve(result);
		processor.process(result);
	}
}
