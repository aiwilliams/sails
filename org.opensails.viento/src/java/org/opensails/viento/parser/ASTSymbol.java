/* Generated By:JJTree: Do not edit this line. ASTSymbol.java */

package org.opensails.viento.parser;

public class ASTSymbol extends SimpleNode {
	protected String value;

	public ASTSymbol(int id) {
		super(id);
	}

	public ASTSymbol(Parser p, int id) {
		super(p, id);
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/** Accept the visitor. * */
	public Object jjtAccept(ParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
