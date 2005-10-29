package org.opensails.sails.util;

import java.lang.reflect.Field;

public class FieldAccessor {
	public static Object read(String fieldName, Object target) {
		return new FieldAccessor(fieldName).get(target);
	}

	public static void write(String fieldName, Object target, Object value) {
		new FieldAccessor(fieldName).set(target, value);
	}

	protected Field field;
	protected String fieldName;

	public FieldAccessor(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object get(Object object) {
		try {
			return getField(object).get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Field getField(Class type) {
		Field field = null;
		Class nextClass = type;
		while (nextClass != null && field == null) {
			try {
				field = nextClass.getDeclaredField(fieldName);
			} catch (Exception e) {}
			nextClass = nextClass.getSuperclass();
		}
		if (field != null) field.setAccessible(true);
		return field;
	}

	public Field getField(Object object) {
		if (field != null) return field;
		return getField(object.getClass());
	}

	public void set(Object object, Object value) {
		try {
			getField(object).set(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}