package org.opensails.ezfile;

public class EzPath {
	/**
	 * Creates a usable path from a variable number of node arguments.
	 * <p>
	 * The first node is never modified. If it starts with slash, it is left
	 * there. If there is no slash, one is not added. This allows for correct
	 * relative paths.
	 * 
	 * @param pathNodes
	 * @return
	 */
	public static final String join(String... pathNodes) {
		StringBuilder s = new StringBuilder(pathNodes.length * 6);
		for (int i = 0; i < pathNodes.length; i++) {
			String node = pathNodes[i];
			if (i != 0 && isRelative(node)) s.append("/");
			if (!isRoot(node) || pathNodes.length == 1) s.append(node);
		}
		return s.toString();
	}

	public static boolean isRoot(String path) {
		return "/".equals(path) || "\\".equals(path);
	}

	public static boolean isRelative(String path) {
		return !(path.startsWith("/") || path.startsWith("\\"));
	}
}
