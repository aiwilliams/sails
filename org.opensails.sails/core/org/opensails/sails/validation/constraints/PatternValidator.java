package org.opensails.sails.validation.constraints;

import org.opensails.sails.SailsException;
import org.opensails.sails.validation.IValidator;

public class PatternValidator implements IValidator<Pattern> {
	protected java.util.regex.Pattern pattern;
	private Pattern constraint;

	public String getConstraintMessage() {
		if (!constraint.message().equals(Pattern.DEFAULT_MESSAGE)) return constraint.message();
		return String.format("must match the pattern %s", pattern);
	}

	public void init(Pattern constraint) {
		this.constraint = constraint;
		pattern = java.util.regex.Pattern.compile(constraint.regex(), constraint.flags());
	}

	public boolean validate(Object value) throws Exception {
		if (value == null) return true;
		if (!(value instanceof CharSequence)) throw new SailsException("Patterns can only be applied to CharSequence");
		String charSeq = (String) value;
		return pattern.matcher(charSeq).matches();
	}

}
