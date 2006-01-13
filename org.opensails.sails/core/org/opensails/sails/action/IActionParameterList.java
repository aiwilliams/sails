package org.opensails.sails.action;

public interface IActionParameterList {
	/**
	 * @param targetTypes
	 * @return action parameters adapted to targetTypes
	 */
	Object[] objects(Class<?>[] targetTypes);

	/**
	 * @return action parameters in original String form
	 */
	String[] strings();

	/**
	 * @param index
	 * @return original parameter at position index
	 * @throws IndexOutOfBoundsException
	 */
	String stringAt(int index) throws IndexOutOfBoundsException;

	/**
	 * @return the number of action parameters
	 */
	int size();
}