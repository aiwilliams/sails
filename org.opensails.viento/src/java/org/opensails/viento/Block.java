package org.opensails.viento;

import org.opensails.viento.parser.ASTBody;

public class Block {
	protected Binding binding;
	protected ASTBody body;

	public Block(Binding binding, ASTBody body) {
		this.binding = new Binding(binding);
		this.body = body;
	}

	public String evaluate() {
		StringBuilder buffer = new StringBuilder();
		body.childrenAccept(new VientoVisitor(buffer, binding), null);
		return buffer.toString();
	}

	public Binding getBinding() {
		return binding;
	}
	
	public void put(String key, Object object) {
		binding.put(key, object);
	}
	
	@Override
	public String toString() {
		return evaluate();
	}
}
