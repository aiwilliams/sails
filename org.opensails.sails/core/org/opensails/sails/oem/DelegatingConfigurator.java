package org.opensails.sails.oem;

import org.apache.commons.configuration.*;
import org.opensails.rigging.*;
import org.opensails.sails.*;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.*;
import org.opensails.sails.url.*;
import org.opensails.sails.util.*;
import org.opensails.viento.*;

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
		// Intentional super call. The root configure causes all methods to be
		// called on this.
		super.configure(application);
	}

	@Override
	public void configure(ISailsEvent event, IBinding binding) {
		delegate.configure(event, binding);
	}

	@Override
	public void configure(ISailsEvent event, MixinResolver resolver) {
		delegate.configure(event, resolver);
	}

	@Override
	public void configure(ISailsEvent event, RequestContainer eventContainer) {
		delegate.configure(event, eventContainer);
	}

	@Override
	public void configure(ResourceResolver resourceResolver) {
		delegate.configure(resourceResolver);
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
	protected String getBuiltinMixinPackage() {
		return delegate.getBuiltinMixinPackage();
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
	protected String getDefaultAdaptersPackage() {
		return delegate.getDefaultAdaptersPackage();
	}

	@Override
	protected String getDefaultComponentPackage() {
		return delegate.getDefaultComponentPackage();
	}

	@Override
	protected String getDefaultControllerPackage() {
		return delegate.getDefaultControllerPackage();
	}

	@Override
	protected String getDefaultMixinPackage() {
		return delegate.getDefaultMixinPackage();
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
	protected void installConfigurator(IConfigurableSailsApplication application) {
		delegate.installConfigurator(application);
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
	protected MixinResolver installMixinResolver(ISailsEvent event, RequestContainer eventContainer) {
		return delegate.installMixinResolver(event, eventContainer);
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
