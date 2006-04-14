package org.opensails.sails.html;

/**
 * Creates valid HTML element ids.
 * 
 * @author aiwilliams
 */
public interface IElementIdGenerator {
	/**
	 * @param label that is shown in UI
	 * @return a useable id from a label
	 */
	String idForLabel(String label);

	/**
	 * @param name that is used in an element
	 * @return a useable id from a name
	 */
	String idForName(String name);

	/**
	 * @param name
	 * @param value
	 * @return an id built from the name and value, providing for unique ids
	 *         where multiple elements can have the same name
	 */
	String idForNameValue(String name, String value);

	/**
	 * @param strings that will be converted
	 * @return a useable id from an array of Strings
	 */
	String idForStrings(String... strings);
}
