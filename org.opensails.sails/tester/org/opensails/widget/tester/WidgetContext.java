package org.opensails.widget.tester;

import org.opensails.sails.html.IHtmlElement;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.SpyGlassy;
import org.opensails.spyglass.resolvers.CompositeClassResolver;
import org.opensails.viento.MethodMissing;

public class WidgetContext implements MethodMissing {
	protected CompositeClassResolver<IHtmlElement> resolver = new CompositeClassResolver<IHtmlElement>();

	public Object methodMissing(String methodName, Object[] args) {
		return SpyGlassy.instantiate(resolver.resolve(methodName), args);
	}

	public void push(IClassResolver<IHtmlElement> resolver) {
		this.resolver.push(resolver);
	}
}
