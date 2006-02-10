package org.opensails.rigging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapComponentResolverResolver implements IComponentResolverResolver {
    private Map<Class, ComponentResolver> componentResolvers;

    public MapComponentResolverResolver() {
        componentResolvers = new LinkedHashMap<Class, ComponentResolver>();
    }

    public Set<Class> keySet() {
    	synchronized(componentResolvers) {
    		return new HashSet<Class>(componentResolvers.keySet());
    	}
    }

    public void put(Class<?> key, ComponentResolver resolver) {
    	synchronized(componentResolvers) {
    		componentResolvers.put(key, resolver);
    	}
    }
    
    protected Map<Class, ComponentResolver> copyOfMap() {
		synchronized (componentResolvers) {
			return new HashMap<Class, ComponentResolver>(componentResolvers);
		}    	
    }
    
    public void putAll(MapComponentResolverResolver resolver) {
    	synchronized(componentResolvers) {
    		componentResolvers.putAll(resolver.copyOfMap());
    	}
    }

    public ComponentResolver resolve(Class key, SimpleContainer container) {
        return componentResolvers.get(key);
    }

    public boolean canResolve(Class key, SimpleContainer container) {
        return componentResolvers.containsKey(key);
    }
}
