package org.opensails.sails.util;

/**
 * Allows subclasses that care only for resolving certain ways. Although, if you
 * don't override {@link #resolve(Class)}, it will convert the parameter to a
 * String that is the non-canonical Class name. Useful for resolving Classes
 * based on the name of another Class.
 * 
 * @author aiwilliams
 */
public class ClassResolverAdapter<T> implements IClassResolver<T> {
    public Class<? extends T> resolve(Class key) {
        return resolve(ClassHelper.getName(key));
    }

    public Class<? extends T> resolve(String key) {
        return null;
    }
}
