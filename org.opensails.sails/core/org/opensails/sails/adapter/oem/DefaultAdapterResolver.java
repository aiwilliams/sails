package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.util.ClassResolverAdapter;

public class DefaultAdapterResolver extends ClassResolverAdapter<IAdapter> {
	@Override
	public Class<? extends IAdapter> resolve(Class key) {
		if (String.class == key || key.isPrimitive() || isPrimitiveWrapper(key)) return PrimitiveAdapter.class;
		else if (IIdentifiable.class.isAssignableFrom(key)) return IdentifiableAdapter.class;
		return null;
	}

	protected boolean isPrimitiveWrapper(Class key) {
		return
			key == Integer.class ||
			key == Long.class ||
			key == Float.class ||
			key == Boolean.class ||
			key == Character.class ||
			key == Double.class ||
			key == Byte.class ||
			key == Short.class;
	}
}
