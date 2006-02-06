package org.opensails.ezfile.memory;

import org.opensails.ezfile.EzDir;
import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzPath;

public class MemoryEzFile implements EzFile {
	protected MemoryEzFileSystem fs;
	protected String path;

	public MemoryEzFile() {
		this("undefined");
	}

	public MemoryEzFile(String... pathNodes) {
		this(new MemoryEzFileSystem(), pathNodes);
	}

	public MemoryEzFile(MemoryEzFileSystem fs, String... pathNodes) {
		this.fs = fs;
		this.path = EzPath.join(pathNodes);
	}

	public EzDir asDir() {
		MemoryEzFileImpl impl = fs.fileImpl(path);
		if (impl.exists && !impl.isDirectory) throw new IllegalStateException("Cannot convert an existing non-directory file into a directory");
		impl.isDirectory = true;
		return new MemoryEzDir(fs, path);
	}

	public boolean exists() {
		return fs.fileImpl(path).exists;
	}

	public String getPath() {
		return path;
	}

	public boolean isDirectory() {
		return fs.fileImpl(path).isDirectory;
	}

	public void mkdirs() {
		String parentPath = parentPath();
		if (parentPath == null) return;
		parent().mkdirs();
	}

	public EzDir parent() {
		return new MemoryEzDir(fs, parentPath());
	}

	private String parentPath() {
		if ("/".equals(path)) return null;
		if (path.lastIndexOf("/") == 0) return "/";
		return path.substring(0, path.lastIndexOf("/"));
	}

	public void save(String text) {
		if (fs.fileImpl(path).isDirectory) throw new IllegalStateException("Cannot save text to a directory");
		mkdirs();
		fs.fileImpl(path).save(text);
	}

	public String text() {
		return fs.fileImpl(path).text;
	}

	public void touch() {
		fs.fileImpl(path).exists = true;
	}

	@Override
	public String toString() {
		return String.format("File [%s]", path);
	}
}
