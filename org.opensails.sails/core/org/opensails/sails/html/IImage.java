package org.opensails.sails.html;

public interface IImage<T extends IImage> extends IHtmlElement<T> {
	/**
	 * Set the text for the alt attribute.
	 * 
	 * @param alt
	 * @return this
	 */
	T alt(String alt);

	/**
	 * Set the value for the src attribute.
	 * 
	 * @param src
	 * @return this
	 */
	T src(String src);
}
