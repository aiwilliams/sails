package org.opensails.viento;

/**
 * Capable of resolving a CallableMethod using a TargetedMethodKey.
 * 
 * @author aiwilliams
 */
public interface IMethodResolver {
	public CallableMethod find(TargetedMethodKey key);
}
