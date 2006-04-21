package org.opensails.sails.tester.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.NoImplementationException;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.AbstractActionEventProcessor;
import org.opensails.sails.util.ClassHelper;

public class VirtualControllerResolver implements IControllerResolver {
	protected Map<String, IControllerImpl> instances = new HashMap<String, IControllerImpl>();
	protected IAdapterResolver adapterResolver;

	public VirtualControllerResolver(IAdapterResolver adapterResolver) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.adapterResolver = adapterResolver;
	}

	public <C extends IControllerImpl> void register(C controller) {
		register(Sails.controllerName(controller), controller);
	}

	public <C extends IControllerImpl> void register(String controllerName, C controller) {
		instances.put(controllerName, controller);
	}

	public IController resolve(String controllerIdentifier) {
		IControllerImpl controllerImpl = instances.get(controllerIdentifier);
		return controllerImpl == null ? null : new VirtualController(controllerImpl, adapterResolver);
	}

	public boolean resolvesNamespace(String namespace) {
		return IControllerResolver.NAMESPACE.equals(namespace);
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
			return (I) controllerInstance;
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
