package org.opensails.sails.helper;

public interface IMixinResolver {
    Object methodMissing(String methodName, Object[] args) throws NoSuchMethodException;
}
