package org.opensails.sails.oem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.opensails.sails.IResourceResolver;

public class FileSystemResourceResolver implements IResourceResolver {
	protected final String mountPoint;

	public FileSystemResourceResolver(String mountPoint) {
		if (mountPoint.matches("[/\\\\]$")) this.mountPoint = mountPoint;
		else this.mountPoint = mountPoint + "/";
	}

	public InputStream resolve(String identifier) {
		File file = new File(mountPoint + identifier);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
