package org.opensails.sails.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;

public class QueryParameters {
	protected final Map<String, Object> map;

	public QueryParameters() {
		this.map = new HashMap<String, Object>();
	}

	public QueryParameters(String queryString) {
		this.map = mapify(queryString);
	}

	public String decode(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("Could not encode value", e);
		}
	}

	public String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SailsException("Could not encode value", e);
		}
	}

	public String get(String name) {
		Object value = rawGet(name);
		if (value == null) return null;
		return value.toString();
	}

	/**
	 * The 'raw' query parameters. The objects may not be strings, and are not
	 * encoded.
	 */
	public Map<String, Object> map() {
		return map;
	}

	public void set(String name, Object value) {
		map.put(name, value);
	}

	/**
	 * @return a String like '?name=adapted value&other=1'
	 */
	public String toString() {
		if (map.isEmpty()) return "";
		StringBuilder s = new StringBuilder(map.size() * 10);
		s.append("?");
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			s.append(name);
			s.append("=");
			s.append(encode(get(name)));
			if (iter.hasNext()) s.append("&");
		}
		return s.toString();
	}

	protected Map<String, Object> mapify(String queryString) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(queryString)) return map;
		String[] params = queryString.split("&");
		for (String param : params) {
			String[] nameValue = param.split("=");
			map.put(nameValue[0], nameValue[1]);
		}
		return map;
	}

	protected Object rawGet(String name) {
		return map.get(name);
	};
}
