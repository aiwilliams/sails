package org.opensails.sails.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.SailsException;

/**
 * YAGNI and YARGNI, all in one class.
 * 
 * commons.lang.ClassUtils provides much of the implementation of this class. I
 * desire that Sails use this instead of ClassUtils because 1) we have a few
 * more methods it doesn't and 2) if we decide to get rid of that, the code will
 * be bound to this.
 * 
 * @author aiwilliams
 * 
 */
public class ClassHelper {
	public static Field fieldNamed(Class clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (Throwable t) {
			throw new RuntimeException(String.format("Could not find a field named %s on %s", name, clazz), t);
		}
	}

	public static Field[] fieldsNamed(Class clazz, String... names) {
		List<Field> fields = new ArrayList<Field>();
		for (String name : names)
			fields.add(fieldNamed(clazz, name));
		return fields.toArray(new Field[fields.size()]);
	}

	public static String getName(Class clazz) {
		return ClassUtils.getShortClassName(clazz);
	}

	public static String getPackage(Class clazz) {
		return ClassUtils.getPackageName(clazz);
	}

	public static String getPackage(Object instance) {
		return getPackage(instance.getClass());
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return getPackage(clazz).replaceAll("\\.", "/");
	}

	public static <T> T instantiate(Class<? extends T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new SailsException("Could not instantiate. Does it have a default constructor?", e);
		} catch (IllegalAccessException e) {
			throw new SailsException("Could not instantiate. Know anything about access?", e);
		}
	}

	public static String lowerCamelName(Class clazz) {
		String className = getName(clazz);
		char lower = Character.toLowerCase(className.charAt(0));
		return lower + className.substring(1);
	}

	public static String lowerCamelName(Object instance) {
		return lowerCamelName(instance.getClass());
	}

	public static String upperCamel(String string) {
		char upper = Character.toUpperCase(string.charAt(0));
		return upper + string.substring(1);
	}
}
