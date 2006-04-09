package org.opensails.sails.model;

public interface IPropertyPath {
	/**
	 * @return the number of nodes in this path
	 */
	public int getNodeCount();

	/**
	 * @return a new path without the model node.
	 */
	IPropertyPath dropModelNode();

	/**
	 * @return the path nodes, including the target identifier
	 */
	String[] getAllNodes();

	/**
	 * @return the first node after the model node.
	 */
	String getFirstProperty();

	/**
	 * @return the last node
	 */
	String getLastProperty();

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
	 * @return the number of nodes not counting the model node.
	 */
	int getPropertyCount();

	/**
	 * @return the name of the property on the target
	 */
	String getPropertyName();
}
