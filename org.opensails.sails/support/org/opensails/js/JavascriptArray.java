package org.opensails.js;

import java.util.*;

import org.opensails.sails.util.Quick;

public class JavascriptArray extends JavascriptGenerator {
	protected List<Object> list;
	
	public static JavascriptArray quick(Object...objects) {
		return new JavascriptArray(Quick.list(objects));
	}
	
	public JavascriptArray() {
		this(new ArrayList<Object>());
	}
	
	public JavascriptArray(List<Object> list) {
		this.list = list;
	}

	public void add(Object object) {
		list.add(object);
	}

	public String renderThyself() {
		StringBuilder b = new StringBuilder();
		b.append("[");
		boolean f = true;
		for (Object each : list) {
			if (!f || (f = false)) b.append(",");
			b.append(possiblyQuoted(each));
		}
		b.append("]");
		return b.toString();
	}
}
