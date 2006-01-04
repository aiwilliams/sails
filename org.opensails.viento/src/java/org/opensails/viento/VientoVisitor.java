package org.opensails.viento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.viento.parser.ASTAnd;
import org.opensails.viento.parser.ASTArguments;
import org.opensails.viento.parser.ASTBlock;
import org.opensails.viento.parser.ASTBody;
import org.opensails.viento.parser.ASTBoolean;
import org.opensails.viento.parser.ASTEqual;
import org.opensails.viento.parser.ASTExpression;
import org.opensails.viento.parser.ASTGreaterThan;
import org.opensails.viento.parser.ASTGreaterThanOrEqual;
import org.opensails.viento.parser.ASTLeftHandExpression;
import org.opensails.viento.parser.ASTLessThan;
import org.opensails.viento.parser.ASTLessThanOrEqual;
import org.opensails.viento.parser.ASTList;
import org.opensails.viento.parser.ASTMap;
import org.opensails.viento.parser.ASTName;
import org.opensails.viento.parser.ASTNot;
import org.opensails.viento.parser.ASTNotEqual;
import org.opensails.viento.parser.ASTNull;
import org.opensails.viento.parser.ASTNumber;
import org.opensails.viento.parser.ASTOr;
import org.opensails.viento.parser.ASTStatement;
import org.opensails.viento.parser.ASTString;
import org.opensails.viento.parser.ASTStringBlock;
import org.opensails.viento.parser.ASTSymbol;
import org.opensails.viento.parser.ASTText;
import org.opensails.viento.parser.Node;

public class VientoVisitor extends AbstractParserVisitor {

	protected StringBuilder buffer;
	protected Binding binding;

	public VientoVisitor(StringBuilder buffer, Binding binding) {
		this.buffer = buffer;
		this.binding = binding;
	}

	@Override
	public void visit(ASTText node) {
		buffer.append(unescape(node.getContents()));
	}

	@Override
	public void visit(ASTStatement node) {
		buffer.append(evaluate(node));
	}

	protected Object evaluate(ASTStatement node) {
		Node call = node.jjtGetChild(0);
		String name = ((ASTName) call.jjtGetChild(0)).getText();
		
		if (name.startsWith("!") && name.length() > 1)
			return tryStatement(node, call, name.substring(1), true);
		return tryStatement(node, call, name, false);
	}

	protected Object tryStatement(ASTStatement node, Node call, String name, boolean isSilent) {
		Object object = binding.call(name, getArguments(call), isSilent);
		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			call = node.jjtGetChild(i);
			name = ((ASTName) call.jjtGetChild(0)).getText();
			object = binding.call(object, name, getArguments(call), isSilent);
		}
		return object;
	}

	protected Object[] getArguments(Node call) {
		if (call.jjtGetNumChildren() == 1)
			return new Object[0];
		if (call.jjtGetNumChildren() == 2)
			if (call.jjtGetChild(1) instanceof ASTBlock)
				return new Object[] {evaluateBlock((ASTBody)call.jjtGetChild(1).jjtGetChild(0))};
			else if (call.jjtGetChild(1) instanceof ASTArguments)
				return evaluate((ASTArguments)call.jjtGetChild(1));
		
		Object[] all = new Object[call.jjtGetChild(1).jjtGetNumChildren() + 1];
		Object[] args = evaluate((ASTArguments)call.jjtGetChild(1));
		System.arraycopy(args, 0, all, 0, args.length);
		all[all.length - 1] = evaluateBlock((ASTBody)call.jjtGetChild(2).jjtGetChild(0));
		return all;
	}

	protected Object[] evaluate(ASTArguments arguments) {
		Object[] args = new Object[arguments.jjtGetNumChildren()];
		for (int j = 0; j < args.length; j++)
			args[j] = evaluate((ASTExpression) arguments.jjtGetChild(j));
		return args;
	}

	protected Block evaluateBlock(ASTBody body) {
		return new Block(binding, body);
	}

	@SuppressWarnings("unchecked")
	protected Object evaluate(ASTExpression expression) {
		Node node = expression.jjtGetChild(0);
		if (node instanceof ASTLeftHandExpression)
			return evaluate((ASTLeftHandExpression) node);
		if (node instanceof ASTAnd)
			return nullOrFalse(evaluate((ASTLeftHandExpression)node.jjtGetChild(0))) && nullOrFalse(evaluate((ASTExpression)node.jjtGetChild(1)));
		if (node instanceof ASTOr)
			return nullOrFalse(evaluate((ASTLeftHandExpression)node.jjtGetChild(0))) || nullOrFalse(evaluate((ASTExpression)node.jjtGetChild(1)));
		if (node instanceof ASTEqual)
			return safeEquals(evaluate((ASTLeftHandExpression)node.jjtGetChild(0)), evaluate((ASTExpression)node.jjtGetChild(1)));
		if (node instanceof ASTNotEqual)
			return !safeEquals(evaluate((ASTLeftHandExpression)node.jjtGetChild(0)), evaluate((ASTExpression)node.jjtGetChild(1)));
		if (node instanceof ASTGreaterThan)
			return ((Comparable)evaluate((ASTLeftHandExpression)node.jjtGetChild(0))).compareTo(evaluate((ASTExpression)node.jjtGetChild(1))) > 0;
		if (node instanceof ASTGreaterThanOrEqual)
			return ((Comparable)evaluate((ASTLeftHandExpression)node.jjtGetChild(0))).compareTo(evaluate((ASTExpression)node.jjtGetChild(1))) >= 0;
		if (node instanceof ASTLessThan)
			return ((Comparable)evaluate((ASTLeftHandExpression)node.jjtGetChild(0))).compareTo(evaluate((ASTExpression)node.jjtGetChild(1))) < 0;
		if (node instanceof ASTLessThanOrEqual)
			return ((Comparable)evaluate((ASTLeftHandExpression)node.jjtGetChild(0))).compareTo(evaluate((ASTExpression)node.jjtGetChild(1))) <= 0;
		return null;
	}

	protected String unescapeString(String value) {
		return value.replace("\\'", "'").replace("\\\\", "\\").replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\\"", "\"").replace("\\$", "$");
	}
	
	protected boolean safeEquals(Object one, Object two) {
		return one == null ? two == null : one.equals(two);
	}
	
	protected String unescape(String value) {
		return value.replaceAll("\\\\([$\\\\])", "$1");
	}
	
	protected Object evaluate(ASTLeftHandExpression expression) {
		Node node = expression.jjtGetChild(0);
		if (node instanceof ASTStatement)
			return evaluate((ASTStatement) node);
		if (node instanceof ASTNumber)
			return ((ASTNumber) node).getValue();
		if (node instanceof ASTBoolean)
			return ((ASTBoolean) node).getValue();
		if (node instanceof ASTString)
			return unescapeString(((ASTString) node).getValue());
		if (node instanceof ASTList)
			return evaluate((ASTList) node);
		if (node instanceof ASTMap)
			return evaluate((ASTMap) node);
		if (node instanceof ASTNull)
			return null;
		if (node instanceof ASTSymbol)
			return ((ASTSymbol)node).getValue();
		if (node instanceof ASTNot)
			return !nullOrFalse(evaluate((ASTStatement)node.jjtGetChild(0)));
		if (node instanceof ASTStringBlock)
			return unescapeString(evaluateBlock((ASTBody) node.jjtGetChild(0)).evaluate());
		return null;
	}

	protected boolean nullOrFalse(Object object) {
		return object != null && object != Boolean.FALSE;
	}

	protected Object evaluate(ASTList node) {
		List<Object> list = new ArrayList<Object>(node.jjtGetNumChildren());
		for (int j = 0; j < node.jjtGetNumChildren(); j++)
			list.add(evaluate((ASTExpression) node.jjtGetChild(j)));
		return list;
	}

	protected Object evaluate(ASTMap node) {
		Map<Object, Object> map = new HashMap<Object, Object>(node.jjtGetNumChildren());
		for (int j = 0; j < node.jjtGetNumChildren(); j++) {
			Node entry = node.jjtGetChild(j);
			map.put(evaluate((ASTExpression) entry.jjtGetChild(0)), evaluate((ASTExpression) entry.jjtGetChild(1)));
		}
		return map;
	}
}
