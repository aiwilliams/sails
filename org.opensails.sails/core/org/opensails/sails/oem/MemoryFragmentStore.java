package org.opensails.sails.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.template.IFragmentStore;

public class MemoryFragmentStore implements IFragmentStore {
	protected final Map<String, String> cache = new HashMap<String, String>();

	public String read(String contextIdentifier, String name) {
		return cache.get(contextIdentifier + name);
	}

	public void write(String contextIdentifier, String name, String content) {
		cache.put(contextIdentifier + name, content);
	}

	public void delete(String contextIdentifier, String name) {
		cache.remove(contextIdentifier + name);
	}

}
