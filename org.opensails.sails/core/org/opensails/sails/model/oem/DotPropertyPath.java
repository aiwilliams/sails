package org.opensails.sails.model.oem;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.model.IPropertyPath;

/**
 * Represents a path to a property as dot(.) separated nodes.
 * 
 * @author aiwilliams
 */
public class DotPropertyPath implements IPropertyPath {
	protected String path;

	/**
	 * The target identifier is the first node.
	 * 
	 * @param path complete property path, including target identifier
	 */
	public DotPropertyPath(String path) {
		if (path == null) throw new NullPointerException("A property path cannot be null");
		if (StringUtils.isBlank(path)) throw new IllegalArgumentException("A property path cannot be blank");
		this.path = path.trim();
	}

	/**
	 * @param path target-relative property path
	 * @param targetIdentifier which is not in the path
	 */
	public DotPropertyPath(String path, String targetIdentifier) {
		this(targetIdentifier + "." + path);
	}

	public String[] getAllNodes() {
		StringTokenizer tokens = new StringTokenizer(path, ".");
		List<String> nodes = new ArrayList<String>();
		while (tokens.hasMoreTokens())
			nodes.add(tokens.nextToken());
		return nodes.toArray(new String[nodes.size()]);
	}

	public String getFullName() {
		return path;
	}

	public String getModelName() {
		if (path.startsWith(".")) return ".";
		int delimiterIndex = path.indexOf(".");
		if (delimiterIndex == -1) return path;
		return path.substring(0, delimiterIndex);
	}

	public int getNodeCount() {
		return getAllNodes().length;
	}

	public String[] getNodes() {
		String[] allNodes = getAllNodes();
		String[] nodes = allNodes;
		if (allNodes.length > 1) {
			nodes = new String[allNodes.length - 1];
			System.arraycopy(allNodes, 1, nodes, 0, nodes.length);
		}
		return nodes;
	}

	public String getProperty(int index) {
		if (index < 0) throw new IllegalArgumentException("Index must be a positive number.");
		int delimiterIndex = 0;
		for (int i = 0; i < index; i++) {
			delimiterIndex = path.indexOf(".", delimiterIndex);
			if (delimiterIndex == -1) return "";
			delimiterIndex++;
		}
		return path.substring(delimiterIndex);
	}

	public String getPropertyName() {
		int lastDelimiter = path.lastIndexOf(".");
		return path.substring(lastDelimiter + 1);
	}

	public String lastNode() {
		String[] allNodes = getAllNodes();
		return allNodes.length > 0 ? allNodes[allNodes.length - 1] : null;
	}

	@Override
	public String toString() {
		return path;
	}
}