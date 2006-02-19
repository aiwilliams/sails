package org.opensails.viento.ast;

import org.opensails.viento.parser.Token;

public interface INode {
	Token first();
	Token last();
}
