package org.opensails.viento.ast;

import org.opensails.viento.Binding;

public abstract class BodyNode extends Node {
	public abstract void evaluate(Binding binding, StringBuilder s);
}
