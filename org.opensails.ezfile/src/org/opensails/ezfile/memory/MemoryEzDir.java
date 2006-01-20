package org.opensails.ezfile.memory;

import java.util.List;

import org.opensails.ezfile.EzDir;
import org.opensails.ezfile.EzDirectoryWalker;
import org.opensails.ezfile.EzFile;

public class MemoryEzDir implements EzDir {
	protected MemoryEzFileSystem fs;

	protected String path;

	public MemoryEzDir(MemoryEzFileSystem fs, String path) {
		this.fs = fs;
		this.path = path;
	}

	public EzDir asDir() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists() {
		return fs.fileImpl(path).exists;
	}

	public EzFile file(String fileName) {
		return fs.file(path + "/" + fileName);
	}

	public String getPath() {
		return path;
	}

	public boolean isDirectory() {
		return true;
	}

	public EzDir mkdir(String relativePathNoLeadingSlash) {
		return fs.mkdir(path + "/" + relativePathNoLeadingSlash);
	}

	public void mkdirs() {
		if ("/".equals(path)) return;

		MemoryEzFileImpl impl = fs.fileImpl(path);
		if (impl.exists && !impl.isDirectory) throw new IllegalStateException(path + " cannot become a directory.");

		impl.exists = true;
		impl.isDirectory = true;
		parent().mkdirs();
	}

	public EzDir parent() {
		return new MemoryEzDir(fs, parentPath());
	}

	public void recurse(EzDirectoryWalker walker) {
		List<EzFile> files = fs.ls(path);
		for (EzFile file : files) {
			if (file instanceof EzDir) {
				walker.step((EzDir) file);
				((EzDir) file).recurse(walker);
			} else walker.step(file);
		}
	}

	public void save(String text) {
		throw new UnsupportedOperationException("Cannot save directory");
	}

	public EzDir subdir(String relativePathNoLeadingSlash) {
		return fs.file(path + "/" + relativePathNoLeadingSlash).asDir();
	}

	public String text() {
		throw new UnsupportedOperationException("Cannot read text from directory");
	}

	public void touch() {
	// TODO Auto-generated method stub

	}

	public EzFile touch(String fileName) {
		EzFile file = fs.file(path + "/" + fileName);
		file.touch();
		return file;
	}

	private String parentPath() {
		if ("/".equals(path)) return null;
		if (path.lastIndexOf("/") == 0) return "/";
		return path.substring(0, path.lastIndexOf("/"));
	}
}
