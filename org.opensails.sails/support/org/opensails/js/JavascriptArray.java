package org.opensails.js;

import java.util.ArrayList;
import java.util.List;

public class JavascriptArray extends JavascriptGenerator {
	protected List<Object> list;
	
	public JavascriptArray() {
		this(new ArrayList<Object>());
	}
	
	public JavascriptArray(List<Object> list) {
		this.list = list;
	}

	public void add(Object object) {
		list.add(object);
	}

	public String renderThyself(boolean strictJson) {
		StringBuilder b = new StringBuilder();
		b.append("[");
		boolean f = true;
		for (Object each : list) {
			if (!f || (f = false)) b.append(",");
			b.append(possiblyQuoted(each, strictJson));
		}
		b.append("]");
		return b.toString();
	}
}
