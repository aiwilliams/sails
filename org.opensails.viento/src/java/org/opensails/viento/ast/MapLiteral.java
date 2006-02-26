package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;

public class MapLiteral extends Node implements Expression {
	protected List<MapEntry> entries = new ArrayList<MapEntry>();
	
	public void add(MapEntry entry) {
		nodeAdded(entry);
		entries.add(entry);
	}

	public Object evaluate(Binding binding) {
		Map<Object, Object> map = new HashMap<Object, Object>(entries.size());
		for (MapEntry entry : entries)
			map.put(entry.evalKey(binding), entry.evalValue(binding));
		return map;
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (MapEntry entry : entries)
			entry.visit(visitor);
	}
}
