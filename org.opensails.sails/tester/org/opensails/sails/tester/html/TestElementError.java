package org.opensails.sails.tester.html;


public class TestElementError extends SourceContentError {
	protected Class<? extends TestElement> elementType;

	public TestElementError(Class<? extends TestElement> elementType, String containerSource, String message) {
		super(containerSource, message);
		this.elementType = elementType;
	}
}
