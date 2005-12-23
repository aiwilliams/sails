package org.opensails.sails.mixins;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.viento.Name;

public class HtmlMixin {
	public String escape(Object o) {
		return StringEscapeUtils.escapeHtml(string(o));
	}

	public String h(Object o) {
		return escape(o);
	}

	@Name("blank?")
	public boolean isBlank(Object o) {
		return StringUtils.isBlank(o == null ? null : o.toString());
	}

	public String urlEncode(Object o) {
		try {
			return URLEncoder.encode(string(o), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("What in the... Seems URLEncoder doesn't support 'UTF-8' encoding today.");
		}
	}

	private String string(Object o) {
		return o == null ? "" : o.toString();
	}
	
	public static String escapeNewlines(Object object) {
		if (object == null)
			return null;
		return object.toString().replace("\n", "\\n").replace("\r", "\\r");
	}
}
