/* Generated By:JJTree: Do not edit this line. ASTLeftHandExpression.java */

package org.opensails.viento.parser;

public class ASTLeftHandExpression extends SimpleNode {
  public ASTLeftHandExpression(int id) {
    super(id);
  }

  public ASTLeftHandExpression(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}