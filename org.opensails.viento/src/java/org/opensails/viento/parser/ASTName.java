/* Generated By:JJTree: Do not edit this line. ASTName.java */

package org.opensails.viento.parser;

public class ASTName extends SimpleNode {
	protected String text;
	
	public ASTName(int id) {
		super(id);
	}

	public ASTName(Parser p, int id) {
		super(p, id);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	/** Accept the visitor. * */
	public Object jjtAccept(ParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
