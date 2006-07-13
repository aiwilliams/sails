package org.opensails.js;

import java.util.LinkedHashMap;
import java.util.Map;

public class JavascriptObject extends JavascriptGenerator {
	protected LinkedHashMap<String, Object> map;
	
	public JavascriptObject() {
		this(new LinkedHashMap<String, Object>());
	}
	
	public JavascriptObject(LinkedHashMap<String, Object> map) {
		this.map = map;
	}
	
	protected String strictlyQuoted(String key, boolean strictJson) {
		return strictJson ? possiblyQuoted(key, strictJson) : key;
	}

	public String renderThyself(boolean strictJson) {
		StringBuilder b = new StringBuilder();
		b.append("{");
		boolean f = true;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (!f || (f = false)) b.append(",");
			b.append(String.format("%s:%s", strictlyQuoted(entry.getKey(), strictJson), possiblyQuoted(entry.getValue(), strictJson)));
		}
		b.append("}");
		return b.toString();
	}

	public void set(String key, Object value) {
		map.put(key, value);
	}
}
