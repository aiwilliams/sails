package org.opensails.generators.oem;

import org.opensails.ezfile.RealEzFileSystem;
import org.opensails.generators.IGeneratorTarget;

public class DiskGeneratorTarget extends RealEzFileSystem implements IGeneratorTarget {

	public DiskGeneratorTarget(String mountPoint) {
		super(mountPoint);
	}

	public void reset() {
		cd("/");
	}

	public void save(String content, String... pathNodes) {
		file(pathNodes).save(content);
	}

}
