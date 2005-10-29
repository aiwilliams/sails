package org.opensails.sails.util;

/**
 * Generates ids for anything!
 */
public interface IIdGenerator<T extends Object> {
	public abstract T next();
}