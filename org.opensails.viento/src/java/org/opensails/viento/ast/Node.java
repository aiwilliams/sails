package org.opensails.viento.ast;

import org.opensails.viento.parser.Token;

public class Node implements INode {
	protected Token first;
	protected Token last;
	
	public Token first() {
		return first;
	}
	
	public Token last() {
		return last;
	}
	
	public int endColumn() {
		return last.endColumn;
	}
	
	public int endLine() {
		return last.endLine;
	}
	
	protected void nodeAdded(INode node) {
		if (first == null) first = node.first();
		last = node.last();
	}
	
	public int startColumn() {
		return first.beginColumn;
	}
	
	public int startLine() {
		return first.beginLine;
	}
	
	public void token(Token t) {
		if (first == null) first = t;
		last = t;
	}
}
