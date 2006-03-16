package org.opensails.sails.tester;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.rigging.ComponentImplementation;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
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
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.oem.ErrorController;
import org.opensails.sails.tester.oem.LazyActionResultProcessor;
import org.opensails.sails.tester.oem.TestingBinding;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.oem.VirtualAdapterResolver;
import org.opensails.sails.tester.oem.VirtualControllerResolver;
import org.opensails.sails.tester.oem.VirtualResourceResolver;
import org.opensails.sails.tester.persist.IShamObjectPersister;
import org.opensails.sails.tester.persist.MemoryObjectPersister;
import org.opensails.spyglass.IClassResolver;
import org.opensails.viento.IBinding;

public class SailsTesterConfigurator extends DelegatingConfigurator {
	protected List<ISailsEvent> configured = new ArrayList<ISailsEvent>();

	public SailsTesterConfigurator(Class<? extends BaseConfigurator> delegateClass) {
		super(delegateClass);
	}

	@Override
	public void configure(ISailsEvent event, IEventContextContainer eventContainer) {
		if (configured.contains(event)) return;

		super.configure(event, eventContainer);

		// Expose the same instance as two types
		ComponentImplementation bindingComponent = new ComponentImplementation(TestingBinding.class, eventContainer);
		eventContainer.registerResolver(IBinding.class, bindingComponent);
		eventContainer.registerResolver(TestingBinding.class, bindingComponent);

		configured.add(event);
	}

	@Override
	protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
		super.configureName(application, configuration);
		application.setName("[TESTING]" + application.getName());
	}

	@Override
	protected ApplicationContainer createApplicationContainer(IConfigurableSailsApplication application) {
		TestApplicationContainer testApplicationContainer = new TestApplicationContainer();
		testApplicationContainer.register(testApplicationContainer);
		return testApplicationContainer;
	}

	@Override
	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
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
	protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		AdapterResolver adapterResolver = super.installAdapterResolver(application, container);
		VirtualAdapterResolver virtualAdapterResolver = new VirtualAdapterResolver();
		adapterResolver.push(virtualAdapterResolver);
		container.register(virtualAdapterResolver);
		return adapterResolver;
	}

	@Override
	protected void installConfigurator(IConfigurableSailsApplication application) {
		application.setConfigurator(this);
	}

	@Override
	protected ApplicationContainer installContainer(IConfigurableSailsApplication application) {
		ApplicationContainer container = super.installContainer(application);
		container.register(ISailsEventConfigurator.class, this);
		return container;
	}

	@Override
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		final VirtualControllerResolver virtualControllerResolver = container.instance(VirtualControllerResolver.class, VirtualControllerResolver.class);
		final ControllerResolver controllerResolver = super.installControllerResolver(application, container);
		ControllerResolver testResolver = new ControllerResolver(container.instance(IAdapterResolver.class)) {
			@Override
			public void push(IClassResolver<IControllerImpl> controllerClassResolver) {
				controllerResolver.push(controllerClassResolver);
			}

			@Override
			public IController resolve(String controllerIdentifier) {
				IController controller = virtualControllerResolver.resolve(controllerIdentifier);
				if (controller == null) controller = ExceptionEvent.CONTROLLER_NAME.equals(controllerIdentifier) ? new ErrorController()
						: controllerResolver.resolve(controllerIdentifier);
				return controller;
			}
		};
		container.register(IControllerResolver.class, testResolver);
		return testResolver;
	}

	@Override
	protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ApplicationContainer container) {
		container.register(Dispatcher.class, TestingDispatcher.class);
		return super.installDispatcher(application, container);
	}

	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ApplicationContainer container) {
		IShamObjectPersister persister = new MemoryObjectPersister();
		container.register(IObjectPersister.class, persister);
		container.register(IShamObjectPersister.class, persister);
		super.installObjectPersister(application, container);
	}

	@Override
	protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		ResourceResolver resourceResolver = super.installResourceResolver(application, container);
		VirtualResourceResolver virtualResourceResolver = new VirtualResourceResolver();
		resourceResolver.push(virtualResourceResolver);
		container.register(virtualResourceResolver);
		return resourceResolver;
	}
}
