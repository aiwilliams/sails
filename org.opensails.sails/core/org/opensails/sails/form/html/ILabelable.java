/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

/**
 * Html elements that can have html labels applied to them.
 */
public interface ILabelable<T extends ILabelable> extends IFormElement<T> {
	/**
	 * @return the id of the ILabelable, used in the for attribute of the Label
	 */
	String getId();

	/**
	 * @param text
	 * @return this ILabelable, which will now render itself and a Label
	 */
	T label(String text);
}
