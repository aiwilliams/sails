package org.opensails.viento.ast;

import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.Binding;
import org.opensails.viento.IRenderable;

public class Statement extends BodyNode implements Expression {
	protected List<Call> calls = new ArrayList<Call>();
	
	public void add(Call call) {
		nodeAdded(call);
		calls.add(call);
	}

	@Override
	public void evaluate(Binding binding, StringBuilder s) {
		s.append(render(evaluate(binding)));
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
}
