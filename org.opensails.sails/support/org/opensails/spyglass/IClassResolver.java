package org.opensails.spyglass;

/**
 * Provides dynamic class resolution, hopefully in a way that is simple yet
 * powerful enough.
 * <p>
 * We need a way to resolve classes dynamically, based on a key. Sometimes, we
 * have a String. Sometimes, we have a Class.
 * <p>
 * Each resolver resolves a particular type or subclass of that type. This means
 * that generics are used to make type casting less painful.
 * 
 * @author aiwilliams
 * 
 * @param <T> the type of class this resolves
 */
public interface IClassResolver<T> {
	/**
	 * @param key
	 * @return the Class associated with the Class key, null if it cannot
	 *         resolve
	 */
	Class<T> resolve(Class key);

	/**
	 * @param key
	 * @return the Class associated with the String key, null if it cannot
	 *         resolve
	 */
	Class<T> resolve(String key);
}
