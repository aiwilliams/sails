package org.opensails.hibernate;

import org.apache.commons.lang.ArrayUtils;

/**
 * Makes for cleaner configurations that only use annotations for mappings.
 * 
 * @author aiwilliams
 */
public abstract class AnnotationMapping implements IHibernateMappingConfiguration {
	public Class[] mappedClasses() {
		return ArrayUtils.EMPTY_CLASS_ARRAY;
	}
}
