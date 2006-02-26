package org.opensails.viento;

import org.opensails.viento.ast.ArgumentList;
import org.opensails.viento.ast.Block;
import org.opensails.viento.ast.BooleanLiteral;
import org.opensails.viento.ast.Call;
import org.opensails.viento.ast.Identifier;
import org.opensails.viento.ast.InfixExpression;
import org.opensails.viento.ast.ListLiteral;
import org.opensails.viento.ast.MapEntry;
import org.opensails.viento.ast.MapLiteral;
import org.opensails.viento.ast.NegatedExpression;
import org.opensails.viento.ast.NullLiteral;
import org.opensails.viento.ast.NumberLiteral;
import org.opensails.viento.ast.Statement;
import org.opensails.viento.ast.StringBlock;
import org.opensails.viento.ast.StringLiteral;
import org.opensails.viento.ast.Template;
import org.opensails.viento.ast.Text;

public class VientoVisitor {
	public void visit(NullLiteral literal) {}
	public void visit(NumberLiteral literal) {}
	public void visit(Text text) {}
	public void visit(StringLiteral literal) {}
	public void visit(Statement statement) {}
	public void visit(NegatedExpression expression) {}
	public void visit(MapLiteral literal) {}
	public void visit(BooleanLiteral literal) {}
	public void visit(Call call) {}
	public void visit(Identifier identifier) {}
	public void visit(InfixExpression expression) {}
	public void visit(ListLiteral literal) {}
	public void visit(MapEntry entry) {}
	public void visit(Template template) {}
	public void visit(Block block) {}
	public void visit(StringBlock stringBlock) {}
	public void visit(ArgumentList list) {}
}
