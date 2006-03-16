package org.opensails.spyglass;

import org.opensails.sails.util.ClassHelper;

/**
 * Allows for IClassResolver implementations that care only for resolving
 * certain ways.
 * 
 * @author aiwilliams
 * 
 * @param <T> the type of class this resolves
 */
public class ClassResolverAdapter<T> implements IClassResolver<T> {
	/**
	 * Converts the key to a String that is the non-canonical name of the key
	 * Class. Useful for resolving Classes based on the name of another Class.
	 */
	public Class<T> resolve(Class key) {
		return resolve(ClassHelper.getName(key));
	}

	public Class<T> resolve(String key) {
		return null;
	}
}
