package org.opensails.sails.util;

import java.util.*;

public class Quick {
	public static <T> List<T> list(T... objects) {
		if (objects == null) return new ArrayList<T>(0);
		ArrayList<T> list = new ArrayList<T>(objects.length);
		for (T object : objects)
			list.add(object);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <KV> Map<KV, KV> map(Class<KV> keyValueType, Object... objects) {
		Map<KV, KV> map = new HashMap<KV, KV>();
		if (objects == null) return map;
		if (objects.length % 2 != 0) throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
		for (int i = 0; i < objects.length; i += 2)
			map.put((KV) objects[i], (KV) objects[i + 1]);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map(Object... objects) {
		Map<K, V> map = new HashMap<K, V>();
		if (objects == null) return map;
		if (objects.length % 2 != 0) throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
		for (int i = 0; i < objects.length; i += 2)
			map.put((K) objects[i], (V) objects[i + 1]);
		return map;
	}

	public static String string(Object... objects) {
		StringBuilder buffer = new StringBuilder();
		for (Object object : objects)
			buffer.append(object);
		return buffer.toString();
	}
}