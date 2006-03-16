package org.opensails.sails.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.opensails.viento.IRenderable;

/**
 * IFormElements can write themselves to a Writer.
 */
public interface IHtmlElement<T extends IHtmlElement> extends IRenderable {
	T addAttributes(Map<String, String> attributes);

	T attributes(Map<String, String> attributes);

	/**
	 * @deprecated Use {@link #renderThyself(Writer)} instead
	 */
	void toString(Writer writer) throws IOException;

	void renderThyself(Writer writer) throws IOException;
}
