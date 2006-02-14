package org.opensails.sails.tester.oem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.url.IUrl;

public class VirtualResourceResolver implements IResourceResolver {
	protected Map<String, String> contentMap = new HashMap<String, String>();

	public boolean exists(IUrl applicationUrl) {
		return false;
	}

	public boolean exists(String identifier) {
		return contentMap.containsKey(identifier);
	}

	public void register(String identifier, String content) {
		contentMap.put(identifier, content);
	}

	public InputStream resolve(IUrl applicationUrl) {
		return null;
	}

	public InputStream resolve(String identifier) {
		return new ByteArrayInputStream(contentMap.get(identifier).getBytes());
	}
}
