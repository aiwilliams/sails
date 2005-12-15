package org.opensails.hibernate;

public interface IHibernateMappingConfiguration {
	Class[] mappedClasses();
	Class[] annotatedClasses();
}
