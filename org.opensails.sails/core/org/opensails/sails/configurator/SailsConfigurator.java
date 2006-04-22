package org.opensails.sails.configurator;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.web.ServletConfiguration;
import org.apache.commons.configuration.web.ServletContextConfiguration;
import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.IResourceResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.Sails;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.ComponentPackage;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.configurator.oem.DefaultConfigurationConfigurator;
import org.opensails.sails.configurator.oem.DefaultContainerConfigurator;
import org.opensails.sails.configurator.oem.DefaultEventConfigurator;
import org.opensails.sails.configurator.oem.DefaultFormProcessingConfigurator;
import org.opensails.sails.configurator.oem.DefaultPackageDescriptor;
import org.opensails.sails.configurator.oem.DefaultResourceResolverConfigurator;
import org.opensails.sails.configurator.oem.MemoryObjectPersisterConfigurator;
import org.opensails.sails.configurator.oem.RequiredEventConfigurator;
import org.opensails.sails.configurator.oem.RequiredFormProcessingConfigurator;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.IActionEventProcessorResolver;
import org.opensails.sails.mixins.ThrowableMixin;
import org.opensails.sails.oem.ClasspathResourceResolver;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.EventProcessorResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.oem.SailsDefaultsConfiguration;
import org.opensails.sails.oem.ServletContextResourceResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class SailsConfigurator implements ISailsApplicationConfigurator {
	protected IPackageDescriptor packageDescriptor;
	protected IConfigurableSailsApplication application;
	protected ApplicationContainer container;

	public void configure(IConfigurableSailsApplication application) {
		setApplication(application);
		setConfigurator(this);

		configureName();

		getConfigurationConfigurator().configure(application, installConfiguration());

		setContainer(installContainer());
		setPackageDescriptor(installPackageDescriptor());

		getContainerConfigurator().configure(application, container);

		getResourceResolverConfigurator().configure(application, installResourceResolver());

		IObjectPersisterConfigurator persisterConfigurator = getPersisterConfigurator();
		persisterConfigurator.configure(application, application.getContainer());

		IFormProcessingConfigurator formProcessingConfigurator = installFormProcessingConfigurator();
		formProcessingConfigurator.configure(application, application.getContainer());

		installEventConfigurator(formProcessingConfigurator, persisterConfigurator);

		AdapterResolver adapterResolver = installAdapterResolver();
		for (ApplicationPackage adapterPackage : packageDescriptor.getAdapterPackages())
			adapterResolver.push(new PackageClassResolver<IAdapter>(adapterPackage.getPackageName(), "Adapter"));

		ActionResultProcessorResolver processorResolver = installActionResultProcessorResolver();
		for (ApplicationPackage processorPackage : packageDescriptor.getResultProcessorPackages())
			processorResolver.push(new PackageClassResolver<IActionResultProcessor>(processorPackage.getPackageName(), "Processor"));

		ControllerResolver controllerResolver = installControllerResolver();
		for (ApplicationPackage controllerPackage : packageDescriptor.getControllerPackages())
			controllerResolver.push(new ControllerPackage(controllerPackage.getPackageName()));

		ComponentResolver componentResolver = installComponentResolver();
		for (ApplicationPackage componentPackage : packageDescriptor.getComponentPackages())
			componentResolver.push(new ComponentPackage(componentPackage.getPackageName()));

		MixinResolver resolver = new MixinResolver(container);
		resolver.push(getBuiltinMixinPackage());
		for (ApplicationPackage mixinPackage : packageDescriptor.getMixinPackages())
			resolver.push(mixinPackage.getPackageName());
		container.register(resolver);

		installEventProcessorResolver();
		installUrlResolverResolver();
		installDispatcher();
	}

	/**
	 * Called after the name has been configured and the Configuration is
	 * configured. Override this to change the packages used when searching for
	 * various application stuff.
	 * 
	 * @return the package descriptor
	 */
	public IPackageDescriptor createPackageDescriptor() {
		return new DefaultPackageDescriptor(getApplicationPackage());
	}

	public IConfigurationConfigurator getConfigurationConfigurator() {
		return new DefaultConfigurationConfigurator();
	}

	public IContainerConfigurator getContainerConfigurator() {
		return new DefaultContainerConfigurator();
	}

	public IEventConfigurator getEventConfigurator() {
		return new DefaultEventConfigurator();
	}

	public IFormProcessingConfigurator getFormProcessingConfigurator() {
		return new DefaultFormProcessingConfigurator();
	}

	public IObjectPersisterConfigurator getPersisterConfigurator() {
		return new MemoryObjectPersisterConfigurator();
	}

	public IResourceResolverConfigurator getResourceResolverConfigurator() {
		return new DefaultResourceResolverConfigurator();
	}

	protected void configureName() {
		String className = SpyGlass.getName(getClass());
		int configuratorIndex = className.indexOf("Configurator");
		if (configuratorIndex > 0) className = className.substring(0, configuratorIndex);
		application.setName(Sails.spaceCamelWords(className));
	}

	protected ApplicationContainer createApplicationContainer() {
		return new ApplicationContainer();
	}

	protected ApplicationPackage getApplicationPackage() {
		return new ApplicationPackage(application.getName(), getApplicationRootPackage().getName());
	}

	protected Package getApplicationRootPackage() {
		return getClass().getPackage();
	}

	protected String getBuiltinAdaptersPackage() {
		return SpyGlass.getPackageName(Sails.class) + ".adapters";
	}

	protected String getBuiltinComponentPackage() {
		return SpyGlass.getPackageName(Sails.class) + ".components";
	}

	protected String getBuiltinControllerPackage() {
		return SpyGlass.getPackageName(Sails.class) + ".controllers";
	}

	protected Package getBuiltinMixinPackage() {
		return ThrowableMixin.class.getPackage();
	}

	protected String getBuitinActionResultProcessorPackage() {
		return SpyGlass.getPackageName(Sails.class) + ".processors";
	}

	protected ActionResultProcessorResolver installActionResultProcessorResolver() {
		ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(application.getContainer());
		resolver.push(new PackageClassResolver<IActionResultProcessor>(getBuitinActionResultProcessorPackage(), "Processor"));
		application.getContainer().register(IActionResultProcessorResolver.class, resolver);
		return resolver;
	}

	protected AdapterResolver installAdapterResolver() {
		AdapterResolver resolver = new AdapterResolver();
		resolver.push(new PackageClassResolver<IAdapter>(getBuiltinAdaptersPackage(), "Adapter"));
		application.getContainer().register(IAdapterResolver.class, resolver);
		return resolver;
	}

	/**
	 * Installs an {@link org.opensails.sails.component.IComponentResolver} into
	 * the application and configures the default component class resolvers.
	 * 
	 * @param application
	 * @param container
	 * @return the installed resolver
	 */
	protected ComponentResolver installComponentResolver() {
		ComponentResolver resolver = (ComponentResolver) application.getContainer().instance(IComponentResolver.class, ComponentResolver.class);
		resolver.push(new PackageClassResolver<IComponentImpl>(getBuiltinComponentPackage(), "Component"));
		return resolver;
	}

	protected CompositeConfiguration installConfiguration() {
		CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
		compositeConfiguration.addConfiguration(new SystemConfiguration());
		compositeConfiguration.addConfiguration(new ServletConfiguration(application));
		compositeConfiguration.addConfiguration(new ServletContextConfiguration(application));
		compositeConfiguration.addConfiguration(new SailsDefaultsConfiguration());
		application.setConfiguration(compositeConfiguration);
		return compositeConfiguration;
	}

	protected ApplicationContainer installContainer() {
		ApplicationContainer applicationContainer = createApplicationContainer();
		provideApplicationScopedContainerAccess(applicationContainer);
		applicationContainer.register(ITemplateRenderer.class, VientoTemplateRenderer.class);
		applicationContainer.register(ISailsApplication.class, application);
		application.setContainer(applicationContainer);
		return applicationContainer;
	}

	/**
	 * Installs an {@link org.opensails.sails.controller.IControllerResolver}
	 * into the application and configures the default controller class
	 * resolvers.
	 * 
	 * @param application
	 * @param container
	 * @return the installed resolver
	 */
	protected ControllerResolver installControllerResolver() {
		ControllerResolver resolver = (ControllerResolver) application.getContainer().instance(IControllerResolver.class, ControllerResolver.class);
		resolver.push(new ControllerPackage(getBuiltinControllerPackage()));
		return resolver;
	}

	protected Dispatcher installDispatcher() {
		Dispatcher dispatcher = container.instance(Dispatcher.class, Dispatcher.class);
		application.setDispatcher(dispatcher);
		return dispatcher;
	}

	protected void installEventConfigurator(IFormProcessingConfigurator formProcessingConfigurator, IObjectPersisterConfigurator persisterConfigurator) {
		container.register(IEventConfigurator.class, new RequiredEventConfigurator(packageDescriptor, getEventConfigurator(), formProcessingConfigurator, persisterConfigurator));
	}

	/**
	 * Invoked after the controller and component resolvers have been installed.
	 * 
	 * @param application
	 * @param container
	 */
	protected EventProcessorResolver installEventProcessorResolver() {
		EventProcessorResolver resolver = new EventProcessorResolver();
		resolver.push(container.instance(IComponentResolver.class));
		resolver.push(container.instance(IControllerResolver.class));
		container.register(IActionEventProcessorResolver.class, resolver);
		return resolver;
	}

	protected IFormProcessingConfigurator installFormProcessingConfigurator() {
		IFormProcessingConfigurator processingConfigurator = new RequiredFormProcessingConfigurator(getFormProcessingConfigurator());
		application.getContainer().register(IFormProcessingConfigurator.class, processingConfigurator);
		return processingConfigurator;
	}

	protected IPackageDescriptor installPackageDescriptor() {
		IPackageDescriptor descriptor = createPackageDescriptor();
		container.register(IPackageDescriptor.class, descriptor);
		return descriptor;
	}

	protected ResourceResolver installResourceResolver() {
		ResourceResolver resolver = new ResourceResolver();
		resolver.push(new ClasspathResourceResolver());
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/views"));
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/"));
		application.getContainer().register(IResourceResolver.class, resolver);
		return resolver;
	}

	protected UrlResolver installUrlResolverResolver() {
		UrlResolver resolver = container.instance(UrlResolver.class, UrlResolver.class);
		container.register(IUrlResolver.class, resolver);
		return resolver;
	}

	/**
	 * Ensures that injection dependents within the application container or in
	 * children containers get what they need.
	 * <p>
	 * Anything instantiated in the descendent hierarchy of the application
	 * container will see the IScopedContainer of the application, unless those
	 * containers are configured to override this. If they are certain that they
	 * want the ApplicationContainer, they should depend on that interface
	 * instead of IScopedContainer. I would guess that if they depend on
	 * IScopedContainer, they are implying that they don't care what scope they
	 * are used in.
	 * 
	 * @param applicationContainer
	 */
	protected void provideApplicationScopedContainerAccess(ApplicationContainer applicationContainer) {
		applicationContainer.register(ApplicationContainer.class, applicationContainer);
		applicationContainer.register(IScopedContainer.class, applicationContainer);
	}

	protected void setApplication(IConfigurableSailsApplication application) {
		this.application = application;
	}

	protected void setConfigurator(ISailsApplicationConfigurator configurator) {
		application.setConfigurator(configurator);
	}

	protected void setContainer(ApplicationContainer installContainer) {
		container = installContainer;
	}

	protected void setPackageDescriptor(IPackageDescriptor installPackageDescriptor) {
		packageDescriptor = installPackageDescriptor;
	}
}
