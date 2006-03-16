package org.opensails.spyglass.resolvers;

import org.apache.commons.lang.StringUtils;
import org.opensails.spyglass.ClassResolverAdapter;

/**
 * Attempts to resolve classes within a package. The search will be done using
 * one of:
 * 
 * <ol>
 * <li>The key, upper-camel-cased.</li>
 * <li>The key, upper-camel-cased and prepended to suffix, if defined.</li>
 * </ol>
 */
public class PackageClassResolver<T> extends ClassResolverAdapter<T> {
	protected String suffix;
	protected String packageRoot;

	/**
	 * @param packageRoot A Class to use to declare the Java package in which
	 *        classes might be found.
	 */
	public PackageClassResolver(Class packageRoot) {
		this(packageRoot.getPackage().getName());
	}

	/**
	 * @param packageRoot A Class to use to declare the Java package in which
	 *        classes might be found.
	 * @param suffix optionally used when searching
	 */
	public PackageClassResolver(Class packageRoot, String suffix) {
		this(packageRoot.getPackage().getName(), suffix);
	}

	/**
	 * @param packageRoot The Java package in which classes might be found.
	 */
	public PackageClassResolver(String packageRoot) {
		this(packageRoot, "");
	}

	/**
	 * @param packageRoot The Java package in which classes might be found.
	 * @param suffix optionally used when searching
	 */
	public PackageClassResolver(String packageRoot, String suffix) {
		this.suffix = suffix;
		this.packageRoot = packageRoot + ".";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> resolve(String key) {
		String canonicalName = packageRoot + StringUtils.capitalize(key);
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
		string.append(packageRoot);
		string.append("<componentName>");
		if (suffix != null) string.append(suffix);
		return string.toString();
	}
}
