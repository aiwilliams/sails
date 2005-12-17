package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.UrlType;

/**
 * An HTML IMG element
 */
public class Image extends EventDependentHtmlElement<Image> implements IImage<Image> {
	public static final String ALT = "alt";
	public static final String IMAGE = "img";
	public static final String SRC = "src";

	protected String alt;
	protected String src;

	/**
	 * @param event
	 * @param src
	 *            the value for src. If starts with '/', relative to host, if
	 *            'http:', kept, otherwise relative to images directory as
	 *            configured by SailsApplication
	 */
	public Image(ISailsEvent event, String src) {
		super(IMAGE, event);
		this.src = src;
	}

	public Image alt(String alt) {
		this.alt = alt;
		return this;
	}

	public Image src(String src) {
		this.src = src;
		return this;
	}

	@Override
	protected void onToStringError(IOException e) {
		throw new SailsException("An exception occurred writing an image <" + src + ">", e);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attribute(SRC, event.resolve(UrlType.IMAGE, src));
		generator.optionalAttribute(ALT, alt);
		super.writeAttributes(generator);
	}
}
