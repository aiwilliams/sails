package org.opensails.sails.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Thanks to RoleModel Software for the implementation.
 */
public class DoubleKeyedMap<K, K2, V> extends HashMap<K, Map<K2, V>> implements Serializable {
	public DoubleKeyedMap() {
		super();
	}

	public DoubleKeyedMap(DoubleKeyedMap<K, K2, V> map) {
		super();
		putAll(map);
	}

	public V get(K levelOneKey, K2 levelTwoKey) {
		Map<K2, V> secondLevelMap = get(levelOneKey);
		if (secondLevelMap == null) return null;

		return secondLevelMap.get(levelTwoKey);
	}

	public void put(K levelOneKey, K2 levelTwoKey, V value) {
		Map<K2, V> secondLevelMap = get(levelOneKey);
		if (secondLevelMap == null) {
			secondLevelMap = new HashMap<K2, V>();
			put(levelOneKey, secondLevelMap);
		}
		secondLevelMap.put(levelTwoKey, value);
	}

	public void putAll(DoubleKeyedMap<K, K2, V> anotherMap) {
		for (Map.Entry<K, Map<K2, V>> entry : anotherMap.entrySet()) {
			K firstLevelKey = entry.getKey();
			Map<K2, V> secondLevelMap = entry.getValue();
			Map<K2, V> mySecondLevelMap = get(firstLevelKey);
			if (mySecondLevelMap == null) put(firstLevelKey, new HashMap<K2, V>(secondLevelMap));
			else mySecondLevelMap.putAll(secondLevelMap);
		}
	}

	public V remove(K levelOneKey, K2 levelTwoKey) {
		Map<K2, V> secondLevelMap = get(levelOneKey);
		if (secondLevelMap == null) return null;
		return secondLevelMap.remove(levelTwoKey);
	}
}
