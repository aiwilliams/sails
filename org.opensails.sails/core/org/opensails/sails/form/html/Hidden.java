package org.opensails.sails.form.html;

/**
 * An HTML INPUT of type HIDDEN.
 * 
 * @author aiwilliams
 */
public class Hidden extends InputElement<Hidden> {
	public static final String HIDDEN = "hidden";

	public Hidden(String name) {
		super(HIDDEN, name);
	}
}
