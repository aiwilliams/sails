package org.opensails.sails.tester.html;

import org.opensails.spyglass.SpyGlass;

/**
 * Thrown by TestElements when they find multiple occurrences of themselves with
 * the same name and no specific value.
 * 
 * @author aiwilliams
 */
public class MultipleElementOccurrencesException extends SourceContentError {
	private static final long serialVersionUID = -5904906313241748549L;

	public MultipleElementOccurrencesException(Class<? extends TestElement> elementType, String containerSource, String name) {
		super(containerSource, String.format("Found multiple elements named %s of type %s in container source", name, SpyGlass.getName(elementType)));
	}
}