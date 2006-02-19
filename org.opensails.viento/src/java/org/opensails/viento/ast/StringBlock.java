package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public class StringBlock extends Block implements Expression {
	@Override
	public Object evaluate(Binding binding) {
		return AstUtils.unescapeString(((org.opensails.viento.Block) super.evaluate(binding)).evaluate());
	}
}
