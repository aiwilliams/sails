package org.opensails.viento.ast;

import org.opensails.viento.VientoVisitor;
import org.opensails.viento.parser.Token;

public interface INode {
	Token first();
	Token last();
	int endColumn();
	int endLine();
	int endOffset();
	int startColumn();
	int startLine();
	int startOffset();
	int length();
	void visit(VientoVisitor visitor);
}
