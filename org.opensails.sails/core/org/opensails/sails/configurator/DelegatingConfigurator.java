package org.opensails.sails.configurator;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.EventProcessorResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.spyglass.SpyGlass;

public class DelegatingConfigurator extends SailsConfigurator {
	protected SailsConfigurator delegate;

	public DelegatingConfigurator(Class<? extends SailsConfigurator> delegateClass) {
		Object[] args = {};
		this.delegate = SpyGlass.instantiate(delegateClass, args);
	}

	@Override
	public void configure(IConfigurableSailsApplication application) {
		// Intentional super call. The root configure causes all methods to be
		// called on this.
		super.configure(application);
	}

	@Override
	public IPackageDescriptor createPackageDescriptor() {
		return delegate.createPackageDescriptor();
	}

	@Override
	public IConfigurationConfigurator getConfigurationConfigurator() {
		return delegate.getConfigurationConfigurator();
	}

	@Override
	public IContainerConfigurator getContainerConfigurator() {
		return delegate.getContainerConfigurator();
	}

	@Override
	public IEventConfigurator getEventConfigurator() {
		return delegate.getEventConfigurator();
	}

	@Override
	public IFormProcessingConfigurator getFormProcessingConfigurator() {
		return delegate.getFormProcessingConfigurator();
	}

	@Override
	public IObjectPersisterConfigurator getPersisterConfigurator() {
		return delegate.getPersisterConfigurator();
	}

	@Override
	public IResourceResolverConfigurator getResourceResolverConfigurator() {
		return delegate.getResourceResolverConfigurator();
	}

	@Override
	protected void configureName() {
		delegate.configureName();
	}

	@Override
	protected ApplicationContainer createApplicationContainer() {
		return delegate.createApplicationContainer();
	}

	@Override
	protected ApplicationPackage getApplicationPackage() {
		return delegate.getApplicationPackage();
	}

	@Override
	protected Package getApplicationRootPackage() {
		return delegate.getApplicationRootPackage();
	}

	@Override
	protected String getBuiltinAdaptersPackage() {
		return delegate.getBuiltinAdaptersPackage();
	}

	@Override
	protected String getBuiltinComponentPackage() {
		return delegate.getBuiltinComponentPackage();
	}

	@Override
	protected String getBuiltinControllerPackage() {
		return delegate.getBuiltinControllerPackage();
	}

	@Override
	protected Package getBuiltinMixinPackage() {
		return delegate.getBuiltinMixinPackage();
	}

	@Override
	protected String getBuitinActionResultProcessorPackage() {
		return delegate.getBuitinActionResultProcessorPackage();
	}

	@Override
	protected ActionResultProcessorResolver installActionResultProcessorResolver() {
		return delegate.installActionResultProcessorResolver();
	}

	@Override
	protected AdapterResolver installAdapterResolver() {
		return delegate.installAdapterResolver();
	}

	@Override
	protected ComponentResolver installComponentResolver() {
		return delegate.installComponentResolver();
	}

	@Override
	protected CompositeConfiguration installConfiguration() {
		return delegate.installConfiguration();
	}

	@Override
	protected ApplicationContainer installContainer() {
		// Intentional super call. The root install causes all methods to be
		// called on this.
		return super.installContainer();
	}

	@Override
	protected ControllerResolver installControllerResolver() {
		return delegate.installControllerResolver();
	}

	@Override
	protected Dispatcher installDispatcher() {
		return delegate.installDispatcher();
	}

	@Override
	protected void installEventConfigurator(IFormProcessingConfigurator formProcessingConfigurator, IObjectPersisterConfigurator persisterConfigurator) {
		delegate.installEventConfigurator(formProcessingConfigurator, persisterConfigurator);
	}

	@Override
	protected EventProcessorResolver installEventProcessorResolver() {
		return delegate.installEventProcessorResolver();
	}

	@Override
	protected IFormProcessingConfigurator installFormProcessingConfigurator() {
		return delegate.installFormProcessingConfigurator();
	}

	@Override
	protected IPackageDescriptor installPackageDescriptor() {
		return delegate.installPackageDescriptor();
	}

	@Override
	protected ResourceResolver installResourceResolver() {
		return delegate.installResourceResolver();
	}

	@Override
	protected UrlResolver installUrlResolverResolver() {
		return delegate.installUrlResolverResolver();
	}

	@Override
	protected void provideApplicationScopedContainerAccess(ApplicationContainer applicationContainer) {
		delegate.provideApplicationScopedContainerAccess(applicationContainer);
	}

	@Override
	protected void setApplication(IConfigurableSailsApplication application) {
		delegate.setApplication(application);
	}

	@Override
	protected void setConfigurator(ISailsApplicationConfigurator configurator) {
		delegate.setConfigurator(configurator);
	}

	@Override
	protected void setContainer(ApplicationContainer installContainer) {
		delegate.setContainer(installContainer);
	}

	@Override
	protected void setPackageDescriptor(IPackageDescriptor installPackageDescriptor) {
		delegate.setPackageDescriptor(installPackageDescriptor);
	}

}
