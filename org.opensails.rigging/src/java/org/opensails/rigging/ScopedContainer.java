package org.opensails.rigging;

public class ScopedContainer extends HierarchicalContainer implements IScopedContainer {
    protected Enum scope;

    public ScopedContainer(Enum scope) {
        this.scope = scope;
    }

    protected ScopedContainer(IScopedContainer parent, Enum scope) {
        super(parent);
        this.scope = scope;
    }

    public IScopedContainer getContainerInHierarchy(Enum scope) {
        if (scope == this.scope) return this;
        if (parent == null) return null;
        return getParent().getContainerInHierarchy(scope);
    }

    @Override
    public IScopedContainer getParent() {
        return (IScopedContainer) super.getParent();
    }

    public Enum getScope() {
        return scope;
    }

    /**
     * Figures out the appropriate scope for the child by looking at the enum
     * type.
     * 
     * @return child
     * @throws NotEnoughScopesException if there isn't another scope in the enum
     * @see ScopedContainer#makeChild(Enum)
     */
    @Override
    public IScopedContainer makeChild() {
        return makeChild(childScope());
    }

    public IScopedContainer makeChild(Enum scope) {
        ScopedContainer child = new ScopedContainer(this, scope);
        addChild(child);
        return child;
    }

    public IHierarchicalContainer makeChildUnscoped() {
        return super.makeChild();
    }

    @Override
    public String toString() {
        return scope + ": " + super.toString();
    }

    protected Enum childScope() {
        int next = scope.ordinal() + 1;
        if (next >= scope.getClass().getEnumConstants().length) throw new NotEnoughScopesException(scope);
        return scope.getClass().getEnumConstants()[next];
    }
}
