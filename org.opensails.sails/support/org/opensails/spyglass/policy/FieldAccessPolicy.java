package org.opensails.spyglass.policy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class FieldAccessPolicy {
	public static FieldAccessPolicy PRIVATE = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return true;
		}
	};
	public static FieldAccessPolicy PROTECTED = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return field.isAccessible() || Modifier.isProtected(field.getModifiers());
		}
	};
	public static FieldAccessPolicy PUBLIC = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return field.isAccessible();
		}
	};

	public abstract boolean canAccess(Field field);
}
