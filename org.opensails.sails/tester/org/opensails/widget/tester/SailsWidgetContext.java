package org.opensails.widget.tester;

import org.opensails.sails.html.IHtmlElement;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class SailsWidgetContext extends WidgetContext {
	public SailsWidgetContext() {
		push(new PackageClassResolver<IHtmlElement>(IHtmlElement.class));
	}
}
