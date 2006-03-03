package org.opensails.rigging;

/**
 * A Container that works with named scopes. For best results, order your
 * constants from highest scope to lowest in the enum declaration.
 */
public interface IScopedContainer extends IHierarchicalContainer {
	/**
	 * @param scope
	 * @return a container at the requested scope. Will be either this
	 *         container, or a container in its ancestry, or null if none could
	 *         be found.
	 */
	IScopedContainer getContainerInHierarchy(Enum scope);

	Enum getScope();

	/**
	 * Figures out the appropriate scope for the child by looking at the enum
	 * type.
	 * 
	 * @return child
	 * @throws NotEnoughScopesException
	 *             if there isn't another scope in the enum
	 * @see ScopedContainer#makeChild(Enum)
	 */
	IScopedContainer makeChild();

	/**
	 * Creates a <em>immediate</em> child with the given scope. Does
	 * <em>not</em> walk down the tree.
	 * 
	 * @param scope
	 * @return child
	 */
	IScopedContainer makeChild(Enum scope);

	/**
	 * Creates a child that is a HierarchicalContainer
	 * 
	 * @return child
	 * @see HierarchicalContainer#makeChild()
	 */
	IHierarchicalContainer makeChildUnscoped();
}