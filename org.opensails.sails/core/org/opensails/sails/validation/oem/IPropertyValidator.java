package org.opensails.sails.validation.oem;

import org.opensails.sails.validation.IInvalidProperty;

public interface IPropertyValidator {
	IInvalidProperty validate(Object model) throws Exception;
}
