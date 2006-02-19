package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public interface BodyNode extends INode {
	void evaluate(Binding binding, StringBuilder s);
}
