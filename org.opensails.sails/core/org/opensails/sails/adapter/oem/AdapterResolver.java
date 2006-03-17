package org.opensails.sails.adapter.oem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.SailsException;
import org.opensails.sails.Scope;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.spyglass.IClassResolver;

public class AdapterResolver implements IAdapterResolver {
	protected final Map<Class, Class<? extends IAdapter>> adapterClassCache;
	protected final Map<Class, IAdapter> adapterInstanceCache;
	protected final List<IClassResolver> classResolvers;

	public AdapterResolver() {
		adapterClassCache = new HashMap<Class, Class<? extends IAdapter>>();
		adapterInstanceCache = new HashMap<Class, IAdapter>();
		classResolvers = new ArrayList<IClassResolver>();
		push(new DefaultAdapterResolver<IAdapter>());
	}

	public void push(IClassResolver<? extends IAdapter> resolver) {
		classResolvers.add(0, resolver);
	}

	public IAdapter resolve(Class<?> parameterClass, IScopedContainer container) {
		IAdapter adapter = adapterInstanceCache.get(parameterClass);
		if (adapter != null) return adapter;

		Class<? extends IAdapter> adapterClass = adapterClassCache.get(parameterClass);
		if (adapterClass == null) for (IClassResolver<IAdapter> resolver : classResolvers) {
			adapterClass = resolver.resolve(parameterClass);
			if (adapterClass != null) break;
		}
		if (adapterClass == null) throw new SailsException(String.format("No %s found to adapt parameter of type %s. Please be sure that your %s installs the appropriate %s", IAdapter.class, parameterClass, ISailsApplicationConfigurator.class, IAdapterResolver.class));
		ApplicationScope scope = getScope(adapterClass);
		if (scope != null) {
			IScopedContainer targetContainer = container.getContainerInHierarchy(scope);
			if (!targetContainer.containsLocally(adapterClass)) targetContainer.register(adapterClass);
			return targetContainer.instance(adapterClass);
		} else { // IAdapter did not declare scope - instance will be cached
			try {
				adapter = adapterClass.newInstance();
				adapterInstanceCache.put(parameterClass, adapter);
				return adapter;
			} catch (Exception e) {
				throw new SailsException("Your adapter " + adapterClass + " does not decare it's Scope. Therefore, it must have a default constructor, as it will be cached.");
			}
		}
	}

	protected ApplicationScope getScope(Class<? extends IAdapter> adapterClass) {
		Scope annotation = adapterClass.getAnnotation(Scope.class);
		if (annotation == null) return null;
		return annotation.value();
	}
}
