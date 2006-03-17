package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.url.ExternalUrl;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.Block;

/**
 * Script with src or in-line content
 */
public class Script extends AbstractHtmlElement<Script> {
	public static final String NAME = "script";

	protected Block inlineContent;
	protected IUrl src;

	public Script() {
		super(NAME);
		type("text/javascript");
	}

	public Script(Block inlineContent) {
		this();
		inline(inlineContent);
	}

	public Script(IUrl url) {
		this();
		src(url);
	}

	public Script(String url) {
		this(new ExternalUrl(url));
	}

	public Script defer(boolean defer) {
		return attribute("defer", Boolean.toString(defer));
	}

	@Override
	public boolean equals(Object obj) {
		return src.equals(((Script) obj).src);
	}

	@Override
	public int hashCode() {
		return src.hashCode();
	}

	public Script inline(Block content) {
		this.inlineContent = content;
		return this;
	}

	public Script language(String lang) {
		return attribute("language", lang);
	}

	public Script src(IUrl url) {
		this.src = url;
		return attribute("src", src.render());
	}

	public Script type(String type) {
		return attribute("type", type);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		if (inlineContent != null) {
			generator.write("\n");
			generator.write(inlineContent.evaluate());
			generator.write("\n");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opensails.sails.html.AbstractHtmlElement#hasBody()
	 * 
	 * Always return true due to bugs in older browsers where script tag must
	 * end with close tag.
	 */
	@Override
	protected boolean hasBody() {
		return true;
	}
}
