package org.opensails.sails.adapter.oem;

import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.spyglass.ClassResolverAdapter;

public class DefaultAdapterResolver<A extends IAdapter> extends ClassResolverAdapter<A> {
	@Override
	@SuppressWarnings("unchecked")
	public Class<A> resolve(Class key) {
		if (String.class == key || key.isPrimitive() || isPrimitiveWrapper(key)) return (Class<A>) PrimitiveAdapter.class;
		else if (key.isArray()) {
			if (key.getComponentType() == String.class) return (Class<A>) StringArrayAdapter.class;
			else return (Class<A>) ArrayAdapter.class;
		} else if (IIdentifiable.class.isAssignableFrom(key)) return (Class<A>) IdentifiableAdapter.class;
		else if (key.isEnum()) return (Class<A>) EnumAdapter.class;
		return null;
	}

	protected boolean isPrimitiveWrapper(Class key) {
		return key == Integer.class || key == Long.class || key == Float.class || key == Boolean.class || key == Character.class || key == Double.class || key == Byte.class
				|| key == Short.class;
	}
}
