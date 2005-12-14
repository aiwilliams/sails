package org.opensails.sails.controller.oem;

import org.opensails.sails.SailsException;

public class MissingComponentDependenciesException extends SailsException {
	public MissingComponentDependenciesException(String componentName) {
		super(String.format("Could not find the component dependencies file for component [%s]", componentName));
	}
}
