package org.opensails.sails.tester;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.rigging.ComponentImplementation;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.ISailsEventConfigurator;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.DelegatingConfigurator;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.oem.ErrorController;
import org.opensails.sails.tester.oem.LazyActionResultProcessor;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.persist.IShamObjectPersister;
import org.opensails.sails.tester.persist.MemoryObjectPersister;
import org.opensails.sails.util.IClassResolver;
import org.opensails.viento.IBinding;

public class SailsTesterConfigurator extends DelegatingConfigurator {
	protected List<ISailsEvent> configured = new ArrayList<ISailsEvent>();

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
				IActionResultProcessor processor = delegatesResolver.resolve(result);
				if (result instanceof RedirectActionResult) return processor;
				return new LazyActionResultProcessor(processor);
			}
		};
		container.register(IActionResultProcessorResolver.class, interceptingResolver);
		return interceptingResolver;
	}

	@Override
	protected void installConfigurator(IConfigurableSailsApplication application) {
		application.setConfigurator(this);
	}

	@Override
	protected ScopedContainer installContainer(IConfigurableSailsApplication application) {
		ScopedContainer container = super.installContainer(application);
		container.register(ISailsEventConfigurator.class, this);
		return container;
	}

	@Override
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		final ControllerResolver controllerResolver = super.installControllerResolver(application, container);
		ControllerResolver testResolver = new ControllerResolver(container.instance(IAdapterResolver.class)) {
			@Override
			public void push(IClassResolver<IControllerImpl> controllerClassResolver) {
				controllerResolver.push(controllerClassResolver);
			}

			@Override
			public IController resolve(String controllerIdentifier) {
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

	@Override
	public void configure(ISailsEvent event, RequestContainer eventContainer) {
		if (configured.contains(event)) return;

		super.configure(event, eventContainer);

		// Expose the same instance as two types
		ComponentImplementation bindingComponent = new ComponentImplementation(TestingBinding.class, eventContainer);
		eventContainer.registerResolver(IBinding.class, bindingComponent);
		eventContainer.registerResolver(TestingBinding.class, bindingComponent);

		configured.add(event);
	}
}
