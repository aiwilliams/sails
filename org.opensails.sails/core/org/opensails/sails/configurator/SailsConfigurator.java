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
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.IActionEventProcessorResolver;
import org.opensails.sails.oem.ClasspathResourceResolver;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.EventProcessorResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.oem.SailsDefaultsConfiguration;
import org.opensails.sails.oem.ServletContextResourceResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class SailsConfigurator implements ISailsApplicationConfigurator {
	public void configure(IConfigurableSailsApplication application) {
		application.setConfigurator(this);

		configureName(application);

		getConfigurationConfigurator().configure(installConfiguration(application));

		ApplicationContainer container = installContainer(application);
		getContainerConfigurator().configure(container);

		getResourceResolverConfigurator().configure(installResourceResolver(application));
		getPersisterConfigurator().configure(application.getContainer());

		getAdapterConfigurator().configure(installAdapterResolver(application));
		getResultProcessorConfigurator().configure(installActionResultProcessorResolver(application));
		getControllerResolverConfigurator().configure(installControllerResolver(application));
		getComponentResolverConfigurator().configure(installComponentResolver(application));

		installEventProcessorResolver(application, container);
		installUrlResolverResolver(application, container);
		installDispatcher(application, container);
	}

	public IAdapterResolverConfigurator getAdapterConfigurator() {
		return null;
	}

	public IComponentResolverConfigurator getComponentResolverConfigurator() {
		return null;
	}

	public IConfigurationConfigurator getConfigurationConfigurator() {
		return null;
	}

	public IContainerConfigurator getContainerConfigurator() {
		return null;
	}

	public IControllerResolverConfigurator getControllerResolverConfigurator() {
		// TODO Auto-generated method stub
		return null;
	}

	public IEventConfigurator getEventConfigurator() {
		return null;
	}

	public IObjectPersisterConfigurator getPersisterConfigurator() {
		return null;
	}

	public IResourceResolverConfigurator getResourceResolverConfigurator() {
		return null;
	}

	public IResultProcessorConfigurator getResultProcessorConfigurator() {
		return null;
	}

	protected void configureName(IConfigurableSailsApplication application) {
		String className = SpyGlass.getName(getClass());
		int configuratorIndex = className.indexOf("Configurator");
		if (configuratorIndex > 0) className = className.substring(0, configuratorIndex);
		application.setName(Sails.spaceCamelWords(className));
	}

	protected ApplicationContainer createApplicationContainer(IConfigurableSailsApplication application) {
		return new ApplicationContainer();
	}

	protected String getApplicationRootPackage() {
		return SpyGlass.getPackage(getClass());
	}

	protected String getBuiltinAdaptersPackage() {
		return SpyGlass.getPackage(Sails.class) + ".adapters";
	}

	protected String getBuiltinComponentPackage() {
		return SpyGlass.getPackage(Sails.class) + ".components";
	}

	protected String getBuiltinControllerPackage() {
		return SpyGlass.getPackage(Sails.class) + ".controllers";
	}

	protected String getBuiltinMixinPackage() {
		return SpyGlass.getPackage(Sails.class) + ".mixins";
	}

	protected String getBuitinActionResultProcessorPackage() {
		return SpyGlass.getPackage(Sails.class) + ".processors";
	}

	protected String getDefaultActionResultProcessorPackage() {
		return getApplicationRootPackage() + ".processors";
	}

	protected String getDefaultAdaptersPackage() {
		return getApplicationRootPackage() + ".adapters";
	}

	protected String getDefaultComponentPackage() {
		return getApplicationRootPackage() + ".components";
	}

	protected String getDefaultControllerPackage() {
		return getApplicationRootPackage() + ".controllers";
	}

	protected String getDefaultMixinPackage() {
		return getApplicationRootPackage() + ".mixins";
	}

	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application) {
		ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(application.getContainer());
		resolver.push(new PackageClassResolver<IActionResultProcessor>(getBuitinActionResultProcessorPackage(), "Processor"));
		resolver.push(new PackageClassResolver<IActionResultProcessor>(getDefaultActionResultProcessorPackage(), "Processor"));
		application.getContainer().register(ActionResultProcessorResolver.class, resolver);
		return resolver;
	}

	protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application) {
		AdapterResolver resolver = new AdapterResolver();
		resolver.push(new PackageClassResolver<IAdapter>(getBuiltinAdaptersPackage(), "Adapter"));
		resolver.push(new PackageClassResolver<IAdapter>(getDefaultAdaptersPackage(), "Adapter"));
		application.getContainer().register(AdapterResolver.class, resolver);
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
	protected ComponentResolver installComponentResolver(IConfigurableSailsApplication application) {
		ComponentResolver<IComponentImpl> resolver = (ComponentResolver<IComponentImpl>) application.getContainer().instance(IComponentResolver.class, ComponentResolver.class);
		resolver.push(new PackageClassResolver<IComponentImpl>(getBuiltinComponentPackage(), "Component"));
		resolver.push(new PackageClassResolver<IComponentImpl>(getDefaultComponentPackage(), "Component"));
		return resolver;
	}

	protected CompositeConfiguration installConfiguration(IConfigurableSailsApplication application) {
		CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
		compositeConfiguration.addConfiguration(new SystemConfiguration());
		compositeConfiguration.addConfiguration(new ServletConfiguration(application));
		compositeConfiguration.addConfiguration(new ServletContextConfiguration(application));
		compositeConfiguration.addConfiguration(new SailsDefaultsConfiguration());
		application.setConfiguration(compositeConfiguration);
		return compositeConfiguration;
	}

	protected ApplicationContainer installContainer(IConfigurableSailsApplication application) {
		ApplicationContainer applicationContainer = createApplicationContainer(application);
		provideApplicationScopedContainerAccess(applicationContainer);
		applicationContainer.register(IEventConfigurator.class, getEventConfigurator());
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
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application) {
		ControllerResolver resolver = (ControllerResolver) application.getContainer().instance(IControllerResolver.class, ControllerResolver.class);
		resolver.push(new ControllerPackage(getBuiltinControllerPackage()));
		resolver.push(new ControllerPackage(getDefaultControllerPackage()));
		return resolver;
	}

	protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ApplicationContainer container) {
		Dispatcher dispatcher = container.instance(Dispatcher.class, Dispatcher.class);
		application.setDispatcher(dispatcher);
		return dispatcher;
	}

	/**
	 * Invoked after the controller and component resolvers have been installed.
	 * 
	 * @param application
	 * @param container
	 */
	protected EventProcessorResolver installEventProcessorResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		EventProcessorResolver resolver = new EventProcessorResolver();
		resolver.push(container.instance(IComponentResolver.class));
		resolver.push(container.instance(IControllerResolver.class));
		container.register(IActionEventProcessorResolver.class, resolver);
		return resolver;
	}

	protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application) {
		ResourceResolver resolver = new ResourceResolver();
		resolver.push(new ClasspathResourceResolver());
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/views"));
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/"));
		application.getContainer().register(IResourceResolver.class, resolver);
		return resolver;
	}

	protected UrlResolver installUrlResolverResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
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
}
