package org.opensails.sails.tester.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.util.ClassResolverAdapter;

public class VirtualAdapterResolver extends ClassResolverAdapter<IAdapter> {
	protected Map<Class, Class<? extends IAdapter>> registry = new HashMap<Class, Class<? extends IAdapter>>();

	public <T> void register(Class<T> modelType, Class<? extends IAdapter<T, ?>> adapter) {
		registry.put(modelType, adapter);
	}

	@Override
	public Class<? extends IAdapter> resolve(Class key) {
		return registry.get(key);
	}
}
