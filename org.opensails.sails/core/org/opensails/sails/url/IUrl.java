package org.opensails.sails.url;

public interface IUrl {
	/**
	 * @return the absolute version of this url
	 */
	AbsoluteUrl absolute();

	/**
	 * @param name
	 * @return the unencoded value of the url query param for unencoded name
	 */
	String getQueryParam(String name);

	String render();

	/**
	 * Secures this url, regardless of where it points to. Be aware that this
	 * will cause the url to be fully qualified. That is good. If it can't be
	 * secured, it should throw an UnsupporedOperationException.
	 * 
	 * @return a secure version of this
	 */
	IUrl secure();

	/**
	 * @param name the name of the param, will be encoded
	 * @param value the value of the param, will be encoded
	 */
	void setQueryParam(String name, String value);
}
