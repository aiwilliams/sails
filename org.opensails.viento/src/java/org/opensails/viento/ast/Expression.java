package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public interface Expression extends INode {
	public abstract Object evaluate(Binding binding);
}
