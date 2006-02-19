package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public class Block extends Body {
	public Object evaluate(Binding binding) {
		return new org.opensails.viento.Block(binding, this);
	}
}
