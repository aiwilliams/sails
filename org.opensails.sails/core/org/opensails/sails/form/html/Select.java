/*
 * Created on May 14, 2005, flying into OHare
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML SELECT
 */
public class Select extends FormElement<Select> implements Labelable<Select> {
	public static final String SELECT = "select";

	protected Label label;
	protected Object selected;
	protected SelectModel selectModel;

	/**
	 * @param name
	 */
	public Select(String name) {
		super(Select.SELECT, name);
	}

	/**
	 * @param name
	 * @param selectModel
	 */
	public Select(String name, SelectModel selectModel) {
		this(name);
		this.selectModel = selectModel;
	}

	/**
	 * @param name
	 * @param selectModel
	 * @param attributes
	 */
	public Select(String name, SelectModel selectModel, Map<String, String> attributes) {
		this(name, selectModel);
		this.attributes = attributes;
	}

	@Override
	public String getId() {
		return id;
	}

	public Select label(String text) {
		label = new Label(this).text(text);
		return this;
	}

	public Select model(SelectModel model) {
		selectModel = model;
		return this;
	}

	/**
	 * @param selected the selected Object. It will be run through the
	 *        SelectModel on render.
	 */
	public Select selected(Object selected) {
		this.selected = selected;
		return this;
	}

	@Override
	public void toString(Writer writer) throws IOException {
		if (label != null) label.toString(writer);
		render(writer);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		if (selectModel == null) return;

		String selectedValue = null;
		if (!selectModel.contains(selected)) option(generator, SelectModel.NULL_OPTION_VALUE, SelectModel.NULL_OPTION_LABEL, true);
		else selectedValue = selectModel.getValue(selected);
		for (int count = 0; count < selectModel.getOptionCount(); count++) {
			String value = selectModel.getValue(count);
			option(generator, value, selectModel.getLabel(count), value.equals(selectedValue));
		}
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	protected HtmlGenerator option(HtmlGenerator generator, String value, String label, boolean selected) throws IOException {
		generator.openTag(HtmlConstants.OPTION).attribute(HtmlConstants.VALUE_ATTRIBUTE, value);
		if (selected) generator.attribute(HtmlConstants.SELECTED_ATTRIBUTE, selected);
		generator.closeTag();
		generator.write(label);
		generator.endTag(HtmlConstants.OPTION);
		return generator;
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		if (selectModel == null) generator.attribute(HtmlConstants.DISABLED_ATTRIBUTE, HtmlConstants.TRUE);
	}
}
