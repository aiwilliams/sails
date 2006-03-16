package org.opensails.sails.tester.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.IAdapter;
import org.opensails.spyglass.ClassResolverAdapter;

public class VirtualAdapterResolver<A extends IAdapter> extends ClassResolverAdapter<A> {
	protected Map<Class, Class<A>> registry = new HashMap<Class, Class<A>>();

	public <T> void register(Class<T> modelType, Class<A> adapter) {
		registry.put(modelType, adapter);
	}

	@Override
	public Class<A> resolve(Class key) {
		return registry.get(key);
	}
}
