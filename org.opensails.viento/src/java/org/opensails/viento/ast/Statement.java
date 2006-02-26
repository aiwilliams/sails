package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.*;

public class Statement extends Node implements BodyNode, Expression {
	protected List<Call> calls = new ArrayList<Call>();
	
	public void add(Call call) {
		nodeAdded(call);
		calls.add(call);
	}

	public void evaluate(Binding binding, StringBuilder s) {
		try {
			s.append(render(evaluate(binding)));
		} catch (Throwable e) {
			if (e instanceof ResolutionFailedException)
				throw (ResolutionFailedException) e;
			s.append(binding.getExceptionHandler().exceptionInRender(e, startLine(), startColumn()));
		}
	}

	public Object evaluate(Binding binding) {
		boolean first = true;
		Object object = null;
		for (Call call : calls) {
			if (first)
				object = binding.call(call.evalIdentifier(), call.evalArgs(binding), startLine(), startColumn());
			else
				object = binding.call(object, call.evalIdentifier(), call.evalArgs(binding), startLine(), startColumn());
			first = false;
		}
		return object;
	}
	
	protected String render(Object result) {
		return result instanceof IRenderable ? ((IRenderable)result).renderThyself() : String.valueOf(result);
	}
	
	public void visit(VientoVisitor visitor) {
		visitor.visit(this);
		for (Call call : calls)
			call.visit(visitor);
	}
}
