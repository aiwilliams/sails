/* Generated By:JJTree: Do not edit this line. ASTMap.java */

package org.opensails.viento.parser;

public class ASTMap extends SimpleNode {
  public ASTMap(int id) {
    super(id);
  }

  public ASTMap(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
