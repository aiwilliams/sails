package org.opensails.sails.adapter;

public interface IWebObjectAdapter {
	/**
	 * The simplest hook into the IAdapter framework to get the String
	 * representation of a Java Object.
	 * 
	 * @param value a Java Object that you would like to convert to a String
	 * @return the String representation of value, according to the IAdapter for
	 *         the value
	 */
	String forWebAsString(Object value);
}
