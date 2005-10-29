package org.opensails.sails.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quick {
    public static <T> List<T> list(T... objects) {
        ArrayList<T> list = new ArrayList<T>(objects.length);
        for (T object: objects)
            list.add(object);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(Object... objects) {
        if (objects.length % 2 != 0)
            throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
        Map<K, V> map = new HashMap<K, V>();
        for (int i=0; i<objects.length; i+=2)
            map.put((K)objects[i], (V)objects[i+1]);
        return map;
    }
}