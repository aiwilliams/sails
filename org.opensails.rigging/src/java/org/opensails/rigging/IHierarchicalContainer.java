package org.opensails.rigging;

public interface IHierarchicalContainer extends IContainer {
    void addChild(IHierarchicalContainer child);

    /**
     * @param key
     * @return whether the key exists locally in this container.
     * @see HierarchicalContainer#contains(Class)
     */
    boolean containsLocally(Class key);

    IHierarchicalContainer getParent();

    /**
     * @return child
     */
    IHierarchicalContainer makeChild();

    void makeLocal(Class key);

    /**
     * Causes container to forget it's parent
     */
    void orphan();

    /**
     * Removes the child.
     * <p>
     * Implementations would be wise to {@link #orphan()} the child.
     * 
     * @param child
     */
    void removeChild(IHierarchicalContainer child);
}