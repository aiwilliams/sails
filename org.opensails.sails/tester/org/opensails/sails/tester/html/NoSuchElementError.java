package org.opensails.sails.tester.html;


public class NoSuchElementError extends SourceContentError {
	public NoSuchElementError(Class<? extends TestElement> elementType, String containerSource, String matchCriteria) {
		super(containerSource, String.format("Could not find element of type %s matching criteria %s in container source", elementType, matchCriteria));
	}
}
