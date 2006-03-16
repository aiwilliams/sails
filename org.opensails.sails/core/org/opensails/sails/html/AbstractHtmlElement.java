package org.opensails.sails.html;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.form.html.FormElement;

public abstract class AbstractHtmlElement<T extends AbstractHtmlElement> implements IHtmlElement<T> {
	public static String idForName(String name) {
		return name.replace('.', '_');
	}

	public static String idForNameAndValue(String name, String value) {
		return idForName(name) + (StringUtils.isBlank(name) || StringUtils.isBlank(value) ? "" : "-") + (value == null ? "" : value.replaceAll("[\\s\\.,]+", "_"));
	}

	protected Map<String, String> attributes;
	protected String elementName;
	protected String id;

	public AbstractHtmlElement(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @param attributes a Map defining additional attributes to be rendered.
	 * @see FormElement#writeAttributes(Writer)
	 * @see #attributes
	 */
	@SuppressWarnings("unchecked")
	public T addAttributes(Map<String, String> attributes) {
		attributes().putAll(attributes);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T attribute(String name, String value) {
		attributes().put(name, value);
		return (T) this;
	}

	/**
	 * @param attributes a Map defining the element attributes to be rendered.
	 * @see FormElement#writeAttributes(Writer)
	 * @see #addAttributes
	 */
	@SuppressWarnings("unchecked")
	public T attributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return (T) this;
	}

	@Override
	public boolean equals(Object obj) {
		return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		StringBuilder code = new StringBuilder();
		code.append(getClass().getName());
		code.append(this.elementName);
		code.append(this.id);
		if (attributes != null && !attributes.isEmpty()) for (Map.Entry<String, String> attribute : attributes.entrySet()) {
			code.append(attribute.getKey());
			code.append(attribute.getValue());
		}
		return code.toString().intern().hashCode();
	}

	@SuppressWarnings("unchecked")
	public T id(String id) {
		this.id = id;
		return (T) this;
	}

	public String renderThyself() {
		StringWriter writer = new StringWriter(75);
		try {
			renderThyself(writer);
		} catch (IOException e) {
			onToStringError(e);
		}
		return writer.toString();
	}

	public void renderThyself(Writer writer) throws IOException {
		render(writer);
	}

	/**
	 * @deprecated Use {@link #renderThyself(Writer)} instead
	 */
	public void toString(Writer writer) throws IOException {
		renderThyself(writer);
	}

	protected Map<String, String> attributes() {
		if (attributes == null) attributes = new HashMap<String, String>();
		return attributes;
	}

	/**
	 * Override to write the body of this element. If you want this called, you
	 * must cause {@link #hasBody()} to return true.
	 * 
	 * @param generator
	 * @throws IOException
	 */
	protected void body(HtmlGenerator generator) throws IOException {}

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
	 * define custom algorithms in {@link #renderThyself(Writer)}.
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
		} else generator.closeTag(true);
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
