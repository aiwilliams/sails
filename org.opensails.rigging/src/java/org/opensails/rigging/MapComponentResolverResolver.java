package org.opensails.rigging;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapComponentResolverResolver implements IComponentResolverResolver {
    protected Map<Class, ComponentResolver> componentResolvers;

    public MapComponentResolverResolver() {
        componentResolvers = new LinkedHashMap<Class, ComponentResolver>();
    }

    public Set<Class> keySet() {
        return componentResolvers.keySet();
    }

    public void put(Class<?> key, ComponentResolver resolver) {
        componentResolvers.put(key, resolver);
    }

    public ComponentResolver resolve(Class key, SimpleContainer container) {
        return componentResolvers.get(key);
    }

    public boolean canResolve(Class key, SimpleContainer container) {
        return componentResolvers.containsKey(key);
    }
}
