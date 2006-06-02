package org.opensails.sails.template;

import java.util.Set;

import org.opensails.sails.oem.FragmentKey;

/**
 * Provides storage of fragment content.
 * 
 * @author aiwilliams
 */
public interface IFragmentStore {

	Set<FragmentKey> keySet();

	void delete(FragmentKey identifier);

	String read(FragmentKey identifier);

	void write(FragmentKey identifier, String content);
}
