package org.opensails.spyglass.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.opensails.spyglass.IClassResolver;

/**
 * Provides a single point of access to multiple IClassResolver implementations.
 * 
 * @author aiwilliams
 */
public class CompositeClassResolver<T> implements IClassResolver<T> {
	protected List<IClassResolver<T>> classResolvers = new ArrayList<IClassResolver<T>>();

	public void append(IClassResolver<T> resolver) {
		classResolvers.add(resolver);
	}

	public void insert(IClassResolver<T> resolver, IClassResolver<T> before) {
		int location = classResolvers.indexOf(before) - 1;
		if (location < 0) location = 0;
		classResolvers.add(location, resolver);
	}

	public void push(IClassResolver<T> resolver) {
		classResolvers.add(0, resolver);
	}

	public Class<T> resolve(Class key) {
		for (IClassResolver<T> resolver : classResolvers) {
			Class<T> implementation = resolver.resolve(key);
			if (implementation != null) return implementation;
		}
		return null;
	}

	public Class<T> resolve(String key) {
		for (IClassResolver<T> resolver : classResolvers) {
			Class<T> implementation = resolver.resolve(key);
			if (implementation != null) return implementation;
		}
		return null;
	}
}
