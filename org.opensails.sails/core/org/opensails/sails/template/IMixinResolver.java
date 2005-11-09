package org.opensails.sails.template;

public interface IMixinResolver {
    Object methodMissing(String methodName, Object[] args) throws NoSuchMethodException;
}
