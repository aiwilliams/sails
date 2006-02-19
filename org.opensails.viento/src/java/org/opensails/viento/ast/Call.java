package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.Binding;

public class Call extends Node {
	protected Identifier identifier;
	protected ArgumentList args;
	protected Block block;

	public void identifier(Identifier identifier) {
		this.identifier = identifier;
		nodeAdded(identifier);
	}
	
	public void args(ArgumentList args) {
		this.args = args;
		nodeAdded(args);
	}
	
	public void block(Block block) {
		this.block = block;
		nodeAdded(block);
	}

	public String evalIdentifier() {
		return identifier.evaluate();
	}

	public Object[] evalArgs(Binding binding) {
		List<Object> evaledArgs = new ArrayList<Object>();
		if (args != null)
			evaledArgs.addAll(args.evaluate(binding));
		if (block != null)
			evaledArgs.add(block.evaluate(binding));
		return evaledArgs.toArray();
	}
}
