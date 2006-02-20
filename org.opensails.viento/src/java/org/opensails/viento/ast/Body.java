package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.Binding;

public class Body extends Node {
	public List<BodyNode> nodes = new ArrayList<BodyNode>();
	
	public void add(BodyNode node) {
		nodeAdded(node);
		nodes.add(node);
	}
	
	public void evaluate(Binding binding, StringBuilder s) {
		for (BodyNode node : nodes)
			node.evaluate(binding, s);
	}
}
