package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.util.ClassResolverAdapter;

public class DefaultAdapterResolver extends ClassResolverAdapter<IAdapter> {
	@Override
	public Class<? extends IAdapter> resolve(Class key) {
		if (key.isPrimitive() || String.class == key) return PrimitiveAdapter.class;
		else if (IIdentifiable.class.isAssignableFrom(key)) return IdentifiableAdapter.class;
		return null;
	}
}
