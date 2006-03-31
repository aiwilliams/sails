package org.opensails.sails.tester.html;

import org.opensails.spyglass.SpyGlass;

public class NoSuchElementError extends SourceContentError {
	private static final long serialVersionUID = 8067144709396455128L;

	public NoSuchElementError(Class<? extends TestElement> elementType, String containerSource, String matchCriteria) {
		super(containerSource, String.format("Could not find element %s of type %s in container source", matchCriteria, SpyGlass.getName(elementType)));
	}
}
