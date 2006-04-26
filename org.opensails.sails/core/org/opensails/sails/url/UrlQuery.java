package org.opensails.sails.url;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.opensails.sails.SailsException;

public class UrlQuery {
	private final String url;

	public UrlQuery(String url) {
		this.url = url;
	}

	public String getParameter(String name) {
		try {
			String query = new URL(url).getQuery();
			String[] params = query.split("&");
			for (String param : params) {
				String[] nameValue = param.split("=");
				if (URLDecoder.decode(nameValue[0], "UTF-8").equals(name)) return URLDecoder.decode(nameValue[1], "UTF-8");
			}
			return null;
		} catch (MalformedURLException e) {
			throw new IllegalStateException("This url is not valid", e);
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("How could this possibly happend", e);
		}
	}
}
