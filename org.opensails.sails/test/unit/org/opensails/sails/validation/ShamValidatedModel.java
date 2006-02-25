package org.opensails.sails.validation;

import org.opensails.sails.validation.constraints.AssertTrue;
import org.opensails.sails.validation.constraints.Length;
import org.opensails.sails.validation.constraints.NotNull;

public class ShamValidatedModel {
	@Length(min = 5)
	protected String lengthFieldProperty;

	@NotNull
	protected String nullFieldProperty;

	@AssertTrue
	protected boolean trueFieldProperty;
}
