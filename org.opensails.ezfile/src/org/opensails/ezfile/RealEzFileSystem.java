package org.opensails.ezfile;

import java.io.File;

public class RealEzFileSystem implements EzFileSystem {
	private String mountPoint;

	public RealEzFileSystem(String mountPoint) {
		this.mountPoint = new File(mountPoint).getAbsolutePath();
	}

	public EzDir dir(String path) {
		return new RealEzDir(String.format("%s/%s", mountPoint, path));
	}

	public EzFile file(String path) {
		return new RealEzFile(String.format("%s/%s", mountPoint, path));
	}

	public EzDir mkdir(String path) {
		EzDir dir = dir(path);
		dir.mkdirs();
		return dir;
	}
}
