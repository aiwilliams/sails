package org.opensails.ezfile;

import java.io.File;

public class RealEzFileSystem implements EzFileSystem {
	private String mountPoint;

	public RealEzFileSystem(String mountPoint) {
		this.mountPoint = new File(mountPoint).getAbsolutePath();
	}

	public EzDir dir(String... pathNodes) {
		return new RealEzDir(mountPoint, EzPath.join(pathNodes));
	}

	public EzFile file(String... pathNodes) {
		return new RealEzFile(mountPoint, EzPath.join(pathNodes));
	}

	public EzDir mkdir(String... pathNodes) {
		EzDir dir = dir(pathNodes);
		dir.mkdirs();
		return dir;
	}

	public EzDir cd(String... pathNodes) {
		String path = EzPath.join(pathNodes);
		if (EzPath.isRelative(path)) mountPoint = EzPath.join(mountPoint, path);
		else mountPoint = path;
		return dir(mountPoint);
	}
}
