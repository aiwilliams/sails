package org.opensails.sails.form;

import org.opensails.sails.validation.constraints.Length;

public class ShamFormModel {
    @Length(min=4) protected String stringProperty;
}
