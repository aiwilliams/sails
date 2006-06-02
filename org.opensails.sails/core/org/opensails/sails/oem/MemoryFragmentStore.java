package org.opensails.sails.oem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.opensails.sails.template.IFragmentStore;

public class MemoryFragmentStore implements IFragmentStore {
	protected final Map<FragmentKey, String> cache = new HashMap<FragmentKey, String>();

	public void delete(FragmentKey identifier) {
		cache.remove(identifier);
	}

	public String read(FragmentKey identifier) {
		return cache.get(identifier);
	}

	public void write(FragmentKey identifier, String content) {
		cache.put(identifier, content);
	}

	public Set<FragmentKey> keySet() {
		return cache.keySet();
	}
}
