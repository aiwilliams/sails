package org.opensails.sails.form.html;

import java.io.IOException;

import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.html.HtmlConstants;
import org.opensails.sails.html.HtmlGenerator;

/**
 * An html input of type image.
 */
public class SubmitImage extends AbstractSubmit<SubmitImage> {
	public static final String IMAGE = "image";
	protected String srcValue;

	public SubmitImage(String name, String srcValue, ContainerAdapterResolver adapterResolver) {
		super(IMAGE, name, adapterResolver);
		this.srcValue = srcValue;
	}

	public String getSrc() {
		return srcValue;
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		generator.attribute(HtmlConstants.SRC_ATTRIBUTE, srcValue);
	}
}
