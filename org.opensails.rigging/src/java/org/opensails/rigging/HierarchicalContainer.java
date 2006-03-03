package org.opensails.rigging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HierarchicalContainer extends SimpleContainer implements IHierarchicalContainer {
    protected IHierarchicalContainer parent;

    private List<IHierarchicalContainer> children;

    public HierarchicalContainer() {
        children = new ArrayList<IHierarchicalContainer>();
    }

    protected HierarchicalContainer(IHierarchicalContainer parent) {
        this();
        this.parent = parent;
    }

    public void addChild(IHierarchicalContainer child) {
        synchronized (children) {
            children.add(child);
        }
    }

    @Override
    public <T> Collection<T> allInstances(Class<T> type, boolean shouldInstantiate) {
        Collection<T> instances = super.allInstances(type, shouldInstantiate);
        for (IHierarchicalContainer child : copyOfChildren())
            instances.addAll(child.allInstances(type, shouldInstantiate));
        return instances;
    }

    @Override
    public boolean contains(Class key) {
        return containsLocally(key) || (parent != null && parent.contains(key));
    }

    public boolean containsLocally(Class key) {
        return super.contains(key);
    }

    public IHierarchicalContainer getParent() {
        return parent;
    }

    @Override
    public <T> T instance(Class<T> key) {
        if (containsLocally(key)) return super.instance(key);
        if (parent == null) return null;
        return parent.instance(key);
    }

    public IHierarchicalContainer makeChild() {
        IHierarchicalContainer child = new HierarchicalContainer(this);
        addChild(child);
        return child;
    }

    @SuppressWarnings("unchecked")
    public void makeLocal(Class key) {
        ComponentResolver resolver = resolverInHeirarchy(key);
        if (resolver != null) registerResolver(key, resolver.cloneFor(this));
    }

    public void orphan() {
        parent = null;
    }

    public void removeChild(IHierarchicalContainer child) {
        synchronized (children) {
            children.remove(child);
        }
        child.orphan();
    }

    protected List<IHierarchicalContainer> copyOfChildren() {
        synchronized (children) {
            return new ArrayList<IHierarchicalContainer>(children);
        }
    }

    protected <T> ComponentResolver resolverInHeirarchy(Class<T> key) {
        ComponentResolver resolver = null;
        IHierarchicalContainer container = this;
        while (resolver == null && container != null) {
            resolver = container.resolver(key);
            container = container.getParent();
        }
        return resolver;
    }
}
