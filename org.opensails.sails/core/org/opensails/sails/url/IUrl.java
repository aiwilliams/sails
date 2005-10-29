package org.opensails.sails.url;

public interface IUrl {
	String render();

	/**
	 * Secures this url, regardless of where it points to. Be aware that this
	 * will cause the url to be fully qualified. That is good. If it can't be
	 * secured, it should throw an UnsupporedOperationException.
	 * 
	 * @return a secure version of this
	 */
	IUrl secure();
}
