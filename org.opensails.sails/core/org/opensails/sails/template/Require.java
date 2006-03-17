package org.opensails.sails.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.opensails.sails.SailsException;
import org.opensails.sails.html.IHtmlElement;
import org.opensails.sails.html.Link;
import org.opensails.sails.html.Script;
import org.opensails.sails.html.Style;

/**
 * Used by the RequireMixin and the ComponentRequire. Not necessarily the
 * 'public' way to require stuff.
 * 
 * @author aiwilliams
 */
public class Require {
	protected final List<Script> componentApplicationScripts = new ArrayList<Script>(3);
	protected final List<Script> componentRequiredScripts = new ArrayList<Script>(3);
	protected final List<Script> componentImplicitScripts = new ArrayList<Script>(3);
	protected final List<Style> componentApplicationStyles = new ArrayList<Style>(3);
	protected final List<Style> componentImplicitStyles = new ArrayList<Style>(3);
	protected final List<Style> componentRequiredStyles = new ArrayList<Style>(3);
	protected final List<Link> links = new ArrayList<Link>(3);
	protected final List<Script> scripts = new ArrayList<Script>(3);
	protected final List<Style> styles = new ArrayList<Style>(3);

	public void componentApplicationScript(Script script) {
		componentApplicationScripts.add(script);
	}

	public void componentApplicationStyle(Style style) {
		componentApplicationStyles.add(style);
	}

	public void componentImplicitScript(Script script) {
		componentImplicitScripts.add(script);
	}

	public void componentImplicitStyle(Style style) {
		componentImplicitStyles.add(style);
	}

	public void componentRequiredScript(Script script) {
		componentRequiredScripts.add(script);
	}

	public void componentRequiredStyle(Style style) {
		componentRequiredStyles.add(style);
	}

	public void link(Link link) {
		links.add(link);
	}

	public RequireOutput output() {
		return new RequireOutput();
	}

	public void script(Script script) {
		scripts.add(script);
	}

	public void style(Style style) {
		styles.add(style);
	}

	public class RequireOutput {
		public String componentApplicationScripts() {
			return toString(componentApplicationScripts);
		}

		public String componentApplicationStyles() {
			return toString(componentApplicationStyles);
		}

		public String componentImplicitScripts() {
			return toString(componentImplicitScripts);
		}

		public String componentImplicitStyles() {
			return toString(componentImplicitStyles);
		}

		public String componentRequiredScripts() {
			return toString(componentRequiredScripts);
		}

		public String componentRequiredStyles() {
			return toString(componentRequiredStyles);
		}

		public String links() {
			return toString(links);
		}

		public String scripts() {
			return toString(scripts);
		}

		public String styles() {
			return toString(styles);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(componentApplicationScripts());
			builder.append("\n");
			builder.append(componentRequiredScripts());
			builder.append("\n");
			builder.append(componentImplicitScripts());
			builder.append("\n");
			builder.append(scripts());
			builder.append("\n");
			builder.append(links());
			builder.append("\n");
			builder.append(componentApplicationStyles());
			builder.append("\n");
			builder.append(componentRequiredStyles());
			builder.append("\n");
			builder.append(componentImplicitStyles());
			builder.append("\n");
			builder.append(styles());
			builder.append("\n");
			return builder.toString();
		}

		private <T extends IHtmlElement> String toString(List<T> elements) {
			StringWriter writer = new StringWriter(75);
			for (Iterator<T> iter = new LinkedHashSet<T>(elements).iterator(); iter.hasNext();) {
				try {
					iter.next().renderThyself(writer);
					if (iter.hasNext()) writer.append("\n");
				} catch (IOException e) {
					throw new SailsException("Failure rendering a required element", e);
				}
			}
			return writer.toString();
		}
	}
}
