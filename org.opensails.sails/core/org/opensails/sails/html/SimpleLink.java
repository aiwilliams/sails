package org.opensails.sails.html;

import java.io.IOException;
import java.io.StringWriter;

import org.opensails.sails.SailsException;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class SimpleLink<T extends SimpleLink> extends EventDependentHtmlElement<T> implements ILink<T> {
	public static final String ANCHOR = "a";
	public static final String HREF = "href";

	protected String text;
	protected IUrl url;

	public SimpleLink(ISailsEvent event, IUrl url) {
		super(ANCHOR, event);
		this.url = url;
	}

	/**
	 * An alias to {@link #secure()} for lame introspectors that require
	 * PropertyDescriptors to do anything
	 */
	public T getSecure() {
		return secure();
	}

	@SuppressWarnings("unchecked")
	public T href(String href) {
		this.url = new ExternalUrl(event, href);
		return (T) this;
	}

	public ImageLink image(IUrl src) {
		ImageLink imageLink = new ImageLink(event, url, src);
		StringWriter writer = new StringWriter();
		try {
			renderLinkBody(new HtmlGenerator(writer));
		} catch (IOException e) {
			throw new SailsException("Failure converting ActionLink to ImageLink", e);
		}
		imageLink.alt(writer.toString());
		imageLink.linkAttributes(attributes);
		return imageLink;
	}

	/**
	 * Provides for the expected behaviour of
	 * $link.action('something').image('some.jpg').
	 * 
	 * @param src
	 * @return an ImageLink bound to the href of this
	 */
	public ImageLink image(String src) {
		return image(event.resolve(UrlType.IMAGE, src));
	}

	@SuppressWarnings("unchecked")
	public T secure() {
		url.secure();
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T text(String text) {
		this.text = text;
		return (T) this;
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		renderLinkBody(generator);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	/**
	 * Subclasses may override to change how the link href is represented.
	 * Default uses href.
	 */
	protected String hrefValue() {
		return url.renderThyself();
	}

	/**
	 * Subclasses may override to change how the link body is rendered. Default
	 * renders text.
	 * 
	 * This is written this way to allow for very custom body generation. The
	 * hrefValue method is the way it is because the href attribute must be a
	 * simple URL String.
	 * 
	 * @param generator
	 * @throws IOException
	 */
	protected void renderLinkBody(HtmlGenerator generator) throws IOException {
		if (text == null) generator.write(url.renderThyself());
		else generator.write(text);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		super.writeAttributes(generator);
		generator.attribute(HREF, hrefValue());
	}
}
