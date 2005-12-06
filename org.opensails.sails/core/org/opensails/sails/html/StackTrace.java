package org.opensails.sails.html;

import java.io.IOException;

import org.opensails.viento.builtins.EscapeMixin;

public class StackTrace extends AbstractHtmlElement<StackTrace> {
	protected Throwable root;

	public StackTrace(Throwable throwable) {
		super("div");
		this.root = throwable;
	}

	@Override
	protected void writeAttributes(HtmlGenerator generator) throws IOException {
		generator.attribute("class", "exception-chain");
		super.writeAttributes(generator);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		printStackTrace(root, generator);
	}

	protected void printStackTrace(Throwable throwable, HtmlGenerator generator) throws IOException {
		generator.openTag("div").attribute("class", "exception").closeTag();

		generator.openTag("div").attribute("class", "exception-class").closeTag().write(throwable.getClass().getName()).endTag("div");
		generator.openTag("div").attribute("class", "message").closeTag().write(EscapeMixin.escapeHtml(throwable.getMessage())).endTag("div");

		generator.openTag("div").attribute("class", "stack-trace").closeTag();
		StackTraceElement[] stackTrace = throwable.getStackTrace();
		for (StackTraceElement element : stackTrace)
			generator.openTag("div").attribute("class", "stack-frame").closeTag().write(element.toString()).endTag("div");
		generator.endTag("div");

		generator.endTag("div");

		if (throwable.getCause() != null) {
			generator.openTag("div").attribute("class", "caused-by").closeTag().write("Caused by:").endTag("div");
			printStackTrace(throwable.getCause(), generator);
		}
	}

	@Override
	protected boolean hasBody() {
		return true;
	}
}
