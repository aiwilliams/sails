package org.opensails.viento;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	protected Map<MethodKey, CallableMethod> map = new HashMap<MethodKey, CallableMethod>();

	public CallableMethod find(MethodKey key) {
		return map.get(key);
	}

	public void put(MethodKey key, CallableMethod method) {
		map.put(key, method);
	}
}
