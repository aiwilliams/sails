package org.opensails.rigging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HierarchicalContainer extends SimpleContainer {
	protected HierarchicalContainer parent;
	private List<HierarchicalContainer> children;

	public HierarchicalContainer() {
		children = new ArrayList<HierarchicalContainer>();
	}

	protected HierarchicalContainer(HierarchicalContainer parent) {
		this();
		this.parent = parent;
	}

	public void addChild(HierarchicalContainer child) {
		synchronized (children) {
			children.add(child);
		}
	}

	@Override public <T> Collection<T> allInstances(Class<T> type, boolean shouldInstantiate) {
		Collection<T> instances = super.allInstances(type, shouldInstantiate);
		for (HierarchicalContainer child : copyOfChildren())
			instances.addAll(child.allInstances(type, shouldInstantiate));
		return instances;
	}

	/**
	 * @param key
	 * @return whether the key exists anywhere in this container or its
	 *         ancestry.
	 * @see HierarchicalContainer#containsLocally(Class)
	 */
	@Override public boolean contains(Class key) {
		return containsLocally(key) || (parent != null && parent.contains(key));
	}

	/**
	 * @param key
	 * @return whether the key exists locally in this container.
	 * @see HierarchicalContainer#contains(Class)
	 */
	public boolean containsLocally(Class key) {
		return super.contains(key);
	}

	public HierarchicalContainer getParent() {
		return parent;
	}

	@Override public <T> T instance(Class<T> key) {
		if (containsLocally(key))
			return super.instance(key);
		if (parent == null)
			return null;
		return parent.instance(key);
	}

	/**
	 * @return child
	 */
	public HierarchicalContainer makeChild() {
		HierarchicalContainer child = new HierarchicalContainer(this);
		addChild(child);
		return child;
	}

	@SuppressWarnings("unchecked") public void makeLocal(Class key) {
		ComponentResolver resolver = resolverInHeirarchy(key);
		if (resolver != null)
			registerResolver(key, resolver.cloneFor(this));
	}

	public void removeChild(HierarchicalContainer child) {
		synchronized (children) {
			children.remove(child);
		}
		child.orphan();
	}

	protected List<HierarchicalContainer> copyOfChildren() {
		synchronized (children) {
			return new ArrayList<HierarchicalContainer>(children);
		}
	}

	protected void orphan() {
		parent = null;
	}

	protected <T> ComponentResolver resolverInHeirarchy(Class<T> key) {
		ComponentResolver resolver = null;
		HierarchicalContainer container = this;
		while (resolver == null && container != null) {
			resolver = container.resolver(key);
			container = container.getParent();
		}
		return resolver;
	}
}
