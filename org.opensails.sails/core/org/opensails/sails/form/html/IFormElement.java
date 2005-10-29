/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import org.opensails.sails.html.IHtmlElement;

/**
 * IHtmlElements that have a name attribute.
 * 
 * @author Adam Williams
 * 
 * @param <T>
 *            the type the element
 */
public interface IFormElement<T extends IFormElement> extends IHtmlElement<T> {
	/**
	 * @return the value for the name attribute
	 */
	String getName();
}