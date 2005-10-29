package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.ISailsEvent;
import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;

public abstract class AbstractLink<T extends AbstractLink> extends EventDependentHtmlElement<T> implements ILink<T> {
	public static final String ANCHOR = "a";
	public static final String HREF = "href";

	protected String text;
	protected IUrl url;

	public AbstractLink(ISailsEvent event, IUrl url) {
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
		return url.render();
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
		generator.write(text);
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attribute(HREF, hrefValue());
	}
}
