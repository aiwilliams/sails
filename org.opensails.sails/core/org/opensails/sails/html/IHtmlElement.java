package org.opensails.sails.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * IFormElements can write themselves to a Writer.
 */
public interface IHtmlElement<T extends IHtmlElement> {
	T attributes(Map attributes);
    void toString(Writer writer) throws IOException;
}
