package org.opensails.sails.template;

/**
 * Provides storage of fragment content.
 * 
 * @author aiwilliams
 */
public interface IFragmentStore {

	void delete(String contextIdentifier, String name);

	String read(String contextIdentifier, String name);

	void write(String contextIdentifier, String name, String content);
}
