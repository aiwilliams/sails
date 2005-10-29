/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import org.opensails.sails.form.HtmlForm;

/**
 * An HTML INPUT of type SUBMIT.
 * 
 * Note that there is no transform for SubmitLink, as that requires the form id.
 */
public class Submit extends InputElement<Submit> {
	// TODO: Need to support the action submit stuff
	public static final String ACTION_PREFIX = HtmlForm.META_PREFIX + "action.";
	public static final String SUBMIT = "submit";

	/**
	 * @param name
	 */
	public Submit(String name) {
		super(Submit.SUBMIT, name);
	}

	/**
	 * 'Transforms' this into a SubmitImage. This provides for what one would
	 * expect - $form.submit('Whatever').image('srclocation').
	 * 
	 * @param src
	 * @return a SubmitImage derived from this
	 */
	public SubmitImage image(String src) {
		return new SubmitImage(getName(), src);
	}
}
