package org.opensails.rigging;

import java.util.Set;

public interface IComponentResolverResolver {
    /**
     * @param key
     * @param container
     * @return true if this can resolve a ComponentResolver for key
     */
    boolean canResolve(Class key, SimpleContainer container);

    /**
     * @return the current known set of keys, which will change as
     *         ComponentResolvers are added or removed
     */
    Set<Class> keySet();

    ComponentResolver resolve(Class key, SimpleContainer container);
}
