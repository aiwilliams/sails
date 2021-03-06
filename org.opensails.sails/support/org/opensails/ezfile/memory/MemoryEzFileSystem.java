package org.opensails.ezfile.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opensails.ezfile.EzDir;
import org.opensails.ezfile.EzFile;
import org.opensails.ezfile.EzFileSystem;
import org.opensails.ezfile.EzPath;

public class MemoryEzFileSystem implements EzFileSystem {
	protected final Map<String, MemoryEzFileImpl> fileSystemImpl = new HashMap<String, MemoryEzFileImpl>();
	protected String workingDir;

	public MemoryEzFileSystem() {
		MemoryEzFileImpl root = new MemoryEzFileImpl(this, "/");
		root.exists = true;
		root.isDirectory = true;
		fileSystemImpl.put("/", root);
		workingDir = "/";
	}

	public EzDir dir(String... pathNodes) {
		return new MemoryEzFile(this, absolutePath(EzPath.join(pathNodes))).asDir();
	}

	public EzFile file(String... pathNodes) {
		return new MemoryEzFile(this, absolutePath(EzPath.join(pathNodes)));
	}

	public List<EzFile> ls(String path) {
		path = absolutePath(path);
		List<EzFile> files = new ArrayList<EzFile>();
		for (Entry node : fileSystemImpl.entrySet()) {
			if (((String) node.getKey()).startsWith(path)) files.add((EzFile) node.getValue());
		}
		return files;
	}

	public EzDir mkdir(String... pathNodes) {
		String path = absolutePath(EzPath.join(workingDir, EzPath.join(pathNodes)));
		MemoryEzFileImpl dirImpl = fileImpl(path);
		dirImpl.exists = true;
		dirImpl.isDirectory = true;
		MemoryEzDir shamEzDir = new MemoryEzDir(this, path);
		shamEzDir.mkdirs();
		return shamEzDir;
	}

	public EzDir cd(String... pathNodes) {
		String path = EzPath.join(pathNodes);
		if (EzPath.isRelative(path)) workingDir = EzPath.join(workingDir, path);
		else workingDir = path;
		return dir(workingDir);
	}

	protected MemoryEzFileImpl fileImpl(String path) {
		path = absolutePath(path);
		MemoryEzFileImpl impl = (MemoryEzFileImpl) fileSystemImpl.get(path);
		if (impl == null) {
			impl = new MemoryEzFileImpl(this, path);
			fileSystemImpl.put(path, impl);
		}
		return impl;
	}

	protected MemoryEzFileImpl rootImpl() {
		return (MemoryEzFileImpl) fileSystemImpl.get("/");
	}

	private String absolutePath(String path) {
		if (!path.startsWith("/")) if (workingDir.equals("/")) path = workingDir + path;
		else path = workingDir + "/" + path;
		return path;
	}
}
