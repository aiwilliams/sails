package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.SailsException;
import org.opensails.sails.url.IUrl;

/**
 * An HTML IMG element
 */
public class Image extends AbstractHtmlElement<Image> implements IImage<Image> {
	public static final String ALT = "alt";
	public static final String IMAGE = "img";
	public static final String SRC = "src";

	protected String alt;
	protected IUrl src;

	public Image(IUrl src) {
		super(IMAGE);
		this.src = src;
	}

	public Image alt(String alt) {
		this.alt = alt;
		return this;
	}

	public Image src(IUrl src) {
		this.src = src;
		return this;
	}

	@Override
	protected void onToStringError(IOException e) {
		throw new SailsException("An exception occurred writing an image <" + src + ">", e);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attribute(SRC, src);
		generator.optionalAttribute(ALT, alt);
		super.writeAttributes(generator);
	}
}
