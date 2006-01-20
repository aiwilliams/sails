package org.opensails.ezfile;

/**
 * When given to an EzDir#recurse(), called upon at each node
 * 
 * @author aiwilliams
 */
public interface EzDirectoryWalker {
	boolean step(EzDir directory);

	void step(EzFile file);
}
