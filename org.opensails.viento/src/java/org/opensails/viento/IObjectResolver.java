package org.opensails.viento;

/**
 * Capable of resolving a CallableMethod using a TopLevelMethodKey.
 * <p>
 * For the Viento expression <code>
 * $something or $something() or $something($somethingElse)
 * </code>,
 * an IObjectResolver can return a value for all those somethings.
 * 
 * @author aiwilliams
 * 
 */
public interface IObjectResolver {
	CallableMethod find(TopLevelMethodKey key);
}
