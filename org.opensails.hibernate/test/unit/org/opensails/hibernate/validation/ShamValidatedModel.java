package org.opensails.hibernate.validation;

import org.hibernate.validator.Length;

public class ShamValidatedModel {
	@Length(min=5) protected String lengthField;
}
