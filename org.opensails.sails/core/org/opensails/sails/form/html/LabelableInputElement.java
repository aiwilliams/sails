package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;

public abstract class LabelableInputElement<T extends LabelableInputElement> extends InputElement<T> implements ILabelable<T> {
	protected static final boolean RENDER_LABEL_AFTER = false;
	protected static final boolean RENDER_LABEL_BEFORE = true;

	protected Label label;
	protected boolean labelBefore;

	public LabelableInputElement(boolean labelBefore, String typeValue, String name) {
		super(typeValue, name);
		this.labelBefore = labelBefore;
	}

	/**
	 * @param labelBefore must be declared by subclasses to indicate whether
	 *        their Label should be rendered before themselves. For instance, a
	 *        Checkbox typically has it's Label rendered after, so this would be
	 *        false.
	 * @param typeValue used in the 'type' attribute
	 * @param name used in the 'name' attribute
	 * @param id used in the 'id' attribute
	 */
	public LabelableInputElement(boolean labelBefore, String typeValue, String name, String id) {
		super(typeValue, name, id);
		this.labelBefore = labelBefore;
	}

	@SuppressWarnings("unchecked")
	public T label(String text) {
		label = new Label(this).text(text);
		return (T) this;
	}

	@Override
	public void renderThyself(Writer writer) throws IOException {
		if (label != null && labelBefore) label.renderThyself(writer);
		render(writer);
		if (label != null && !labelBefore) label.renderThyself(writer);
	}
}
