package org.opensails.sails.form;

/**
 * Creates valid HTML element ids.
 * <p>
 * This is pluggable because I believe there are people out there who don't
 * think we do it correctly.
 * 
 * @author aiw10802
 */
public interface IFormElementIdGenerator {
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
	 * @param strings that will be converted
	 * @return a useable id from an array of Strings
	 */
	String idForStrings(String... strings);
}
