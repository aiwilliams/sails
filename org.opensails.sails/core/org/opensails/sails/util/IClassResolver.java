package org.opensails.sails.util;

/**
 * We need a way to resolve classes dynamically, based on a key. Sometimes, we
 * have a String. Sometimes, we have a Class.
 * 
 * @param <T> the types of components this resolves
 */
public interface IClassResolver<T> {
    /**
     * @param key
     * @return the Class associated with the String key
     */
    Class<? extends T> resolve(String key);

    /**
     * @param key
     * @return the Class associated with the Class key
     */
    Class<? extends T> resolve(Class key);
}
