package org.opensails.viento.ast;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoVisitor;

public class StringBlock extends Block implements Expression {
	@Override
	public Object evaluate(Binding binding) {
		return AstUtils.unescapeString(((org.opensails.viento.Block) super.evaluate(binding)).evaluate());
	}
	
	@Override public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (BodyNode node : nodes)
			node.visit(visitor);
	}
}
