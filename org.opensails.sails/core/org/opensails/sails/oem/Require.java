package org.opensails.sails.oem;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opensails.sails.html.Script;

public class Require {
	protected final Set<Script> scripts = new LinkedHashSet<Script>();

	public String output() {
		StringBuilder builder = new StringBuilder();
		for (Iterator iter = scripts.iterator(); iter.hasNext();) {
			Script script = (Script) iter.next();
			builder.append(script);
			if (iter.hasNext()) builder.append("\n");
		}
		return builder.toString();
	}

	public void script(Script script) {
		scripts.add(script);
	}
}
