/*
 * Created on May 16, 2005
 *
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.form.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.html.AbstractHtmlElement;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An HTML LABEL.
 */
public class Label extends AbstractHtmlElement<Label> {
	protected static class VirtualLabelable implements Labelable<VirtualLabelable> {
		private final String forId;

		protected VirtualLabelable(String forId) {
			this.forId = forId;
		}

		public VirtualLabelable attributes(Map attributes) {
			// do nothing
			return this;
		}

		public String getId() {
			return forId;
		}

		public String getName() {
			return "undefined";
		}

		public VirtualLabelable label(String text) {
			throw new SailsException("Should be called on this");
		}

		public void toString(Writer writer) throws IOException {
			// do nothing
		}
	}

	public static final String FOR = "for";
	public static final String LABEL = "label";

	protected Labelable<? extends Labelable> labelable;

	protected String labelText;

	/**
	 * @param labelable
	 */
	public Label(Labelable<? extends Labelable> labelable) {
		super(LABEL);
		this.labelable = labelable;
	}

	/**
	 * @param labelable
	 */
	public Label(String forId) {
		this(new VirtualLabelable(forId));
	}

	/**
	 * @param labelText
	 */
	public Label text(String labelText) {
		this.labelText = labelText;
		return this;
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		if (labelText != null)
			generator.write(labelText);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}
	
	@Override
	protected void render(HtmlGenerator generator) throws IOException {
		if (StringUtils.isBlank(labelable.getId()))
			throw new IllegalArgumentException("Cannot render a valid label when element to be labeled <" + labelable.getName() + "> has no id");
		super.render(generator);
	}

	@Override
	protected void onToStringError(IOException e) {
		throw new RuntimeException("An exception occurred writing the element <" + labelable.getId() + "> of type <" + getClass() + ">", e);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attribute(FOR, labelable.getId());
		super.writeAttributes(generator);
	}
}
