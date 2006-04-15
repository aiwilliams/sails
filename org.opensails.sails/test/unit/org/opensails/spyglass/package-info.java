/**
 * Demonstrates the ability to map class keys to a class.
 * <p>
 * Note that imports come after the package declaration, just like a regular
 * java file. This proves that we can reference classes outside of this package.
 */
@ClassKeyMappings( { @Mapping(classKeys = { Map.class, String.class }, value = PackageClassResolverTests.class) })
package org.opensails.spyglass;

import java.util.Map;