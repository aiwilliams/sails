package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;

public class Block extends Body {
	public Object evaluate(Binding binding) {
		return new org.opensails.viento.Block(binding, this);
	}

	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (BodyNode node : nodes)
			node.visit(visitor);
	}
}
