package org.opensails.sails.oem;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.web.ServletConfiguration;
import org.apache.commons.configuration.web.ServletContextConfiguration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.IActionResultProcessor;
import org.opensails.sails.IActionResultProcessorResolver;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.IResourceResolver;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.ISailsEventConfigurator;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.IControllerResolver;
import org.opensails.sails.controllers.ErrorController;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.form.UnderscoreIdGenerator;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.helper.oem.HelperResolver;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.viento.VientoBinding;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.url.IUrlResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.url.oem.ImageUrlResolver;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.ComponentPackage;
import org.opensails.sails.validation.IValidationEngine;
import org.opensails.sails.validation.oem.SailsValidationEngine;

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
public class BaseConfigurator implements ISailsApplicationConfigurator, ISailsEventConfigurator {
	public void configure(ActionResultProcessorResolver resultProcessorResolver) {}

	public void configure(AdapterResolver adapterResolver) {}

	/**
	 * Subclasses override this to add custom IControllerImpl class resolution.
	 * 
	 * Called after the ControllerResolver has been installed into the
	 * application. All {@link org.opensails.sails.util.IClassResolver}s
	 * configured by this method will be consulted before the defaults.
	 * 
	 * @param controllerResolver
	 */
	public void configure(ControllerResolver controllerResolver) {}

	public void configure(IConfigurableSailsApplication application) {
		CompositeConfiguration configuration = installConfiguration(application);
		configure(application, configuration);
		configureName(application, configuration);

		ScopedContainer container = installContainer(application);
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

		installUrlResolverResolver(application, container);
		installDispatcher(application, container);
	}

	/**
	 * Called for each event, after
	 * {@link #installHelperResolver(ISailsEvent, ScopedContainer)}
	 * 
	 * @param event
	 * @param helperResolver
	 */
	public void configure(ISailsEvent event, HelperResolver helperResolver) {}

	public void configure(ISailsEvent event, ScopedContainer eventContainer) {
		HelperResolver helperResolver = installHelperResolver(event, eventContainer);
		configure(event, helperResolver);

		eventContainer.register(ScopedContainer.class, eventContainer);
		eventContainer.register(ContainerAdapterResolver.class, ContainerAdapterResolver.class);
		eventContainer.register(ITemplateBinding.class, VientoBinding.class);
	}

	public void configure(ResourceResolver resourceResolver) {}

	protected void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration) {
		compositeConfiguration.addConfiguration(new SystemConfiguration());
		compositeConfiguration.addConfiguration(new ServletConfiguration(application));
		compositeConfiguration.addConfiguration(new ServletContextConfiguration(application));
		compositeConfiguration.addConfiguration(new SailsDefaultsConfiguration());
	}

	/**
	 * Called after the container has been installed and before any other
	 * configure* methods (except configureConfiguration). This DOES NOT
	 * disallow, nor discourage, other conigurator methods from modifying the
	 * container.
	 * 
	 * @param application
	 * @param container
	 */
	protected void configure(IConfigurableSailsApplication application, ScopedContainer container) {}

	protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
		String className = ClassHelper.getName(getClass());
		int configuratorIndex = className.indexOf("Configurator");
		if (configuratorIndex > 0) className = className.substring(0, configuratorIndex);
		application.setName(Sails.spaceCamelWords(className));
	}

	protected String getApplicationRootPackage() {
		return ClassHelper.getPackage(getClass());
	}

	protected String getBuiltinControllerPackage() {
		return ClassHelper.getPackage(ErrorController.class);
	}

	protected String getBuiltinHelperPackage() {
		return ClassHelper.getPackage(HelperResolver.class);
	}

	protected String getBuiltinUrlResolverPackage() {
		return ClassHelper.getPackage(ImageUrlResolver.class);
	}

	protected String getBuitinActionResultProcessorPackage() {
		return ClassHelper.getPackage(Sails.class) + ".processors";
	}

	protected String getDefaultActionResultProcessorPackage() {
		return getApplicationRootPackage() + ".processors";
	}

	protected String getDefaultControllerPackage() {
		return getApplicationRootPackage() + ".controllers";
	}

	protected String getDefaultHelperPackage() {
		return getApplicationRootPackage() + ".helpers";
	}

	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		ActionResultProcessorResolver resolver = new ActionResultProcessorResolver(container);
		resolver.push(new ComponentPackage<IActionResultProcessor>(getBuitinActionResultProcessorPackage(), "Processor"));
		resolver.push(new ComponentPackage<IActionResultProcessor>(getDefaultActionResultProcessorPackage(), "Processor"));
		container.register(IActionResultProcessorResolver.class, resolver);
		return resolver;
	}

	protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		AdapterResolver resolver = new AdapterResolver();
		container.register(IAdapterResolver.class, resolver);
		return resolver;
	}

	protected CompositeConfiguration installConfiguration(IConfigurableSailsApplication application) {
		CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
		application.setConfiguration(compositeConfiguration);
		return compositeConfiguration;
	}

	protected ScopedContainer installContainer(IConfigurableSailsApplication application) {
		ScopedContainer container = new ScopedContainer(ApplicationScope.SERVLET);

		container.register(ISailsEventConfigurator.class, this);
		container.register(IValidationEngine.class, SailsValidationEngine.class);
		container.register(IFormElementIdGenerator.class, UnderscoreIdGenerator.class);
		container.register(ITemplateBinding.class, VientoBinding.class);
		container.register(ITemplateRenderer.class, VientoTemplateRenderer.class);
		
		container.register(ISailsApplication.class, application);
		application.setContainer(container);
		return container;
	}

	/**
	 * Installs an
	 * {@link org.opensails.sails.controller.oem.IControllerResolver} into the
	 * application and configures the default controller class resolvers.
	 * 
	 * @param application
	 * @param container
	 * @return the installed resolver
	 */
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		ControllerResolver resolver = (ControllerResolver) container.instance(IControllerResolver.class, ControllerResolver.class);
		resolver.push(new ComponentPackage<IControllerImpl>(getBuiltinControllerPackage(), "Controller"));
		resolver.push(new ComponentPackage<IControllerImpl>(getDefaultControllerPackage(), "Controller"));
		return resolver;
	}

	protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ScopedContainer container) {
		Dispatcher dispatcher = container.instance(Dispatcher.class, Dispatcher.class);
		application.setDispatcher(dispatcher);
		return dispatcher;
	}

	protected HelperResolver installHelperResolver(ISailsEvent event, ScopedContainer eventContainer) {
		HelperResolver resolver = new HelperResolver(event);
		resolver.push(new ComponentPackage(getBuiltinHelperPackage(), "Helper"));
		resolver.push(new ComponentPackage(getDefaultHelperPackage(), "Helper"));
		eventContainer.register(IHelperResolver.class, resolver);
		return resolver;
	}

	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {

	}

	protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		ResourceResolver resolver = new ResourceResolver();
		resolver.push(new ClasspathResourceResolver());
		resolver.push(new ServletContextResourceResolver(application.getServletConfig().getServletContext(), "/WEB-INF/views"));
		container.register(IResourceResolver.class, resolver);
		return resolver;
	}

	protected UrlResolver installUrlResolverResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		UrlResolver resolver = container.instance(UrlResolver.class, UrlResolver.class);
		container.register(IUrlResolver.class, resolver);
		return resolver;
	}
}
