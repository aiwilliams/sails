package org.opensails.sails.action.oem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.IActionResultProcessor;
import org.opensails.sails.action.IActionResultProcessorResolver;
import org.opensails.sails.util.IClassResolver;

public class ActionResultProcessorResolver implements IActionResultProcessorResolver {
    protected final ScopedContainer container;
    protected Map<Class<? extends IActionResult>, Class<? extends IActionResultProcessor>> processorClassCache;
    protected List<IClassResolver> resolvers;

    public ActionResultProcessorResolver(ScopedContainer container) {
        if (container.getScope() != ApplicationScope.SERVLET) throw new IllegalArgumentException("IActionResultProcessors must be scoped at " + ApplicationScope.SERVLET);
        this.container = container;
        this.resolvers = new ArrayList<IClassResolver>();
        this.processorClassCache = new HashMap<Class<? extends IActionResult>, Class<? extends IActionResultProcessor>>();
    }

    public void push(IClassResolver<? extends IActionResultProcessor> resolver) {
        resolvers.add(0, resolver);
    }

    @SuppressWarnings("unchecked")
    public IActionResultProcessor resolve(IActionResult result) {
        Class processorClass = processorClassCache.get(result.getClass());
        if (processorClass == null) {
            for (IClassResolver resolver : resolvers) {
                processorClass = resolver.resolve(result.getClass());
                if (processorClass != null) {
                    processorClassCache.put(result.getClass(), processorClass);
                    break;
                }
            }
        }
        if (processorClass == null) throw new SailsException(String.format("Could not find an %s implementation for %s. If you have created your own %s, please be sure to place it in the processors package of your application.", IActionResultProcessor.class, result.getClass(), IActionResult.class));
        return (IActionResultProcessor) container.instance(processorClass, processorClass);
    }
}
