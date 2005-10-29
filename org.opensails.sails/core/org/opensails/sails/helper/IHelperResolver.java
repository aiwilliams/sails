package org.opensails.sails.helper;

public interface IHelperResolver {
    Object methodMissing(String methodName, Object[] args) throws NoSuchMethodException;
}
