package org.opensails.ezfile;

import java.io.File;

public class RealEzDir implements EzDir {
	private File file;

	public RealEzDir(File dir) {
		if (dir.exists() && !dir.isDirectory()) throw new IllegalArgumentException("Not a directory" + dir);
		file = dir;
	}

	public RealEzDir(String... pathNodes) {
		this(new File(EzPath.join(pathNodes)));
	}

	public EzDir asDir() {
		return null;
	}

	public boolean exists() {
		return file.exists();
	}

	public EzFile file(String... pathNodes) {
		return null;
	}

	public String getPath() {
		return file.getPath();
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public EzDir mkdir(String... pathNodes) {
		return null;
	}

	public void mkdirs() {
		file.mkdirs();
	}

	public EzDir parent() {
		return null;
	}

	public void recurse(EzDirectoryWalker walker) {
		for (File each : file.listFiles()) {
			if (each.isDirectory()) {
				RealEzDir subFolder = new RealEzDir(each);
				if (walker.step(subFolder)) subFolder.recurse(walker);
			} else walker.step(new RealEzFile(each));
		}
	}

	public void save(String text) {}

	public EzDir subdir(String... pathNodes) {
		return null;
	}

	public String text() {
		return null;
	}

	public void touch() {}

	public EzFile touch(String... pathNodes) {
		return null;
	}
}
