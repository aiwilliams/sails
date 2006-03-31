package org.opensails.sails.model;

public interface IPropertyPath {
	/**
	 * @return the number of nodes in this path
	 */
	public int getNodeCount();

	/**
	 * @return the path nodes, including the target identifier
	 */
	String[] getAllNodes();

	/**
	 * The model name is used to identify which Object this should be used on.
	 * {@link #getAllNodes()} will include this in position 0.
	 * 
	 * @return the name of the model upon which this property exists
	 */
	String getModelName();

	/**
	 * @return the path nodes relative to the target
	 */
	String[] getNodes();

	/**
	 * @return the name of the property on the target
	 */
	String getPropertyName();
}
