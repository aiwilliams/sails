package org.opensails.sails.oem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.IActionResultProcessorResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsEventConfigurator;
import org.opensails.sails.controller.IAction;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.controller.oem.IControllerResolver;

public class Dispatcher {
	protected static final Log DISPATCHER_LOG = LogFactory.getLog(Dispatcher.class);
	protected static final Log ACTION_LOG = LogFactory.getLog(IAction.class);
	protected static final Log RESULT_LOG = LogFactory.getLog(IActionResultProcessor.class);

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
		DISPATCHER_LOG.debug(event);
		beginDispatch(event);
		try {
			process(event);
		} catch (Throwable t) {
			dispatch(new ExceptionEvent(event, t));
		}
		endDispatch(event);
		DISPATCHER_LOG.debug(event);
	}

	public void dispatch(PostEvent event) {
		dispatch((ILifecycleEvent) event);
	}

	protected void beginDispatch(ILifecycleEvent event) {
		installContainer(event);
		event.beginDispatch();
	}

	protected void endDispatch(ILifecycleEvent event) {
		event.endDispatch();
	}

	protected void installContainer(ILifecycleEvent event) {
		eventConfigurator.configure(event, event.getContainer());
	}

	@SuppressWarnings("unchecked")
	protected void process(ILifecycleEvent event) {
		Controller controller = controllerResolver.resolve(event.getControllerName());

		ACTION_LOG.debug(event);
		// delegate to event so that is calls most specific process
		IActionResult result = event.visit(controller);
		ACTION_LOG.debug(event);

		RESULT_LOG.debug(event);
		IActionResultProcessor processor = processorResolver.resolve(result);
		processor.process(result);
		RESULT_LOG.debug(event);
	}
}
