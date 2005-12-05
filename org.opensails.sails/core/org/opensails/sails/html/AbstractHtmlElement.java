package org.opensails.sails.html;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.form.html.FormElement;

public abstract class AbstractHtmlElement<T extends AbstractHtmlElement> implements IHtmlElement<T> {
	public static String idForName(String name) {
		return name.replace('.', '_');
	}
	
	public static String idForNameAndValue(String name, String value) {
		return idForName(name) + (StringUtils.isBlank(name) || StringUtils.isBlank(value) ? "" : "-") + value;
	}

	protected Map<String, String> attributes;
	protected String elementName;

	protected String id;

	public AbstractHtmlElement(String elementName) {
		this.elementName = elementName;
	}
	
	/**
	 * @param attributes
	 *            a Map containing extra element attributes to be rendered.
	 * @see FormElement#writeAttributes(Writer)
	 */
	@SuppressWarnings("unchecked")
	public T attributes(Map attributes) {
		this.attributes = attributes;
		return (T) this;
	}
	
	public String getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public T id(String id) {
		this.id = id;
		return (T) this;
	}

	@Override
    public String toString() {
		StringWriter writer = new StringWriter(75);
		try {
			toString(writer);
		} catch (IOException e) {
			onToStringError(e);
		}
		return writer.toString();
	}

	public void toString(HtmlGenerator generator) throws IOException {
		render(generator);
	}
	
	public void toString(Writer writer) throws IOException {
		render(writer);
	}

	/**
	 * Override to write the body of this element. If you want this called, you
	 * must cause {@link #hasBody()} to return true.
	 * 
	 * @param generator
	 * @throws IOException
	 */
	protected void body(HtmlGenerator generator) throws IOException {
	}

	/**
	 * @return whether or not this element can have a body. Many elements do
	 *         not, so the default is false.
	 */
	protected boolean hasBody() {
		return false;
	}

	/**
	 * Override to customize handling of failure writing this element.
	 * 
	 * @param e
	 */
	protected void onToStringError(IOException e) {
		throw new SailsException("An exception occurred writing the element <" + elementName + "> of type <" + getClass() + ">", e);
	}

	/**
	 * Actual rendering of element is done here. This allows for subclasses to
	 * define custom algorithms in {@link #toString(Writer)}.
	 * 
	 * @param writer
	 * @throws IOException
	 */
	protected void render(HtmlGenerator generator) throws IOException {
		generator.openTag(elementName, getId());
		writeAttributes(generator);
		if (hasBody()) {
			generator.closeTag();
			body(generator);
			generator.endTag(elementName);
		} else
			generator.closeTag(true);
	}

	protected void render(Writer writer) throws IOException {
		render(new HtmlGenerator(writer));
	}
	
	/**
	 * Override to add attributes to the element when it is written. The default
	 * implementation writes the attributes Map, so be sure to call this if you
	 * want that.
	 * 
	 * @param writer
	 * @throws IOException
	 */
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attributes(attributes);
	}
}
