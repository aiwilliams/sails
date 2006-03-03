package org.opensails.sails.oem;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.util.ClassHelper;
import org.opensails.viento.IBinding;

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
    public void configure(ComponentResolver componentResolver) {
        delegate.configure(componentResolver);
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
    public void configure(ISailsEvent event, IEventContextContainer eventContainer) {
        delegate.configure(event, eventContainer);
    }

    @Override
    public void configure(ISailsEvent event, MixinResolver resolver) {
        delegate.configure(event, resolver);
    }

    @Override
    public void configure(ResourceResolver resourceResolver) {
        delegate.configure(resourceResolver);
    }

    @Override
    protected void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
        delegate.configure(application, container);
    }

    @Override
    protected void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration) {
        delegate.configure(application, compositeConfiguration);
    }

    @Override
    protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
        delegate.configureName(application, configuration);
    }

    @Override
    protected ApplicationContainer createApplicationContainer(IConfigurableSailsApplication application) {
        return delegate.createApplicationContainer(application);
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
    protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installActionResultProcessorResolver(application, container);
    }

    @Override
    protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installAdapterResolver(application, container);
    }

    @Override
    protected ComponentResolver installComponentResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installComponentResolver(application, container);
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
    protected ApplicationContainer installContainer(IConfigurableSailsApplication application) {
        // Intentional super call. The root install causes all methods to be
        // called on this.
        return super.installContainer(application);
    }

    @Override
    protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installControllerResolver(application, container);
    }

    @Override
    protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installDispatcher(application, container);
    }

    @Override
    protected EventProcessorResolver installEventProcessorResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installEventProcessorResolver(application, container);
    }

    @Override
    protected MixinResolver installMixinResolver(ISailsEvent event, IEventContextContainer eventContainer) {
        return delegate.installMixinResolver(event, eventContainer);
    }

    @Override
    protected void installObjectPersister(IConfigurableSailsApplication application, ApplicationContainer container) {
        delegate.installObjectPersister(application, container);
    }

    @Override
    protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installResourceResolver(application, container);
    }

    @Override
    protected UrlResolver installUrlResolverResolver(IConfigurableSailsApplication application, ApplicationContainer container) {
        return delegate.installUrlResolverResolver(application, container);
    }

    @Override
    protected void provideApplicationScopedContainerAccess(ApplicationContainer applicationContainer) {
        this.delegate.provideApplicationScopedContainerAccess(applicationContainer);
    }

    @Override
    protected void provideEventScopedContainerAccess(IEventContextContainer eventContainer) {
        this.delegate.provideEventScopedContainerAccess(eventContainer);
    }
}
