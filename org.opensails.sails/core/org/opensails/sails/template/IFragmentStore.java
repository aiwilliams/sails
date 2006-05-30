package org.opensails.sails.template;

import org.opensails.sails.oem.FragmentKey;

/**
 * Provides storage of fragment content.
 * 
 * @author aiwilliams
 */
public interface IFragmentStore {

	void delete(FragmentKey identifier);

	String read(FragmentKey identifier);

	void write(FragmentKey identifier, String content);
}
