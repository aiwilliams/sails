package org.opensails.widget.tester;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.opensails.viento.Binding;
import org.opensails.viento.VientoTemplate;

public class WidgetTester {
	protected Binding vientoBinding;

	public WidgetTester() {
		this(new SailsWidgetContext());
	}

	public WidgetTester(WidgetContext widgetContext) {
		vientoBinding = new Binding();
		vientoBinding.mixin(widgetContext);
	}

	/**
	 * Based on the actualUsage of a widget, assert that what is rendered looks
	 * like the expectedRender.
	 * 
	 * @param expectedRender
	 * @param actualUsage
	 * @throws AssertionFailedError
	 */
	public void assertRender(String expectedRender, String actualUsage) {
		VientoTemplate template = new VientoTemplate(actualUsage);
		Assert.assertEquals(expectedRender, template.render(vientoBinding));
	}

	/**
	 * Makes value available as name for use in your actualUsage templates.
	 * These stick around.
	 * 
	 * @param name
	 * @param value
	 */
	public void expose(String name, Object value) {
		vientoBinding.put(name, value);
	}
}
