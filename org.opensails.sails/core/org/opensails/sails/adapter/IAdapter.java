package org.opensails.sails.adapter;

import org.opensails.sails.adapter.oem.PrimitiveAdapter;

/**
 * Converts Strings into Objects and back again.
 * 
 * These are used by various clients to deal the way of the web: everything is a
 * String. These do not generate HTML.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IAdapter<M, W> {
	IAdapter PRIMITIVE_ADAPTER = new PrimitiveAdapter();

	/**
	 * @param fromWeb String or String[] from web to be adapted
	 * @return value for model
	 * @throws AdaptationException
	 */
	M forModel(Class<? extends M> modelType, W fromWeb) throws AdaptationException;

	/**
	 * @param fromModel value from model to be adapted
	 * @return either a String or String[]
	 * @throws AdaptationException
	 */
	W forWeb(Class<? extends M> modelType, M fromModel) throws AdaptationException;

	/**
	 * @return the 'web type' the adapter converts to and from
	 */
	FieldType getFieldType();
}
