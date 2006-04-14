package org.opensails.sails.html;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * It is intended that this generator be created for each request, such that any
 * id's generated within on request will be unique.
 * 
 * @author aiwilliams
 */
public class UnderscoreIdGenerator implements IElementIdGenerator {
	protected Map<String, Integer> ids = new HashMap<String, Integer>();

	public String idForLabel(String label) {
		return uniqueId(label.replace(' ', '_'));
	}

	public String idForName(String name) {
		return uniqueId(name.replace('.', '_'));
	}

	public String idForNameValue(String name, String value) {
		return uniqueId(name.replace('.', '_') + (StringUtils.isBlank(name) || StringUtils.isBlank(value) ? "" : "-")
				+ (value == null ? "" : value.replaceAll("[\\s\\.,]+", "_")));
	}

	public String idForStrings(String... strings) {
		return uniqueId(StringUtils.join(strings, '_'));
	}

	protected String uniqueId(String value) {
		Integer countObj = ids.get(value);
		int count = countObj == null ? 0 : countObj.intValue();
		ids.put(value, count + 1);
		if (count == 0) return value;
		else return String.format("%s-%d", value, count);
	}
}
