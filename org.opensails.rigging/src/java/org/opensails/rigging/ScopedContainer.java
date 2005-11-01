package org.opensails.rigging;

/**
 * A Container that works with named scopes. For best results, order your
 * constants from highest scope to lowest in the enum declaration.
 */
public class ScopedContainer extends HierarchicalContainer {
	protected Enum scope;

	public ScopedContainer(Enum scope) {
		this.scope = scope;
	}

	protected ScopedContainer(ScopedContainer parent, Enum scope) {
		super(parent);
		this.scope = scope;
	}

	public Enum getScope() {
		return scope;
	}

	/**
	 * Figures out the appropriate scope for the child by looking at the enum
	 * type.
	 * 
	 * @return child
	 * @throws NotEnoughScopesException
	 *             if there isn't another scope in the enum
	 * @see ScopedContainer#makeChild(Enum)
	 */
	@Override
	public ScopedContainer makeChild() {
		return makeChild(childScope());
	}

	protected Enum childScope() {
		int next = scope.ordinal() + 1;
		if (next >= scope.getClass().getEnumConstants().length)
			throw new NotEnoughScopesException(scope);
		return scope.getClass().getEnumConstants()[next];
	}

	/**
	 * @param scope
	 * @return a container at the requested scope. Will be either this
	 *         container, or a container in its ancestry, or null if none
	 *         could be found.
	 */
	public ScopedContainer getContainerInHierarchy(Enum scope) {
		if (scope == this.scope)
			return this;
		if (parent == null)
			return null;
		return getParent().getContainerInHierarchy(scope);
	}

	@Override
	public ScopedContainer getParent() {
		return (ScopedContainer) super.getParent();
	}

	/**
	 * Creates a <em>immediate</em> child with the given scope. Does
	 * <em>not</em> walk down the tree.
	 * 
	 * @param scope
	 * @return child
	 */
	public ScopedContainer makeChild(Enum scope) {
		ScopedContainer child = new ScopedContainer(this, scope);
		children.add(child);
		return child;
	}

    @Override
    public String toString() {
    	return scope + ": " + super.toString();
    }
}
