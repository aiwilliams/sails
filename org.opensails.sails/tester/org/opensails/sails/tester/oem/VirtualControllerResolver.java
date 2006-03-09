package org.opensails.sails.tester.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.IControllerResolver;
import org.opensails.sails.controller.oem.Controller;

public class VirtualControllerResolver implements IControllerResolver {
	protected Map<String, IControllerImpl> instances = new HashMap<String, IControllerImpl>();
	protected IAdapterResolver adapterResolver;

	public VirtualControllerResolver(IAdapterResolver adapterResolver) {
		if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
		this.adapterResolver = adapterResolver;
	}

	public <C extends IControllerImpl> void register(C controller) {
		instances.put(Sails.controllerName(controller), controller);
	}

	public IController resolve(String controllerIdentifier) {
		IControllerImpl controllerImpl = instances.get(controllerIdentifier);
		return controllerImpl == null ? null : new VirtualController(controllerImpl, adapterResolver);
	}

	public boolean resolvesNamespace(String namespace) {
		return IControllerResolver.NAMESPACE.equals(namespace);
	}

	static class VirtualController extends Controller {
		protected IControllerImpl controllerInstance;

		@SuppressWarnings("unchecked")
		public VirtualController(IControllerImpl controller, IAdapterResolver adapterResolver) {
			super(null, adapterResolver);
			this.controllerInstance = controller;
		}
	}
}
