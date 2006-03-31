package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML SELECT
 * 
 * @author aiwilliams
 * 
 * @param <M> The type of SelectModel
 */
public class Select extends FormElement<Select> implements Labelable<Select> {
	public static final String SELECT = "select";

	protected Label label;
	protected SelectModel selectModel;

	/**
	 * @param name
	 */
	public Select(String name) {
		this(name, new ListSelectModel(new ArrayList<Object>()));
	}

	/**
	 * @param name
	 * @param selectModel
	 */
	public Select(String name, SelectModel selectModel) {
		super(SELECT, name);
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

	public Select model(Collection<? extends Object> model) {
		return model(new ListSelectModel(model));
	}

	public Select model(SelectModel model) {
		selectModel = model;
		return this;
	}

	@Override
	public void renderThyself(Writer writer) throws IOException {
		if (label != null) label.renderThyself(writer);
		render(writer);
	}

	/**
	 * @param option the selected Object. It will be run through the SelectModel
	 *        on render.
	 */
	public Select selected(Object option) {
		selectModel.select(option);
		return this;
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		if (!hasOptions()) return;

		String selectedValue = null;
		if (!selectModel.contains(selectModel.getSelected())) option(generator, SelectModel.NULL_OPTION_VALUE, SelectModel.NULL_OPTION_LABEL, true);
		else selectedValue = selectModel.getValue(selectModel.getSelected());
		for (int count = 0; count < selectModel.getOptionCount(); count++) {
			String value = selectModel.getValue(count);
			option(generator, value, selectModel.getLabel(count), value.equals(selectedValue));
		}
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	protected boolean hasOptions() {
		return selectModel != null && selectModel.getOptionCount() > 0;
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
		if (!hasOptions()) generator.attribute(HtmlConstants.DISABLED_ATTRIBUTE, HtmlConstants.TRUE);
	}
}
