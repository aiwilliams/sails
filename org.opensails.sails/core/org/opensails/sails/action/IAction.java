package org.opensails.sails.action;

/**
 * An action descriptor, if you will.
 * 
 * @author aiwilliams
 */
public interface IAction {
	/**
	 * @param numberOfArguments
	 * @return a Class[] representing the types of the arguments for the action
	 *         that requires the specified numberOfArguments
	 */
	Class<?>[] getParameterTypes(int numberOfArguments);
}