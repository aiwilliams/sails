package org.opensails.sails.oem;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.helper.oem.HelperResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.util.ClassHelper;

public class DelegatingConfigurator extends BaseConfigurator {
	protected BaseConfigurator delegate;

	public DelegatingConfigurator(Class<? extends BaseConfigurator> delegateClass) {
		this.delegate = ClassHelper.instantiate(delegateClass);
	}

	@Override
	public void configure(ActionResultProcessorResolver resultProcessorResolver) {
		delegate.configure(resultProcessorResolver);
	}

	@Override
	public void configure(AdapterResolver adapterResolver) {
		delegate.configure(adapterResolver);
	}

	@Override
	public void configure(ControllerResolver controllerResolver) {
		delegate.configure(controllerResolver);
	}

	@Override
	public void configure(IConfigurableSailsApplication application) {
		super.configure(application);
	}

	@Override
	public void configure(ISailsEvent event, HelperResolver helperResolver) {
		delegate.configure(event, helperResolver);
	}

	@Override
	public void configure(ISailsEvent event, ScopedContainer eventContainer) {
		delegate.configure(event, eventContainer);
	}

	@Override
	public void configure(ResourceResolver resourceResolver) {
		delegate.configure(resourceResolver);
	}

	@Override
	public void configure(UrlResolver urlResolverResolver) {
		delegate.configure(urlResolverResolver);
	}

	@Override
	protected void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration) {
		delegate.configure(application, compositeConfiguration);
	}

	@Override
	protected void configure(IConfigurableSailsApplication application, ScopedContainer container) {
		delegate.configure(application, container);
	}

	@Override
	protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
		delegate.configureName(application, configuration);
	}

	@Override
	protected String getApplicationRootPackage() {
		return delegate.getApplicationRootPackage();
	}

	@Override
	protected String getBuiltinControllerPackage() {
		return delegate.getBuiltinControllerPackage();
	}

	@Override
	protected String getBuiltinHelperPackage() {
		return delegate.getBuiltinHelperPackage();
	}

	@Override
	protected String getBuiltinUrlResolverPackage() {
		return delegate.getBuiltinUrlResolverPackage();
	}

	@Override
	protected String getBuitinActionResultProcessorPackage() {
		return delegate.getBuitinActionResultProcessorPackage();
	}

	@Override
	protected String getDefaultActionResultProcessorPackage() {
		return delegate.getDefaultActionResultProcessorPackage();
	}

	@Override
	protected String getDefaultControllerPackage() {
		return delegate.getDefaultControllerPackage();
	}

	@Override
	protected String getDefaultHelperPackage() {
		return delegate.getDefaultHelperPackage();
	}

	@Override
	protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installActionResultProcessorResolver(application, container);
	}

	@Override
	protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installAdapterResolver(application, container);
	}

	@Override
	protected CompositeConfiguration installConfiguration(IConfigurableSailsApplication application) {
		return delegate.installConfiguration(application);
	}

	@Override
	protected ScopedContainer installContainer(IConfigurableSailsApplication application) {
		return delegate.installContainer(application);
	}

	@Override
	protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installControllerResolver(application, container);
	}

	@Override
	protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installDispatcher(application, container);
	}

	@Override
	protected HelperResolver installHelperResolver(ISailsEvent event, ScopedContainer eventContainer) {
		return delegate.installHelperResolver(event, eventContainer);
	}

	@Override
	protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
		delegate.installObjectPersister(application, container);
	}

	@Override
	protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installResourceResolver(application, container);
	}

	@Override
	protected UrlResolver installUrlResolverResolver(IConfigurableSailsApplication application, ScopedContainer container) {
		return delegate.installUrlResolverResolver(application, container);
	}
}
