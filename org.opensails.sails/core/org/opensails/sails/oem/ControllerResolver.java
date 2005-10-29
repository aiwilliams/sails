package org.opensails.sails.oem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.controller.oem.IControllerResolver;
import org.opensails.sails.util.IClassResolver;

public class ControllerResolver implements IControllerResolver {
    protected final IAdapterResolver adapterResolver;
    protected final List<IClassResolver<IController>> classResolvers;
    protected final Map<String, Controller> controllerCache;

    public ControllerResolver(IAdapterResolver adapterResolver) {
        if (adapterResolver == null) throw new NullPointerException("You must provide an implementation of " + IAdapterResolver.class);
        this.adapterResolver = adapterResolver;
        this.classResolvers = new ArrayList<IClassResolver<IController>>();
        this.controllerCache = new HashMap<String, Controller>();
    }

    public void push(IClassResolver<IController> controllerClassResolver) {
        classResolvers.add(0, controllerClassResolver);
    }

    public Controller resolve(String controllerIdentifier) {
        Controller controller = controllerCache.get(controllerIdentifier);
        if (controller == null) {
            Class<? extends IController> controllerImplementation = null;
            for (IClassResolver<IController> resolver : classResolvers) {
                controllerImplementation = resolver.resolve(controllerIdentifier);
                if (controllerImplementation != null) break;
            }
            controllerCache.put(controllerIdentifier, controller = new Controller(controllerImplementation, adapterResolver));
        }
        return controller;
    }
}
