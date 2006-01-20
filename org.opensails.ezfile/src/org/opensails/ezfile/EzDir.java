package org.opensails.ezfile;

public interface EzDir extends EzFile {
	/**
	 * Returns a sub directory relative to this. The directory and it's parent's
	 * will be created.
	 * 
	 * @param pathNodes relative to this, will be combined to form complete path
	 * @return an EzDir that will exist, unless they already do and aren't
	 *         directories.
	 */
	EzDir mkdir(String... pathNodes);

	/**
	 * Opens an existing file in this directory. If the file does not exist, no
	 * exception is thrown.
	 * 
	 * @param pathNodes relative to this, will be combined to form complete path
	 * @return the EzFile that is in this directory with fileName
	 */
	public EzFile file(String... pathNodes);

	/**
	 * Returns a sub directory relative to this. The directory and it's parent
	 * may not exist.
	 * 
	 * @param pathNodes relative to this, will be combined to form complete path
	 * @return an EzDir that may or may not exist
	 */
	EzDir subdir(String... pathNodes);

	/**
	 * Creates a file relative to this.
	 * 
	 * @param pathNodes relative to this, will be combined to form complete path
	 * @return an EzFile that will exist and not be a directory
	 */
	EzFile touch(String... pathNodes);

	/**
	 * Beginning at this directory, recurse through every child directory and
	 * call the walker for each file.
	 * 
	 * @param walker
	 */
	void recurse(EzDirectoryWalker walker);
}
