package org.opensails.sails.tester;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.action.oem.RedirectActionResult;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.configurator.DelegatingConfigurator;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.configurator.IFormProcessingConfigurator;
import org.opensails.sails.configurator.IObjectPersisterConfigurator;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.configurator.oem.RequiredEventConfigurator;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.tester.oem.ErrorController;
import org.opensails.sails.tester.oem.LazyActionResultProcessor;
import org.opensails.sails.tester.oem.TesterEventConfigurator;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.oem.VirtualAdapterResolver;
import org.opensails.sails.tester.oem.VirtualControllerResolver;
import org.opensails.sails.tester.oem.VirtualResourceResolver;
import org.opensails.spyglass.IClassResolver;

public class TesterApplicationConfigurator extends DelegatingConfigurator {
	public TesterApplicationConfigurator(Class<? extends SailsConfigurator> delegateClass) {
		super(delegateClass);
	}

	@Override
	public IEventConfigurator getEventConfigurator() {
		return new TesterEventConfigurator(super.getEventConfigurator());
	}

	@Override
	protected void configureName() {
		super.configureName();
		application.setName("[TESTING]" + application.getName());
	}

	@Override
	protected ApplicationContainer createApplicationContainer() {
		TesterApplicationContainer testApplicationContainer = new TesterApplicationContainer();
		testApplicationContainer.register(testApplicationContainer);
		return testApplicationContainer;
	}

	@Override
	protected ActionResultProcessorResolver installActionResultProcessorResolver() {
		final ActionResultProcessorResolver delegatesResolver = super.installActionResultProcessorResolver();
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
	protected AdapterResolver installAdapterResolver() {
		AdapterResolver adapterResolver = super.installAdapterResolver();
		VirtualAdapterResolver<IAdapter> virtualAdapterResolver = new VirtualAdapterResolver<IAdapter>();
		adapterResolver.push(virtualAdapterResolver);
		container.register(virtualAdapterResolver);
		return adapterResolver;
	}

	@Override
	protected ControllerResolver installControllerResolver() {
		final VirtualControllerResolver virtualControllerResolver = container.instance(VirtualControllerResolver.class, VirtualControllerResolver.class);
		final ControllerResolver controllerResolver = super.installControllerResolver();
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
	protected Dispatcher installDispatcher() {
		container.register(Dispatcher.class, TestingDispatcher.class);
		return super.installDispatcher();
	}

	@Override
	protected void installEventConfigurator(IFormProcessingConfigurator formProcessingConfigurator, IObjectPersisterConfigurator persisterConfigurator) {
		container.register(IEventConfigurator.class, new TesterEventConfigurator(new RequiredEventConfigurator(packageDescriptor, super.getEventConfigurator(), formProcessingConfigurator, persisterConfigurator)));
	}

	@Override
	protected ResourceResolver installResourceResolver() {
		ResourceResolver resourceResolver = super.installResourceResolver();
		VirtualResourceResolver virtualResourceResolver = new VirtualResourceResolver();
		resourceResolver.push(virtualResourceResolver);
		container.register(virtualResourceResolver);
		return resourceResolver;
	}

	protected void setApplication(IConfigurableSailsApplication application) {
		super.setApplication(application);
		this.application = application;
	}

	@Override
	protected void setConfigurator(ISailsApplicationConfigurator configurator) {
		super.setConfigurator(this);
	}

	@Override
	protected void setContainer(ApplicationContainer installContainer) {
		super.setContainer(installContainer);
		container = installContainer;
	}

	@Override
	protected void setPackageDescriptor(IPackageDescriptor installPackageDescriptor) {
		super.setPackageDescriptor(installPackageDescriptor);
		packageDescriptor = installPackageDescriptor;
	}
}
