package org.opensails.sails.util;

import java.util.HashMap;

/**
 * First in for a key wins.
 */
public class WriteOnceHashMap<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = -6785043132405603970L;

    @Override
    public V put(K key, V value) {
        if (!containsKey(key))
            return super.put(key, value);
        return value;
    }
}
