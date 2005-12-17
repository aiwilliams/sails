package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.url.IUrl;

/**
 * In-line style.
 * <p>
 * Styles that are linked in are done so with a
 * {@link org.opensails.sails.html.Link}.
 */
public class Style extends AbstractHtmlElement<Style> implements IInlineContentElement<Style> {
	public static final String NAME = "style";

	protected IInlineContent inlineContent;
	protected Link linkedStyle;

	public Style(IInlineContent content) {
		this();
		inline(content);
	}

	public Style(IUrl url) {
		this();
		linkedStyle = new Link();
		linkedStyle.href(url);
		linkedStyle.rel("stylesheet");
	}

	protected Style() {
		super(NAME);
		type("text/css");
	}

	@Override
	public int hashCode() {
		if (inlineContent != null) return super.hashCode() & inlineContent.render().hashCode();
		return super.hashCode() & linkedStyle.hashCode();
	}

	public Style inline(IInlineContent content) {
		this.inlineContent = content;
		return this;
	}

	public Style type(String type) {
		return attribute("type", type);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		generator.write("\n");
		generator.write(inlineContent.render());
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
