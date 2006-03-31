package org.opensails.sails.oem;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.web.ServletConfiguration;
import org.apache.commons.configuration.web.ServletContextConfiguration;
import org.opensails.rigging.IScopedContainer;
import org.opensails.rigging.InstantiationListener;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.IResourceResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.Sails;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.controller.ControllerPackage;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.IActionEventProcessorResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.HtmlForm;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.UnderscoreIdGenerator;
import org.opensails.sails.form.ValidationContext;
import org.opensails.sails.model.IPropertyFactory;
import org.opensails.sails.model.ModelContext;
import org.opensails.sails.model.oem.DefaultPropertyFactory;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.template.Require;
import org.opensails.sails.template.viento.VientoBinding;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.oem.SailsValidationEngine;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;
import org.opensails.viento.IBinding;

/**
 * The base implementation of ISailsApplicationConfigurator.
 * 
 * Although you may write your own implementation of
 * ISailsApplicationConfigurator, it is likely to be less work to subclass this.
 * If you study it, you will see that there are only a few dependencies that the
 * ISailsApplication itself has, but there are many others introduced throughout
 * the lifecycle of an ISailsEvent.
 * 
 * This is written in a way that attempts to make clear what should be
 * overridden to extend the default behavior. All the public methods, except
 * those from ISailsApplicationConfigurator and ISailsEventConfigurator, are the
 * ones you should look at first. They typically begin with 'configure'. The
 * 'install' methods are those that represent the minimal needs of a real,
 * running ISailsAppilcation. They likely don't need to be overridden, but you
 * may know better than me.
 * 
 * @author aiwilliams
 * 
 */
public class BaseConfigurator implements ISailsApplicationConfigurator, IEventConfigurator {
	public void configure(ActionResultProcessorResolver resultProcessorResolver) {}

	public void configure(AdapterResolver adapterResolver) {}

	/**
	 * Subclasses override this to add custom IComponentImpl class resolution.
	 * 
	 * Called after the ComponentResolver has been installed into the
	 * application. All {@link org.opensails.spyglass.IClassResolver}s
	 * configured by this method will be consulted before the defaults.
	 * 
	 * @param componentResolver
	 */
	public void configure(ComponentResolver<IComponentImpl> componentResolver) {}

	/**
	 * Subclasses override this to add custom IControllerImpl class resolution.
	 * 
	 * Called after the ControllerResolver has been installed into the
	 * application. All {@link org.opensails.spyglass.IClassResolver}s
	 * configured by this method will be consulted before the defaults.
	 * 
	 * @param controllerResolver
	 */
	public void configure(ControllerResolver controllerResolver) {}

	/**
	 * The main configure method.
	 * <p>
	 * This knows exactly what a IConfigurableSailsApplication needs. It is
	 * likely the wrong thing to override this method. Consider whether there is
	 * something this calls that is more appropriate to gain the hook you need.
	 */
	public void configure(IConfigurableSailsApplication application) {
		installConfigurator(application);

		CompositeConfiguration configuration = installConfiguration(application);
		configure(application, configuration);
		configureName(application, configuration);

		ApplicationContainer container = installContainer(application);
		configure(application, container);

		installObjectPersister(application, container);

		ResourceResolver resourceResolver = installResourceResolver(application, container);
		configure(resourceResolver);

		AdapterResolver adapterResolver = installAdapterResolver(application, container);
		configure(adapterResolver);

		ActionResultProcessorResolver resultProcessorResolver = installActionResultProcessorResolver(application, container);
		configure(resultProcessorResolver);

		ControllerResolver controllerResolver = installControllerResolver(application, container);
		configure(controllerResolver);

		ComponentResolver componentResolver = installComponentResolver(application, container);
		configure(componentResolver);

		installEventProcessorResolver(application, container);
		installUrlResolverResolver(application, container);
		installDispatcher(application, container);
	}

	/**
	 * Invoked just after an IBinding is created. Override to extend the binding
	 * for every event.
	 * 
	 * @param event
	 * @param binding
	 */
	public void configure(ISailsEvent event, IBinding binding) {}

	public void configure(final ISailsEvent event, IEventContextContainer eventContainer) {
		MixinResolver resolver = installMixinResolver(event, eventContainer);
		configure(event, resolver);

		eventContainer.register(Require.class);
		eventContainer.registerResolver(Flash.class, new FlashComponentResolver(event));
		provideEventScopedContainerAccess(eventContainer);

		eventContainer.register(ContainerAdapterResolver.class, ContainerAdapterResolver.class);
		eventContainer.register(IBinding.class, VientoBinding.class);
		eventContainer.registerInstantiationListener(IBinding.class, new InstantiationListener<IBinding>() {
			public void instantiated(IBinding newInstance) {
				configure(event, newInstance);
			}
		});

		// TODO: Move form stuff to more configurable location, as framework is
		// fleshed out
		eventContainer.register(ModelContext.class);
		eventContainer.register(ValidationContext.class);
		eventContainer.register(HtmlForm.class);
		eventContainer.register(IPropertyFactory.class, DefaultPropertyFactory.class);
	}

	/**
	 * Called for each event, after
	 * {@link #installMixinResolver(ISailsEvent, IEventContextContainer)}
	 * 
	 * @param event
	 * @param resolver
	 */
	public void configure(ISailsEvent event, MixinResolver resolver) {}

	public void configure(ResourceResolver resourceResolver) {}

	/**
	 * Called after the container has been installed and before any other
	 * configure* methods (except configureConfiguration). This DOES NOT
	 * disallow, nor discourage, other configurator methods from modifying the
	 * container.
	 * 
	 * @param application
	 * @param container
	 */
	protected void configure(IConfigurableSailsApplication application, ApplicationContainer container) {}

	protected void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration) {
		compositeConfiguration.addConfiguration(new SystemConfiguration());
		compositeConfiguration.addConfiguration(new ServletConfiguration(application));
		compositeConfiguration.addConfiguration(new ServletContextConfiguration(application));
		compositeConfiguration.addConfiguration(new SailsDefaultsConfiguration());
	}

	protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
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

	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(container);
		resolver.push(new PackageClassResolver<IActionResultProcessor>(getBuitinActionResultProcessorPackage(), "Processor"));
		resolver.push(new PackageClassResolver<IActionResultProcessor>(getDefaultActionResultProcessorPackage(), "Processor"));
		container.register(IActionResultProcessorResolver.class, resolver);
		return resolver;
	}

	protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		AdapterResolver resolver = new AdapterResolver();
		resolver.push(new PackageClassResolver<IAdapter>(getBuiltinAdaptersPackage(), "Adapter"));
		resolver.push(new PackageClassResolver<IAdapter>(getDefaultAdaptersPackage(), "Adapter"));
		container.register(IAdapterResolver.class, resolver);
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
	protected ComponentResolver installComponentResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		ComponentResolver resolver = (ComponentResolver) container.instance(IComponentResolver.class, ComponentResolver.class);
		resolver.push(new PackageClassResolver<IComponentImpl>(getBuiltinComponentPackage(), "Component"));
		resolver.push(new PackageClassResolver<IComponentImpl>(getDefaultComponentPackage(), "Component"));
		return resolver;
	}

	protected CompositeConfiguration installConfiguration(IConfigurableSailsApplication application) {
		CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
		application.setConfiguration(compositeConfiguration);
		return compositeConfiguration;
	}

	protected void installConfigurator(IConfigurableSailsApplication application) {
		application.setConfigurator(this);
	}

	/**
	 * Installs an ApplicationContainer into the application.
	 * <p>
	 * This method really should not be overridden. The DelegatingConfigurator
	 * is aware of some of the behavior of this method to allow for tester
	 * magic.
	 * 
	 * @param application
	 * @see DelegatingConfigurator
	 * @see org.opensails.sails.tester.SailsTesterConfigurator
	 * @return
	 */
	protected ApplicationContainer installContainer(IConfigurableSailsApplication application) {
		ApplicationContainer applicationContainer = createApplicationContainer(application);
		provideApplicationScopedContainerAccess(applicationContainer);
		applicationContainer.register(IEventConfigurator.class, this);
		applicationContainer.register(IValidationEngine.class, SailsValidationEngine.class);
		applicationContainer.register(IFormElementIdGenerator.class, UnderscoreIdGenerator.class);
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
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		ControllerResolver resolver = (ControllerResolver) container.instance(IControllerResolver.class, ControllerResolver.class);
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

	protected MixinResolver installMixinResolver(ISailsEvent event, IEventContextContainer eventContainer) {
		MixinResolver resolver = new MixinResolver(event);
		resolver.push(new PackageClassResolver(getBuiltinMixinPackage(), "Mixin"));
		resolver.push(new PackageClassResolver(getDefaultMixinPackage(), "Mixin"));
		eventContainer.register(MixinResolver.class, resolver);
		return resolver;
	}

	protected void installObjectPersister(IConfigurableSailsApplication application, ApplicationContainer container) {

	}

	protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
		ResourceResolver resolver = new ResourceResolver();
		resolver.push(new ClasspathResourceResolver());
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/views"));
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/"));
		container.register(IResourceResolver.class, resolver);
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

	/**
	 * Ensures that injection dependents within an event container get what they
	 * need.
	 * <p>
	 * Anything instantiated with the event container will see the
	 * IScopedContainer of the current event. If they are certain that they want
	 * the IEventContextContainer, they should depend on that interface instead
	 * of IScopedContainer. I would guess that if they depend on
	 * IScopedContainer, they are implying that they don't care what scope they
	 * are used in.
	 * 
	 * @param eventContainer
	 */
	protected void provideEventScopedContainerAccess(IEventContextContainer eventContainer) {
		eventContainer.register(IEventContextContainer.class, eventContainer);
		eventContainer.register(IScopedContainer.class, eventContainer);
	}

}
