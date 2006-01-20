package org.opensails.ezfile;

public interface EzFileSystem {
	/**
	 * Changes to the specified directory. The directory may not exist.
	 * 
	 * @param pathNodes is a String[] of path fragments that will be appended to
	 *        create a complete path
	 * @return the new directory
	 */
	EzDir cd(String... pathNodes);

	/**
	 * Answers an EzDir for path. The directory may not exist. If it does, and
	 * is a file, an exception will be thrown.
	 */
	EzDir dir(String... pathNodes);

	/**
	 * Answers an EzFile for path. The file may not exist. The EzFile may be a
	 * directory.
	 */
	EzFile file(String... pathNodes);

	/**
	 * Answers an EzDir for path and creates it if it does not exist.
	 * 
	 * @param path
	 * @return
	 */
	EzDir mkdir(String... pathNodes);
}
