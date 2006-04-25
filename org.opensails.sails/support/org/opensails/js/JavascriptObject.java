package org.opensails.js;

import java.util.LinkedHashMap;
import java.util.Map;

import org.opensails.sails.util.Quick;

public class JavascriptObject extends JavascriptGenerator {
	protected LinkedHashMap<String, Object> map;
	
	@SuppressWarnings("unchecked")
	public static JavascriptObject quick(Object...objects) {
		return new JavascriptObject((LinkedHashMap) Quick.linkedMap(objects));
	}
	
	public JavascriptObject() {
		this(new LinkedHashMap<String, Object>());
	}
	
	public JavascriptObject(LinkedHashMap<String, Object> map) {
		this.map = map;
	}

	public String renderThyself() {
		StringBuilder b = new StringBuilder();
		b.append("{");
		boolean f = true;
		for (Map.Entry entry : map.entrySet()) {
			if (!f || (f = false)) b.append(",");
			b.append(String.format("%s:%s", entry.getKey(), possiblyQuoted(entry.getValue())));
		}
		b.append("}");
		return b.toString();
	}

	public void set(String key, Object value) {
		map.put(key, value);
	}
}
