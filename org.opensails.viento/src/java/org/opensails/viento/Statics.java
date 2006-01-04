package org.opensails.viento;

import java.util.HashMap;
import java.util.Map;

public class Statics {
	protected Map<String, Object> map = new HashMap<String, Object>();

	public CallableMethod find(TopLevelMethodKey key) {
		if (key.argClasses.length > 0)
			return null;
		if (!map.containsKey(key.methodName))
			return null;
		return new ObjectReference(map.get(key.methodName));
	}

	public Object get(String key) {
		return map.get(key);
	}

	public void put(String key, Object object) {
		map.put(key, object);
	}
}
