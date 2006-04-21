package org.opensails.sails.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.SpyObject;

/**
 * YAGNI and YARGNI, all in one class.
 * 
 * commons.lang.ClassUtils provides much of the implementation of this class. I
 * desire that Sails use this instead of ClassUtils because 1) we have a few
 * more methods it doesn't and 2) if we decide to get rid of that, the code will
 * be bound to this.
 * 
 * @author aiwilliams
 */
public class ClassHelper {
	private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

	public static Field[] declaredFieldsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		for (Field field : declaredFields)
			if (field.isAnnotationPresent(annotation)) annotatedFields.add(field);
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	public static Field[] declaredFieldsUniquelyAnnotated(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		Set<Annotation> annotations = new HashSet<Annotation>(declaredFields.length);
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(annotationClass);
			if (field.isAnnotationPresent(annotationClass) && !annotations.contains(annotation)) {
				annotations.add(annotation);
				annotatedFields.add(field);
			}
		}
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	/**
	 * @param clazz
	 * @param name
	 * @return an array of Methods with name, in order of least arguments to
	 *         most, or empty array if clazz is null or there are no methods
	 *         having name
	 */
	public static Method[] declaredMethodsNamed(Class<?> clazz, String name) {
		if (clazz == null) return EMPTY_METHOD_ARRAY;

		List<Method> matches = new ArrayList<Method>();
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			if (declaredMethods[i].getName().equals(name)) matches.add(declaredMethods[i]);
		}
		Collections.sort(matches, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				return o2.getParameterTypes().length - o1.getParameterTypes().length;
			}
		});
		return matches.toArray(new Method[matches.size()]);
	}

	public static Field[] fieldsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] declaredFields = clazz.getFields();
		List<Field> annotatedFields = new ArrayList<Field>(declaredFields.length);
		for (Field field : declaredFields)
			if (field.isAnnotationPresent(annotation)) annotatedFields.add(field);
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}

	public static Field[] fieldsNamed(Class clazz, String... names) {
		List<Field> fields = new ArrayList<Field>();
		for (String name : names)
			fields.add(SpyGlass.getField(clazz, name));
		return fields.toArray(new Field[fields.size()]);
	}

	public static String getPackageDirectory(Class<?> clazz) {
		return SpyGlass.getPackageName(clazz).replaceAll("\\.", "/");
	}

	/**
	 * @param clazz
	 * @param interfaze
	 * @return the interface that clazz implements where that inteface is a
	 *         descendent of interfaze
	 */
	@SuppressWarnings("unchecked")
	public static Class interfaceExtending(Class clazz, Class interfaze) {
		Class current = clazz;
		do {
			for (Class i : current.getInterfaces())
				if (interfaze.isAssignableFrom(i)) return i;
			current = current.getSuperclass();
		} while (current != Object.class);
		throw new IllegalArgumentException(String.format("The class does not implement an interface that extends %s", interfaze));
	}

	public static Method[] methodsAnnotated(Class<?> clazz, Class<? extends Annotation> annotation) {
		Method[] declaredMethods = clazz.getDeclaredMethods();
		List<Method> annotatedMethods = new ArrayList<Method>(declaredMethods.length);
		for (Method method : declaredMethods)
			if (method.isAnnotationPresent(annotation)) annotatedMethods.add(method);
		return annotatedMethods.toArray(new Method[annotatedMethods.size()]);
	}

	public static Object readField(Object target, String name) {
		return readField(target, name, true);
	}

	public static Object readField(Object target, String name, boolean publicOnly) {
		return SpyObject.create(target).readSpy(name).getObject();
	}

	public static String upperCamel(String string) {
		char upper = Character.toUpperCase(string.charAt(0));
		return upper + string.substring(1);
	}

	public static void writeDeclaredField(Object target, String field, Object value) {
		SpyObject.create(target).write(field, value);
	}
}
