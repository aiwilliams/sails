package org.opensails.viento.ast;

import org.opensails.viento.VientoVisitor;

public class Template extends Body {
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (BodyNode node : nodes)
			node.visit(visitor);
	}
}
