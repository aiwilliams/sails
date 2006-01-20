package org.opensails.generators;

import org.opensails.ezfile.EzFileSystem;

public interface IGeneratorTarget extends EzFileSystem {

	/**
	 * Places the target back into it's original mount state.
	 * <p>
	 * Since the target can be used across generators, and because a generator
	 * my fail, the target must be reset between generators to support the
	 * assumption that a target client holds.
	 */
	void reset();

	void save(String content, String... pathNodes);
}
