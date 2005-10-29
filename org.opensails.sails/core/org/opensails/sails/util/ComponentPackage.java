package org.opensails.sails.util;

import org.apache.commons.lang.StringUtils;

/**
 * This resolver will attempt to discover components within a package. The
 * search will be done using one of:
 * 
 * <ol>
 * <li>The key, upper-camel-cased.</li>
 * <li>The key, upper-camel-cased and prepended to suffix, if defined.</li>
 * </ol>
 */
public class ComponentPackage<T> extends ClassResolverAdapter<T> {
    protected String componentSuffix;
    protected String packageRoot;

    /**
     * @param packageRoot The Java package in which component classes might be
     *        found.
     */
    public ComponentPackage(String packageRoot) {
        this(packageRoot, "");
    }

    /**
     * @param packageRoot The Java package in which component classes might be
     *        found.
     * @param componentSuffix optionally used when searching
     */
    public ComponentPackage(String packageRoot, String componentSuffix) {
        this.componentSuffix = componentSuffix;
        this.packageRoot = packageRoot + ".";
    }

    public String componentSuffix() {
        return componentSuffix;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends T> resolve(String key) {
        String absoluteControllerName = packageRoot + StringUtils.capitalize(key);
        Class<? extends T> componentClass = null;
        try {
            componentClass = (Class<? extends T>) Class.forName(absoluteControllerName);
        } catch (ClassNotFoundException notFoundWithoutController) {
            try {
                componentClass = (Class<? extends T>) Class.forName(absoluteControllerName + componentSuffix());
            } catch (ClassNotFoundException notFoundWithController) {
                return null;
            }
        }
        return componentClass;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(packageRoot);
        string.append("<componentName>");
        if (componentSuffix != null) string.append(componentSuffix);
        return string.toString();
    }
}
