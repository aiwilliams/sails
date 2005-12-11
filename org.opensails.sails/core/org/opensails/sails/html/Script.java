package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.sails.url.IUrl;
import org.opensails.viento.Block;

/**
 * Script with src or in-line content
 */
public class Script extends AbstractHtmlElement<Script> {
	public static final String NAME = "script";

	protected Block block;
	protected IUrl src;

	public Script() {
		super(NAME);
		type("text/javascript");
	}

	public Script(Block block) {
		this();
		inline(block);
	}

	public Script(IUrl url) {
		this();
		src(url);
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

	public Script inline(Block block) {
		this.block = block;
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
		if (block != null) {
			generator.write("\n");
			generator.write(block.evaluate());
			generator.write("\n");
		}
	}

	@Override
	protected boolean hasBody() {
		return true;
	}
}
