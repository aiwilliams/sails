package org.opensails.sails.util;

import org.apache.commons.lang.WordUtils;


public class EnumHelper {

	public static String titleCase(Enum item) {
		return WordUtils.capitalizeFully(item.name().replace('_', ' '));
	}

}
