package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.Block;

/**
 * In-line style.
 * <p>
 * Styles that are linked in are done so with a
 * {@link org.opensails.sails.html.Link}.
 */
public class Style extends AbstractHtmlElement<Style> {
	public static final String NAME = "style";

	protected Block inlineContent;
	protected Link linkedStyle;

	public Style(Block content) {
		this();
		inline(content);
	}

	public Style(IUrl url) {
		this();
		linkedStyle = new Link();
		linkedStyle.href(url);
		linkedStyle.rel("stylesheet");
	}

	public Style(String url) {
		this(new ExternalUrl(url));
	}

	protected Style() {
		super(NAME);
		type("text/css");
	}

	@Override
	public int hashCode() {
		if (inlineContent != null) return super.hashCode() & inlineContent.evaluate().hashCode();
		return super.hashCode() & linkedStyle.hashCode();
	}

	public Style inline(Block content) {
		this.inlineContent = content;
		return this;
	}

	public Style type(String type) {
		return attribute("type", type);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		generator.write("\n");
		generator.write(inlineContent.evaluate());
		generator.write("\n");
	}

	@Override
	protected boolean hasBody() {
		return inlineContent != null;
	}

	@Override
	protected void render(HtmlGenerator generator) throws IOException {
		if (linkedStyle == null) super.render(generator);
		else {
			linkedStyle.addAttributes(attributes);
			linkedStyle.render(generator);
		}
	}
}
