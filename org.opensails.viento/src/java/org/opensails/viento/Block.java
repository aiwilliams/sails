package org.opensails.viento;


public class Block {
	protected Binding binding;
	protected org.opensails.viento.ast.Block body;

	public Block(Binding binding, org.opensails.viento.ast.Block body) {
		this.binding = new Binding(binding);
		this.body = body;
	}

	public String evaluate() {
		StringBuilder s = new StringBuilder();
		body.evaluate(binding, s);
		return s.toString();
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
