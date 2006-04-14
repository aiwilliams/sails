package org.opensails.spyglass.policy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class FieldAccessPolicy {
	public static FieldAccessPolicy PRIVATE = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return true;
		}

		@Override
		public String getDescription() {
			return "public, protected and private";
		}
	};
	public static FieldAccessPolicy PROTECTED = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers());
		}

		@Override
		public String getDescription() {
			return "public and protected";
		}
	};
	public static FieldAccessPolicy PUBLIC = new FieldAccessPolicy() {
		@Override
		public boolean canAccess(Field field) {
			return Modifier.isPublic(field.getModifiers());
		}

		@Override
		public String getDescription() {
			return "public only";
		}
	};

	public abstract boolean canAccess(Field field);

	/**
	 * This describes what the policy will allow.
	 * <p>
	 * Dynamic policies should give descriptions that will make sense of their
	 * programmatic rules.
	 * 
	 * @return a description of this policy
	 */
	public abstract String getDescription();

	@Override
	public String toString() {
		return String.format("Field: %s", getDescription());
	}
}
