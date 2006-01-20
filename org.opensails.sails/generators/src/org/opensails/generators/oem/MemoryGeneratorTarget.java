package org.opensails.generators.oem;

import org.opensails.ezfile.memory.MemoryEzFileSystem;
import org.opensails.generators.IGeneratorTarget;

public class MemoryGeneratorTarget extends MemoryEzFileSystem implements IGeneratorTarget {
	public void reset() {
		cd("/");
	}

	public void save(String content, String... pathNodes) {
		file(pathNodes).save(content);
	}
}
