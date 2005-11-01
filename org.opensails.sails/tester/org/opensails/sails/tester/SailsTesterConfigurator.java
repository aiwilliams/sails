package org.opensails.sails.tester;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.IActionResultProcessorResolver;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.controller.oem.IControllerResolver;
import org.opensails.sails.oem.ActionResultProcessorResolver;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.ControllerResolver;
import org.opensails.sails.oem.DelegatingConfigurator;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.ExceptionEvent;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.oem.ErrorController;
import org.opensails.sails.tester.oem.LazyActionResultProcessor;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.persist.IShamObjectPersister;
import org.opensails.sails.tester.persist.MemoryObjectPersister;
import org.opensails.sails.util.IClassResolver;

public class SailsTesterConfigurator extends DelegatingConfigurator {
	public SailsTesterConfigurator(Class<? extends BaseConfigurator> delegateClass) {
		super(delegateClass);
	}

	@Override
	protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
		super.configureName(application, configuration);
		application.setName("[TESTING]" + application.getName());
	}

	@Override
	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		final ActionResultProcessorResolver delegatesResolver = super.installActionResultProcessorResolver(application, container);
		ActionResultProcessorResolver interceptingResolver = new ActionResultProcessorResolver(container) {
			@Override
			public void push(IClassResolver<? extends IActionResultProcessor> resolver) {
				delegatesResolver.push(resolver);
			}

			@Override
			public IActionResultProcessor resolve(IActionResult result) {
				return new LazyActionResultProcessor(delegatesResolver.resolve(result));
			}
		};
		container.register(IActionResultProcessorResolver.class, interceptingResolver);
		return interceptingResolver;
	}

	@Override
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		final ControllerResolver controllerResolver = super.installControllerResolver(application, container);
		ControllerResolver testResolver = new ControllerResolver(container.instance(IAdapterResolver.class)) {
			@Override
			public void push(IClassResolver<IController> controllerClassResolver) {
				controllerResolver.push(controllerClassResolver);
			}

			@Override
			public Controller resolve(String controllerIdentifier) {
				return ExceptionEvent.CONTROLLER_NAME.equals(controllerIdentifier) ? new ErrorController() : controllerResolver.resolve(controllerIdentifier);
			}
		};
		container.register(IControllerResolver.class, testResolver);
		return testResolver;
	}

	@Override
	protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ScopedContainer container) {
		container.register(Dispatcher.class, TestingDispatcher.class);
		return super.installDispatcher(application, container);
	}

	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		IShamObjectPersister persister = new MemoryObjectPersister();
		container.register(IObjectPersister.class, persister);
		container.register(IShamObjectPersister.class, persister);
		super.installObjectPersister(application, container);
	}
}
