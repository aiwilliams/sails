package org.opensails.sails.oem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.url.IUrl;

public class FileSystemResourceResolver implements IResourceResolver {
	protected final String mountPoint;

	public FileSystemResourceResolver(String mountPoint) {
		if (mountPoint.matches("[/\\\\]$")) this.mountPoint = mountPoint;
		else this.mountPoint = mountPoint + "/";
	}

	public boolean exists(IUrl applicationUrl) {
		if (applicationUrl.renderThyself().startsWith("file://")) try {
			return new File(new URI(applicationUrl.renderThyself())).exists();
		} catch (URISyntaxException e) {
			return false;
		}
		return false;
	}

	public boolean exists(String identifier) {
		return new File(mountPoint + identifier).exists();
	}

	public InputStream resolve(IUrl applicationUrl) {
		if (applicationUrl.renderThyself().startsWith("file://")) try {
			return new FileInputStream(new File(new URI(applicationUrl.renderThyself())));
		} catch (URISyntaxException e) {} catch (FileNotFoundException e) {}
		return null;
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
