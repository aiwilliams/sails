/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import org.opensails.sails.adapter.ContainerAdapterResolver;

/**
 * An html input of type submit.
 * 
 * Note that there is no transform for SubmitLink, as that requires the form id.
 */
public class Submit extends AbstractSubmit<Submit> {
	public static final String SUBMIT = "submit";

	public Submit(String name, ContainerAdapterResolver adapterResolver) {
		super(Submit.SUBMIT, name, adapterResolver);
	}

	/**
	 * 'Transforms' this into a SubmitImage. This provides for what one would
	 * expect - $form.submit('Whatever').image('srclocation').
	 * 
	 * @param src
	 * @return a SubmitImage derived from this
	 */
	public SubmitImage image(String src) {
		return new SubmitImage(name, src, adapterResolver).value(value).action(action, parameters);
	}
}
