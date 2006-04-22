package org.opensails.sails.tester.oem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.NoImplementationException;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.AbstractActionEventProcessor;
import org.opensails.sails.util.ClassHelper;

public class VirtualControllerResolver implements IControllerResolver {
	protected Map<String, IControllerImpl> instances = new HashMap<String, IControllerImpl>();
	protected Map<String, Class<? extends IControllerImpl>> types = new HashMap<String, Class<? extends IControllerImpl>>();
	protected IAdapterResolver adapterResolver;
	protected Set<String> used;

	public VirtualControllerResolver(IAdapterResolver adapterResolver) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.adapterResolver = adapterResolver;
		this.used = new HashSet<String>();
	}

	public <C extends IControllerImpl> void register(C controller) {
		register(Sails.controllerName(controller), controller);
	}

	public void register(Class<? extends IControllerImpl> controller) {
		register(Sails.controllerName(controller), controller);
	}

	public <C extends IControllerImpl> void register(String controllerIdentifier, C controller) {
		instances.put(controllerIdentifier, controller);
		used.remove(controllerIdentifier);
	}

	public void register(String controllerIdentifier, Class<? extends IControllerImpl> controller) {
		types.put(controllerIdentifier, controller);
	}

	@SuppressWarnings("unchecked")
	public IController resolve(String controllerIdentifier) {
		IControllerImpl controllerImpl = instances.get(controllerIdentifier);
		if (controllerImpl != null) {
			ensureSingleUse(controllerIdentifier);
			return new VirtualController(controllerImpl, adapterResolver);
		}

		Class<IControllerImpl> controllerImplClass = (Class<IControllerImpl>) types.get(controllerIdentifier);
		if (controllerImplClass != null) { return new Controller<IControllerImpl>(controllerImplClass, adapterResolver); }
		return null;
	}

	public boolean resolvesNamespace(String namespace) {
		return IControllerResolver.NAMESPACE.equals(namespace);
	}

	protected void ensureSingleUse(String controllerIdentifier) {
		if (used.contains(controllerIdentifier)) throw new IllegalStateException(String.format("The virtual controller [%s] instance cannot be used twice. Controllers are normally created one for each request. Too many opportunities for bugs if we don't honor that.", controllerIdentifier));
		else used.add(controllerIdentifier);
	}

	@SuppressWarnings("unchecked")
	static class VirtualController<I extends IControllerImpl> extends AbstractActionEventProcessor<I> implements IController {
		protected IControllerImpl controllerInstance;

		public VirtualController(IControllerImpl controller, IAdapterResolver adapterResolver) {
			super((Class<I>) controller.getClass(), adapterResolver);
			this.controllerInstance = controller;
		}

		@Override
		public I createInstance(ISailsEvent event) throws NoImplementationException {
			throw new UnsupportedOperationException("This should not be called for virtual controllers. If you are seeing this, I am wrong.");
		}

		@Override
		protected I createInstance(ISailsEvent event, Class<I> contextImpl) {
			// FOREIGN - DUPLICATION - RequestContainer#createEventContext
			event.getContainer().register(IEventProcessingContext.class, controllerInstance);
			event.getContainer().register(ClassHelper.interfaceExtending(controllerInstance.getClass(), IEventProcessingContext.class), controllerInstance);
			// END FOREIGN
			return (I) controllerInstance;
		}

		@Override
		protected I createNullInstance(ISailsEvent event) {
			throw new UnsupportedOperationException("This should not be called, as the instance certainly exists.");
		}
	}
}
