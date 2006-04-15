package org.opensails.spyglass.resolvers;

import org.opensails.spyglass.ClassKeyMappings;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.Mapping;
import org.opensails.spyglass.SpyGlass;

/**
 * Attempts to resolve classes within a package. The search will be done using
 * one of:
 * 
 * <ol>
 * <li>The key, upper-camel-cased.</li>
 * <li>The key, upper-camel-cased and prepended to suffix, if defined.</li>
 * </ol>
 */
public class PackageClassResolver<T> implements IClassResolver<T> {
	protected String suffix;
	protected Package javaPackage;
	protected String packageName;

	/**
	 * @param packageClass A Class to use to declare the Java package in which
	 *        classes might be found.
	 */
	public PackageClassResolver(Class packageClass) {
		this(packageClass, null);
	}

	/**
	 * @param packageClass A Class to use to declare the Java package in which
	 *        classes might be found.
	 * @param suffix optional, used when searching
	 */
	public PackageClassResolver(Class packageClass, String suffix) {
		checkClass(packageClass);
		initialize(packageClass.getPackage(), suffix);
	}

	/**
	 * @param javaPackage the Java package in which classes might be found.
	 */
	public PackageClassResolver(Package javaPackage) {
		this(javaPackage, null);
	}

	/**
	 * @param javaPackage the Java package in which classes might be found.
	 * @param suffix optional, used when searching
	 */
	public PackageClassResolver(Package javaPackage, String suffix) {
		checkPackage(javaPackage);
		initialize(javaPackage, suffix);
	}

	/**
	 * @param packageName the Java package name in which classes might be found.
	 */
	public PackageClassResolver(String packageName) {
		this(packageName, null);
	}

	/**
	 * @param packageName the Java package name in which classes might be found
	 * @param suffix optional, used when searching
	 */
	public PackageClassResolver(String packageName, String suffix) {
		initialize(Package.getPackage(packageName), packageName, suffix);
	}

	public String getPackageName() {
		return packageName;
	}

	@SuppressWarnings("unchecked")
	public Class<T> resolve(Class key) {
		if (javaPackage != null) {
			ClassKeyMappings mappings = javaPackage.getAnnotation(ClassKeyMappings.class);
			if (mappings != null) for (Mapping mapping : mappings.value())
				for (Class classKey : mapping.classKeys())
					if (classKey == key) return mapping.value();
		}
		return resolve(SpyGlass.getName(key));
	}

	@SuppressWarnings("unchecked")
	public Class<T> resolve(String key) {
		String canonicalName = getPackageName() + "." + SpyGlass.upperCamelName(key);
		Class<T> classFound = null;
		try {
			classFound = (Class<T>) Class.forName(canonicalName);
		} catch (ClassNotFoundException notFoundWithoutController) {
			try {
				classFound = (Class<T>) Class.forName(canonicalName + suffix());
			} catch (ClassNotFoundException notFoundWithSuffix) {
				return null;
			}
		}
		return classFound;
	}

	public String suffix() {
		return suffix;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(getPackageName());
		string.append(".<className>");
		if (suffix != null) string.append(suffix);
		return string.toString();
	}

	protected void checkClass(Class packageClass) {
		if (packageClass == null) throw new IllegalArgumentException("Null class is useless for finding package");
	}

	protected void checkPackage(Package javaPackage) {
		if (javaPackage == null) throw new IllegalArgumentException("Null package is useless for finding classes");
	}

	protected void initialize(Package javaPackage, String suffix) {
		initialize(javaPackage, javaPackage == null ? null : javaPackage.getName(), suffix);
	}

	protected void initialize(Package javaPackage, String packageName, String suffix) {
		this.suffix = suffix != null ? suffix : "";
		this.javaPackage = javaPackage;
		this.packageName = packageName;
	}
}
