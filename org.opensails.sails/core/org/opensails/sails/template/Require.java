package org.opensails.sails.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.opensails.sails.html.Link;
import org.opensails.sails.html.Script;
import org.opensails.sails.html.Style;

public class Require {
	protected final List<Link> links = new ArrayList<Link>(3);
	protected final List<Script> scripts = new ArrayList<Script>(3);
	protected final List<Style> styles = new ArrayList<Style>(3);

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
			builder.append(scripts());
			builder.append("\n");
			builder.append(links());
			builder.append("\n");
			builder.append(styles());
			builder.append("\n");
			return builder.toString();
		}

		private <T> String toString(List<T> elements) {
			StringBuilder builder = new StringBuilder();
			for (Iterator iter = new LinkedHashSet<T>(elements).iterator(); iter.hasNext();) {
				builder.append(iter.next());
				if (iter.hasNext()) builder.append("\n");
			}
			return builder.toString();
		}
	}
}
