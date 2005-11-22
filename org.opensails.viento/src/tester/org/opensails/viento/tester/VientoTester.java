package org.opensails.viento.tester;

import junit.framework.Assert;

import org.opensails.viento.Binding;
import org.opensails.viento.ExceptionHandler;
import org.opensails.viento.VientoTemplate;

public class VientoTester {
	private Binding binding;
	
	public void verifyRender(String input, String expectedOutput) {
		VientoTemplate template = new VientoTemplate(input);
		Assert.assertEquals(expectedOutput, template.render(getBinding()));
	}
	
	protected Binding getBinding() {
		if (binding == null)
			binding = initializeBinding();
		return binding;
	}

	protected Binding initializeBinding() {
		return new Binding();
	}
	
	public void put(String key, Object object) {
		getBinding().put(key, object);
	}
	
	public void mixin(Class<?> targetClass, Object mixin) {
		getBinding().mixin(targetClass, mixin);
	}
	
	public void mixin(Object mixin) {
		getBinding().mixin(mixin);
	}
	
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		getBinding().setExceptionHandler(exceptionHandler);
	}
}
