package org.opensails.sails.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.opensails.sails.url.IUrl;

/**
 * An HtmlGenerator maintains no state. Therefore, it is safe to pass this
 * around between elements. This is useful when one element uses another and
 * performs its render by calling on that other element.
 * 
 * @author Adam Williams
 */
public class HtmlGenerator {
	protected final Writer writer;

	public HtmlGenerator(Writer writer) {
		this.writer = writer;
	}

	public HtmlGenerator attribute(String name, boolean value) throws IOException {
		return attribute(name, String.valueOf(value));
	}

	public HtmlGenerator attribute(String name, IUrl url) throws IOException {
		return attribute(name, String.valueOf(url));
	}

	public HtmlGenerator attribute(String name, String value) throws IOException {
		writer.write(" ");
		writer.write(name);
		writer.write("=\"");
		writer.write(value);
		writer.write("\"");
		return this;
	}

	public HtmlGenerator attributes(Map attributes) throws IOException {
		if (attributes != null) {
			for (Iterator iter = attributes.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object value = entry.getValue();
				if (value == null) continue;
				attribute(entry.getKey().toString(), value.toString());
			}
		}
		return this;
	}

	public HtmlGenerator closeTag() throws IOException {
		return closeTag(false);
	}

	/**
	 * Write a closing tag.
	 * 
	 * @param closeElementToo
	 * @return this
	 * @throws IOException
	 */
	public HtmlGenerator closeTag(boolean closeElementToo) throws IOException {
		if (closeElementToo) writer.write(" /");
		writer.write(">");
		return this;
	}

	public HtmlGenerator endTag(String tagName) throws IOException {
		writer.write("</");
		writer.write(tagName);
		writer.write(">");
		return this;
	}

	/**
	 * @return the underlying Writer
	 */
	public Writer getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param writer
	 * @param id If null, it will not be written. If you want an empty id, pass
	 *        an empty String.
	 * @throws IOException
	 */
	public HtmlGenerator idAttribute(String id) throws IOException {
		if (id != null) attribute(HtmlConstants.ID_ATTRIBUTE, id);
		return this;
	}

	/**
	 * @param writer
	 * @param nameValue
	 * @return
	 * @throws IOException
	 */
	public HtmlGenerator nameAttribute(String nameValue) throws IOException {
		return attribute(HtmlConstants.NAME_ATTRIBUTE, nameValue);
	}

	public HtmlGenerator openTag(String tagName) throws IOException {
		writer.write("<");
		writer.write(tagName);
		return this;
	}

	public HtmlGenerator openTag(String tagName, String id) throws IOException {
		writer.write("<");
		writer.write(tagName);
		idAttribute(id);
		return this;
	}

	/**
	 * @param tagName
	 * @param id the id value to use in the tag. If null, it will not be
	 *        written. If you want an empty id, pass an empty String.
	 * @param name
	 * @param writer
	 * @return the writer given
	 * @throws IOException
	 */
	public HtmlGenerator openTag(String tagName, String id, String name) throws IOException {
		openTag(tagName);
		nameAttribute(name);
		idAttribute(id);
		return this;
	}

	/**
	 * If value is not null, write this attribute
	 * 
	 * @param name
	 * @param value
	 * @return this
	 * @throws IOException
	 */
	public HtmlGenerator optionalAttribute(String name, String value) throws IOException {
		if (value != null) attribute(name, value);
		return this;
	}

	/**
	 * @param writer
	 * @param typeValue
	 * @throws IOException
	 */
	public HtmlGenerator typeAttribute(String typeValue) throws IOException {
		return attribute(HtmlConstants.TYPE_ATTRIBUTE, typeValue);
	}

	/**
	 * @param writer
	 * @param valueValue
	 * @throws IOException
	 */
	public HtmlGenerator valueAttribute(String valueValue) throws IOException {
		return attribute(HtmlConstants.VALUE_ATTRIBUTE, valueValue);
	}

	/**
	 * Write directly to underlying Writer.
	 * 
	 * @param content
	 * @return this
	 * @throws IOException
	 */
	public HtmlGenerator write(String content) throws IOException {
		writer.write(content);
		return this;
	}
}
