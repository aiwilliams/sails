package org.opensails.sails.configurator.oem;

import org.opensails.rigging.IScopedContainer;
import org.opensails.rigging.InstantiationListener;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.configurator.ApplicationPackage;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.configurator.IFormProcessingConfigurator;
import org.opensails.sails.configurator.IObjectPersisterConfigurator;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.Flash;
import org.opensails.sails.oem.FlashComponentResolver;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.template.Require;
import org.opensails.sails.template.ToolResolver;
import org.opensails.sails.tools.CacheTool;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;
import org.opensails.viento.Binding;
import org.opensails.viento.IBinding;

/**
 * Performs required event configuration.
 * <p>
 * Every event must have certain services installed. This guarantees that. It
 * wraps the application's {@link IEventConfigurator}, delegating to it after
 * this has completed required configuration.
 * 
 * @author aiwilliams
 */
public class RequiredEventConfigurator implements IEventConfigurator {
	protected IEventConfigurator delegate;
	protected IPackageDescriptor packageDescriptor;
	protected IFormProcessingConfigurator formProcessingConfigurator;
	protected IObjectPersisterConfigurator persisterConfigurator;

	public RequiredEventConfigurator(IPackageDescriptor packageDescriptor, IEventConfigurator delegate, IFormProcessingConfigurator formProcessingConfigurator, IObjectPersisterConfigurator persisterConfigurator) {
		this.packageDescriptor = packageDescriptor;
		this.delegate = delegate;
		this.formProcessingConfigurator = formProcessingConfigurator;
		this.persisterConfigurator = persisterConfigurator;
	}

	public void configure(ISailsEvent event, ComponentContainer componentContainer) {
		delegate.configure(event, componentContainer);
	}

	public void configure(ISailsEvent event, IBinding binding) {
		delegate.configure(event, binding);
	}

	public void configure(final ISailsEvent event, IEventContextContainer eventContainer) {
		delegate.configure(event, eventContainer);
		if (eventContainer instanceof RequestContainer) configure(event, (RequestContainer) eventContainer);
		if (eventContainer instanceof ComponentContainer) configure(event, (ComponentContainer) eventContainer);
	}

	public void configure(final ISailsEvent event, final RequestContainer container) {
		provideEventScopedContainerAccess(container);

		ToolResolver resolver = new ToolResolver(event);
		resolver.push(new PackageClassResolver(getBuiltinToolPackage(), "Tool"));
		for (ApplicationPackage toolPackage : packageDescriptor.getToolPackages())
			resolver.push(new PackageClassResolver(toolPackage.getPackageName(), "Tool"));
		container.register(resolver);

		container.register(Require.class);

		container.registerResolver(Flash.class, new FlashComponentResolver(event));

		container.register(ContainerAdapterResolver.class, ContainerAdapterResolver.class);

		container.register(IBinding.class, Binding.class);
		container.registerInstantiationListener(IBinding.class, new InstantiationListener<IBinding>() {
			public void instantiated(IBinding newInstance) {
				newInstance.addMethodResolver(container.instance(MixinResolver.class));
				configure(event, newInstance);
			}
		});

		container.register(CacheTool.class);

		persisterConfigurator.configure(event, container);
		formProcessingConfigurator.configure(event, container);

		delegate.configure(event, container);
	}

	protected String getBuiltinToolPackage() {
		return SpyGlass.getPackageName(Sails.class) + ".tools";
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
